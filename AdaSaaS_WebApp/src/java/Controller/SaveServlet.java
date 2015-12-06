/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import System.Cmds;
import System.ConsoleHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gb
 */
public class SaveServlet extends HttpServlet {

    protected String retrieveMainProcedureName(String fileLine) {
        String fileName = null;
        Pattern p = Pattern.compile("procedure (.+) is");
        Matcher m;

        System.out.println(fileLine);
        m = p.matcher(fileLine);
        if (m.find()) {
            fileName = m.group(1);
        }

        return fileName;
    }

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
        System.out.println("SaveServlet");
        try (PrintWriter out = response.getWriter()) {

            //first we read the file to compile from the request
            boolean save = true;
            ArrayList<String> jb = new ArrayList<String>();
            String line = null;
            String fileName = null;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    jb.add(line);
                    //Check if the line contains the fileName;
                    if (fileName == null) {
                        fileName = retrieveMainProcedureName(line);
                    }
                }
            } catch (Exception e) {
                System.out.println("Save_Text.java : Error " + e.getMessage() + " in doPost");
                save = false;
            }

            if (fileName == null) {
                fileName = "test_adb";
            }
            System.out.println("SaveServlet : File name is : " + fileName);

            if (save) {
                HttpSession s = request.getSession();
                System.out.print("saving and compiling client's programm");
                ConsoleHelper sh = (ConsoleHelper) s.getAttribute("consoleHelper");
                if (sh == null) {
                    out.println("ERROR : consoleHelper does not exist in session.");
                } else {
                    //delete everything except the adb file
                    List<String> lines = sh.executeProgram(Cmds.cmdListDir());
                    if (!lines.isEmpty()) {
                        sh.executeProgram(Cmds.cmdRemoveFilesInDir(lines));
                    }

                    //Save the program into a file
                    if (sh.saveClientFile(fileName + ".adb", jb)) {
                        out.println("File saved");
                                                
                        sh.setClientProgramName(fileName);
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
