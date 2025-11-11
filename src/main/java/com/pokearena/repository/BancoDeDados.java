package com.pokearena.repository;

import com.pokearena.model.Pokemon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {
    private static final String URL = "jdbc:postgresql://localhost:5433/pokearena";
    private static final String USER = "postgres";
    private static final String PASSWORD = "senha123";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco!");
            e.printStackTrace();
            return null;
        }
    }

    public static void salvarPokemon(Pokemon pokemon) {
        String sql = "INSERT INTO pokemons (id, nome, tipo, ataque, defesa, hp, hp_maximo, sprite) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = conectar();
        if (conn == null) {
            return;
        }

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pokemon.getId());
            stmt.setString(2, pokemon.getNome());
            stmt.setString(3, pokemon.getTipo());
            stmt.setInt(4, pokemon.getAtaque());
            stmt.setInt(5, pokemon.getDefesa());
            stmt.setInt(6, pokemon.getHp());
            stmt.setInt(7, pokemon.getHpMaximo());
            stmt.setString(8, pokemon.getSprite());

            stmt.executeUpdate();
            System.out.println("Pokemon " + pokemon.getNome() + " salvo com sucesso!");

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar pokemon: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static List<Pokemon> listarPokemons() {
        List<Pokemon> pokemons = new ArrayList<>();
        String sql = "SELECT id, nome, tipo, ataque, defesa, hp, hp_maximo, sprite FROM pokemons ORDER BY nome";

        Connection conn = conectar();
        if (conn == null) {
            return pokemons;
        }

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String tipo = rs.getString("tipo");
                int ataque = rs.getInt("ataque");
                int defesa = rs.getInt("defesa");
                int hp = rs.getInt("hp");
                int hpMaximo = rs.getInt("hp_maximo");
                String sprite = rs.getString("sprite");

                Pokemon pokemon = new Pokemon(id, nome, ataque, defesa, hpMaximo, tipo, sprite);
                pokemon.setHp(hp);
                pokemons.add(pokemon);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao listar pokemons: " + e.getMessage());
            e.printStackTrace();
        }

        return pokemons;
    }
}