package test;



import java.util.ArrayList;
import java.util.List;

import net.elbandi.pve2api.data.VmOpenvz;
import proxmox.api.API;

public class Tester {
	
	public static void main(String[] args) {
		API.authRoot("root", "_______");

		
		List <VmOpenvz> list = new ArrayList<VmOpenvz>();
		while (true){
			try{
				list = API.recupererContainers();
				for(VmOpenvz vm:list){
					if (vm.getVmid() == 112)
						System.out.println(vm);
				}
				Thread.sleep(60000);
				
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		
		
		
		
	}
}
