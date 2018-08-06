package module;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import toolkit.MyUtil;

import bean.ATMButton;
import bean.ATMPanel;
import bean.ATMTextField;
import bean.Clocker;
import bean.Clocker.Timeout;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePW_2 extends ATMPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton returnCard, ok;
	private Clocker clocker;
	private JLabel tips, label, label_1, hint;
	private ATMTextField passwordField;
	private String pw;
	
	public ChangePW_2(){
		super("resource/ATM.png");
		
		
		tips=new JLabel("<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>");
		tips.setFont(new Font("����", Font.BOLD, 22));
		tips.setBounds(692, 10, 208, 67);
		add(tips);
		
		
		label = new JLabel("<html>�����������¸�������</html>");
		label.setFont(new Font("����", Font.BOLD, 30));
		label.setBounds(290, 187, 320, 35);
		add(label);
		

		passwordField = new ATMTextField(120,45,true);
		passwordField.setEchoChar('*');
		passwordField.setTextFont(new Font("����", Font.PLAIN, 30));
		passwordField.setBounds(390, 250, 120, 45);
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		add(passwordField);
		passwordField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				char[] s = passwordField.getText().toCharArray();
				Pattern p = Pattern.compile("[0-9]");
				Matcher m = p.matcher(String.valueOf(e.getKeyChar()));
				boolean b = m.matches();
				if (s.length >= 6 || !b)
					e.consume();
				hint.setText("");
			}
		});
		
		
		label_1 = new JLabel("<html><p>������6λ����</p><p>��ܰ��ʾ��Ϊ�˼�ǿ�����ԣ���������������ʹ�����֤�š�����������</p><p>������Ϊ���˻��������п�������</p></html>");
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("����", Font.PLAIN, 18));
		label_1.setBounds(162, 411, 576, 70);
		add(label_1);
		
		
		hint = new JLabel();
		hint.setForeground(new Color(153, 0, 0));
		hint.setFont(new Font("����", Font.BOLD, 18));
		hint.setBounds(369, 310, 162, 30);
		add(hint);
		
		
		returnCard=new ATMButton("�˿�");
		returnCard.setBounds(0, 499, 190, 45);
		add(returnCard);
		returnCard.addActionListener(this);
		
		ok = new ATMButton("ȷ��");
		ok.addActionListener(this);
		ok.setBounds(710, 499, 190, 45);
		add(ok);
		
		
		clocker=new Clocker();
		clocker.setSize(90, 70);
		clocker.setLocation(592, 10);
		add(clocker);
		clocker.startCountDown();
		clocker.setTimeout(new Timeout(){
			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				MyUtil.panelLeap(ChangePW_2.this, new MainWindow(), null);
			}
		});
		
		setSize(900,600);
		setLayout(null);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==returnCard){
			
			MyUtil.panelLeap(this, new Welcome(), clocker);
			
		}else if(arg0.getSource()==ok){
			pw = passwordField.getText();
			if(pw.length()==6&&!MyUtil.getCardInformation().getPassword().equals(pw)){

				MyUtil.panelLeap(this, new ChangePW_3(pw), clocker);
				
			}
			else {
				hint.setText("����������6λ����");
				passwordField.setText("");
			}
		}		
	}
}













