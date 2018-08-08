package module;
/**
 * 欢迎界面
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.ImageIcon;
import javax.swing.JButton;

import bean.ATMPanel;
import toolkit.MyUtil;


public class Welcome extends ATMPanel {
	
	private static final long serialVersionUID = -1448951816003042936L;
	private JButton exit;//退出
	
	public Welcome() {
		super("resource/welcome.png");
		MyUtil.setCardInformation(null);
		MyUtil.setProblem(null);
		
		exit=new JButton(new ImageIcon("resource/exit.png"));//退出图标
		exit.setContentAreaFilled(false);
		exit.setBorderPainted(false);
		exit.setPressedIcon(new ImageIcon("resource/exit_press.png"));//按下退出图标
		exit.setBounds(845, 10, 45, 45);
		exit.setToolTipText("关闭");	//提示
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Entrance.getDBInstance().closeDB();
				System.exit(0);
			}
		});
		add(exit);
		
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				MyUtil.panelLeap(Welcome.this, new Login(), null);	//跳到登录面板
			}
		});
	}

//类的Finalize方法,可以告诉垃圾回收器应该执行的操作
	protected void finalize(){
		exit=null;
	}
}
