package IO.FileClass;

import java.io.File;

public class DirStat {
	public static long getDirectorySize(File dir) {
		long result = 0;
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				result += getDirectorySize(file);
			} else {
				result += file.length();
			}
		}
		return result;
	}
	
	public static boolean checkChildren(File file) {
		File parentFile = file.getParentFile();
		File[] childrenFiles = parentFile.listFiles();
		for (int i = 0; i < childrenFiles.length; i++) {
			if (childrenFiles[i].getPath().equals(file.getPath()) && i == childrenFiles.length - 1) {
				return false;
			}
		}
		return true;
	}

	public static void dirTree(File file, boolean[] levels, int rootChildren) {
		File[] childrenFiles = file.listFiles();
		int children = childrenFiles.length;
		String gap = "";

		// set root path
		if (rootChildren != 1) {
			gap += "|";
		} else {
			gap += " ";
		}

		// set child path
		for (int i = 0; i < levels.length; i++) {
			if (levels[i]) {
				gap += "     |";
			} else {
				if (i == levels.length - 1) {
					gap += "     |";
				} else {
					gap += "      ";
				}
			}
		}

		for (File child : childrenFiles) {
			if (child.isDirectory()) {
				boolean[] newLevels = new boolean[levels.length + 1];

				// set level for child
				for (int i = 0; i < levels.length; i++) {
					if (i < levels.length - 1) {
						newLevels[i] = levels[i];
					} else {
						newLevels[i] = checkChildren(child);
					}
				}
				newLevels[levels.length] = children != 1;
				children--;
				System.out.println(gap + "-----" + getDirectorySize(child) + " bytes");
				dirTree(child, newLevels, rootChildren);
			} else {
				children--;
				System.out.println(gap + "-----" + child.length() + " bytes");
			}
		}

	}

	public static void dirTree(String path) {
		File file = new File(path);
		File[] childrenFiles = file.listFiles();
		int children = childrenFiles.length;

		System.out.println(getDirectorySize(file) + " bytes");
		for (File child : childrenFiles) {
			if (child.isDirectory()) {
				boolean[] levels = new boolean[1];
				levels[0] = child.listFiles().length != 1;
				System.out.println("|-----" + getDirectorySize(child) + " bytes");
				dirTree(child, levels, children);
				children--;
			} else {
				System.out.println("|-----" + child.length() + " bytes");
				children--;
			}
		}
	}

	public static void main(String[] args) {
		File file = new File("C:\\Users\\ADMIN\\Downloads\\TEMP");
		dirTree(file.getPath());
	}
}
