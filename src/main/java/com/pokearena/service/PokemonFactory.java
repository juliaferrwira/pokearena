package com.pokearena.service;

import com.pokearena.model.Pokemon;
import java.util.ArrayList;
import java.util.List;

public class PokemonFactory {
    
    public static List<Pokemon> criarTodosPokemons() {
        List<Pokemon> pokemons = new ArrayList<>();
        pokemons.add(new Pokemon(1, "bulbasaur", 0, 0, 100, "grama", ""));
        pokemons.add(new Pokemon(2, "charmander", 0, 0, 100, "fogo", ""));
        pokemons.add(new Pokemon(3, "squirtle", 0, 0, 100, "agua", ""));
        pokemons.add(new Pokemon(4, "pikachu", 0, 0, 100, "eletrico", ""));
        pokemons.add(new Pokemon(5, "jigglypuff", 0, 0, 100, "normal", ""));
        pokemons.add(new Pokemon(6, "eevee", 0, 0, 100, "normal", ""));
        pokemons.add(new Pokemon(7, "machop", 0, 0, 100, "lutador", ""));
        pokemons.add(new Pokemon(8, "abra", 0, 0, 100, "psiquico", ""));
        pokemons.add(new Pokemon(9, "geodude", 0, 0, 100, "pedra", ""));
        return pokemons;
    }
    
    public static Pokemon criarPokemonPorId(int id) {
        List<Pokemon> todos = criarTodosPokemons();
        for (Pokemon p : todos) {
            if (p.getId() == id) {
                return new Pokemon(p.getId(), p.getNome(), 0, 0, 100, p.getTipo(), p.getSprite());
            }
        }
        return null;
    }
    
    public static boolean temFraqueza(String tipoAtacante, String tipoDefensor) {
        if (tipoAtacante.equals("fogo") && tipoDefensor.equals("grama")) {
            return true;
        }
        if (tipoAtacante.equals("agua") && tipoDefensor.equals("fogo")) {
            return true;
        }
        if (tipoAtacante.equals("agua") && tipoDefensor.equals("pedra")) {
            return true;
        }
        if (tipoAtacante.equals("grama") && tipoDefensor.equals("agua")) {
            return true;
        }
        if (tipoAtacante.equals("eletrico") && tipoDefensor.equals("agua")) {
            return true;
        }
        if (tipoAtacante.equals("lutador") && tipoDefensor.equals("normal")) {
            return true;
        }
        if (tipoAtacante.equals("lutador") && tipoDefensor.equals("pedra")) {
            return true;
        }
        if (tipoAtacante.equals("psiquico") && tipoDefensor.equals("lutador")) {
            return true;
        }
        if (tipoAtacante.equals("pedra") && tipoDefensor.equals("eletrico")) {
            return true;
        }
        return false;
    }
    
    public static int calcularDano(String tipoAtacante, String tipoDefensor) {
        if (temFraqueza(tipoAtacante, tipoDefensor)) {
            return 50;
        }
        return 25;
    }
}
