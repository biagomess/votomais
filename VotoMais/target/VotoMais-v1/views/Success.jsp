<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Operação Realizada</title>
        <!-- Fonte Poppins -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <!-- CSS Customizado -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Sucess.css">
    </head>
    <body>
        <div class="success-container">
            <h1>Operação Realizada com Sucesso!</h1>
            <p>${message}</p>
            <a href="${pageContext.request.contextPath}/views/PaginaPrincipal.jsp" class="btn btn-primary">Voltar</a>
        </div>
    </body>
</html>
