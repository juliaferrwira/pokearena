package com.pokearena.view;
import com.pokearena.model.Insignia;
import com.pokearena.model.Pokemon;
import com.pokearena.repository.BancoDeDados;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.util.ArrayList;

public class TelaInsignias {
    private static final double FatorEscala = 1.1;
    private static final int DuracaoMS = 150;
    private static final String insiStyle = "-fx-padding: 0; -fx-background-color: transparent;";
    private static final String infoStyle = "-fx-background-color: rgba(0, 0, 0, 0.7); " +
                                            "-fx-border-color: white; " +
                                            "-fx-border-width: 3px; " +
                                            "-fx-border-radius: 15px; " +
                                            "-fx-background-radius: 15px;";

    public static String WallpaperTelaInsignias = "-fx-background-image: url('srcPokearena/wallpaper1.jpg'); " +
                                                  "-fx-background-size: cover; " + "-fx-background-position: center center; " +
                                                  "-fx-background-repeat: no-repeat;";

    private static final ArrayList<Insignia> InsiList = new ArrayList<>();

    public static void animacaoHover(ImageView insignia){
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

    public static void configInsi(ImageView insignia){
        insignia.setScaleX(1.0);
        insignia.setScaleY(1.0);
        insignia.setStyle(insiStyle);
    }
    public Image imgInsiRock(){
        Insignia brockBadge = new Insignia(1,"Insígnia de Pedra","Pedra","Brock");
        Image imgRockBadge = new Image(getClass().getResourceAsStream("/srcPokearena/insignias/BrockBadge.png"));
        brockBadge.setInsigniaImage(imgRockBadge);
        brockBadge.fazerDescricaoInsignia();
        InsiList.add(brockBadge);
        return imgRockBadge;
    }
    public Image imgInsiWater(){
        Insignia mistyBadge = new Insignia(2,"Insígnia de Água","Água","Misty");
        Image imgWaterBadge = new Image(getClass().getResourceAsStream("/srcPokearena/insignias/MistyBadge.png"));
        mistyBadge.setInsigniaImage(imgWaterBadge);
        mistyBadge.fazerDescricaoInsignia();
        InsiList.add(mistyBadge);
        return imgWaterBadge;
    }
    public Image imgInsiAsh(){
        Insignia ashBadge = new Insignia(3,"Boné do Ash","","Ash");
        Image imgAshBadge = new Image(getClass().getResourceAsStream("/srcPokearena/insignias/AshCap.png"));
        ashBadge.setInsigniaImage(imgAshBadge);
        ashBadge.fazerDescricaoInsigniaAsh();
        InsiList.add(ashBadge);
        return imgAshBadge;
    }
    public void configImageView(ImageView iv){
        iv.setFitWidth(800);
        iv.setFitHeight(180);
        iv.setPreserveRatio(true);
    }

    public BorderPane criarRootTelaInsignias(){
        ImageView RockInsi = new ImageView(imgInsiRock());
        ImageView WaterInsi = new ImageView(imgInsiWater());
        ImageView AshCap = new ImageView(imgInsiAsh());
        configImageView(RockInsi);
        configImageView(WaterInsi);
        configImageView(AshCap);

        Insignia rockBadge = InsiList.get(0);
        Insignia waterBadge = InsiList.get(1);
        Insignia ashCapBadge = InsiList.get(2);

        RockInsi.setUserData(rockBadge);
        WaterInsi.setUserData(waterBadge);
        AshCap.setUserData(ashCapBadge);

        Label descricaoInsi = new Label("");
        descricaoInsi.setStyle("-fx-font-size: 26px; -fx-text-fill: white; -fx-font-weight: bold;");

        configInsi(RockInsi);
        configInsi(WaterInsi);
        configInsi(AshCap);
        RockInsi.setStyle(insiStyle);
        WaterInsi.setStyle(insiStyle);
        AshCap.setStyle(insiStyle);
        animacaoHover(RockInsi);
        animacaoHover(WaterInsi);
        animacaoHover(AshCap);

        HBox descricaoBox = new HBox();
        descricaoBox.setAlignment(Pos.CENTER);
        descricaoBox.setStyle(infoStyle);
        descricaoBox.getChildren().add(descricaoInsi);

        HBox insigniasBox = new HBox(100);
        insigniasBox.setAlignment(Pos.CENTER);
        insigniasBox.getChildren().add(RockInsi);
        insigniasBox.getChildren().add(WaterInsi);
        insigniasBox.getChildren().add(AshCap);


        BorderPane root = new BorderPane();
        root.setCenter(insigniasBox);
        root.setStyle(WallpaperTelaInsignias);
        BorderPane.setMargin(descricaoBox,new Insets(0,30,60,30));

        RockInsi.setOnMouseEntered(e->{
            Insignia insi = (Insignia) RockInsi.getUserData();
            descricaoInsi.setText(insi.getDescricao());
            root.setBottom(descricaoBox);
        });
        RockInsi.setOnMouseExited(e->{
            descricaoInsi.setText("");
            root.setBottom(null);
        });
        WaterInsi.setOnMouseEntered(e->{
            Insignia insi = (Insignia) WaterInsi.getUserData();
            descricaoInsi.setText(insi.getDescricao());
            root.setBottom(descricaoBox);
        });
        WaterInsi.setOnMouseExited(e->{
            descricaoInsi.setText("");
            root.setBottom(null);
        });
        AshCap.setOnMouseEntered(e->{
            Insignia insi = (Insignia) AshCap.getUserData();
            descricaoInsi.setText(insi.getDescricao());
            root.setBottom(descricaoBox);
        });
        AshCap.setOnMouseExited(e->{
            descricaoInsi.setText("");
            root.setBottom(null);
        });


        return root;
    }

    public TelaInsignias(){}
}
