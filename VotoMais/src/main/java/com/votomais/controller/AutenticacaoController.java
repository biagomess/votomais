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

/**
 * Controlador responsável pela autenticação de usuários.
 */
@WebServlet(name = "ControllerAutenticacao", urlPatterns = {"/ControllerAutenticacao"})
public class AutenticacaoController extends HttpServlet {

    /**
     * Processa as requisições POST enviadas pelo formulário de login.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            // Obtém os parâmetros do formulário
            String email = request.getParameter("txtemail");
            String senha = request.getParameter("txtsenha");

            // Instancia o DAO para verificar as credenciais
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = usuarioDAO.autenticar(email, senha);

            // Se as credenciais forem válidas, cria a sessão do usuário
            if (usuario != null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", usuario);

                // Redireciona para a página principal
                response.sendRedirect(request.getContextPath() + "/views/PaginaPrincipal.jsp");
            } else {
                // Caso as credenciais sejam inválidas, exibe uma mensagem de erro
                request.setAttribute("mensagem", "E-mail ou senha inválidos. Tente novamente.");
                request.getRequestDispatcher("/views/Login.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Em caso de erro, exibe uma mensagem genérica
            request.setAttribute("mensagem", "Erro ao autenticar. Por favor, tente novamente mais tarde.");
            request.getRequestDispatcher("/views/Login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet responsável pela autenticação de usuários.";
    }
}
