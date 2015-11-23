package adaaas.monitor;

import java.util.TimerTask;

public class MonitoringTask extends TimerTask {
	
	private Monitor monitor;
	
	
	public MonitoringTask(Monitor monitor){
		this.monitor=monitor;
	}

	@Override
	public void run() {
		System.out.println("Salut poto je suis la routine de monitoring");
		
		//TODO Récupérer les containers en fonctionnement
		
		//TODO Générer la liste des containers qui sont en bonne santé(selon les critères de monitoring)
		
		//TODO Acceder à la liste
		
		//TODO Faire la différence des deux listes et mettre à jour la liste
		
		
	}
	
	

}
