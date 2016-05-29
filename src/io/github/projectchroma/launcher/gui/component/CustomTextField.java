package io.github.projectchroma.launcher.gui.component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class CustomTextField extends JTextField{
	private static final long serialVersionUID = 1L;
	public CustomTextField(String text, Font font, boolean editable, Color bg, Color fg){
		super(text);
		setFont(font);
		setEditable(editable);
		setFocusable(editable);
		setBackground(bg);
		setForeground(fg);
		setBorder(editable ? new LineBorder(Color.blue, 1, true) : null);
	}
}
