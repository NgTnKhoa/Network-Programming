package SocketServer.Practice;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DAO {
	private static String path = "src/Practice/DB.dat";
	private static DAO instance;
	private Map<Integer, Long> map;
	private RandomAccessFile raf;

	public DAO() throws IOException {
		File file = new File(path);
		this.map = new HashMap<Integer, Long>();

		if (!file.exists()) {
			file.createNewFile();
			raf = new RandomAccessFile(file, "rw");
			raf.writeInt(0);
		} else {
			raf = new RandomAccessFile(file, "rw");
		}
	}

	public static DAO getInstance() throws IOException {
		if (instance == null) {
			instance = new DAO();
		}
		return instance;
	}

	public boolean isValidCandidate(Candidate candidate) {
		return candidate.isValidAge();
	}

	public void insertCandidate(Candidate candidate) throws IOException {
		// set number of candidate & id
		raf.seek(0);
		int size = raf.readInt();
		size++;
		raf.seek(0);
		raf.writeInt(size);

		raf.seek(raf.length());
		candidate.setId(size);

		// string to bytes
		byte[] nameBytes = charToByte(toCharArray(candidate.getName(), 25));
		byte[] birthdayBytes = charToByte(toCharArray(candidate.getBirthday(), 10));
		byte[] addressBytes = charToByte(toCharArray(candidate.getAddress(), 25));
		// empty bytes for img
		byte[] bytes = new byte[100000];

		// write candidate
		long pointer = raf.getFilePointer();

		raf.writeInt(candidate.getId());
		raf.write(nameBytes);
		raf.write(birthdayBytes);
		raf.write(addressBytes);
		raf.write(bytes);

		map.put(candidate.getId(), pointer);
	}

	private char[] toCharArray(String s, int length) {
		char[] result = new char[length];
		s.getChars(0, s.length(), result, 0);
		return result;
	}

	private byte[] charToByte(char[] chars) {
		byte[] result = new byte[chars.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) chars[i];
		}
		return result;
	}

	public void writeFile(int id, byte[] buff) throws IOException {
		long pointer = map.get(id);
		pointer += 4 + 25 + 10 + 25;
		raf.seek(pointer);
		raf.write(buff);
	}

	public void update(int id, String addressUpdate) throws IOException {
		long pointer = map.get(id);
		pointer += 4 + 25 + 10;
		raf.seek(pointer);
		byte[] addressBytes = charToByte(toCharArray(addressUpdate, 25));
		raf.write(addressBytes);
	}

	public boolean isExist(int idView) {
		return map.containsKey(idView);
	}

	public String getUserData(int idView) throws IOException {
		long pointer = map.get(idView);
		raf.seek(pointer);

		byte[] nameBytes = new byte[25];
		byte[] birthdayBytes = new byte[10];
		byte[] addressBytes = new byte[25];
		byte[] imgBytes = new byte[100000];

		int id = raf.readInt();
		raf.read(nameBytes);
		raf.read(birthdayBytes);
		raf.read(addressBytes);
		raf.read(imgBytes);
		
		String name = bytesToString(nameBytes);
		String birthday = bytesToString(birthdayBytes);
		String address = bytesToString(addressBytes);
		long size = 0;
		for (int i = 0; i < imgBytes.length; i++) {
			if (imgBytes[i] != 0) {
				size++;
			} else {
				break;
			}
		}
		
		return "ID = " + id + ", NAME = " + name + ", BIRTHDAY = " + birthday + ", ADDRESS = " + address + ", FILE SIZE = " + size;
	}

	private String bytesToString(byte[] bytes) {
		return new String(bytes);
	}
}
