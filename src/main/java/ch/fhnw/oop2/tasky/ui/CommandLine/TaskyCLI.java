package ch.fhnw.oop2.tasky.ui.CommandLine;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;

import ch.fhnw.oop2.tasky.model.Repository;
import ch.fhnw.oop2.tasky.model.State;
import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.model.TaskData;
import ch.fhnw.oop2.tasky.model.impl.SQLite;

/**
 * Diese Klasse implementiert das CLI (Command Line Interface).
 * Das ist eine erste und einfache Implementierung eines UI (User Interfaces).
 * <p>
 * Das UI ist abhängig von Klassen aus dem 'Shared'-Modul.
 */
public final class TaskyCLI {
	private static Repository repository;
	private static Scanner scanner;

	/**
	 * Die Main Methode.
	 *
	 * @param args Der String Array den möglichen Programm Argumenten
	 * @throws IOException Die IO Exception die der TaskService wirft
	 */
	public static void main(String[] args)  {
		new TaskyCLI().start();
	}

	/**
	 * Startet das CLI und läuft in einem endlosen Loopdatabase. Das CLI wartet auf
	 * Input vom Benutzer. Gültige Befehler sind:
	 * - show
	 * - create
	 * - update
	 * - delete
	 *
	 * @throws IOException
	 */
	private void start() {
		repository = new SQLite(SQLite.Database.REPOSITORY);
		scanner = new Scanner(System.in);
		boolean newInput = false;
		String cmd = null;

		System.out.println("===Tasky CLI===");
		while (true) {
			if (!newInput) {
				cmd = getUserInput(null);
			}
			newInput = false;
			switch (cmd) {
				case "show":
					cmd = show();
					newInput = true; //fixme
					break;
				case "add":
					ask();
					break;
				case "get":
					get();
					break;
				case "update":
					update();
					break;
				case "delete":
					delete();
					break;
				default:
					System.out.println("Invalid input");
					System.out.println("Supported commands: show | add | get | update | delete");
			}
		}
	}

	private String show() {
		String cmd;
		List<Task> tasks = repository.read();
		Comparator<Task> comparator = askForSortCriteria(scanner);
		tasks.stream()
				.sorted(comparator)
				.forEach(System.out::println);

		cmd = getUserInput("Specify filter or any other command.");
		while (cmd.toLowerCase().equals("filter")) {
			Predicate<Task> filter = askForFilterCriteria();
			tasks.stream()
					.filter(filter)
					.sorted(comparator)
					.forEach(System.out::println);
			cmd = getUserInput(null);
		}
		return cmd;
	}

	private void ask() {
		TaskData addTaskData = askForTaskData();
		Task addedTask = repository.create(addTaskData);
		System.out.println("Added: " + addedTask);
	}

	private void get() {
		long id = askForID();
		Optional<Task> optionalTask = repository.read(id);
		while (optionalTask.isEmpty()) {
			System.out.println("Invalid ID");
			repository.read(askForID());
		}
		System.out.println(optionalTask.get());
	}

	private void update() {
		long updateId = askForID();
		TaskData updateTaskData = askForTaskData();
		Task updateTask = new Task(updateId, updateTaskData);

		repository.update(updateTask);
		System.out.println("Update successful");
	}

	private void delete() {
		long id = askForID();
		repository.delete(id);
		System.out.println("Deleted: " + id);
	}

	private String getUserInput(String prompt) {
		if (prompt != null) {
			System.out.println(prompt);
		}
		System.out.println(">");
		return scanner.nextLine();
	}

	/**
	 * Gibt den Prompt aus für die ID und liest die Eingabe ein.
	 *
	 * @return Die ID
	 */
	private long askForID() {
		return Long.parseLong(getUserInput("Please enter ID."));
	}

	/**
	 * Gibt die nötigen Prompts aus für die Daten und liest die jeweiligen Eingaben ein.
	 *
	 * @return Das TaskData Objekt mit den eingelesenen Daten
	 */
	private TaskData askForTaskData() {
		String addTitle = getTitleInput();
		String addDesc = getDescriptionInput();
		LocalDate addDueDate = getDateInput();
		State state = getStateInput();
		return new TaskData(addTitle, addDesc, addDueDate, state);
	}

	private Predicate<Task> askForFilterCriteria() {
		State state = getStateInput();
		return task -> task.data.state.equals(state);
	}

	private Comparator<Task> askForSortCriteria(Scanner scanner) {
		String criteria = getUserInput("Order by (t=title, d=description, s=state, z=date");

		switch (criteria) {
			case "t":
				return Comparator.comparing(task -> task.data.title);
			case "d":
				return Comparator.comparing(task -> task.data.desc);
			case "s":
				return Comparator.comparing(task -> task.data.state);
			case "z":
				return Comparator.comparing(task -> task.data.dueDate);
			default:
				return Comparator.comparing(Task::toString);
		}
	}

	private String getTitleInput() {
		String input = getUserInput("Title");
		return input;
	}

	private String getDescriptionInput() {
		String input = getUserInput("Description");
		return input;
	}

	private LocalDate getDateInput() {
		LocalDate date;
		String input = getUserInput("Due date (YYYY-MM-DD)");

		if (input.equals("") || input.toLowerCase().equals("today")) {
			date = LocalDate.now();
		} else {
			date = LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);
		}
		return date;
	}

	private State getStateInput() {
		Optional<State> input = State.getState(getUserInput("State (Todo|Doing|Done)"));
		while (input.isEmpty()) {
			input = State.getState(getUserInput("Invalid State. Valid inputs are (Todo|Doing|Done)"));
		}
		return input.get();
	}
}
