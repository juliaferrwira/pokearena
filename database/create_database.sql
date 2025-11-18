-- Criar o banco de dados (execute como superusuário)
CREATE DATABASE pokearena
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Conectar ao banco pokearena antes de criar as tabelas
\c pokearena;

-- Tabela de Pokémons
CREATE TABLE IF NOT EXISTS pokemons (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    ataque INTEGER NOT NULL DEFAULT 0,
    defesa INTEGER NOT NULL DEFAULT 0,
    hp INTEGER NOT NULL DEFAULT 100,
    hp_maximo INTEGER NOT NULL DEFAULT 100,
    sprite TEXT,
    CONSTRAINT hp_valido CHECK (hp >= 0 AND hp <= hp_maximo)
);

-- Tabela de Treinadores
CREATE TABLE IF NOT EXISTS treinadores (
    nome VARCHAR(100) PRIMARY KEY
);

-- Tabela de Insígnias
CREATE TABLE IF NOT EXISTS insignias (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao TEXT,
    icone TEXT
);

-- Tabela de relacionamento Treinador-Pokémon
CREATE TABLE IF NOT EXISTS treinador_pokemon (
    treinador_nome VARCHAR(100) NOT NULL,
    pokemon_id INTEGER NOT NULL,
    PRIMARY KEY (treinador_nome, pokemon_id),
    FOREIGN KEY (treinador_nome) REFERENCES treinadores(nome) ON DELETE CASCADE,
    FOREIGN KEY (pokemon_id) REFERENCES pokemons(id) ON DELETE CASCADE
);

-- Tabela de relacionamento Treinador-Insígnia
CREATE TABLE IF NOT EXISTS treinador_insignia (
    treinador_nome VARCHAR(100) NOT NULL,
    insignia_id INTEGER NOT NULL,
    data_conquista TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (treinador_nome, insignia_id),
    FOREIGN KEY (treinador_nome) REFERENCES treinadores(nome) ON DELETE CASCADE,
    FOREIGN KEY (insignia_id) REFERENCES insignias(id) ON DELETE CASCADE
);

-- Tabela de Batalhas
CREATE TABLE IF NOT EXISTS batalhas (
    id SERIAL PRIMARY KEY,
    pokemon1_id INTEGER NOT NULL,
    pokemon2_id INTEGER NOT NULL,
    vencedor_id INTEGER NOT NULL,
    data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (pokemon1_id) REFERENCES pokemons(id) ON DELETE CASCADE,
    FOREIGN KEY (pokemon2_id) REFERENCES pokemons(id) ON DELETE CASCADE,
    FOREIGN KEY (vencedor_id) REFERENCES pokemons(id) ON DELETE CASCADE
);

-- Índices para melhorar performance
CREATE INDEX IF NOT EXISTS idx_pokemons_tipo ON pokemons(tipo);
CREATE INDEX IF NOT EXISTS idx_pokemons_nome ON pokemons(nome);
CREATE INDEX IF NOT EXISTS idx_batalhas_data ON batalhas(data);
CREATE INDEX IF NOT EXISTS idx_treinador_pokemon_treinador ON treinador_pokemon(treinador_nome);
CREATE INDEX IF NOT EXISTS idx_treinador_pokemon_pokemon ON treinador_pokemon(pokemon_id);

-- Inserir algumas insígnias padrão AINDA NÃO DEFINIDAS, ESPERAR ÍCARO
INSERT INTO insignias (nome, descricao) VALUES
    ('Insígnia de Pedra', 'Conquistada ao derrotar Brock'),
    ('Insígnia de Água', 'Conquistada ao derrotar Misty'),
    ('Insígnia de Raio', 'Conquistada ao derrotar Ash')
ON CONFLICT (nome) DO NOTHING;

-- Comentários nas tabelas
COMMENT ON TABLE pokemons IS 'Armazena informações dos pokémons';
COMMENT ON TABLE treinadores IS 'Armazena informações dos treinadores';
COMMENT ON TABLE insignias IS 'Armazena informações das insígnias disponíveis';
COMMENT ON TABLE treinador_pokemon IS 'Relacionamento muitos-para-muitos entre treinadores e pokémons';
COMMENT ON TABLE treinador_insignia IS 'Relacionamento muitos-para-muitos entre treinadores e insígnias';
COMMENT ON TABLE batalhas IS 'Histórico de batalhas realizadas';


