<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Associar Vice-Candidato</title>
    </head>
    <body>
        <h1>Associar Vice a um Candidato</h1>

        <!-- Exibir mensagens de status -->
        <%
            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
        <div style="color: red;"><%= message %></div>
        <%
            }
        %>

        <form action="<%= request.getContextPath() %>/controller_candidato" method="post">
            <input type="hidden" name="btnoperacao" value="Associar Vice" />

            <label for="candidatoId">ID do Candidato:</label>
            <input type="text" id="candidatoId" name="txtcandidatoId" required /><br/><br/>

            <label for="viceId">ID do Vice-Candidato:</label>
            <input type="text" id="viceId" name="txtviceId" required /><br/><br/>

            <input type="submit" value="Agregar Vice" />
        </form>

        <br/>
        <a href="<%= request.getContextPath() %>/view/PaginaInicial.jsp">Voltar para a página principal</a>

    </body>
</html>
