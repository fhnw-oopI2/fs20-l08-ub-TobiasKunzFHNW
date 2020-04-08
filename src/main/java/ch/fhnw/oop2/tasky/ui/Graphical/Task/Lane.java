package ch.fhnw.oop2.tasky.ui.Graphical.Task;

import ch.fhnw.oop2.tasky.model.State;
import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.model.TaskyPM;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Lane extends VBox {
	private State state;
	private Label labelTitle;
	private VBox container;
	private static int MIN_WIDTH = 200;
	private static int MIN_HEIGHT = 500;
	private TaskyPM pm;

	public Lane(TaskyPM pm, State state) {
		this.pm = pm;
		this.state = state;
		initializeControls();
		layoutControls();
		initializeListeners();
		drawTasks();
	}

	private void initializeListeners() {
		ListChangeListener<Task> listener = change -> drawTasks();
		pm.getTasks().addListener(listener);
	}

	private void initializeControls() {
		labelTitle = new Label(state.name());
		container = new VBox();
		getStylesheets().add("CSS/Lane.css");
	}

	private void layoutControls() {
		HBox.setHgrow(this, Priority.ALWAYS);
		setMinWidth(MIN_WIDTH);
		setMinHeight(MIN_HEIGHT);
		styleTitle();
		styleContainer();
		getChildren().addAll(labelTitle, container);
	}

	private void styleTitle() {
		labelTitle.setPadding(new Insets(5));
		labelTitle.setMaxWidth(Integer.MAX_VALUE);
		labelTitle.setAlignment(Pos.TOP_CENTER);
		labelTitle.setFont(new Font(18));
	}

	private void styleContainer() {
		setVgrow(container, Priority.ALWAYS);
		setMargin(container, new Insets(10));
		container.setSpacing(10);
		container.setPadding(new Insets(10));
		container.getStyleClass().add("LaneContainer");

	}

	private void drawTasks() {
		container.getChildren().clear();
		container.getChildren().addAll(pm.getTasksByState(state));
	}
}
