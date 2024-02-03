package SocketServer.Practice;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerProcess extends Thread {
	private Socket socket;
	private DataInputStream netIn;
	private DataOutputStream netOut;
	private boolean isExit;
	private DAO dao;

	public ServerProcess(Socket socket) throws IOException {
		this.socket = socket;
		this.netIn = new DataInputStream(socket.getInputStream());
		this.netOut = new DataOutputStream(socket.getOutputStream());
		this.isExit = false;
		this.dao = dao.getInstance();
	}

	@Override
	public void run() {
		try {
			while (!isExit) {
				String line = netIn.readUTF();
				StringTokenizer st = new StringTokenizer(line, "\t");
				String command = st.nextToken().toUpperCase();

				switch (command) {
				case "REGISTER":
					String name = st.nextToken();
					String birthday = st.nextToken();
					String address = st.nextToken();
					Candidate candidate = new Candidate(name, birthday, address);

					if (dao.isValidCandidate(candidate)) {
						dao.insertCandidate(candidate);

						netOut.writeBoolean(true);
						netOut.flush();
						
						netOut.writeInt(candidate.getId());
						netOut.flush();
					} else {
						netOut.writeBoolean(false);
						netOut.flush();
					}
					break;
				case "FOTO":
					long size = netIn.readLong();

					if (size <= 100000) {
						netOut.writeBoolean(true);
						netOut.flush();
						
						byte[] buff = new byte[100000];
						netIn.read(buff);
                        int id = netIn.readInt();
                        dao.writeFile(id, buff);
                        netOut.writeBoolean(true);
                        netOut.flush();
					} else {
						netOut.writeBoolean(false);
						netOut.flush();
					}
					break;
				case "VIEW":
					int idView = Integer.parseInt(st.nextToken());
					if (dao.isExist(idView)) {
						netOut.writeUTF(dao.getUserData(idView));
						netOut.flush();
					} else {
						netOut.writeUTF("Candidate is not exist !");
						netOut.flush();
					}
				case "UPDATE":
					int idUpdate = Integer.parseInt(st.nextToken());
					String addressUpdate = st.nextToken();
					dao.update(idUpdate, addressUpdate);
					netOut.writeUTF("Update successfully !");
					netOut.flush();
					break;
				case "QUIT":
					netOut.writeUTF("Bye !");
					netOut.flush();
					isExit = true;
					break;
				default:
					netOut.writeUTF("Invalid command !");
					netOut.flush();
					break;
				}
			}
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
