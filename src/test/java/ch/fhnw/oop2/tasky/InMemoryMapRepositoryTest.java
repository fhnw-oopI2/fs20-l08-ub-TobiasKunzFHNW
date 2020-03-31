package ch.fhnw.oop2.tasky;

import ch.fhnw.oop2.tasky.model.impl.InMemoryMapRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

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
