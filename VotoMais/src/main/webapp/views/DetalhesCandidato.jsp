<%@page import="java.util.List"%>
<%@page import="com.votomais.model.Usuario"%>
<%@page import="java.sql.SQLException"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.votomais.dao.CandidatoDAO"%>
<%@page import="com.votomais.dao.AvaliacaoDAO"%>
<%@page import="com.votomais.model.Candidato"%>
<%@page import="com.votomais.model.Avaliacao"%>

<%
    // Verifica a sess�o existente
    HttpSession sessionUsuario = request.getSession();
    Usuario usuarioLogado = (Usuario) sessionUsuario.getAttribute("usuarioLogado");

    String idCandidatoStr = request.getParameter("id");
    int idCandidato = -1;
    try {
        idCandidato = Integer.parseInt(idCandidatoStr);
    } catch (NumberFormatException e) {
        out.println("<p class='alert alert-danger'>ID inv�lido. N�o foi poss�vel recuperar os detalhes.</p>");
    }

    Candidato candidato = null;
    if (idCandidato != -1) {
        CandidatoDAO candidatoDAO = new CandidatoDAO();
        try {
            candidato = candidatoDAO.consultarCandidatoPorId(idCandidato);
        } catch (SQLException | ClassNotFoundException e) {
            out.println("<p class='alert alert-danger'>Erro ao consultar os dados do candidato. Tente novamente mais tarde.</p>");
        }
    }

    // Consulta todas as avalia��es do candidato
    AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
    List<Avaliacao> todasAvaliacoes = null;
    double mediaAvaliacoes = 0;
    int totalAvaliacoes = 0;
    try {
        todasAvaliacoes = avaliacaoDAO.obterAvaliacoesPorCandidato(idCandidato);
        if (todasAvaliacoes != null && !todasAvaliacoes.isEmpty()) {
            // Calcula a m�dia das avalia��es
            for (Avaliacao a : todasAvaliacoes) {
                mediaAvaliacoes += a.getEstrelas();
            }
            mediaAvaliacoes /= todasAvaliacoes.size();
            totalAvaliacoes = todasAvaliacoes.size();
        }
    } catch (SQLException | ClassNotFoundException e) {
        out.println("<p class='alert alert-danger'>Erro ao consultar as avalia��es do candidato, n�o possui tabela.</p>");
    }

    // Verifica se o usu�rio j� avaliou o candidato
    boolean usuarioAvaliado = false;
    if (usuarioLogado != null) {
        try {
            Avaliacao avaliacaoExistente = avaliacaoDAO.obterAvaliacaoPorUsuarioECandidato(usuarioLogado.getId(), idCandidato);
            if (avaliacaoExistente != null) {
                usuarioAvaliado = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            out.println("<p class='alert alert-danger'>Erro ao verificar avalia��o anterior.</p>");
        }
    }

    if (candidato != null) {
%>

<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Detalhes do Candidato</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">   
        <!-- Font-Family Poppins -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">
        <!-- CSS Customizado -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/DetalhesCandidato.css">
    </head>
    <body>
        <div class="container mt-3">

            <!-- Imagem do Candidato -->
            <div class="text-center mb-3">
                <img src="${pageContext.request.contextPath}/imagens/ProjetoCandidatoVoto+.png" alt="Foto do Candidato" class="img-fluid">
            </div>

            <!-- Detalhes do Candidato -->
            <div class="card mb-3 shadow-sm">
                <div class="card-body">
                    <h5 class="card-title text-center">Informa��es do Candidato</h5>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><strong>N� de Registro:</strong> <%= candidato.getNmrRegistro()%></li>
                        <li class="list-group-item"><strong>Nome:</strong> <%= candidato.getNomeCandidato()%></li>
                        <li class="list-group-item"><strong>Idade:</strong> <%= candidato.getIdadeCandidato()%></li>
                        <li class="list-group-item"><strong>Cargo Pol�tico:</strong> <%= candidato.getCargoPoliticoCandidato()%></li>
                        <li class="list-group-item"><strong>Partido:</strong> <%= candidato.getPartidoCandidato()%></li>
                        <li class="list-group-item"><strong>Hist�rico:</strong> <%= candidato.getHistoricoCandidato()%></li>
                    </ul>
                </div>
            </div>

            <!-- Verifica��o e exibi��o do Vice -->
            <% if (candidato.getIdVice() != null && candidato.getNomeVice() != null && !candidato.getNomeVice().isEmpty()) {%>
            <div class="card mb-3 shadow-sm">
                <div class="card-body">
                    <div class="text-center mb-3">
                        <img src="${pageContext.request.contextPath}/imagens/ProjetoCandidatoVoto+.png" alt="Foto do Candidato" class="img-fluid">
                    </div>
                    <h5 class="card-title text-center">Informa��es do Vice-Candidato</h5>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><strong>N� de Registro:</strong> <%= candidato.getNmrRegistroVice()%></li>
                        <li class="list-group-item"><strong>Nome:</strong> <%= candidato.getNomeVice()%></li>
                        <li class="list-group-item"><strong>Idade:</strong> <%= candidato.getIdadeVice()%></li>
                        <li class="list-group-item"><strong>Cargo Pol�tico:</strong> <%= candidato.getCargoPoliticoVice()%></li>
                        <li class="list-group-item"><strong>Partido:</strong> <%= candidato.getPartidoVice()%></li>
                        <li class="list-group-item"><strong>Hist�rico:</strong> <%= candidato.getHistoricoVice()%></li>
                    </ul>
                </div>
            </div>
            <% } %>


            <!-- Avalia��o -->
            <div class="card mb-3 shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">Avalia��o</h5>
                    <!-- Exibe as notas dos outros usu�rios -->
                    <% if (todasAvaliacoes != null && !todasAvaliacoes.isEmpty()) {%>
                    <p><strong>M�dia das avalia��es: </strong><%= String.format("%.2f", mediaAvaliacoes)%> estrelas (Total de <%= totalAvaliacoes%> avalia��es)</p>
                    <ul class="list-group">
                        <% for (Avaliacao a : todasAvaliacoes) {%>
                        <li class="list-group-item">Nota: <%= a.getEstrelas()%> estrelas</li>
                        <li class="list-group-item"><strong>Coment�rio:</strong> <%= a.getComentario()%></li>
                            <% } %>
                    </ul>
                    <% } else { %>
                    <p class="text-muted">Ainda n�o h� avalia��es para este candidato.</p>
                    <% } %>
                </div>
            </div>

            <!-- Formul�rio de Avalia��o -->
            <% if (usuarioLogado != null && !usuarioAvaliado) {%>
            <div class="card mb-3 shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">Deixe sua Avalia��o</h5>
                    <form action="<%= request.getContextPath()%>/AvaliacaoController" method="post">
                        <input type="hidden" name="txtcandidatoId" value="<%= idCandidato%>">
                        <input type="hidden" name="txtusuarioId" value="<%= usuarioLogado.getId()%>">
                        <div class="mb-3">
                            <label class="form-label"><strong>Nota:</strong></label>
                            <div class="d-flex justify-content-between">
                                <input type="radio" name="txtestrelas" value="1"> 1
                                <input type="radio" name="txtestrelas" value="2"> 2
                                <input type="radio" name="txtestrelas" value="3"> 3
                                <input type="radio" name="txtestrelas" value="4"> 4
                                <input type="radio" name="txtestrelas" value="5"> 5
                            </div>
                        </div>
                        <!-- Coment�rio -->
                        <div class="mb-3">
                            <label for="comentario">Coment�rio:</label>
                            <textarea name="txtcomentario" id="comentario" rows="4" class="form-control" placeholder="Deixe um coment�rio sobre o candidato"></textarea>
                        </div>
                        <!-- Bot�o Avaliar -->
                        <button type="submit" name="btnoperacao" value="CADASTRAR" class="btn btn-primary w-100">Avaliar</button>
                    </form>
                    <% } else if (usuarioAvaliado) { %>
                    <!-- Mensagem de aviso -->
                    <p class="alert alert-info text-center">Voc� j� avaliou este candidato.</p>
                    <% } else { %>
                    <p>Fa�a login para poder avaliar este candidato.</p>
                    <% }%>

                    <!-- Bot�o Voltar -->
                    <div class="text-center mt-4">
                        <a href="<%= request.getContextPath()%>/views/PaginaPrincipal.jsp" class="btn btn-primary w-100">Voltar</a>
                    </div>
                </div>
                <% } else { %>
                <p class="alert alert-danger">Candidato n�o encontrado.</p>
                <% }%>
            </div>
    </body>
</html>
