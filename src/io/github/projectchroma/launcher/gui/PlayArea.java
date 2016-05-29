package io.github.projectchroma.launcher.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import io.github.projectchroma.launcher.Launcher;
import io.github.projectchroma.launcher.gui.component.BaseComponent;
import io.github.projectchroma.launcher.gui.component.Spacer;

public class PlayArea extends BaseComponent{
	private static final long serialVersionUID = 1L;
	public PlayArea(){
		super(new GridBagLayout(), Color.white, Color.black);
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = c.gridheight = 1;
		c.gridx = c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(5, 10, 0, 0);
		add(createText("Play", Launcher.getFont(36)), c);
		
		Font textFont = Launcher.getFont(24);
		c.gridy++;
		add(createButton("Launch Game", textFont, () -> {Launcher.run();}), c);
		
		c.gridy++;
		add(createButton("Open Data Folder", textFont, () -> {Launcher.openDataDir();}), c);
		
		c.weighty = 1;
		add(new Spacer(1, 1, false), c);
	}
}
