package gui.AppStateMachine;

import gui.App;
import gui.Controller.StartingLogoController;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.MotionBlur;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import animatefx.animation.Tada;
import animatefx.animation.ZoomOut;
import animatefx.animation.FadeOutLeft;
import animatefx.animation.RubberBand;

import java.util.ArrayList;

import animatefx.animation.BounceInUp;
import lib.State;

public class StartingLogosState implements State {
    private String state_name = "Starting Logos State";
    private static final StartingLogosState instance = new StartingLogosState();
    private StackPane black_background = new StackPane();
    private StackPane starting_logos = new StackPane();

    private final double MAX_FONT_SIZE = 30.0;
    private Font pixel_font = Font.loadFont(getClass().getResourceAsStream("/font/pixel_font.ttf"), MAX_FONT_SIZE);

    private StartingLogosState() {
        // Constructeur privé pour empêcher la création d'autres instances
    }

    public static StartingLogosState getInstance() {
        return instance;
    }

    public String showState() {
        return state_name;
    }

    private ImageView createGhost(String name) {
        MotionBlur motionBlur = new MotionBlur();
        motionBlur.setRadius(25);
        motionBlur.setAngle(180);

        ImageView ghost = new ImageView(new Image(getClass().getResourceAsStream("/logos/3d_" + name + ".png")));
        ghost.setFitHeight(100);
        ghost.setFitWidth(100);
        ghost.setPreserveRatio(true);
        ghost.setRotationAxis(Rotate.Y_AXIS);
        ghost.setRotate(180);
        ghost.setEffect(motionBlur);
        return ghost;
    }

    private TranslateTransition cTranslateTransition(ImageView ghost, double fromX, double fromY, double toX) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), ghost);
        translateTransition.setFromX(fromX); // Position de départ en X
        translateTransition.setFromY(fromY); // Position de départ en Y

        translateTransition.setToX(toX);
        translateTransition.setCycleCount(1);
        return translateTransition;
    }

    private void createBackground(String bgPath) {
        starting_logos.setMaxHeight(App.pStage.getHeight());
        starting_logos.setMaxWidth(App.pStage.getWidth());
        starting_logos.setStyle("-fx-background-image: url('" + bgPath + "'); " +
                "-fx-background-repeat: repeat; " +
                "-fx-background-size: cover;");
        System.out.println(App.pStage.getHeight() + " " + App.pStage.getWidth());
    }

    private ArrayList<ImageView> createGhosts(boolean isEaten) {
        ArrayList<ImageView> ghosts = new ArrayList<ImageView>();
        if (isEaten) {
            for (int i = 0; i < 4; i++) {
                ghosts.add(createGhost("eat"));
            }
        } else {
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0:
                        ghosts.add(createGhost("inky"));
                        break;
                    case 1:
                        ghosts.add(createGhost("pinky"));
                        break;
                    case 2:
                        ghosts.add(createGhost("clyde"));
                        break;
                    case 3:
                        ghosts.add(createGhost("blinky"));
                        break;
                }
            }
        }
        return ghosts;
    }

    private ArrayList<TranslateTransition> createTranslateTransitions(ArrayList<ImageView> ghosts, String direction) {
        ArrayList<TranslateTransition> translateTransitions = new ArrayList<TranslateTransition>();
        double halfScreenHeight = starting_logos.getMaxHeight() / 2;
        double halfScreenWidth = starting_logos.getMaxWidth() / 2;
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    if (direction == "left") {
                        translateTransitions.add(cTranslateTransition(ghosts.get(i), -halfScreenWidth - 100,
                                -halfScreenHeight + 100, halfScreenWidth + 100));
                    } else if (direction == "right") {
                        translateTransitions.add(cTranslateTransition(ghosts.get(i), halfScreenWidth + 100,
                                -halfScreenHeight + 100, -halfScreenWidth - 100));
                    }
                    break;
                case 1:
                    if (direction == "left") {
                        translateTransitions.add(cTranslateTransition(ghosts.get(i), -halfScreenWidth - 100, -75,
                                halfScreenWidth + 100));
                    } else if (direction == "right") {
                        translateTransitions.add(cTranslateTransition(ghosts.get(i), halfScreenWidth + 100, 0 - 75,
                                -halfScreenWidth - 100));
                    }
                    break;
                case 2:
                    if (direction == "left") {
                        translateTransitions.add(
                                cTranslateTransition(ghosts.get(i), -halfScreenWidth - 100, 75, halfScreenWidth + 100));
                    } else if (direction == "right") {
                        translateTransitions.add(
                                cTranslateTransition(ghosts.get(i), halfScreenWidth + 100, 75, -halfScreenWidth - 100));
                    }
                    break;
                case 3:
                    if (direction == "left") {
                        translateTransitions.add(cTranslateTransition(ghosts.get(i), -halfScreenWidth - 100,
                                halfScreenHeight - 100, halfScreenWidth + 100));
                    } else if (direction == "right") {
                        translateTransitions.add(cTranslateTransition(ghosts.get(i), halfScreenWidth + 100,
                                halfScreenHeight - 100, -halfScreenWidth - 100));
                    }
                    break;
            }
        }
        return translateTransitions;
    }

    private Timeline createTranslateTransition(ArrayList<TranslateTransition> translateTransitions,
            ArrayList<ImageView> ghosts) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            for (TranslateTransition translateTransition : translateTransitions) {
                translateTransition.play();
            }
            for (ImageView ghost : ghosts) {
                ghost.setVisible(false);
                starting_logos.getChildren().add(ghost);

            }
            Timeline zizi = new Timeline(new KeyFrame(Duration.seconds(0.2), event2 -> {
                for (ImageView ghost : ghosts) {
                    ghost.setVisible(true);
                }
            }));
            zizi.play(); // On lance zizi
        }));
        return timeline;
    }

    private Timeline createBouncingTitle(StackPane pane, Label text1, Label text2) {
        return new Timeline(new KeyFrame(Duration.seconds(1.5), event0 -> {
            pane.setVisible(false);
            starting_logos.getChildren().removeAll();
            starting_logos.getChildren().add(pane);
            BounceInUp bounce8h30 = new BounceInUp(text1);
            BounceInUp bounceStudio = new BounceInUp(text2);
            bounce8h30.play();
            bounceStudio.play();
            Timeline jpp = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> {
                pane.setVisible(true);
            }));
            jpp.play();
            Timeline blurText = new Timeline(new KeyFrame(Duration.seconds(2), event3 -> {
                GaussianBlur gaussianBlur = new GaussianBlur();
                gaussianBlur.setRadius(10);
                text1.setEffect(gaussianBlur);
                text2.setEffect(gaussianBlur);
            }));
            blurText.play();
        }));
    }

    private Timeline createFadeOut(StackPane p) {
        return new Timeline(new KeyFrame(Duration.seconds(2.0), event2 -> {
            FadeOut fadeOutTransitionText1 = new FadeOut(p);
            fadeOutTransitionText1.play();
        }));
    }

    private Label crLabel(String text) {
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.setFont(pixel_font);
        label.setTextFill(javafx.scene.paint.Color.WHITE);
        return label;
    }

    private void createCharacterIcon(BorderPane character, String name) {
        Image ch = new Image(getClass().getResourceAsStream("/logos/3d_" + name + ".png"));
        ImageView viewCharacter = new ImageView(ch);
        Image bordePixel = new Image(getClass().getResourceAsStream("/logos/border_pixel.png"));
        ImageView viewBorderPixel = new ImageView(bordePixel);
        viewBorderPixel.setFitHeight(100);
        viewBorderPixel.setFitWidth(200);
        Label label = crLabel(name);
        viewCharacter.setFitHeight(400);
        viewCharacter.setFitWidth(400);
        viewCharacter.setPreserveRatio(true);

        // Frame the label with viewBorderPixel
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(viewBorderPixel, label);
        stackPane.setAlignment(Pos.CENTER);

        character.setBottom(viewCharacter);
        character.setTop(stackPane);

    }

    private Timeline createPopCharacter(String name, Group group) {
        BorderPane character = new BorderPane();
        Tada lightSpeedIn = new Tada(character);
        character.setVisible(false);
        createCharacterIcon(character, name);
        group.getChildren().add(character);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            lightSpeedIn.play();
            character.setVisible(true);
        }));
        return timeline;
    }

    private void createPopCharacters() {
        Group characters = new Group();
        starting_logos.getChildren().add(characters);
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(10);
        Timeline pacmanPop = createPopCharacter("pacman", characters);
        Timeline blinkyPop = createPopCharacter("blinky", characters);
        Timeline pinkyPop = createPopCharacter("pinky", characters);
        Timeline inkyPop = createPopCharacter("inky", characters);
        Timeline clydePop = createPopCharacter("clyde", characters);

        pacmanPop.play();
        new RubberBand(starting_logos).play();
        pacmanPop.setOnFinished(event -> {
            ZoomOut zoomPac = new ZoomOut(characters.getChildren().get(0));
            Timeline zoomOut = new Timeline(new KeyFrame(Duration.seconds(1.5), event2 -> {
                zoomPac.play();
                new RubberBand(starting_logos).play();
            }));
            zoomOut.play();
            blinkyPop.play();
            blinkyPop.setOnFinished(event2 -> {
                ZoomOut zoomBlinky = new ZoomOut(characters.getChildren().get(1));
                Timeline zoomOutBlinky = new Timeline(new KeyFrame(Duration.seconds(1.5), event3 -> {
                    zoomBlinky.play();
                    new RubberBand(starting_logos).play();
                }));
                zoomOutBlinky.play();
                pinkyPop.play();
            });
        });
        pinkyPop.setOnFinished(event -> {
            ZoomOut zoomPinky = new ZoomOut(characters.getChildren().get(2));
            Timeline zoomOutPinky = new Timeline(new KeyFrame(Duration.seconds(1.5), event2 -> {
                zoomPinky.play();
                new RubberBand(starting_logos).play();
            }));
            zoomOutPinky.play();
            inkyPop.play();
        });
        inkyPop.setOnFinished(event -> {
            ZoomOut zoomInky = new ZoomOut(characters.getChildren().get(3));
            Timeline zoomOutInky = new Timeline(new KeyFrame(Duration.seconds(1.5), event2 -> {
                zoomInky.play();
                new RubberBand(starting_logos).play();
            }));
            zoomOutInky.play();
            clydePop.play();
        });
        ArrayList<ImageView> ghosts = createGhosts(true);
        ArrayList<TranslateTransition> translateTransitionsRight = createTranslateTransitions(ghosts, "right");
        Timeline timelineTranslateTransition = createTranslateTransition(translateTransitionsRight, ghosts);
        clydePop.setOnFinished(event -> {
            ZoomOut zoomClyde = new ZoomOut(characters.getChildren().get(4));
            Timeline zoomOutClyde = new Timeline(new KeyFrame(Duration.seconds(1.5), event2 -> {
                zoomClyde.play();
            }));
            zoomOutClyde.play();
            Timeline wait = new Timeline(new KeyFrame(Duration.seconds(2), event3 -> {
                timelineTranslateTransition.play();
                FadeOutLeft fadeOut = new FadeOutLeft(starting_logos);
                fadeOut.setDelay(Duration.seconds(0.5));
                fadeOut.setSpeed(0.75);
                fadeOut.play();
            }));wait.play();

        });
        KeyFrame wait = new KeyFrame(Duration.seconds(2), event3 -> {
            App.app_state.changeState(HomeScreenState.getInstance());
        });
        timelineTranslateTransition.setOnFinished(event -> {
            new Timeline(wait).play();
        });

    }

    private void whenGhostTranslateIsFinishedAnimation() {

        double maxX = Screen.getPrimary().getBounds().getMaxX();
        double halfScreen = starting_logos.getMaxHeight() / 2;

        Label textGameBy = crLabel("A game by");
        Label text8h30 = crLabel("8h30");
        Label textStudio = crLabel("Studio");

        StackPane paneGameBy = new StackPane();
        paneGameBy.getChildren().add(textGameBy);
        paneGameBy.opacityProperty().setValue(0.0);

        StackPane pane8h30Studio = new StackPane();
        pane8h30Studio.setMaxHeight(halfScreen);
        pane8h30Studio.setMaxWidth(maxX);
        pane8h30Studio.getChildren().addAll(text8h30, textStudio);
        StackPane.setAlignment(text8h30, Pos.TOP_CENTER);
        StackPane.setAlignment(textStudio, Pos.CENTER);

        FadeIn fadeInTransitionText1 = new FadeIn(paneGameBy);
        fadeInTransitionText1.play();
        starting_logos.getChildren().add(paneGameBy);

        Timeline fadeOutText1 = createFadeOut(paneGameBy);
        fadeOutText1.play();

        Timeline bouncing8h30 = createBouncingTitle(pane8h30Studio, text8h30, textStudio);

        fadeOutText1.setOnFinished(event1 -> {
            bouncing8h30.play();
        });

        bouncing8h30.setOnFinished(event2 -> {
            createPopCharacters();
        });
    }

    public void enter() {
        black_background.setStyle("-fx-background-color: black");
        black_background.setMaxHeight(App.pStage.getHeight());
        black_background.setMaxWidth(App.pStage.getWidth());

        black_background.getChildren().add(starting_logos);
        createBackground("/black_background.jpg");

        ArrayList<ImageView> ghosts = createGhosts(false);
        ArrayList<TranslateTransition> translateTransitionsLeft = createTranslateTransitions(ghosts, "left");

        Timeline timeline = createTranslateTransition(translateTransitionsLeft, ghosts);
        timeline.play();

        translateTransitionsLeft.get(0).setOnFinished(event -> {
            //Remove the ghost from the pane when the animation is finished
            for (ImageView ghost : ghosts) {
                starting_logos.getChildren().remove(ghost);
            }
            whenGhostTranslateIsFinishedAnimation();
        });

        var startingLogoController = new StartingLogoController();
        App.screen.setOnKeyPressed(startingLogoController::keyPressedHandler);

        App.screen.setRoot(black_background);
    }

    public void process(long deltaT) {

    }

    public void exit() {

    }
}
