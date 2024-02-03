package SocketServer.POP3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ServerProcess extends Thread {
	Socket socket;
	DataInputStream netIn;
	DataOutputStream netOut;
	DAO dao;

	public ServerProcess(Socket socket) throws IOException {
		this.socket = socket;
		netIn = new DataInputStream(socket.getInputStream());
		netOut = new DataOutputStream(socket.getOutputStream());
		this.dao = new DAO();
	}

	@Override
	public void run() {
		boolean isLogin = false;
		String com, param;
		String currentUsername = null;

		try {
			String response = null;
			String line;
			
			while (!isLogin) {
				line = netIn.readUTF();
				
				if (line.equalsIgnoreCase("quit")) {
					break;
				}
				
				StringTokenizer st = new StringTokenizer(line);

				com = st.nextToken();
				com = com.toUpperCase();
				param = st.nextToken();

				switch (com) {
				case "USER":
					if (dao.checkUser(param)) {
						response = "OK !";
						currentUsername = param;
					}
					break;
				case "PASS":
					if (currentUsername != null) {
						if (dao.login(currentUsername, param)) {
							response = "Logged in successfully !";
							isLogin = true;
						} else {
							response = "Invalid password !";
						}
					} else {
						response = "Username first !";
					}
					break;
				default:
					response = "Invalid command !";
					break;
				}
				netOut.writeUTF(response);
				netOut.flush();
			}
			
			while (isLogin) {
				line = netIn.readUTF();
				
				if (line.equalsIgnoreCase("quit")) {
					break;
				}
				
				StringTokenizer st = new StringTokenizer(line);
				
				com = st.nextToken();
				com = com.toUpperCase();
				param = st.nextToken();
				
				switch (com) {
				case "FINDBYID":
					response = makeResponse(dao.findById(param));
					break;
				case "FINDBYNAME":
					response = makeResponse(dao.findByName(line.substring(com.length()).trim()));
					break;
				default:
					response = "Invalid command !";
					break;
				}
				
				netOut.writeUTF(response);
				netOut.flush();
			}
			
			netOut.writeUTF("Bye");
			netOut.flush();
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String makeResponse(ArrayList<Student> result) {
		String response = "";
		
		if (result.isEmpty()) {
			return "Can not find student !";
		} else {
			for (Student student : result) {
				response += student.toString() + "\n";
			}
		}
		
		return response;
	}
}
