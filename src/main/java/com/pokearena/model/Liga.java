package com.pokearena.model;

import javafx.scene.Scene;

import java.util.ArrayList;

public abstract class Liga {
    public String regiao;
    public ArrayList<Scene> battles;

    public Liga(String regiao){
        this.regiao=regiao;
        this.battles=new ArrayList<>();
    }

    public abstract String mostrarInformacoesLiga();


}

