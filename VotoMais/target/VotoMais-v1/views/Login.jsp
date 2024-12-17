<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <!-- Fonte Poppins -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <!-- CSS Customizado -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login.css">
    </head>
    <body>
        <div class="login-container">
            <div class="login-container">
                <!-- Logo e Título -->
                <img src="${pageContext.request.contextPath}/imagens/ProjetoVoto+.png" alt="Voto+" class="logo">
                <h1>Login</h1>
                <!-- Formulário -->
                <form action="${pageContext.request.contextPath}/ControllerAutenticacao" method="post">
                    <!-- Campo de E-mail -->
                    <div class="mb-3">
                        <label for="txtemail" class="form-label">E-mail</label>
                        <input type="email" class="form-control" id="txtemail" name="txtemail" placeholder="Digite seu e-mail" required>
                    </div>
                    <!-- Campo de Senha --> 
                    <div class="mb-3">
                        <label for="txtsenha" class="form-label">Senha</label>
                        <input type="password" class="form-control" id="txtsenha" name="txtsenha" placeholder="Digite sua senha" required>
                    </div>
                    <!-- Botão de Entrar -->
                    <button type="submit" class="btn">Entrar</button>
                    <!-- Links adicionais -->
                    <div class="text-center mt-3">
                        <p>Não tem uma conta? <a href="${pageContext.request.contextPath}/views/CadastroUsuario.jsp" class="text-primary">Cadastre-se</a></p>
                        <a href="${pageContext.request.contextPath}/views/PaginaPrincipal.jsp" class="btn">Voltar</a>
                    </div>
                </form>
            </div>
    </body>
</html>
