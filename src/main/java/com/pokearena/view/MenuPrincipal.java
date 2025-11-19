package com.pokearena.view;
import com.pokearena.model.Pokemon;
import com.pokearena.repository.BancoDeDados;
import com.pokearena.service.ScreenService;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.awt.*;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;

public class MenuPrincipal extends Application {

    public MenuPrincipal(){}
    public void putBtnIniciarImg(Button btn){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokeArena/botoes/btnIniciar.png")));
        ImageView iv = new ImageView(image);
        iv.setFitWidth(500);
        iv.setFitHeight(120);
        iv.setPreserveRatio(true);
        btn.setGraphic(iv);
    }
    public void putBtnInsiImg(Button btn){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokeArena/botoes/btnInsignias.png")));
        ImageView iv = new ImageView(image);
        iv.setFitWidth(500);
        iv.setFitHeight(120);
        iv.setPreserveRatio(true);
        btn.setGraphic(iv);
    }

    public static String backgroundStyle = "-fx-background-image: url('srcPokearena/wallpaper2.jpg'); " +
            "-fx-background-size: cover; " +
            "-fx-background-position: center center; " +
            "-fx-background-repeat: no-repeat;";

    @Override
    public void start(Stage stage){
        ScreenService MenuService = new ScreenService();
        Button btnIniciarJogo = new Button("");
        Button btnInsignias = new Button("");
        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/logoPokearena.png")));
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(800);
        logoView.setFitHeight(180);
        logoView.setPreserveRatio(true);

        MenuService.configBtn(btnIniciarJogo);
        MenuService.configBtn(btnInsignias);
        putBtnIniciarImg(btnIniciarJogo);
        putBtnInsiImg(btnInsignias);
        MenuService.animacaoHover(btnIniciarJogo);
        MenuService.animacaoHover(btnInsignias);

        VBox MenuContainer = new VBox();
        MenuContainer.getChildren().add(logoView);
        MenuContainer.getChildren().add(btnIniciarJogo);
        MenuContainer.getChildren().add(btnInsignias);
        MenuContainer.setSpacing(15);
        MenuContainer.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setCenter(MenuContainer);
        root.setStyle(backgroundStyle);

        Scene scene = new Scene(root,MenuService.screenWidth,MenuService.screenHeight);
        stage.setTitle("PokeArena");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        TelaPokemon telaPokemon = new TelaPokemon();
        Scene selectPokemonScene = telaPokemon.criarScenePokemon();
        Node nodePokeVoltar = selectPokemonScene.lookup("#PokeVoltar");

        TelaTreinador TelaTreinador = new TelaTreinador();
        Scene selectTrainerScene = TelaTreinador.criarSceneTreinador();
        Node nodeTrainerVoltar = selectTrainerScene.lookup("#TrainerVoltar");

        TelaInsignias TelaInsignias = new TelaInsignias();
        Scene infoInsigniasScene = TelaInsignias.criarSceneInsignias();
        Node nodeInsiVoltar = infoInsigniasScene.lookup("#InsiVoltar");

        nodeTrainerVoltar.setOnMouseClicked(e->{
            MenuService.changeScene(stage,selectPokemonScene);
        });
        nodeInsiVoltar.setOnMouseClicked(e->{
            MenuService.changeScene(stage,scene);
        });
        nodePokeVoltar.setOnMouseClicked(e->{
            MenuService.changeScene(stage,scene);
        });
        btnInsignias.setOnAction(e->{
            MenuService.changeScene(stage,infoInsigniasScene);
        });
        btnIniciarJogo.setOnAction(e->{
            MenuService.changeScene(stage,selectPokemonScene);
        });
        telaPokemon.setOnProsseguirAction(() -> {
            MenuService.changeScene(stage,selectTrainerScene);
        });
        Node cardTrainer1 = selectTrainerScene.lookup("#cardTrainer1");
        Node cardTrainer2 = selectTrainerScene.lookup("#cardTrainer2");
        TelaBatalha screenBattle = new TelaBatalha("Kanto");
        cardTrainer1.setOnMouseClicked(e-> {
            List<Pokemon> pokemonsSelecionados = telaPokemon.getPokemonsSelecionados();
            Scene battle1 = screenBattle.criarSceneBatalha(1, pokemonsSelecionados,stage,1,TelaInsignias.InsiList);
            screenBattle.kanto.battles.add(battle1);
            Node imgvwLabel = battle1.lookup("#imgvwLabel");
            MenuService.changeScene(stage,battle1);
            Platform.runLater(()->{
                SequentialTransition animarLabels = MenuService.animarLabels(imgvwLabel,MenuService.offScreenDistance + 800,MenuService.offScreenDistance + 800);
                animarLabels.play();
            });
        });
        cardTrainer2.setOnMouseClicked(e->{
            List<Pokemon> pokemonsSelecionados = telaPokemon.getPokemonsSelecionados();
            Scene battle1 = screenBattle.criarSceneBatalha(1, pokemonsSelecionados,stage,2,TelaInsignias.InsiList);
            MenuService.changeScene(stage,battle1);
            screenBattle.kanto.battles.add(battle1);
            battle1.getStylesheets().add(MenuService.dataUrl);
            Node imgvwLabel = battle1.lookup("#imgvwLabel");
            Platform.runLater(()->{
                SequentialTransition animarLabels = MenuService.animarLabels(imgvwLabel,MenuService.offScreenDistance + 800,MenuService.offScreenDistance + 800);
                animarLabels.play();
            });
        });

    }

    public static void exibirPokemons() {
        List<Pokemon> pokemons = BancoDeDados.listarPokemons();

        if (pokemons.isEmpty()) {
            System.out.println("Nenhum pokemon cadastrado.");
            return;
        }

        System.out.println("\n=== POKÉMONS CADASTRADOS ===");
        for (Pokemon pokemon : pokemons) {
            System.out.println(pokemon.getNome() + " - Tipo: " + pokemon.getTipo() +
                    " - HP: " + pokemon.getHp() + "/" + pokemon.getHpMaximo());
        }
    }
    public static void main(String[] args) {
        System.out.println("=== POKEARENA ===");
        Connection conn = BancoDeDados.conectar();

        if (conn != null) {
            System.out.println("Conexão com o banco bem-sucedida");
        } else {
            System.out.println("Falha ao conectar ao banco");
        }

        launch(args);
    }
}