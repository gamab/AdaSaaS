package adaaas.monitor;

import java.util.List;
import java.util.TimerTask;

import adaaas.Machine;
import proxmox.api.API;

public class MonitoringTask extends TimerTask {
	
	private Monitor monitor;
	
	
	public MonitoringTask(Monitor monitor){
		this.monitor=monitor;
	}

	@Override
	public void run() {
		System.out.println("Salut poto je suis la routine de monitoring");
		
		
		//On travaille avec la nouvelle liste
		List<Machine> copie = Monitor.containersToMachine(API.recupererContainers());
		System.out.println("Nouvelle liste");
		
		int nombreMachineEligible = 0;
		for(Machine m :copie){
			if (m.updateEligible()){
				nombreMachineEligible++;
			}
		}
		System.out.println("Nombre de machine eligible : "+nombreMachineEligible);
		
		if (nombreMachineEligible<=1){
			monitor.deployNew();
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
