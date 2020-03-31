package ch.fhnw.oop2.tasky.ui.Graphical;

import ch.fhnw.oop2.tasky.model.Repository;
import ch.fhnw.oop2.tasky.model.State;
import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.model.impl.SQLite;
import ch.fhnw.oop2.tasky.ui.Graphical.Task.TaskDetails;
import ch.fhnw.oop2.tasky.ui.Graphical.Task.TaskLane;
import javafx.application.Application;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableLongValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class ApplicationUI extends BorderPane {
	private Parent details;
	private Parent footer;
	private HBox center;
	private List<TaskLane> container;
	private static Repository repository;
	private static ObservableList<Task> tasks;
	private static SimpleLongProperty selectedId = new SimpleLongProperty();

	public static Repository getRepository() {
		return repository;
	}

	public static ObservableList<Task> getTasks() {
		return tasks;
	}

	public static void setSelectedId(Long value) {
		selectedId.set(value);
	}

	public static SimpleLongProperty getSelectedId() {
		return selectedId;
	}

	public static void refreshTasks() {
		tasks.setAll(repository.read());
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	private void initConfigs() {
		repository = new SQLite(SQLite.Database.REPOSITORY);
		tasks = FXCollections.observableArrayList();
		selectedId.set(-1);
		refreshTasks();
	}

	public ApplicationUI() {
		initConfigs();
		initializeControls();
		layoutControls();
		initializeListener();
	}

	private void initializeControls() {
		details = new TaskDetails();
		footer = new Footer();
		container = List.of(new TaskLane(State.TODO),
				new TaskLane(State.DOING),
				new TaskLane(State.DONE));
		center = new HBox();
		center.getChildren().addAll(container);
	}

	private void initializeListener() {
		ListChangeListener<Task> listener = change -> container.forEach(container -> container.setTasks(ApplicationUI.getTasks()));

		ApplicationUI.tasks.addListener(listener);
	}


	private void layoutControls() {
		setMargin(details, new Insets(20));
		setMargin(footer, new Insets(20));
		setMargin(center, new Insets(20));
		setCenter(center);
		setRight(details);
		setBottom(footer);
	}

}
