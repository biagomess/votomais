<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    // Obter a sessão atual
    HttpSession sessionlogout = request.getSession(); // Use apenas esta linha para obter a sessão
    if (sessionlogout != null) {
        sessionlogout.invalidate(); // Invalidar a sessão
    }
    response.sendRedirect(request.getContextPath() + "/views/PaginaInicial.jsp"); // Redirecionar para a página principal
%>
