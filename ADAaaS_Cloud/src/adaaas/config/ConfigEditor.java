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

	private String configFile="/etc/haproxy/haproxy.conf";
	private String configFileSortie="/etc/haproxybuf";

	ArrayList<String> listOfIPs =new ArrayList<String>();;
	ArrayList<String> listMachine = new ArrayList<String>();

	private InputStream ips;
	private InputStreamReader ipsr;
	private BufferedReader br;
	private BufferedWriter fichier;

	String ipRegexp = "(25[0-5]|2[0-4][0-9]|[0|1]?[0-9]{1,2})\\.(25[0-5]|2[0-4][0-9]|[0|1]?[0-9]{1,2})\\.(25[0-5]|2[0-4][0-9]|[0|1]?[0-9]{1,2})\\.(25[0-5]|2[0-4][0-9]|[0|1]?[0-9]{1,2})";
	String beforeIPRegExp = "        server.+";
	String regExpTest = "        server [a-z]+[0-9]?";


	private MachineListe wrapper;
	private String path;

	public ConfigEditor(MachineListe wrapper,String path) {
		this.wrapper=wrapper;
		this.path=path;
	}

	public MachineListe getWrapper() {
		return wrapper;
	}

	public void setWrapper(MachineListe wrapper) {
		this.wrapper = wrapper;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////

	private void initFlow(String source, String dest) {
		try {
			ips = new FileInputStream(source);
			ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);
			fichier = new BufferedWriter(new FileWriter(dest));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void closeFlow() {
		try {
			fichier.close();
			br.close();
			updateFile();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void printListOfIPs() {
		System.out.println("List of IP adresses :");
		for (String ip : this.listOfIPs) {
			System.out.println(ip);
		}
	}

	private boolean checkIfPresent(String ip) {
		for (String s:listOfIPs){
			if (s.equals(ip))
				return true;
		}
		return false;
	}

	private void removeIP(String ip) {
		initFlow(configFile, configFileSortie);
		try {
			String ligne = "";
			while ((ligne = br.readLine()) != null) {
				if (ligne.matches(beforeIPRegExp)
						&& ligne.substring(22, 33).equals(ip)) {

				} else {

					fichier.write(ligne);
					fichier.newLine();
				}
			}
			closeFlow();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void addIP(Machine machine) {
		initFlow(configFile, configFileSortie);
		try {
			String ligne;
			while ((ligne = br.readLine()) != null) {
				fichier.write(ligne);
				fichier.newLine();
			}
			fichier.write("        ");

			String chaine2 = machine.getIp();
			String reg = ("\\.");
			String[] tableau = chaine2.split(reg);
			String inter = tableau[2];

			fichier.write("server " + machine.getName() + " " + machine.getIp()
			+ ":80 cookie S" + tableau[3] + " check");

			fichier.newLine();

			fichier.close();
			br.close();
			updateFile();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void updateFile() {
		String ligne = "";
		initFlow(configFileSortie, configFile);
		try {

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


			if (machine.isEligible()) {
				if (!checkIfPresent(machine.getIp())) {
					this.addIP(machine);
				}
			} else {
				if (checkIfPresent(machine.getIp())) {
					System.out.println("SUPPRIME");
					this.removeIP(machine.getIp());

				}
			}
		}
	}

	public void buildList() {
		this.listOfIPs.clear();

		try {
			String ligne;
			ips = new FileInputStream(this.configFile);
			ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);

			fichier = new BufferedWriter(new FileWriter(configFileSortie));

			while ((ligne = br.readLine()) != null) {
				if (!(ligne.matches(beforeIPRegExp))) {
					fichier.write(ligne);
					fichier.newLine();
				} else {
					String chaine2 = ligne.substring(8, ligne.length());
					String reg = (" |:");
					String[] tableau = chaine2.split(reg);
					String inter = tableau[2];
					// printListOfIPs();
					if (listMachine.contains(inter)
							&& !(listOfIPs.contains(inter))) {
						listOfIPs.add(inter);
						fichier.write(ligne);
						fichier.newLine();
					} else {

					}
				}

			}
			br.close();
			fichier.close();
			updateFile();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void printConfigFile(String file) {
		try {
			String ligne;
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

	public void buildMachineList() {
		this.listMachine.clear();
		for (Machine mach : this.wrapper.getList()) {
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
		buildMachineList(); //copie les ip seulement (eligibles)
		buildList(); //regarde le fichier et ne conserve que les IP qui sont eligibles
		readAndUpdate();

		// lance la tache periodique

		//On cr�e le timer
		Timer time = new Timer(); 

		//On instancie la routine de monitoring
		ConfigEditorTask cet = new ConfigEditorTask(this);

		//On programme la t�che 
		time.schedule(cet, 30000, 10000); 

	}

	public static void main(String[] args) {

		/*List<Machine> wrap = new ArrayList<Machine>();
		// (String ip, int id, String name,boolean running,float cpu)
		Machine machine1 = new Machine("192.168.2.1", 1, "bijou", true,
				(float) 1.33);
		machine1.setEligible(true);

		Machine machine2 = new Machine("192.168.2.2", 1, "bijou2", true,
				(float) 1.33);
		machine2.setEligible(true);
		Machine machine3 = new Machine("192.168.2.3", 1, "bijou3", true,
				(float) 1.33);
		Machine machine4 = new Machine("192.168.2.4", 1, "bijou4", false,
				(float) 1.33); // à supprimer
		machine4.setEligible(false);
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

		ConfigEditor edit1 = new ConfigEditor(wrap);
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
		 * dit1.addIP("192.168.2.7");
		 * System.out.println(" -- Fichier en sortie --");
		 * edit1.printConfigFile(edit1.configFile);
		 * ///edit1.removeIP("192.168.2.4");
		 * edit1.printConfigFile(edit1.configFile);


		System.out.println("Début liste machine");
		edit1.buildMachineList(wrap);
		edit1.printListmachine();
		System.out.println("Fin liste machine");

		System.out.println("Début liste IP");
		edit1.buildList();
		edit1.printListOfIPs();
		System.out.println("Fin liste IP");

		edit1.readAndUpdate(wrap);
		edit1.printConfigFile(edit1.configFile);
		 */

	}

}

