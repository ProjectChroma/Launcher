package io.github.projectchroma.launcher;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

import io.github.projectchroma.launcher.gui.SettingsArea;
import io.github.projectchroma.launcher.gui.Window;
import io.github.projectchroma.launcher.io.FileCopier;
import io.github.projectchroma.launcher.io.FileInterface;
import io.github.projectchroma.launcher.io.Log;
import io.github.projectchroma.launcher.io.WebInterface;

public class Launcher{
	public static final Gson GSON = new Gson();
	private static Log log;
	private static Font font;
	private static BufferedImage logo;
	public static void main(String[] args){
		FileInterface.init();
		try{
			log = new Log();
		}catch(FileNotFoundException ex){
			System.err.println("Error creating logger");
			ex.printStackTrace();
		}
		try{
			font = Font.createFont(Font.TRUETYPE_FONT, Launcher.class.getResourceAsStream("/assets/fonts/mysteron.ttf"));
		}catch(IOException | FontFormatException ex){
			log.write("Error creating primary font", Log.FATAL_ERROR);
			ex.printStackTrace();
			System.exit(-1);
		}
		try{
			logo = ImageIO.read(Launcher.class.getResourceAsStream("/assets/images/logo.png"));
		}catch(IOException ex){
			log.write("Error loading logo image", Log.FATAL_ERROR);
			ex.printStackTrace();
			System.exit(-1);
		}
		
		Window window = new Window();
		window.show();
		
		try{
			String onlineVersion = WebInterface.getLatestChromaVersion("master").sha;
			log.write("Online version: " + onlineVersion, Log.DEBUG);
			String localVersion = FileInterface.getDownloadedChromaVersion();
			log.write("Local version:  " + localVersion, Log.DEBUG);
			if(onlineVersion.equals(localVersion)) SettingsArea.instance().setChromaStatus(SettingsArea.UP_TO_DATE);
			else SettingsArea.instance().setChromaStatus(SettingsArea.UPDATE_AVAILABLE);
		}catch(IOException ex){
			SettingsArea.instance().setChromaStatus(SettingsArea.ERROR);
			log.write("Error finding update for Chroma", Log.ERROR);
			ex.printStackTrace();
		}
	}
	public static Font getFont(float size){
		return font.deriveFont(size);
	}
	public static BufferedImage getLogo(){
		return logo;
	}
	public static Log log(){
		return log;
	}
	
	public static void update(){
		try{
			String sha = WebInterface.getLatestChromaVersion("master").sha;
			WebInterface.download("https://media.githubusercontent.com/media/ProjectChroma/Chroma/master/Chroma.jar", FileInterface.JAR_FILE);
			WebInterface.download("https://media.githubusercontent.com/media/ProjectChroma/Launcher/master/lib.zip", FileInterface.LIB_FILE);
			FileInterface.extract(FileInterface.LIB_FILE, FileInterface.LIB_DIR);
			FileInterface.writeSHA(sha);
			SettingsArea.instance().setChromaStatus(SettingsArea.UP_TO_DATE);
			log.write("Update complete!", Log.INFO);
		}catch(IOException ex){
			log.write("Error updating Chroma", Log.ERROR);
			ex.printStackTrace();
			SettingsArea.instance().setChromaStatus(SettingsArea.ERROR);
		}
	}
	public static void run(){
		try{
			log.write("Running game", Log.INFO);
			String cmd = "java \"-Djava.library.path=" + FileInterface.LIB_DIR + "\" -jar " + FileInterface.JAR_FILE;
			log.write(cmd, Log.DEBUG);
			Process p = Runtime.getRuntime().exec(cmd);
			new FileCopier(p.getInputStream(), System.out).start();
			new FileCopier(p.getErrorStream(), System.err).start();
		}catch(IOException ex){
			log.write("Error running Chroma", Log.ERROR);
			ex.printStackTrace();
		}
	}
	public static void openDataDir(){
		try{
			Desktop.getDesktop().open(FileInterface.CHROMA_DIR);
		}catch(IOException ex){
			log.write("Error opening data folder " + FileInterface.CHROMA_DIR, Log.ERROR);
			ex.printStackTrace();
		}
	}
}
