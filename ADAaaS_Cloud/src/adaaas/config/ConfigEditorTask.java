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
		System.out.println("Coucou editor de fichier :D");
		System.out.println("Liste coté editor : ");
		for(Machine m :this.editor.getWrapper().getList()){
			System.out.println(m);
		}
		this.editor.readAndUpdate();
		
		
	}
	

}
