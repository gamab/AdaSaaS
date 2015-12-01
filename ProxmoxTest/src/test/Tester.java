package test;



import java.util.List;

import net.elbandi.pve2api.Pve2Api;
import net.elbandi.pve2api.data.VmOpenvz;
import net.elbandi.pve2api.data.resource.Vm;
import proxmox.api.API;

public class Tester {
	
	public static void main(String[] args) {
		
//		VmOpenvz vm =new VmOpenvz();
//		vm.setName("coucou");
//		vm.setOstemplate("local:vztmpl/ubuntu-10.04-AdaaaS_10.04-4_i386.tar.gz");
//		vm.setMemory(512);
//		vm.setVmid(400);
//		vm.setSwap(512);
//		API.creerContainer(vm);
		
		
		
		Pve2Api api = API.api;
		VmOpenvz vm2 = new VmOpenvz();
		
		try {
			api.getOpenvzConfig(API.mainNode.getName(),231,vm2);
			vm2.setHostname("coucou.test");
			vm2.setOstemplate("local:vztmpl/ubuntu-10.04-AdaaaS_10.04-4_i386.tar.gz");
			vm2.setNetif("ifname=eth0,bridge=vmbr2");
			vm2.setVmid(232);
			api.createOpenvz(API.mainNode.getName(),vm2);
			Thread.sleep(3000);
			//TODO Ajouter IP adress, demander à gabriel.
		} catch (Exception e){
			e.printStackTrace();
		}
		
		
		
		
	}
}
