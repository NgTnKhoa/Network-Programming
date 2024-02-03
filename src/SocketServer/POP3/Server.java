package SocketServer.POP3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(2222);
		System.out.println("Waiting for login ...");
		
		while (true) {
			Socket socket = serverSocket.accept();
			new ServerProcess(socket).start();
		}
	}
}
