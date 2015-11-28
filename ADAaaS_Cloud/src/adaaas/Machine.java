package adaaas;

public class Machine {
	
	private String ip;
	private int id;
	private String name;
	private boolean running;
	private boolean eligible;
	private float cpu;
	
	public Machine(String ip, int id, String name,boolean running,float cpu) {
		this.ip = ip;
		this.id = id;
		this.name = name;
		this.eligible=false;
		this.running=running;
		this.cpu =cpu;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEligible() {
		return eligible;
	}

	public void setEligible(boolean eligible) {
		this.eligible = eligible;
	}
	
	

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public float getCpu() {
		return cpu;
	}

	public void setCpu(float cpu) {
		this.cpu = cpu;
	}

	@Override
	public String toString() {
		return "Machine [ip=" + ip + ", id=" + id + ", name=" + name + ", running=" + running + ", eligible=" + eligible
				+ ", cpu=" + cpu + "]";
	}
	
	
	public boolean updateEligible(){
		if (this.isRunning()&& this.cpu<0.50){
			eligible =true;
		} else {
			eligible=false;
		}
		return eligible;
	}
	
	




	
	
	
	
	
	

}
