package com.pokearena.view;

import com.pokearena.model.Pokemon;
import com.pokearena.service.PokemonFactory;
import com.pokearena.service.ScreenService;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TelaPokemon {
    private static final int MAX_SELECIONADOS = 3;
    private List<Pokemon> pokemonsSelecionados;
    private Button btnProsseguir;
    private Label labelContador;
    private Runnable onProsseguirAction;
    private ScreenService PokeService;

    public static String WallpaperSelectPokemon = "-fx-background-image: url('srcPokearena/wallpaper1.jpg'); " +
                                                   "-fx-background-size: cover; " +
                                                   "-fx-background-position: center center; " +
                                                   "-fx-background-repeat: no-repeat;";


    public void putBtnVoltarImg(Button btn){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/botoes/btnVoltar.png")));
        ImageView iv = new ImageView(image);
        iv.setFitWidth(500);
        iv.setFitHeight(120);
        iv.setPreserveRatio(true);
        btn.setGraphic(iv);
    }

    public Scene criarScenePokemon(){
        pokemonsSelecionados = new ArrayList<>();
        PokeService = new ScreenService();
        Image selectPokemon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/selecaoPokemons/nrRestantesPokemons/restantes3.png")));
        ImageView iv = new ImageView(selectPokemon);
        iv.setFitWidth(800);
        iv.setFitHeight(180);
        iv.setPreserveRatio(true);
        Button btnVoltar = new Button();
        btnVoltar.setId("PokeVoltar");
        putBtnVoltarImg(btnVoltar);
        PokeService.configBtn(btnVoltar);
        PokeService.animacaoHover(btnVoltar);

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
            PokeService.configBtn(btnPokemon);
            PokeService.putPokemonImg(btnPokemon, pokemonIds[i], pokemonNomes[i], false);
            PokeService.animacaoHover(btnPokemon);

            final int index = i; // pra usar dentro do listener
            btnPokemon.setOnAction(e -> selecionarPokemon(btnPokemon, pokemonIds[index], pokemonNomes[index]));

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

        HBox topBox = new HBox(btnVoltar,iv);
        topBox.setSpacing(PokeService.offScreenDistance - 800);

        BorderPane root = new BorderPane();
        root.setTop(topBox);
        BorderPane.setAlignment(topBox, Pos.CENTER);
        BorderPane.setMargin(topBox, new Insets(10, 15, 0, 15));
        root.setCenter(centerBox);
        root.setStyle(WallpaperSelectPokemon);

        return new Scene(root,PokeService.screenWidth,PokeService.screenHeight);
    }

    private void selecionarPokemon(Button btn, int pokemonId, String pokemonNome){
        boolean jaSelecionado = false;
        int posicaoRemover = -1;

        for (int i = 0; i < pokemonsSelecionados.size(); i++) {
            if (pokemonsSelecionados.get(i).getId() == pokemonId) {
                jaSelecionado = true;
                posicaoRemover = i;
                break;
            }
        }
        if (jaSelecionado) {
            pokemonsSelecionados.remove(posicaoRemover);
            PokeService.putPokemonImg(btn, pokemonId, pokemonNome, false);
        } else {
            if (pokemonsSelecionados.size() >= MAX_SELECIONADOS) {
                return;
            }
            Pokemon pokemon = PokemonFactory.criarPokemonPorId(pokemonId);
            if (pokemon != null) {
                pokemonsSelecionados.add(pokemon);
                PokeService.putPokemonImg(btn, pokemonId, pokemonNome, true);
            }
        }
        atualizarContador();
    }
    private void atualizarContador(){
        int selecionados = pokemonsSelecionados.size();
        btnProsseguir.setDisable(true);
        labelContador.setText("Selecione 3 pokémons (" + selecionados + "/3)");
        
        if (selecionados == MAX_SELECIONADOS && onProsseguirAction != null) {
            btnProsseguir.setDisable(false);
            btnProsseguir.setOnAction(e->{
                onProsseguirAction.run();
            });
        }
    }

    public List<Pokemon> getPokemonsSelecionados(){
        return new ArrayList<>(pokemonsSelecionados);
    }

    public void setOnProsseguirAction(Runnable action){
        this.onProsseguirAction = action;
    }

    public TelaPokemon(){}
}
