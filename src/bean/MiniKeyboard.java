package bean;

import javax.swing.*;

import java.awt.Point;

import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.*;

public class MiniKeyboard extends JDialog implements ActionListener{

	/**
	 * 虚拟键盘，待加入主程序
	 */
	private static final long serialVersionUID = -5063491670931749081L;
    private JButton num0,num1,num2,num3,num4,num5,num6,num7,num8,num9,ensure,cancel;
    private ATMTextField jtf;
    static Point origin = new Point();
    private JLabel title;
    private TextFieldListener tfl;
    
	public MiniKeyboard(JFrame jf){
		super(jf,"ATM小键盘");
		this.setUndecorated(true);
		
		addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent we) {  
                setVisible(false);  
                dispose();  
            }  
        });
		setSize(370,315);
		this.setLocationRelativeTo(null);
		
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				origin.x = e.getX();
				origin.y = e.getY();
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = MiniKeyboard.this.getLocation();
				MiniKeyboard.this.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()- origin.y);
			}
		});
		
		title=new JLabel("ATM\u5C0F\u952E\u76D8");
		title.setFont(new Font("宋体", Font.BOLD, 24));
		title.setBounds(0, 0, 370, 35);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().setLayout(null);
		getContentPane().add(title);
		
		
		num0= new JButton("0");
		num0.setBounds(10, 45, 80, 80);
		num0.setFont(new Font("Segoe UI Semilight", Font.BOLD, 30));
		getContentPane().add(num0);
		num0.addActionListener(this);
		
		num1= new JButton("1");
		num1.setBounds(100, 45, 80, 80);
		num1.setFont(new Font("Segoe UI Semilight", Font.BOLD, 30));
		getContentPane().add(num1);
		num1.addActionListener(this);
		
		num2= new JButton("2");
		num2.setBounds(190, 45, 80, 80);
		num2.setFont(new Font("Segoe UI Semilight", Font.BOLD, 30));
		getContentPane().add(num2);
		num2.addActionListener(this);
		
		num3= new JButton("3");
		num3.setBounds(280, 45, 80, 80);
		num3.setFont(new Font("Segoe UI Semilight", Font.BOLD, 30));
		getContentPane().add(num3);
		num3.addActionListener(this);
		
		num4= new JButton("4");
		num4.setBounds(10, 135, 80, 80);
		num4.setFont(new Font("Segoe UI Semilight", Font.BOLD, 30));
		getContentPane().add(num4);
		num4.addActionListener(this);
		
		num5= new JButton("5");
		num5.setBounds(100, 135, 80, 80);
		num5.setFont(new Font("Segoe UI Semilight", Font.BOLD, 30));
		getContentPane().add(num5);
		num5.addActionListener(this);
		
		num6= new JButton("6");
		num6.setBounds(190, 135, 80, 80);
		num6.setFont(new Font("Segoe UI Semilight", Font.BOLD, 30));
		getContentPane().add(num6);
		num6.addActionListener(this);
		
		num7= new JButton("7");
		num7.setBounds(280, 135, 80, 80);
		num7.setFont(new Font("Segoe UI Semilight", Font.BOLD, 30));
		getContentPane().add(num7);
		num7.addActionListener(this);
		
		num8= new JButton("8");
		num8.setBounds(10, 225, 80, 80);
		num8.setFont(new Font("Segoe UI Semilight", Font.BOLD, 30));
		getContentPane().add(num8);
		num8.addActionListener(this);
		
		num9= new JButton("9");
		num9.setBounds(100, 225, 80, 80);
		num9.setFont(new Font("Segoe UI Semilight", Font.BOLD, 30));
		getContentPane().add(num9);
		num9.addActionListener(this);
		
		cancel=new JButton("清除");
		cancel.setBounds(190, 225, 80, 80);
		cancel.setFont(new Font("宋体", Font.BOLD, 20));
		getContentPane().add(cancel);
		cancel.addActionListener(this);
		
		ensure=new JButton("确认");
		ensure.setBounds(280, 225, 80, 80);
		ensure.setFont(new Font("宋体", Font.BOLD, 20));
		getContentPane().add(ensure);
		ensure.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(jtf==null)return;
		jtf.requestFocus();
		// TODO Auto-generated method stub
		Object j=e.getSource();
		String s=jtf.getText();
		if(tfl.textType(s,((ATMTextField)jtf).getLimitLength())){
			if(j==num0){
				s=s+"0";
			}else if(j==num1){
				s=s+"1";
			}else if(j==num2){
				s=s+"2";
			}else if(j==num3){
				s=s+"3";
			}else if(j==num4){
				s=s+"4";
			}else if(j==num5){
				s=s+"5";
			}else if(j==num6){
				s=s+"6";
			}else if(j==num7){
				s=s+"7";
			}else if(j==num8){
				s=s+"8";
			}else if(j==num9){
				s=s+"9";
			}
		}
		if(j==cancel){
				if(jtf.getText().equals(""))return;
				s=s.substring(0,s.length()-1);
			}else if(j==ensure){
				this.setVisible(false);
			}
			jtf.setText(s);
		
	}
	
	//必须调用传入文本框
	public void setTextField(ATMTextField jtf){
		this.jtf=jtf;
	}
	
	public void addTextFieldListener(TextFieldListener tfl){
		this.tfl=tfl;
	}
	
	public interface TextFieldListener{
		public boolean textType(String text,int limitLengh); //返回true则增加字符
	};
}

