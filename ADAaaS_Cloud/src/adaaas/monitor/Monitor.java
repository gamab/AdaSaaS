package adaaas.monitor;

import java.util.Timer;

import adaaas.ListWrapper;
import test.ScheduledTask;

public class Monitor implements Runnable{
	
	private ListWrapper wrapper;
	private MonitoringTask mt;
	
	

	public Monitor(ListWrapper wrapper) {
		super();
		this.wrapper = wrapper;
	}



	@Override
	public void run() {
		System.out.println("Yeah le moniteur est OK :D");
		
		
		//TODO Configuration de la connexion et initialisation de la liste
		
		//On cr�e le timer
		Timer time = new Timer(); 
		
		//On instancie la routine de monitoring
		 mt = new MonitoringTask(this); 
		
		//On programme la t�che 
		time.schedule(mt, 0, 1000); 

		
		
		
	}

}
