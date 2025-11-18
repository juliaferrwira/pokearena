package com.pokearena.model;

public class LigaKanto extends Liga {
    public LigaKanto(String regiao){
        super(regiao);
    }
    @Override
    public String mostrarInformacoesLiga() {
        return "Liga Pokémon de Kanto\n" +
                "A Liga Pokémon de Kanto é o objetivo final de todo Treinador.\n" +
                "\n" +
                "Para competir, você deve primeiro derrotar os três Líderes de Ginásio da região e conquistar as suas Insígnias de mérito:\n" +
                "\n" + "1.Brock\n" + "2.Misty\n" + "3.Ash\n" +
                "Ao provar sua força com todas as insígnias, o Treinador se torna o Campeão de Kanto.";
    }
}
