package IO.FileClass;

import java.io.File;

public class FileClass {
	public static boolean remove(String path) {
		File file = new File(path);
		File[] listFile = file.listFiles();

		if (!file.exists()) return true;
		
		if (listFile != null) {
			for (File f : listFile) {
				remove(f.getAbsolutePath());
			}
		}

		return file.delete();
	}

	public static boolean removeAdvanced(String path) {
		File file = new File(path);
		File[] listFile = file.listFiles();

		if (!file.exists()) return true;
				
		if (listFile != null) {
			for (File f : listFile) {
				removeAdvanced(f.getAbsolutePath());
			}
		}

		if (file.isDirectory()) {
			return true;
		} else {
			return file.delete();
		}
	}
	
	public static void findAll(String path, String ...ex) {
		File file = new File(path);
		File[] listFile = file.listFiles();
		
		if (!file.exists()) return;
		
		if (listFile != null) {
			for (File f : listFile) {
				findAll(f.getAbsolutePath(), ex);
			}
		}

		for (String s : ex) {
			if (file.getName().endsWith(s)) {
				System.out.println(file.getAbsolutePath());
			}
		}
	}
	
	public static boolean deleteAll(String path, String ...ex) {
		File file = new File(path);
		File[] listFile = file.listFiles();
		
		if (!file.exists()) return true;
		
		if (listFile != null) {
			for (File f : listFile) {
				deleteAll(f.getAbsolutePath(), ex);
			}
		}

		for (String s : ex) {
			if (file.getName().endsWith(s)) {
				return file.delete();
			}
		}
		
		return true;
	}

	public static void main(String[] args) {
		File file = new File("D://test//POP3");
//		remove(file.getAbsolutePath());
//		removeAdvanced(file.getAbsolutePath());
//		findAll(file.getAbsolutePath(), ".java", ".md");
		deleteAll(file.getAbsolutePath(), ".java", ".md");
	}
}
