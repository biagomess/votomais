<%@page import="com.votomais.model.Candidato"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consulta de Candidato</title>
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
                max-width: 600px;
                margin: auto;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            }
            h1 {
                color: #0072cc;
                text-align: center;
            }
            p {
                color: #666;
            }
            .botao-voltar {
                margin-top: 20px;
                background-color: #0072cc;
                color: white;
                padding: 10px;
                border: none;
                cursor: pointer;
                border-radius: 5px;
                transition: background-color 0.3s;
                display: block;
                text-align: center;
                width: 100%;
            }
            .botao-voltar:hover {
                background-color: #005999;
            }
            table {
                border-collapse: collapse;
                width: 100%;
                margin-top: 20px;
            }
            th, td {
                border: 1px solid #0072cc;
                padding: 10px;
                text-align: center;
            }
            th {
                background-color: #0072cc;
                color: white;
            }
            tr:hover {
                background-color: rgba(0, 114, 204, 0.1);
            }
        </style>
    </head>
    <body>
        <div class="perfil">
            <h1>Resultado da Consulta por ID do Candidato</h1>
            <button class="home-button" onclick="window.location.href = '<%= request.getContextPath() %>/views/PaginaInicial.jsp'">Início</button>
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
                    <% Candidato objCandidato = (Candidato) request.getAttribute("candidato"); %>
                    <% if (objCandidato != null) { %>
                    <tr>
                        <td><% out.println(objCandidato.getId()); %></td>
                        <td><% out.println(objCandidato.getNome()); %></td>
                        <td><% out.println(objCandidato.getIdade()); %></td>
                        <td><% out.println(objCandidato.getCargoPolitico()); %></td>
                        <td><% out.println(objCandidato.getPartido()); %></td>
                        <td><% out.println(objCandidato.getHistorico()); %></td>
                    </tr>
                    <% } else { %>
                    <tr>
                        <td colspan="6"><p>Candidato não encontrado, tabela vazia!</p></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <button class="botao-voltar" onclick="window.history.back();">Voltar</button>
        </div>
    </body>
</html>
