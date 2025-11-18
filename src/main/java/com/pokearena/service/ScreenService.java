package com.pokearena.service;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ScreenService {
    //Variáveis Gerais
    private static final double FatorEscala = 1.1;
    private static final int DuracaoMS = 150;


    //Insignias Service
    private static final String insiStyle = "-fx-padding: 0; -fx-background-color: transparent;";

    public void configInsi(ImageView insignia){
        insignia.setScaleX(1.0);
        insignia.setScaleY(1.0);
        insignia.setStyle(insiStyle);
    }

    public void animacaoHover(ImageView insignia){
        ScaleTransition crescer = new ScaleTransition(Duration.millis(DuracaoMS),insignia);
        crescer.setToX(FatorEscala);
        crescer.setToY(FatorEscala);
        ScaleTransition diminuir = new ScaleTransition(Duration.millis(DuracaoMS),insignia);
        diminuir.setToX(1.0);
        diminuir.setToY(1.0);

        insignia.setOnMouseEntered(e->{
            diminuir.stop();
            crescer.playFromStart();
        });
        insignia.setOnMouseExited(e->{
            crescer.stop();
            diminuir.playFromStart();
        });
    }
    public void configImageView(ImageView iv){
        iv.setFitWidth(800);
        iv.setFitHeight(180);
        iv.setPreserveRatio(true);
    }
    public TranslateTransition criarAnimacaoPosicao(ImageView insignia, double yTarget) {
        javafx.animation.TranslateTransition tt = new javafx.animation.TranslateTransition(Duration.millis(DuracaoMS), insignia);
        tt.setToY(yTarget);
        return tt;
    }
    //***********************************************************************************************

    // Todos os botões
    private static final String btnStyle = "-fx-padding: 0; -fx-background-color: transparent;" +
            "-fx-min-width: 200px; -fx-min-height: 100px;";

    public void animacaoHover(Button botao){
        ScaleTransition crescer = new ScaleTransition(Duration.millis(DuracaoMS),botao);
        crescer.setToX(FatorEscala);
        crescer.setToY(FatorEscala);
        ScaleTransition diminuir = new ScaleTransition(Duration.millis(DuracaoMS),botao);
        diminuir.setToX(1.0);
        diminuir.setToY(1.0);

        botao.setOnMouseEntered(e->{
            diminuir.stop();
            crescer.playFromStart();
        });
        botao.setOnMouseExited(e->{
            crescer.stop();
            diminuir.playFromStart();
        });
    }

    public void configBtn(Button btn){
        btn.setScaleX(1.0);
        btn.setScaleY(1.0);
        btn.setStyle(btnStyle);
    }

    // Função pra trocar as telas
    public void changeScene(Stage stage, BorderPane root, double currentWidth, double currentHeight){
        Scene newScene = new Scene(root,currentWidth,currentHeight);
        stage.setScene(newScene);
    }
    public ScreenService(){}
}
