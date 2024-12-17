package com.votomais.controller;

import com.votomais.dao.AvaliacaoDAO;
import com.votomais.model.Avaliacao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet AvaliacaoController - Gerencia operações relacionadas às avaliações.
 */
@WebServlet(name = "AvaliacaoController", urlPatterns = {"/AvaliacaoController"})
public class AvaliacaoController extends HttpServlet {

    private AvaliacaoDAO avaliacaoDAO; // Objeto DAO para manipulação de avaliações

    // Método init para inicializar o DAO
    @Override
    public void init() throws ServletException {
        avaliacaoDAO = new AvaliacaoDAO();
    }

    /**
     * Método principal que processa as requisições POST.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtém a operação solicitada
        String operacao = request.getParameter("btnoperacao");

        try {
            // Verifica a operação e chama o método correspondente
            switch (operacao.toUpperCase()) {
                case "CADASTRAR":
                    cadastrarAvaliacao(request, response);
                    break;
                case "EDITAR":
                    editarAvaliacao(request, response);
                    break;
                case "DELETAR":
                    deletarAvaliacao(request, response);
                    break;
                case "CONSULTAR":
                    consultarAvaliacoes(request, response);
                    break;
                default:
                    setErrorMessage(request, "Operação inválida.");
                    forwardToErrorPage(request, response);
                    break;
            }
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AvaliacaoController.class.getName()).log(Level.SEVERE, "Erro no processamento", e);
            setErrorMessage(request, "Erro: " + e.getMessage());
            forwardToErrorPage(request, response);
        }
    }

    // Método para cadastrar uma nova avaliação
    private void cadastrarAvaliacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        // Obtém os parâmetros do formulário
        int candidatoId = Integer.parseInt(request.getParameter("txtcandidatoId"));
        int usuarioId = Integer.parseInt(request.getParameter("txtusuarioId"));
        String nomeUsuario = request.getParameter("txtnomeUsuario");
        int estrelas = Integer.parseInt(request.getParameter("txtestrelas"));
        String comentario = request.getParameter("txtcomentario");

        // Verifica se o usuário já avaliou esse candidato
        Avaliacao avaliacaoExistente = avaliacaoDAO.obterAvaliacaoPorUsuarioECandidato(usuarioId, candidatoId);
        if (avaliacaoExistente != null) {
            // Se já existe uma avaliação, exibe mensagem de erro e não realiza o cadastro
            setErrorMessage(request, "Você já avaliou este candidato.");
            forwardToErrorPage(request, response);
            return;
        }

        // Cria um novo objeto Avaliacao
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setCandidatoId(candidatoId);
        avaliacao.setUsuarioId(usuarioId);
        avaliacao.setNomeUsuario(nomeUsuario);
        avaliacao.setEstrelas(estrelas);
        avaliacao.setComentario(comentario);

        // Cadastra a avaliação no banco de dados
        avaliacaoDAO.cadastrar(avaliacao);

        // Obtém a média de avaliações para o candidato e adiciona como atributo
        double media = avaliacaoDAO.obterMediaAvaliacoesPorCandidato(candidatoId);
        request.setAttribute("mediaEstrelas", media);

        // Redireciona para a página de detalhes do candidato, agora com as avaliações atualizadas
        response.sendRedirect(request.getContextPath() + "/views/DetalhesCandidato.jsp?id=" + candidatoId);
    }

    // Método para editar uma avaliação existente
    private void editarAvaliacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        // Cria um objeto Avaliacao e preenche com os dados do formulário
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(Integer.parseInt(request.getParameter("txtid")));
        avaliacao.setEstrelas(Integer.parseInt(request.getParameter("txtestrelas")));
        avaliacao.setComentario(request.getParameter("txtcomentario"));

        // Atualiza a avaliação no banco de dados
        avaliacaoDAO.atualizar(avaliacao);

        // Redireciona para a página de edição com mensagem de sucesso
        response.sendRedirect(request.getContextPath() + "/views/EditarCandidato.jsp?message=Avaliação editada com sucesso!");
    }

    // Método para deletar uma avaliação pelo ID
    private void deletarAvaliacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        // Obtém o ID da avaliação a ser deletada
        int id = Integer.parseInt(request.getParameter("txtid"));

        // Deleta a avaliação no banco de dados
        avaliacaoDAO.deletar(id);

        // Redireciona para a página de sucesso com mensagem
        response.sendRedirect(request.getContextPath() + "/views/DeletadoComSucesso.jsp?message=Avaliação deletada com sucesso!");
    }

    // Método para consultar as avaliações de um candidato
    private void consultarAvaliacoes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        // Obtém o ID do candidato
        int candidatoId = Integer.parseInt(request.getParameter("txtcandidatoId"));

        // Obtém a lista de avaliações para o candidato
        List<Avaliacao> avaliacoes = avaliacaoDAO.obterAvaliacoesPorCandidato(candidatoId);

        // Adiciona as avaliações e a média de estrelas como atributos na request
        request.setAttribute("avaliacoes", avaliacoes);

        double media = avaliacaoDAO.obterMediaAvaliacoesPorCandidato(candidatoId);
        request.setAttribute("mediaEstrelas", media);

        // Redireciona para a página de perfil do candidato
        request.getRequestDispatcher("/views/PerfilCandidato.jsp").forward(request, response);
    }

    // Método para definir uma mensagem de erro na request
    private void setErrorMessage(HttpServletRequest request, String message) {
        request.setAttribute("message", message);
    }

    // Método para redirecionar para a página de erro
    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
    }
}
