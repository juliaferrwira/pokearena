package com.pokearena.service;

import com.pokearena.model.LigaKanto;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.awt.desktop.ScreenSleepEvent;
import java.util.Objects;

public class ScreenService {
    //Variáveis Gerais
    private static final double FatorEscala = 1.1;
    private static final int DuracaoMS = 150;
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();
    public int screenWidth = screenSize.width;
    public int screenHeight = screenSize.height;
    public final double offScreenDistance = ((double) screenWidth /2);
    private final Image fullPokeballs;
    private final Image twoPokeballs;
    private final Image onePokeballs;
    private final Image zeroPokeballs;
    private final Image fullPokeballsInverted;
    private final Image twoPokeballsInverted;
    private final Image onePokeballsInverted;
    private final Image zeroPokeballsInverted;
    private final Image cardBrock;
    private final Image cardMisty;
    private final Image cardAsh;
    private final Image cardIcaro;
    private final Image cardJulia;

    // String que guarda o css da barra de hp
    public String cssStyles = """
        .hp-bar-default > .track {
            -fx-control-inner-background: #444444;
            -fx-background-radius: 5;
        }
        .hp-full > .bar {
            -fx-background-color: linear-gradient(to bottom, #8BC34A, #4CAF50); /* Tons de Verde Maçã */
            -fx-background-radius: 5;
            -fx-padding: 3px;
        }
        .hp-medium > .bar {
            -fx-background-color: linear-gradient(to bottom, #FFCA28, #FF9800); /* Tons de Âmbar */
            -fx-background-radius: 5;
            -fx-padding: 3px;
        }
        .hp-low > .bar {
            -fx-background-color: linear-gradient(to bottom, #E53935, #C62828); /* Tons de Vermelho Escarlate */
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
    public void configHpBar(ProgressBar hpbar){
        hpbar.setPrefWidth(300);
        hpbar.setPrefHeight(30);
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

    public SequentialTransition atacar(Node iv){
        TranslateTransition diagonal = new TranslateTransition(Duration.seconds(1.0),iv);
        diagonal.setFromX(0);
        diagonal.setFromY(0);
        diagonal.setToX(50);
        diagonal.setToY(50);

        TranslateTransition voltar = new TranslateTransition(Duration.seconds(1.0));
        voltar.setToX(0);
        voltar.setToY(0);

        return new SequentialTransition(diagonal,voltar);
    }

    public SequentialTransition tomarDano(Node iv){
        TranslateTransition direita = new TranslateTransition(Duration.seconds(1.0),iv);
        direita.setFromX(0);
        direita.setToX(50);

        TranslateTransition esquerda = new TranslateTransition(Duration.seconds(1.0),iv);
        esquerda.setFromX(50);
        esquerda.setToX(-50);

        TranslateTransition inicial = new TranslateTransition(Duration.seconds(1.0),iv);
        inicial.setFromX(-50);
        inicial.setToX(0);

        return new SequentialTransition(direita,esquerda,inicial);
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

        return new SequentialTransition(fadeOut,fadeIn);
    }

    public void changePokeballNumsPlayer(ImageView iv,int pokemons){
        if (pokemons == 3){
            criarTransicaoPokebolas(iv).play();
            Platform.runLater(()-> iv.setImage(fullPokeballs));
        } else if (pokemons == 2) {
            criarTransicaoPokebolas(iv).play();
            Platform.runLater(()-> iv.setImage(twoPokeballs));
        } else if (pokemons == 1) {
            criarTransicaoPokebolas(iv).play();
            Platform.runLater(()->iv.setImage(onePokeballs));
        } else {
            criarTransicaoPokebolas(iv).play();
            Platform.runLater(()->iv.setImage(zeroPokeballs));
        }
    }
    public void changePokeballNumsMaquina(ImageView iv,int pokemons){
        if (pokemons == 3){
            criarTransicaoPokebolas(iv).play();
            Platform.runLater(()->iv.setImage(fullPokeballsInverted));
        } else if (pokemons == 2) {
            criarTransicaoPokebolas(iv).play();
            Platform.runLater(()->iv.setImage(twoPokeballsInverted));
        } else if (pokemons == 1) {
            criarTransicaoPokebolas(iv).play();
            Platform.runLater(()->iv.setImage(onePokeballsInverted));
        } else {
            criarTransicaoPokebolas(iv).play();
            Platform.runLater(()->iv.setImage(zeroPokeballsInverted));
        }
    }
    public void iniciateBattle(int whatBattle,ImageView cardMaquina,ImageView cardPlayer,int whatCard){
        if (whatBattle == 1) {
            cardMaquina.setImage(cardBrock);
        } else if (whatBattle == 2) {
            cardMaquina.setImage(cardMisty);
        } else if (whatBattle == 3) {
            cardMaquina.setImage(cardAsh);
        }

        if (whatCard == 1){
            cardPlayer.setImage(cardIcaro);
        }
        else if (whatCard == 2) {
           cardPlayer.setImage(cardJulia);
        }
    }
    public void putPokemonImg(Button btn, int pokemonId, String pokemonNome, boolean selecionado){
        String sufixo = selecionado ? "Red" : "";
        String imagePath = "/srcPokearena/selecaoPokemons/pokemonsParaSelecao/" + pokemonId + "-" + pokemonNome + sufixo + ".png";

        java.io.InputStream stream = getClass().getResourceAsStream(imagePath);
        if (stream == null) {
            return; // se não encontrar a imagem, não faz nada
        }

        Image image = new Image(stream);
        ImageView iv = new ImageView(image);
        iv.setFitWidth(200);
        iv.setFitHeight(200);
        iv.setPreserveRatio(true);
        btn.setGraphic(iv);
    }
    public void putPokemonImgBattle(ImageView iv,String pokemonNome, boolean trainer){
        String sufixo = trainer ? "Back1" : "";
        String num = trainer ? "" : "1";
        String imagePath = "/srcPokearena/spritesPokemons/" + pokemonNome + "/" + pokemonNome+ num + sufixo + ".png";

        java.io.InputStream stream = getClass().getResourceAsStream(imagePath);
        if (stream == null) {
            return; // se não encontrar a imagem, não faz nada
        }

        Image image = new Image(stream);
        iv.setImage(image);
        iv.setFitWidth(200);
        iv.setFitHeight(200);
        iv.setPreserveRatio(true);
    }
    public Image updateLabelInfo(int whatBattle){
        if (whatBattle == 1) {
            return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/batalha1.png")));
        } else if (whatBattle == 2) {
            return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/batalha2.png")));
        } else if (whatBattle == 3) {
            return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/batalha3.png")));
        }
        return null;
    }
    public ScreenService(){
        fullPokeballs = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/fullPokebolas.png")));
        twoPokeballs = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/2Pokebolas.png")));
        onePokeballs = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/1Pokebola.png")));
        zeroPokeballs = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/0Pokebolas.png")));
        fullPokeballsInverted = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/fullPokebolasInvertido.png")));
        twoPokeballsInverted = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/2PokebolasInvertido.png")));
        onePokeballsInverted = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/1PokebolasInvertido.png")));
        zeroPokeballsInverted = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/0PokebolasInvertido.png")));
        cardBrock = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/iconBrock.png")));
        cardMisty = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/iconMisty.png")));
        cardAsh = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/iconAsh.png")));
        cardIcaro = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/iconIcaro.png")));
        cardJulia = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/iconJulia.png")));
    }
}