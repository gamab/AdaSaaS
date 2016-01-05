package adaaas.monitor;

import java.util.List;
import java.util.TimerTask;

import adaaas.ADAaas;
import adaaas.Machine;
import proxmox.api.API;

public class MonitoringTask extends TimerTask {
	
	private Monitor monitor;
	//private boolean updateMade;
	
	
	public MonitoringTask(Monitor monitor){
		this.monitor=monitor;
		//updateMade =true;
	}

	@Override
	public synchronized void run() {
		System.out.println("--DEBUT DE LA ROUTINE PERIODIQUE DE MONITORING--");
		
		
		//On travaille avec la nouvelle liste
		List<Machine> copie = Monitor.containersToMachine(API.recupererContainers());

			
			synchronized(ADAaas.LOCK){
				System.out.println("MONITOR | Notify ");
				ADAaas.LOCK.notify();
			}
		
		int nombreMachineEligible = 0;
		for(Machine m :copie){
			if (m.updateEligible()){
				nombreMachineEligible++;
			}
			
			if (m.updateDeletable()){
				monitor.stop(m);
			}
		}
		
		
		
		
		System.out.println("MONITOR | Eligible Machines: "+nombreMachineEligible);
		
		

		if (nombreMachineEligible<=1 ){
			monitor.deployNew();
		}
		
		

		
	
		//Mise à jour de la liste
		monitor.getWrapper().setList(copie);
		System.out.println("MONITOR | List");
		for (Machine m:monitor.getWrapper().getList()){
			System.out.println(m);
		}
		
		
		
	}
	
	

}
