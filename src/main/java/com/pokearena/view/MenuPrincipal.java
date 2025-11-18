package com.pokearena.view;

import com.pokearena.model.Pokemon;
import com.pokearena.repository.BancoDeDados;
import com.pokearena.service.ScreenService;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.util.Duration;

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

        VBox root = new VBox();
        root.setStyle(backgroundStyle);
        root.getChildren().add(logoView);
        root.getChildren().add(btnIniciarJogo);
        root.getChildren().add(btnInsignias);
        root.setSpacing(15);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root,800,600);
        stage.setTitle("PokeArena");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        TelaPokemon telaPokemon = new TelaPokemon();
        BorderPane selectPokemonRoot = telaPokemon.criarRootPokemon(stage);
        
        TelaTreinador TelaTreinador = new TelaTreinador();
        BorderPane selectTrainerRoot = TelaTreinador.criarRootTreinador();


        TelaInsignias TelaInsignias = new TelaInsignias();
        BorderPane infoInsigniasRoot = TelaInsignias.criarRootTelaInsignias();

        btnInsignias.setOnAction(e->{
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();
            MenuService.changeScene(stage,infoInsigniasRoot,currentWidth,currentHeight);
        });
        
        btnIniciarJogo.setOnAction(e->{
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();
            MenuService.changeScene(stage,selectPokemonRoot,currentWidth,currentHeight);
        });
        
        telaPokemon.setOnProsseguirAction(() -> {
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();
            MenuService.changeScene(stage,selectTrainerRoot,currentWidth,currentHeight);
        });
        Node cardTrainer1 = selectTrainerRoot.lookup("#cardTrainer1");
        Node cardTrainer2 = selectTrainerRoot.lookup("#cardTrainer2");
        TelaBatalha screenBattle = new TelaBatalha();
        screenBattle.setStage(stage);
        cardTrainer1.setOnMouseClicked(e-> {
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();
            List<Pokemon> pokemonsSelecionados = telaPokemon.getPokemonsSelecionados();
            BorderPane battle1 = screenBattle.criarSceneBatalha(1, pokemonsSelecionados);
            MenuService.changeScene(stage,battle1,currentWidth,currentHeight);
        });
        cardTrainer2.setOnMouseClicked(e->{
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();
            List<Pokemon> pokemonsSelecionados = telaPokemon.getPokemonsSelecionados();
            BorderPane battle1 = screenBattle.criarSceneBatalha(1, pokemonsSelecionados);
            MenuService.changeScene(stage,battle1,currentWidth,currentHeight);
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


