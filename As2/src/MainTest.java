
import java.io.PrintStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import tasks.TasksTest;

public class MainTest {

	public static void zipIt(String zipFileName)
			throws IOException {
		String sourceFile = "src";
		FileOutputStream fos = new FileOutputStream(
				zipFileName);
		ZipOutputStream zipOut = new ZipOutputStream(fos);
		File fileToZip = new File(sourceFile);

		zipFile(fileToZip, fileToZip.getName(), zipOut);
		zipOut.close();
		fos.close();
	}

	private static void zipFile(File fileToZip,
			String fileName, ZipOutputStream zipOut)
			throws IOException {
		if (fileToZip.isHidden()) {
			return;
		}
		if (fileToZip.isDirectory()) {
			if (fileName.endsWith("/")) {
				zipOut.putNextEntry(
						new ZipEntry(fileName));
				zipOut.closeEntry();
			} else {
				zipOut.putNextEntry(
						new ZipEntry(fileName + "/"));
				zipOut.closeEntry();
			}
			File[] children = fileToZip.listFiles();
			for (File childFile : children) {
				zipFile(childFile,
						fileName + "/"
								+ childFile.getName(),
						zipOut);
			}
			return;
		}
		FileInputStream fis = new FileInputStream(
				fileToZip);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zipOut.putNextEntry(zipEntry);
		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zipOut.write(bytes, 0, length);
		}
		fis.close();
	}

	public static boolean allGood = true;

	private static boolean check(boolean cond,
			String message) {
		if (!cond) {
			System.err.println("Problem: " + message);
			allGood = false;
			return false;
		}
		return true;
	}

	private static PrintStream stdout = System.out;

	private static void testExDetails() {
		ExDetails s1 = ExDetails.firstStudent();
		ExDetails s2 = ExDetails.secondStudent();

		System.out.println(
				"Check your personal details are correct here:");
		System.out.println(s1);
		System.out.println(s2);
		String zipName = String.format("%s_%s.zip",
				s1.getId(), s2.getId());
		System.out.println("--------------------------------------");
		try {
			zipIt(zipName);
			System.out.println(
					"Your zip file was created automatically! "
							+ "you can find it in the project directory");
			System.out.println("its name is " + zipName);
		} catch (IOException e) {
			System.err.println(
					"Problem creating zip file! " + e);
		}
	}

// -------------------------------------------------------------------------------
// main

	public static void main(String[] args) {
		check(MainTest.class.getPackage() == null,
				"MainTest is not in the default package!");

		tree.TreeTest.main(null);
		TasksTest.main(null);
		game.GameTest.main(null);

		if (allGood && tree.TreeTest.allGood
				&& TasksTest.allGood
				&& game.GameTest.allGood) {
			System.out.println("ALL BASIC TESTS PASSED");
			System.out.println(
					"--------------------------------------");
			testExDetails();
		} else {
			System.err.println(
					"--------------------------------------");
			System.err.println("SEE PROBLEMS ABOVE");
		}
	}
}
