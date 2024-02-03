package SocketServer.CopyFile;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerProcess extends Thread {
	Socket socket;
	DataInputStream netIn;
	DataOutputStream netOut;
	
	public ServerProcess(Socket socket) throws IOException {
		this.socket = socket;
		netIn = new DataInputStream(socket.getInputStream());
		netOut = new DataOutputStream(socket.getOutputStream());
	}
	
	@Override
	public void run() {
		try {
			String dest = netIn.readUTF();
			boolean error = false;
			try {
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest));
				netOut.writeBoolean(error);
				netOut.flush();
				
				byte[] buff = new byte[1024];
				int data;
				
				while ((data = netIn.read(buff)) != -1) {
					bos.write(buff, 0, data);
				}

				bos.close();
				socket.close();
			} catch (FileNotFoundException e) {
				error = true;
				netOut.writeBoolean(error);
				netOut.flush();
				socket.close();
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
