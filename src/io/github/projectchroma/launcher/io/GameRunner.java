package io.github.projectchroma.launcher.io;

import java.io.IOException;
import java.io.InputStreamReader;

import io.github.projectchroma.launcher.Launcher;

public class GameRunner extends Thread{
	private Process p;
	private InputStreamReader in, err;
	public GameRunner(Process p){
		this.p = p;
		in = new InputStreamReader(p.getInputStream());
		err = new InputStreamReader(p.getErrorStream());
	}
	@Override
	public void run(){
		char[] buffer = new char[1024];
		int len;
		try{
			while(p.isAlive()){
				if(in.ready()){
					len = in.read(buffer);
					Launcher.log().write("[Game|OUT] ".concat(new String(buffer, 0, len).trim()), Log.INFO);
				}
				if(err.ready()){
					len = err.read(buffer);
					Launcher.log().write("[Game|ERR] ".concat(new String(buffer, 0, len).trim()), Log.ERROR);
				}
			}
			Launcher.log().write("Game ended", Log.INFO);
		}catch(IOException ex){
			Launcher.log().write("Error producing output for game", ex, Log.ERROR);
		}finally{
			try{
				in.close();
			}catch(IOException ex){Launcher.log().write("Error closing STDIN for game", ex, Log.ERROR);}
			try{
				err.close();
			}catch(IOException ex){Launcher.log().write("Error closing STDERR for game", ex, Log.ERROR);}
		}
	}
}
