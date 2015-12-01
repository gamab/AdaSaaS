package adaaas;

import java.util.ArrayList;
import java.util.List;

import adaaas.config.ConfigEditor;
import adaaas.monitor.Monitor;

public class ADAaas {
	
	public static void main(String[] args) {
		List <Machine> list = new ArrayList<Machine>();
		
		new ConfigEditor(list).run();
		new Monitor(list).run();
		
	}

}
