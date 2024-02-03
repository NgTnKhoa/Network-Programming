package SocketClient.Operations;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientProcess extends Thread {
	Socket socket;
	DataInputStream netIn;
	DataOutputStream netOut;
	
	public ClientProcess(Socket socket) throws IOException {
		this.socket = socket;
		netIn = new DataInputStream(socket.getInputStream());
		netOut = new DataOutputStream(socket.getOutputStream());
	}
	
	@Override
	public void run() {
		BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				String request = userIn.readLine();
				
				netOut.writeUTF(request);
				netOut.flush();
				
				String response = netIn.readUTF();
				
				if (response.equalsIgnoreCase("bye")) {
					System.out.println(response);
					netIn.close();
					netOut.close();
					userIn.close();
					break;
				}
				
				System.out.println(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
