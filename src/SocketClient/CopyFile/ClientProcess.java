package SocketClient.CopyFile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

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
		try {
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			String line = userIn.readLine();
			StringTokenizer st = new StringTokenizer(line);
			String command, src, dest;
			
			command = st.nextToken();
			src = st.nextToken();
			dest = st.nextToken();
			
			netOut.writeUTF(dest);
			netOut.flush();
			
			boolean error = netIn.readBoolean();
			
			if (error) {
				socket.close();
				return;
			}
			
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
			byte[] buff = new byte[1024];
			int data;
			
			while ((data = bis.read(buff)) != -1) {
				netOut.write(buff, 0, data);
			}
			netOut.flush();
			
			bis.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
