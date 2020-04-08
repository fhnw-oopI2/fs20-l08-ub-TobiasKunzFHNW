package ch.fhnw.oop2.tasky.ui.Graphical;

import ch.fhnw.oop2.tasky.model.State;
import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.model.TaskyPM;
import ch.fhnw.oop2.tasky.ui.Graphical.Task.LaneGroup;
import ch.fhnw.oop2.tasky.ui.Graphical.Task.TaskDetails;
import javafx.application.Application;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.layout.*;

public class ApplicationUI extends GridPane {
	private Parent taskDetail;
	private Parent footer;
	private Parent laneGroup;
	private TaskyPM pm;

	private static final int WIDTH_LANES = 60;
	private static final int HEIGHT_FOOTER = 10;

	public static void main(String[] args) {
		Application.launch(args);
	}

	public ApplicationUI(TaskyPM pm) {
		this.pm = pm;
		initializeControls();
		layoutControls();
	}

	private void initializeControls() {
		taskDetail = new TaskDetails(pm);
		footer = new Footer(pm);
		laneGroup = new LaneGroup(pm,State.TODO, State.DOING, State.DONE);

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
}

	private void layoutControls() {
		add(laneGroup, 0, 0);
		add(taskDetail, 1, 0);
		add(footer, 0, 1);
	}
}
