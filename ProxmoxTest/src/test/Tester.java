package test;

import java.util.List;

import net.elbandi.pve2api.Pve2Api;
import net.elbandi.pve2api.data.Node;

public class Tester {
	public static void main(String[] args) {
		Pve2Api api = new Pve2Api("localhost", "root", "pam", "aurelbg");
		
		try{
			api.login();
			System.out.println("Login OK");
			
			List <Node> nodes =api.getNodeList();
			
			for (Node node:nodes){
				System.out.println(node.getName());
			}
			
			
			
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("Login Failed");
		}
		
		
		
	}

}
