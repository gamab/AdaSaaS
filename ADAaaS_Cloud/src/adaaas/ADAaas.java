package adaaas;

import adaaas.config.ConfigEditor;
import adaaas.monitor.Monitor;

public class ADAaas {
	public static Object LOCK =new Object();

	public static void main(String[] args) {
		if (args.length!=1){
			System.out.println("The path of the config file is needed");
			System.exit(1);

		}
		String path = args[0];

		MachineListe wrapper = new MachineListe();
		ConfigEditor editor =new ConfigEditor(wrapper,path);
		editor.start();

		new Monitor(wrapper).run();





	}

}
