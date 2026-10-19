CREATE TABLE IF NOT EXISTS `categoria` (
    `idCategoria` INT NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(45) NOT NULL,
    `descricao` VARCHAR(255) NULL,
    PRIMARY KEY (`idCategoria`)
    )

CREATE TABLE IF NOT EXISTS `produto` (
    `idProduto` INT NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(100) NOT NULL,
    `descricao` VARCHAR(255) NULL,
    `preco` DECIMAL(5,2) NOT NULL,
    `imagem_url` VARCHAR(255) NULL,
    `disponivel` TINYINT NULL,
    `fk_categoria` INT NOT NULL,
    PRIMARY KEY (`idProduto`),
    CONSTRAINT `fk_produto_categoria`
    FOREIGN KEY (`fk_categoria`)
    REFERENCES `categoria` (`idCategoria`)
    )

CREATE TABLE IF NOT EXISTS `usuario` (
    `idUsuario` INT NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `senha` VARCHAR(255) NOT NULL,
    `telefone` CHAR(11) NULL,
    `criado_em` DATETIME NULL,
    `atualizado_em` DATETIME NULL,
    `status_usuario` TINYINT NULL,
    `funcao` TINYINT(3) NULL,
    `dt_nasc` DATE NULL,
    `cidade` VARCHAR(100) NULL,
    `selos` INT,
    PRIMARY KEY (`idUsuario`)
    )

CREATE TABLE IF NOT EXISTS `feedback` (
    `idFeedback` INT NOT NULL AUTO_INCREMENT,
    `nota` INT NULL,
    `comentario` VARCHAR(255) NULL,
    `data_hora` DATETIME NULL,
    `fk_usuario` INT NOT NULL,
    PRIMARY KEY (`idFeedback`),
    CONSTRAINT `fk_feedback_usuario`
    FOREIGN KEY (`fk_usuario`)
    REFERENCES `usuario` (`idUsuario`)
    )

CREATE TABLE IF NOT EXISTS `resgate` (
    `idResgate` INT NOT NULL AUTO_INCREMENT,
    `data_resgate` DATETIME NULL,
    `usuario_idUsuario` INT NOT NULL,
    PRIMARY KEY (`idResgate`),
    CONSTRAINT `fk_resgate_usuario`
    FOREIGN KEY (`usuario_idUsuario`)
    REFERENCES `usuario` (`idUsuario`)
    )

CREATE TABLE IF NOT EXIST Codigo (
     idCodigo INT AUTO_INCREMENT PRIMARY KEY,
     codigo CHAR(6) NOT NULL,
     statusScan TINYINT
    )

CREATE TABLE IF NOT EXIST resgate_selo (
      Codigo_idCodigo INT,
      usuario_idUsuario INT,
      valor DECIMAL(5,2),
      data_hora DATETIME,
      PRIMARY KEY (Codigo_idCodigo, usuario_idUsuario),
      CONSTRAINT fk_resgate_selo_codigo FOREIGN KEY (Codigo_idCodigo)
          REFERENCES Codigo(idCodigo),
      CONSTRAINT fk_resgate_selo_usuario FOREIGN KEY (usuario_idUsuario)
          REFERENCES usuario(idUsuario)
    )