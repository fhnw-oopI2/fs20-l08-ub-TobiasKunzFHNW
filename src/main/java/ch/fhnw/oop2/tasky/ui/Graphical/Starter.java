package ch.fhnw.oop2.tasky.ui.Graphical;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Starter extends Application {
	public static final int WIDTH = 1400;
	public static final int HEIGHT = 1000;
	public static final Font LABEL_FONT = new Font(18);
	public static final Font BUTTON_FONT = new Font(18);


	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Parent rootPane = new ApplicationUI();
		Scene myScene = new Scene(rootPane);

		primaryStage.setTitle("Tasky!");
		primaryStage.setScene(myScene);
		primaryStage.setWidth(WIDTH);
		primaryStage.setHeight(HEIGHT);
		primaryStage.show();
	}
}
