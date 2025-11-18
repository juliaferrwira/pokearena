-- Ver todos os pokémons
SELECT * FROM pokemons ORDER BY nome;

-- Ver todos os treinadores
SELECT * FROM treinadores;

-- Ver todas as insígnias
SELECT * FROM insignias;

-- Ver pokémons de um treinador específico
SELECT p.* 
FROM pokemons p
INNER JOIN treinador_pokemon tp ON p.id = tp.pokemon_id
WHERE tp.treinador_nome = 'NomeDoTreinador';

-- Ver insígnias de um treinador
SELECT i.*, ti.data_conquista
FROM insignias i
INNER JOIN treinador_insignia ti ON i.id = ti.insignia_id
WHERE ti.treinador_nome = 'NomeDoTreinador';

-- Ver histórico de batalhas
SELECT 
    b.id,
    p1.nome AS pokemon1,
    p2.nome AS pokemon2,
    v.nome AS vencedor,
    b.data
FROM batalhas b
INNER JOIN pokemons p1 ON b.pokemon1_id = p1.id
INNER JOIN pokemons p2 ON b.pokemon2_id = p2.id
INNER JOIN pokemons v ON b.vencedor_id = v.id
ORDER BY b.data DESC;

-- Inserir um pokémon
INSERT INTO pokemons (id, nome, tipo, ataque, defesa, hp, hp_maximo, sprite)
VALUES (1, 'Bulbasaur', 'grama', 0, 0, 100, 100, '');

-- Inserir um treinador
INSERT INTO treinadores (nome) VALUES ('Ash')
ON CONFLICT (nome) DO NOTHING;

-- Associar pokémon a um treinador
INSERT INTO treinador_pokemon (treinador_nome, pokemon_id)
VALUES ('Ash', 1)
ON CONFLICT DO NOTHING;

-- Conceder insígnia a um treinador
INSERT INTO treinador_insignia (treinador_nome, insignia_id)
VALUES ('Ash', 1);

-- Atualizar HP de um pokémon
UPDATE pokemons SET hp = 50 WHERE id = 1;

-- Atualizar nome de um treinador
UPDATE treinadores SET nome = 'NovoNome' WHERE nome = 'NomeAntigo';

-- Deletar um pokémon (cuidado: isso também deleta relacionamentos)
DELETE FROM pokemons WHERE id = 1;

-- Deletar um treinador (cuidado: isso também deleta relacionamentos)
DELETE FROM treinadores WHERE nome = 'NomeDoTreinador';

-- Contar pokémons por tipo
SELECT tipo, COUNT(*) AS quantidade
FROM pokemons
GROUP BY tipo
ORDER BY quantidade DESC;

-- Treinadores com mais pokémons
SELECT 
    t.nome,
    COUNT(tp.pokemon_id) AS total_pokemons
FROM treinadores t
LEFT JOIN treinador_pokemon tp ON t.nome = tp.treinador_nome
GROUP BY t.nome
ORDER BY total_pokemons DESC;

-- Pokémons mais usados em batalhas
SELECT 
    p.nome,
    COUNT(*) AS vezes_usado
FROM pokemons p
INNER JOIN (
    SELECT pokemon1_id AS pokemon_id FROM batalhas
    UNION ALL
    SELECT pokemon2_id AS pokemon_id FROM batalhas
) b ON p.id = b.pokemon_id
GROUP BY p.id, p.nome
ORDER BY vezes_usado DESC;

-- Limpar todas as batalhas
-- DELETE FROM batalhas;

-- Limpar todos os relacionamentos treinador-pokémon
-- DELETE FROM treinador_pokemon;

-- Limpar todos os relacionamentos treinador-insígnia
-- DELETE FROM treinador_insignia;

-- Limpar todos os treinadores
-- DELETE FROM treinadores;

-- Limpar todos os pokémons
-- DELETE FROM pokemons;


