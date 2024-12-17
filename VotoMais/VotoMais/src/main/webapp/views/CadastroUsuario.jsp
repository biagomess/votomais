<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">

        <!-- Bootstrap Icons -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

        <!-- Custom CSS -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/CadastroUsuario.css">

        <title>Cadastro - Voto+</title>  
    </head>
    <body>
        <div class="container d-flex justify-content-center align-items-center min-vh-100">
            <div class="cadastro-card card shadow p-4">
                <div class="text-center mb-4">
                    <img src="${pageContext.request.contextPath}/images/ProjetoVoto+.png" alt="Voto+" class="logo">
                    <h2 class="text-primary mt-2">Cadastro</h2>
                </div>

                <% String mensagem = (String) request.getAttribute("mensagem"); %>
                <% if (mensagem != null) {%>
                <div class="alert alert-danger text-center" role="alert"><%= mensagem%></div>
                <% }%>

                <form action="${pageContext.request.contextPath}/Controller_Cadastro" method="post" class="needs-validation" novalidate>
                    <div class="mb-3">
                        <label for="nome" class="form-label">Nome</label>
                        <input type="text" name="txtnome" id="nome" class="form-control" placeholder="Digite seu nome" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">E-mail</label>
                        <input type="email" name="txtemail" id="email" class="form-control" placeholder="Digite seu e-mail" required>
                    </div>
                    <div class="mb-3">
                        <label for="senha" class="form-label">Senha</label>
                        <input type="password" name="txtsenha" id="senha" class="form-control" placeholder="Digite sua senha" required>
                    </div>
                    <button type="submit" name="btnoperacao" class="btn btn-primary w-100">Cadastrar</button>
                </form>

                <div class="text-center mt-3">
                    <p>Já possui uma conta? <a href="Login.jsp" class="text-primary">Faça login</a></p>
                    <a href="PaginaInicial.jsp" class="btn btn-secondary mt-2">Voltar à Página Inicial</a>
                </div>
            </div>
        </div>
    </body>
</html>
