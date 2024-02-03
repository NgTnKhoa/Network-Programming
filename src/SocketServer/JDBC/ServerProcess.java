package SocketServer.JDBC;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServerProcess extends Thread {
	Socket socket;
	DataInputStream netIn;
	DataOutputStream netOut;
	boolean exit;
	DAO dao;
	
	public ServerProcess(Socket socket) throws IOException {
		this.socket = socket;
		netIn = new DataInputStream(socket.getInputStream());
		netOut = new DataOutputStream(socket.getOutputStream());
		this.exit = false;
		dao = new DAO();
	}
	
	@Override
	public void run() {
		try {
			while (!exit) {
				String command = netIn.readUTF();
				
				switch (command) {
				case "FINDBYID": 
					findById();
					break;
				case "FINDBYNAME": 
					findByName();
					break;
				case "FINDBYAGE": 
					findByAge();
					break;
				case "FINDBYSCORE": 
					findByScore();
					break;
				case "INSERT": 
					insert();
					break;
				case "UPDATE": 
					update();
					break;
				case "DELETE": 
					delete();
					break;
				case "EXIT": 
					exit();
					break;
				default:
					break;
				}
			}
			
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void exit() {
		this.exit = true;
		System.out.println("Client " + socket.getInetAddress() + " has disconnected !");
	}
	
	private void delete() throws IOException, ClassNotFoundException, SQLException {
		String command = netIn.readUTF();
		int id = Integer.parseInt(command);
		
		dao.delete(id);
		netOut.writeUTF("Student deleted !");
	}

	private void update() throws IOException, ClassNotFoundException, SQLException {
		String command = netIn.readUTF();
		String[] com = command.split(" ");
		
		int id = Integer.parseInt(com[0]);
		String name = "";
		for (int i = 1; i < com.length - 2; i++) {
			name += com[i] + " ";
		}
		name = name.trim();
		int age = Integer.parseInt(com[com.length - 2]);
		double score = Double.parseDouble(com[com.length - 1]);
		
		Student updateStudent = new Student(name, age, score);
		dao.update(id, updateStudent);
		
		netOut.writeUTF("Student updated !");
		netOut.flush();
	}

	private void insert() throws IOException, ClassNotFoundException, SQLException {
		String command = netIn.readUTF();
		String[] com = command.split(" ");
		
		String name = "";
		for (int i = 0; i < com.length - 2; i++) {
			name += com[i] + " ";
		}
		name = name.trim();
		int age = Integer.parseInt(com[com.length - 2]);
		double score = Double.parseDouble(com[com.length - 1]);
		
		Student newStudent = new Student(name, age, score);
		dao.insert(newStudent);
		
		netOut.writeUTF("New student inserted !");
		netOut.flush();
	}

	private void findById() throws IOException, ClassNotFoundException, SQLException {
		int id = netIn.readInt();
		List<Student> result = dao.findById(id);
		
		netOut.writeInt(result.size());
		netOut.flush();
		for (Student student : result) {
			netOut.writeUTF(student.toString());
			netOut.flush();
		}
	}
	
	private void findByName() throws IOException, ClassNotFoundException, SQLException {
		String name = netIn.readUTF();
		List<Student> result = dao.findByName(name);
		
		netOut.writeInt(result.size());
		netOut.flush();
		for (Student student : result) {
			netOut.writeUTF(student.toString());
			netOut.flush();
		}
	}
	
	private void findByAge() throws IOException, ClassNotFoundException, SQLException {
		int age = netIn.readInt();
		List<Student> result = dao.findByAge(age);
		
		netOut.writeInt(result.size());
		netOut.flush();
		for (Student student : result) {
			netOut.writeUTF(student.toString());
			netOut.flush();
		}
	}
	
	private void findByScore() throws IOException, ClassNotFoundException, SQLException {
		double score = netIn.readDouble();
		List<Student> result = dao.findByScore(score);
		
		netOut.writeInt(result.size());
		netOut.flush();
		for (Student student : result) {
			netOut.writeUTF(student.toString());
			netOut.flush();
		}
	}
}
