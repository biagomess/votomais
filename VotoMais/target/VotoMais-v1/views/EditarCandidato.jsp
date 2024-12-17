<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Editar Candidato</title>
        <!-- Fonte Poppins -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <!-- CSS Customizado -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/EditarCandidato.css">
    </head>
    <body>
        <div class="container mt-5">
            <!-- Título da página -->
            <h2 class="text-center mb-3">Editar Candidato</h2>

            <!-- Verifica se o candidato existe -->
            <c:if test="${not empty candidato}">
                <form action="${pageContext.request.contextPath}/CandidatoController" method="POST">
                    <input type="hidden" name="btnoperacao" value="ATUALIZAR_CANDIDATO">

                    <!-- Campo ID -->
                    <div class="mb-2">
                        <label for="txtid" class="form-label">ID</label>
                        <input type="number" class="form-control" name="txtid" id="txtid" 
                               value="${candidato.idCandidato}" readonly>
                    </div>

                    <!-- Campo Nº de Registro -->
                    <div class="mb-2">
                        <label for="nmrRegistro" class="form-label">Nº de Registro</label>
                        <input type="number" class="form-control" name="nmrRegistro" id="nmrRegistro" 
                               required value="${candidato.nmrRegistro}">
                    </div>                    

                    <!-- Campo Nome -->
                    <div class="mb-2">
                        <label for="txtnome" class="form-label">Nome</label>
                        <input type="text" class="form-control" name="txtnome" id="txtnome" 
                               value="${candidato.nomeCandidato}" required>
                    </div>

                    <!-- Campo ID do Vice -->
                    <div class="mb-2">
                        <label for="txtidVice" class="form-label">Vice</label>
                        <c:choose>
                            <c:when test="${candidato.idVice == null or candidato.idVice == 0}">
                                <input type="number" class="form-control" name="txtidVice" id="txtidVice" 
                                       placeholder="Digite o ID do vice" />
                            </c:when>
                            <!-- Se vice associado, exibe o nome do vice -->
                            <c:otherwise>
                                <input type="text" class="form-control" name="txtidVice" id="txtidVice" 
                                       value="${candidato.nomeVice}" readonly>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Campos lado a lado idade -->
                    <div class="row">
                        <!-- Campo Idade -->
                        <div class="col-md-6 mb-2">
                            <label for="txtidade" class="form-label">Idade</label>
                            <input type="number" class="form-control" name="txtidade" id="txtidade" 
                                   value="${candidato.idadeCandidato}" required>
                        </div>
                        <!-- Campo Cargo Político -->
                        <div class="col-md-6 mb-2">
                            <label for="txtcargoPolitico" class="form-label">Cargo Político</label>
                            <input type="text" class="form-control" name="txtcargoPolitico" id="txtcargoPolitico" 
                                   value="${candidato.cargoPoliticoCandidato}" readonly>
                        </div>
                    </div>

                    <!-- Campo Partido -->
                    <div class="mb-2">
                        <label for="txtpartido" class="form-label">Partido</label>
                        <input type="text" class="form-control" name="txtpartido" id="txtpartido" 
                               value="${candidato.partidoCandidato}" required>
                    </div>

                    <!-- Campo Histórico -->
                    <div class="mb-2">
                        <label for="txthistorico" class="form-label">Histórico</label>
                        <textarea class="form-control" name="txthistorico" id="txthistorico" rows="3" required>${candidato.historicoCandidato}</textarea>
                    </div>

                    <!-- Botão Atualizar -->
                    <button type="submit" class="btn btn-primary w-100 mb-2">Atualizar Candidato</button>
                </form>
            </c:if>

            <!-- Botões de navegação -->
            <div class="d-flex gap-2">
                <a href="${pageContext.request.contextPath}/views/CadastroCandidato.jsp" class="btn btn-primary w-100 mb-2">Voltar</a>
            </div>
        </div>
    </body>
</html>
