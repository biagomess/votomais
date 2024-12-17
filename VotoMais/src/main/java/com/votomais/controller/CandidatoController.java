package com.votomais.controller;

import com.votomais.dao.CandidatoDAO;
import com.votomais.dao.ViceDAO;
import com.votomais.model.Candidato;
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
 * Servlet CandidatoController - Gerencia as operações relacionadas a candidatos.
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
@WebServlet(name = "CandidatoController", urlPatterns = {"/CandidatoController"})
public class CandidatoController extends HttpServlet {

    private final CandidatoDAO candidatoDAO = new CandidatoDAO();
    private final ViceDAO viceDAO = new ViceDAO(); // Instanciando a DAO do Vice
    private String uploadPath;

    /**
     * Configura o caminho para upload de arquivos e cria o diretório, se necessário.
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
                case "CADASTRAR_CANDIDATO":
                    cadastrarCandidato(request, response);
                    break;
                case "ATUALIZAR_CANDIDATO":
                    atualizarCandidato(request, response);
                    break;
                case "DELETAR_CANDIDATO":
                    deletarCandidato(request, response);
                    break;
                case "CONSULTAR_CANDIDATO":
                    consultarCandidatoPorId(request, response);
                    break;
                case "CONSULTAR_TODOS_CANDIDATOS":
                    consultarTodosCandidatos(request, response);
                    break;
                case "LISTAR_VICES":
                    listarVices(request, response);
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
     * Lista todos os vices disponíveis para serem associados a um candidato.
     */
    private void listarVices(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException, ClassNotFoundException {
        // Obtém a lista de vices
        List<Vice> vice = viceDAO.consultarTodosVices();
        // Adiciona a lista de vices ao request, para ser acessada na JSP
        request.setAttribute("vice", vice);
        // Redireciona para a página de cadastro de candidato
        request.getRequestDispatcher("/views/CadastroCandidato.jsp").forward(request, response);
    }

    /**
     * Cadastra um novo candidato no banco de dados.
     */
    private void cadastrarCandidato(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, ClassNotFoundException, ServletException, IOException {
        Candidato candidato = new Candidato();
        candidato.setNmrRegistro(Integer.parseInt(request.getParameter("nmrRegistro")));
        candidato.setNomeCandidato(request.getParameter("txtnome"));
        candidato.setIdadeCandidato(Integer.parseInt(request.getParameter("txtidade")));
        candidato.setCargoPoliticoCandidato(request.getParameter("txtcargoPolitico"));
        candidato.setPartidoCandidato(request.getParameter("txtpartido"));
        candidato.setHistoricoCandidato(request.getParameter("txthistorico"));
        candidato.setFotoCandidato(request.getParameter("txtfoto"));

        // Aqui, estamos apenas cadastrando o candidato, sem vincular um vice no momento
         candidato.setIdVice(null);
          // O campo de vice será nulo por enquanto

        candidatoDAO.salvarCandidato(candidato);

        request.setAttribute("message", "Candidato cadastrado com sucesso!");
        request.getRequestDispatcher("/views/Success.jsp").forward(request, response);
    }

    /**
     * Atualiza os dados de um candidato no banco de dados.
     */
    private void atualizarCandidato(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, ClassNotFoundException, ServletException, IOException {
        Candidato candidato = new Candidato();
        candidato.setNmrRegistro(Integer.parseInt(request.getParameter("nmrRegistro")));
        candidato.setIdCandidato(Integer.parseInt(request.getParameter("txtid")));
        candidato.setNomeCandidato(request.getParameter("txtnome"));
        candidato.setIdadeCandidato(Integer.parseInt(request.getParameter("txtidade")));
        candidato.setCargoPoliticoCandidato(request.getParameter("txtcargoPolitico"));
        candidato.setPartidoCandidato(request.getParameter("txtpartido"));
        candidato.setHistoricoCandidato(request.getParameter("txthistorico"));
        candidato.setFotoCandidato(request.getParameter("txtfoto"));

        candidatoDAO.atualizarCandidato(candidato);

        request.setAttribute("message", "Candidato atualizado com sucesso!");
        request.getRequestDispatcher("/views/Success.jsp").forward(request, response);
    }

    /**
     * Deleta um candidato do banco de dados pelo ID.
     */
    private void deletarCandidato(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ClassNotFoundException, ServletException, IOException {
        int idCandidato = Integer.parseInt(request.getParameter("txtid"));
        candidatoDAO.deletarCandidato(idCandidato);

        request.setAttribute("message", "Candidato deletado com sucesso!");
        request.getRequestDispatcher("/views/CadastroCandidato.jsp").forward(request, response);
    }

    /**
     * Consulta um candidato pelo ID.
     */
private void consultarCandidatoPorId(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, ClassNotFoundException, ServletException, IOException {
    try {
        int idCandidato = Integer.parseInt(request.getParameter("txtid"));
        Candidato candidato = candidatoDAO.consultarCandidatoPorId(idCandidato);

        // Verifica se o candidato foi encontrado
        if (candidato != null) {
            // Verifica se o candidato possui um vice associado
            if (candidato.getIdVice() != null) {
                // Busca o nome do vice associando o idVice
                String sqlVice = "SELECT nome FROM vice WHERE idVice = ?";
                try (Connection conn = Conexao.getConexao(); 
                     PreparedStatement psVice = conn.prepareStatement(sqlVice)) {
                    psVice.setInt(1, candidato.getIdVice());
                    try (ResultSet rsVice = psVice.executeQuery()) {
                        if (rsVice.next()) {
                            candidato.setNomeVice(rsVice.getString("nome"));
                        }
                    }
                }
            }
            
            // Atribui o candidato à requisição e redireciona para a página de edição
            request.setAttribute("candidato", candidato);
            request.getRequestDispatcher("/views/EditarCandidato.jsp").forward(request, response);
        } else {
            // Caso não encontre o candidato
            request.setAttribute("message", "Candidato não encontrado para o ID informado.");
            request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
        }
    } catch (NumberFormatException e) {
        // Caso haja erro ao processar o ID
        request.setAttribute("message", "Erro ao processar o ID. Certifique-se de que é um número válido.");
        request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
    }
}


    /**
     * Consulta todos os candidatos.
     */
    private void consultarTodosCandidatos(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ClassNotFoundException, ServletException, IOException {
        List<Candidato> candidatos = candidatoDAO.consultarTodosCandidatos();
        request.setAttribute("candidatos", candidatos);
        request.getRequestDispatcher("/views/ListaCandidatos.jsp").forward(request, response);
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
        return "CandidatoController Servlet para gerenciar Candidatos.";
    }
}
