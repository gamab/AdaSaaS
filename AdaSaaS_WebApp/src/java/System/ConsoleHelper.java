/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author gb
 */
public class ConsoleHelper implements ConsoleHelperImpl {

    private String clientID;
    
    private String clientProgramName;

    @Override
    public boolean createClientFolder(String clientID) {
        this.clientID = clientID;
        File dir = new File(clientID);
        boolean created = dir.mkdir();
        System.out.println(dir.getAbsolutePath());

        return created;
    }

    @Override
    public boolean deleteClientFolder() {
        File dir = new File(clientID);
        boolean delete = true;
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException ex) {
            System.out.println("ConsoleHelper : Error deleting the directory." + ex.getMessage());
            delete = false;
        }
        return delete;
    }

    @Override
    public boolean saveClientFile(String fileName, ArrayList<String> lines) {
        PrintWriter writer = null;
        boolean saved = true;
        try {
            writer = new PrintWriter(clientID + "/" + fileName, "UTF-8");
            for (String l : lines) {
                writer.println(l);
            }
            writer.close();
        } catch (FileNotFoundException ex) {
            System.out.println("ConsoleHelper : Error saving client's file " + ex.getMessage());
            saved = false;
        } catch (UnsupportedEncodingException ex) {
            System.out.println("ConsoleHelper : Error saving client's file " + ex.getMessage());
            saved = false;
        }
        if (writer != null) {
            writer.close();
        }

        return saved;
    }

    @Override
    public List<String> getClientFile(String fileName) {
        List<String> result = null;
        try {
            result = Files.readAllLines(Paths.get(clientID + "/" + fileName));
        } catch (IOException ex) {
            System.out.println("ConsoleHelper : Error retrieving client's file " + ex.getMessage());
        }
        return result;
    }

    @Override
    public ArrayList<String> executeProgram(String programName) {
        System.out.println("ConsoleHelper : executing programm " + programName);

        ArrayList<String> result = new ArrayList<>();
        File dir = new File(clientID);
        Process p;
        try {
            p = Runtime.getRuntime().exec(programName,null,dir);
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader reader_err
                    = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
            while ((line = reader_err.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException ex) {
            System.out.println("ConsoleHelper : Error executing programm " + programName + " " + ex.getMessage());
        }
        return result;
    }

    @Override
    public ArrayList<String> executeProgramThread(String programName) {
        System.out.println("ConsoleHelper : executing programm in a thread" + programName);

        ArrayList<String> result = new ArrayList<>();
        File dir = new File(clientID);
        Process p;
        try {
            p = Runtime.getRuntime().exec(programName,null,dir);
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader reader_err
                    = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
            while ((line = reader_err.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException ex) {
            System.out.println("ConsoleHelper : Error executing programm " + programName + " " + ex.getMessage());
        }
        return result;
    }

    @Override
    public List<String> getTurtleScript(String scriptName) {
        List<String> result = null;
        try {
            result = Files.readAllLines(Paths.get(clientID + "/" + scriptName));
        } catch (IOException ex) {
            System.out.println("ConsoleHelper : Error retrieving client's file " + ex.getMessage());
        }
        return result;
    }
    
    public void setClientProgramName(String programName) {
        this.clientProgramName = programName;
    }
    
    public String getClientProgramName() {
        return this.clientProgramName;
    }

}
