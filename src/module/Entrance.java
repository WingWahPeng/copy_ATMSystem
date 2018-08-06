package module;


import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import bean.DatabaseBean;



public class Entrance extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFrame jframe;
	private Welcome w;
	private static DatabaseBean db;
	static Point origin = new Point();
	
	public static Entrance newInstance(){
		db=DatabaseBean.newInstance();
		return new Entrance();
	}
	
	public static DatabaseBean getDBInstance(){
		return db;
	}
	
	public static JFrame getEntranceInstance(){
		return jframe;
	}
	
	
	public Entrance(){
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				origin.x = e.getX();
				origin.y = e.getY();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				// 当鼠标拖动时获取窗口当前位置
				Point p = Entrance.this.getLocation();
				// 设置窗口的位置
				// 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
				Entrance.this.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()- origin.y);
			}
		});
		
		
		w=new Welcome();
		add(w);

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		jframe=newInstance();
		jframe.setUndecorated(true);
		jframe.pack();
		jframe.setSize(900, 600);
		jframe.setLocationRelativeTo(null);
		jframe.setTitle("中国银行ATM系统");
		jframe.setVisible(true);
		jframe.setIconImage(new ImageIcon("resource/mark.png").getImage());
		
	}

}
