<%@ page import="java.util.List" %>
<%@ page import="com.votomais.model.Candidato" %>
<%@ page import="com.votomais.dao.CandidatoDAO" %>
<%@ page import="com.votomais.model.Usuario" %>

<%
    HttpSession httpSession = request.getSession(false);
    Usuario usuarioLogado = (Usuario) (httpSession != null ? httpSession.getAttribute("usuarioLogado") : null);

    CandidatoDAO candidatoDAO = new CandidatoDAO();
    List<Candidato> listaCandidatos = null;
    try {
        listaCandidatos = candidatoDAO.consultarTodos();
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

        <!-- FONT-FAMILY Poppins -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">

        <!-- CSS Customizado -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/PaginaInicial.css">
    </head>
    <body>
        <!-- Barra de Navegação -->
        <nav class="navbar">
            <div class="container">
                <a class="navbar-brand" href="PaginaPrincipal.jsp">
                    <img src="${pageContext.request.contextPath}/images/ProjetoVoto+.png" alt="Voto+" class="logo">
                    Voto+
                </a>
                <div class="navbar-nav">
                    <% if (usuarioLogado != null) {%>
                    <span class="navbar-text me-3">Bem-vindo <strong><%= usuarioLogado.getNome()%></strong>!</span>
                    <form action="Logout.jsp" method="post" class="d-inline">
                        <button class="btn btn-logout">Logout</button>
                    </form>
                    <select name="filtro" class="form-control">
                        <option value="partido">Por Partido</option>
                        <option value="melhores">Melhores Avaliados</option>
                        <option value="piores">Piores Avaliados</option>
                    </select>
                    <button type="submit" class="btn btn-primary ml-2" name="btnoperacao" value="Consultar Todos">Buscar</button>
                    <% if (usuarioLogado.getNivelAcesso() == Usuario.NivelAcesso.ADMIN) { %>
                    <a class="btn btn-secondary ms-2" href="CadastroCandidato.jsp">Cadastro de Candidatos</a>
                    <a class="btn btn-secondary ms-2" href="AssociarVice.jsp">Associar Vice-Candidato</a>
                    <% } %>
                    <% } else { %>
                    <a class="btn btn-login ms-2" href="Login.jsp">Login</a>
                    <a class="btn btn-cadastro ms-2" href="CadastroUsuario.jsp">Cadastre-se</a>
                    <% } %>
                </div>
            </div>
        </nav>

        <!-- Conteúdo Principal -->
        <main class="container mt-4">
            <h1 class="text-center">Avalie os Candidatos</h1>
            <p class="text-center text-muted">Explore a lista de candidatos e saiba mais sobre cada um.</p>

            <% if (listaCandidatos != null && !listaCandidatos.isEmpty()) { %>
            <div class="row">
                <% for (Candidato candidato : listaCandidatos) {%>
                <div class="col-md-4">
                    <div class="card mb-4 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">
                                <a href="PerfilCandidatoComVice?id=<%= candidato.getId()%>" class="text-decoration-none text-dark"><%= candidato.getNome()%></a>
                            </h5>
                            <p class="card-text">Idade: <%= candidato.getIdade()%></p>
                            <p class="card-text">Cargo Político: <%= candidato.getCargoPolitico()%></p>
                            <p class="card-text">Partido: <%= candidato.getPartido()%></p>
                            <p class="card-text">Vice-Candidato: <%= (candidato.getVice_id() > 0) ? "Candidato do vice (ID: " + candidato.getVice_id() + ")" : "Nenhum vice associado"%></p>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
            <% } else { %>
            <p class="text-center text-muted">Nenhum candidato encontrado.</p>
            <% }%>
        </main>

        <!-- Rodapé -->
        <footer class="footer mt-auto">
            <div class="container text-center">
                <p>© 2024 Voto+. Todos os direitos reservados.</p>
            </div>
        </footer>
    </body>
</html>
