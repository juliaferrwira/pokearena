package com.pokearena.model;

public abstract class Liga {
    private String regiao;
    private List<Treinador> treinadores;
    private List<Insignia> insignias;

    public Liga(String regiao){
        this.regiao=regiao;
        this.treinadores=new ArrayList<>();
        this.insignias=new ArrayList<>();
    }


}

