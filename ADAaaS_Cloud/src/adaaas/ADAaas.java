package adaaas;

import adaaas.config.ConfigEditor;
import adaaas.monitor.Monitor;

public class ADAaas {

	public static void main(String[] args) {
		if (args.length!=1){
			System.out.println("Where is the config file bro ?");
			System.exit(1);

		}
		String path = args[0];

		MachineListe wrapper = new MachineListe();
		ConfigEditor editor =new ConfigEditor(wrapper,path);
		editor.run();

		new Monitor(wrapper,editor).run();





	}

}
