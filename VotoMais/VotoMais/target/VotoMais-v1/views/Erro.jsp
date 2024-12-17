<%-- 
    Document   : erro
    Created on : 05/05/2023, 21:54:45
    Author     : alunos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% String msg = (String) request.getAttribute("message"); %>
        <h1>Erro na Operação: <%= msg != null ? msg : "Mensagem de erro não disponível." %></h1>
        <button class="home-button" onclick="window.location.href = '<%= request.getContextPath() %>/PaginaInicial.jsp'">Início</button>
    </body>
</html>
