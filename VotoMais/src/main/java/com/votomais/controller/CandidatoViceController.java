package com.votomais.controller;

import com.votomais.dao.CandidatoDAO;
import com.votomais.dao.ViceDAO;
import com.votomais.model.Candidato;
import com.votomais.model.Vice;

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

@WebServlet(name = "CandidatoViceController", urlPatterns = {"/CandidatoViceController"})
public class CandidatoViceController extends HttpServlet {

    private CandidatoDAO candidatoDAO;
    private ViceDAO viceDAO;

    public CandidatoViceController() {
        this.candidatoDAO = new CandidatoDAO();
        this.viceDAO = new ViceDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtém o id do Candidato e do Vice a partir da requisição
            String idCandidatoString = request.getParameter("idCandidato");
            String idViceString = request.getParameter("idVice");

            // Converte os IDs para inteiros
            int idCandidato = Integer.parseInt(idCandidatoString);  // Aqui, converte de String para int
            int idVice = Integer.parseInt(idViceString);

            // Busca o candidato e o vice pelos IDs
            Candidato candidato = candidatoDAO.consultarCandidatoPorId(idCandidato);
            Vice vice = viceDAO.consultarVicePorId(idVice);

            if (candidato == null || vice == null) {
                request.setAttribute("error", "Candidato ou Vice não encontrados.");
                request.getRequestDispatcher("/views/Error.jsp").forward(request, response);
                return;
            }

            // Verifica se o candidato já tem um vice associado
            if (candidato.getIdVice() != null) {
                request.setAttribute("error", "Este candidato já tem um vice associado.");
                // Redireciona para a página de associação
                List<Candidato> candidatos = candidatoDAO.consultarTodosCandidatos();
                List<Vice> vices = viceDAO.consultarTodosVices();

                // Passa os candidatos e vices para o JSP
                request.setAttribute("candidatos", candidatos);
                request.setAttribute("vices", vices);

                // Redireciona para a página de associação com a mensagem de erro
                request.getRequestDispatcher("/views/associarCandidatoVice.jsp").forward(request, response);
                return;
            }

            // Chama o método para associar o Candidato ao Vice com base nos IDs
            candidatoDAO.associarCandidatoVicePorID(idCandidato, idVice);

            // Mensagem de sucesso
            request.setAttribute("message", "Candidato e Vice associados com sucesso!");
            request.getRequestDispatcher("/views/Success.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Erro ao associar candidato e vice.");
            request.getRequestDispatcher("/views/Error.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // Caso os IDs não sejam válidos
            request.setAttribute("error", "Os IDs fornecidos são inválidos.");
            request.getRequestDispatcher("/views/Error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Carrega os candidatos e vices para exibir no formulário de associação
        try {
            List<Candidato> candidatos = candidatoDAO.consultarTodosCandidatos();
            List<Vice> vices = viceDAO.consultarTodosVices();

            // Passa os candidatos e vices para o JSP
            request.setAttribute("candidatos", candidatos);
            request.setAttribute("vices", vices);

            // Redireciona para o JSP
            request.getRequestDispatcher("/views/associarCandidatoVice.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            request.setAttribute("error", "Erro ao carregar dados.");
            request.getRequestDispatcher("/views/Error.jsp").forward(request, response);
        }
    }
}
