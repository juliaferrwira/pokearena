package com.pokearena.model;

public class Insignia {
    private int id;
    private String nome;
    private String ginasio;
    private String atualDetentor;
    private String descricao;

    public Insignia(int id,String nome,String ginasio,String atualDetentor) {
        this.id = id;
        this.nome = nome;
        this.ginasio = ginasio;
        this.atualDetentor = atualDetentor;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getGinasio() {
        return ginasio;
    }

    public String getAtualDetentor() {
        return atualDetentor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGinasio(String ginasio) {
        this.ginasio = ginasio;
    }

    public void setAtualDetentor(String atualDetentor) {
        this.atualDetentor = atualDetentor;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void fazerDescricaoInsignia(String nome, String ginasio, String atualDetentor){
        this.descricao = "A "+nome+", concedida pelo Ginásio de "+ginasio+"após vencer o líder "+atualDetentor+","+
                " é mais que um troféu: é a prova de superação e orgulho para todo treinador que busca se tornar o melhor";
    }

    public void mudarAtualDetentor(String novoDetentor,Insignia I){
        if (!I.atualDetentor.equals(novoDetentor)) I.atualDetentor = novoDetentor;
    };
}

