package com.pokearena.service;

import com.pokearena.model.Pokemon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BatalhaService {
    private List<Pokemon> pokemonsJogador;
    private List<Pokemon> pokemonsMaquina;
    private Pokemon pokemonAtualJogador;
    private Pokemon pokemonAtualMaquina;
    private int indicePokemonAtualJogador;
    private int indicePokemonAtualMaquina;
    private Random random;
    private boolean batalhaTerminada;
    private String vencedor;

    public BatalhaService(List<Pokemon> pokemonsJogador) {
        this.pokemonsJogador = criarCopiasPokemons(pokemonsJogador);
        this.pokemonsMaquina = gerarPokemonsMaquina();
        this.random = new Random();
        this.batalhaTerminada = false;
        
        inicializarHPs();
        
        this.indicePokemonAtualJogador = 0;
        this.indicePokemonAtualMaquina = 0;
        this.pokemonAtualJogador = this.pokemonsJogador.get(0);
        this.pokemonAtualMaquina = this.pokemonsMaquina.get(0);
    }

    private List<Pokemon> criarCopiasPokemons(List<Pokemon> originais) {
        List<Pokemon> copias = new ArrayList<>();
        for (Pokemon original : originais) {
            Pokemon copia = new Pokemon(
                original.getId(),
                original.getNome(),
                original.getAtaque(),
                0,
                100,
                original.getTipo(),
                original.getSprite()
            );
            copia.setHp(100);
            copias.add(copia);
        }
        return copias;
    }

    private List<Pokemon> gerarPokemonsMaquina() {
        List<Pokemon> todosPokemons = PokemonFactory.criarTodosPokemons();
        List<Pokemon> pokemonsSelecionados = new ArrayList<>();
        List<Integer> indicesUsados = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            int indice;
            do {
                indice = random.nextInt(todosPokemons.size());
            } while (indicesUsados.contains(indice));
            
            indicesUsados.add(indice);
            Pokemon original = todosPokemons.get(indice);
            
            Pokemon copia = new Pokemon(
                original.getId() + 100,
                original.getNome() + "_maquina",
                original.getAtaque(),
                0,
                100,
                original.getTipo(),
                original.getSprite()
            );
            copia.setHp(100);
            pokemonsSelecionados.add(copia);
        }
        return pokemonsSelecionados;
    }

    private void inicializarHPs() {
        for (Pokemon pokemon : pokemonsJogador) {
            pokemon.setHp(100);
        }
        for (Pokemon pokemon : pokemonsMaquina) {
            pokemon.setHp(100);
        }
    }

    private int calcularEAplicarDano(Pokemon atacante, Pokemon defensor) {
        int dano = PokemonFactory.calcularDano(atacante.getTipo(), defensor.getTipo());
        int novoHp = defensor.getHp() - dano;
        if (novoHp < 0) {
            novoHp = 0;
        }
        defensor.setHp(novoHp);
        return dano;
    }

    public String jogadorAtaca() {
        if (batalhaTerminada) {
            return "A batalha já terminou!";
        }

        if (!pokemonAtualJogador.estaVivo()) {
            return "Seu pokémon está morto! Troque de pokémon!";
        }

        int dano = calcularEAplicarDano(pokemonAtualJogador, pokemonAtualMaquina);
        String mensagem = pokemonAtualJogador.getNome() + " atacou " + 
                         pokemonAtualMaquina.getNome() + " causando " + dano + " de dano!";
        
        if (PokemonFactory.temFraqueza(pokemonAtualJogador.getTipo(), pokemonAtualMaquina.getTipo())) {
            mensagem += " (É super efetivo!)";
        }
        
        if (!pokemonAtualMaquina.estaVivo()) {
            mensagem += "\n" + pokemonAtualMaquina.getNome() + " foi derrotado!";
            
            if (temPokemonVivo(pokemonsMaquina)) {
                trocarPokemonMaquina();
                mensagem += "\nA máquina trocou para " + pokemonAtualMaquina.getNome() + "!";
            } else {
                batalhaTerminada = true;
                vencedor = "JOGADOR";
                mensagem += "\nVocê venceu a batalha!";
            }
        }
        
        if (!batalhaTerminada && pokemonAtualJogador.estaVivo()) {
            mensagem += "\n" + maquinaAtaca();
        }
        
        return mensagem;
    }

    private String maquinaAtaca() {
        if (!pokemonAtualMaquina.estaVivo()) {
            return "";
        }

        int dano = calcularEAplicarDano(pokemonAtualMaquina, pokemonAtualJogador);
        String mensagem = pokemonAtualMaquina.getNome() + " atacou " + 
                         pokemonAtualJogador.getNome() + " causando " + dano + " de dano!";
        
        if (PokemonFactory.temFraqueza(pokemonAtualMaquina.getTipo(), pokemonAtualJogador.getTipo())) {
            mensagem += " (É super efetivo!)";
        }
        
        if (!pokemonAtualJogador.estaVivo()) {
            mensagem += "\n" + pokemonAtualJogador.getNome() + " foi derrotado!";
            
            if (!temPokemonVivo(pokemonsJogador)) {
                batalhaTerminada = true;
                vencedor = "MAQUINA";
                mensagem += "\nVocê perdeu a batalha!";
            }
        }
        
        return mensagem;
    }

    public boolean jogadorTrocaPokemon(int indice) {
        if (batalhaTerminada) {
            return false;
        }

        if (indice < 0 || indice >= pokemonsJogador.size()) {
            return false;
        }

        Pokemon novoPokemon = pokemonsJogador.get(indice);
        
        if (!novoPokemon.estaVivo()) {
            return false;
        }

        if (indice == indicePokemonAtualJogador) {
            return false;
        }

        this.indicePokemonAtualJogador = indice;
        this.pokemonAtualJogador = novoPokemon;
        
        if (!batalhaTerminada && pokemonAtualMaquina.estaVivo()) {
            maquinaAtaca();
        }
        
        return true;
    }

    private void trocarPokemonMaquina() {
        for (int i = 0; i < pokemonsMaquina.size(); i++) {
            if (pokemonsMaquina.get(i).estaVivo() && i != indicePokemonAtualMaquina) {
                this.indicePokemonAtualMaquina = i;
                this.pokemonAtualMaquina = pokemonsMaquina.get(i);
                return;
            }
        }
    }

    private boolean temPokemonVivo(List<Pokemon> pokemons) {
        for (Pokemon pokemon : pokemons) {
            if (pokemon.estaVivo()) {
                return true;
            }
        }
        return false;
    }

    public Pokemon getPokemonAtualJogador() {
        return pokemonAtualJogador;
    }

    public Pokemon getPokemonAtualMaquina() {
        return pokemonAtualMaquina;
    }

    public List<Pokemon> getPokemonsJogador() {
        return new ArrayList<>(pokemonsJogador);
    }

    public List<Pokemon> getPokemonsMaquina() {
        return new ArrayList<>(pokemonsMaquina);
    }

    public boolean isBatalhaTerminada() {
        return batalhaTerminada;
    }

    public String getVencedor() {
        return vencedor;
    }

    public int getIndicePokemonAtualJogador() {
        return indicePokemonAtualJogador;
    }

    public List<Pokemon> getPokemonsVivosJogador() {
        List<Pokemon> vivos = new ArrayList<>();
        for (Pokemon pokemon : pokemonsJogador) {
            if (pokemon.estaVivo()) {
                vivos.add(pokemon);
            }
        }
        return vivos;
    }
}
