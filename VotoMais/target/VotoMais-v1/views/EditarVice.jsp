<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Editar Vice</title>
        <!-- Fonte Poppins -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <!-- CSS Customizado -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/EditarVice.css">
    </head>
    <body>
        <div class="container mt-5">
            <!-- Título da página -->
            <h2 class="text-center mb-3">Editar Vice</h2>

            <!-- Verifica se o vice existe -->
            <c:if test="${not empty vice}">
                <form action="${pageContext.request.contextPath}/ViceController" method="POST">
                    <input type="hidden" name="btnoperacao" value="ATUALIZAR_VICE">

                    <!-- Campo ID -->
                    <div class="mb-2">
                        <label for="txtid" class="form-label">ID</label>
                        <input type="number" class="form-control" name="txtid" id="txtid" 
                               value="${vice.idVice}" readonly>
                    </div>

                    <!-- Campo Nº de Registro -->
                    <div class="mb-2">
                        <label for="nmrRegistroVice" class="form-label">Nº de Registro</label>
                        <input type="number" class="form-control" name="nmrRegistroVice" id="nmrRegistroVice" 
                               value="${vice.nmrRegistroVice}" required>
                    </div>
                    
                    <!-- Campo Nome -->
                    <div class="mb-2">
                        <label for="txtnome" class="form-label">Nome</label>
                        <input type="text" class="form-control" name="txtnome" id="txtnome" 
                               value="${vice.nomeVice}" required>
                    </div>

                    <!-- Campo Idade -->
                    <div class="mb-2">
                        <label for="txtidade" class="form-label">Idade</label>
                        <input type="number" class="form-control" name="txtidade" id="txtidade" 
                               value="${vice.idadeVice}" required>
                    </div>

                    <!-- Campo Cargo Político -->
                    <div class="mb-2">
                        <label for="txtcargoPolitico" class="form-label">Cargo Político</label>
                        <input type="text" class="form-control" name="txtcargoPolitico" id="txtcargoPolitico" 
                               value="${vice.cargoPoliticoVice}" readonly>
                    </div>

                    <!-- Campo Partido -->
                    <div class="mb-2">
                        <label for="txtpartido" class="form-label">Partido</label>
                        <input type="text" class="form-control" name="txtpartido" id="txtpartido" 
                               value="${vice.partidoVice}" required>
                    </div>

                    <!-- Campo Histórico -->
                    <div class="mb-2">
                        <label for="txthistorico" class="form-label">Histórico</label>
                        <textarea class="form-control" name="txthistorico" id="txthistorico" rows="3" required>${vice.historicoVice}</textarea>
                    </div>

                    <!-- Botão Atualizar -->
                    <button type="submit" class="btn btn-primary w-100 mb-2">Atualizar Vice</button>
                </form>
            </c:if>

            <!-- Mensagem de aviso caso o vice não seja encontrado -->
            <c:if test="${empty vice}">
                <p class="alert alert-danger text-center">Vice não encontrado.</p>
            </c:if>

            <!-- Botões de navegação -->
            <div class="d-flex gap-2">
                <a href="${pageContext.request.contextPath}/views/CadastroVice.jsp" class="btn btn-primary w-100 mb-2">Voltar</a>
            </div>
        </div>
    </body>
</html>
