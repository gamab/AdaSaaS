package adaaas;

import adaaas.config.ConfigEditor;
import adaaas.monitor.Monitor;

public class ADAaas {
	
	public static void main(String[] args) {
		ListWrapper wrapper = new ListWrapper();
		
		new ConfigEditor(wrapper).run();
		new Monitor(wrapper).run();
		
	}

}
