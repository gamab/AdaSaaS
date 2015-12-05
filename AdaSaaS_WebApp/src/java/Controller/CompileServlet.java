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
public class CompileServlet extends HttpServlet {

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
        System.out.println("CompileServlet");
        try (PrintWriter out = response.getWriter()) {

            HttpSession s = request.getSession();
            System.out.println("CompileServlet : Compiling client's programm");
            ConsoleHelper sh = (ConsoleHelper) s.getAttribute("consoleHelper");
            if (sh == null) {
                out.println("ERROR : consoleHelper does not exist in session.");
            } else {
                //delete everything except the adb file
                String fileName = sh.getClientProgramName();
                List<String> lines = sh.executeProgram(Cmds.cmdListDir());
                if (!lines.isEmpty()) {
                    sh.executeProgram(Cmds.cmdRemoveFilesInDirExceptAdb(lines,fileName));
                }

                //compile the program
                lines = sh.executeProgram(Cmds.cmdCompileAdbFileWEBINF(fileName,s.getServletContext()));
                if (lines.isEmpty()) {
                    out.println("No output from gnatmake.");
                }
                //print the output to the client
                for (String l : lines) {
                    System.out.println(l);
                    out.println(l);
                }
                sh.setClientProgramName(fileName);
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
        processRequest(request, response);
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
        //processRequest(request, response);
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
