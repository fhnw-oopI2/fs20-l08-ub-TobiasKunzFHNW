package ch.fhnw.oop2.tasky.model.impl;

import ch.fhnw.oop2.tasky.model.Repository;
import ch.fhnw.oop2.tasky.model.State;
import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.model.TaskData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLite implements Repository {
	private Connection conn;
	private int nextID;

	public SQLite(Database database) {
		conn = connect(database);
		createNewDatabase(conn);
		createNewTable(conn);
		nextID = getCurrentHighestID(conn) + 1;
	}

	/**
	 * Call me to create a new DB
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = connect(Database.REPOSITORY);
		createNewDatabase(conn);
		createNewTable(conn);
	}

	/**
	 * Opens a connection to the DB
	 *
	 * @return the Connection duh
	 */

	private static Connection connect(Database db) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(db.getUrl());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	/**0L
	 * @param conn
	 * @return ID of the last element in the table
	 */
	private static int getCurrentHighestID(Connection conn) {
		String sql = "SELECT MAX(id) FROM Tasks";

		try {
			Statement stmt = conn.createStatement();
			return stmt.executeQuery(sql).getInt(1);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Inserts a task into the DB
	 *
	 * @param conn
	 * @param task Task to be inserted
	 */
	private static void insert(Connection conn, Task task) {
		final String sql = "INSERT INTO Tasks(id,title,description,state,due) VALUES(?,?,?,?,?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) task.id); //fixme cast
			pstmt.setString(2, task.data.title);
			pstmt.setString(3, task.data.desc);
			pstmt.setString(4, task.data.state.name());
			pstmt.setDate(5, Date.valueOf(task.data.dueDate));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns all the tasks from in the DB
	 *
	 * @param conn
	 */
	private static Optional<List<Task>> getTasks(Connection conn) {
		final String sql = "SELECT id, title, description, state, due "
				+ "FROM Tasks";

		List<Task> tasks = new ArrayList<>();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt(1);
				TaskData taskData = new TaskData(
						rs.getString(2),
						rs.getString(3),
						rs.getDate(5).toLocalDate(),
						State.valueOf(rs.getString(4)));
				tasks.add(new Task(id, taskData));
			}
			return Optional.of(tasks);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	/**
	 * Returns a task by id
	 *
	 * @param conn
	 * @param id   the id of the task
	 * @return
	 */
	private static Optional<Task> getTask(Connection conn, long id) {
		String sql = "SELECT id, title, description, state, due FROM Tasks WHERE id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) id); //fixme cast
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return Optional.of(new Task(rs.getInt(1),
						new TaskData(
								rs.getString(2),
								rs.getString(3),
								rs.getDate(5).toLocalDate(),
								State.valueOf(rs.getString(4)))));
			}
			return Optional.empty();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return Optional.empty();
	}

	/**
	 * Updates the task
	 *
	 * @param conn
	 */
	private static void update(Connection conn, Task task) {
		final String sql = "UPDATE Tasks " +
				"SET title = ? , description = ? , state = ? , due = ? " +
				"WHERE id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, task.data.title);
			pstmt.setString(2, task.data.desc);
			pstmt.setString(3, task.data.state.name());
			pstmt.setDate(4, Date.valueOf(task.data.dueDate));
			pstmt.setInt(5, (int) task.id); //fixme cast
			int res = pstmt.executeUpdate();
			if (res == 0) {
				throw new IllegalStateException("Update - keine Task mit dieser ID: " + task.id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes the task by id
	 *
	 * @param conn
	 * @param id
	 */
	private static void delete(Connection conn, long id) {
		final String sql = "DELETE FROM Tasks " +
				"WHERE id IS ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) id); //fixme cast
			if (pstmt.executeUpdate() == 0) {
				throw new IllegalStateException("Delete - keine Task mit dieser ID: " + id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new Database at the given (= variable url) location
	 *
	 * @param conn
	 */
	public static void createNewDatabase(Connection conn) {
		DatabaseMetaData meta = null;
		try {
			meta = conn.getMetaData();
			//System.out.println("The driver name is " + meta.getDriverName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("A new database has been created.");
	}

	/**
	 * Creates a new table
	 *
	 * @param conn
	 */
	public static void createNewTable(Connection conn) {
		// SQL statement for creating a new table
		final String sql = "CREATE TABLE IF NOT EXISTS Tasks(\n"
				+ "	id integer PRIMARY KEY,\n"
				+ "	title text NOT NULL,\n"
				+ "	description text NOT NULL,\n"
				+ "	state text NOT NULL,\n"
				+ "	due date NOT NULL"
				+ ");";

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		deleteTable(conn);
	}

	public void setUp() {
//		deleteTable(conn);
		createNewDatabase(conn);
		createNewTable(conn);
	}

	@Override
	public Task create(TaskData data) {
		Task newTask = new Task(nextID++, data);
		insert(conn, newTask);
		return newTask;
	}

	@Override
	public List<Task> read() {
		return getTasks(conn).orElse(null); //fixme throw error?
	}

	@Override
	public Task read(long id) {
		return getTask(conn, id).orElse(null); //fixme throw error?
	}

	@Override
	public void update(Task updated) {
		update(conn, updated);
	}

	@Override
	public void delete(long id) {
		delete(conn, id);
	}

	public void disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteTable(Connection conn) {
		final String sql = "DELETE FROM Tasks";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public enum Database {
		REPOSITORY("jdbc:sqlite:Repository.db"), TEST("jdbc:sqlite:TestRepository.db");
		String url;

		Database(String url) {
			this.url = url;
		}

		public String getUrl() {
			return url;
		}
	}
}
