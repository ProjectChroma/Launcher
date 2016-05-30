package io.github.projectchroma.launcher;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

import io.github.projectchroma.launcher.gui.SettingsArea;
import io.github.projectchroma.launcher.gui.Window;

public class Launcher{
	public static final Gson GSON = new Gson();
	private static Font font;
	private static BufferedImage logo;
	public static void main(String[] args){
		try{
			font = Font.createFont(Font.TRUETYPE_FONT, Launcher.class.getResourceAsStream("/assets/fonts/mysteron.ttf"));
		}catch(IOException | FontFormatException ex){
			System.err.println("Error creating primary font");
			ex.printStackTrace();
			System.exit(-1);
		}
		
		try{
			logo = ImageIO.read(Launcher.class.getResourceAsStream("/assets/images/logo.png"));
		}catch(IOException ex){
			System.err.println("Error loading logo image");
			ex.printStackTrace();
			System.exit(-1);
		}
		
		FileInterface.init();
		
		Window window = new Window();
		window.show();
		
		try{
			String onlineVersion = WebInterface.getLatestChromaVersion("master").sha;
			System.out.println("Online version:\t" + onlineVersion);
			String localVersion = FileInterface.getDownloadedChromaVersion();
			System.out.println("Local version:\t" + localVersion);
			if(onlineVersion.equals(localVersion)) SettingsArea.instance().setChromaStatus(SettingsArea.UP_TO_DATE);
			else SettingsArea.instance().setChromaStatus(SettingsArea.UPDATE_AVAILABLE);
		}catch(IOException ex){
			SettingsArea.instance().setChromaStatus(SettingsArea.ERROR);
			System.err.println("Error finding update for Chroma");
			ex.printStackTrace();
		}
	}
	public static Font getFont(float size){
		return font.deriveFont(size);
	}
	public static BufferedImage getLogo(){
		return logo;
	}
	
	public static void update(){
		try{
			String sha = WebInterface.getLatestChromaVersion("master").sha;
			WebInterface.download("https://media.githubusercontent.com/media/ProjectChroma/Chroma/" + sha + "/Chroma.jar", FileInterface.JAR_FILE);
			WebInterface.download("https://media.githubusercontent.com/media/ProjectChroma/Launcher/" + sha + "/lib.zip", FileInterface.LIB_FILE);
			FileInterface.extract(FileInterface.LIB_FILE, FileInterface.LIB_DIR);
			FileInterface.writeSHA(WebInterface.getLatestChromaVersion("master").sha);
			SettingsArea.instance().setChromaStatus(SettingsArea.UP_TO_DATE);
			System.out.println("Update complete!");
		}catch(IOException ex){
			System.err.println("Error updating Chroma");
			ex.printStackTrace();
			SettingsArea.instance().setChromaStatus(SettingsArea.ERROR);
		}
	}
	public static void run(){
		try{
			String cmd = "java \"-Djava.library.path=" + FileInterface.LIB_DIR + "\" -jar " + FileInterface.JAR_FILE;
			System.out.println("Running game: " + cmd);
			Process p = Runtime.getRuntime().exec(cmd);
			new FileCopier(p.getInputStream(), System.out).start();
			new FileCopier(p.getErrorStream(), System.err).start();
		}catch(IOException ex){
			System.err.println("Error running Chroma");
			ex.printStackTrace();
		}
	}
	public static void openDataDir(){
		try{
			Desktop.getDesktop().open(FileInterface.DIR);
		}catch(IOException ex){
			System.err.println("Error opening data folder " + FileInterface.DIR);
			ex.printStackTrace();
		}
	}
}
