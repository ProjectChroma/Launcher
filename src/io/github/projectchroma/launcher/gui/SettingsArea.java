package io.github.projectchroma.launcher.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JTextField;

import io.github.projectchroma.launcher.Launcher;
import io.github.projectchroma.launcher.gui.component.BaseComponent;
import io.github.projectchroma.launcher.gui.component.Spacer;

public class SettingsArea extends BaseComponent{
	private static final long serialVersionUID = 1L;
	public static final int UP_TO_DATE = 0, UPDATE_AVAILABLE = 1, ERROR = 2, UNKNOWN = 3;
	private static final String[] statusTexts = {"Up-to-date", "Update available", "Error", "Checking..."};
	private static final Color[] statusColors = {Color.green, Color.yellow, Color.red, Color.black};
	private static SettingsArea instance;
	private JTextField statusField;
	private JButton updateButton;
	private GridBagConstraints c = new GridBagConstraints();
	private SettingsArea(){
		super(new GridBagLayout(), Color.white, Color.black);
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(5, 10, 0, 0);
		add(createText("Settings", Launcher.getFont(36)), c);
		
		Font textFont = Launcher.getFont(24);
		c.gridy++;
		c.gridwidth = 1;
		add(createText("Chroma", textFont), c);
		
		c.gridx++;
		statusField = createText("", textFont);
		setChromaStatus(UNKNOWN);
		add(statusField, c);
		
		c.gridwidth = 2;
		c.weighty = 1;
		c.gridy+=2;//Save room for update button
		add(new Spacer(1, 1, false), c);//Push list items to top
		
		c.gridx = 0;
		c.gridy--;
		c.weighty = 0;
		updateButton = createButton("Update Chroma", textFont, () -> {Launcher.update();});
	}
	
	public void setChromaStatus(int status){
		statusField.setForeground(statusColors[status]);
		statusField.setText(statusTexts[status]);
		statusField.repaint();
		
		if(updateButton == null) return;
		if(status == UPDATE_AVAILABLE){
			add(updateButton, c);
		}else{
			remove(updateButton);			
		}
		revalidate();
	}
	
	public static SettingsArea instance(){
		if(instance == null) instance = new SettingsArea();
		return instance;
	}
}
