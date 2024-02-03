package SocketClient.Practice;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientProcess extends Thread {
	private Socket socket;
	private DataInputStream netIn;
	private DataOutputStream netOut;
	private boolean isExit;
	private boolean isRegistered;
	private boolean isUploaded;
	private int id;

	public ClientProcess(Socket socket) throws IOException {
		this.socket = socket;
		this.netIn = new DataInputStream(socket.getInputStream());
		this.netOut = new DataOutputStream(socket.getOutputStream());
		this.isExit = false;
		this.isRegistered = false;
		this.isUploaded = false;
	}

	@Override
	public void run() {
		try {
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			while (!isExit) {
				String line = userIn.readLine();
				StringTokenizer st = new StringTokenizer(line, "\t");
				int countTokens = st.countTokens();
				String command = st.nextToken().toUpperCase();
				
				switch (command) {
				case "REGISTER":
					if (countTokens == 4) {
						if (!isRegistered) {
							netOut.writeUTF(line);
							netOut.flush();
						} else {
							System.out.println("Already registered !");
							break;
						}
						
						boolean isInsert = netIn.readBoolean();
						
						if (isInsert) {
							this.id = netIn.readInt();
							this.isRegistered = true;
							System.out.println("Insert successfully !");
						} else {
							System.out.println("Invalid age !");
						}
					} else {
						System.out.println("Invalid command !");
					}
					break;
				case "FOTO":
					if (countTokens == 2) {
						if (isRegistered) {
							if (!isUploaded) {
								netOut.writeUTF(line);
								netOut.flush();
								
								String path = st.nextToken();
								File file = new File(path);
								
								if (file.exists()) {
									netOut.writeLong(file.length());
									netOut.flush();
									
									boolean isValid = netIn.readBoolean();
									if (isValid) {
										BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
										byte[] buff = new byte[100000];
										bis.read(buff);
                                        netOut.write(buff);
                                        bis.close();
                                        netOut.writeInt(this.id);
                                        netOut.flush();
                                        
										boolean isDone = netIn.readBoolean();
										if (isDone) {
											this.isUploaded = true;
											System.out.println("Upload successfully !");
										}
									} else {
										System.out.println("File is too large !");
									}
								} else {
									System.out.println("Invalid file !");
								}
							} else {
								System.out.println("Already uploaded !");
							}
						} else {
							System.out.println("Unregistered !");
						}
					} else {
						System.out.println("Invalid command !");
					}
					break;
				case "VIEW":
					if (countTokens == 2) {
						netOut.writeUTF(line);
						netOut.flush();
						
						String response = netIn.readUTF();
						System.out.println(response);
					} else {
						netOut.writeUTF("Invalid command !");
						netOut.flush();
					}
					break;
				case "UPDATE":
					if (countTokens == 3) {
						netOut.writeUTF(line);
						netOut.flush();
						
						String response = netIn.readUTF();
						System.out.println(response);
					} else {
						netOut.writeUTF("Invalid command !");
						netOut.flush();
					}
					break;
				case "QUIT":
					netOut.writeUTF(line);
					netOut.flush();
					System.out.println(netIn.readUTF());
					isExit = true;
					break;
				default:
					System.out.println("Invalid command !");
					break;
				}
			}
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
