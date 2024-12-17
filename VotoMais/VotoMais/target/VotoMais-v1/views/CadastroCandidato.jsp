<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Bootstrap Icons -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

        <!-- CSS -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/CadastroCandidato.css">

        <title>Cadastro de Candidatos</title>
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="text-center">Cadastro de Candidatos</h1>
            <button class="btn btn-secondary my-3" onclick="window.location.href = '${pageContext.request.contextPath}/views/PaginaInicial.jsp'">Início</button>

            <!-- Exibe mensagem de erro, se houver -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" role="alert">${errorMessage}</div>
            </c:if>

            <form name="form1" action="${pageContext.request.contextPath}/controller_candidato" method="post" enctype="multipart/form-data">
                <div class="mb-3">
                    <input type="text" name="txtid" id="id" class="form-control" placeholder="Id" required>
                </div>
                <div class="mb-3">
                    <input type="text" name="txtnome" id="nome" class="form-control" placeholder="Nome do Candidato" required>
                </div>
                <div class="mb-3">
                    <input type="number" name="txtidade" id="idade" class="form-control" placeholder="Idade" required>
                </div>
                <div class="mb-3">
                    <input type="text" name="txtcargoPolitico" id="cargoPolitico" class="form-control" placeholder="Cargo Político" required>
                </div>
                <div class="mb-3">
                    <input type="text" name="txtpartido" id="partido" class="form-control" placeholder="Partido" required>
                </div>
                <div class="mb-3">
                    <textarea name="txthistorico" id="historico" class="form-control" placeholder="Histórico" style="height: 100px; resize: vertical;" required></textarea>
                </div>
                <div class="mb-3">
                    <input type="file" name="txtfoto" id="foto" class="form-control" accept="image/*" required>
                </div>

                <div class="d-grid gap-2 mt-4">
                    <button type="submit" name="btnoperacao" value="CADASTRAR" class="btn btn-outline-primary">Cadastrar</button>
                    <button type="submit" name="btnoperacao" value="DELETAR" class="btn btn-outline-primary">Deletar</button>
                    <button type="submit" name="btnoperacao" value="Consultar por ID" class="btn btn-outline-primary">Consultar por ID</button>
                    <button type="submit" name="btnoperacao" value="Consultar Todos" class="btn btn-outline-primary">Consultar Todos</button>
                    <button type="submit" name="btnoperacao" value="Atualizar Candidato" class="btn btn-outline-primary">Atualizar Candidato</button>
                </div>
            </form>
        </div>

    <c:if test="${not empty message}">
        <script>
            alert('${message}');
            window.location.href = '${pageContext.request.contextPath}/views/PaginaInicial.jsp';
        </script>
    </c:if>
</body>
</html>
