CREATE TABLE IF NOT EXISTS categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nome         VARCHAR(45),
    descricao    VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario      INT AUTO_INCREMENT PRIMARY KEY,
    nome            VARCHAR(100),
    email           VARCHAR(100),
    senha           VARCHAR(255),
    telefone        CHAR(11),
    criado_em       TIMESTAMP,
    atualizado_em   TIMESTAMP,
    status_usuario  TINYINT,
    funcao          TINYINT,
    dt_nasc         DATE,
    cidade          VARCHAR(100),
    regiao          VARCHAR(100),
    selos           INT
);

CREATE TABLE IF NOT EXISTS codigo (
    id_codigos  INT AUTO_INCREMENT PRIMARY KEY,
    codigo      CHAR(6) NOT NULL UNIQUE,
    status_scan TINYINT,
    valor       DECIMAL(5,2)
);

CREATE TABLE IF NOT EXISTS produto (
    id_produto   INT AUTO_INCREMENT PRIMARY KEY,
    nome         VARCHAR(100),
    descricao    VARCHAR(255),
    preco        DECIMAL(5,2),
    imagem_url   VARCHAR(255),
    disponivel   TINYINT,
    fk_categoria INT,
    CONSTRAINT fk_produto_categoria
        FOREIGN KEY (fk_categoria) REFERENCES categoria (id_categoria)
);

CREATE TABLE IF NOT EXISTS feedback (
    id_feedback INT AUTO_INCREMENT PRIMARY KEY,
    nota        INT,
    comentario  VARCHAR(255),
    data_hora   TIMESTAMP,
    fk_usuario  INT,
    CONSTRAINT fk_feedback_usuario
        FOREIGN KEY (fk_usuario) REFERENCES usuario (id_usuario)
);

CREATE TABLE IF NOT EXISTS resgate_item (
    id_resgate          INT AUTO_INCREMENT PRIMARY KEY,
    data_resgate        TIMESTAMP,
    usuario_id_usuario  INT,
    CONSTRAINT fk_resgate_item_usuario
        FOREIGN KEY (usuario_id_usuario) REFERENCES usuario (id_usuario)
);

CREATE TABLE IF NOT EXISTS resgate_selo (
    id_resgate          INT AUTO_INCREMENT,
    codigos_id_codigos  INT,
    usuario_id_usuario  INT,
    data_hora           TIMESTAMP,

    PRIMARY KEY (id_resgate, codigos_id_codigos, usuario_id_usuario),

    CONSTRAINT fk_resgate_selo_codigo
        FOREIGN KEY (codigos_id_codigos) REFERENCES codigo (id_codigos),

    CONSTRAINT fk_resgate_selo_usuario
        FOREIGN KEY (usuario_id_usuario) REFERENCES usuario (id_usuario)
);
