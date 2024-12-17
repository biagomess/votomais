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

@WebServlet(name = "controller_avaliacao", urlPatterns = {"/controller_avaliacao"})
public class AvaliacaoServlet extends HttpServlet {

    private AvaliacaoDAO avaliacaoDAO;

    @Override
    public void init() throws ServletException {
        avaliacaoDAO = new AvaliacaoDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op = request.getParameter("btnoperacao");

        try {
            switch (op) {
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
                    request.setAttribute("message", "Operação inválida.");
                    request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
                    break;
            }
        } catch (SQLException | NumberFormatException e) {
            request.setAttribute("message", "Erro: " + e.getMessage());
            request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvaliacaoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cadastrarAvaliacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setCandidatoId(Integer.parseInt(request.getParameter("txtcandidatoId")));

        String usuarioIdParam = request.getParameter("txtusuarioId");
        if (usuarioIdParam == null || usuarioIdParam.isEmpty()) {
            request.setAttribute("message", "ID de usuário não pode ser nulo ou vazio. A avaliação não pode ser registrada.");
            request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
            return;
        }

        int usuarioId = Integer.parseInt(usuarioIdParam);
        if (usuarioId <= 0) {
            request.setAttribute("message", "ID de usuário inválido. A avaliação não pode ser registrada.");
            request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
            return;
        }

        avaliacao.setUsuarioId(usuarioId);
        avaliacao.setEstrelas(Integer.parseInt(request.getParameter("txtestrelas")));
        avaliacao.setComentario(request.getParameter("txtcomentario"));

        avaliacaoDAO.cadastrar(avaliacao);
        double media = avaliacaoDAO.obterMediaAvaliacoesPorCandidato(avaliacao.getCandidatoId());
        request.setAttribute("mediaEstrelas", media);
        request.getRequestDispatcher(request.getContextPath() + "/views/PerfilCandidato.jsp?id=" + avaliacao.getCandidatoId()).forward(request, response);
    }

    private void editarAvaliacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(Integer.parseInt(request.getParameter("txtid")));
        avaliacao.setEstrelas(Integer.parseInt(request.getParameter("txtestrelas")));
        avaliacao.setComentario(request.getParameter("txtcomentario"));

        avaliacaoDAO.atualizar(avaliacao);
        response.sendRedirect(request.getContextPath() + "/views/EditarCandidato.jsp?message=Avaliação editada com sucesso!");
    }

    private void deletarAvaliacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        int id = Integer.parseInt(request.getParameter("txtid"));
        avaliacaoDAO.deletar(id);
        response.sendRedirect(request.getContextPath() + "/views/DeletadoComSucesso.jsp?message=Avaliação deletada com sucesso!");
    }

    private void consultarAvaliacoes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        int candidatoId = Integer.parseInt(request.getParameter("txtcandidatoId"));
        List<Avaliacao> avaliacoes = avaliacaoDAO.obterAvaliacoesPorCandidato(candidatoId);
        request.setAttribute("avaliacoes", avaliacoes);

        double media = avaliacaoDAO.obterMediaAvaliacoesPorCandidato(candidatoId);
        request.setAttribute("mediaEstrelas", media);
        request.getRequestDispatcher(request.getContextPath() + "/views/PerfilCandidato.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response); // Redireciona o GET para o POST
    }

    @Override
    public String getServletInfo() {
        return "Controle de Avaliações";
    }
}
