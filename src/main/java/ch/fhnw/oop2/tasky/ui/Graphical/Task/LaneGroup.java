package ch.fhnw.oop2.tasky.ui.Graphical.Task;

import ch.fhnw.oop2.tasky.model.State;
import ch.fhnw.oop2.tasky.model.TaskyPM;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class LaneGroup extends GridPane {
	List<State> states;
	List<Lane> lanes = new ArrayList<>();
	TaskyPM pm ;

	public LaneGroup(TaskyPM pm,State... state) {
		this.pm = pm;
		states = List.of(state);
		initializeControls();
		layoutControls();
	}

	private void createLane(State state) {
		ColumnConstraints columnConstraints = new ColumnConstraints();
		columnConstraints.setPercentWidth(100 / states.size());
		getColumnConstraints().add(columnConstraints);
		Lane lane = new Lane(pm,state);
		lanes.add(lane);
		addColumn(states.indexOf(state),lane);

	}

	private void initializeControls() {
		states.forEach(this::createLane);
	}

	private void layoutControls() {
		RowConstraints rowConstraints = new RowConstraints();
		rowConstraints.setPercentHeight(100);
		getRowConstraints().add(rowConstraints);
	}
}
