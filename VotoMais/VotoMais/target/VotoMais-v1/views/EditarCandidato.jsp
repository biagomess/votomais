<%@page import="com.votomais.model.Candidato"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atualização do Candidato</title>
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
            h2 {
                color: #0072cc;
                text-align: center;
            }
            h3 {
                color: #0072cc;
            }
            form input[type=text], form input[type=number] {
                width: 100%;
                height: 25px;
                border: 1px solid #0072cc;
                padding-left: 10px;
                margin: 10px 0;
                border-radius: 5px;
            }
            form input[type=submit] {
                width: 100%;
                height: 40px;
                cursor: pointer;
                background: #0072cc;
                color: white;
                border: 0;
                border-radius: 5px;
                transition: background-color 0.3s;
            }
            form input[type=submit]:hover {
                background-color: #005999;
            }
            .mensagem {
                text-align: center;
                color: #ff0000;
            }
        </style>
    </head>
    <body>
        <div class="perfil">
            <form action="<%= request.getContextPath() %>/controller_candidato" method="POST">           
                <% 
                    Candidato objCandidato = (Candidato) request.getAttribute("candidato");
                    String message = (String) request.getAttribute("message");
                %>
                <% if (objCandidato != null) { %>
                <h2>Atualize suas informações</h2><br>

                <input type="hidden" name="txtid" value="<%= objCandidato.getId() %>"> 
                <h3><b>ID selecionado: <%= objCandidato.getId() %><br><br></b></h3>

                Nome: <input type="text" name="txtnome" value="<%= objCandidato.getNome() %>"><br>
                Idade: <input type="number" name="txtidade" value="<%= objCandidato.getIdade() %>"><br>
                Cargo Político: <input type="text" name="txtcargoPolitico" value="<%= objCandidato.getCargoPolitico() %>"><br>
                Partido: <input type="text" name="txtpartido" value="<%= objCandidato.getPartido() %>"><br>
                Histórico: <input type="text" name="txthistorico" value="<%= objCandidato.getHistorico() %>"><br>
                Vice ID: <input type="number" name="txtviceId" value="<%= objCandidato.getVice_id() %>"><br><br>

                <input type="submit" name="btnoperacao" value="Atualizar"><br>
                <% } else { %>
                <p class="mensagem">Candidato não encontrado!</p>
                <% } %>

                <% if (message != null) { %>
                <p class="mensagem"><%= message %></p>
                <% } %>        
            </form>
        </div>
    </body>
</html>
