/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author gb
 */
public class ConsoleHelper implements ConsoleHelperImpl {

    private String clientID;

    @Override
    public boolean create_client_folder(String clientID) {
        this.clientID = clientID;
        File dir = new File(clientID);
        boolean created = dir.mkdir();
        System.out.println(dir.getAbsolutePath());

        return created;
    }

    @Override
    public boolean delete_client_folder() {
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
    public boolean save_client_file(String fileName, ArrayList<String> lines) {
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
    public List<String> get_client_file(String fileName) {
        List<String> result = null;
        try {
            result = Files.readAllLines(Paths.get(clientID + "/" + fileName));
        } catch (IOException ex) {
            System.out.println("ConsoleHelper : Error retrieving client's file " + ex.getMessage());
        }
        return result;
    }

    @Override
    public ArrayList<String> execute_program(String programName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> get_turtle_script(String scriptName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}