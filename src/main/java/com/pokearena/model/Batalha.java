package com.pokearena.model;

import java.time.LocalDateTime;

public class Batalha {
    private int id;
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private Pokemon vencedor;
    private LocalDateTime data;

    public Batalha(Pokemon pokemon1, Pokemon pokemon2) {
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;
        this.data = LocalDateTime.now();
    }

    public Batalha(int id, Pokemon pokemon1, Pokemon pokemon2, Pokemon vencedor, LocalDateTime data) {
        this.id = id;
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;
        this.vencedor = vencedor;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pokemon getPokemon1() {
        return pokemon1;
    }

    public void setPokemon1(Pokemon pokemon1) {
        this.pokemon1 = pokemon1;
    }

    public Pokemon getPokemon2() {
        return pokemon2;
    }

    public void setPokemon2(Pokemon pokemon2) {
        this.pokemon2 = pokemon2;
    }

    public Pokemon getVencedor() {
        return vencedor;
    }

    public void setVencedor(Pokemon vencedor) {
        this.vencedor = vencedor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Batalha{" +
                "id=" + id +
                ", pokemon1=" + (pokemon1 != null ? pokemon1.getNome() : "null") +
                ", pokemon2=" + (pokemon2 != null ? pokemon2.getNome() : "null") +
                ", vencedor=" + (vencedor != null ? vencedor.getNome() : "null") +
                ", data=" + data +
                '}';
    }

    public static Batalha iniciarBatalha(Pokemon p1, Pokemon p2) {
        // Criar cópias para não alterar os originais!
        Pokemon pokemon1 = new Pokemon(p1.getId(), p1.getNome(), p1.getAtaque(), 
                                       p1.getDefesa(), p1.getHpMaximo(), p1.getTipo(), p1.getSprite());
        Pokemon pokemon2 = new Pokemon(p2.getId(), p2.getNome(), p2.getAtaque(), 
                                       p2.getDefesa(), p2.getHpMaximo(), p2.getTipo(), p2.getSprite());
        
        Batalha batalha = new Batalha(pokemon1, pokemon2);
        
        System.out.println("\n=== BATALHA: " + pokemon1.getNome() + " vs " + pokemon2.getNome() + " ===\n");
        
        int turno = 1;
        boolean p1Ataca = true;
        
        while (pokemon1.estaVivo() && pokemon2.estaVivo()) {
            System.out.println("Turno " + turno + ":");
            
            if (p1Ataca) {
                int dano = pokemon1.getAtaque() - (pokemon2.getDefesa() / 2);
                if (dano < 1) dano = 1;
                pokemon2.receberDano(dano);
                System.out.println(pokemon1.getNome() + " ataca! Dano: " + dano);
                System.out.println(pokemon2.getNome() + " HP: " + pokemon2.getHp() + "/" + pokemon2.getHpMaximo());
            } else {
                int dano = pokemon2.getAtaque() - (pokemon1.getDefesa() / 2);
                if (dano < 1) dano = 1;
                pokemon1.receberDano(dano);
                System.out.println(pokemon2.getNome() + " ataca! Dano: " + dano);
                System.out.println(pokemon1.getNome() + " HP: " + pokemon1.getHp() + "/" + pokemon1.getHpMaximo());
            }
            
            p1Ataca = !p1Ataca;
            turno++;
            System.out.println();
        }
        
        Pokemon vencedor = pokemon1.estaVivo() ? pokemon1 : pokemon2;
        batalha.setVencedor(vencedor);
        
        System.out.println("VENCEDOR: " + vencedor.getNome() + "!\n");
        
        return batalha;
    }
}