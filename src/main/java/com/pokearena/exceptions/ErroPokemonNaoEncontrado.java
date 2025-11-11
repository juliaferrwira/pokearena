package com.pokearena.exceptions;

public class ErroPokemonNaoEncontrado extends Exception {
    
    public ErroPokemonNaoEncontrado(String mensagem) {
        super(mensagem);
    }
}