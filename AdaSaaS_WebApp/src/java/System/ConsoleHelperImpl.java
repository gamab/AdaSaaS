/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gb
 */
public interface ConsoleHelperImpl {
    public boolean createClientFolder(String clientID);
    
    public boolean deleteClientFolder();
    
    public boolean saveClientFile(String fileName, ArrayList<String> lines);
    
    public List<String> getClientFile(String fileName);
    
    public ArrayList<String> executeProgram(String programName);
    
    /*public ArrayList<String> executeProgramThread(String programName);*/
    
    public List<String> getTurtleScript(String scriptName);
}
