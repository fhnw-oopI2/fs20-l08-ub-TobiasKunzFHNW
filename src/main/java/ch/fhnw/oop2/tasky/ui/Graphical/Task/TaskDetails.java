package ch.fhnw.oop2.tasky.ui.Graphical.Task;

import ch.fhnw.oop2.tasky.model.State;
import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.ui.Graphical.Starter;
import ch.fhnw.oop2.tasky.model.TaskyPM;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Stream;


public class TaskDetails extends VBox {
	private static final int PADDING = 5;
	private static final double LABEL_WIDTH = 120;

	private TextField textFieldID, textFieldTitle;
	private Label labelID, labelTitle, labelDesc, labelDue, labelState;
	private TextArea textAreaDesc;
	private DatePicker datePickerDue;
	private ComboBox<State> comboBoxState;
	private Button buttonSave, buttonDelete;

	private TaskyPM pm;

	public TaskDetails(TaskyPM pm) {
		this.pm = pm;
		initializeControls();
		layoutControls();
		initializeListeners();
	}

	private void initializeListeners() {
		buttonSave.setOnMouseClicked(event -> pm.saveTask());
		buttonDelete.setOnMouseClicked(event -> pm.deleteTask());
		pm.selectedTaskIdProperty().addListener((event, oldValue, newValue) -> setAllDisabled(newValue.longValue() != -1));
	}

	private void setAllDisabled(boolean b) {
		textFieldID.setDisable(!b);
		textAreaDesc.setDisable(!b);
		textFieldTitle.setDisable(!b);
		datePickerDue.setDisable(!b);
		comboBoxState.setDisable(!b);
	}

	private void setTextFieldID(long id) {
		if (id == -1) {
			textFieldID.setText("");
		} else {
			textFieldID.setText(String.valueOf(id));
		}
	}

	private void initializeControls() {
		labelID = new Label("ID: ");
		textFieldID = new TextField();
		pm.selectedTaskIdProperty().addListener((event, oldV,newV)-> setTextFieldID(newV.longValue()));

		labelTitle = new Label("Title: ");
		textFieldTitle = new TextField();
		textFieldTitle.textProperty().bindBidirectional(pm.selectedTaskTitleProperty());

		labelDesc = new Label("Description: ");
		textAreaDesc = new TextArea();
		textAreaDesc.textProperty().bindBidirectional(pm.selectedTaskDescProperty());

		labelDue = new Label("Date: ");
		datePickerDue = new DatePicker();
		datePickerDue.valueProperty().bindBidirectional(pm.selectedTaskDateProperty());

		labelState = new Label("State: ");
		comboBoxState = new ComboBox<>();
		comboBoxState.valueProperty().bindBidirectional(pm.selectedTaskStateProperty());

		buttonSave = new Button("Save");
		buttonDelete = new Button("Delete");
	}

	private void layoutControls() {
		List<Label> labels = List.of(labelID, labelTitle, labelDesc, labelDue, labelState);
		List<Control> elements = List.of(textFieldID, textFieldTitle, textAreaDesc, datePickerDue, comboBoxState);

		Stream.iterate(0, n -> n + 1).limit(labels.size()).forEach(n -> {
			getChildren().add(wrapInHbox(labels.get(n), elements.get(n)));
			HBox.setHgrow(elements.get(n), Priority.ALWAYS);
		});

		textFieldID.setEditable(false);
		comboBoxState.setEditable(false);
		comboBoxState.getItems().setAll(State.values());
		comboBoxState.getSelectionModel().select(0);
		getChildren().add(wrapButtons(buttonSave, buttonDelete));
		setSpacing(10);
		setPadding(new Insets(10));
	}

	private HBox wrapInHbox(Label label, Control element) {
		HBox hBox = new HBox();
		label.setFont(Starter.LABEL_FONT);
		label.setMinWidth(LABEL_WIDTH);
		element.setStyle("-fx-font-size: 18px");
		element.setMaxWidth(500);
		hBox.getChildren().addAll(label, element);
		return hBox;
	}

	private HBox wrapButtons(Button b1, Button b2) {
		HBox hBox = new HBox();
		b1.setFont(Starter.BUTTON_FONT);
		b2.setFont(Starter.BUTTON_FONT);
		hBox.setPadding(new Insets(PADDING));
		hBox.setSpacing(10);
		hBox.getChildren().addAll(b1, b2);
		return hBox;
	}
}
