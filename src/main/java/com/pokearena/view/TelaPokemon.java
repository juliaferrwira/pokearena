package com.pokearena.view;

import com.pokearena.model.Pokemon;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class TelaPokemon {
    private static final double FatorEscala = 1.1;
    private static final int DuracaoMS = 150;
    private static final int MAX_SELECIONADOS = 3;
    private static final String btnStyle = "-fx-padding: 0; -fx-background-color: transparent;";
    
    private List<Pokemon> pokemonsSelecionados;
    private Button btnProsseguir;
    private Label labelContador;
    
    public static String WallpaperSelectPokemon = "-fx-background-image: url('srcPokearena/wallpaper1.jpg'); " +
                                                   "-fx-background-size: cover; " +
                                                   "-fx-background-position: center center; " +
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

    public void putPokemonImg(Button btn, int pokemonId, String pokemonNome, boolean isRed){
        String imagePath;
        if (isRed) {
            imagePath = "/srcPokearena/selecaoPokemons/pokemonsParaSelecao/" + pokemonId + "-" + pokemonNome + "Red.png";
        } else {
            imagePath = "/srcPokearena/selecaoPokemons/pokemonsParaSelecao/" + pokemonId + "-" + pokemonNome + ".png";
        }
        
        java.io.InputStream stream = getClass().getResourceAsStream(imagePath);
        if (stream == null && isRed) {
            putPokemonImg(btn, pokemonId, pokemonNome, false);
            return;
        }
        
        Image image = new Image(stream);
        ImageView iv = new ImageView(image);
        iv.setFitWidth(200);
        iv.setFitHeight(200);
        iv.setPreserveRatio(true);
        btn.setGraphic(iv);
    }

    public void putPokemonImg(Button btn, int pokemonId, String pokemonNome){
        putPokemonImg(btn, pokemonId, pokemonNome, false);
    }

    public BorderPane criarRootPokemon(Stage stage){
        pokemonsSelecionados = new ArrayList<>();
        
        Image selectPokemon = new Image(getClass().getResourceAsStream("/srcPokearena/selecaoPokemons/nrRestantesPokemons/restantes3.png"));
        ImageView iv = new ImageView(selectPokemon);
        iv.setFitWidth(800);
        iv.setFitHeight(180);
        iv.setPreserveRatio(true);

        int[] pokemonIds = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        String[] pokemonNomes = {"bulbasaur", "charmander", "squirtle", "pikachu", 
                                 "jigglypuff", "eevee", "machop", "abra", "geodude"};

        GridPane gridPokemons = new GridPane();
        gridPokemons.setHgap(20);
        gridPokemons.setVgap(20);
        gridPokemons.setAlignment(Pos.CENTER);
        
        for (int i = 0; i < pokemonIds.length; i++) {
            Button btnPokemon = new Button();
            btnPokemon.setId("pokemon_" + pokemonIds[i]);
            configBtn(btnPokemon);
            putPokemonImg(btnPokemon, pokemonIds[i], pokemonNomes[i]);
            animacaoHover(btnPokemon);
            
            int finalI = i;
            btnPokemon.setOnAction(e -> toggleSelecaoPokemon(btnPokemon, pokemonIds[finalI], pokemonNomes[finalI]));
            
            gridPokemons.add(btnPokemon, i % 3, i / 3);
        }

        labelContador = new Label("Selecione 3 pokémons (0/3)");
        labelContador.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");

        btnProsseguir = new Button("Prosseguir");
        btnProsseguir.setStyle("-fx-font-size: 20px; -fx-padding: 15 30; -fx-background-color: #cccccc; " +
                              "-fx-text-fill: #666666; -fx-font-weight: bold; -fx-background-radius: 10;");
        btnProsseguir.setDisable(true);

        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(gridPokemons, labelContador, btnProsseguir);
        centerBox.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setTop(iv);
        BorderPane.setAlignment(iv,Pos.CENTER);
        BorderPane.setMargin(iv,new Insets(10,15,15,15));
        root.setCenter(centerBox);
        root.setStyle(WallpaperSelectPokemon);

        return root;
    }

    private void toggleSelecaoPokemon(Button btn, int pokemonId, String pokemonNome) {
        boolean jaSelecionado = pokemonsSelecionados.stream().anyMatch(p -> p.getId() == pokemonId);
        
        if (jaSelecionado) {
            pokemonsSelecionados.removeIf(p -> p.getId() == pokemonId);
            btn.setStyle(btnStyle);
            putPokemonImg(btn, pokemonId, pokemonNome, false); // Volta para imagem original
        } else {
            if (pokemonsSelecionados.size() >= MAX_SELECIONADOS) return;
            pokemonsSelecionados.add(new Pokemon(pokemonId, pokemonNome, 50, 50, 100, "normal", ""));
            btn.setStyle(btnStyle);
            putPokemonImg(btn, pokemonId, pokemonNome, true); // Troca para versão Red
        }
        
        atualizarContador();
    }

    private void atualizarContador() {
        int selecionados = pokemonsSelecionados.size();
        labelContador.setText("Selecione 3 pokémons (" + selecionados + "/3)");
        
        if (selecionados == MAX_SELECIONADOS) {
            btnProsseguir.setDisable(false);
            btnProsseguir.setStyle("-fx-font-size: 20px; -fx-padding: 15 30; -fx-background-color: #4CAF50; " +
                                  "-fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10;");
        } else {
            btnProsseguir.setDisable(true);
            btnProsseguir.setStyle("-fx-font-size: 20px; -fx-padding: 15 30; -fx-background-color: #cccccc; " +
                                  "-fx-text-fill: #666666; -fx-font-weight: bold; -fx-background-radius: 10;");
        }
    }

    public List<Pokemon> getPokemonsSelecionados() {
        return new ArrayList<>(pokemonsSelecionados);
    }

    public void setOnProsseguirAction(Runnable action) {
        btnProsseguir.setOnAction(e -> action.run());
    }

    public TelaPokemon(){
    }
}