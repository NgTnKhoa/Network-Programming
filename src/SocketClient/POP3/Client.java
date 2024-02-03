package SocketClient.POP3;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1", 2222);
		System.out.println("Hello client !");
		
		new ClientProcess(socket).start();
	}
}
