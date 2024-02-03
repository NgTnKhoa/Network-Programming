package SocketServer.Operations;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(2222);
		System.out.println("Waiting for expression ...");
		
		while (true) {
			Socket socket = serverSocket.accept();
			new ServerProcess(socket).start();
		}
	}
}
