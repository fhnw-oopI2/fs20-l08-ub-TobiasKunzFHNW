package ch.fhnw.oop2.tasky.ui.Graphical;

import ch.fhnw.oop2.tasky.model.Repository;
import ch.fhnw.oop2.tasky.model.State;
import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.model.TaskData;
import ch.fhnw.oop2.tasky.model.impl.SQLite;
import ch.fhnw.oop2.tasky.ui.Graphical.Task.TaskUi;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskyPM {
	private static Repository repository = new SQLite(SQLite.Database.REPOSITORY);
	private StringProperty title = new SimpleStringProperty();
	private StringProperty buttonNew = new SimpleStringProperty("New Task");
	private StringProperty buttonRefresh = new SimpleStringProperty("Refresh");
	private LongProperty selectedTaskId = new SimpleLongProperty();
	private StringProperty selectedTaskTitle = new SimpleStringProperty();
	private StringProperty selectedTaskDesc = new SimpleStringProperty();
	private ObjectProperty<LocalDate> selectedTaskDate = new SimpleObjectProperty<>();
	private ObjectProperty<State> selectedTaskState = new SimpleObjectProperty<>();
	private ObservableList<Task> tasks = FXCollections.observableArrayList();

	public String getTitle() {
		return title.get();
	}

	public long getSelectedTaskId() {
		return selectedTaskId.get();
	}

	public LongProperty selectedTaskIdProperty() {
		return selectedTaskId;
	}

	public String getSelectedTaskTitle() {
		return selectedTaskTitle.get();
	}

	public StringProperty selectedTaskTitleProperty() {
		return selectedTaskTitle;
	}

	public String getSelectedTaskDesc() {
		return selectedTaskDesc.get();
	}

	public StringProperty selectedTaskDescProperty() {
		return selectedTaskDesc;
	}

	public LocalDate getSelectedTaskDate() {
		return selectedTaskDate.get();
	}

	public ObjectProperty<LocalDate> selectedTaskDateProperty() {
		return selectedTaskDate;
	}

	public State getSelectedTaskState() {
		return selectedTaskState.get();
	}

	public ObjectProperty<State> selectedTaskStateProperty() {
		return selectedTaskState;
	}

	public StringProperty titleProperty() {
		return title;
	}

	public ObservableList<Task> getTasks() {
		return tasks;
	}

	public String getButtonNew() {
		return buttonNew.get();
	}

	public StringProperty buttonNewProperty() {
		return buttonNew;
	}

	public String getButtonRefresh() {
		return buttonRefresh.get();
	}

	public StringProperty buttonRefreshProperty() {
		return buttonRefresh;
	}

	public void createNewTask() {
		Task newTask = repository.create(new TaskData("", "", LocalDate.now(), State.TODO));
		refreshTasks();
		selectTask(newTask.id);
	}

	public void selectTask(long id){
		Task task = repository.read(id);
		if (task != null) {
			selectedTaskId.set(task.id);
			selectedTaskTitle.set(task.data.title);
			selectedTaskDesc.set(task.data.desc);
			selectedTaskDate.set(task.data.dueDate);
			selectedTaskState.set(task.data.state);
		} else {
			selectedTaskId.set(-1);
			selectedTaskTitle.set(null);
			selectedTaskDesc.set(null);
			selectedTaskDate.set(LocalDate.now());
			selectedTaskState.set(State.TODO);
		}
	}

	public void refreshTasks() {
		tasks.setAll(repository.read());
	}

	public List<TaskUi> getTasksByState(State state) {
		return tasks.stream().filter(task -> task.data.state == state)
				.map(t -> new TaskUi(this,t)).collect(Collectors.toList());
	}

	public void saveTask() {
		TaskData newData = new TaskData(selectedTaskTitle.get(), selectedTaskDesc.get(), selectedTaskDate.get(), selectedTaskState.getValue());
		repository.update(new Task(selectedTaskId.get(), newData));
		refreshTasks();
	}

	public void deleteTask(){
		repository.delete(selectedTaskId.get());
		refreshTasks();
		selectTask(-1);
	}
}
