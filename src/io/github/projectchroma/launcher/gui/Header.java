package io.github.projectchroma.launcher.gui;

import java.awt.Color;

import javax.swing.BoxLayout;

import io.github.projectchroma.launcher.Launcher;
import io.github.projectchroma.launcher.gui.component.BaseComponent;
import io.github.projectchroma.launcher.gui.component.ImageComponent;
import io.github.projectchroma.launcher.gui.component.Spacer;

public class Header extends BaseComponent{
	private static final long serialVersionUID = 1L;
	public Header(){
		super(Color.white, Color.black);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		add(new ImageComponent(Launcher.getLogo(), 100));
		add(new Spacer(10));
		add(createText("Chroma Launcher", Launcher.getFont(48)));
	}
}
