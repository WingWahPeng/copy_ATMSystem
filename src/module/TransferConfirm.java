package module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

import bean.ATMButton;
import bean.ATMPanel;
import bean.ATMTextField;
import bean.Clocker;
import bean.Clocker.Timeout;
import toolkit.MyUtil;

import java.awt.Color;
import java.awt.Font;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingConstants;

public class TransferConfirm extends ATMPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton returnCard, yesButton;
	private JLabel tips, tips1, tips2,tips3;
	private ATMTextField transferPassword;
	private Clocker clocker;

	public TransferConfirm() {
		super("resource/ATM.png");

		clocker = new Clocker();
		clocker.setBounds(592, 10, 90, 70);
		clocker.setTimeout(new Timeout() {

			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				MyUtil.panelLeap(TransferConfirm.this, new Welcome(), null);
			}

		});
		add(clocker);
		clocker.startCountDown();

		returnCard = new ATMButton("退卡");
		returnCard.setBounds(0, 520, 190, 45);
		add(returnCard);
		returnCard.addActionListener(new ActionListener() {
			// 自动调用
			public void actionPerformed(ActionEvent arg0) {
					MyUtil.panelLeap(TransferConfirm.this, new Welcome(),
							null);

			}
		});

		yesButton = new ATMButton("确认");
		yesButton.setBounds(710, 520, 190, 45);
		add(yesButton);
		yesButton.addActionListener(new ActionListener() {
			// 自动调用
			public void actionPerformed(ActionEvent arg0) {
				if(MyUtil.getCardInformation().getPassword().equals(transferPassword.getText())){		
				MyUtil.panelLeap(TransferConfirm.this,new TransferAccount(), clocker);
				}else{
					tips3.setText("密码错误!");
				}
			}
		});

		tips = new JLabel(
				"<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>");
		tips.setFont(new Font("宋体", Font.BOLD, 22));
		tips.setBounds(692, 10, 208, 67);
		add(tips);

		tips1 = new JLabel("请输入您的个人密码");
		tips1.setLocation(306, 194);
		tips1.setSize(288, 45);
		tips1.setFont(new Font("宋体", Font.BOLD, 30));

		add(tips1);
		tips2 = new JLabel("\u2606\u8BF7\u8F93\u51656\u4F4D\u5BC6\u7801");
		tips2.setFont(new Font("宋体", Font.PLAIN, 20));
		tips2.setLocation(375, 470);
		tips2.setSize(150, 45);
		add(tips2);

		
		tips3=new JLabel();
		tips3.setFont(new Font("宋体", Font.BOLD, 18));
		tips3.setForeground(new Color(139, 0, 0));
		tips3.setBounds(407, 314, 86, 35);
		add(tips3);
			
		transferPassword = new ATMTextField(120,45,true);
		transferPassword.setEchoChar('*');
		transferPassword.setHorizontalAlignment(SwingConstants.CENTER);
		transferPassword.setTextFont(new Font("宋体", Font.PLAIN, 30));
		transferPassword.setBounds(390,248,120,45);
		add(transferPassword);

		transferPassword.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {  
			    // TODO Auto-generated method stub  
				char[] s = transferPassword.getText().toCharArray();
				Pattern p = Pattern.compile("[0-9]");
			    Matcher m = p.matcher(String.valueOf(e.getKeyChar()));
			    boolean b = m.matches();
			    if(s.length >= 6 || !b) e.consume();  
			    tips3.setText("");
			}  
		});
	
	}

}
