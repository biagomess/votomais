<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastro de Usuários</title>
        <!-- Fonte Poppins -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <!-- CSS Customizado -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/CadastroUsuario.css">
    </head>
    <body>
        <div class="cadastro-container">
            <div class="cadastro-container">
                <!-- Logo e Título -->
                <img src="${pageContext.request.contextPath}/imagens/ProjetoVoto+.png" alt="Voto+" class="logo">
                <div class="text-center mt-3">
                    <h1 class="mt-3">Cadastro</h1>
                </div>

                <!-- Exibindo mensagens de erro -->
                <% String mensagem = (String) request.getAttribute("mensagem"); %>
                <% if (mensagem != null) {%>
                <div class="alert alert-danger text-center" role="alert"><%= mensagem%></div>
                <% }%>

                <!-- Formulário -->
                <form action="${pageContext.request.contextPath}/ControllerCadastro" method="post" novalidate>
                    <!-- Campo de Nome -->
                    <div class="text-center mt-3">
                        <label for="userName" class="form-label">Nome</label>
                        <input 
                            type="text" 
                            name="txtnome" 
                            id="userName" 
                            class="form-control" 
                            placeholder="Digite seu nome" 
                            value="<%= request.getParameter("txtnome") != null ? request.getParameter("txtnome") : ""%>" 
                            required>
                    </div>
                    <!-- Campo de E-mail -->
                    <div class="text-center mt-3">
                        <label for="userEmail" class="form-label">E-mail</label>
                        <input 
                            type="email" 
                            name="txtemail" 
                            id="userEmail" 
                            class="form-control" 
                            placeholder="Digite seu e-mail" 
                            value="<%= request.getParameter("txtemail") != null ? request.getParameter("txtemail") : ""%>" 
                            required>
                    </div>
                    <!-- Campo de Senha -->
                    <div class="text-center mt-3">
                        <label for="userPassword" class="form-label">Senha</label>
                        <input 
                            type="password" 
                            name="txtsenha" 
                            id="userPassword" 
                            class="form-control" 
                            placeholder="Digite sua senha" 
                            required>
                    </div>
                    <button type="submit" name="btnoperacao" value="Cadastrar" class="btn">Cadastrar</button>
                </form>
                <!-- Links adicionais -->
                <div class="text-center mt-3">
                    <p>Já possui uma conta? <a href="Login.jsp" class="text-primary">Faça login</a></p>
                    <a href="PaginaPrincipal.jsp" class="btn">Voltar</a>
                </div>
            </div>
        </div>
        <script>
            // Bootstrap para validação
            (function () {
                'use strict';
                var forms = document.querySelectorAll('.needs-validation');
                Array.prototype.slice.call(forms).forEach(function (form) {
                    form.addEventListener('submit', function (event) {
                        if (!form.checkValidity()) {
                            event.preventDefault();
                            event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                    }, false);
                });
            })();
        </script>
    </body>
</html>
