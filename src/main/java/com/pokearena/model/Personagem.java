package com.pokearena.model;

public abstract class Personagem {
    protected String nome;
    protected int ataque;
    protected int defesa;
    protected int hp;
    protected int hpMaximo;

    public Personagem(String nome, int ataque, int defesa, int hp) {
        this.nome = nome;
        this.ataque = ataque;
        this.defesa = defesa;
        this.hp = hp;
        this.hpMaximo = hp;
    }

    public abstract String acaoEspecial();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp < 0) {
            this.hp = 0;
        } else if (hp > hpMaximo) {
            this.hp = hpMaximo;
        } else {
            this.hp = hp;
        }
    }

    public int getHpMaximo() {
        return hpMaximo;
    }

    public boolean estaVivo() {
        return hp > 0;
    }

    public void receberDano(int dano) {
        int danoReal = dano - defesa;
        if (danoReal < 1) {
            danoReal = 1;
        }
        this.hp = this.hp - danoReal;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }
}