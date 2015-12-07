package adaaas;

import java.util.ArrayList;
import java.util.List;

public class MachineListe {
	private List <Machine> list ;
	
	public MachineListe(){
		list = new ArrayList<Machine>();
	}

	public List<Machine> getList() {
		return list;
	}

	public void setList(List<Machine> list) {
		this.list = list;
	}
	
	
	

}
