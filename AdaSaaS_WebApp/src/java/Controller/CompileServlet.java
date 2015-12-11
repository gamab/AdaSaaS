/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import System.Cmds;
import System.ConsoleHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
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

    protected ArrayList<String> changeLibraryPath(List<String> outputFromGnat, ServletContext ctx) {
        ArrayList<String> outputChanged = new ArrayList<>();
        String pathToDelete = ctx.getRealPath("/WEB-INF/ada_package/");
        Pattern p = Pattern.compile("(.*)" + pathToDelete + "(.*)");
        Matcher m;
        Boolean found;

        for (String line : outputFromGnat) {
            found = true;
            //while we find the pattern in the line we replace it
            while (found) {
                m = p.matcher(line);
                if (m.find()) {
                    found = true;
                    line = m.group(1) + "ada_package" + m.group(2);
                }
                else {
                    found = false;
                }
                System.out.println("Changed line :" + line);
            }
            outputChanged.add(line);
        }
    return outputChanged ;
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
                //compile the program
                List<String> lines = sh.executeProgram(Cmds.cmdCompileAdbFileWEBINF(fileName, s.getServletContext()));
                lines = changeLibraryPath(lines, s.getServletContext());
                if (lines.isEmpty()) {
                    out.println("No output from gnatmake.");
                } else {
                    //print the output to the client
                    for (String l : lines) {
                        System.out.println(l);
                        out.println(l);
                    }
                }
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
