package SocketServer.Operations;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class ServerProcess extends Thread {
	Socket socket;
	DataInputStream netIn;
	DataOutputStream netOut;
	double operand1, operand2;
	char operator;
	
	public ServerProcess(Socket socket) throws IOException {
		this.socket = socket;
		netIn = new DataInputStream(socket.getInputStream());
		netOut = new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		String request, response;

		try {
			while (true) {
				request = netIn.readUTF();

				if (request.equalsIgnoreCase("exit")) {
					netOut.writeUTF("Bye");
					netOut.flush();
					break;
				}

				try {
					requestAnal(request);
					double result = doRequest();
					response = request + " = " + result;
					
					netOut.writeUTF(response);
					netOut.flush();
				} catch (MyException e) {
					netOut.writeUTF(e.getMessage());
				}
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void requestAnal(String request) throws MyException {
		StringTokenizer st = new StringTokenizer(request, "+-*/");
		String value1, value2;

		try {
			value1 = st.nextToken();
			value2 = st.nextToken();
		} catch (NoSuchElementException e) {
			String line = request.trim();
			char c = line.charAt(0);
			if ((c == '+') || (c == '-') || (c == '*') || (c == '/'))
				throw new MyException("First operand is invalid !");
			else
				throw new MyException("Second operand is invalid !");
		}

		try {
			this.operand1 = Double.parseDouble(value1.trim());
		} catch (NumberFormatException e) {
			throw new MyException("First operand is invalid !");
		}

		try {
			this.operand2 = Double.parseDouble(value2.trim());
		} catch (NumberFormatException e) {
			throw new MyException("Second operand is invalid !");
		}

		this.operator = request.charAt(value1.length());
	}

	private double doRequest() {
		double result = 0;

		switch (this.operator) {
		case '+':
			result = this.operand1 + this.operand2;
			break;
		case '-':
			result = this.operand1 - this.operand2;
			break;
		case '*':
			result = this.operand1 * this.operand2;
			break;
		case '/':
			result = this.operand1 / this.operand2;
			break;
		}

		return result;
	}
}
