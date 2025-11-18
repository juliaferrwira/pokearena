package com.pokearena.view;
import com.pokearena.model.Insignia;
import com.pokearena.repository.BancoDeDados;
import com.pokearena.service.ScreenService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Objects;

public class TelaInsignias {
    public final double DistanciaSubir = -50.0; // Sobe 50 pixels (valor negativo)
    public final double PosicaoOrigem = 0.0;
    private static final String infoStyle = "-fx-background-color: rgba(0, 0, 0, 0.7); " +
                                            "-fx-border-color: white; " +
                                            "-fx-border-width: 3px; " +
                                            "-fx-border-radius: 15px; " +
                                            "-fx-background-radius: 15px;";

    public String WallpaperTelaInsignias = "-fx-background-image: url('srcPokearena/wallpaper1.jpg'); " +
                                           "-fx-background-size: cover; " + "-fx-background-position: center center; " +
                                           "-fx-background-repeat: no-repeat;";
    private static final ArrayList<Insignia> InsiList = new ArrayList<>();

    public Image imgInsiRock(){
        Insignia brockBadge = new Insignia(1,"Insígnia de Pedra","Pedra","Brock");
        Image imgRockBadge = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/insignias/BrockBadge.png")));
        brockBadge.setInsigniaImage(imgRockBadge);
        brockBadge.fazerDescricaoInsignia();
        InsiList.add(brockBadge);
        return imgRockBadge;
    }
    public Image imgInsiWater(){
        Insignia mistyBadge = new Insignia(2,"Insígnia de Água","Água","Misty");
        Image imgWaterBadge = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/insignias/MistyBadge.png")));
        mistyBadge.setInsigniaImage(imgWaterBadge);
        mistyBadge.fazerDescricaoInsignia();
        InsiList.add(mistyBadge);
        return imgWaterBadge;
    }
    public Image imgInsiAsh(){
        Insignia ashBadge = new Insignia(3,"Boné do Ash","","Ash");
        Image imgAshBadge = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/insignias/AshCap.png")));
        ashBadge.setInsigniaImage(imgAshBadge);
        ashBadge.fazerDescricaoInsigniaAsh();
        InsiList.add(ashBadge);
        return imgAshBadge;
    }
    public void putBtnVoltarImg(Button btn){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/botoes/btnVoltar.png")));
        ImageView iv = new ImageView(image);
        iv.setFitWidth(500);
        iv.setFitHeight(120);
        iv.setPreserveRatio(true);
        btn.setGraphic(iv);
    }

    public Scene criarSceneInsignias(){
        ScreenService InsiService = new ScreenService();
        ImageView RockInsi = new ImageView(imgInsiRock());
        ImageView WaterInsi = new ImageView(imgInsiWater());
        ImageView AshCap = new ImageView(imgInsiAsh());
        Button btnVoltar = new Button();
        btnVoltar.setId("InsiVoltar");
        putBtnVoltarImg(btnVoltar);
        InsiService.configImageView(RockInsi);
        InsiService.configImageView(WaterInsi);
        InsiService.configImageView(AshCap);
        InsiService.configBtn(btnVoltar);

        Insignia rockBadge = InsiList.get(0);
        Insignia waterBadge = InsiList.get(1);
        Insignia ashCapBadge = InsiList.get(2);

        RockInsi.setUserData(rockBadge);
        WaterInsi.setUserData(waterBadge);
        AshCap.setUserData(ashCapBadge);

        Label descricaoInsi = new Label("");
        descricaoInsi.setStyle("-fx-font-size: 26px; -fx-text-fill: white; -fx-font-weight: bold;");

        InsiService.configInsi(RockInsi);
        InsiService.configInsi(WaterInsi);
        InsiService.configInsi(AshCap);
        InsiService.animacaoHover(btnVoltar);

        HBox descricaoBox = new HBox();
        descricaoBox.setAlignment(Pos.CENTER);
        descricaoBox.setStyle(infoStyle);
        descricaoBox.getChildren().add(descricaoInsi);

        HBox insigniasBox = new HBox(100);
        insigniasBox.setAlignment(Pos.CENTER);
        insigniasBox.getChildren().add(RockInsi);
        insigniasBox.getChildren().add(WaterInsi);
        insigniasBox.getChildren().add(AshCap);

        VBox InsiContainer = new VBox(btnVoltar,insigniasBox);
        InsiContainer.setSpacing(250);
        InsiContainer.getChildren().get(0).setLayoutX(-100);

        BorderPane root = new BorderPane();
        root.setTop(InsiContainer);
        root.setStyle(WallpaperTelaInsignias);
        BorderPane.setMargin(descricaoBox,new Insets(0,30,60,30));

        RockInsi.setOnMouseEntered(e->{
            Insignia insi = (Insignia) RockInsi.getUserData();
            descricaoInsi.setText(insi.getDescricao());
            root.setBottom(descricaoBox);
            InsiService.criarAnimacaoPosicao(RockInsi,DistanciaSubir).play();
        });
        RockInsi.setOnMouseExited(e->{
            descricaoInsi.setText("");
            root.setBottom(null);
            RockInsi.setTranslateY(PosicaoOrigem);
            InsiService.criarAnimacaoPosicao(RockInsi,PosicaoOrigem).play();
        });
        WaterInsi.setOnMouseEntered(e->{
            Insignia insi = (Insignia) WaterInsi.getUserData();
            descricaoInsi.setText(insi.getDescricao());
            root.setBottom(descricaoBox);
            InsiService.criarAnimacaoPosicao(WaterInsi,DistanciaSubir).play();
        });
        WaterInsi.setOnMouseExited(e->{
            descricaoInsi.setText("");
            root.setBottom(null);
            InsiService.criarAnimacaoPosicao(WaterInsi,PosicaoOrigem).play();
        });
        AshCap.setOnMouseEntered(e->{
            Insignia insi = (Insignia) AshCap.getUserData();
            descricaoInsi.setText(insi.getDescricao());
            root.setBottom(descricaoBox);
            InsiService.criarAnimacaoPosicao(AshCap,DistanciaSubir).play();
        });
        AshCap.setOnMouseExited(e->{
            descricaoInsi.setText("");
            root.setBottom(null);
            InsiService.criarAnimacaoPosicao(AshCap,PosicaoOrigem).play();
        });

        return new Scene(root,InsiService.screenWidth,InsiService.screenHeight);
    }

    public TelaInsignias(){}
}
