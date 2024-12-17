package com.votomais.controller;

import com.votomais.dao.UsuarioDAO;
import com.votomais.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ControllerCadastro", urlPatterns = {"/ControllerCadastro"})
public class CadastroUsuarioServlet extends HttpServlet {

    // Método principal que processa a requisição
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            // Obtém a operação solicitada a partir do botão do formulário
            String operacao = request.getParameter("btnoperacao");

            // Verifica se a operação é de cadastro
            if ("Cadastrar".equals(operacao)) {
                cadastrarUsuario(request, response); // Chama a função de cadastro de usuário
            }
        } catch (SQLException e) {
            // Tratamento de erro para falhas de banco de dados
            setErrorMessage(request, "Erro ao cadastrar o usuário: " + e.getMessage());
            forwardToCadastroPage(request, response); // Redireciona para a página de cadastro com erro
        } catch (ClassNotFoundException e) {
            // Tratamento de erro para classes não encontradas
            setErrorMessage(request, "Erro de classe não encontrada: " + e.getMessage());
            forwardToCadastroPage(request, response);
        } catch (IOException e) {
            // Tratamento de erro de entrada/saída
            setErrorMessage(request, "Erro de entrada/saída: " + e.getMessage());
            forwardToCadastroPage(request, response);
        }
    }

    // Método para cadastrar o usuário
    private void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        // Obtém os parâmetros do formulário (dados do usuário)
        String nome = request.getParameter("txtnome");
        String email = request.getParameter("txtemail");
        String senha = request.getParameter("txtsenha");

        // Cria um novo objeto Usuario e define seus atributos
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha); // Senha pode ser criptografada aqui, caso necessário

        // Instancia o DAO para manipulação do banco de dados
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Verifica se o e-mail já existe no banco de dados
        if (usuarioDAO.emailExistente(email)) {
            // Define mensagem de erro se o e-mail já está cadastrado e redireciona para a página de cadastro
            setErrorMessage(request, "E-mail já cadastrado.");
            forwardToCadastroPage(request, response);
            return;  // Interrompe o cadastro
        }

        // Cadastra o novo usuário no banco de dados
        usuarioDAO.cadastrar(usuario);

        // Cria uma nova sessão para o usuário recém-cadastrado e logado
        HttpSession session = request.getSession();
        session.setAttribute("usuarioLogado", usuario);

        // Redireciona o usuário para a página inicial após o cadastro
        response.sendRedirect(request.getContextPath() + "/views/PaginaPrincipal.jsp");
    }

    // Método para setar a mensagem de erro na request
    private void setErrorMessage(HttpServletRequest request, String message) {
        request.setAttribute("mensagem", message);  // Adiciona a mensagem de erro à request
    }

    // Método para redirecionar para a página de cadastro
    private void forwardToCadastroPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/CadastroUsuario.jsp").forward(request, response);
    }

    // Implementação do método doPost, processa requisições via POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // Implementação do método doGet, responde com erro para requisições GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);  // Responde com erro se a requisição for GET
    }

    // Retorna uma breve descrição sobre o servlet
    @Override
    public String getServletInfo() {
        return "Servlet para cadastro de usuários.";
    }
}
