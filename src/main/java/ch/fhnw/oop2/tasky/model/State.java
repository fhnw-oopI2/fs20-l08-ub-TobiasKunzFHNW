package ch.fhnw.oop2.tasky.model;

import java.util.Arrays;
import java.util.Optional;

public enum State {
	TODO, DOING, DONE;

	public static Optional<State> getState(String s) {
		if (s == null){
			return Optional.empty();
		}
		return Arrays.stream(State.values())
				.filter(state -> state.name().toLowerCase().equals(s.toLowerCase()))
				.findAny();
	}
}
