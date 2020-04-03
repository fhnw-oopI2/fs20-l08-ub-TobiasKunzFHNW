package ch.fhnw.oop2.tasky.ui.Graphical;

import ch.fhnw.oop2.tasky.model.Repository;
import ch.fhnw.oop2.tasky.model.State;
import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.model.impl.SQLite;
import ch.fhnw.oop2.tasky.ui.Graphical.Task.LaneGroup;
import ch.fhnw.oop2.tasky.ui.Graphical.Task.TaskDetails;
import javafx.application.Application;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.layout.*;

public class ApplicationUI extends GridPane {
	private Parent taskDetail;
	private Parent footer;
	private Parent laneGroup;
	private static Repository repository;
	private static ObservableList<Task> tasks;
	private static SimpleLongProperty selectedId = new SimpleLongProperty();

	private static final int WIDTH_LANES = 60;
	private static final int HEIGHT_FOOTER = 10;

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
	}

	private void initializeControls() {
		taskDetail = new TaskDetails();
		footer = new Footer();
		laneGroup = new LaneGroup(State.TODO, State.DOING, State.DONE);

		ColumnConstraints columnLanes = new ColumnConstraints();
		columnLanes.setPercentWidth((WIDTH_LANES));
		ColumnConstraints columnDetails = new ColumnConstraints();
		columnDetails.setPercentWidth((100 - WIDTH_LANES));
		getColumnConstraints().addAll(columnLanes, columnDetails);

		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(100 - HEIGHT_FOOTER);
		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(HEIGHT_FOOTER);
		getRowConstraints().addAll(row1, row2);

		add(laneGroup, 0, 0);
		add(taskDetail, 1, 0);
		add(footer, 0, 1);
	}


	private void layoutControls() {
	}

}
