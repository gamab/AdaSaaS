package test;

import net.elbandi.pve2api.Pve2Api;
import net.elbandi.pve2api.data.VmOpenvz;

public class Tester {
	public static void main(String[] args) {
		Pve2Api api = new Pve2Api("localhost", "root", "pam", "aurelbg");
		try {
			api.login();
			System.out.println("Login is OK");
			
			System.out.println("Here are my nodes !");
			for(String s1:api.getNodeList()){
				System.out.println(s1);
				System.out.println("Containers for this node :");
				for(VmOpenvz vm:api.getOpenvzCTs(s1)){
					System.out.println(vm.toString());
				}
			}
			
			
			
		}catch (Exception e){
			System.out.println("Login is not OK");
			e.printStackTrace();
		}
	}

}
