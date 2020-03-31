package ch.fhnw.oop2.tasky.model.impl.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import ch.fhnw.oop2.tasky.model.impl.InMemoryMapRepository;

public class InMemoryMapRepositoryTest extends RepositoryTest {
	
	@BeforeEach
	public void setUp()  {
		repository = new InMemoryMapRepository();
	}
	
	@AfterEach
	public void tearDown() {
		repository = null;
	}
}
