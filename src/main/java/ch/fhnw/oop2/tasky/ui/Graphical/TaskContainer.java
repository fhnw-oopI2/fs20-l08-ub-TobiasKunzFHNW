package ch.fhnw.oop2.tasky.ui.Graphical;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class TaskContainer extends VBox {
	public enum Status {Todo, Doing, Done} //todo remove in merged project
	private Status status;
	private Label labelTitle;
	private VBox container;

	public TaskContainer(Status status) {
		this.status = status;
		initializeControls();
		layoutControls();
	}

	private void initializeControls() {
		labelTitle = new Label(status.name());
		container = new VBox();
	}

	private void layoutControls() {
		HBox.setHgrow(this,Priority.ALWAYS);
		styleTitle();
		styleContainer();
		getChildren().addAll(labelTitle, container);
	}

	private void styleTitle(){
		labelTitle.setPadding(new Insets(5));
		labelTitle.setMaxWidth(Integer.MAX_VALUE);
		labelTitle.setAlignment(Pos.TOP_CENTER);
		labelTitle.setFont(new Font(18));
	}

	private void styleContainer(){
		setVgrow(container, Priority.ALWAYS);
		setMargin(container,new Insets(10));
		container.setSpacing(10);
		container.setPadding(new Insets(10));
		container.setStyle("-fx-border-color: gray");
		container.setStyle("-fx-border-style: dashed");
		//container.setAlignment(Pos.TOP_CENTER);
	}

/*
	public void setTasks(List<ch.fhnw.oop2.tasky.part4.Logic.Task> tasks) {
		container.getChildren().removeAll();
		tasks.forEach(t -> container.getChildren().add(new TaskUi(t)));
	}
*/

	public void addTask() { //todo remove
		container.getChildren().removeAll();
		container.getChildren().add(new TaskUi());
		container.getChildren().add(new TaskUi());
	}

}
