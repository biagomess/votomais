<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lista de Vices</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">   
        <!-- Font-Family Poppins -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">
        <!-- CSS Customizado -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ListaVice.css">
                <style>
            /* Diminuir o tamanho da fonte da tabela inteira */
            .table td, .table th {
                font-size: 0.70rem; /* Ajuste conforme necessário */
            }
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <h2 class="text-center mb-4">Lista de Vices</h2>

            <!-- Tabela de resultados -->
            <c:if test="${not empty vice}">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover">
                        <thead class="table-dark text-center">
                            <tr>
                                <th class="table-primary">ID</th>
                                <th>Nº de Registro</th>
                                <th>Nome</th>
                                <th>Idade</th>
                                <th>Cargo Político</th>
                                <th>Partido</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="vice" items="${vice}">
                                <tr>
                                    <td class="table-primary text-center">${vice.idVice}</td>
                                    <td>${vice.nmrRegistroVice}</td>
                                    <td>${vice.nomeVice}</td>
                                    <td>${vice.idadeVice}</td>
                                    <td>${vice.cargoPoliticoVice}</td>
                                    <td>${vice.partidoVice}</td>
                                    <td class="text-center">
                                        <!-- Editar -->
                                        <a href="${pageContext.request.contextPath}/ViceController?btnoperacao=CONSULTAR_VICE&txtid=${vice.idVice}" class="btn btn-warning btn-sm me-1">Editar</a>

                                        <!-- Deletar -->
                                        <form action="${pageContext.request.contextPath}/ViceController" method="POST" style="display:inline;">
                                            <input type="hidden" name="btnoperacao" value="DELETAR_VICE">
                                            <input type="hidden" name="txtid" value="${vice.idVice}">
                                            <button type="submit" class="btn btn-danger btn-sm me-1" onclick="return confirm('Tem certeza que deseja deletar este vice?')">Deletar</button>
                                        </form>

                                        <!-- Ver Detalhes -->
                                        <a href="${pageContext.request.contextPath}/views/DetalhesVice.jsp?id=${vice.idVice}" class="btn btn-info btn-sm">Ver Detalhes</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <!-- Mensagem de aviso -->
            <c:if test="${empty vice}">
                <p class="alert alert-warning text-center">Nenhum vice encontrado.</p>
            </c:if>

            <!-- Botão de voltar -->
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/views/CadastroVice.jsp" class="btn btn-primary">Voltar</a>
            <!-- Adicionando o botão "Associar candidatos" -->
                <a href="${pageContext.request.contextPath}/views/associarCandidatoVice.jsp" class="botao botao-primario btn btn-primary mt-3">Associar candidatos</a>
            </div>
        </div>
    </body>
</html>
