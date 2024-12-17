<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lista de Candidatos</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">   
        <!-- Font-Family Poppins -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">
        <!-- CSS Customizado -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ListaCandidatos.css">
        <style>
            /* Diminuir o tamanho da fonte da tabela inteira */
            .table td, .table th {
                font-size: 0.70rem; /* Ajuste conforme necessário */
            }
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <h2 class="text-center mb-4">Lista de Candidatos</h2>

            <!-- Tabela de resultados -->
            <c:if test="${not empty candidatos}">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover">
                        <thead class="table-dark text-center">
                            <tr>
                                <th class="table-primary">ID</th>
                                <th>N° de Registro</th>
                                <th>Nome</th>
                                <th>Idade</th>
                                <th>Cargo Político</th>
                                <th>Partido</th>
                                <th>Vice Associado</th> <!-- Coluna para o Vice -->
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="candidato" items="${candidatos}">
                                <tr>
                                    <td class="table-primary text-center">${candidato.idCandidato}</td>
                                    <td>${candidato.nmrRegistro}</td>
                                    <td>${candidato.nomeCandidato}</td>
                                    <td>${candidato.idadeCandidato}</td>
                                    <td>${candidato.cargoPoliticoCandidato}</td>
                                    <td>${candidato.partidoCandidato}</td>

                                    <!-- Verificando se o candidato tem um vice associado -->
                                    <td>
                                        <c:if test="${not empty candidato.nomeVice}">
                                            <!-- Exibe o nome do vice se houver -->
                                            ${candidato.nomeVice}
                                        </c:if>
                                        <c:if test="${empty candidato.nomeVice}">
                                            Nenhum vice associado <!-- Exibe mensagem caso não tenha vice -->
                                        </c:if>
                                    </td>

                                    <td class="text-center">
                                        <!-- Editar -->
                                        <a href="${pageContext.request.contextPath}/CandidatoController?btnoperacao=CONSULTAR_CANDIDATO&txtid=${candidato.idCandidato}" class="btn btn-warning btn-sm me-1">Editar</a>

                                        <!-- Deletar -->
                                        <form action="${pageContext.request.contextPath}/CandidatoController" method="POST" style="display:inline;">
                                            <input type="hidden" name="btnoperacao" value="DELETAR_CANDIDATO">
                                            <input type="hidden" name="txtid" value="${candidato.idCandidato}">
                                            <button type="submit" class="btn btn-warning me-1" onclick="return confirm('Tem certeza que deseja deletar este candidato?')">Deletar</button>
                                        </form>

                                        <!-- Ver Detalhes -->
                                        <a href="${pageContext.request.contextPath}/views/DetalhesCandidato.jsp?id=${candidato.idCandidato}" class="btn btn-info btn-sm">Visualizar</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <!-- Mensagem de aviso -->
            <c:if test="${empty candidatos}">
                <p class="alert alert-warning text-center">Nenhum candidato encontrado.</p>
            </c:if>

            <!-- Botões -->
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/views/CadastroCandidato.jsp" class="btn btn-primary">Voltar</a>
                <!-- Adicionando o botão "Associar candidatos" -->
                <a href="${pageContext.request.contextPath}/views/associarCandidatoVice.jsp" class="botao botao-primario btn btn-primary mt-3">Associar candidatos</a>
            </div>
        </div>
    </body>
</html>
