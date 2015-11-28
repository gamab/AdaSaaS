package adaaas.config;

import java.util.List;

import adaaas.Machine;

public class ConfigEditor implements Runnable {
	
	private List <Machine> list;
	
	
	
	

	public ConfigEditor(List<Machine> wrapper) {
		this.list = wrapper;
	}





	@Override
	public void run() {
		System.out.println("Yeah le ConfigEditor est OK :D");
		
	}

}
