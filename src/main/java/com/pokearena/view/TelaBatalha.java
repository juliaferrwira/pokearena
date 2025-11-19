package com.pokearena.view;
import com.pokearena.model.Pokemon;
import com.pokearena.service.BatalhaService;
import com.pokearena.service.ScreenService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.util.List;
import java.util.Objects;

public class TelaBatalha {
    private static final String hboxStyle = "-fx-background-color: rgba(0, 0, 0, 0.7); " +
            "-fx-border-color: white; " +
            "-fx-border-width: 3px; " +
            "-fx-border-radius: 15px; " +
            "-fx-background-radius: 15px;";

    private static final String labelStyle = "-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold;";
    private static final String mensagemStyle = "-fx-font-size: 14px; -fx-text-fill: yellow; -fx-font-weight: bold;";
    private final ScreenService battleScreenService = new ScreenService();
    private Stage stage;
    private Image PlayerCard;

    private Pokemon pokemonJogador;
    private Pokemon pokemonMaquina;

    private ProgressBar hpJogadorBar;
    private ProgressBar hpMaquinaBar;

    private BatalhaService batalhaService;
    private Label labelMensagem;
    private BorderPane root;
    private VBox trocaPokemonBox;
    private Runnable onVoltarAction;
    private List<Pokemon> pokemonsJogador;

    public void putBtnAtacarImg(Button btn){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/botoes/btnAtacar.png")));
        ImageView iv = new ImageView(image);
        iv.setFitWidth(500);
        iv.setFitHeight(120);
        iv.setPreserveRatio(true);
        btn.setGraphic(iv);
    }

    public void putBtnPokemonImg(Button btn){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokeArena/botoes/btnPokemon.png")));
        ImageView iv = new ImageView(image);
        iv.setFitWidth(500);
        iv.setFitHeight(120);
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

    public static String WallpaperBattle1 = "-fx-background-image: url('/srcPokearena/wallpaper2-1.jpg'); " +
                                            "-fx-background-size: cover; " + "-fx-background-position: center center; " +
                                            "-fx-background-repeat: no-repeat;";

    public static String WallpaperBattle2 = "-fx-background-image: url('/srcPokearena/wallpaper3.jpg'); " +
                                            "-fx-background-size: cover; " + "-fx-background-position: center center; " +
                                            "-fx-background-repeat: no-repeat;";

    public static String WallpaperBattle3 = "-fx-background-image: url('/srcPokearena/wallpaper4.jpg'); " +
                                            "-fx-background-size: cover; " + "-fx-background-position: center center; " +
                                            "-fx-background-repeat: no-repeat;";

    public Scene criarSceneBatalha(int numeroBatalha, List<Pokemon> pokemonsJogador,Stage aStage,Image card){
        this.pokemonsJogador = pokemonsJogador;
        batalhaService = new BatalhaService(pokemonsJogador, numeroBatalha);
        stage = aStage;

        Button btnAtacar = new Button();
        Button btnPokemon = new Button();
        Button btnVoltar = new Button();

        hpJogadorBar = new ProgressBar();
        hpJogadorBar.setPrefWidth(300);
        hpJogadorBar.setPrefHeight(30);

        hpMaquinaBar = new ProgressBar();
        hpMaquinaBar.setPrefWidth(300);
        hpMaquinaBar.setPrefHeight(30);

        hpJogadorBar.getStyleClass().add("hp-bar-default");
        hpMaquinaBar.getStyleClass().add("hp-bar-default");

        labelMensagem = new Label("Batalha iniciada! " + batalhaService.getNomeMaquina() + " apareceu!");
        labelMensagem.setStyle(mensagemStyle);
        labelMensagem.setWrapText(true);
        labelMensagem.setMaxWidth(600);

        ScrollPane scrollMensagem = new ScrollPane(labelMensagem);
        scrollMensagem.setStyle("-fx-background: transparent; -fx-background-color: rgba(0, 0, 0, 0.5);");
        scrollMensagem.setFitToWidth(true);
        scrollMensagem.setPrefWidth(600);
        scrollMensagem.setMaxHeight(140);
        PlayerCard = card;

        ImageView cardTrainer = new ImageView(card);
        cardTrainer.setId("cardTrainerIcon");
        battleScreenService.configRemainingPokemons(cardTrainer);
        battleScreenService.animacaoHover(cardTrainer);
        Image pokeball = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/fullPokebolas.png")));
        ImageView pokeballNums = new ImageView(pokeball);
        battleScreenService.configRemainingPokemons(pokeballNums);
        battleScreenService.animacaoHover(pokeballNums);

        HBox TrainerLife = new HBox(20,cardTrainer,pokeballNums);
        TrainerLife.setAlignment(Pos.CENTER);
        TrainerLife.setStyle(hboxStyle);

        Image cardM = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/iconBrock.png")));
        ImageView cardMaquina = new ImageView(cardM);
        cardTrainer.setId("cardMaquinaIcon");
        battleScreenService.configRemainingPokemons(cardMaquina);
        battleScreenService.animacaoHover(cardMaquina);
        Image pokeballM = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/fullPokebolasInvertido.png")));
        ImageView pokeballMNums = new ImageView(pokeballM);
        battleScreenService.configRemainingPokemons(pokeballMNums);
        battleScreenService.animacaoHover(pokeballMNums);

        HBox MaquinaLife = new HBox(20,pokeballMNums,cardMaquina);
        MaquinaLife.setAlignment(Pos.CENTER);
        MaquinaLife.setStyle(hboxStyle);

        HBox LifeTrainersContainer = new HBox(TrainerLife,scrollMensagem,MaquinaLife);
        LifeTrainersContainer.setAlignment(Pos.CENTER);
        LifeTrainersContainer.setSpacing(220);

        battleScreenService.configBtn(btnAtacar);
        battleScreenService.configBtn(btnPokemon);
        battleScreenService.configBtn(btnVoltar);
        putBtnAtacarImg(btnAtacar);
        putBtnPokemonImg(btnPokemon);
        putBtnVoltarImg(btnVoltar);
        battleScreenService.animacaoHover(btnAtacar);
        battleScreenService.animacaoHover(btnPokemon);
        battleScreenService.animacaoHover(btnVoltar);

        btnAtacar.setOnAction(e -> atacar());
        btnPokemon.setOnAction(e -> mostrarTrocaPokemon());

        HBox actionBar = new HBox(30);
        actionBar.getChildren().addAll(btnAtacar, btnPokemon, btnVoltar);
        actionBar.setAlignment(Pos.CENTER);
        actionBar.setStyle(hboxStyle);

        pokemonJogador = batalhaService.getPokemonAtualJogador();
        pokemonMaquina = batalhaService.getPokemonAtualMaquina();

        battleScreenService.atualizarBarraHP(hpJogadorBar,pokemonJogador.getHp(),100);
        battleScreenService.atualizarBarraHP(hpMaquinaBar,pokemonMaquina.getHp(),100);

        HBox hpBox = new HBox(100);
        hpBox.setAlignment(Pos.CENTER);
        hpBox.getChildren().addAll(hpJogadorBar,hpMaquinaBar);
        hpBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 15px; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        trocaPokemonBox = new VBox(10);
        trocaPokemonBox.setAlignment(Pos.CENTER);
        trocaPokemonBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.9); -fx-padding: 20px; -fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 15px; -fx-background-radius: 15px;");
        trocaPokemonBox.setVisible(false);

        Image imgLabel = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/srcPokearena/batalhas/batalha1.png")));
        ImageView imgvwLabel = new ImageView(imgLabel);
        imgvwLabel.setId("imgvwLabel");
        battleScreenService.configImageView(imgvwLabel);

        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(hpBox,imgvwLabel ,trocaPokemonBox);
        centerBox.setPadding(new Insets(20));
        centerBox.setAlignment(Pos.CENTER);


        root = new BorderPane();
        root.setTop(LifeTrainersContainer);
        root.setCenter(centerBox);
        root.setBottom(actionBar);
        BorderPane.setAlignment(actionBar, Pos.CENTER);
        BorderPane.setAlignment(LifeTrainersContainer,Pos.CENTER);
        BorderPane.setMargin(actionBar, new Insets(10, 15, 10, 15));

        if(numeroBatalha == 1){
            root.setStyle(WallpaperBattle1);
        } else if (numeroBatalha == 2) {
            root.setStyle(WallpaperBattle2);
        } else if (numeroBatalha == 3) {
            root.setStyle(WallpaperBattle3);
        }

        return new Scene(root,battleScreenService.screenWidth,battleScreenService.screenHeight);
    }

    private void atacar() {
        if (batalhaService.isBatalhaTerminada() || batalhaService.isJogoTerminado()) {
            labelMensagem.setText("A batalha terminou!");
            return;
        }

        String mensagem = batalhaService.jogadorAtaca();
        labelMensagem.setText(mensagem);
        battleScreenService.atualizarBarraHP(hpJogadorBar,pokemonJogador.getHp(),100);
        battleScreenService.atualizarBarraHP(hpMaquinaBar,pokemonMaquina.getHp(),100);

        if (batalhaService.isBatalhaTerminada()) {
            if (batalhaService.getVencedor().equals("JOGADOR")) {
                int proximaBatalha = batalhaService.getNumeroBatalha() + 1;
                if (proximaBatalha <= 3) {
                    labelMensagem.setText(mensagem + "\n\nIniciando próxima batalha...");
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(e -> {
                        List<Pokemon> pokemonsComHpAtual = batalhaService.getPokemonsJogador();
                        Scene proximaBatalhaScene = criarSceneBatalha(proximaBatalha, pokemonsComHpAtual,stage,PlayerCard);
                        battleScreenService.changeScene(stage,proximaBatalhaScene);
                    });
                    pause.play();
                } else {
                    labelMensagem.setText(mensagem + "\n\nPARABÉNS! Você venceu todas as batalhas!");
                }
            }
        }

        if (batalhaService.isJogoTerminado()) {
            labelMensagem.setText(mensagem + "\nGAME OVER!");
        }
    }

    private void mostrarTrocaPokemon() {
        if (batalhaService.isBatalhaTerminada() || batalhaService.isJogoTerminado()) {
            labelMensagem.setText("A batalha terminou!");
            return;
        }

        trocaPokemonBox.getChildren().clear();
        Label labelTitulo = new Label("Escolha um pokémon para trocar:");
        labelTitulo.setStyle("-fx-font-size: 18px; -fx-text-fill: yellow; -fx-font-weight: bold;");
        trocaPokemonBox.getChildren().add(labelTitulo);

        List<Pokemon> disponiveis = batalhaService.getPokemonsDisponiveisParaTroca();

        if (disponiveis.isEmpty()) {
            Label labelSemOpcoes = new Label("Não há pokémons disponíveis para troca!");
            labelSemOpcoes.setStyle(labelStyle);
            trocaPokemonBox.getChildren().add(labelSemOpcoes);
        } else {
            for (Pokemon pokemon : disponiveis) {
                Button btnTrocar = new Button(pokemon.getNome() + " (HP: " + pokemon.getHp() + "/100)");
                btnTrocar.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: rgba(0, 100, 0, 0.8); -fx-padding: 10px; -fx-min-width: 200px;");
                btnTrocar.setOnAction(e -> trocarPokemon(pokemon));
                battleScreenService.animacaoHover(btnTrocar);
                trocaPokemonBox.getChildren().add(btnTrocar);
            }
        }

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-background-color: rgba(100, 0, 0, 0.8); -fx-padding: 8px;");
        btnCancelar.setOnAction(e -> trocaPokemonBox.setVisible(false));
        trocaPokemonBox.getChildren().add(btnCancelar);

        trocaPokemonBox.setVisible(true);
    }

    private void trocarPokemon(Pokemon pokemon) {
        int indice = batalhaService.getPokemonsJogador().indexOf(pokemon);
        String mensagem = batalhaService.jogadorTrocaPokemon(indice);

        if (mensagem != null) {
            labelMensagem.setText(mensagem);
            battleScreenService.atualizarBarraHP(hpJogadorBar,pokemonJogador.getHp(),100);
            battleScreenService.atualizarBarraHP(hpMaquinaBar,pokemonMaquina.getHp(),100);
            trocaPokemonBox.setVisible(false);
        } else {
            labelMensagem.setText("Não foi possível trocar de pokémon!");
        }
    }

    public void setOnVoltarAction(Runnable action) {
        this.onVoltarAction = action;
    }


    public TelaBatalha() {
    }
}
