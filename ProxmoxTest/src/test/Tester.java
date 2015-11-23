package test;



import proxmox.api.API;

public class Tester {
	
	public static void main(String[] args) {
		API.auth("aurel", "aurelbg");
		API.arreterContainer(222);
	}
}
