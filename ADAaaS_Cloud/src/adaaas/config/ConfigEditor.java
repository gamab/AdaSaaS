package adaaas.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import adaaas.ADAaas;
import adaaas.Machine;
import adaaas.MachineListe;

public class ConfigEditor extends Thread {

	//	private String configFile="home/febroshka/git/AdaSaaS/ADAaaS_Cloud/src/adaaas/config.txt";
	//	private String configFileBegin="home/febroshka/git/AdaSaaS/ADAaaS_Cloud/src/adaaas/configdeb.txt";




	private ArrayList<Machine> listMachine = new ArrayList<Machine>();

	private MachineListe wrapper;
	private String path;

	public ConfigEditor(MachineListe  wrap,String path) {
		this.wrapper= wrap;
		this.path=path;
	}

	public MachineListe getWrapper() {
		return wrapper;
	}

	public void setWrapper(MachineListe wrapper) {
		this.wrapper = wrapper;
	}


	private void getRunningMachines() {
		this.listMachine.clear();
		for (Machine mach : wrapper.getList()) {
			if (mach.isRunning()) {
				this.listMachine.add(mach);
			}
		}
	}

	private void copyBeginningToConfig(BufferedReader in, BufferedWriter out) {
		String ligne = "";
		try {
			while ((ligne = in.readLine()) != null) {
				out.write(ligne);
				out.newLine();
			}
		} catch (Exception e) {
			System.out.println("copy :"+e.toString());
		}
	}


	private void addMachinesToConfig(BufferedWriter out){
		String chaine2 = "";
		String reg = ("\\.");
		String[] ip;
		try {
			for ( Machine machine : listMachine){
				chaine2 = machine.getIp();
				ip = chaine2.split(reg);
				String toWrite = "server " + machine.getName() + " " + machine.getIp()
				+ ":8080 cookie S" + ip[3] + " check ";
				if (! machine.isEligible()) {
					toWrite += "weight 0";
				}
				//toWrite += "\n";

				out.write(toWrite);
				out.newLine();
			}

		} catch (Exception e) {
			System.out.println("add : "+e.toString());
		}

	}


	public  void run() {
		try {
			while (true) {
				//wait to be notified
				System.out.println("EDITOR | Sleep");
				synchronized(ADAaas.LOCK){
					ADAaas.LOCK.wait();
				}

				System.out.println("EDITOR | Wake up");
				//First get the machines currently running from the list of machines
				getRunningMachines();
				//Create the flows
				BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path+"/"+"buf")));
				BufferedWriter out = new BufferedWriter(new FileWriter(path+"/"+"haproxy.cfg"));
				//Copy the beginning of the config file
				copyBeginningToConfig(in, out);
				//Add the machines to the config file
				addMachinesToConfig(out);		
				//close the flows
				in.close();
				out.close();
				
				

				//Run the script
				Runtime rt =Runtime.getRuntime();
				Process proc=rt.exec(path+"/haproxy_restart");
				BufferedReader stdInput = new BufferedReader(new 
						InputStreamReader(proc.getInputStream()));

				//read the output from the command
				System.out.println("Output of the command:");
				String s = null;
				while ((s = stdInput.readLine()) != null) {
					System.out.println(s);
				}

			}
		}catch (Exception e) {
			System.out.println("Le configEditor a planté. ");
			e.printStackTrace();
		
		}
	}

}

