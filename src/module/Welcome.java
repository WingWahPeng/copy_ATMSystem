package module;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.ImageIcon;
import javax.swing.JButton;

import bean.ATMPanel;
import toolkit.MyUtil;


public class Welcome extends ATMPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1448951816003042936L;
	private JButton exit;
	
	public Welcome() {
		super("resource/welcome.png");
		MyUtil.setCardInformation(null);
		MyUtil.setProblem(null);
		
		exit=new JButton(new ImageIcon("resource/exit.png"));
		exit.setContentAreaFilled(false);
		exit.setBorderPainted(false);
		exit.setPressedIcon(new ImageIcon("resource/exit_press.png"));
		exit.setBounds(845, 10, 45, 45);
		exit.setToolTipText("¹Ø±Õ");
		exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Entrance.getDBInstance().closeDB();
				System.exit(0);
			}
			
		});
		add(exit);
		
		
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){

				MyUtil.panelLeap(Welcome.this, new Login(), null);

			}
		});
	}
	protected void finalize(){
		exit=null;
	}
}
