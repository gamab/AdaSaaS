package adaaas.monitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import adaaas.Machine;
import net.elbandi.pve2api.data.VmOpenvz;
import proxmox.api.API;

public class Monitor implements Runnable{
	
	private List <Machine> list;
	private MonitoringTask mt;
	
	

	public Monitor(List <Machine> list) {
		super();
		this.list = list;
	}



	@Override
	public void run() {
		System.out.println("Yeah le moniteur est OK :D");
		
		
		//Configuration de la connexion
		if (!API.auth("aurel", "aurelbg")){
			System.exit(0);
		}
		
		//Recuperation de la liste des containers
		List <VmOpenvz>listOpenVZ =API.recupererContainers();
		
		//Initialisation de la donnée
		list=Monitor.containersToMachine(listOpenVZ);
		
		//On éteint tous les containers allumés just in case
		shutdownAll();
		
		
		//On en allume une seule 
		//TODO j'ai mis l'id en dur pour l'instant, a terme ça pourra être n'importe laquelle
		API.demarerContainer(231);
		
		

		
		//On crée le timer
		Timer time = new Timer(); 
		
		//On instancie la routine de monitoring
		 mt = new MonitoringTask(this); 
		
		//On programme la tâche 
		time.schedule(mt, 0, 10000); 

		
		
		
	}
	
	public static List <Machine> containersToMachine(List <VmOpenvz> list){
		List <Machine> retour = new ArrayList<Machine>();
		
		for (VmOpenvz vm: list){
			retour.add(new Machine(vm.getIp(), vm.getVmid(), vm.getName(),vm.isRunning(),vm.getCpu()));
		}
		return retour;
	}



	public List<Machine> getList() {
		return list;
	}



	public void setList(List<Machine> list) {
		this.list = list;
	}
	
	private void shutdownAll(){
		for(Machine m :list){
			if (m.isRunning()){
				API.arreterContainer(m.getId());
			}
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void deployNew(){
		for(Machine m:list){
			if (!m.isRunning()){
				API.demarerContainer(m.getId());
				return;
			}
		}
		//TODO détecter le fait qu'on est à cours de containers !!!!!!!!!!!!!
	}
	
	

}
