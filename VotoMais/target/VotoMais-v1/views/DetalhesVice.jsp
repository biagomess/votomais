<%@page import="java.sql.SQLException"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.votomais.dao.ViceDAO"%>
<%@page import="com.votomais.model.Vice"%>

<%
    // Verifica a sessão existente
    HttpSession sessionUsuario = request.getSession();
    String idViceStr = request.getParameter("id");
    int idVice = -1;
    try {
        idVice = Integer.parseInt(idViceStr);
    } catch (NumberFormatException e) {
        out.println("<p class='alert alert-danger'>ID inválido. Não foi possível recuperar os detalhes.</p>");
    }

    Vice vice = null;
    if (idVice != -1) {
        ViceDAO viceDAO = new ViceDAO();
        try {
            vice = viceDAO.consultarVicePorId(idVice);
        } catch (SQLException | ClassNotFoundException e) {
            out.println("<p class='alert alert-danger'>Erro ao consultar os dados do vice. Tente novamente mais tarde.</p>");
        }
    }

    if (vice != null) {
%>

<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Detalhes do Vice</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">   
        <!-- Font-Family Poppins -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">
        <!-- CSS Customizado -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/DetalhesVice.css">
    </head>
    <body>
        <div class="container mt-3">

            <!-- Imagem do Vice -->
            <div class="text-center mb-3">
                <img src="${pageContext.request.contextPath}/imagens/ProjetoViceVoto+.png" alt="Foto do Vice" class="img-fluid">
            </div>

            <!-- Detalhes do Vice -->
            <div class="card mb-3 shadow-sm">
                <div class="card-body">
                    <h5 class="card-title text-center">Informações do Vice</h5>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><strong>Nº de Registro:</strong> <%= vice.getNmrRegistroVice()%></li>
                        <li class="list-group-item"><strong>Nome:</strong> <%= vice.getNomeVice()%></li>
                        <li class="list-group-item"><strong>Idade:</strong> <%= vice.getIdadeVice()%></li>
                        <li class="list-group-item"><strong>Cargo Político:</strong> <%= vice.getCargoPoliticoVice()%></li>
                        <li class="list-group-item"><strong>Partido:</strong> <%= vice.getPartidoVice()%></li>
                        <li class="list-group-item"><strong>Histórico:</strong> <%= vice.getHistoricoVice()%></li>
                    </ul>
                </div>
            </div>

            <!-- Botão Voltar -->
            <div class="text-center mt-4">
                <a href="<%= request.getContextPath()%>/views/CadastroVice.jsp" class="btn btn-primary w-100">Voltar</a>
            </div>
        </div>
    </body>
</html>

<% } else { %>
<p class="alert alert-danger">Vice não encontrado.</p>
<% }%>
