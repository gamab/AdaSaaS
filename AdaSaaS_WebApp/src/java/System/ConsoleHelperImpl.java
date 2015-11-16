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
    public boolean create_client_folder(String clientID);
    
    public boolean delete_client_folder();
    
    public boolean save_client_file(String fileName, ArrayList<String> lines);
    
    public List<String> get_client_file(String fileName);
    
    public ArrayList<String> execute_program(String programName);
    
    public List<String> get_turtle_script(String scriptName);
}
