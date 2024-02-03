package SocketClient.JDBC;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientProcess extends Thread {
	Socket socket;
	DataInputStream netIn;
	DataOutputStream netOut;
	boolean exit;
	
	public ClientProcess(Socket socket) throws IOException {
		this.socket = socket;
		netIn = new DataInputStream(socket.getInputStream());
		netOut = new DataOutputStream(socket.getOutputStream());
		this.exit = false;
	}
	
	@Override
	public void run() {
		try {
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			
			while (!exit) {
				System.out.println("1. Find student");
				System.out.println("2. Add student");
				System.out.println("3. Update student");
				System.out.println("4. Delete student");
				System.out.println("Enter \"EXIT\" to exit !");
				
				String command = userIn.readLine().toUpperCase();
				
				switch (command) {
				case "1":
					findStudent();
					break;
				case "2":
					addStudent();
					break;
				case "3":
					updateStudent();
					break;
				case "4":
					deleteStudent();
					break;
				case "EXIT":
					exit();
					break;
				default:
					System.out.println("Invalid command !");
				}
			}

			userIn.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void exit() throws IOException{
		netOut.writeUTF("EXIT");
		netOut.flush();
		
		this.exit = true;
	}
	
	private void deleteStudent() throws IOException {
		try {
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			netOut.writeUTF("DELETE");
			netOut.flush();
			
			System.out.println("Enter your command: ");
			String line = userIn.readLine();
			netOut.writeUTF(line);
			netOut.flush();
			
			System.out.println(netIn.readUTF());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateStudent() throws IOException {
		try {
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			netOut.writeUTF("UPDATE");
			netOut.flush();
			
			System.out.println("Enter your command: ");
			String line = userIn.readLine();
			netOut.writeUTF(line);
			netOut.flush();
			
			System.out.println(netIn.readUTF());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addStudent() throws IOException {
		try {
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			netOut.writeUTF("INSERT");
			netOut.flush();
			
			System.out.println("Enter your command: ");
			String line = userIn.readLine();
			netOut.writeUTF(line);
			netOut.flush();
			
			System.out.println(netIn.readUTF());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void findStudent() throws IOException {
		try {
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter your command: ");
			String line = userIn.readLine();
			StringTokenizer st = new StringTokenizer(line);
			String com = st.nextToken().toUpperCase();
			String param = st.nextToken();

			switch (com) {
			case "FINDBYID":
				int id = Integer.parseInt(param);
				findById(id);
				break;
			case "FINDBYNAME":
				findByName(param);
				break;
			case "FINDBYAGE":
				int age = Integer.parseInt(param);
				findByAge(age);
				break;
			case "FINDBYSCORE":
				double score = Double.parseDouble(param);
				findByScore(score);
				break;

			default:
				System.out.println("Invalid command !");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void findById(int id) throws IOException {
		netOut.writeUTF("FINDBYID");
		netOut.flush();
		netOut.writeInt(id);
		netOut.flush();
		
		int size = netIn.readInt();
		for (int i = 0; i < size; i++) {
			System.out.println(netIn.readUTF());
		}
	}
	
	private void findByName(String name) throws IOException {
		netOut.writeUTF("FINDBYNAME");
		netOut.flush();
		netOut.writeUTF(name);
		netOut.flush();
		
		int size = netIn.readInt();
		for (int i = 0; i < size; i++) {
			System.out.println(netIn.readUTF());
		}
	}
	
	private void findByAge(int age) throws IOException {
		netOut.writeUTF("FINDBYAGE");
		netOut.flush();
		netOut.writeInt(age);
		netOut.flush();
		
		int size = netIn.readInt();
		for (int i = 0; i < size; i++) {
			System.out.println(netIn.readUTF());
		}
	}
	
	private void findByScore(double score) throws IOException {
		netOut.writeUTF("FINDBYSCORE");
		netOut.flush();
		netOut.writeDouble(score);
		netOut.flush();
		
		int size = netIn.readInt();
		for (int i = 0; i < size; i++) {
			System.out.println(netIn.readUTF());
		}
	}
}
