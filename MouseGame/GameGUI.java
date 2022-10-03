package MouseGame;
/**
 * GUI of the game
 * @author joel-
 *
 */
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Timer;


/**
 * 
 * @author Joel Hempel
 * @since March 15th 2022
 * Plays a game similar to "osu!"
 */
public class GameGUI extends Application{
	private ArrayList<Circle> circleList;
	private BorderPane root, gameField, scoreBoard;
	private Rectangle middleSquare, enterName, leaderBoardSquare;
	private TextField playerNameField;
	private VBox menuSelectionBox, titleBox;
	private Text playGame, readInstructions, LeaderboardText;
	private Text time, score, accuracy;
	private VBox gameInfo;
//	GameTimer timer;
	private Text LeaderboardTextField, instructionField;
	private double scoreValue = 0; 
	private double accuracyValue = 100, totalClicks = 0, falseClicks = 0, correctClicks = 0;
	private int numberOfItems = 0;
	private long timeLeft = 10;
	private Boolean gameStarted = false;
	private Timeline timeline;
	private Leaderboard leaderboard;
	private StringBuilder sb;
	private Button returnButton;
	private HBox returnButtonBox;
	
	
	public void start(Stage primaryStage) {
		root = new BorderPane();
		
		//Font Style through https://docs.oracle.com/javafx/2/text/jfxpub-text.htm
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
		 
//		Text t = new Text();
//		t.setEffect(ds);
//		t.setCache(true);
//		t.setX(10.0f);
//		t.setY(270.0f);
//		t.setFill(Color.RED);
//		t.setText("JavaFX drop shadow...");
//		t.setFont(Font.font(null, FontWeight.BOLD, 32));
		
		//MENU
		Text mainTitle = new Text("Mouse Accuracy Game");
		mainTitle.setEffect(ds);
		mainTitle.setFill(Color.CRIMSON);
		mainTitle.setFont(Font.font(null, FontWeight.BOLD, 64));
		
		titleBox = new VBox(50);
		titleBox.setPadding(new Insets(10));
		titleBox.getChildren().addAll(mainTitle);
		titleBox.setAlignment(Pos.CENTER);

		playGame = new Text("Play Game");
		playGame.setEffect(ds);
		playGame.setFill(Color.FIREBRICK);
		playGame.setFont(Font.font(null, FontWeight.BOLD, 32));
		playGame.setOnMouseClicked(new playGameEvent());
//		middleSquare = new Rectangle(0, 0, 780, 780);
		middleSquare = new Rectangle(780, 780);
		middleSquare.setOnMouseClicked(new ClickListener());
		middleSquare.setStroke(Color.BEIGE);
		middleSquare.setStrokeWidth(10);
		middleSquare.setFill(Color.LIGHTGRAY);
		root.setCenter(middleSquare);
		BorderPane.setAlignment(middleSquare, Pos.CENTER);
		
		readInstructions = new Text("Read Instructions");
		readInstructions.setEffect(ds);
		readInstructions.setFill(Color.FIREBRICK);
		readInstructions.setFont(Font.font(null, FontWeight.BOLD, 32));
		readInstructions.setOnMouseClicked(new showInstructionEvent());
		LeaderboardText = new Text("Leaderboard");
		LeaderboardText.setEffect(ds);
		LeaderboardText.setFill(Color.FIREBRICK);
		LeaderboardText.setFont(Font.font(null, FontWeight.BOLD, 32));
		LeaderboardText.setOnMouseClicked(new showLeaderboardTextHandler());
		menuSelectionBox = new VBox(25);
		menuSelectionBox.getChildren().addAll( playGame, readInstructions, LeaderboardText);
		menuSelectionBox.setAlignment(Pos.CENTER);
		leaderBoardSquare = new Rectangle(780, 780);
		leaderBoardSquare.setFill(Color.INDIANRED);
		root.setCenter(leaderBoardSquare);
		leaderBoardSquare.setVisible(false);
		root.setCenter(menuSelectionBox);
		root.setTop(titleBox);
		
		//Navigation
		returnButton = new Button("Return");
		
//		returnButton.
		returnButtonBox = new HBox(returnButton);
		returnButtonBox.setPadding(new Insets(20));
//		returnButton.setPadding(new Insets(5));
		returnButton.setOnAction(new ReturnListener());
		
		//GAME
		time = new Text(timeLeft +"s");
		score = new Text("0");
		accuracy = new Text(accuracyValue/1.00 + "%");
		sb = new StringBuilder();
		gameInfo = new VBox(10);
		gameInfo.getChildren().addAll(time, score, accuracy);
		gameField = new BorderPane();
		gameField.setTop(gameInfo);
		BorderPane.setAlignment(gameInfo, Pos.TOP_LEFT);
		gameField.setVisible(false);
		root.getChildren().addAll(gameField);
		circleList = new ArrayList<Circle>();
		
		//LeaderboardText
		scoreBoard = new BorderPane();
		scoreBoard.setVisible(false);
		leaderboard = new Leaderboard("C:\\Users\\joel-\\eclipse-JavaFX\\TestJavaFx\\src\\MouseGame\\Leaderboard.txt");
		LeaderboardTextField = new Text(leaderboard.toString());
		LeaderboardTextField.setEffect(ds);
		LeaderboardTextField.setFill(Color.ORANGERED);
		LeaderboardTextField.setFont(Font.font(null, FontWeight.BOLD, 40));
		LeaderboardText.setOnMouseClicked(new showLeaderboardTextHandler());
		LeaderboardTextField.setTextAlignment(TextAlignment.CENTER);
//		LeaderboardTextField add insets
		scoreBoard.setCenter(LeaderboardTextField);
		root.getChildren().addAll(scoreBoard);
		
		//End of Game
		enterName = new Rectangle(100, 70);
		enterName.setFill(Color.GRAY);
		enterName.setStroke(Color.BLACK);
		enterName.setStrokeWidth(5);
		playerNameField = new TextField("Nickname:");
		playerNameField.setEffect(ds);
//		playerNameField.set(Color.FIREBRICK);
		playerNameField.setFont(Font.font(null, FontWeight.BOLD, 25));
		
		
		//Instructions
		instructionField = new Text("THIS CONTAINS INSTRUCTIONS\n TESTTESTTEST");
//		BorderPane.setAlignment(instructionField, Pos.CENTER);
		root.getChildren().addAll(instructionField);
		instructionField.setVisible(false);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setHeight(800);
		primaryStage.setWidth(800);
		primaryStage.setTitle("Mouse Test Game - Menu");
		primaryStage.show();
		
		

	}
	
	//NEEDS MORE WORK
	public void showMenu() {
//		System.out.println(root.getChildren());
		titleBox.setVisible(true);
		menuSelectionBox.setVisible(true);
		middleSquare.setVisible(true);
		middleSquare.setFill(Color.LIGHTGRAY);
		middleSquare.setStroke(Color.BEIGE);
		root.getChildren().add(middleSquare);
		root.setCenter(menuSelectionBox);
		
//		mainTitle.setFont(new Font(mainTitle, null, null, null, 0));

		
		
		root.setTop(titleBox);
	}
	
	public static void main(String[] args) {	
		Application.launch(args);
	}
	
	public void createReturnButton() {
		root.setBottom(returnButtonBox);
		BorderPane.setAlignment(returnButtonBox, Pos.BOTTOM_LEFT);
	}
	
	public class playGameEvent implements EventHandler<MouseEvent>{
		
		@Override
		public void handle(MouseEvent e) {
			gameStarted = true;
			menuSelectionBox.setVisible(false);
			titleBox.setVisible(false);
			gameField.setVisible(true);
			
			
		//Code from stack overflow V
			KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), new createCircleEvent());
//					event ->
//					Circle tmpCircle;
//					tmpCircle = new Circle((int)(Math.random() * 701 + 50) , Math.random() * 701 + 50), 30, Color.FUCHSIA);
//					root.getChildren().addAll(tmpCircle));
					
					//generates a new circle on the plane randomly every 0.5 seconds
			timeline = new Timeline(keyFrame);
			
			timeline.setCycleCount(20);
			timeline.play();
			
			//until here ^
		}
		
		
		
	}
	
	public class showInstructionEvent implements EventHandler<MouseEvent>{
		
		@Override
		public void handle(MouseEvent e) {
			titleBox.setVisible(false);
			instructionField.setVisible(true);
			menuSelectionBox.setVisible(false);
			createReturnButton();
		}
	}
	
	public class showLeaderboardTextHandler implements EventHandler<MouseEvent>{
		
		@Override
		public void handle(MouseEvent e) {
			titleBox.setVisible(false);
			menuSelectionBox.setVisible(false);
			middleSquare.setFill(Color.LIGHTGRAY);
			scoreBoard.setVisible(true);
			leaderBoardSquare.setVisible(true);
			root.setCenter(leaderBoardSquare);
			root.setCenter(LeaderboardTextField);
			
			createReturnButton();
			
		}
	}
	
	
//	public String getLeaderboardText() {
//		String output = "";
//		File fileInput = new File("C:\\Users\\joel-\\eclipse-JavaFX\\TestJavaFx\\src\\MouseGame\\Leaderboard.txt");
//		try (Scanner input = new Scanner(fileInput)){
//			
//			while (input.hasNext()) {
//				output += input.nextLine() + "\n";
//			}
//		}
//		catch (IOException e){
//			e.printStackTrace();
//		}
//		
//		return output;
//		
//	}
	
	public class createCircleEvent implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent e) {
			if (timeLeft <= 0) {
				timeline.stop();
				root.getChildren().clear();
				root.setCenter(enterName);
				root.setCenter(playerNameField);
				playerNameField.resize(enterName.getWidth()*0.5, enterName.getHeight()*0.5);
				playerNameField.setPadding(new Insets(enterName.getWidth()*0.4,0 , enterName.getWidth()*0.4, 0)); //NEED TO WORK ON THIS
				BorderPane.setAlignment(playerNameField, Pos.CENTER);
				BorderPane.setAlignment(enterName, Pos.CENTER);
				
			}
			if ((int)(Math.random()*100 + 1) > 20){
				Circle tmpCircle = new Circle((int)(Math.random() * 701 + 50) , Math.random() * 701 + 50, 30, Color.FUCHSIA);
				tmpCircle.setOnMouseClicked(new ItemClickedEvent());
				circleList.add(tmpCircle);
				root.getChildren().addAll(tmpCircle);
				numberOfItems++;
			}
			timeLeft -= 0.5;
			time.setText(timeLeft + "s");
			
		}
	}
	
	public class ItemClickedEvent implements EventHandler<MouseEvent>{
		
		@Override
		public void handle(MouseEvent e) {
			correctClicks++;
			totalClicks++;
			root.getChildren().remove(e.getSource());
			circleList.remove(e.getSource());
			scoreValue += 100;
			score.setText("" + (scoreValue));
			accuracyValue =(correctClicks)/totalClicks;
			String tempString = String.format("%2.2f", accuracyValue*100);
			accuracy.setText(tempString+"%");
		}
	}

	public class ClickListener implements EventHandler<MouseEvent>{
		
		@Override
		public void handle(MouseEvent e) {
			if (gameStarted) {
				falseClicks++;
				totalClicks++;
				scoreValue -= 50;
				score.setText("" + (scoreValue));
				accuracyValue =(correctClicks)/totalClicks;
				String tempString = String.format("%2.2f", accuracyValue*100);
				accuracy.setText(tempString+"%");
			}
			
		}
		
	}
	
	public class ReturnListener implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent e) {
			root.getChildren().clear();
			
			showMenu();
			
		}
	}
	
	
}


