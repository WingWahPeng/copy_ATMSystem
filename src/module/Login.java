package module;

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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel accountLabel, passwordLabel, tipsLabel, tips1;
	private ATMTextField accountText;
	private ATMTextField passwordText;
	private ATMButton yesButton, exitButton;
	private Clocker clocker;

	public Login() {
		super("resource/safetyTips.png");

		
		accountLabel = new JLabel("\u5361\u53F7:");
		accountLabel.setFont(new Font("宋体", Font.BOLD, 30));
		accountLabel.setBounds(208, 105, 95, 70);
		add(accountLabel);

		passwordLabel = new JLabel("\u5BC6\u7801:");
		passwordLabel.setFont(new Font("宋体", Font.BOLD, 30));
		passwordLabel.setBounds(208, 185, 95, 70);
		add(passwordLabel);

		
		accountText = new ATMTextField(275,45);
		accountText.setTextFont(new Font("宋体", Font.PLAIN, 30));
		accountText.setBounds(323, 120, 275, 45);
		if(MyUtil.getCardInformation()!=null)accountText.setText(MyUtil.getCardInformation().getCardID());
		add(accountText);
		accountText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				String s = accountText.getText();
				Pattern p = Pattern.compile("[0-9]");
				Matcher m = p.matcher(String.valueOf(e.getKeyChar()));
				boolean b = m.matches();
				if (s.length() >= 18 || !b)
					e.consume();
				tips1.setText("");
			}
		});
		

		passwordText = new ATMTextField(275,45,true);
		passwordText.setTextFont(new Font("宋体", Font.PLAIN, 30));
		passwordText.setBounds(323, 190, 275, 45);
		passwordText.setEchoChar('*');
		add(passwordText);
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

		
		yesButton = new ATMButton("确认");
		yesButton.addActionListener(this);
		yesButton.setBounds(0, 520, 190, 45);
		add(yesButton);

		exitButton = new ATMButton("退卡");
		exitButton.addActionListener(this);
		exitButton.setBounds(710, 520, 190, 45);
		add(exitButton);

		tipsLabel = new JLabel(
				"<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>\r\n\r\n");
		tipsLabel.setFont(new Font("宋体", Font.BOLD, 22));
		tipsLabel.setBounds(692, 10, 208, 67);
		add(tipsLabel);

		clocker = new Clocker();
		clocker.setBounds(592, 10, 90, 70);
		clocker.startCountDown();
		clocker.setTimeout(new Timeout() {

			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				MyUtil.panelLeap(Login.this, new Welcome(), null);
			}

		});
		add(clocker);

		tips1 = new JLabel();
		if(MyUtil.getProblem()==null){
			tips1.setText("");
		}else{
			tips1.setText(MyUtil.getProblem());
		}
		tips1.setForeground(new Color(128, 0, 0));
		tips1.setHorizontalAlignment(SwingConstants.CENTER);
		tips1.setFont(new Font("宋体", Font.BOLD, 18));
		tips1.setBounds(298, 238, 304, 38);
		add(tips1);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object action = e.getSource();
		if (action == yesButton) {
			
			String text = accountText.getText();
			char[] text1 = passwordText.getText().toCharArray();
			
			if(text.equals("")){
				return;
			}
			if(String.valueOf(text1).equals("")){
				return;
			}
			
			String sql = "select * from BankCard where cardID='" + text + "'";
			MyUtil.panelLeap(this, new ATMWait(ATMWait.QUERY,sql,String.valueOf(text1)), clocker);
			
		} else {
			
			MyUtil.panelLeap(this, new Welcome(), clocker);
			
		}
	}
}
