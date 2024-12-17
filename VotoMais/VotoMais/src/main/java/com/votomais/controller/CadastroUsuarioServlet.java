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

@WebServlet(name = "Controller_Cadastro", urlPatterns = {"/Controller_Cadastro"})
public class CadastroUsuarioServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String op = request.getParameter("btnoperacao");

            if ("Cadastrar".equals(op)) {
                Usuario usuario = new Usuario();
                usuario.setNome(request.getParameter("txtnome"));
                usuario.setEmail(request.getParameter("txtemail"));
                usuario.setSenha(request.getParameter("txtsenha"));

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.cadastrar(usuario);

                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", usuario);
                response.sendRedirect(request.getContextPath() + "/views/PaginaInicial.jsp");
            }
        } catch (SQLException e) {
            request.setAttribute("mensagem", "Erro ao cadastrar o usuário: " + e.getMessage());
            request.getRequestDispatcher("/views/CadastroUsuario.jsp").forward(request, response);
        } catch (ClassNotFoundException e) {
            request.setAttribute("mensagem", "Erro de classe não encontrada: " + e.getMessage());
            request.getRequestDispatcher("/views/CadastroUsuario.jsp").forward(request, response);
        } catch (IOException e) {
            request.setAttribute("mensagem", "Erro de entrada/saída: " + e.getMessage());
            request.getRequestDispatcher("/views/CadastroUsuario.jsp").forward(request, response);
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
        return "Servlet para cadastro de usuários";
    }
}
