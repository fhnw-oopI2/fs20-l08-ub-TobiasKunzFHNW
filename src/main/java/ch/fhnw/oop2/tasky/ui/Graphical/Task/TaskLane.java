package ch.fhnw.oop2.tasky.ui.Graphical.Task;

import ch.fhnw.oop2.tasky.model.State;
import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.ui.Graphical.ApplicationUI;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;
import java.util.stream.Collectors;

public class TaskLane extends VBox {
	private State state;
	private Label labelTitle;
	private VBox container;
	private List<TaskUi> taskUis;

	public List<TaskUi> getTaskUis() {
		return taskUis;
	}

	public TaskLane(State state) {
		this.state = state;
		initializeControls();
		layoutControls();
		initializeListeners();
		updateTaskUIs();
	}

	private void initializeListeners() {
		ListChangeListener<Task> listener = change -> updateTaskUIs();
		ApplicationUI.getTasks().addListener(listener);
	}

	private void updateTaskUIs() {
		taskUis = ApplicationUI.getTasks().stream().filter(task -> task.data.state == state)
				.map(TaskUi::new).collect(Collectors.toList());

		drawTasks();
	}

	private void initializeControls() {
		labelTitle = new Label(state.name());
		container = new VBox();
	}

	private void layoutControls() {
		HBox.setHgrow(this, Priority.ALWAYS);
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
		container.setSpacing(20);
		container.setPadding(new Insets(10));
		container.setStyle("-fx-border-color: gray");
		container.setStyle("-fx-border-style: dashed");

		//container.setAlignment(Pos.TOP_CENTER);
	}

	private void drawTasks() {
		container.getChildren().clear();
		container.getChildren().addAll(taskUis);
	}
}
