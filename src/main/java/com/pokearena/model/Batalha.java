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
}