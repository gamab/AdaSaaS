package adaaas;

public class ConfigEditor implements Runnable {
	
	private ListWrapper wrapper;
	
	
	
	

	public ConfigEditor(ListWrapper wrapper) {
		this.wrapper = wrapper;
	}





	@Override
	public void run() {
		System.out.println("Yeah le ConfigEditor est OK :D");
		
	}

}
