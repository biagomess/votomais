<%@page import="com.votomais.model.Vice"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastro de Candidato</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">   
        <!-- Font-Family Poppins -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">
        <!-- CSS Customizado -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/CadastroCandidato.css">
    </head>
    <body>
        <div class="container">
            <h2>Gerenciamento de Prefeitos</h2>

            <!-- Navegação simplificada -->
            <div class="text-end mb-3">
                <a href="${pageContext.request.contextPath}/CandidatoController?btnoperacao=CONSULTAR_TODOS_CANDIDATOS" class="btn btn-secondary">Consultar Todos os Candidatos</a>
                <a href="${pageContext.request.contextPath}/views/PaginaPrincipal.jsp" class="btn btn-secondary">Voltar</a>
            </div>

            <!-- Formulário de Cadastro -->
            <h3>Cadastro de Prefeito</h3>
            <form action="${pageContext.request.contextPath}/CandidatoController" method="POST">
                <input type="hidden" name="btnoperacao" value="CADASTRAR_CANDIDATO">

                <!-- Número de Registro do Candidato -->
                <div class="mb-3">
                    <label for="nmrRegistro" class="form-label">Número de Registro Eleitoral</label>
                    <input type="number" class="form-control" name="nmrRegistro" id="nmrRegistro" placeholder="Nº de Registro" required>
                </div>
                <!-- Nome do Candidato -->
                <div class="mb-3">
                    <label for="txtnome" class="form-label">Nome do Candidato</label>
                    <input type="text" class="form-control" name="txtnome" id="txtnome" placeholder="Digite o nome do candidato" required>
                </div>
                <!-- Idade -->
                <div class="mb-3">
                    <label for="txtidade" class="form-label">Idade</label>
                    <input type="number" class="form-control" name="txtidade" id="txtidade" placeholder="Digite a idade" required>
                </div>

                <!-- Partido -->
                <div class="mb-3">
                    <label for="txtpartido" class="form-label">Partido</label>
                    <input type="text" class="form-control" name="txtpartido" id="txtpartido" placeholder="Digite o partido" required>
                </div>

                <!-- Histórico -->
                <div class="mb-3">
                    <label for="txthistorico" class="form-label">Histórico</label>
                    <textarea class="form-control" name="txthistorico" id="txthistorico" rows="4" placeholder="Digite o histórico" required></textarea>
                </div>

                <button type="submit" class="btn btn-primary">Cadastrar Candidato</button>
            </form>
        </div>
    </body>
</html>
