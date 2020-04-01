package ch.fhnw.oop2.tasky.ui.Graphical;

import ch.fhnw.oop2.tasky.model.State;
import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.model.TaskData;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

public class Footer extends HBox {
	private Button buttonNew, buttonRefresh;

	public Footer() {
		initializeControls();
		layoutControls();
	}

	private void initializeControls() {
		buttonNew = new Button("New");
		buttonNew.setOnAction(b ->createNewTask());
		buttonRefresh = new Button("Refresh");
		buttonRefresh.setOnAction(b -> ApplicationUI.refreshTasks());
	}

	private void layoutControls() {
		getChildren().addAll(buttonNew, buttonRefresh);
		buttonNew.setFont(Starter.BUTTON_FONT);
		buttonRefresh.setFont(Starter.BUTTON_FONT);
		buttonNew.setPrefWidth(200);
		buttonRefresh.setPrefWidth(200);
		setSpacing(10);
		setPadding(new Insets(10));
	}

	private void createNewTask(){
		Task newTask = ApplicationUI.getRepository().create(new TaskData("","", LocalDate.now(), State.TODO));
		ApplicationUI.refreshTasks();
		ApplicationUI.setSelectedId(newTask.id);
	}
}
