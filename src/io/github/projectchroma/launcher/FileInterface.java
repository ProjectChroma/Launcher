package io.github.projectchroma.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileInterface{
	/**Base directory for all files*/
	public static final File DIR = new File("chroma");
	/**File for storing the commit SHA last downloaded*/
	public static final File SHA_FILE = new File(DIR, "sha.dat");
	/**File for storing the game JAR file*/
	public static final File JAR_FILE = new File(DIR, "Chroma.jar");
	/**File where the compressed libraries and natives are downloaded to*/
	public static final File LIB_FILE = new File(DIR, "lib.zip");
	/**File for storing native files required for the game*/
	public static final File LIB_DIR = new File(DIR, "lib");
	/**File for storing native files required for the game*/
	public static final File NATIVE_DIR = new File(LIB_DIR, "natives");
	public static void init(){
		DIR.mkdirs();
		NATIVE_DIR.mkdirs();
	}
	public static String getDownloadedChromaVersion(){
		try(Scanner in = new Scanner(SHA_FILE)){
			return in.nextLine();
		}catch(FileNotFoundException ex){return null;}
	}
	public static void writeSHA(String sha) throws FileNotFoundException{
		System.out.println("Writing Chroma commit SHA " + sha);
		try(PrintStream out = new PrintStream(SHA_FILE)){
			out.println(sha);
		}
	}
	public static void extract(File zipFile, File dir) throws IOException{
		try(ZipInputStream zip = new ZipInputStream(new FileInputStream(zipFile))){
			ZipEntry entry;
			while((entry = zip.getNextEntry()) != null){
				File out = new File(dir, entry.getName());
				System.out.println(out);
				if(entry.isDirectory()){
					out.mkdirs();
				}else{
					FileCopier.copy(zip, new FileOutputStream(out));
				}
				zip.closeEntry();
			}
		}
	}
}
