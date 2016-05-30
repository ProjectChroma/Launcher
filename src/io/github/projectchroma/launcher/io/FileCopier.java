package io.github.projectchroma.launcher.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.github.projectchroma.launcher.Launcher;

public class FileCopier extends Thread{
	private static final int BUFFER_SIZE = 1024;
	private static int index = 0;
	
	private InputStream input;
	private OutputStream output;
	private int size;
	public FileCopier(InputStream in, OutputStream out){this(in, out, BUFFER_SIZE);}
	public FileCopier(InputStream in, OutputStream out, int bufferSize){
		super("CopyThread-" + index++);
		input = in;
		output = out;
		this.size = bufferSize;
	}
	@Override
	public void run(){
		try(InputStream in = input; OutputStream out = output){
			copy(in, out, size);
		}catch(IOException ex){
			Launcher.log().write("Error copying " + input + " to " + output, Log.ERROR);
			ex.printStackTrace();
		}
	}
	public static void copy(InputStream in, OutputStream out) throws IOException{copy(in, out, BUFFER_SIZE);}
	public static void copy(InputStream in, OutputStream out, int size) throws IOException{
		byte[] buffer = new byte[size];
		int len;
		while((len=in.read(buffer)) >= 0){
		    out.write(buffer, 0, len);
		}
	}
}
