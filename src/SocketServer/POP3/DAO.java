package SocketServer.POP3;

import java.util.ArrayList;

public class DAO {
	ArrayList<Student> students;
	ArrayList<User> users;
	
	public DAO() {
		students = new ArrayList<Student>();
		students.add(new Student(111, "Trần Văn Ơn", 2000, 7.5));
		students.add(new Student(112, "Nguyễn Thị Như", 2002, 7.5));
		students.add(new Student(113, "Nguyen Thi Hong", 2002, 9));
		students.add(new Student(114, "Nguyen Thi Hoa Hong", 2003, 10));
		students.add(new Student(115, "Trần Văn Ơn", 2000, 7.5));
		
		users = new ArrayList<User>();
		users.add(new User("lphung", "12345"));
		users.add(new User("pvthuan", "23456"));
	}
	
	public boolean checkUser(String username) {
		boolean result = false;
		
		for (User user : this.users) {
			if (user.name.equals(username)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public boolean login(String username, String password) {
		boolean result = false;
		
		for (User user : this.users) {
			if (user.name.equals(username) && user.password.equals(password)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public ArrayList<Student> findById(String id) {
		ArrayList<Student> result = new ArrayList<Student>();
		
		for (Student student : this.students) {
			if (student.id == Integer.parseInt(id)) {
				result.add(student);
				break;
			}
		}
		
		return result;
	}
	
	public ArrayList<Student> findByName(String name) {
		ArrayList<Student> result = new ArrayList<Student>();
		
		for (Student student : this.students) {
			if (student.name.endsWith(name)) {
				result.add(student);
			}
		}
		
		return result;
	}
}
