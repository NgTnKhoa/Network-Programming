package IO.IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class IO {
	public static void fileSpliterBySize(String path, long size) throws IOException {
		File file = new File(path);

		if (!file.exists())
			return;

		if (file.isFile()) {
			File newFolder = new File(file.getParent() + "\\split_" + file.getName().split("\\.")[0]);
			newFolder.mkdir();

			FileInputStream fis = new FileInputStream(file);
			long totalSize = file.length();
			long countFile = totalSize / size;
			long remainder = totalSize % size;

			for (int i = 1; i <= countFile; i++) {
				FileOutputStream fos = new FileOutputStream(newFolder.getAbsolutePath() + "\\split" + i);
				for (int j = 0; j < size; j++) {
					fos.write(fis.read());
				}
				fos.close();
			}

			if (remainder != 0) {
				FileOutputStream fos = new FileOutputStream(newFolder.getAbsolutePath() + "\\split" + (countFile + 1));
				for (int i = 0; i < remainder; i++) {
					fos.write(fis.read());
				}
				fos.close();
			}
			fis.close();
		}
	}

	public static void fileSpliterByQuantity(String path, long quantity) throws IOException {
		File file = new File(path);

		if (!file.exists())
			return;

		if (file.isFile()) {
			File newFolder = new File(file.getParent() + "\\split_" + file.getName().split("\\.")[0]);
			newFolder.mkdir();

			FileInputStream fis = new FileInputStream(file);
			long totalSize = file.length();
			long size = totalSize / quantity;

			for (int i = 1; i <= quantity; i++) {
				FileOutputStream fos = new FileOutputStream(newFolder.getAbsolutePath() + "\\split" + i);
				for (int j = 0; j < size; j++) {
					fos.write(fis.read());
				}
				fos.close();
			}
			fis.close();
		}
	}

	public static void fileJoiner(String path, String ext) throws IOException {
		File folder = new File(path);

		if (!folder.exists())
			return;

		if (folder.isDirectory()) {
			File[] listFile = folder.listFiles();
			FileOutputStream fos = new FileOutputStream(folder.getParent() + "\\joiner." + ext);

			for (int i = 1; i <= listFile.length; i++) {
				FileInputStream fis = new FileInputStream(folder.getAbsoluteFile() + "\\split" + i);

				int data;
				while ((data = fis.read()) != -1) {
					fos.write(data);
				}
				fis.close();
			}
			fos.close();
		}
	}

	public static void fileCopy(String src, String dest) throws IOException {
		FileInputStream fis = new FileInputStream(src);
		FileOutputStream fos = new FileOutputStream(dest);

		int data;
		while ((data = fis.read()) != -1) {
			fos.write(data);
		}

		fis.close();
		fos.close();
	}

	public static void folderCopy(String src, String dest) throws IOException {
		File srcFolder = new File(src);
		File[] srcChildren = srcFolder.listFiles();

		if (!srcFolder.isDirectory()) return;
		
		File destFolder = new File(dest);
		destFolder.mkdir();
		for (File child : srcChildren) {
			if (child.isDirectory()) {
				folderCopy(child.getAbsolutePath(), destFolder.getAbsolutePath() + "\\" + child.getName());
			} else {
				fileCopy(child.getAbsolutePath(), destFolder.getAbsolutePath() + "\\" + child.getName());
			}
		}
	}
	
	public static List<File> getRegularFiles(String folder) {
		List<File> regFiles = new ArrayList<File>();
		File dir = new File(folder);
		File[] listFile = dir.listFiles();
		
		for (File file : listFile) {
			if (file.isFile()) {
				regFiles.add(file);
			}
		}
		
		return regFiles;
	}
	
	public static void pack(String folder, String packedFile) throws IOException {
		List<File> regFiles = getRegularFiles(folder);
		RandomAccessFile raf = new RandomAccessFile(packedFile, "rw");
		
		long[] FEPos = new long[regFiles.size()];
		int index = 0;
		long pos = 0;
		
		raf.writeInt(regFiles.size());
		for (File file : regFiles) {
			FEPos[index++] = raf.getFilePointer();
			raf.writeLong(pos);
			raf.writeLong(file.length());
			raf.writeUTF(file.getName());
		}
		
		index = 0;
		for (File file : regFiles) {
			pos = raf.getFilePointer();
			System.out.println(pos);
			raf.seek(FEPos[index++]);
			raf.writeLong(pos);
			raf.seek(pos);
			
			byte[] buff = new byte[102400];
			int data;
			FileInputStream fis = new FileInputStream(file);
			while((data = fis.read()) != -1) {
				raf.write(buff, 0, data);
			}
			fis.close();
		}
		raf.close();
	}
	
	public static void unPack(String packedFile, String extractFile, String dest) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(packedFile, "r");
		long pos, size;
		String name;
		
		int fileNo = raf.readInt();
		for (int i = 0; i < fileNo; i++) {
			pos = raf.readLong();
			size = raf.readLong();
			name = raf.readUTF();
			
			if (name.equalsIgnoreCase(extractFile)) {
				FileOutputStream fos = new FileOutputStream(dest);
				raf.seek(pos);
				for (long k = 0; k < size; k++) {
					fos.write(raf.read());
				}
				fos.close();
				break;
			}
		}
		raf.close();
	}

	public static void main(String[] args) throws IOException {
//		File file = new File("D://test//Teachers.txt");
//		fileSpliterBySize(file.getAbsolutePath(), 100);
//		fileSpliterByQuantity(file.getAbsolutePath(), 5);
		
//		fileJoiner(file.getParent() + "//split_Teachers", "txt");
		
//		folderCopy(source, dest);
		
//		String source = "D:\\test\\POP3";
//		String dest = "D:\\test\\packed.pack";
//		pack(source, dest);
		
		String packed = "D:\\test\\packed.pack";
		String extractFile = "README.md";
		String dest = "D:\\test\\README_copy.md";
		unPack(packed, extractFile, dest);
		
	}
}
