package com.pokearena.model;
import javafx.scene.image.Image;

public class Insignia {
    private int id;
    private String nome;
    private String ginasio;
    private String atualDetentor;
    private String descricao;
    private Image insigniaImage;

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

    public Image getInsigniaImage() {
        return insigniaImage;
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

    public void setInsigniaImage(Image insigniaImage) {
        this.insigniaImage = insigniaImage;
    }

    public void fazerDescricaoInsignia(){
        this.descricao = "A "+this.nome+", concedida pelo Ginásio de "+this.ginasio+" após vencer o líder "+this.atualDetentor+","+
                "é mais que um troféu:\n                é a prova de superação e orgulho para todo treinador que busca se tornar o melhor";
    }
    public void fazerDescricaoInsigniaAsh(){
        this.descricao = "          O "+this.nome+", concedido após vencer o líder "+this.atualDetentor+","+
                " é mais que um troféu:\n é a prova de superação e orgulho para todo treinador que busca se tornar o melhor";
    }

    public void mudarAtualDetentor(String novoDetentor,Insignia I){
        if (!I.atualDetentor.equals(novoDetentor)) I.atualDetentor = novoDetentor;
    };
}

