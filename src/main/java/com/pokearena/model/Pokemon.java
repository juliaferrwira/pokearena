package com.pokearena.model;

public class Pokemon extends Personagem {
    private String tipo;
    private String sprite;
    private int id; // ID do Pokémon na PokeAPI

    public Pokemon(String nome, int ataque, int defesa, int hp, String tipo, String sprite) {
        super(nome, ataque, defesa, hp);
        this.tipo = tipo;
        this.sprite = sprite;
    }

    public Pokemon(int id, String nome, int ataque, int defesa, int hp, String tipo, String sprite) {
        super(nome, ataque, defesa, hp);
        this.id = id;
        this.tipo = tipo;
        this.sprite = sprite;
    }

    @Override
    public String acaoEspecial() {
        if (tipo.equalsIgnoreCase("fire") || tipo.equalsIgnoreCase("fogo")) {
            return nome + " usa poderpoder de fogo!";
        } else if (tipo.equalsIgnoreCase("water") || tipo.equalsIgnoreCase("água") || tipo.equalsIgnoreCase("agua")) {
            return nome + " usa poderpoder de água!";
        } else if (tipo.equalsIgnoreCase("electric") || tipo.equalsIgnoreCase("elétrico")
                || tipo.equalsIgnoreCase("eletrico")) {
            return nome + " usa poderpoder de trovão!";
        } else if (tipo.equalsIgnoreCase("grass") || tipo.equalsIgnoreCase("grama")) {
            return nome + " usa poderpoder de grama!";
        } else {
            return nome + " usa Ataque Especial!";
        }
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", ataque=" + ataque +
                ", defesa=" + defesa +
                ", hp=" + hp + "/" + hpMaximo +
                '}';
    }
}