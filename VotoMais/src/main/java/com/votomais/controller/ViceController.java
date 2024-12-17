package com.votomais.controller;

import com.votomais.dao.ViceDAO;
import com.votomais.model.Conexao;
import com.votomais.model.Vice;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet ViceController - Gerencia as operações relacionadas aos vices.
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
@WebServlet(name = "ViceController", urlPatterns = {"/ViceController"})
public class ViceController extends HttpServlet {

    private final ViceDAO viceDAO = new ViceDAO();
    private String uploadPath;

    /**
     * Configura o caminho para upload de arquivos e cria o diretório, se
     * necessário.
     */
    @Override
    public void init() throws ServletException {
        uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    /**
     * Método principal que processa as requisições HTTP (GET ou POST).
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operacao = request.getParameter("btnoperacao");

        try {
            switch (operacao) {
                case "CADASTRAR_VICE":
                    cadastrarVice(request, response);
                    break;
                case "ATUALIZAR_VICE":
                    atualizarVice(request, response);
                    break;
                case "DELETAR_VICE":
                    deletarVice(request, response);
                    break;
                case "CONSULTAR_VICE":
                    consultarVicePorId(request, response);
                    break;
                case "CONSULTAR_TODOS_VICES":
                    consultarTodosVices(request, response);
                    break;
                case "PREENCHER_FORMULARIO":
                    preencherFormularioAtualizacao(request, response);
                    break;
                default:
                    request.setAttribute("errorMessage", "Operação inválida!");
                    request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
                    break;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erro: " + e.getMessage());
            request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
        }
    }

    /**
     * Cadastra um novo vice no banco de dados.
     */
    private void cadastrarVice(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ClassNotFoundException, ServletException, IOException {
        Vice vice = new Vice();

        // Verifica o campo NmrRegistroVice (obrigatório)
        String nmrRegistroViceStr = request.getParameter("nmrRegistroVice");

        if (nmrRegistroViceStr == null || nmrRegistroViceStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "O campo NmrRegistroVice é obrigatório.");
            request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
            return; // Para a execução se não for fornecido
        }

        try {
            // Converte o valor de String para int
            int nmrRegistroVice = Integer.parseInt(nmrRegistroViceStr);
            vice.setNmrRegistroVice(nmrRegistroVice); // Atribui o NmrRegistroVice
        } catch (NumberFormatException e) {
            // Caso o valor fornecido não seja um número válido
            request.setAttribute("errorMessage", "O campo NmrRegistroVice deve ser um número válido.");
            request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
            return; // Interrompe a execução se não for válido
        }
        // Verifica e atribui o nome
        String nome = request.getParameter("txtnome");
        if (nome == null || nome.trim().isEmpty()) {
            request.setAttribute("errorMessage", "O nome do vice é obrigatório.");
            request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
            return;
        }
        vice.setNomeVice(nome); // Atribui o nome

        // Verifica e define a idade (caso não fornecida, atribui valor 0 ou outra lógica)
        String idadeStr = request.getParameter("txtidade");
        if (idadeStr != null && !idadeStr.trim().isEmpty()) {
            try {
                vice.setIdadeVice(Integer.parseInt(idadeStr));
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Idade fornecida não é válida.");
                request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
                return;
            }
        } else {
            vice.setIdadeVice(0); // Define um valor padrão, caso não fornecido
        }

        // Verifica e define o cargo político
        String cargo = request.getParameter("txtcargoPolitico");
        if (cargo == null || cargo.trim().isEmpty()) {
            cargo = "Vice-Prefeito"; // Cargo padrão
        }
        vice.setCargoPoliticoVice(cargo);

        // Define os outros parâmetros
        vice.setPartidoVice(request.getParameter("txtpartido"));
        vice.setHistoricoVice(request.getParameter("txthistorico"));
        vice.setFotoVice(request.getParameter("txtfoto"));

        // Salva o vice no banco de dados
        try {
            viceDAO.salvarVice(vice);
            request.setAttribute("message", "Vice cadastrado com sucesso!");
            request.getRequestDispatcher("/views/Success.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            request.setAttribute("errorMessage", "Erro ao cadastrar o vice: " + e.getMessage());
            request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
        }
    }

    /**
     * Atualiza os dados de um vice no banco de dados.
     */
    private void atualizarVice(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ClassNotFoundException, ServletException, IOException {
        Vice vice = new Vice();

        // Verifica e converte o ID
        String idStr = request.getParameter("txtid");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                vice.setIdVice(Integer.parseInt(idStr));
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "ID inválido.");
                request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
                return;
            }
        }

        String nmrRegistroViceStr = request.getParameter("nmrRegistroVice");

        if (nmrRegistroViceStr == null || nmrRegistroViceStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "O campo NmrRegistroVice é obrigatório.");
            request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
            return; // Para a execução se não for fornecido
        }

        try {
            // Converte o valor de String para int
            int nmrRegistroVice = Integer.parseInt(nmrRegistroViceStr);
            vice.setNmrRegistroVice(nmrRegistroVice); // Atribui o NmrRegistroVice
        } catch (NumberFormatException e) {
            // Caso o valor fornecido não seja um número válido
            request.setAttribute("errorMessage", "O campo NmrRegistroVice deve ser um número válido.");
            request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
            return; // Interrompe a execução se não for válido
        }
        
       
        vice.setNomeVice(request.getParameter("txtnome"));

        // Verifica a idade
        String idadeStr = request.getParameter("txtidade");
        if (idadeStr != null && !idadeStr.trim().isEmpty()) {
            try {
                vice.setIdadeVice(Integer.parseInt(idadeStr));
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Idade fornecida não é válida.");
                request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
                return;
            }
        }

        vice.setCargoPoliticoVice(request.getParameter("txtcargoPolitico"));
        vice.setPartidoVice(request.getParameter("txtpartido"));
        vice.setHistoricoVice(request.getParameter("txthistorico"));
        vice.setFotoVice(request.getParameter("txtfoto"));

        // Atualiza o vice no banco de dados
        viceDAO.atualizarVice(vice);

        request.setAttribute("message", "Vice atualizado com sucesso!");
        request.getRequestDispatcher("/views/Success.jsp").forward(request, response);
    }

    /**
     * Deleta um vice do banco de dados pelo ID.
     */
    private void deletarVice(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ClassNotFoundException, ServletException, IOException {
        String idStr = request.getParameter("txtid");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int idVice = Integer.parseInt(idStr);
                viceDAO.deletarVice(idVice);
                request.setAttribute("message", "Vice deletado com sucesso!");
                request.getRequestDispatcher("/views/CadastroVice.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "ID inválido.");
                request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
            }
        }
    }

    /**
     * Consulta um vice pelo ID.
     */
    private void consultarVicePorId(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ClassNotFoundException, ServletException, IOException {
        try {
            int idVice = Integer.parseInt(request.getParameter("txtid"));
            Vice vice = viceDAO.consultarVicePorId(idVice);

            if (vice != null) {
                request.setAttribute("vice", vice);
                request.getRequestDispatcher("/views/EditarVice.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Vice não encontrado para o ID informado.");
                request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Erro ao processar o ID. Certifique-se de que é um número válido.");
            request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
        }
    }

    /**
     * Consulta todos os vices.
     */
    private void consultarTodosVices(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ClassNotFoundException, ServletException, IOException {
        List<Vice> vice = viceDAO.consultarTodosVices();
        request.setAttribute("vice", vice);
        request.getRequestDispatcher("/views/ListaVice.jsp").forward(request, response);
    }

    private void preencherFormularioAtualizacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idVice = Integer.parseInt(request.getParameter("txtid"));
            Vice vice = viceDAO.consultarVicePorId(idVice);

            if (vice != null) {
                request.setAttribute("vice", vice);
            } else {
                request.setAttribute("message", "Vice não encontrado para o ID informado.");
            }
            request.getRequestDispatcher("/views/CadastroVice.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("message", "Erro ao processar o ID. Certifique-se de que é um número válido.");
            request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            request.setAttribute("message", "Erro ao buscar vice: " + e.getMessage());
            request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "ViceController Servlet para gerenciar Vices.";
    }

}
