CREATE TABLE IF NOT EXISTS categoria (
                                         idCategoria INT AUTO_INCREMENT PRIMARY KEY,
                                         nome VARCHAR(45),
    descricao VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS usuario (
                                       idUsuario INT AUTO_INCREMENT PRIMARY KEY,
                                       nome VARCHAR(100),
    email VARCHAR(100),
    senha VARCHAR(255),
    telefone CHAR(11),
    criado_em TIMESTAMP,
    atualizado_em TIMESTAMP,
    status_usuario TINYINT,
    funcao TINYINT,
    dt_nasc DATE,
    cidade VARCHAR(100),
    regiao VARCHAR(100),
    selos INT
    );

CREATE TABLE IF NOT EXISTS Codigo (
                                      idCodigos INT AUTO_INCREMENT PRIMARY KEY,
                                      codigo CHAR(6) NOT NULL UNIQUE,
    statusScan TINYINT,
    valor DECIMAL(5,2)
    );

CREATE TABLE IF NOT EXISTS produto (
                                       idProduto INT AUTO_INCREMENT PRIMARY KEY,
                                       nome VARCHAR(100),
    descricao VARCHAR(255),
    preco DECIMAL(5,2),
    imagem_url VARCHAR(255),
    disponivel TINYINT,
    fk_categoria INT,
    CONSTRAINT fk_produto_categoria
    FOREIGN KEY (fk_categoria)
    REFERENCES categoria(idCategoria)
    );

CREATE TABLE IF NOT EXISTS feedback (
                                        idFeedback INT AUTO_INCREMENT PRIMARY KEY,
                                        nota INT,
                                        comentario VARCHAR(255),
    data_hora TIMESTAMP,
    fk_usuario INT,
    CONSTRAINT fk_feedback_usuario
    FOREIGN KEY (fk_usuario)
    REFERENCES usuario(idUsuario)
    );

CREATE TABLE IF NOT EXISTS resgate_item (
                                            idResgate INT AUTO_INCREMENT PRIMARY KEY,
                                            data_resgate TIMESTAMP,
                                            usuario_idUsuario INT,
                                            CONSTRAINT fk_resgate_item_usuario
                                            FOREIGN KEY (usuario_idUsuario)
    REFERENCES usuario(idUsuario)
    );

CREATE TABLE IF NOT EXISTS resgate_selo (
                                            idResgate INT AUTO_INCREMENT,
                                            codigos_idCodigos INT,
                                            usuario_idUsuario INT,
                                            data_hora TIMESTAMP,

                                            PRIMARY KEY (idResgate, codigos_idCodigos, usuario_idUsuario),

    CONSTRAINT fk_resgate_selo_codigo
    FOREIGN KEY (codigos_idCodigos)
    REFERENCES Codigo(idCodigos),

    CONSTRAINT fk_resgate_selo_usuario
    FOREIGN KEY (usuario_idUsuario)
    REFERENCES usuario(idUsuario)
    );