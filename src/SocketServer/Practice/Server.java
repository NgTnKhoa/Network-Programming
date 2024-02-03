package SocketServer.Practice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(2000);
		System.out.println("Waiting for client ...");
		
		while (true) {
			Socket socket = serverSocket.accept();
			System.out.println("Client " + socket.getInetAddress() + " connected !");
			new ServerProcess(socket).start();
		}
	}
}
