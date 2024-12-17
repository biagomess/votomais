<%@ page import="java.util.List" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.votomais.model.Avaliacao" %>
<%@ page import="com.votomais.dao.AvaliacaoDAO, com.votomais.dao.CandidatoDAO" %>
<%@ page import="com.votomais.model.Usuario, com.votomais.model.Candidato" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Obtendo o parâmetro ID do candidato
    String idParam = request.getParameter("id");
    Candidato candidato = null;

    // Obtendo a sessão do usuário logado
    HttpSession httpSession = request.getSession(false);
    Usuario usuarioLogado = (httpSession != null) ? (Usuario) httpSession.getAttribute("usuarioLogado") : null;

    // Consultando o candidato se o ID for fornecido
    if (idParam != null && !idParam.isEmpty()) {
        try {
            int id = Integer.parseInt(idParam);
            CandidatoDAO candidatoDAO = new CandidatoDAO();
            candidato = candidatoDAO.consultarById(id);
        } catch (Exception e) {
            out.println("<p>Erro ao consultar candidato: " + e.getMessage() + "</p>");
        }
    } else {
        out.println("<p>ID do candidato não fornecido.</p>");
    }

    // Obtendo avaliações do candidato
    AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
    List<Avaliacao> avaliacoes = null;
    double mediaEstrelas = 0.0;
    if (candidato != null) {
        try {
            avaliacoes = avaliacaoDAO.obterAvaliacoesPorCandidato(candidato.getId());
            mediaEstrelas = avaliacaoDAO.obterMediaAvaliacoesPorCandidato(candidato.getId());
        } catch (Exception e) {
            out.println("<p>Erro ao obter avaliações: " + e.getMessage() + "</p>");
        }
    }

    // Consultando vice-candidato se existir
    Candidato viceCandidato = null;
    if (candidato != null && candidato.getVice_id() > 0) {
        try {
            viceCandidato = new CandidatoDAO().consultarById(candidato.getVice_id());
        } catch (Exception e) {
            out.println("<p>Erro ao consultar vice-candidato: " + e.getMessage() + "</p>");
        }
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Perfil do Candidato</title>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Bootstrap Icons -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

        <!-- CSS -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/PerfilCandidato.css">
    </head>
    <body>
        <header class="d-flex justify-content-between align-items-center bg-primary text-white p-3">
            <button class="btn btn-light" onclick="window.location.href = 'PaginaInicial.jsp'">Início</button>
            <h1>Voto+</h1>
            <div>
                <% if (usuarioLogado != null) {%>
                <span><%= usuarioLogado.getNome()%></span>
                <form action="Logout.jsp" method="post" class="d-inline">
                    <button class="btn btn-secondary">Logout</button>
                </form>
                <% } else { %>
                <a href="Login.jsp" class="btn btn-secondary">Login</a>
                <a href="CadastroUsuario.jsp" class="btn btn-secondary">Cadastre-se</a>
                <% } %>
            </div>
        </header>

        <div class="perfil mt-4">
            <% if (candidato != null) {%>
            <h2><%= candidato.getNome()%></h2>
            <p><strong>Idade:</strong> <%= candidato.getIdade()%></p>
            <p><strong>Cargo:</strong> <%= candidato.getCargoPolitico()%></p>
            <p><strong>Partido:</strong> <%= candidato.getPartido()%></p>
            <p><strong>Histórico:</strong> <%= candidato.getHistorico()%></p>
            <% if (candidato.getFoto() != null && !candidato.getFoto().isEmpty()) {%>
            <img src="imagens/<%= candidato.getFoto()%>" alt="Foto de <%= candidato.getNome()%>" class="foto">
            <% } else { %>
            <p>Foto não disponível.</p>
            <% }%>

            <h3>Avaliações</h3>
            <p>Média de estrelas: <%= mediaEstrelas > 0 ? String.format("%.1f", mediaEstrelas) : "Nenhuma avaliação disponível"%></p>

            <% if (usuarioLogado != null) {%>
            <form action="controller_avaliacao" method="post">
                <input type="hidden" name="txtcandidatoId" value="<%= candidato.getId()%>">
                <input type="hidden" name="txtusuarioId" value="<%= usuarioLogado.getId()%>">
                <div class="mb-3">
                    <label>Estrelas:</label>
                    <input type="number" name="txtestrelas" max="5" min="1" required>
                </div>
                <div class="mb-3">
                    <label>Comentário:</label>
                    <textarea name="txtcomentario" placeholder="Deixe um comentário sobre o candidato" required></textarea>
                </div>
                <button type="submit" name="btnoperacao" value="CADASTRAR" class="btn btn-primary mt-2">Enviar Avaliação</button>
            </form>
            <% } else { %>
            <p>Para avaliar, faça login.</p>
            <% } %>
            <% } else { %>
            <p>Candidato não encontrado.</p>
            <% }%>
        </div>
    </body>
</html>
