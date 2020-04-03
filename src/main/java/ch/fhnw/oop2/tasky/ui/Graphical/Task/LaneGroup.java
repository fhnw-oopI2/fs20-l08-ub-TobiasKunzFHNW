package ch.fhnw.oop2.tasky.ui.Graphical.Task;

import ch.fhnw.oop2.tasky.model.State;
import ch.fhnw.oop2.tasky.ui.Graphical.ApplicationUI;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class LaneGroup extends HBox {
	List<State> states;
	List<Lane> lanes = new ArrayList<>();

	public LaneGroup(State... state) {
		states = List.of(state);
		initializeControls();
		layoutControls();
		initializeListener();
	}

	private void createLane(State state) {
		Lane lane = new Lane(state);
		lanes.add(lane);
		getChildren().add(lane);
	}

	private void initializeControls() {
		states.forEach(this::createLane);
	}

	private void initializeListener() {
		ApplicationUI.getSelectedId().addListener((event, oldId, newId) -> highlightSelectedTask(oldId.longValue(), newId.longValue()));
	}

	private void highlightSelectedTask(long oldId, long newId) {
		lanes.stream()
				.flatMap(lane -> lane.getTaskUis().stream())
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
	}
}
