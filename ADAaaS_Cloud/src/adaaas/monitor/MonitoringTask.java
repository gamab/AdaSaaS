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
		
		//TODO R�cup�rer les containers en fonctionnement
		
		//TODO G�n�rer la liste des containers qui sont en bonne sant�(selon les crit�res de monitoring)
		
		//TODO Acceder � la liste
		
		//TODO Faire la diff�rence des deux listes et mettre � jour la liste
		
		
	}
	
	

}
