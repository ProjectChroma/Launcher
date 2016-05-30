package io.github.projectchroma.launcher.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Log{
	public static final int DEBUG = 0, INFO = 1, WARNING = 2, ERROR = 3, FATAL_ERROR = 4;
	private static final String[] names = {"DEBUG", "INFO", "WARN", "ERR", "FATAL"};
	private static final SimpleDateFormat format = new SimpleDateFormat("kk:mm:ss.SSS");
	
	static{
		int len = 0;
		for(String name : names) len = Math.max(len, name.length());
		for(int i=0; i<names.length; i++){
			int numSpaces = len - names[i].length();
			if(numSpaces == 0) continue;
			char[] spaces = new char[numSpaces];
			Arrays.fill(spaces, ' ');
			names[i] = names[i].concat(new String(spaces));
		}
	}
	
	private File file, latestFile;
	private PrintStream[] outs;
	public Log() throws FileNotFoundException{
		int i = 0;
		do{
			file = new File(FileInterface.LOG_DIR, "log" + i++ + ".txt");
		}while(file.exists());
		latestFile = new File(FileInterface.LOG_DIR, "latest.txt");
		outs = new PrintStream[]{System.out, new PrintStream(file), new PrintStream(latestFile)};
		write("Log initialized in " + file, DEBUG);
	}
	public void write(String message, int level){
		String date = format.format(new Date(System.currentTimeMillis()));
		message = date + " [" + names[level] + "] " + message;
		for(PrintStream out : outs){
			out.println(message);
			out.flush();
		}
	}
	public File getFile(){
		return file;
	}
}
