package com.mycompany.projeto_java;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private ConexaoBanco conexaoBanco = new ConexaoBanco();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Aluno aluno = new Aluno(conexaoBanco);
        boolean loginSuccessful = aluno.login(username, password);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (loginSuccessful) {
            out.println("<html><body>");
            out.println("<h1>Login successful!</h1>");
            out.println("</body></html>");
        } else {
            out.println("<html><body>");
            out.println("<h1>Login failed!</h1>");
            out.println("</body></html>");
        }
    }
}
