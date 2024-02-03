package RMIClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.StringTokenizer;

import RMI.IServer;
import RMI.Student;

public class Client {
	public static void main(String[] args) throws NotBoundException, IOException {
		Registry registry = LocateRegistry.getRegistry("localhost", 2222);
		IServer server = (IServer) registry.lookup("server");
		boolean login = false;
		boolean exit = false;
		String currentUsername = "";
		BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

		String line;
		StringTokenizer st;
		String com, param;
		List<Student> result;
		while (!exit) {
			try {
				while (!login) {
					line = userIn.readLine();
					st = new StringTokenizer(line);
					com = st.nextToken().toUpperCase();
					
					if (com.equalsIgnoreCase("EXIT")) {
						System.out.println("Bye !");
						exit = true;
						break;
					}
					
					param = st.nextToken();
					
					try {
						switch (com) {
						case "USERNAME":
							if (server.checkUsername(param)) {
								System.out.println("Username is correct !");
								currentUsername = param;
							} else {
								System.out.println("Invalid username !");
							}
							break;
						case "PASSWORD":
							if (server.checkLogin(currentUsername, param)) {
								System.out.println("Login successfully !");
								login = true;
							} else {
								System.out.println("Invalid password !");
							}
							break;
						case "FINDBYID":
							System.out.println("Please login !");
							break;
						case "FINDBYNAME":
							System.out.println("Please login !");
							break;
						case "FINDBYAGE":
							System.out.println("Please login !");
							break;
						case "FINDBYSCORE":
							System.out.println("Please login !");
							break;
						default:
							System.out.println("Invalid command !");
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (exit) break;
				
				while (login) {
					try {
						line = userIn.readLine();
						st = new StringTokenizer(line);
						com = st.nextToken().toUpperCase();
						
						if (com.equalsIgnoreCase("EXIT")) {
							System.out.println("Bye !");
							exit = true;
							break;
						}
						
						param = st.nextToken();
						
						switch (com) {
						case "USERNAME":
							System.out.println("You are already logged in !");
							break;
						case "PASSWORD":
							System.out.println("You are already logged in !");
							break;
						case "FINDBYID":
							result = server.findById(Integer.parseInt(param));
							
							if (result.size() == 0) {
								System.out.println("Can not find student !");
								break;
							}
							
							for (Student student : result) {
								System.out.println(student.toString());
							}
							break;
						case "FINDBYNAME":
							result = server.findByName(param);
							
							if (result.size() == 0) {
								System.out.println("Can not find student !");
								break;
							}
							
							for (Student student : result) {
								System.out.println(student.toString());
							}
							break;
						case "FINDBYAGE":
							result = server.findByAge(Integer.parseInt(param));
							
							if (result.size() == 0) {
								System.out.println("Can not find student !");
								break;
							}
							
							for (Student student : result) {
								System.out.println(student.toString());
							}
							break;
						case "FINDBYSCORE":
							result = server.findByScore(Double.parseDouble(param));
							
							if (result.size() == 0) {
								System.out.println("Can not find student !");
								break;
							}
							
							for (Student student : result) {
								System.out.println(student.toString());
							}
							break;
						default:
							System.out.println("Invalid command !");
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
