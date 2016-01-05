package adaaas;

public class Machine {
	
	private String ip;
	private int id;
	private String name;
	private boolean running;
	private boolean eligible;
	private boolean deletable;
	private float cpu;
	private long netin;
	private long netout;
	
	
	//Si ce nombre arrive à 5 alors il n'y a plus personne sur le Container
	private int count;
	
	public Machine(String ip, int id, String name,boolean running,float cpu,long netin,long netout) {
		this.ip = ip;
		this.id = id;
		this.name = name;
		this.eligible=false;
		this.running=running;
		this.cpu =cpu;
		this.netin=netin;
		this.netout=netout;
		this.count=0;
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
	
	


	
	
	public long getNetin() {
		return netin;
	}

	public void setNetin(int netin) {
		this.netin = netin;
	}

	public long getNetout() {
		return netout;
	}

	public void setNetout(int netout) {
		this.netout = netout;
	}
	
	

	@Override
	public String toString() {
		return "Machine [ip=" + ip + ", id=" + id + ", name=" + name + ", running=" + running + ", eligible=" + eligible
				+ ", cpu=" + cpu + ", netin=" + netin + ", netout=" + netout + "]";
	}

	
	//TODO update the eligibility criteria
	public boolean updateEligible(){
		if (this.isRunning()&& this.cpu<0.90){
			eligible =true;
		} else {
			eligible=false;
		}
		return eligible;
	}
	
	
	//TODO update the deletability criteria
	public boolean updateDeletable(){
		if (isRunning() && this.netin<1000 && this.netout<1000){
			count++;
		} else {
			count = 0;
		}
		
		if (count>5){
			deletable = true;
		}
		
		
		return deletable;
	}

	public void setDelete(boolean b) {
		deletable=b;
		
	}
	
	




	
	
	
	
	
	

}
