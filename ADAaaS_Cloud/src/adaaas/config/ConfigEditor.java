package adaaas.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import adaaas.Machine;
import adaaas.MachineListe;

public class ConfigEditor implements Runnable {

	private String configFile="C:/Users/Aur�lien/test.conf";
	private String configFileSortie="C:/Users/Aur�lien/buf";

	//	private String configFile = "/home/febroshka/Téléchargements/AdaSaaS-master/ADAaaS_Cloud/src/adaaas/config/configfile";
	//	private final String configFileSortie = "/home/febroshka/Téléchargements/AdaSaaS-master/ADAaaS_Cloud/src/adaaas/config/configfilesortie";

	private ArrayList<String> listOfIPs =new ArrayList<String>();;
	private ArrayList<String> listMachine = new ArrayList<String>();

	//	private InputStream ips;
	//	private InputStreamReader ipsr;
	//	private BufferedReader br;
	//	private BufferedWriter fichier;

	String ipRegexp = "(25[0-5]|2[0-4][0-9]|[0|1]?[0-9]{1,2})\\.(25[0-5]|2[0-4][0-9]|[0|1]?[0-9]{1,2})\\.(25[0-5]|2[0-4][0-9]|[0|1]?[0-9]{1,2})\\.(25[0-5]|2[0-4][0-9]|[0|1]?[0-9]{1,2})";
	String beforeIPRegExp = ".+server.+";


	private MachineListe wrapper;



	public ConfigEditor(MachineListe wrapper) {
		this.wrapper=wrapper;
	}

	public MachineListe getWrapper() {
		return wrapper;
	}


	public void setWrapper(MachineListe wrapper) {
		this.wrapper = wrapper;
	}

	//	private void initFlow(String source, String dest) {
	//		try {
	//			ips = new FileInputStream(source);
	//			ipsr = new InputStreamReader(ips);
	//			br = new BufferedReader(ipsr);
	//			fichier = new BufferedWriter(new FileWriter(dest));
	//		} catch (Exception e) {
	//			System.out.println(e.toString());
	//		}
	//	}

	//	private void closeFlow() {
	//		try {
	//			fichier.close();
	//			br.close();
	//			buildList();
	//			updateFile();
	//		} catch (Exception e) {
	//			System.out.println(e.toString());
	//		}
	//	}

	private void printListOfIPs() {
		System.out.println("List of IP adresses :");
		for (String ip : this.listOfIPs) {
			System.out.println(ip);
		}
	}

	private boolean checkIfPresent(String ip) {
		return this.listOfIPs.contains(ip);
	}

	private void removeIP(String ip) {
		InputStream ips;
		InputStreamReader ipsr;
		BufferedReader br;
		BufferedWriter fichier;



		try {
			ips = new FileInputStream(configFile);
			ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);
			fichier = new BufferedWriter(new FileWriter(configFileSortie));
			String ligne = "";
			while ((ligne = br.readLine()) != null) {
				if (ligne.matches(beforeIPRegExp)
						&& ligne.substring(22, 33).equals(ip)) {

				} else {

					fichier.write(ligne);
					fichier.newLine();
				}
			}
			fichier.close();
			br.close();
			buildList();
			updateFile();

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {

		}
	}

	private void addIP(Machine machine) {
		InputStream ips;
		InputStreamReader ipsr;
		BufferedReader br;
		BufferedWriter fichier;

		try {
			ips = new FileInputStream(configFile);
			ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);
			fichier = new BufferedWriter(new FileWriter(configFileSortie));
			String ligne;
			while ((ligne = br.readLine()) != null) {
				fichier.write(ligne);
				fichier.newLine();
			}
			fichier.write("        ");
			fichier.write("server "+machine.getName()+" "+machine.getIp()+":80"
					+ " " + "cookie S" + machine.getIp().charAt(10) + " check");
			fichier.newLine();
			fichier.close();
			br.close();
			buildList();
			updateFile();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void updateFile() {
		String ligne = "";
		InputStream ips;
		InputStreamReader ipsr;
		BufferedReader br;
		BufferedWriter fichier;
		try {
			ips = new FileInputStream(configFile);
			ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);
			fichier = new BufferedWriter(new FileWriter(configFileSortie));
			while ((ligne = br.readLine()) != null) {
				fichier.write(ligne);
				fichier.newLine();
			}
			fichier.close();
			br.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}


	public void readAndUpdate() {

		for (Machine machine : wrapper.getList()) {
			// System.out.println(" IsElegible : " + machine.isEligible());
			if (machine.isEligible()) {
				if (checkIfPresent(machine.getIp())) {
					// System.out.println("Présente dans la liste");
				} else {
					// System.out.println("absente de la liste");
					this.addIP(machine);
				}
			} else {
				if (checkIfPresent(machine.getIp())) {
					// System.out.println("Présente dans la liste");

					this.removeIP(machine.getIp());
				} else {
					// System.out.println("absente de la liste");
				}
			}
		}
	}

	private void buildList() {
		this.listOfIPs.clear();
		String ligne = "";
		InputStream ips;
		InputStreamReader ipsr;
		BufferedReader br;
		BufferedWriter fichier;

		try {

			ips = new FileInputStream(this.configFile);
			ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);

			while ((ligne = br.readLine()) != null) {
				if (ligne.matches(beforeIPRegExp)) {
					String inter = ligne.substring(22, 33);
					if (inter.matches(ipRegexp)) {
						listOfIPs.add(inter);
					}
				}
			}
			br.close();

			for (String ip : listOfIPs) {
				if (listMachine.contains(ip)) {
				} else {
					this.removeIP(ip);
				}
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}


	}





	private void printConfigFile(String file) {
		String ligne = "";
		InputStream ips;
		InputStreamReader ipsr;
		BufferedReader br;
		BufferedWriter fichier;
		try {
			
			ips = new FileInputStream(file);
			ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);

			while ((ligne = br.readLine()) != null) {
				System.out.println(ligne);
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void buildMachineList(List<Machine> list) {
		this.listMachine.clear();
		for (Machine mach : list) {
			if (mach.isEligible()) {
				this.listMachine.add(mach.getIp());
			}

		}
	}

	private void printListmachine() {
		for (String ip : this.listMachine) {
			System.out.println(ip);
		}
	}

	@Override
	public void run() {
		// config
		buildList();
		//readAndUpdate();

		// lance la tache periodique


		//On cr�e le timer
		Timer time = new Timer(); 

		//On instancie la routine de monitoring
		ConfigEditorTask cet = new ConfigEditorTask(this);

		//On programme la t�che 
		time.schedule(cet, 30000, 10000); 

	}

	public static void main(String[] args) {

		List<Machine> wrap = new ArrayList<Machine>();
		// (String ip, int id, String name,boolean running,float cpu)
		Machine machine1 = new Machine("192.168.2.1", 1, "bijou", true,
				(float) 1.33);
		machine1.setEligible(false);
		Machine machine2 = new Machine("192.168.2.2", 1, "bijou2", true,
				(float) 1.33);
		machine2.setEligible(false);
		Machine machine3 = new Machine("192.168.2.3", 1, "bijou3", true,
				(float) 1.33);
		Machine machine4 = new Machine("192.168.2.4", 1, "bijou4", false,
				(float) 1.33); // à supprimer
		Machine machine5 = new Machine("192.168.2.5", 1, "bijou2", true,
				(float) 1.33); // à ajouter
		machine5.setEligible(true);
		Machine machine6 = new Machine("192.168.2.9", 1, "bijou2", true,
				(float) 1.33); // à ajouter
		machine6.setEligible(true);
		wrap.add(machine1);
		wrap.add(machine2);
		wrap.add(machine3);
		wrap.add(machine4);
		wrap.add(machine5);
		wrap.add(machine6);
		MachineListe wrapper = new MachineListe();
		wrapper.setList(wrap);

		ConfigEditor edit1 = new ConfigEditor(wrapper);
		System.out.println(" -- Fichier de config initial --");
		edit1.printConfigFile(edit1.configFile);

		/*
		 * edit1.buildList(); System.out.println("Fin fichier de config");
		 * System.out.println("Début liste machine");
		 * edit1.buildMachineList(wrap); edit1.printListmachine();
		 * System.out.println("Fin liste machine");
		 * System.out.println(edit1.checkIfPresent("192.168.2.2"));
		 * System.out.println(edit1.checkIfPresent("192.168.2.3"));
		 * //System.out.println("Test suppression d'une IP : 192.168.2.3");
		 * //edit1.removeIP("192.168.2.3");
		 * //System.out.println(" -- Fichier de sortie --");
		 * //edit1.printConfigFile(edit1.configFile);
		 * System.out.println("Test ajout d'une IP : 192.168.2.4");
		 * //edit1.addIP("192.168.2.7");
		 * System.out.println(" -- Fichier en sortie --");
		 * edit1.printConfigFile(edit1.configFile);
		 * ///edit1.removeIP("192.168.2.4");
		 * edit1.printConfigFile(edit1.configFile);
		 */

		System.out.println("Début liste IP");
		edit1.buildList();
		edit1.printListOfIPs();
		System.out.println("Fin liste IP");

		System.out.println("Début liste machine");
		edit1.buildMachineList(wrap);
		edit1.printListmachine();
		System.out.println("Fin liste machine");
		edit1.readAndUpdate();
		edit1.printConfigFile(edit1.configFile);

	}

}

