package ch.fhnw.oop2.tasky.ui.Graphical;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class ApplicationUI extends BorderPane {
	private Parent details;
	private Parent footer;
	private HBox center;

	public ApplicationUI() {
		initializeControls();
		layoutControls();
	}

	private void initializeControls() {
		details = new TaskDetails();
		footer = new Footer();
		List<TaskContainer> containers = List.of(new TaskContainer(TaskContainer.Status.Todo),
				new TaskContainer(TaskContainer.Status.Doing),
				new TaskContainer(TaskContainer.Status.Done));
		containers.forEach(TaskContainer::addTask); //fill with dummy task todo remove
		center = new HBox();
		center.getChildren().addAll(containers);

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
