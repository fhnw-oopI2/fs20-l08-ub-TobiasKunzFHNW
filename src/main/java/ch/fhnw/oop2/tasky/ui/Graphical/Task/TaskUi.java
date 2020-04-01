package ch.fhnw.oop2.tasky.ui.Graphical.Task;

import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.ui.Graphical.ApplicationUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TaskUi extends GridPane {
	//private Task task; todo
	private Label labelTitle;
	private Label labelDue;
	private static final int PADDING = 20;
	private Task task;

/*
	private static final String STYLE_NORMAL = "-fx-background-color: \n" +
			"       -fx-shadow-highlight-color, \n" +
			"       -fx-outer-border, \n" +
			"       -fx-inner-border, \n" +
			"       -fx-body-color;";

	private static final String STYLE_SELECTED = "-fx-background-color: \n" +
			"       -fx-shadow-highlight-color, \n" +
			"       -fx-outer-border, \n" +
			"       -fx-inner-border, \n" +
			"       -fx-accent;" +
			":hover -fx-background-color:#dae7f3";
*/

	public Task getTask() {
		return task;
	}

	public TaskUi(Task task) {
		this.task = task;
		initializeControls();
		layoutControls();
		initializeListeners();
	}

	private void initializeListeners() {
		setOnMouseClicked(event -> ApplicationUI.setSelectedId(task.id));
	}

	private void initializeControls() {
		labelTitle = new Label(task.data.title);
		labelDue = new Label(task.data.dueDate.toString());
	}

	private void layoutControls() {
		labelTitle.setPadding(new Insets(10));
		labelTitle.setAlignment(Pos.TOP_CENTER);
		labelDue.setAlignment(Pos.BOTTOM_RIGHT);

		add(labelTitle,0,0);
		add(labelDue,3,1);

		//setStyle(STYLE_NORMAL);


		//	setPrefWidth(Integer.MAX_VALUE);
		setHgap(5);
		setVgap(5);
		getStylesheets().add("CSS/TaskUI.css");
		getStyleClass().add("TaskUI");
		setPrefHeight(80);
		//setPadding(new Insets(PADDING));
	}

	public void select() {
		getStyleClass().add("selected");
	}

	public void deselect() {
		getStyleClass().remove("selected");
	}
}
