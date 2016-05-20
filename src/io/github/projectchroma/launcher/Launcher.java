package io.github.projectchroma.launcher;

import java.io.IOException;

import com.google.gson.Gson;

public class Launcher{
	public static final Gson GSON = new Gson();
	public static void main(String[] args) throws IOException{
		
	}
	public static boolean update() throws IOException{
		String online = WebInterface.getLatestVersion("master").sha, local = FileInterface.getDownloadedVersion();
		if(online.equals(local)){//No update
			return false;
		}else{
			WebInterface.download("Chroma.jar", FileInterface.JAR_FILE);
			FileInterface.writeSHA(online);
			return true;
		}
	}
}
