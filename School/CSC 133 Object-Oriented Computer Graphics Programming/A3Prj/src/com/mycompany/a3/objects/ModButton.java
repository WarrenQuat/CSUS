package com.mycompany.a3.objects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.plaf.Border;

/*
 * ModButton extends Button class to initialize buttons
 * with the same colors,padding,etc
 */
public class ModButton extends Button
{
	public ModButton(String title)
	{
		super(title);
		getAllStyles().setBorder(Border.createLineBorder(7,ColorUtil.BLACK));
		getAllStyles().setBgTransparency(255);
		getAllStyles().setBgColor(0X708090);
		getAllStyles().setFgColor(ColorUtil.BLACK);
		getAllStyles().setPadding(2, 2,2,2);
		getDisabledStyle().setBgTransparency(100);
		getDisabledStyle().setBgColor(ColorUtil.BLACK);
	}
	
	/*creates a button without a title
	 * as a title will be replaced by the command call
	 */
	public ModButton()
	{
		getAllStyles().setBorder(Border.createLineBorder(7,ColorUtil.BLACK));
		getAllStyles().setBgTransparency(255);
		getAllStyles().setBgColor(0X708090);
		getAllStyles().setFgColor(ColorUtil.BLACK);
		getAllStyles().setPadding(2, 2,2,2);
		getDisabledStyle().setBgTransparency(100);
		getDisabledStyle().setBgColor(ColorUtil.BLACK);
	}
}
