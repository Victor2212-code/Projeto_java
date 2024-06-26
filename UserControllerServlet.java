package com.mycompany.projeto_java;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class UserControllerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userType = request.getParameter("userType");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean loginSuccessful = false;

        switch (userType) {
            case "0":
                loginSuccessful = new OperacoesAdministrador().login(username, password);
                break;
            case "1":
                loginSuccessful = new OperacoesSecretario().login(username, password);
                break;
            case "2":
                loginSuccessful = new OperacoesProfessor().login(username, password);
                break;
            case "3":
                loginSuccessful = new OperacoesDiretor().login(username, password);
                break;
            case "4":
                loginSuccessful = new OperacoesAluno().login(username, password);
                break;
        }

        if (loginSuccessful) {
            response.sendRedirect("result.html");
        } else {
            response.sendRedirect("error.html");
        }
    }
}
