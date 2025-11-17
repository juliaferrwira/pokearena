package com.pokearena.model;

import java.util.ArrayList;

public abstract class Liga {
    private String regiao;
    private ArrayList<Treinador> treinadores;
    private ArrayList<Insignia> insignias;

    public Liga(String regiao){
        this.regiao=regiao;
        this.treinadores=new ArrayList<>();
        this.insignias=new ArrayList<>();
    }

    public abstract String mostrarInformacoesLiga();


}

