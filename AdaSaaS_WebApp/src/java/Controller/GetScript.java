/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gb
 */
public class GetScript extends HttpServlet {

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
            System.out.println("Sending the client script");
            /* TODO output your page here. You may use following sample code. */
            out.println("function draw_side(recur, dist) {");
            out.println("  if (recur==1) {");
            out.println("    turtle_forward(dist);");
            out.println("  }");
            out.println("  else {");
            out.println("    recur = recur - 1;");
            out.println("    dist = dist / 3;");
            out.println("    draw_side(recur,dist);");
            out.println("    turtle_left(60);");
            out.println("    draw_side(recur,dist);");
            out.println("    turtle_right(120);");
            out.println("    draw_side(recur,dist);");
            out.println("    turtle_left(60);");
            out.println("    draw_side(recur,dist);");
            out.println("  }");
            out.println("};");
            out.println("init(600,500);");
            out.println("turtle_goto(10,60);");
            out.println("var dist = 200;");
            out.println("for (i=0; i<3; i++) {");
            out.println("  draw_side(1,dist);");
            out.println("  turtle_right(120);");
            out.println("}");
            out.println("turtle_goto(375,60);");
            out.println("for (i=0; i<3; i++) {");
            out.println("  draw_side(2,dist);");
            out.println("  turtle_right(120);");
            out.println("}");
            out.println("turtle_goto(10,310);");
            out.println("for (i=0; i<3; i++) {");
            out.println("  draw_side(3,dist);");
            out.println("  turtle_right(120);");
            out.println("}");
            out.println("turtle_goto(375,310);");
            out.println("for (i=0; i<3; i++) {");
            out.println("  draw_side(4,dist);");
            out.println("  turtle_right(120);");
            out.println("}");

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
