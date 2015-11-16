/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import System.ConsoleHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gb
 */
public class CompileAndSaveServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //first we read the file to compile from the request
            boolean save = true;
            ArrayList<String> jb = new ArrayList<String>();
            String line = null;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    jb.add(line);
                }
            } catch (Exception e) {
                System.out.println("Save_Text.java : Error " + e.getMessage() + " in doPost");
                save = false;
            }

            if (save) {
                HttpSession s = request.getSession();
                System.out.print("saving and compiling client's programm");
                ConsoleHelper sh = (ConsoleHelper) s.getAttribute("consoleHelper");
                if (sh == null) {
                    out.println("ERROR : consoleHelper does not exist in session.");
                } else {
                    //delete everything except the adb file
                    String command = "ls -1";
                    List<String> lines = sh.execute_program(command);
                    if (!lines.isEmpty()) {
                        command = "rm ";
                        for (String l : lines) {
                            command += l + " ";
                        }
                        lines = sh.execute_program(command);
                    }

                    //Save and compile the program into a file
                    if (sh.save_client_file("test_ada.adb", jb)) {
                        out.println("File saved");

                        //compile the program
                        command = "gnatmake -aI../ada_package/ test_ada.adb -aO../ada_package/-o test_ada";
                        lines = sh.execute_program(command);
                        if (lines.isEmpty()) {
                            out.println("No output from gnatmake.");
                        }
                        //print the output to the client
                        for (String l : lines) {
                            System.out.println(l);
                            out.println(l);
                        }
                    } else {
                        out.println("Could not save file.");
                    }
                }
            } else {
                out.println("Did not find anything to compile.");
            }
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
