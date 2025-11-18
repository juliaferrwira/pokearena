package com.pokearena.view;
import com.pokearena.service.ScreenService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

import java.util.Objects;

public class TelaTreinador {

    public static String WallpaperSelectTrainer = "-fx-background-image: url('srcPokearena/wallpaper1.jpg'); " +
                                                  "-fx-background-size: cover; " + "-fx-background-position: center center; " +
                                                  "-fx-background-repeat: no-repeat;";

    public void putCard1Img(Button btn){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/selecaoTreinador/treinadoresParaSelecao/cardIcaro.png")));
        ImageView iv = new ImageView(image);
        iv.setFitWidth(415);
        iv.setFitHeight(730);
        iv.setPreserveRatio(true);
        btn.setGraphic(iv);
    }
    public void putCard2Img(Button btn){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/selecaoTreinador/treinadoresParaSelecao/cardJulia.png")));
        ImageView iv = new ImageView(image);
        iv.setFitWidth(415);
        iv.setFitHeight(730);
        iv.setPreserveRatio(true);
        btn.setGraphic(iv);
    }
    public void putBtnVoltarImg(Button btn){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/botoes/btnVoltar.png")));
        ImageView iv = new ImageView(image);
        iv.setFitWidth(500);
        iv.setFitHeight(120);
        iv.setPreserveRatio(true);
        btn.setGraphic(iv);
    }

    public Scene criarSceneTreinador(){
        ScreenService TrainerService = new ScreenService();
        Image selectTrainer = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/selecaoTreinador/selecioneTreinador.png")));
        ImageView iv = new ImageView(selectTrainer);
        iv.setFitWidth(800);
        iv.setFitHeight(180);
        iv.setPreserveRatio(true);

        Button btnVoltar = new Button();
        btnVoltar.setId("TrainerVoltar");
        Button cardTrainer1 = new Button();
        cardTrainer1.setId("cardTrainer1");
        Button cardTrainer2 = new Button();
        cardTrainer2.setId("cardTrainer2");

        TrainerService.configBtn(btnVoltar);
        TrainerService.configBtn(cardTrainer1);
        TrainerService.configBtn(cardTrainer2);
        putBtnVoltarImg(btnVoltar);
        putCard1Img(cardTrainer1);
        putCard2Img(cardTrainer2);
        TrainerService.animacaoHover(cardTrainer1);
        TrainerService.animacaoHover(cardTrainer2);
        TrainerService.animacaoHover(btnVoltar);

        HBox selectTrainerBox = new HBox(50);
        selectTrainerBox.getChildren().addAll(cardTrainer1,cardTrainer2);
        selectTrainerBox.setAlignment(Pos.CENTER);

        HBox topBox = new HBox(btnVoltar,iv);
        topBox.setSpacing(420);

        BorderPane root = new BorderPane();
        root.setTop(topBox);
        BorderPane.setAlignment(topBox,Pos.CENTER);
        BorderPane.setMargin(topBox,new Insets(10,15,0,15));
        root.setCenter(selectTrainerBox);
        root.setStyle(WallpaperSelectTrainer);

        return new Scene(root,TrainerService.screenWidth,TrainerService.screenHeight);
    }

    public TelaTreinador(){
    }
}
