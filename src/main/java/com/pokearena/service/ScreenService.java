package com.pokearena.service;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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

    // String que guarda o css da barra de hp
    public String cssStyles = """
        .hp-bar-default > .track {
            -fx-control-inner-background: #444444; /* Cor escura do fundo da barra */
            -fx-background-radius: 5;
        }
        .hp-full > .bar {
            -fx-background-color: linear-gradient(to bottom, #7FFF00, #4c9600);
            -fx-background-radius: 5;
            -fx-padding: 3px;
        }
        .hp-medium > .bar {
            -fx-background-color: linear-gradient(to bottom, #FFD700, #ff8c00);
            -fx-background-radius: 5;
            -fx-padding: 3px;
        }
        .hp-low > .bar {
            -fx-background-color: linear-gradient(to bottom, #FF6347, #b22222);
            -fx-background-radius: 5;
            -fx-padding: 3px;
        }
        """;

    // String que funciona como se fosse o arquivo do Style.css
    public String dataUrl = "data:text/css," + cssStyles.replaceAll("\n", "").replaceAll("\"", "'");

    //Insignias Service
    private static final String insiStyle = "-fx-padding: 0; -fx-background-color: transparent;";

    public void configInsi(ImageView insignia){
        insignia.setScaleX(1.0);
        insignia.setScaleY(1.0);
        insignia.setStyle(insiStyle);
    }

    public void animacaoHover(ImageView iv){
        ScaleTransition crescer = new ScaleTransition(Duration.millis(DuracaoMS),iv);
        crescer.setToX(FatorEscala);
        crescer.setToY(FatorEscala);
        ScaleTransition diminuir = new ScaleTransition(Duration.millis(DuracaoMS),iv);
        diminuir.setToX(1.0);
        diminuir.setToY(1.0);

        iv.setOnMouseEntered(e->{
            diminuir.stop();
            crescer.playFromStart();
        });
        iv.setOnMouseExited(e->{
            crescer.stop();
            diminuir.playFromStart();
        });
    }
    public void configImageView(ImageView iv){
        iv.setFitWidth(800);
        iv.setFitHeight(180);
        iv.setPreserveRatio(true);
    }
    public void configRemainingPokemons(ImageView iv){
        iv.setFitWidth(600);
        iv.setFitHeight(140);
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
    //***********************************************************************************************

    public SequentialTransition animarLabels(Node iv, double offScreenLeft, double offScreenRight){
        TranslateTransition entrar = new TranslateTransition(Duration.seconds(1.0),iv);
        entrar.setFromX(-offScreenLeft);
        entrar.setToX(0);

        PauseTransition pausa = new PauseTransition(Duration.seconds(2.0));

        TranslateTransition sair = new TranslateTransition(Duration.seconds(1.0),iv);
        sair.setToX(offScreenRight);

        return new SequentialTransition(entrar,pausa,sair);
    }

    // Funções pra trocar as telas
    public void createNewScene(Stage stage, BorderPane root, double currentWidth, double currentHeight){
        Scene newScene = new Scene(root,currentWidth,currentHeight);
        stage.setScene(newScene);
    }
    public void changeScene(Stage stage,Scene scene){
        stage.setMaximized(true);
        stage.setScene(scene);
    }
    //***********************************************************************************************

    // Função para atualizar a barra de HP do pokemon
    public void atualizarBarraHP(ProgressBar hpBar, double hpAtual, double hpMax) {
        double progress = hpAtual / hpMax;
        hpBar.setProgress(progress);
        hpBar.getStyleClass().removeAll("hp-full", "hp-medium", "hp-low","hp-default");
        if (progress > 0.5) {
            hpBar.getStyleClass().add("hp-full"); // Verde (50% - 100%)
        } else if (progress > 0.2) {
            hpBar.getStyleClass().add("hp-medium"); // Amarelo (20% - 50%)
        } else {
            hpBar.getStyleClass().add("hp-low"); // Vermelho (0% - 20%)
        }
    }

    // Função de animação para as pokebolas
    public SequentialTransition criarTransicaoPokebolas(Node elemento) {

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.0), elemento);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.0), elemento);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        SequentialTransition sequencia = new SequentialTransition(fadeOut,fadeIn);

        return sequencia;
    }
    public ScreenService(){}
}