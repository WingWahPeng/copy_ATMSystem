package module;
/**
 * ��ӭ����
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
	private JButton exit;//�˳�
	
	public Welcome() {
		super("resource/welcome.png");
		MyUtil.setCardInformation(null);
		MyUtil.setProblem(null);
		
		exit=new JButton(new ImageIcon("resource/exit.png"));//�˳�ͼ��
		exit.setContentAreaFilled(false);
		exit.setBorderPainted(false);
		exit.setPressedIcon(new ImageIcon("resource/exit_press.png"));//�����˳�ͼ��
		exit.setBounds(845, 10, 45, 45);
		exit.setToolTipText("�ر�");	//��ʾ
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Entrance.getDBInstance().closeDB();
				System.exit(0);
			}
		});
		add(exit);
		
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				MyUtil.panelLeap(Welcome.this, new Login(), null);	//������¼���
			}
		});
	}

//���Finalize����,���Ը�������������Ӧ��ִ�еĲ���
	protected void finalize(){
		exit=null;
	}
}
