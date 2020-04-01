package ch.fhnw.oop2.tasky.ui.Graphical;

import ch.fhnw.oop2.tasky.model.Repository;
import ch.fhnw.oop2.tasky.model.State;
import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.model.impl.SQLite;
import ch.fhnw.oop2.tasky.ui.Graphical.Task.TaskDetails;
import ch.fhnw.oop2.tasky.ui.Graphical.Task.TaskLane;
import ch.fhnw.oop2.tasky.ui.Graphical.Task.TaskUi;
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
import javafx.scene.layout.*;

import java.util.List;

public class ApplicationUI extends GridPane {
	private Parent details;
	private Parent footer;
	private GridPane center;
	private List<TaskLane> container;
	private static Repository repository;
	private static ObservableList<Task> tasks;
	private static SimpleLongProperty selectedId = new SimpleLongProperty();

	private static final int WIDTH_LANES = 60;
	//private static final float WIDTH_DETAILS = 0.4f;
	private static final int HEIGHT_FOOTER = 10;
	private static final int NUMBER_OF_LANES = 3;


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
		/*center = new GridPane();

		center.add(container.get(0),0,0);
		center.add(container.get(1),1,0);
		center.add(container.get(2),2,0);
		center.getColumnConstraints().add(new ColumnConstraints(250)); // fixme
		center.getColumnConstraints().add(new ColumnConstraints(250));
		center.getColumnConstraints().add(new ColumnConstraints(250));
		center.getRowConstraints().add(new RowConstraints(800));
*/

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(WIDTH_LANES / NUMBER_OF_LANES);

		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(WIDTH_LANES / NUMBER_OF_LANES);

		ColumnConstraints column3 = new ColumnConstraints();
		column3.setPercentWidth(WIDTH_LANES / NUMBER_OF_LANES);

		ColumnConstraints column4 = new ColumnConstraints();
		column4.setPercentWidth((100 - WIDTH_LANES));

		getColumnConstraints().addAll(column1, column2, column3, column4);


		/*getRowConstraints().add(new RowConstraints((1 - HEIGHT_FOOTER) * Starter.HEIGHT));
		getRowConstraints().add(new RowConstraints(HEIGHT_FOOTER * Starter.HEIGHT));*/
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(100-HEIGHT_FOOTER);
		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(HEIGHT_FOOTER);
		getRowConstraints().addAll(row1, row2);


		add(container.get(0), 0, 0);
		add(container.get(1), 1, 0);
		add(container.get(2), 2, 0);
		add(details, 3, 0);
		add(footer, 0, 1);


		//center.getChildren().addAll(container);
	}

	private void initializeListener() {
		selectedId.addListener((event, oldId, newId) -> highlightSelectedTask(oldId.longValue(), newId.longValue()));
	}

	private void highlightSelectedTask(long oldId, long newId) {
		container.stream()
				.flatMap(taskLane -> taskLane.getTaskUis().stream())
				.filter(taskUi -> taskUi.getTask().id == oldId || taskUi.getTask().id == newId)
				.forEach(taskUi -> {
					if (taskUi.getTask().id == oldId) {
						taskUi.deselect();
					} else {
						taskUi.select();
					}
				});
	}

	private void layoutControls() {
		/*setMargin(details, new Insets(20));
		setMargin(footer, new Insets(20));
		setMargin(center, new Insets(20));*/
	/*	setCenter(center);
		setRight(details);
		setBottom(footer);*/
	}

}
