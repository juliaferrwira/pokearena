package com.pokearena.service;

import com.pokearena.model.Pokemon;
import java.util.ArrayList;
import java.util.List;

public class BatalhaService {
    private List<Pokemon> pokemonsJogador;
    private List<Pokemon> pokemonsMaquina;
    private Pokemon pokemonAtualJogador;
    private Pokemon pokemonAtualMaquina;
    private int indicePokemonAtualJogador;
    public int indicePokemonAtualMaquina;
    private boolean batalhaTerminada;
    private String vencedor;
    private int numeroBatalha;
    private String nomeMaquina;
    private boolean jogoTerminado;
    public int trocasMaquina;
    public int trocasPlayer;

    public BatalhaService(List<Pokemon> pokemonsJogador, int numeroBatalha) {
        this.pokemonsJogador = criarCopiasPokemons(pokemonsJogador);
        this.numeroBatalha = numeroBatalha;
        this.pokemonsMaquina = criarTimeMaquina(numeroBatalha);
        this.batalhaTerminada = false;
        this.jogoTerminado = false;
        
        inicializarHPs();
        
        this.indicePokemonAtualJogador = 0;
        this.indicePokemonAtualMaquina = 0;
        this.pokemonAtualJogador = this.pokemonsJogador.get(0);
        this.pokemonAtualMaquina = this.pokemonsMaquina.get(0);
        this.trocasMaquina = 0;
        this.trocasPlayer = 0;
    }

    private List<Pokemon> criarCopiasPokemons(List<Pokemon> originais) {
        List<Pokemon> copias = new ArrayList<>();
        for (Pokemon original : originais) {
            Pokemon copia = new Pokemon(
                original.getId(),
                original.getNome(),
                0,
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

    private List<Pokemon> criarTimeMaquina(int numeroBatalha) {
        List<Pokemon> time = new ArrayList<>();
        
        if (numeroBatalha == 1) {
            nomeMaquina = "Brock";
            time.add(criarCopiaPokemon(PokemonFactory.criarPokemonPorId(9)));
            time.add(criarCopiaPokemon(PokemonFactory.criarPokemonPorId(7)));
            time.add(criarCopiaPokemon(PokemonFactory.criarPokemonPorId(8)));
        } else if (numeroBatalha == 2) {
            nomeMaquina = "Misty";
            time.add(criarCopiaPokemon(PokemonFactory.criarPokemonPorId(3)));
            time.add(criarCopiaPokemon(PokemonFactory.criarPokemonPorId(6)));
            time.add(criarCopiaPokemon(PokemonFactory.criarPokemonPorId(4)));
        } else if (numeroBatalha == 3) {
            nomeMaquina = "Ash";
            time.add(criarCopiaPokemon(PokemonFactory.criarPokemonPorId(4)));
            time.add(criarCopiaPokemon(PokemonFactory.criarPokemonPorId(2)));
            time.add(criarCopiaPokemon(PokemonFactory.criarPokemonPorId(1)));
        }
        
        return time;
    }

    private Pokemon criarCopiaPokemon(Pokemon original) {
        if (original == null) {
            return null;
        }
        Pokemon copia = new Pokemon(
            original.getId() + 100,
            original.getNome() + "_" + nomeMaquina,
            0,
            0,
            100,
            original.getTipo(),
            original.getSprite()
        );
        copia.setHp(100);
        return copia;
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
        if (batalhaTerminada || jogoTerminado) {
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
                trocasMaquina++;
                trocarPokemonMaquina();
                mensagem += "\n" + nomeMaquina + " trocou para " + pokemonAtualMaquina.getNome() + "!";
            } else {
                batalhaTerminada = true;
                vencedor = "JOGADOR";
                mensagem += "\nVocê venceu " + nomeMaquina + "!";
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
                jogoTerminado = true;
                vencedor = "MAQUINA";
                mensagem += "\nVocê perdeu para " + nomeMaquina + "! O jogo acabou!";
            }
        }
        
        return mensagem;
    }

    public String jogadorTrocaPokemon(int indice) {
        if (batalhaTerminada || jogoTerminado) {
            return null;
        }

        if (indice < 0 || indice >= pokemonsJogador.size()) {
            return null;
        }

        Pokemon novoPokemon = pokemonsJogador.get(indice);
        
        if (!novoPokemon.estaVivo()) {
            return null;
        }

        if (indice == indicePokemonAtualJogador) {
            return null;
        }

        this.indicePokemonAtualJogador = indice;
        this.pokemonAtualJogador = novoPokemon;
        
        String mensagem = "Você trocou para " + pokemonAtualJogador.getNome() + "!";
        
        return mensagem;
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

    public boolean isJogoTerminado() {
        return jogoTerminado;
    }

    public String getVencedor() {
        return vencedor;
    }

    public int getIndicePokemonAtualJogador() {
        return indicePokemonAtualJogador;
    }

    public int getNumeroBatalha() {
        return numeroBatalha;
    }

    public String getNomeMaquina() {
        return nomeMaquina;
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

    public List<Pokemon> getPokemonsDisponiveisParaTroca() {
        List<Pokemon> disponiveis = new ArrayList<>();
        for (int i = 0; i < pokemonsJogador.size(); i++) {
            if (i != indicePokemonAtualJogador && pokemonsJogador.get(i).estaVivo()) {
                disponiveis.add(pokemonsJogador.get(i));
            }
        }
        return disponiveis;
    }

}
