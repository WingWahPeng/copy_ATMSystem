package module;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

import bean.ATMButton;
import bean.ATMPanel;
import bean.ATMTextField;
import bean.ATMWait;
import bean.Clocker;
import bean.Clocker.Timeout;
import toolkit.MyUtil;

import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import java.awt.Color;
import java.math.BigDecimal;

public class TransferAccount2 extends ATMPanel {

	/**
	 * 转账二次确认界面
	 */
	private static final long serialVersionUID = 1L;

	private JButton cancel, yesButton;
	private JLabel tips;
	private Clocker clocker;
	private ATMTextField moneyText;
	private JLabel tips1;
	private JLabel label_1;
	private float chance=0.00f;
	
	public TransferAccount2() {
		super("resource/ATM.png");
			
		clocker = new Clocker();
		clocker.setBounds(592, 10, 90, 70);
		clocker.setTimeout(new Timeout() {

			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				MyUtil.panelLeap(TransferAccount2.this, new Welcome(), null);
			}

		});
		add(clocker);
		clocker.startCountDown();

		cancel = new ATMButton("取消");
		cancel.setBounds(0, 520, 190, 45);
		add(cancel);
		cancel.addActionListener(new ActionListener() {
			// 自动调用
			public void actionPerformed(ActionEvent arg0) {

				MyUtil.panelLeap(TransferAccount2.this, new TransferAccount(),
						clocker);

			}
		});

		yesButton = new ATMButton("确认");
		yesButton.setBounds(710, 520, 190, 45);
		add(yesButton);
		yesButton.addActionListener(new ActionListener() {
			// 自动调用
			public void actionPerformed(ActionEvent arg0) {

				float transferMoney = Float.valueOf(moneyText.getText());
				float balance1 = Float.valueOf(TransferAccount1.cInfo1
						.getBalance());
				float money = Float.valueOf(TransferAccount1.cInfo1
						.getTransferMoney());    
				float balance2 = Float.valueOf(MyUtil.getCardInformation()
						.getBalance());
				// 当天可转账的额度
				float money1 = money - transferMoney;
				
				//跨行转账
				if(TransferAccount.flag == 2)
					chance = (float) (transferMoney * 0.01);
				
				if (transferMoney > 10000.00) {
					
					tips1.setText("输入金额有误，请重新输入！");

				} else if (Float.valueOf(TransferAccount1.cInfo1.getTransferMoney())<=0) {

					tips1.setText("您输入的账号已超过当日最大转账金额！");
					
				} else if(Float.valueOf(MyUtil.getCardInformation().getTransferMoney())<=0||transferMoney+chance>Float.valueOf(MyUtil.getCardInformation().getTransferMoney())){
					
					tips1.setText("您的账号已超过当日最大转账金额！");
					
				}else if(transferMoney+chance>balance2){
					
					tips1.setText("余额不足！");
					
				}else{
					//满足转账条件
					/* 转入 */float newBalance1 = transferMoney + balance1;
					/* 转出 */float newBalance2 = balance2 - transferMoney-chance;
					// 可交易金额待加入
					String sql1 = "update BankCard set balance=" + newBalance1
							+ ",transferMoney=" + money1 + "where cardID='"
							+ TransferAccount1.cInfo1.getCardID() + "';";
					String sql2 = "update BankCard set balance=" + newBalance2
							+ ",transferMoney=" + money1 + "where cardID='"
							+ MyUtil.getCardInformation().getCardID() + "';";
					String sql = sql1 + sql2;
					MyUtil.panelLeap(TransferAccount2.this, new ATMWait(
							ATMWait.UPDATE, sql, transferMoney + "",
							"transfer", chance + ""), clocker);
					
				}
			}

		});// 是否要二次确认信息？才继续进行操作。

		// 需要可以输入小数，有待修改
		moneyText = new ATMTextField(125,45);
		moneyText.setHorizontalAlignment(SwingConstants.CENTER);
		moneyText.setTextFont(new Font("宋体", Font.PLAIN, 30));
		moneyText.setBounds(385, 250, 125, 45);
		add(moneyText);
		moneyText.setDocument(new NumOnlyDocument());
		moneyText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				tips1.setText("");
			}
		});

		JLabel label = new JLabel("\u8BF7\u8F93\u5165\u8F6C\u8D26\u91D1\u989D");
		label.setFont(new Font("宋体", Font.BOLD, 30));
		label.setBounds(338, 195, 224, 45);
		add(label);

		tips1 = new JLabel();
		tips1.setHorizontalAlignment(SwingConstants.CENTER);
		tips1.setForeground(new Color(128, 0, 0));
		tips1.setFont(new Font("宋体", Font.BOLD, 20));
		tips1.setBounds(299, 340, 302, 38);
		add(tips1);

		label_1 = new JLabel(
				"\u6E29\u99A8\u63D0\u793A\uFF1A\u6700\u9AD8\u53EF\u8F93\u516510000\u5143");
		label_1.setForeground(new Color(128, 0, 0));
		label_1.setFont(new Font("宋体", Font.BOLD, 20));
		label_1.setBounds(305, 470, 291, 28);
		add(label_1);

		tips = new JLabel(
				"<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>");
		tips.setFont(new Font("宋体", Font.BOLD, 22));
		tips.setBounds(692, 10, 208, 67);
		add(tips);
	}
	class NumOnlyDocument extends PlainDocument {  
        private static final long serialVersionUID = 1001689415662878505L;  
          
        int maxLen;  //最长字符长度  
        int decimalLen; //小数位数  
          
        public NumOnlyDocument() {  
            this(2,7);  
        }  
          
        public NumOnlyDocument(int newDecimalLen, int newMaxLen) {  
            super();  
            decimalLen = newDecimalLen;  
            maxLen = newMaxLen;  
        }  
          
        public void insertString(int offset, String inStr, AttributeSet attrSet)   
            throws BadLocationException {  
            // 获得输入框有效值  
            String numStr = getText(0, offset) + inStr + getText(offset, getLength() - offset);  
              
            // 校验字符长度限制  
            if (getLength() + inStr.length() > maxLen) {  
                return;  
            }  
            // 校验是否是有效数字  
            try {  
                new BigDecimal(numStr);  
            } catch (NumberFormatException e1) {  
                return;  
            }  
            // 校验小数位数限制  
            int indexNum = numStr.indexOf(".");  
            if (indexNum > 0) {  
                int len = numStr.substring(indexNum + 1).length();  
                if (len > decimalLen) {  
                    return;  
                }  
            }  
            super.insertString(offset, inStr, attrSet);  
        } 
    }
}
