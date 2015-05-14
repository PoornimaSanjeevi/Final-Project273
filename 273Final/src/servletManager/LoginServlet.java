package servletManager;

import servletManager.functions.CheckLogin;
import servletManager.model.UserModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Created by Spurthy on 5/4/2015.
 */
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("pwd");
        UserModel  userModel = CheckLogin.isValidLogin(userName, password);
        if(userModel!=null && userModel.getUserType().equalsIgnoreCase("user")){
            HttpSession session = request.getSession(true);
            session.setAttribute("userType", userName);
            System.out.println("In LoginServlet user type set");
            response.setContentType("text/html");
            response.sendRedirect("sendEmail.jsp"); //logged-in page
        } else  if(userModel!=null && userModel.getUserType().equalsIgnoreCase("admin")){
            HttpSession session = request.getSession(true);
            session.setAttribute("userType", userName);
            System.out.println("In LoginServlet user type set");
            response.setContentType("text/html");
            response.sendRedirect("sendEmail.jsp"); //logged-in page
        }
        else
            response.sendRedirect("invalidLogin.jsp"); //error page


    }


}

