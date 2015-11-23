package adaaas;

public class Monitor implements Runnable{
	private ListWrapper wrapper;
	
	

	public Monitor(ListWrapper wrapper) {
		super();
		this.wrapper = wrapper;
	}



	@Override
	public void run() {
		System.out.println("Yeah le moniteur est OK :D");
		
	}

}
