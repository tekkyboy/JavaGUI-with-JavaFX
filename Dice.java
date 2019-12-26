import java.util.Arrays;
import java.util.Comparator;

import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.*;

/**
 * 
 * @author Parayao_K
 *
 */

public class Dice extends Application {

	private Button playButton;
	private Image dice1side;
	private Image dice1sideheld;
	private Image dice2side;
	private Image dice2sideheld;
	private Image dice3side;
	private Image dice3sideheld;
	private Image dice4side;
	private Image dice4sideheld;
	private Image dice5side;
	private Image dice5sideheld;
	private Image dice6side;
	private Image dice6sideheld;
	private int counter;
	private int totalScore;
	private int roundScore;
	private Label roundScoreLabel;
	private Label rollsLabel;
	private Label totalScoreLabel;
	private Label winningHandLabel;
	private Label fiveOfAKindLabel;
	private Label fiveOfAKindScoreLabel;
	private Label straightLabel;
	private Label straightScoreLabel;
	private Label fourOfAKindLabel;
	private Label fourOfAKindScoreLabel;
	private Label fullHouseLabel;
	private Label fullHouseScoreLabel;
	private Label threeOfAKindLabel;
	private Label threeOfAKindScoreLabel;
	private Label twoPairsLabel;
	private Label twoPairsScoreLabel;
	private Label twoOfAKindLabel;
	private Label twoOfAKindScoreLabel;
	private Button[] heldLabelArray = new Button[5];
	private DiceObject[] diceArray = new DiceObject[5];
	private ImageView[] diceViewArray = new ImageView[5];
	private boolean initState = true;
	private String disableColor = "#D3D3D3";

	private class DiceObject {
		int index;
		int sides;
		int faceValue;
		boolean isHeld = false;

		public int getSides() {
			if (sides == 0) {
				faceValue = 1;
			} else if (sides == 1) {
				faceValue = 2;
			} else if (sides == 2) {
				faceValue = 3;
			} else if (sides == 3) {
				faceValue = 4;
			} else if (sides == 4) {
				faceValue = 5;
			} else if (sides == 5) {
				faceValue = 6;
			}
			return faceValue;
		}

		public void roll() {
			if (!isHeld) {
				sides = (int) (5.0 * Math.random());
			}
		}

		public void isHeld(boolean held) {
			isHeld = held;
		}

		public boolean getHeld() {
			return isHeld;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	}

	public static void main(String[] args) {
		launch(args);
	} 

	public void start(Stage primaryStage) {
		dice1side = new Image("file:./res/Dice1.png");
		dice1sideheld = new Image("file:./res/Dice1Held.png");
		dice2side = new Image("file:./res/Dice2.png");
		dice2sideheld = new Image("file:./res/Dice2Held.png");
		dice3side = new Image("file:./res/Dice3.png");
		dice3sideheld = new Image("file:./res/Dice3Held.png");
		dice4side = new Image("file:./res/Dice4.png");
		dice4sideheld = new Image("file:./res/Dice4Held.png");
		dice5side = new Image("file:./res/Dice5.png");
		dice5sideheld = new Image("file:./res/Dice5Held.png");
		dice6side = new Image("file:./res/Dice6.png");
		dice6sideheld = new Image("file:./res/Dice6Held.png");

		roundScoreLabel = new Label();
		totalScoreLabel = new Label();
		rollsLabel = new Label();
		winningHandLabel = new Label();

		fiveOfAKindLabel = new Label("Five of A Kind");
		fiveOfAKindLabel.setId("winning-label");

		fiveOfAKindScoreLabel = new Label("10");
		fiveOfAKindScoreLabel.setId("winning-label");

		straightLabel = new Label("Straight");
		straightLabel.setId("winning-label");

		straightScoreLabel = new Label("08");
		straightScoreLabel.setId("winning-label");

		fourOfAKindLabel = new Label("Four of a Kind");
		fourOfAKindLabel.setId("winning-label");

		fourOfAKindScoreLabel = new Label("07");
		fourOfAKindScoreLabel.setId("winning-label");

		fullHouseLabel = new Label("Full House");
		fullHouseLabel.setId("winning-label");

		fullHouseScoreLabel = new Label("06");
		fullHouseScoreLabel.setId("winning-label");

		threeOfAKindLabel = new Label("Three of a Kind");
		threeOfAKindLabel.setId("winning-label");

		threeOfAKindScoreLabel = new Label("05");
		threeOfAKindScoreLabel.setId("winning-label");

		twoPairsLabel = new Label("Two Pairs");
		twoPairsLabel.setId("winning-label");

		twoPairsScoreLabel = new Label("04");
		twoPairsScoreLabel.setId("winning-label");

		twoOfAKindLabel = new Label("Two of a Kind");
		twoOfAKindLabel.setId("winning-label");

		twoOfAKindScoreLabel = new Label("01");
		twoOfAKindScoreLabel.setId("winning-label");

		for (int i = 0; i < 5; i++) {
			ImageView imageView = new ImageView(dice1side);
			imageView.setPreserveRatio(true);
			diceViewArray[i] = imageView;
			DiceObject dice = new DiceObject();
			dice.setIndex(i);
			diceArray[i] = dice;
			heldLabelArray[i] = new Button("HOLD");
			heldLabelArray[i].setVisible(false);
		}

		EventHandler<MouseEvent> mouseEventHandlerCounter = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!initState) {
					resetLabel();
				}
				if (counter < 5) {
					counter++;
					rollsLabel.setText("Dice Roll:\t\t\t" + counter);
					roundScoreLabel.setText("Round Score: " + 0);
					totalScoreLabel.setText("Total Score: " + totalScore);

					Arrays.sort(diceArray, Comparator.comparing(DiceObject::getSides));

					examineDice();

					Arrays.sort(diceArray, Comparator.comparing(DiceObject::getIndex));

					if (counter == 3) {
						rollsLabel.setText("Dice Roll:\t\t\t" + 3);
						playButton.setText("Play Again");
					}
					if (counter == 4) {
						restartGame();
						counter = 0;
						totalScore = 0;
					}
				} else {
					counter = 0;
				}
			}
		};

		EventHandler<MouseEvent> mouseEventHandlerChangeColour = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				playButton.setStyle("-fx-effect: dropshadow(Gaussian, yellow, 5, 0, 2, 1)");
			}
		};

		EventHandler<MouseEvent> mouseEventHandlerDefaultColour = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				playButton.setStyle(null);
			}
		};

		roundScoreLabel.setText("Press the Roll Dice");
		totalScoreLabel.setText("button to begin round.");
		playButton = new Button();
		playButton.setText("Roll Dice");
		playButton.setOnAction(new RollEventHandler());
		playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandlerCounter);
		playButton.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEventHandlerChangeColour);
		playButton.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEventHandlerDefaultColour);

		for (int i = 0; i < 5; i++) {
			ImageView diceView = diceViewArray[i];
			DiceObject dice = diceArray[i];
			Button heldButton = heldLabelArray[i];
			diceViewArray[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					if (!initState) {
						if (!dice.getHeld()) {
							if (diceView.getImage().equals(dice1side)) {
								diceView.setImage(dice1sideheld);
							} else if (diceView.getImage().equals(dice2side)) {
								diceView.setImage(dice2sideheld);
							} else if (diceView.getImage().equals(dice3side)) {
								diceView.setImage(dice3sideheld);
							} else if (diceView.getImage().equals(dice4side)) {
								diceView.setImage(dice4sideheld);
							} else if (diceView.getImage().equals(dice5side)) {
								diceView.setImage(dice5sideheld);
							} else if (diceView.getImage().equals(dice6side)) {
								diceView.setImage(dice6sideheld);
							}
							dice.isHeld(true);
							heldButton.setVisible(true);
						} else {
							if (diceView.getImage().equals(dice1sideheld)) {
								diceView.setImage(dice1side);
							} else if (diceView.getImage().equals(dice2sideheld)) {
								diceView.setImage(dice2side);
							} else if (diceView.getImage().equals(dice3sideheld)) {
								diceView.setImage(dice3side);
							} else if (diceView.getImage().equals(dice4sideheld)) {
								diceView.setImage(dice4side);
							} else if (diceView.getImage().equals(dice5sideheld)) {
								diceView.setImage(dice5side);
							} else if (diceView.getImage().equals(dice6sideheld)) {
								diceView.setImage(dice6side);
							}
							dice.isHeld(false);
							heldButton.setVisible(false);
						}
					}
				}
			});
		}

		BorderPane borderPane = new BorderPane();
		GridPane gridPaneLeft = new GridPane();
		HBox diceBox = new HBox(10);
		VBox typesOfWinsBox = new VBox(10);
		HBox labelBox = new HBox(2);
		VBox gameStatsBox = new VBox(10);
		VBox playBox = new VBox(10);
		VBox heldLabelBox1 = new VBox(2);
		VBox heldLabelBox2 = new VBox(2);
		VBox heldLabelBox3 = new VBox(2);
		VBox heldLabelBox4 = new VBox(2);
		VBox heldLabelBox5 = new VBox(2);
		VBox winningHandBox = new VBox(2);

		diceBox.setAlignment(Pos.CENTER);
		labelBox.setAlignment(Pos.CENTER);
		playBox.setAlignment(Pos.CENTER);
		heldLabelBox1.setAlignment(Pos.CENTER);
		heldLabelBox2.setAlignment(Pos.CENTER);
		heldLabelBox3.setAlignment(Pos.CENTER);
		heldLabelBox4.setAlignment(Pos.CENTER);
		heldLabelBox5.setAlignment(Pos.CENTER);

		heldLabelBox1.getChildren().addAll(heldLabelArray[0], diceViewArray[0]);
		heldLabelBox2.getChildren().addAll(heldLabelArray[1], diceViewArray[1]);
		heldLabelBox3.getChildren().addAll(heldLabelArray[2], diceViewArray[2]);
		heldLabelBox4.getChildren().addAll(heldLabelArray[3], diceViewArray[3]);
		heldLabelBox5.getChildren().addAll(heldLabelArray[4], diceViewArray[4]);

		gridPaneLeft.add(fiveOfAKindLabel, 0, 0);
		gridPaneLeft.add(straightLabel, 0, 1);
		gridPaneLeft.add(fourOfAKindLabel, 0, 2);
		gridPaneLeft.add(fullHouseLabel, 0, 3);
		gridPaneLeft.add(threeOfAKindLabel, 0, 4);
		gridPaneLeft.add(twoPairsLabel, 0, 5);
		gridPaneLeft.add(twoOfAKindLabel, 0, 6);
		gridPaneLeft.setHgap(100);
		gridPaneLeft.add(fiveOfAKindScoreLabel, 1, 0);
		gridPaneLeft.add(straightScoreLabel, 1, 1);
		gridPaneLeft.add(fourOfAKindScoreLabel, 1, 2);
		gridPaneLeft.add(fullHouseScoreLabel, 1, 3);
		gridPaneLeft.add(threeOfAKindScoreLabel, 1, 4);
		gridPaneLeft.add(twoPairsScoreLabel, 1, 5);
		gridPaneLeft.add(twoOfAKindScoreLabel, 1, 6);

		diceBox.getChildren().addAll(heldLabelBox1, heldLabelBox2, heldLabelBox3, heldLabelBox4, heldLabelBox5);
		typesOfWinsBox.getChildren().addAll(gridPaneLeft);
		gameStatsBox.setAlignment(Pos.CENTER_RIGHT);
		gameStatsBox.getChildren().addAll(roundScoreLabel, totalScoreLabel, rollsLabel);
		winningHandBox.getChildren().add(winningHandLabel);
		winningHandBox.setAlignment(Pos.CENTER);

		playBox.getChildren().addAll(playButton);
		playBox.setPadding(new Insets(30));
		diceBox.setPadding(new Insets(30));
		winningHandBox.setPadding(new Insets(60));

		borderPane.setTop(diceBox);
		borderPane.setLeft(typesOfWinsBox);
		borderPane.setRight(gameStatsBox);
		borderPane.setBottom(playBox);
		borderPane.setCenter(winningHandBox);
		borderPane.setPadding(new Insets(50, 50, 50, 50));

		Scene scene = new Scene(borderPane);
		scene.getStylesheets().add("DiceStyle.css");
		resetLabel();
		primaryStage.setTitle("Dice");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void examineDice() {
		if (checkForFiveOfKind()) {
			roundScore = 10;
			totalScore += roundScore;
			fiveOfAKindLabel.setTextFill(Color.web("#FFD700"));
			fiveOfAKindScoreLabel.setTextFill(Color.web("#FFD700"));
			winningHandLabel.setText("Winning Hand:\nFive of a Kind");
			roundScoreLabel.setText("Your round score:\t\t" + roundScore);
			totalScoreLabel.setText("Your total score:\t\t" + totalScore);
		} else if (checkForStraight()) {
			roundScore = 8;
			totalScore += roundScore;
			straightLabel.setTextFill(Color.web("#FFD700"));
			straightScoreLabel.setTextFill(Color.web("#FFD700"));
			winningHandLabel.setText("Winning Hand:\nStraight");
			roundScoreLabel.setText("Your round score:\t\t" + roundScore);
			totalScoreLabel.setText("Your total score:\t\t" + totalScore);
		} else if (checkForFourOfAKind()) {
			roundScore = 7;
			totalScore += roundScore;
			fourOfAKindLabel.setTextFill(Color.web("#FFD700"));
			fourOfAKindScoreLabel.setTextFill(Color.web("#FFD700"));
			winningHandLabel.setText("Winning Hand:\nFour of a Kind");
			roundScoreLabel.setText("Your round score:\t\t" + roundScore);
			totalScoreLabel.setText("Your total score:\t\t" + totalScore);
		} else if (checkForFullHouse()) {
			roundScore = 6;
			totalScore += roundScore;
			fullHouseLabel.setTextFill(Color.web("#FFD700"));
			fullHouseScoreLabel.setTextFill(Color.web("#FFD700"));
			winningHandLabel.setText("Winning Hand:\nFull House");
			roundScoreLabel.setText("Your round score:\t\t" + roundScore);
			totalScoreLabel.setText("Your total score:\t\t" + totalScore);
		} else if (checkForThreeOfAKind()) {
			roundScore = 5;
			totalScore += roundScore;
			threeOfAKindLabel.setTextFill(Color.web("#FFD700"));
			threeOfAKindScoreLabel.setTextFill(Color.web("#FFD700"));
			winningHandLabel.setText("Winning Hand:\nThree of a Kind");
			roundScoreLabel.setText("Your round score:\t\t" + roundScore);
			totalScoreLabel.setText("Your total score:\t\t" + totalScore);
		} else if (checkForTwoPairs()) {
			roundScore = 4;
			totalScore += roundScore;
			twoPairsLabel.setTextFill(Color.web("#FFD700"));
			twoPairsScoreLabel.setTextFill(Color.web("#FFD700"));
			winningHandLabel.setText("Winning Hand:\nTwo Pairs");
			roundScoreLabel.setText("Your round score:\t\t" + roundScore);
			totalScoreLabel.setText("Your total score:\t\t" + totalScore);
		} else if (checkForTwoOfAKind()) {
			roundScore = 1;
			totalScore += roundScore;
			twoOfAKindLabel.setTextFill(Color.web("#FFD700"));
			twoOfAKindScoreLabel.setTextFill(Color.web("#FFD700"));
			winningHandLabel.setText("Winning Hand:\nTwo of a Kind");
			roundScoreLabel.setText("Your round score:\t\t" + roundScore);
			totalScoreLabel.setText("Your total score:\t\t" + totalScore);
		} else {
			roundScore = 0;
			totalScore += roundScore;
			winningHandLabel.setText("Winning Hand:\nNone");
			roundScoreLabel.setText("Your round score:\t\t" + roundScore);
			totalScoreLabel.setText("Your total score:\t\t" + totalScore);
		}
	}

	public void resetLabel() {
		fiveOfAKindLabel.setTextFill(Color.web(disableColor));
		straightLabel.setTextFill(Color.web(disableColor));
		fourOfAKindLabel.setTextFill(Color.web(disableColor));
		fullHouseLabel.setTextFill(Color.web(disableColor));
		threeOfAKindLabel.setTextFill(Color.web(disableColor));
		twoPairsLabel.setTextFill(Color.web(disableColor));
		twoOfAKindLabel.setTextFill(Color.web(disableColor));
		fiveOfAKindScoreLabel.setTextFill(Color.web(disableColor));
		straightScoreLabel.setTextFill(Color.web(disableColor));
		fourOfAKindScoreLabel.setTextFill(Color.web(disableColor));
		fullHouseScoreLabel.setTextFill(Color.web(disableColor));
		threeOfAKindScoreLabel.setTextFill(Color.web(disableColor));
		twoPairsScoreLabel.setTextFill(Color.web(disableColor));
		twoOfAKindScoreLabel.setTextFill(Color.web(disableColor));
	}

	public boolean checkForFiveOfKind() {
		boolean check = true;
		for (int i = 0; i < 5; i++) {
			DiceObject dice = diceArray[i];
			for (int j = i + 1; j < 5; j++) {
				DiceObject dice2 = diceArray[j];
				if (dice.getSides() != dice2.getSides()) {
					check = false;
					break;
				}
			}
			if (!check) {
				break;
			}
		}
		return check;
	}

	public boolean checkForStraight() {
		boolean check = false;
		int countDice = 0;
		for (int i = 0; i < 4; i++) {
			DiceObject dice = diceArray[i];
			DiceObject dice2 = diceArray[i + 1];
			if (dice.getSides() + 1 == dice2.getSides()) {
				countDice++;
			}
		}
		if (countDice == 4) {
			check = true;
		}
		return check;
	}

	public boolean checkForFourOfAKind() {
		boolean check = false;

		if (diceArray[0].getSides() == diceArray[1].getSides() && diceArray[0].getSides() == diceArray[2].getSides()
				&& diceArray[0].getSides() == diceArray[3].getSides()
				&& diceArray[1].getSides() == diceArray[2].getSides()
				&& diceArray[1].getSides() == diceArray[3].getSides()
				&& diceArray[3].getSides() == diceArray[4].getSides()) {
			check = true;
		} else if (diceArray[0].getSides() == diceArray[1].getSides()
				&& diceArray[0].getSides() == diceArray[2].getSides()
				&& diceArray[0].getSides() == diceArray[4].getSides()
				&& diceArray[1].getSides() == diceArray[2].getSides()
				&& diceArray[1].getSides() == diceArray[4].getSides()
				&& diceArray[2].getSides() == diceArray[4].getSides()) {
			check = true;
		} else if (diceArray[0].getSides() == diceArray[2].getSides()
				&& diceArray[0].getSides() == diceArray[3].getSides()
				&& diceArray[0].getSides() == diceArray[4].getSides()
				&& diceArray[2].getSides() == diceArray[3].getSides()
				&& diceArray[2].getSides() == diceArray[4].getSides()
				&& diceArray[3].getSides() == diceArray[4].getSides()) {
			check = true;
		} else if (diceArray[0].getSides() == diceArray[1].getSides()
				&& diceArray[0].getSides() == diceArray[3].getSides()
				&& diceArray[0].getSides() == diceArray[4].getSides()
				&& diceArray[1].getSides() == diceArray[3].getSides()
				&& diceArray[1].getSides() == diceArray[4].getSides()
				&& diceArray[3].getSides() == diceArray[4].getSides()) {
			check = true;
		} else if (diceArray[1].getSides() == diceArray[2].getSides()
				&& diceArray[1].getSides() == diceArray[3].getSides()
				&& diceArray[1].getSides() == diceArray[4].getSides()
				&& diceArray[2].getSides() == diceArray[3].getSides()
				&& diceArray[2].getSides() == diceArray[4].getSides()
				&& diceArray[3].getSides() == diceArray[4].getSides()) {
			check = true;
		}
		return check;

	}

	public boolean checkForFullHouse() {
		boolean check = false;
		if (diceArray[0].getSides() == diceArray[2].getSides() && diceArray[3].getSides() == diceArray[4].getSides()) {
			check = true;
		}
		if (diceArray[0].getSides() == diceArray[1].getSides() && diceArray[2].getSides() == diceArray[4].getSides()) {
			check = true;
		}
		return check;
	}

	public boolean checkForThreeOfAKind() {
		boolean check = false;
		for (int i = 0; i <= diceArray.length - 3; i++) {
			if (diceArray[i].getSides() == diceArray[i + 2].getSides()) {
				check = true;
			}
		}
		return check;
	}

	public boolean checkForTwoPairs() {
		boolean check = false;

		if (diceArray[0].getSides() == diceArray[1].getSides() && diceArray[2].getSides() == diceArray[3].getSides()) {
			check = true;
		} else if (diceArray[0].getSides() == diceArray[2].getSides()
				&& diceArray[1].getSides() == diceArray[3].getSides()) {
			check = true;
		} else if (diceArray[0].getSides() == diceArray[3].getSides()
				&& diceArray[1].getSides() == diceArray[2].getSides()) {
			check = true;
		} else if (diceArray[0].getSides() == diceArray[1].getSides()
				&& diceArray[3].getSides() == diceArray[4].getSides()) {
			check = true;
		} else if (diceArray[0].getSides() == diceArray[3].getSides()
				&& diceArray[1].getSides() == diceArray[4].getSides()) {
			check = true;
		} else if (diceArray[0].getSides() == diceArray[4].getSides()
				&& diceArray[1].getSides() == diceArray[3].getSides()) {
			check = true;
		} else if (diceArray[1].getSides() == diceArray[2].getSides()
				&& diceArray[3].getSides() == diceArray[4].getSides()) {
			check = true;
		} else if (diceArray[1].getSides() == diceArray[3].getSides()
				&& diceArray[2].getSides() == diceArray[4].getSides()) {
			check = true;
		} else if (diceArray[1].getSides() == diceArray[4].getSides()
				&& diceArray[2].getSides() == diceArray[3].getSides()) {
			check = true;
		}
		return check;
	}

	public boolean checkForTwoOfAKind() {
		boolean check = false;
		if (diceArray[0].getSides() == diceArray[1].getSides() || diceArray[0].getSides() == diceArray[2].getSides()
				|| diceArray[0].getSides() == diceArray[3].getSides()
				|| diceArray[0].getSides() == diceArray[4].getSides()
				|| diceArray[1].getSides() == diceArray[2].getSides()
				|| diceArray[1].getSides() == diceArray[3].getSides()
				|| diceArray[1].getSides() == diceArray[4].getSides()
				|| diceArray[2].getSides() == diceArray[3].getSides()
				|| diceArray[2].getSides() == diceArray[4].getSides()
				|| diceArray[3].getSides() == diceArray[4].getSides()) {
			check = true;
		}
		return check;
	}

	public void restartGame() {
		for (int i = 0; i < 5; i++) {
			DiceObject dice = diceArray[i];
			ImageView diceView = diceViewArray[i];
			dice.isHeld(false);
			diceView.setImage(dice1side);
			diceView.setDisable(true);
			heldLabelArray[i].setVisible(false);
		}
		fiveOfAKindLabel.setTextFill(Color.web(disableColor));
		straightLabel.setTextFill(Color.web(disableColor));
		fourOfAKindLabel.setTextFill(Color.web(disableColor));
		fullHouseLabel.setTextFill(Color.web(disableColor));
		threeOfAKindLabel.setTextFill(Color.web(disableColor));
		twoPairsLabel.setTextFill(Color.web(disableColor));
		twoOfAKindLabel.setTextFill(Color.web(disableColor));
		fiveOfAKindScoreLabel.setTextFill(Color.web(disableColor));
		straightScoreLabel.setTextFill(Color.web(disableColor));
		fourOfAKindScoreLabel.setTextFill(Color.web(disableColor));
		fullHouseScoreLabel.setTextFill(Color.web(disableColor));
		threeOfAKindScoreLabel.setTextFill(Color.web(disableColor));
		twoPairsScoreLabel.setTextFill(Color.web(disableColor));
		twoOfAKindScoreLabel.setTextFill(Color.web(disableColor));
		playButton.setText("Roll Dice");
		rollsLabel.setText("Dice Roll:\t\t" + 0);
		winningHandLabel.setText("Winning Hand:\nNone");
		roundScoreLabel.setText("Round Score:\t\t" + 0);
		totalScoreLabel.setText("Total Score:\t\t" + 0);
		initState = true;
	}

	private class RollEventHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			initState = false;
			for (int i = 0; i < 5; i++) {
				DiceObject dice = diceArray[i];
				ImageView diceView = diceViewArray[i];
				dice.roll();
				diceView.setDisable(false);
				if (!dice.getHeld()) {
					if (dice.getSides() == 0) {
						diceView.setImage(dice1side);
					} else if (dice.getSides() == 1) {
						diceView.setImage(dice2side);
					} else if (dice.getSides() == 2) {
						diceView.setImage(dice3side);
					} else if (dice.getSides() == 3) {
						diceView.setImage(dice4side);
					} else if (dice.getSides() == 4) {
						diceView.setImage(dice5side);
					} else if (dice.getSides() == 5) {
						diceView.setImage(dice6side);
					}
				}
			}
		}
	}
}