package com.pokearena.view;

import com.pokearena.model.Pokemon;
import com.pokearena.repository.BancoDeDados;
import java.sql.Connection;
import java.util.List;

public class MenuPrincipal {

    public static void exibirPokemons() {
        List<Pokemon> pokemons = BancoDeDados.listarPokemons();
        
        if (pokemons.isEmpty()) {
            System.out.println("Nenhum pokemon cadastrado.");
            return;
        }
        
        System.out.println("\n=== POKÉMONS CADASTRADOS ===");
        for (Pokemon pokemon : pokemons) {
            System.out.println(pokemon.getNome() + " - Tipo: " + pokemon.getTipo() + 
                             " - HP: " + pokemon.getHp() + "/" + pokemon.getHpMaximo());
        }
    }
    public static void main(String[] args) {
        System.out.println("=== POKEARENA ===");
        Connection conn = BancoDeDados.conectar();

        if (conn != null) {
            System.out.println("Conexão com o banco bem-sucedida");
        } else {
            System.out.println("Falha ao conectar ao banco");
        }
    }
}


