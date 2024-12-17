<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- FONT-FAMILY Poppins -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">

        <!-- Custom CSS -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Login.css">

        <title>Login - Voto+</title>
    </head>
    <body>
        <div class="container d-flex justify-content-center align-items-center min-vh-100">
            <div class="login-card card shadow p-4">
                <div class="text-center mb-4">
                    <img src="${pageContext.request.contextPath}/images/ProjetoVoto+.png" alt="Voto+" class="logo">
                    <h2 class="text-primary mt-2">Login</h2>
                </div>

                <form action="${pageContext.request.contextPath}/Controller_Autenticacao" method="post">
                    <div class="mb-3">
                        <label for="email" class="form-label">E-mail</label>
                        <input type="email" name="txtemail" id="email" class="form-control" placeholder="Digite seu e-mail" required>
                    </div>
                    <div class="mb-3">
                        <label for="senha" class="form-label">Senha</label>
                        <input type="password" name="txtsenha" id="senha" class="form-control" placeholder="Digite sua senha" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Entrar</button>
                </form>

                <% if (request.getAttribute("mensagem") != null) {%>
                <div class="alert alert-danger mt-3 text-center"><%= request.getAttribute("mensagem")%></div>
                <% }%>

                <div class="text-center mt-3">
                    <p>Não tem uma conta? <a href="${pageContext.request.contextPath}/views/CadastroUsuario.jsp" class="text-primary">Cadastre-se</a></p>
                    <a href="${pageContext.request.contextPath}/views/PaginaInicial.jsp" class="btn btn-secondary mt-2">Voltar à Página Inicial</a>
                </div>
            </div>
        </div>
    </body>
</html>
