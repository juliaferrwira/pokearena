package com.pokearena.service;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;

public class ScreenService {
    //Variáveis Gerais
    private static final double FatorEscala = 1.1;
    private static final int DuracaoMS = 150;
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();
    public int screenWidth = screenSize.width;
    public int screenHeight = screenSize.height;
    public final double offScreenDistance = ((double) screenWidth /2);

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

    public SequentialTransition animarLabels(Label label, double offScreenLeft, double offScreenRight){
        TranslateTransition entrar = new TranslateTransition(Duration.seconds(1.0),label);
        entrar.setFromX(-offScreenLeft);
        entrar.setToX(0);

        PauseTransition pausa = new PauseTransition(Duration.seconds(2.0));

        TranslateTransition sair = new TranslateTransition(Duration.seconds(1.0),label);
        sair.setToX(offScreenRight);

        return new SequentialTransition(entrar,pausa,sair);
    }

    // Funções pra trocar as telas
    public void changeScene(Stage stage, BorderPane root, double currentWidth, double currentHeight){
        Scene newScene = new Scene(root,currentWidth,currentHeight);
        stage.setScene(newScene);
    }
    public void changeScene(Stage stage,Scene scene){
        stage.setMaximized(true);
        stage.setScene(scene);
    }

    public ScreenService(){}
}
