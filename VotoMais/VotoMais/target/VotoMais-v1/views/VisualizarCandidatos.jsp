<%@page import="com.votomais.model.Candidato"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Candidatos</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: linear-gradient(135deg, #0072cc, #fdfff5);
                padding: 20px;
            }
            .perfil {
                background: rgba(255, 255, 255, 0.9);
                border-radius: 8px;
                padding: 20px;
                max-width: 800px;
                margin: auto;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            }
            h1 {
                color: #0072cc;
                text-align: center;
            }
            table {
                border: 1px solid powderblue;
                border-collapse: collapse;
                width: 100%;
                margin-top: 20px;
            }
            th, td {
                padding: 12px;
                text-align: center;
                border: 1px solid #ddd;
            }
            th {
                background-color: #0072cc;
                color: white;
            }
            tr:hover {
                background-image: linear-gradient(black, paleturquoise);
            }
            p {
                color: #666;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div class="perfil">
            <h1>Todos os Candidatos</h1>
            <% List<Candidato> listCandidatos = (List<Candidato>) request.getAttribute("listCandidatos"); %>
            <% if (listCandidatos != null && !listCandidatos.isEmpty()) { %>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Idade</th>
                        <th>Cargo Político</th>
                        <th>Partido</th>
                        <th>Histórico</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Candidato c : listCandidatos) { %>
                    <tr>
                        <td><%= c.getId() %></td>
                        <td><%= c.getNome() %></td>
                        <td><%= c.getIdade() %></td>
                        <td><%= c.getCargoPolitico() %></td>
                        <td><%= c.getPartido() %></td>
                        <td><%= c.getHistorico() %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <% } else { %>
            <p>Consulta sem retorno.</p>
            <% } %>
        </div>
    </body>
</html>
