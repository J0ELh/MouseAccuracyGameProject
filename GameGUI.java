/**
 * GUI of the game
 * @author joel-
 *
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.Timer;

public class GameGUI extends Application{
	BorderPane root, gameField;
	Rectangle middleSquare;
	VBox menuSelectionBox, titleBox;
	Text playGame, readInstructions, leaderboard;
	Text time, score, accuracy;
	VBox gameInfo;
	GameTimer timer;
	
	public void start(Stage primaryStage) {
		root = new BorderPane();
		
		//MENU
		Text mainTitle = new Text("Mouse Accuracy Game");
		titleBox = new VBox(10);
		titleBox.getChildren().addAll(mainTitle);
		titleBox.setAlignment(Pos.CENTER);
//		mainTitle.setFont(new Font(mainTitle, null, null, null, 0));
		playGame = new Text("Play Game");
		playGame.setOnMouseClicked(new playGameEvent());
		middleSquare = new Rectangle(0, 0, 800, 800);
		middleSquare.setFill(Color.BURLYWOOD);
		root.getChildren().addAll(middleSquare);
		readInstructions = new Text("Read Instructions");
		leaderboard = new Text("Leaderboard");
		menuSelectionBox = new VBox(25);
		menuSelectionBox.getChildren().addAll( playGame, readInstructions, leaderboard);
		menuSelectionBox.setAlignment(Pos.CENTER);
		root.setCenter(menuSelectionBox);
		root.setTop(titleBox);
		
		//GAME
		time = new Text("20s");
		score = new Text("0");
		accuracy = new Text("100%");
		gameInfo = new VBox(10);
		gameInfo.getChildren().addAll(time, score, accuracy);
		gameField = new BorderPane();
		gameField.setTop(gameInfo);
		gameField.setAlignment(gameInfo, Pos.TOP_LEFT);
		gameField.setVisible(false);
		root.getChildren().addAll(gameField);

		
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setHeight(800);
		primaryStage.setWidth(800);
		primaryStage.setTitle("Mouse Test Game - Menu");
		primaryStage.show();
	}
	
	public static void main(String[] args) {	
		Application.launch(args);
	}
	
	public class playGameEvent implements EventHandler<MouseEvent>{
		
		@Override
		public void handle(MouseEvent e) {
			menuSelectionBox.setVisible(false);
			gameField.setVisible(true);
			
//			timer = new GameTimer(20);
//			Thread t1 = new Thread(timer);
//			t1.start();
//			while (timer.getTime() > 0) {
//				try {
//					Thread.sleep(900);
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}
//				time.setText(timer.getTime() + "s");
//			}
//			if (timer.getTime() < 0) {
//				t1.interrupt();
//				System.out.println("GAME DONE");
//			}
		}
		
	}
	

	
	
	
}


