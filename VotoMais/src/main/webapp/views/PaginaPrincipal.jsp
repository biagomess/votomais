<%@ page import="java.util.List"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.Comparator"%>
<%@ page import="com.votomais.dao.CandidatoDAO"%> <!-- Alterado para CandidatoDAO -->
<%@ page import="com.votomais.dao.AvaliacaoDAO"%>
<%@ page import="com.votomais.model.Candidato"%> <!-- Alterado para Candidato -->
<%@ page import="com.votomais.model.Usuario"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Usuario usuarioLogado = (Usuario) (session != null ? session.getAttribute("usuarioLogado") : null);

    CandidatoDAO candidatoDAO = new CandidatoDAO();
    AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
    List<Candidato> listaCandidatos = null;

    try {
        listaCandidatos = candidatoDAO.consultarTodosCandidatos();

        if (listaCandidatos != null) {
            for (Candidato candidato : listaCandidatos) {
                double mediaAvaliacoes = avaliacaoDAO.obterMediaAvaliacoesPorCandidato(candidato.getIdCandidato());
                candidato.setMediaAvaliacoes(mediaAvaliacoes);
            }

            Collections.sort(listaCandidatos, new Comparator<Candidato>() {
                @Override
                public int compare(Candidato c1, Candidato c2) {
                    return Double.compare(c2.getMediaAvaliacoes(), c1.getMediaAvaliacoes());
                }
            });
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Voto+ - Página Principal</title>
        <!-- Fonte Poppins -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <!-- CSS Customizado -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/PaginaPrincipal.css">
    </head>
    <body>
        <div class="container">
            <!-- Cabeçalho Fixo -->
            <header class="cabecalho">
                <div class="logo-container">
                    <img src="${pageContext.request.contextPath}/imagens/ProjetoVoto+.png" alt="Voto+" class="logo">
                    <span class="titulo-logo">Voto+</span>
                </div>
                <div class="botoes-container">
                    <% if (usuarioLogado != null) {%>
                    <span class="nome-usuario">Olá, <%= usuarioLogado.getNome()%>!</span>
                    <% if (usuarioLogado.getNivelAcesso() == Usuario.NivelAcesso.ADMIN) { %>
                    <a href="CadastroCandidato.jsp" class="botao botao-primario">Gestão de Prefeitos</a>
                    <a href="CadastroVice.jsp" class="botao botao-primario">Gestão de Vices</a>
                    <a href="associarCandidatoVice.jsp" class="botao botao-primario">Associar candidatos</a>
                    <% } %>
                    <form action="Logout.jsp" method="post" class="d-inline">
                        <button class="botao botao-secundario">Logout</button>
                    </form>
                    <% } else { %>
                    <a href="Login.jsp" class="botao botao-primario">Login</a>
                    <a href="CadastroUsuario.jsp" class="botao botao-secundario">Cadastro</a>
                    <% } %>
                </div>
            </header>

            <!-- Conteúdo Principal -->
            <main class="conteudo-principal">
                <div class="container">
                    <h2 class="titulo-candidatos text-center mb-4">Acredite e Avalie: Sua Opinião Conta</h2>

                    <div class="lista-candidatos">
                        <% if (listaCandidatos != null && !listaCandidatos.isEmpty()) {
                                for (Candidato candidato : listaCandidatos) {%> <!-- Alterado para Candidato -->
                        <div class="cartao">
                            <div class="card-body">
                                <h5 class="cartao-titulo"><%= candidato.getNomeCandidato()%></h5>
                                <p class="cartao-texto"><strong>Idade:</strong> <%= candidato.getIdadeCandidato()%></p>
                                <p class="cartao-texto"><strong>Cargo:</strong> <%= candidato.getCargoPoliticoCandidato()%></p>
                                <p class="cartao-texto"><strong>Partido:</strong> <%= candidato.getPartidoCandidato()%></p>

                                <!-- Verifica se o idVice não é null ou 0, e exibe o nome do vice -->
                                <% if (candidato.getIdVice() != null && candidato.getIdVice() != 0) { %>
                                <p class="cartao-texto"><strong>Vice:</strong> <%= candidato.getNomeVice()%></p>
                                <% } else { %>
                                <p class="cartao-texto"><strong>Vice:</strong> Nenhum vice associado</p>
                                <% } %>
                                <p class="cartao-texto"><strong>Média de Avaliações:</strong> <%= String.format("%.2f", candidato.getMediaAvaliacoes())%></p>
                                <a href="DetalhesCandidato.jsp?id=<%= candidato.getIdCandidato()%>" class="botao botao-primario w-100">Ver Detalhes</a>
                            </div>
                        </div>
                        <% }
                        } else { %>
                        <div class="col-12">
                            <p class="text-center">Nenhum candidato encontrado.</p>
                        </div>
                        <% }%>
                    </div>
                </div>
            </main>
        </div>
    </body>
</html>
