package SocketClient.Practice;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1", 2000);
		System.out.println("Hello client !");
		new ClientProcess(socket).start();
	}
}
