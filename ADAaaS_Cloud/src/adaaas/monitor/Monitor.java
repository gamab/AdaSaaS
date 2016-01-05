package adaaas.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import adaaas.Machine;
import adaaas.MachineListe;
import adaaas.config.ConfigEditor;
import net.elbandi.pve2api.data.VmOpenvz;
import proxmox.api.API;

public class Monitor implements Runnable{
	
	private MachineListe wrapper;
	private MonitoringTask mt;
	private ConfigEditor editor;
	
	

	public Monitor(MachineListe wrapper,ConfigEditor editor) {
		this.wrapper=wrapper;
		this.editor=editor;
	}



	@Override
	public void run() {
		
		
		//Configuration de la connexion
		if (!API.auth("aurel", "______")){
			System.exit(0);
		}
		
		//Recuperation de la liste des containers
		List <VmOpenvz>listOpenVZ =API.recupererContainers();
		
		//Initialisation de la donn�e
		wrapper.setList(Monitor.containersToMachine(listOpenVZ));
		
		//On �teint tous les containers allum�s just in case
		shutdownAll();
		
		
		//On en allume une seule 
		//TODO j'ai mis l'id en dur pour l'instant, a terme �a pourra �tre n'importe laquelle
		API.demarerContainer(231);
		
		

		
		//On cr�e le timer
		Timer time = new Timer(); 
		
		//On instancie la routine de monitoring
		 mt = new MonitoringTask(this); 
		
		//On programme la t�che 
		time.schedule(mt, 5000, 10000); 

		
		
		
	}
	
	public static List <Machine> containersToMachine(List <VmOpenvz> list){
		List <Machine> retour = new ArrayList<Machine>();
		
		for (VmOpenvz vm: list){
			if (vm.getVmid() >200){
				retour.add(new Machine(vm.getIp(), vm.getVmid(), 
						vm.getName(),vm.isRunning(),vm.getCpu(),
						vm.getNetin(),vm.getNetout()));
			}
		}
		return retour;
	}
	
	public MachineListe getWrapper() {
		return wrapper;
	}



	public void setWrapper(MachineListe wrapper) {
		this.wrapper = wrapper;
	}
	
	



	public ConfigEditor getEditor() {
		return editor;
	}



	public void setEditor(ConfigEditor editor) {
		this.editor = editor;
	}



	private void shutdownAll(){
		for(Machine m :wrapper.getList()){
			if (m.isRunning()){
				API.arreterContainer(m.getId());
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	//Meme pas besoin de detecter le fait qu'on est � cours de container ! �a se fera tout seul en refusant les requetes.
	public void deployNew(){
		for(Machine m:wrapper.getList()){
			if (!m.isRunning()){
				API.demarerContainer(m.getId());
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}
	
	public void stop(Machine m){
		API.arreterContainer(m.getId());
	}
	
	

}
