/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.UserDao;
import dto.UserDTO;
import dto.UserError;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author nguye
 */
@WebServlet(name="RegisterController", urlPatterns={"/RegisterController"})
public class RegisterController extends HttpServlet {
   private static final String REGISTER_PAGE = "Register.jsp";
   private static final String SUCCESS = "index.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
      
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = REGISTER_PAGE;
        UserError userError = new UserError();
        UserDao dao = new UserDao();
        
        try {
        String email = request.getParameter("email");
        String fullname = request.getParameter("fullname");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");
        boolean check = true;
       
        if(fullname.length()<5 || fullname.length()>30){
            userError.setFullnameError("Fullname in range 5-30");
            check=false;
        }
        if(password.length()<5 || password.length() > 30){
            userError.setPasswordError("Password in range 5-30");
            check=false;
        }
        if(!password.equalsIgnoreCase(confirm)){
            userError.setConfirmError("Passwords do not match");
            check=false;
        }
       
       
        if(check){
           boolean checkInsert= dao.insertUser(fullname, email, password);
            if(checkInsert){
                url=SUCCESS;
            }else{
                userError.setError("Unkown user");
                request.setAttribute("USER_ERROR", userError);
            }
        }else{
            request.setAttribute("USER_ERROR",userError);
        }
    
        } catch (Exception e) {
             log("Error at LoginController" + e.getMessage());
             if(e.toString().contains("duplicate")){
                 userError.setEmailError("Duplice email");
                 request.setAttribute("USER_ERROR", userError);
             }
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
