<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Associar Candidato e Vice</title>
        <!-- Fonte Poppins -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <!-- CSS Customizado -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AssociarVice.css">
    </head>
<body>

<h1>Associar Candidato e Vice</h1>

<!-- Exibindo mensagem de erro, se houver -->
<c:if test="${not empty error}">
    <div class="alert alert-danger" role="alert">
        ${error}
    </div>
</c:if>

<!-- Formulário de associação de candidato e vice -->
<form action="${pageContext.request.contextPath}/CandidatoViceController" method="POST">
    
    <!-- ID do Candidato -->
    <div>
        <label for="idCandidato">Numero do registro Candidato:</label>
        <input type="number" id="idCandidato" name="idCandidato" required />
    </div>

    <!-- ID do Vice -->
    <div>
        <label for="idVice">Numero do registro Vice:</label>
        <input type="number" id="idVice" name="idVice" required />
    </div>

    <div>
        <button type="submit" class="btn btn-primary">Associar</button>
    </div>
    
    <!-- Botão Voltar -->
    <div>
        <button type="button" class="voltar-btn btn btn-secondary" onclick="window.history.back();">Voltar</button>
    </div>
</form>

<!-- Consultar Todos os Candidatos -->
<form action="${pageContext.request.contextPath}/CandidatoController" method="GET">
    <input type="hidden" name="btnoperacao" value="CONSULTAR_TODOS_CANDIDATOS"/>
    <button type="submit" class="btn btn-secondary">Mostrar Candidatos</button>
</form>

<!-- Tabela de candidatos -->
<c:if test="${not empty candidatos}">
    <h3>Lista de Candidatos</h3>
    <table class="table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Idade</th>
                <th>Cargo</th>
                <th>Partido</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="candidato" items="${candidatos}">
                <tr>
                    <td>${candidato.idCandidato}</td>
                    <td>${candidato.nomeCandidato}</td>
                    <td>${candidato.idadeCandidato}</td>
                    <td>${candidato.cargoPoliticoCandidato}</td>
                    <td>${candidato.partidoCandidato}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<!-- Consultar Todos os Vices -->
<form action="${pageContext.request.contextPath}/ViceController" method="GET">
    <input type="hidden" name="btnoperacao" value="CONSULTAR_TODOS_VICES"/>
    <button type="submit" class="btn btn-warning">Mostrar Vices</button>
</form>

<!-- Tabela de vices -->
<c:if test="${not empty vices}">
    <h3>Lista de Vices</h3>
    <table class="table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Partido</th>
                <th>Cargo</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="vice" items="${vices}">
                <tr>
                    <td>${vice.idVice}</td>
                    <td>${vice.nomeVice}</td>
                    <td>${vice.partidoVice}</td>
                    <td>${vice.cargoVice}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/ViceController?btnoperacao=CONSULTAR_VICE&txtid=${vice.idVice}" class="btn btn-warning btn-sm me-1">Editar</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

</body>
</html>
