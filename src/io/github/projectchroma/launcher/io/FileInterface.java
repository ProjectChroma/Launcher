package io.github.projectchroma.launcher.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.github.projectchroma.launcher.Launcher;

public class FileInterface{
	/**Directory for all files related to the game download*/
	public static final File CHROMA_DIR = new File("chroma");
	/**File for storing the commit SHA last downloaded*/
	public static final File SHA_FILE = new File(CHROMA_DIR, "sha.dat");
	/**File for storing the game JAR file*/
	public static final File JAR_FILE = new File(CHROMA_DIR, "Chroma.jar");
	/**File where the compressed libraries and natives are downloaded to*/
	public static final File LIB_FILE = new File(CHROMA_DIR, "lib.zip");
	/**File for storing native files required for the game*/
	public static final File LIB_DIR = new File(CHROMA_DIR, "lib");
	/**File for accumulating launcher logs*/
	public static final File LOG_DIR = new File("logs");
	public static void init(){
		CHROMA_DIR.mkdirs();
		LIB_DIR.mkdir();
		LOG_DIR.mkdirs();
	}
	public static String getDownloadedChromaVersion(){
		try(Scanner in = new Scanner(SHA_FILE)){
			return in.nextLine();
		}catch(FileNotFoundException ex){return null;}
	}
	public static void writeSHA(String sha) throws FileNotFoundException{
		Launcher.log().write("Writing Chroma commit SHA " + sha, Log.DEBUG);
		try(PrintStream out = new PrintStream(SHA_FILE)){
			out.println(sha);
		}
	}
	public static void extract(File zipFile, File dir) throws IOException{
		try(ZipInputStream zip = new ZipInputStream(new FileInputStream(zipFile))){
			ZipEntry entry;
			while((entry = zip.getNextEntry()) != null){
				File out = new File(dir, entry.getName());
				if(entry.isDirectory()){
					out.mkdirs();
				}else{
					FileOutputStream output = new FileOutputStream(out);
					FileCopier.copy(zip, output);
					output.close();
				}
				zip.closeEntry();
			}
		}
	}
}
