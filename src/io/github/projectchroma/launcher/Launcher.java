package io.github.projectchroma.launcher;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

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
		
		Window window = new Window();
		window.show();
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
	public static Font getFont(float size){
		return font.deriveFont(size);
	}
	public static BufferedImage getLogo(){
		return logo;
	}
}
