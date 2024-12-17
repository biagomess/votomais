drop table usuarios
-- Tabela de Usuarios
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    nivel_acesso INT DEFAULT 2 CHECK (nivel_acesso IN (1, 2))
);

drop table candidatos
	-- Tabela de Candidatos
SELECT * FROM candidatos
select * from vice
CREATE TABLE candidatos (
    id SERIAL PRIMARY KEY,
    nmrRegistro INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    idade INT NOT NULL,
    cargo_politico VARCHAR(100),
    partido VARCHAR(100),
    historico TEXT,
    foto VARCHAR(255),
    idVice INTEGER,
    FOREIGN KEY (idVice) REFERENCES vice(idVice) -- Chave estrangeira para vice
);
SELECT nome, nmrRegistroVice FROM vice WHERE idVice = 1
UPDATE candidatos
SET idVice = 1  -- ID do vice que você quer associar ao candidato
WHERE id = 1;   -- ID do candidato que você quer atualizar

);
drop table vice
select * from vice
CREATE TABLE vice (
    idVice SERIAL PRIMARY KEY,
	nmrRegistroVice INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    idade INT NOT NULL,
    cargo_politico VARCHAR(100) DEFAULT 'Vice-Prefeito',
    partido VARCHAR(100),
    foto VARCHAR(255),
	historico TEXT
);

drop table avaliacao
-- Tabela de Avaliações
select * from avaliacao
-- Criar a tabela 'avaliacao' com a restrição UNIQUE já incluída
CREATE TABLE avaliacao (
    id SERIAL PRIMARY KEY,  -- ID da avaliação com auto-incremento
    candidato_id INT NOT NULL REFERENCES candidatos(id) ON DELETE CASCADE,  -- Referência ao candidato
    usuario_id INT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,  -- Referência ao usuário que fez a avaliação
    nome_usuario VARCHAR(100),  -- Nome do usuário (opcional)
    estrelas INT NOT NULL CHECK (estrelas BETWEEN 1 AND 5),  -- Avaliação com estrelas (1 a 5)
    comentario TEXT,  -- Comentário opcional sobre a avaliação
    CONSTRAINT unique_user_candidato UNIQUE (usuario_id, candidato_id)  -- Garante que cada usuário só possa avaliar um candidato uma vez
);

-- Inserindo um Administrador Inicial (Opcional)
INSERT INTO usuarios (nome, email, senha, nivel_acesso) 
VALUES ('Admin', 'admin@votomais.com', 'admin123', '1');

-- Consultar Tabelas 
SELECT * FROM usuarios;
SELECT * FROM candidatos;
SELECT * FROM avaliacao;

