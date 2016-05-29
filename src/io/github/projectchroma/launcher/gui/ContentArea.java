package io.github.projectchroma.launcher.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import io.github.projectchroma.launcher.gui.component.BaseComponent;
import io.github.projectchroma.launcher.gui.component.Spacer;

public class ContentArea extends BaseComponent{
	private static final long serialVersionUID = 1L;
	public ContentArea(){
		super(new GridBagLayout(), Color.white, Color.black);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		add(new Header(), c);
		
		c.gridy++;
		c.gridwidth = 1;
		c.weighty = 1;
		
		add(new Spacer(1, 1, false), c);
		
		c.gridy++;
		c.weighty = 2;
		c.anchor = GridBagConstraints.NORTHWEST;
		add(new PlayArea(), c);
		
		c.gridx++;
		c.anchor = GridBagConstraints.NORTHEAST;
		add(new UpdateArea(), c);
	}
}
