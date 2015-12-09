package adaaas.config;

import java.util.TimerTask;

import adaaas.Machine;

public class ConfigEditorTask extends TimerTask {
	
	private ConfigEditor editor;
	
	
	public ConfigEditorTask (ConfigEditor editor){
		this.editor=editor;
	}

	@Override
	public void run() {
		System.out.println("----------------------------");
		System.out.println("EDITOR");
		System.out.println("Liste coté editor : ");
		for(Machine m :this.editor.getWrapper().getList()){
			System.out.println(m);
		}
		editor.buildMachineList();
		editor.buildList();
		
		System.out.println("ListMachine");
		for (String s : editor.listMachine){
			System.out.println(s);
		}
		System.out.println("List IP");
		for(String s:editor.listOfIPs){
			System.out.println(s);
		}
		this.editor.readAndUpdate();
		System.out.println("----------------------------");
	}
	

}
