/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import java.util.ArrayList;

/**
 *
 * @author gb
 */
public interface Console_Helper {
    public boolean create_client_folder(String clientID);
    
    public boolean save_client_file(String fileName);
    
    public ArrayList<String> execute_program(String programName);
    
    public ArrayList<String> get_turtle_script(String scriptName);
}
