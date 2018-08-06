package bean;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

/**
 * Custom JButton
 * 
 * @version 0.1.0
 * @author ruislan
 *         <a href="mailto:z17520@126.com" mce_href="mailto:z17520@126.com"></a>
 */
public class ATMButton extends JButton {
	private static final long serialVersionUID = 39082560987930759L;
	public static final Color BUTTON_COLOR1 = new Color(255, 255, 255);
	public static final Color BUTTON_COLOR2 = new Color(174, 172, 172);
	public static final Color BUTTON_FOREGROUND_COLOR = Color.BLACK;


	public ATMButton(String name) {
		setPreferredSize(new Dimension(180,45));
		setFont(new Font("ËÎÌו", Font.PLAIN, 20));
		setBorderPainted(false);
		setForeground(BUTTON_FOREGROUND_COLOR);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setText(name);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		int h = getHeight();
		int w = getWidth();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint p1;
		GradientPaint p2;
		
		if (getModel().isPressed()) {
			p1 = new GradientPaint(0, 0, new Color(204, 0, 51), 0, h - 1, new Color(100, 100, 100));
			p2 = new GradientPaint(0, 1, new Color(204, 0, 51, 50), 0, h - 3, new Color(255, 255, 255, 100));
			setForeground(Color.white);
		} else{
			p1 = new GradientPaint(0, 0, new Color(204, 0, 51), 0, h - 1, new Color(204, 0, 51));
			p2 = new GradientPaint(0, 1, new Color(204, 0, 51, 0), 0, h - 3, new Color(0, 0, 0, 50));
			setForeground(Color.BLACK);
		}
		GradientPaint gp = new GradientPaint(0.0F, 0.0F, BUTTON_COLOR1, 0.0F, h, BUTTON_COLOR2, true);
		g2d.setPaint(gp);
			RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1, h - 1, 50, 50);
			Shape clip = g2d.getClip();
			g2d.clip(r2d);
			g2d.fillRect(0, 0, w, h);
			g2d.setClip(clip);
			g2d.setPaint(p1);
			g2d.drawRoundRect(0, 0, w - 1, h - 1, 50, 50);
			g2d.setPaint(p2);
			g2d.drawRoundRect(1, 1, w - 3, h - 3, 48, 48);

		g2d.dispose();
		super.paintComponent(g);
	}
}
