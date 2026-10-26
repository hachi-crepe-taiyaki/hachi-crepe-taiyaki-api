-- Dados de teste para desenvolvimento local.
-- As senhas abaixo estão armazenadas com BCrypt, compatível com BCryptPasswordEncoder.

-- Senha em texto puro para login de teste: Marley123!
INSERT INTO usuario (nome, email, senha, telefone, criado_em, atualizado_em, status_usuario, funcao, dt_nasc, cidade, regiao, selos)
SELECT 'Marley', 'marley@hachi.test', '$2a$10$VTtyfYH5jn7l2QE7acC6mOlYMhUO7lJBWL02YGYRYa2DfmTAhGDVC',
       '11987654321', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 0, DATE '2000-05-15', 'São Paulo', 'Centro', 0
WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE LOWER(email) = 'marley@hachi.test');

-- Senha em texto puro para login de teste: Julia123!
INSERT INTO usuario (nome, email, senha, telefone, criado_em, atualizado_em, status_usuario, funcao, dt_nasc, cidade, regiao, selos)
SELECT 'Julia', 'julia@hachi.test', '$2a$10$wwkEuFnjhMlDiwTSC6KIJenstKiXG5fI5MI3qBjmBgTbj9iW/kRVa',
       '11987654322', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, DATE '1998-08-22', 'São Paulo', 'Centro', 0
WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE LOWER(email) = 'julia@hachi.test');

-- Senha em texto puro para login de teste: Leonardo123!
INSERT INTO usuario (nome, email, senha, telefone, criado_em, atualizado_em, status_usuario, funcao, dt_nasc, cidade, regiao, selos)
SELECT 'Leonardo', 'leonardo@hachi.test', '$2a$10$.WyWmWSJA4jmeS8x4Fb3D.qqBq/nL9kYIBLQDpvNqE7QXXI0l79d.',
       '11987654323', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2, DATE '1995-03-10', 'São Paulo', 'Centro', 0
WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE LOWER(email) = 'leonardo@hachi.test');

INSERT INTO categoria (nome, descricao)
SELECT 'Cafés', 'Cafés quentes e gelados preparados na hora.'
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE LOWER(nome) = 'cafés');

INSERT INTO categoria (nome, descricao)
SELECT 'Doces', 'Sobremesas e doces para acompanhar seu café.'
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE LOWER(nome) = 'doces');

INSERT INTO categoria (nome, descricao)
SELECT 'Salgados', 'Lanches salgados preparados diariamente.'
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE LOWER(nome) = 'salgados');

INSERT INTO produto (nome, descricao, preco, imagem_url, disponivel, fk_categoria)
SELECT 'Espresso', 'Café espresso tradicional.', 6.00, NULL, 1, c.id_categoria
FROM categoria c
WHERE LOWER(c.nome) = 'cafés'
  AND NOT EXISTS (SELECT 1 FROM produto WHERE LOWER(nome) = 'espresso');

INSERT INTO produto (nome, descricao, preco, imagem_url, disponivel, fk_categoria)
SELECT 'Cappuccino', 'Café com leite vaporizado e espuma.', 10.00, NULL, 1, c.id_categoria
FROM categoria c
WHERE LOWER(c.nome) = 'cafés'
  AND NOT EXISTS (SELECT 1 FROM produto WHERE LOWER(nome) = 'cappuccino');

INSERT INTO produto (nome, descricao, preco, imagem_url, disponivel, fk_categoria)
SELECT 'Café Gelado', 'Café gelado levemente adocicado.', 12.00, NULL, 1, c.id_categoria
FROM categoria c
WHERE LOWER(c.nome) = 'cafés'
  AND NOT EXISTS (SELECT 1 FROM produto WHERE LOWER(nome) = 'café gelado');

INSERT INTO produto (nome, descricao, preco, imagem_url, disponivel, fk_categoria)
SELECT 'Brownie', 'Brownie de chocolate.', 9.00, NULL, 1, c.id_categoria
FROM categoria c
WHERE LOWER(c.nome) = 'doces'
  AND NOT EXISTS (SELECT 1 FROM produto WHERE LOWER(nome) = 'brownie');

INSERT INTO produto (nome, descricao, preco, imagem_url, disponivel, fk_categoria)
SELECT 'Cheesecake', 'Fatia de cheesecake cremosa.', 14.00, NULL, 1, c.id_categoria
FROM categoria c
WHERE LOWER(c.nome) = 'doces'
  AND NOT EXISTS (SELECT 1 FROM produto WHERE LOWER(nome) = 'cheesecake');

INSERT INTO produto (nome, descricao, preco, imagem_url, disponivel, fk_categoria)
SELECT 'Cookie', 'Cookie com gotas de chocolate.', 7.00, NULL, 1, c.id_categoria
FROM categoria c
WHERE LOWER(c.nome) = 'doces'
  AND NOT EXISTS (SELECT 1 FROM produto WHERE LOWER(nome) = 'cookie');

INSERT INTO produto (nome, descricao, preco, imagem_url, disponivel, fk_categoria)
SELECT 'Pão de Queijo', 'Pão de queijo assado na hora.', 6.50, NULL, 1, c.id_categoria
FROM categoria c
WHERE LOWER(c.nome) = 'salgados'
  AND NOT EXISTS (SELECT 1 FROM produto WHERE LOWER(nome) = 'pão de queijo');

INSERT INTO produto (nome, descricao, preco, imagem_url, disponivel, fk_categoria)
SELECT 'Coxinha', 'Coxinha de frango.', 8.00, NULL, 1, c.id_categoria
FROM categoria c
WHERE LOWER(c.nome) = 'salgados'
  AND NOT EXISTS (SELECT 1 FROM produto WHERE LOWER(nome) = 'coxinha');

INSERT INTO produto (nome, descricao, preco, imagem_url, disponivel, fk_categoria)
SELECT 'Misto Quente', 'Sanduíche de queijo e presunto.', 11.00, NULL, 1, c.id_categoria
FROM categoria c
WHERE LOWER(c.nome) = 'salgados'
  AND NOT EXISTS (SELECT 1 FROM produto WHERE LOWER(nome) = 'misto quente');
