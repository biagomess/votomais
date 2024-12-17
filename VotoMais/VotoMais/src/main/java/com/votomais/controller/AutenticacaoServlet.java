package com.votomais.controller;

import com.votomais.dao.UsuarioDAO;
import com.votomais.model.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Controller_Autenticacao", urlPatterns = {"/Controller_Autenticacao"})
public class AutenticacaoServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {

            String email = request.getParameter("txtemail");
            String senha = request.getParameter("txtsenha");

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = usuarioDAO.autenticar(email, senha);

            if (usuario != null) {

                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", usuario);
                response.sendRedirect(request.getContextPath() + "/views/PaginaInicial.jsp");
            } else {

                request.setAttribute("mensagem", "Usuário ou senha inválidos. Tente novamente.");
                request.getRequestDispatcher(request.getContextPath() + "/views/Login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("mensagem", "Erro ao acessar o banco de dados. Por favor, tente mais tarde.");
            request.getRequestDispatcher(request.getContextPath() + "/views/Login.jsp").forward(request, response);
        } catch (ClassNotFoundException e) {
            request.setAttribute("mensagem", "Erro no sistema. Classe não encontrada: " + e.getMessage());
            request.getRequestDispatcher(request.getContextPath() + "/views/Login.jsp").forward(request, response);
        } catch (IOException e) {
            request.setAttribute("mensagem", "Erro de entrada/saída: " + e.getMessage());
            request.getRequestDispatcher(request.getContextPath() + "/views/Login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    public String getServletInfo() {
        return "Servlet de autenticação de usuários.";
    }
}
