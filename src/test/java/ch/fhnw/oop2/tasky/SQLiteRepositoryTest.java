package ch.fhnw.oop2.tasky;

import ch.fhnw.oop2.tasky.model.impl.SQLite;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class SQLiteRepositoryTest extends RepositoryTest {
	private SQLite sqLite;

	@BeforeEach
	public void setUp() {
		startID = 1;
		sqLite = new SQLite(SQLite.Database.TEST);
		repository = sqLite;
	}

	@AfterEach
	public void tearDown() {
		sqLite.clear();
		sqLite.disconnect();
	}
}
