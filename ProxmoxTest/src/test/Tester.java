package test;



import java.util.List;

import net.elbandi.pve2api.data.VmOpenvz;
import proxmox.api.API;

public class Tester {
	
	public static void main(String[] args) {
		API.auth("aurel", "aurelbg");
		//API.demarerContainer(222);
		//API.creerContainer(222);
		//API.arreterContainer(222);
		
		List<VmOpenvz> CTs = API.recupererContainers();
		
		for(VmOpenvz ct:CTs){
			System.out.println(ct);
		}
		
		
		
	}
}
