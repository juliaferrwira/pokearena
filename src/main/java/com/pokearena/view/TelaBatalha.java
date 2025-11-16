package com.pokearena.view;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.util.Duration;


public class TelaBatalha extends VBox {
    private static final double FatorEscala = 1.1;
    private static final int DuracaoMS = 150;
    private static final String btnStyle = "-fx-padding: 0; -fx-background-color: transparent;" +
            "-fx-min-width: 200px; -fx-min-height: 100px;";
    private static final String hboxStyle = "-fx-background-color: rgba(0, 0, 0, 0.7); " +
                                            "-fx-border-color: white; " +
                                            "-fx-border-width: 3px; " +
                                            "-fx-border-radius: 15px; " +
                                            "-fx-background-radius: 15px;";

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
    public void putBtnAtacarImg(Button btn){
        Image image = new Image(getClass().getResourceAsStream("/srcPokearena/botoes/btnAtacar.png"));
        ImageView iv = new ImageView(image);
        iv.setFitWidth(500);
        iv.setFitHeight(120);
        iv.setPreserveRatio(true);
        btn.setGraphic(iv);
    }
    public void putBtnPokemonImg(Button btn){
        Image image = new Image(getClass().getResourceAsStream("/srcPokeArena/botoes/btnPokemon.png"));
        ImageView iv = new ImageView(image);
        iv.setFitWidth(500);
        iv.setFitHeight(120);
        iv.setPreserveRatio(true);
        btn.setGraphic(iv);
    }
    public void putBtnBattleInfoImg(Button btn){
        Image image = new Image(getClass().getResourceAsStream("/srcPokearena/botoes/btnBattleInfo.png"));
        ImageView iv = new ImageView(image);
        iv.setFitWidth(500);
        iv.setFitHeight(120);
        iv.setPreserveRatio(true);
        btn.setGraphic(iv);
    }


   public static String WallpaperBattle1 = "-fx-background-image: url('srcPokearena/wallpaper2-1.jpg'); " +
                                           "-fx-background-size: cover; " + "-fx-background-position: center center; " +
                                           "-fx-background-repeat: no-repeat;";

   public static String WallpaperBattle2 = "-fx-background-image: url('srcPokearena/wallpaper3.jpg'); " +
                                           "-fx-background-size: cover; " + "-fx-background-position: center center; " +
                                           "-fx-background-repeat: no-repeat;";

   public static String WallpaperBattle3 = "-fx-background-image: url('srcPokearena/wallpaper4.jpg'); " +
                                           "-fx-background-size: cover; " + "-fx-background-position: center center; " +
                                           "-fx-background-repeat: no-repeat;";

   public Scene criarSceneBatalha(int whatBattle,double width, double height){
       Button btnAtacar = new Button();
       Button btnPokemon = new Button();
       Button btnBattleInfo = new Button();
       configBtn(btnAtacar);
       configBtn(btnPokemon);
       configBtn(btnBattleInfo);
       putBtnAtacarImg(btnAtacar);
       putBtnPokemonImg(btnPokemon);
       putBtnBattleInfoImg(btnBattleInfo);
       animacaoHover(btnAtacar);
       animacaoHover(btnPokemon);
       animacaoHover(btnBattleInfo);

       HBox actionBar = new HBox(10);
       actionBar.getChildren().addAll(
           btnAtacar,btnPokemon,btnBattleInfo
       );
       actionBar.setAlignment(Pos.CENTER);
       actionBar.setStyle(hboxStyle);

       BorderPane root = new BorderPane();
       root.setBottom(actionBar);
       BorderPane.setAlignment(actionBar,Pos.CENTER);
       BorderPane.setMargin(actionBar,new Insets(10,15,10,15));
       if(whatBattle==1){
           root.setStyle(WallpaperBattle1);
       } else if (whatBattle==2) {
           root.setStyle(WallpaperBattle2);
       } else if (whatBattle==3) {
           root.setStyle(WallpaperBattle3);
       }
       return new Scene(root, width, height);
   }

   public TelaBatalha() {
   }

}
