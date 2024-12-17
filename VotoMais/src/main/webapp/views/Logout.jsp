<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Finaliza a sessão atual, caso exista
    if (session != null) {
        session.invalidate();
    }

    // Redireciona o usuário para a página principal ou de login
    response.sendRedirect("PaginaPrincipal.jsp");
%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Logout</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/EstiloBaseLogout.css">
    </head>
    <body>
        <div class="logout-container">
            <h1>Saindo...</h1>
            <p>Você está sendo redirecionado para a página principal.</p>
            <a href="PaginaPrincipal.jsp" class="btn btn-primary">Ir para a Página Principal</a>
        </div>
    </body>
</html>
