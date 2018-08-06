package module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import bean.ATMButton;
import bean.ATMPanel;
import bean.Clocker;
import bean.Clocker.Timeout;
import toolkit.MyUtil;

import java.awt.Font;

public class TransferAccount extends ATMPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton returnCard, inlineTransfer, interBankTransfer,
			backMainWindow;
	private JLabel tips, tips1;
	private Clocker clocker;
	public static int flag;

	public TransferAccount() {
		super("resource/ATM.png");

		clocker = new Clocker();
		clocker.setBounds(592, 10, 90, 70);
		clocker.setTimeout(new Timeout() {

			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				MyUtil.panelLeap(TransferAccount.this, new Welcome(), null);
			}

		});
		add(clocker);
		clocker.startCountDown();

		tips = new JLabel(
				"<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>");
		tips.setFont(new Font("宋体", Font.BOLD, 22));
		tips.setBounds(692, 10, 208, 67);
		add(tips);

		tips1 = new JLabel("请选择转账类型");
		tips1.setHorizontalAlignment(SwingConstants.CENTER);
		tips1.setLocation(105, 177);
		tips1.setFont(new Font("宋体", Font.BOLD, 30));
		tips1.setSize(690, 45);
		add(tips1);

		returnCard = new ATMButton("退卡");
		returnCard.setBounds(0, 520, 190, 45);
		add(returnCard);
		returnCard.addActionListener(this);
	
		inlineTransfer = new ATMButton("行内转账");
		inlineTransfer.setBounds(710, 325, 190, 45);
		add(inlineTransfer);
		inlineTransfer.addActionListener(this);

		interBankTransfer = new ATMButton("跨行转账");
		interBankTransfer.setBounds(710, 424, 190, 45);
		add(interBankTransfer);
		interBankTransfer.addActionListener(this);

		backMainWindow = new ATMButton("回主菜单");
		backMainWindow.setBounds(710, 520, 190, 45);
		add(backMainWindow);
		backMainWindow.addActionListener(this);
		
		
		if(MyUtil.getCardInformation().getBelong()!=1){
			tips1.setText("由于您使用非中行账户登录，只能选择跨行转账!");
			inlineTransfer.setEnabled(false);
		}
	}


	public void actionPerformed(ActionEvent arg0) {
		Object action = arg0.getSource();
		if (action == backMainWindow) {
			
			MyUtil.panelLeap(this, new MainWindow(), clocker);

		} else if (action == inlineTransfer) {
			flag=1;
			MyUtil.panelLeap(this, new TransferAccount1(), clocker);
			
		} else if (action == interBankTransfer) {
			flag=2;
			MyUtil.panelLeap(this, new TransferAccount1(), clocker);

		} else if (action == returnCard) {
			
			MyUtil.panelLeap(this, new Welcome(), clocker);
		}

	}

}
