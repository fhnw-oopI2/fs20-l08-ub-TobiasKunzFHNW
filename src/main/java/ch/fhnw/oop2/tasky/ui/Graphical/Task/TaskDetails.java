package ch.fhnw.oop2.tasky.ui.Graphical.Task;

import ch.fhnw.oop2.tasky.model.State;
import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.model.TaskData;
import ch.fhnw.oop2.tasky.ui.Graphical.ApplicationUI;
import ch.fhnw.oop2.tasky.ui.Graphical.Starter;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableLongValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.time.LocalDate;
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

	private Task currentTask;

	public TaskDetails() {
		initializeControls();
		layoutControls();
		initializeListeners();
	}

	private void initializeListeners() {
		ApplicationUI.getSelectedId().addListener((event, oldId, newId) -> updateSelectedTask(newId.longValue()));
		buttonSave.setOnMouseClicked(event -> saveTask());
		buttonDelete.setOnMouseClicked(event -> deleteTask());
	}

	private void initializeControls() {
		labelID = new Label("ID: ");
		textFieldID = new TextField();

		labelTitle = new Label("Title: ");
		textFieldTitle = new TextField();

		labelDesc = new Label("Description: ");
		textAreaDesc = new TextArea();

		labelDue = new Label("Date: ");
		datePickerDue = new DatePicker();

		labelState = new Label("State: ");
		comboBoxState = new ComboBox<>();

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
		comboBoxState.getItems().setAll(State.values());
		comboBoxState.getSelectionModel().select(0);
		datePickerDue.setValue(LocalDate.now());
		getChildren().add(wrapButtons(buttonSave, buttonDelete));
		setSpacing(10);
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

	private void updateSelectedTask(long id) {
		currentTask = ApplicationUI.getRepository().read(id);

		if (currentTask != null) {
			fillFields();
		} else {
			emptyFields();
		}

	}

	private void fillFields() {

		textFieldID.setText(String.valueOf(currentTask.id));
		textFieldTitle.setText(currentTask.data.title);
		textAreaDesc.setText(currentTask.data.desc);
		datePickerDue.setValue(currentTask.data.dueDate);
		comboBoxState.setValue(currentTask.data.state);
		textFieldTitle.requestFocus();
	}

	private void emptyFields() {
		textFieldID.clear();
		textFieldTitle.clear();
		textAreaDesc.clear();
		datePickerDue.setValue(LocalDate.now());
		comboBoxState.setValue(null);
	}

	private void saveTask() {
		TaskData newData = new TaskData(textFieldTitle.getText(), textAreaDesc.getText(), datePickerDue.getValue(), comboBoxState.getValue());

		ApplicationUI.getRepository().update(new Task(currentTask.id, newData));
		ApplicationUI.refreshTasks();
	}

	private void deleteTask() {
		ApplicationUI.getRepository().delete(currentTask.id);
		ApplicationUI.refreshTasks();
		emptyFields();
	}

	public void createNewTask(){
		emptyFields();

	}
}
