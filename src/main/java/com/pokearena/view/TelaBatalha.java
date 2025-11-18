package com.pokearena.view;
import com.pokearena.model.Pokemon;
import com.pokearena.service.BatalhaService;
import com.pokearena.service.ScreenService;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.util.List;

public class TelaBatalha {
    private static final String hboxStyle = "-fx-background-color: rgba(0, 0, 0, 0.7); " +
                                            "-fx-border-color: white; " +
                                            "-fx-border-width: 3px; " +
                                            "-fx-border-radius: 15px; " +
                                            "-fx-background-radius: 15px;";

    private static final String labelStyle = "-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold;";
    private static final String mensagemStyle = "-fx-font-size: 48px; -fx-text-fill: yellow; -fx-font-weight: bold;";
    private final ScreenService battleScreenService = new ScreenService();
    private Stage stage;

    private BatalhaService batalhaService;
    private Label labelMensagem;
    private Label labelHpJogador;
    private Label labelHpMaquina;
    private Label labelPokemonJogador;
    private Label labelPokemonMaquina;
    private BorderPane root;
    private VBox trocaPokemonBox;
    private Runnable onVoltarAction;
    private List<Pokemon> pokemonsJogador;

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

   public static String WallpaperBattle1 = "-fx-background-image: url('/srcPokearena/wallpaper2-1.jpg'); " +
                                           "-fx-background-size: cover; " + "-fx-background-position: center center; " +
                                           "-fx-background-repeat: no-repeat;";

   public static String WallpaperBattle2 = "-fx-background-image: url('/srcPokearena/wallpaper3.jpg'); " +
                                           "-fx-background-size: cover; " + "-fx-background-position: center center; " +
                                           "-fx-background-repeat: no-repeat;";

   public static String WallpaperBattle3 = "-fx-background-image: url('/srcPokearena/wallpaper4.jpg'); " +
                                           "-fx-background-size: cover; " + "-fx-background-position: center center; " +
                                           "-fx-background-repeat: no-repeat;";

   public Scene criarSceneBatalha(int numeroBatalha, List<Pokemon> pokemonsJogador,Stage aStage){
       this.pokemonsJogador = pokemonsJogador;
       batalhaService = new BatalhaService(pokemonsJogador, numeroBatalha);
       stage = aStage;

       Button btnAtacar = new Button();
       Button btnPokemon = new Button();
       battleScreenService.configBtn(btnAtacar);
       battleScreenService.configBtn(btnPokemon);
       putBtnAtacarImg(btnAtacar);
       putBtnPokemonImg(btnPokemon);
       battleScreenService.animacaoHover(btnAtacar);
       battleScreenService.animacaoHover(btnPokemon);

       btnAtacar.setOnAction(e -> atacar());
       btnPokemon.setOnAction(e -> mostrarTrocaPokemon());

       HBox actionBar = new HBox(30);
       actionBar.getChildren().addAll(btnAtacar, btnPokemon);
       actionBar.setAlignment(Pos.CENTER);
       actionBar.setStyle(hboxStyle);

       labelMensagem = new Label("Batalha iniciada! " + batalhaService.getNomeMaquina() + " apareceu!");
       labelMensagem.setStyle(mensagemStyle);
       labelMensagem.setWrapText(true);
       labelMensagem.setMaxWidth(1000);
       labelMensagem.setId("mensagemInicial");

       labelPokemonJogador = new Label();
       labelHpJogador = new Label();
       labelPokemonMaquina = new Label();
       labelHpMaquina = new Label();
       atualizarLabelsHP();

       VBox infoBox = new VBox(10);
       infoBox.setAlignment(Pos.CENTER);
       infoBox.getChildren().addAll(labelPokemonJogador, labelHpJogador, labelPokemonMaquina, labelHpMaquina);
       infoBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 15px; -fx-border-radius: 10px; -fx-background-radius: 10px;");

       trocaPokemonBox = new VBox(10);
       trocaPokemonBox.setAlignment(Pos.CENTER);
       trocaPokemonBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.9); -fx-padding: 20px; -fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 15px; -fx-background-radius: 15px;");
       trocaPokemonBox.setVisible(false);

       VBox centerBox = new VBox(20);
       centerBox.setAlignment(Pos.CENTER);
       centerBox.getChildren().addAll(infoBox, trocaPokemonBox,labelMensagem);
       centerBox.setPadding(new Insets(20));
       labelMensagem.setAlignment(Pos.CENTER);


       root = new BorderPane();
       root.setCenter(centerBox);
       root.setBottom(actionBar);
       BorderPane.setAlignment(actionBar, Pos.CENTER);
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

   private void atualizarLabelsHP() {
       Pokemon pokemonJogador = batalhaService.getPokemonAtualJogador();
       Pokemon pokemonMaquina = batalhaService.getPokemonAtualMaquina();

       labelPokemonJogador.setText("Seu Pokémon: " + pokemonJogador.getNome() + " (" + pokemonJogador.getTipo() + ")");
       labelPokemonJogador.setStyle(labelStyle);

       labelHpJogador.setText("HP: " + pokemonJogador.getHp() + "/100");
       labelHpJogador.setStyle(labelStyle);

       labelPokemonMaquina.setText(batalhaService.getNomeMaquina() + ": " + pokemonMaquina.getNome() + " (" + pokemonMaquina.getTipo() + ")");
       labelPokemonMaquina.setStyle(labelStyle);

       labelHpMaquina.setText("HP: " + pokemonMaquina.getHp() + "/100");
       labelHpMaquina.setStyle(labelStyle);
   }

   private void atacar() {
       if (batalhaService.isBatalhaTerminada() || batalhaService.isJogoTerminado()) {
           labelMensagem.setText("A batalha terminou!");
           return;
       }

       String mensagem = batalhaService.jogadorAtaca();
       labelMensagem.setText(mensagem);
       atualizarLabelsHP();

       if (batalhaService.isBatalhaTerminada()) {
           if (batalhaService.getVencedor().equals("JOGADOR")) {
               int proximaBatalha = batalhaService.getNumeroBatalha() + 1;
               if (proximaBatalha <= 3) {
                   labelMensagem.setText(mensagem + "\n\nIniciando próxima batalha...");
                   PauseTransition pause = new PauseTransition(Duration.seconds(2));
                   pause.setOnFinished(e -> {
                       List<Pokemon> pokemonsComHpAtual = batalhaService.getPokemonsJogador();
                       Scene proximaBatalhaScene = criarSceneBatalha(proximaBatalha, pokemonsComHpAtual,stage);
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
           atualizarLabelsHP();
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
