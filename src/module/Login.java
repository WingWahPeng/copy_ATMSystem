package module;
/**
 * ��¼���
 */
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import bean.ATMButton;
import bean.ATMPanel;
import bean.ATMTextField;
import bean.ATMWait;
import bean.Clocker;
import bean.Clocker.Timeout;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import toolkit.MyUtil;

public class Login extends ATMPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JLabel accountLabel, passwordLabel, tipsLabel, tips1;
	private ATMTextField accountText;
	private ATMTextField passwordText;
	private ATMButton yesButton, exitButton;
	private Clocker clocker;//��ʱ��

	public Login() {
		super("resource/safetyTips.png");		//��ȫ��ʾ
	
		accountLabel = new JLabel("\u5361\u53F7:");//�˺�
		accountLabel.setFont(new Font("����", Font.BOLD, 30));
		accountLabel.setBounds(208, 105, 95, 70);
		add(accountLabel);

		passwordLabel = new JLabel("\u5BC6\u7801:");//����
		passwordLabel.setFont(new Font("����", Font.BOLD, 30));
		passwordLabel.setBounds(208, 185, 95, 70);
		add(passwordLabel);
	
		accountText = new ATMTextField(275,45);
		accountText.setTextFont(new Font("����", Font.PLAIN, 30));
		accountText.setBounds(323, 120, 275, 45);
//��¼����������ȷ���˺�	
		if(MyUtil.getCardInformation()!=null)accountText.setText(MyUtil.getCardInformation().getCardID());		
		add(accountText);

		//�����˺������ֻ������0~9�����֣����ȴ��ڻ����18
		accountText.addKeyListener(new KeyAdapter() {	//��Ӽ����¼���������������������ӿ�
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				String s = accountText.getText();
				Pattern p = Pattern.compile("[0-9]");	//������ʽ�����ã�ģ��������ã���
				Matcher m = p.matcher(String.valueOf(e.getKeyChar()));//��ȡ���������¼�����ĸ
				boolean b = m.matches();
				if (s.length() >= 18 || !b)
					e.consume();
				tips1.setText("");
			}
		});
		
		passwordText = new ATMTextField(275,45,true);
		passwordText.setTextFont(new Font("����", Font.PLAIN, 30));
		passwordText.setBounds(323, 190, 275, 45);
		passwordText.setEchoChar('*');	//������ʾ����,����ַ�
		add(passwordText);
	//������������ļ����¼�
		passwordText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				char[] s = passwordText.getText().toCharArray();
				Pattern p = Pattern.compile("[0-9]");
				Matcher m = p.matcher(String.valueOf(e.getKeyChar()));
				boolean b = m.matches();
				if (s.length >= 6 || !b)
					e.consume();
				tips1.setText("");
			}
		});
	
		yesButton = new ATMButton("ȷ��");
		yesButton.addActionListener(this);
		yesButton.setBounds(0, 520, 190, 45);
		add(yesButton);

		exitButton = new ATMButton("�˿�");
		exitButton.addActionListener(this);
		exitButton.setBounds(710, 520, 190, 45);
		add(exitButton);

		tipsLabel = new JLabel(
				"<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>\r\n\r\n");
		tipsLabel.setFont(new Font("����", Font.BOLD, 22));
		tipsLabel.setBounds(692, 10, 208, 67);
		add(tipsLabel);

		clocker = new Clocker();
		clocker.setBounds(592, 10, 90, 70);
		clocker.startCountDown();
		//���ü�ʱ�����¼���Ӧ
		clocker.setTimeout(new Timeout() {
			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				MyUtil.panelLeap(Login.this, new Welcome(), null);
			}
		});
		add(clocker);

		tips1 = new JLabel();//������ʾ
		if(MyUtil.getProblem()==null){
			tips1.setText("");
		}else{
			tips1.setText(MyUtil.getProblem());
		}
		tips1.setForeground(new Color(128, 0, 0));
		tips1.setHorizontalAlignment(SwingConstants.CENTER);
		tips1.setFont(new Font("����", Font.BOLD, 18));
		tips1.setBounds(298, 238, 304, 38);
		add(tips1);
		
	}

//�¼�����
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object action = e.getSource();
		if (action == yesButton) {	
			String text = accountText.getText();
			char[] text1 = passwordText.getText().toCharArray();		
	//�������˺�������Ϊ�յ���Ӧ���		
			if(text.equals("")||String.valueOf(text1).equals("")){
				tips1.setText("�˺Ż����벻��Ϊ��");
			}
			else
			{
				String sql = "select * from BankCard where cardID='" + text + "'";
				MyUtil.panelLeap(this, new ATMWait(ATMWait.QUERY,sql,String.valueOf(text1)), clocker);
			}
			
		} else {		
			MyUtil.panelLeap(this, new Welcome(), clocker);
			
		}
	}
}
