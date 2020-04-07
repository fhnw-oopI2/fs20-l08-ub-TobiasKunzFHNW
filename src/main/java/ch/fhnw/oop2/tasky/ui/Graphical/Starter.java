package ch.fhnw.oop2.tasky.ui.Graphical;

import ch.fhnw.oop2.tasky.model.Repository;
import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.model.impl.SQLite;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
		TaskyPM pm = new TaskyPM();
		Parent rootPane = new ApplicationUI(pm);
		Scene myScene = new Scene(rootPane);

		primaryStage.setTitle(pm.getTitle());
		primaryStage.setScene(myScene);
		primaryStage.setWidth(WIDTH);
		primaryStage.setHeight(HEIGHT);
		primaryStage.show();
	}
}
