package ch.fhnw.oop2.tasky;

import ch.fhnw.oop2.tasky.ui.CommandLine.TaskyCLI;
import ch.fhnw.oop2.tasky.ui.Graphical.Starter;

public class Main {

	/**
	 * Starting the application here because of this bug
	 * https://github.com/javafxports/openjdk-jfx/issues/236
	 * @param args
	 */
	public static void main(String[] args) {
		Starter.main(args);
	}
}
