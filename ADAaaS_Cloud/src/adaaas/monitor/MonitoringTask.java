package adaaas.monitor;

import java.util.List;
import java.util.TimerTask;

import adaaas.ADAaas;
import adaaas.Machine;
import proxmox.api.API;

public class MonitoringTask extends TimerTask {
	
	private Monitor monitor;
	private boolean deploymentMade;
	
	
	public MonitoringTask(Monitor monitor){
		this.monitor=monitor;
		deploymentMade =false;
	}

	@Override
	public synchronized void run() {
		System.out.println("Salut poto je suis la routine de monitoring");
		
		
		//On travaille avec la nouvelle liste
		List<Machine> copie = Monitor.containersToMachine(API.recupererContainers());
		System.out.println("Nouvelle liste");
		if (deploymentMade){
			deploymentMade=false;
			
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
		}
		System.out.println("Nombre de machine eligible : "+nombreMachineEligible);
		
		if (nombreMachineEligible<=1){
			monitor.deployNew();
			deploymentMade =true;
		}
		
		//TODO Maintenant il faudrait identifier le moment ou une machine n'a pas de client pour l'éteindre.
		

		
	
		//Mise à jour de la liste
		monitor.getWrapper().setList(copie);
		System.out.println("Liste du moniteur");
		for (Machine m:monitor.getWrapper().getList()){
			System.out.println(m);
		}
		
		
		
	}
	
	

}
