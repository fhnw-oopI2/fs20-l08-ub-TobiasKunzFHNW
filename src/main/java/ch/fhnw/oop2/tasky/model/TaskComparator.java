package ch.fhnw.oop2.tasky.model;

import java.util.Comparator;
public enum TaskComparator {
	ID(Comparator.comparing(task -> task.id)),
	TITLE(Comparator.comparing(task -> task.data.title)),
	DESC(Comparator.comparing(task -> task.data.desc)),
	DUE(Comparator.comparing(task -> task.data.dueDate));

	private Comparator<Task> comparator;

	TaskComparator(Comparator<Task> comparator) {
		this.comparator = comparator;
	}

	public Comparator<Task> getComparator() {
		return comparator;
	}
}
