package com.votomais.controller;

import com.votomais.dao.AvaliacaoDAO;
import com.votomais.dao.CandidatoDAO;
import com.votomais.model.Avaliacao;
import com.votomais.model.Candidato;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

@MultipartConfig
@WebServlet(name = "controller_candidato", urlPatterns = {"/controller_candidato"})
public class CandidatoServlet extends HttpServlet {

    private String UPLOAD_DIR;

    @Override
    public void init() throws ServletException {
        UPLOAD_DIR = getServletContext().getRealPath("/imagens");

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String op = request.getParameter("btnoperacao");
            String message = "Operação não realizada";
            CandidatoDAO cdao = new CandidatoDAO();
            AvaliacaoDAO adao = new AvaliacaoDAO();

            try {
                switch (op) {
                    case "CADASTRAR":
                        cadastrarCandidato(request, response, cdao);
                        return;
                    case "DELETAR":
                        deletarCandidato(request, response, cdao);
                        return;
                    case "Atualizar Candidato":
                        atualizarCandidatoForm(request, response, cdao);
                        return;
                    case "Atualizar":
                        atualizarCandidato(request, response, cdao);
                        return;
                    case "Consultar por ID":
                        consultarPorId(request, response, cdao);
                        return;
                    case "Consultar Todos":
                        consultarTodos(request, response, cdao);
                        return;
                    case "Adicionar Avaliação":
                        adicionarAvaliacao(request, response, adao);
                        return;
                    case "Agregar vice":
                        agregarVice(request, response, cdao);
                        return;
                    default:
                        message = "Operação inválida!";
                        forwardToErrorPage(message, request, response);
                        return;
                }
            } catch (IOException | ClassNotFoundException | SQLException | ServletException ex) {
                handleException(ex, request, response);
            }

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Controle Candidato</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Candidato: " + message + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void cadastrarCandidato(HttpServletRequest request, HttpServletResponse response, CandidatoDAO cdao) throws IOException, ServletException, ClassNotFoundException, SQLException {
        Candidato c = new Candidato();
        String message;

        c.setNome(request.getParameter("txtnome"));
        String idadeParam = request.getParameter("txtidade");
        if (isEmpty(idadeParam)) {
            message = "A idade não pode estar vazia.";
            forwardToErrorPage(message, request, response);
            return;
        }
        c.setIdade(Integer.parseInt(idadeParam));
        c.setCargoPolitico(request.getParameter("txtcargoPolitico"));
        c.setPartido(request.getParameter("txtpartido"));
        c.setHistorico(request.getParameter("txthistorico"));

        Part filePart = request.getPart("txtfoto");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            String filePath = UPLOAD_DIR + File.separator + fileName;
            filePart.write(filePath);
            c.setFoto(fileName);
        } else {
            message = "Por favor, selecione uma foto para upload.";
            forwardToErrorPage(message, request, response);
            return;
        }

        cdao.cadastrar(c);
        message = "Candidato cadastrado com sucesso!!";
        response.sendRedirect(request.getContextPath() + "/views/PaginaInicial.jsp?message=" + message);
    }

    private void deletarCandidato(HttpServletRequest request, HttpServletResponse response, CandidatoDAO cdao) throws IOException, ServletException, ClassNotFoundException, SQLException {
        String idParam = request.getParameter("txtid");
        if (isEmpty(idParam)) {
            String message = "O ID não pode estar vazio para a operação de deleção.";
            forwardToErrorPage(message, request, response);
            return;
        }
        int id = Integer.parseInt(idParam);
        cdao.deletar(id);
        String message = "Deletado com sucesso!!";
        response.sendRedirect(request.getContextPath() + "/views/PaginaInicial.jsp?message=" + message);
    }

    private void atualizarCandidatoForm(HttpServletRequest request, HttpServletResponse response, CandidatoDAO cdao) throws IOException, ServletException, ClassNotFoundException, SQLException {
        String idParam = request.getParameter("txtid");
        if (isEmpty(idParam)) {
            String message = "O ID não pode estar vazio para a operação de atualização.";
            forwardToErrorPage(message, request, response);
            return;
        }

        int id = Integer.parseInt(idParam);
        Candidato candidato = cdao.consultarById(id);
        request.setAttribute("candidato", candidato);
        request.getRequestDispatcher("/views/EditarCandidato.jsp").forward(request, response);
    }

    private void atualizarCandidato(HttpServletRequest request, HttpServletResponse response, CandidatoDAO cdao) throws IOException, ServletException, ClassNotFoundException, SQLException {
        String idParam = request.getParameter("txtid");
        if (isEmpty(idParam)) {
            String message = "O ID não pode estar vazio para a operação de atualização.";
            forwardToErrorPage(message, request, response);
            return;
        }

        Candidato c = new Candidato();
        c.setId(Integer.parseInt(idParam));
        c.setNome(request.getParameter("txtnome"));
        c.setIdade(Integer.parseInt(request.getParameter("txtidade")));
        c.setCargoPolitico(request.getParameter("txtcargoPolitico"));
        c.setPartido(request.getParameter("txtpartido"));
        c.setHistorico(request.getParameter("txthistorico"));

        cdao.update(c);
        String message = "Candidato atualizado com sucesso!";
        request.setAttribute("message", message);
        request.setAttribute("candidato", c);
        request.getRequestDispatcher("/views/AtualizadoComSucesso.jsp").forward(request, response);
    }

    private void consultarPorId(HttpServletRequest request, HttpServletResponse response, CandidatoDAO cdao) throws IOException, ServletException, ClassNotFoundException, SQLException {
        String idParam = request.getParameter("txtid");
        if (isEmpty(idParam)) {
            String message = "O ID não pode estar vazio para a consulta.";
            forwardToErrorPage(message, request, response);
            return;
        }

        int id = Integer.parseInt(idParam);
        Candidato candidato = cdao.consultarById(id);
        request.setAttribute("candidato", candidato);
        request.getRequestDispatcher("/views/ConsultaCandidatoPorId.jsp").forward(request, response);
    }

    private void consultarTodos(HttpServletRequest request, HttpServletResponse response, CandidatoDAO cdao) throws IOException, ServletException, ClassNotFoundException, SQLException {
        List<Candidato> listCandidatos = cdao.consultarTodos();
        request.setAttribute("listCandidatos", listCandidatos);
        request.getRequestDispatcher("/views/VisualizarCandidatos.jsp").forward(request, response);
    }

    private void adicionarAvaliacao(HttpServletRequest request, HttpServletResponse response, AvaliacaoDAO adao) throws IOException, ServletException, ClassNotFoundException, SQLException {
        Avaliacao avaliacao = new Avaliacao();
        String candidatoIdParam = request.getParameter("txtcandidatoId");
        String usuarioIdParam = request.getParameter("txtusuarioId");
        String estrelasParam = request.getParameter("txtestrelas");

        if (isEmpty(candidatoIdParam) || isEmpty(usuarioIdParam) || isEmpty(estrelasParam)) {
            String message = "Candidato, usuário ou estrelas não podem estar vazios.";
            forwardToErrorPage(message, request, response);
            return;
        }

        avaliacao.setCandidatoId(Integer.parseInt(candidatoIdParam));
        avaliacao.setUsuarioId(Integer.parseInt(usuarioIdParam));
        avaliacao.setEstrelas(Integer.parseInt(estrelasParam));
        avaliacao.setComentario(request.getParameter("txtcomentario"));

        adao.cadastrar(avaliacao);
        String message = "Avaliação adicionada com sucesso!";
        response.sendRedirect(request.getContextPath() + "/views/PerfilCandidato.jsp?message=" + message);
    }

    private void agregarVice(HttpServletRequest request, HttpServletResponse response, CandidatoDAO cdao) throws IOException, ServletException, ClassNotFoundException, SQLException {
        String candidatoIdParam = request.getParameter("txtcandidatoId");
        String viceIdParam = request.getParameter("txtviceId");

        if (isEmpty(candidatoIdParam) || isEmpty(viceIdParam)) {
            String message = "Os IDs do candidato e do vice não podem estar vazios.";
            forwardToErrorPage(message, request, response);
            return;
        }

        int candidatoId = Integer.parseInt(candidatoIdParam);
        int viceId = Integer.parseInt(viceIdParam);

        cdao.agregarVice(candidatoId, viceId);

        String message = "Vice agregado com sucesso!";
        response.sendRedirect(request.getContextPath() + "/views/PerfilCandidato.jsp?message=" + message);
    }

    private void forwardToErrorPage(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("/views/Erro.jsp").forward(request, response);
    }

    private void handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = "Ocorreu um erro: " + ex.getMessage();
        forwardToErrorPage(message, request, response);
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
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
        return "Servlet de controle de candidatos";
    }
}
