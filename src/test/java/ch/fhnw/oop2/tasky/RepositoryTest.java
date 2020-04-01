package ch.fhnw.oop2.tasky;

import ch.fhnw.oop2.tasky.model.Repository;
import ch.fhnw.oop2.tasky.model.State;
import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.model.TaskData;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public abstract class RepositoryTest {
	protected int startID = 0;
	protected Repository repository;

	@Test
	public void testCreate() {

		// when
		Task task = repository.create(createTaskData("A", State.TODO));

		// then
		assertSame((long)startID , task.id);
		assertEquals("A-Title", task.data.title);
		assertEquals("A-Desc", task.data.desc);
		assertTrue(repository.read().size() == 1);
	}

	@Test
	public void testRead() {

		// when
		repository.create(createTaskData("A", State.TODO));
		repository.create(createTaskData("B", State.DOING));
		repository.create(createTaskData("C", State.DONE));

		// then
		assertEquals(3, repository.read().size());
		assertEquals("A-Title", repository.read().get(0).data.title);
		assertEquals("A-Title", repository.read((long)startID).data.title);
		assertEquals("B-Title", repository.read((long)startID+1).data.title);
		assertEquals("C-Title", repository.read((long)startID+2).data.title);
		assertNull(repository.read(42L));
	}

	@Test
	public void testUpdate() {

		// when
		Task taskA = repository.create(createTaskData("A", State.TODO));
		Task taskAUpdated = new Task(taskA.id, createTaskData("B", State.DOING));
		repository.update(taskAUpdated);

		// then
		assertEquals("B-Title", repository.read((long)startID).data.title);
		assertSame(State.DOING, repository.read((long)startID).data.state);
	}

	@Test
	public void testUpdateNotExistingTask() {

		// when
		long nonExistingID = 42;
		Task nonExistingTask = new Task(nonExistingID, createTaskData("B", State.DOING));

		// then
		try {
			repository.update(nonExistingTask);
			fail("Nicht existierende Tasks können nicht mittels update verändert werden");
		} catch (IllegalStateException e) {
			assertEquals("Update - keine Task mit dieser ID: " + nonExistingTask.id, e.getMessage());
		}
	}

	@Test
	public void testDelete() {

		// when
		Task taskA = repository.create(createTaskData("A", State.TODO));
		repository.delete(taskA.id);

		// then
		assertNull(repository.read(0L));
		assertTrue(repository.read().size() == 0);
	}

	@Test
	public void testDeleteNonExistingTask() {

		// when
		long nonExistingID = 42;

		// then
		try {
			repository.delete(nonExistingID);
			fail("Nicht existierende Tasks können nicht gelöscht werden");
		} catch (IllegalStateException e) {
			assertEquals("Delete - keine Task mit dieser ID: " + nonExistingID, e.getMessage());
		}
	}

	/**
	 * Erzeugt ein neues TaskDate Objekt. Das Präfix wird vor den Titel und die
	 * Beschreibung konkateniert. Datum wird konstant vergeben.
	 *
	 * @param prefix Das Präfix
	 * @param state  Der State (Todo, Doing, Done)
	 * @return Ein neues TaskData
	 */
	private TaskData createTaskData(String prefix, State state) {
		LocalDate date = LocalDate.parse("2018-03-12", DateTimeFormatter.ISO_LOCAL_DATE);
		return new TaskData(prefix + "-Title", prefix + "-Desc", date, state);
	}
}
