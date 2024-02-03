package RMIServer.RMI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO {
	public static String DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";
	public static String URL = "jdbc:ucanaccess://D://test//DB.accdb";
	private Connection connection = null;
	
	public void createConnection() throws SQLException, ClassNotFoundException {
		Class.forName(DRIVER);
		this.connection = DriverManager.getConnection(URL);
	}
	
	public boolean checkUsername(String username) throws ClassNotFoundException, SQLException {
		boolean result = false;
		
		createConnection();
		String sql = "select * from account";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			String usernameChecker = rs.getString("username");
			
			if (username.equals(usernameChecker)) {
				result = true;
				break;
			}
		}
		rs.close();
		stmt.close();
		connection.close();
		
		return result;
	}
	
	public boolean checkLogin(String username, String password) throws ClassNotFoundException, SQLException {
		boolean result = false;
		
		createConnection();
		String sql = "select * from account";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			String usernameChecker = rs.getString("username");
			String passwordChecker = rs.getString("password");
			
			if (username.equals(usernameChecker) && password.equals(passwordChecker)) {
				result = true;
				break;
			}
		}
		rs.close();
		stmt.close();
		connection.close();
		
		return result;
	}
	
	public List<Student> findById(int id) throws SQLException, ClassNotFoundException {
		List<Student> result = new ArrayList<Student>();
		
		createConnection();
		String sql = "select * from student where id = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, id);
		
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int idStudent = rs.getInt("id");
			String name = rs.getString("name");
			int age = rs.getInt("age");
			double score = rs.getDouble("score");
			
			result.add(new Student(idStudent, name, age, score));
		}
		
		rs.close();
		stmt.close();
		connection.close();
		
		return result;
	}
	
	public List<Student> findByName(String name) throws SQLException, ClassNotFoundException {
		List<Student> result = new ArrayList<Student>();
		
		createConnection();
		String sql = "select * from student";
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			String nameStudent = rs.getString("name");
			
			if (nameStudent.endsWith(name)) {
				int id = rs.getInt("id");
				int age = rs.getInt("age");
				double score = rs.getDouble("score");
				result.add(new Student(id, nameStudent, age, score));
			}
		}
		
		rs.close();
		stmt.close();
		connection.close();
		
		return result;
	}
	
	public List<Student> findByAge(int age) throws SQLException, ClassNotFoundException {
		List<Student> result = new ArrayList<Student>();
		
		createConnection();
		String sql = "select * from student where age = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, age);
		
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int ageStudent = rs.getInt("age");
			double score = rs.getDouble("score");
			
			result.add(new Student(id, name, ageStudent, score));
		}
		
		rs.close();
		stmt.close();
		connection.close();
		
		return result;
	}
	
	public List<Student> findByScore(double score) throws SQLException, ClassNotFoundException {
		List<Student> result = new ArrayList<Student>();
		
		createConnection();
		String sql = "select * from student where score = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, score);
		
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int age = rs.getInt("age");
			double scoreStudent = rs.getDouble("score");
			
			result.add(new Student(id, name, age, scoreStudent));
		}
		
		rs.close();
		stmt.close();
		connection.close();
		
		return result;
	}
	
	public void insert(Student student) throws ClassNotFoundException, SQLException {
		createConnection();
		String sql = "insert into Student(name, age, score) values(?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, student.getName());
		stmt.setInt(2, student.getAge());
		stmt.setDouble(3, student.getScore());
		stmt.executeUpdate();
		
		stmt.close();
		connection.close();
	}
	
	public void update(int id, Student student) throws ClassNotFoundException, SQLException {
		createConnection();
		String sql = "update Student set name = ?, age = ?, score = ? where id = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, student.getName());
		stmt.setInt(2, student.getAge());
		stmt.setDouble(3, student.getScore());
		stmt.setInt(4, id);
		stmt.executeUpdate();
		
		stmt.close();
		connection.close();
	}
	
	public void delete(int id) throws SQLException, ClassNotFoundException {
		createConnection();
		String sql = "delete from Student where id = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.executeUpdate();
		
		stmt.close();
		connection.close();
	}
}
