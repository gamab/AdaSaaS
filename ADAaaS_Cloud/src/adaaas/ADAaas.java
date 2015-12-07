package adaaas;

import adaaas.config.ConfigEditor;
import adaaas.monitor.Monitor;

public class ADAaas {

	public static void main(String[] args) {
		
		MachineListe wrapper = new MachineListe();

		new ConfigEditor(wrapper).run();
		new Monitor(wrapper).run();

		for (int i = 0;i<1000;i++){
			try {
				System.out.println("Affichage de la liste dans le main ");
				for (Machine m :wrapper.getList()){
					System.out.println(m);
				}
				Thread.sleep(5000);
			} catch (Exception e){
				e.printStackTrace();
			}

		}


	}

}
