package io.github.projectchroma.launcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class FileInterface{
	public static final File DIR = new File("data"), SHA_FILE = new File(DIR, "sha.dat"), JAR_FILE = new File(DIR, "Chroma.jar");
	public static String getDownloadedVersion(){
		try(Scanner in = new Scanner(SHA_FILE)){
			return in.nextLine();
		}catch(FileNotFoundException ex){return null;}
	}
	public static void writeSHA(String sha) throws FileNotFoundException{
		try(PrintStream out = new PrintStream(SHA_FILE)){
			out.println(sha);
		}
	}
}
