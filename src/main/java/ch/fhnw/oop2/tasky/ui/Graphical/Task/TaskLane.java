package ch.fhnw.oop2.tasky.ui.Graphical.Task;

import ch.fhnw.oop2.tasky.model.State;
import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.ui.Graphical.ApplicationUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

public class TaskLane extends VBox {
	private State state;
	private Label labelTitle;
	private VBox container;
	public TaskLane(State state) {
		this.state = state;
		initializeControls();
		layoutControls();
		setTasks(ApplicationUI.getTasks());

/*
		ApplicationUI.getSelectedId().addListener((event, oldId, newId) -> selectTask(newId.longValue()));
*/

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


	public void setTasks(List<Task> tasks) {
		container.getChildren().clear();
		tasks.stream()
				.filter(task -> task.data.state == state)
				.forEach(this::addTask);
	}

	private void addTask(Task task){
		TaskUi taskUi = new TaskUi(task);
		container.getChildren().add(taskUi);
	}

}
