package com.pokearena.model;

import java.util.ArrayList;
import java.util.List;

public class Treinador {
    private String nome;
    private List<Pokemon> pokemons;
    private List<Insignia> insignias;

    public Treinador(String nome) {
        this.nome = nome;
        this.pokemons = new ArrayList<>();
        this.insignias = new ArrayList<>();
    }

    // Métodos para gerenciar pokémons
    public void adicionarPokemon(Pokemon pokemon) {
        if (pokemon != null) {
            pokemons.add(pokemon);
        }
    }

    public void removerPokemon(Pokemon pokemon) {
        pokemons.remove(pokemon);
    }

    public Pokemon buscarPokemonPorNome(String nome) {
        for (Pokemon pokemon : pokemons) {
            if (pokemon.getNome().equalsIgnoreCase(nome)) {
                return pokemon;
            }
        }
        return null;
    }

    public Pokemon buscarPokemonPorId(int id) {
        for (Pokemon pokemon : pokemons) {
            if (pokemon.getId() == id) {
                return pokemon;
            }
        }
        return null;
    }

    public List<Pokemon> getPokemons() {
        return new ArrayList<>(pokemons);
    }

    public boolean temPokemons() {
        return !pokemons.isEmpty();
    }

    public int quantidadePokemons() {
        return pokemons.size();
    }

    // Métodos para gerenciar insignias
    public void adicionarInsignia(Insignia insignia) {
        if (insignia != null) {
            insignias.add(insignia);
        }
    }

    public List<Insignia> getInsignias() {
        return new ArrayList<>(insignias);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Treinador{" +
                "nome='" + nome + '\'' +
                ", pokemons=" + pokemons.size() +
                ", insignias=" + insignias.size() +
                '}';
    }
}{

}
