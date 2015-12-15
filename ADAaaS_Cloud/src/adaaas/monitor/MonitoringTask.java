package adaaas.monitor;

import java.util.List;
import java.util.TimerTask;

import adaaas.ADAaas;
import adaaas.Machine;
import proxmox.api.API;

public class MonitoringTask extends TimerTask {
	
	private Monitor monitor;
	private boolean updateMade;
	
	
	public MonitoringTask(Monitor monitor){
		this.monitor=monitor;
		updateMade =false;
	}

	@Override
	public synchronized void run() {
		System.out.println("Salut poto je suis la routine de monitoring");
		
		
		//On travaille avec la nouvelle liste
		List<Machine> copie = Monitor.containersToMachine(API.recupererContainers());
		System.out.println("Nouvelle liste");
		if (updateMade){
			updateMade=false;
			
			synchronized(ADAaas.LOCK){
				System.out.println("Monitor : Je notifie ! ");
				ADAaas.LOCK.notify();
			}
		}
		
		int nombreMachineEligible = 0;
		for(Machine m :copie){
			if (m.updateEligible()){
				nombreMachineEligible++;
			}
			
			if (m.updateDeletable()){
				//monitor.stop(m);
			}
		}
		
		
		
		
		System.out.println("Nombre de machine eligible : "+nombreMachineEligible);
		
		
		//TODO ajouter toute la partie suppression ici.
		if (nombreMachineEligible<=1 ){
			monitor.deployNew();
			updateMade =true;
		}
		
		

		
	
		//Mise à jour de la liste
		monitor.getWrapper().setList(copie);
		System.out.println("Liste du moniteur");
		for (Machine m:monitor.getWrapper().getList()){
			System.out.println(m);
		}
		
		
		
	}
	
	

}
