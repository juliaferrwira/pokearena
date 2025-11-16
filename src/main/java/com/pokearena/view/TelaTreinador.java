package com.pokearena.view;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class TelaTreinador {
    private static final double FatorEscala = 1.1;
    private static final int DuracaoMS = 150;
    private static final String btnStyle = "-fx-padding: 0; -fx-background-color: transparent;" +
                                           "-fx-min-width: 200px; -fx-min-height: 100px;";

    public static String WallpaperSelectTrainer = "-fx-background-image: url('srcPokearena/wallpaper1.jpg'); " +
                                                  "-fx-background-size: cover; " + "-fx-background-position: center center; " +
                                                  "-fx-background-repeat: no-repeat;";

    public static void animacaoHover(Button botao){
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

    public static void configBtn(Button btn){
        btn.setScaleX(1.0);
        btn.setScaleY(1.0);
        btn.setStyle(btnStyle);
    }

    public void putCard1Img(Button btn){
        Image image = new Image(getClass().getResourceAsStream("/srcPokearena/selecaoTreinador/treinadoresParaSelecao/cardIcaro.png"));
        ImageView iv = new ImageView(image);
        iv.setFitWidth(415);
        iv.setFitHeight(730);
        iv.setPreserveRatio(true);
        btn.setGraphic(iv);
    }
    public void putCard2Img(Button btn){
        Image image = new Image(getClass().getResourceAsStream("/srcPokearena/selecaoTreinador/treinadoresParaSelecao/cardJulia.png"));
        ImageView iv = new ImageView(image);
        iv.setFitWidth(415);
        iv.setFitHeight(730);
        iv.setPreserveRatio(true);
        btn.setGraphic(iv);
    }

    public BorderPane criarRootTreinador(){
        Image selectTrainer = new Image(getClass().getResourceAsStream("/srcPokearena/selecaoTreinador/selecioneTreinador.png"));
        ImageView iv = new ImageView(selectTrainer);
        iv.setFitWidth(800);
        iv.setFitHeight(180);
        iv.setPreserveRatio(true);

        Button cardTrainer1 = new Button();
        cardTrainer1.setId("cardTrainer1");
        Button cardTrainer2 = new Button();
        cardTrainer2.setId("cardTrainer2");

        configBtn(cardTrainer1);
        configBtn(cardTrainer2);
        putCard1Img(cardTrainer1);
        putCard2Img(cardTrainer2);
        animacaoHover(cardTrainer1);
        animacaoHover(cardTrainer2);

        HBox selectTrainerBox = new HBox(50);
        selectTrainerBox.getChildren().addAll(cardTrainer1,cardTrainer2);
        selectTrainerBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(iv);
        BorderPane.setAlignment(iv,Pos.CENTER);
        BorderPane.setMargin(iv,new Insets(10,15,15,15));
        root.setCenter(selectTrainerBox);
        root.setStyle(WallpaperSelectTrainer);

        return root;
    }

    public TelaTreinador(){
    }
}
