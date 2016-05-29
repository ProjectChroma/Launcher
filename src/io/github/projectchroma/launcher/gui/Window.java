package io.github.projectchroma.launcher.gui;

import javax.swing.JFrame;

import io.github.projectchroma.launcher.Launcher;

public class Window{
	private JFrame window;
	public Window(){
		window = new JFrame("Chroma Launcher");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(new ContentArea());
		window.setIconImage(Launcher.getLogo());
		window.setSize(800, 600);
	}
	public void show(){
		window.setVisible(true);
	}
	public void hide(){
		window.setVisible(false);
	}
}
