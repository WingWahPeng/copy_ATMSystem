package bean;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.MatteBorder;
import javax.swing.text.Document;


public class ATMTextField extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPasswordField jtf;
	private int limitLength=18;
	
	public ATMTextField(int width,int height){
		this(width,height,false);
	}
	public ATMTextField(int width,int height,boolean hide){
		// TODO Auto-generated constructor stub
		setLayout(null);
		jtf=new JPasswordField();
		jtf.setBorder(new MatteBorder(3, 4, 4, 2, new Color(101, 78, 86)));
		jtf.setBounds(0, 0, width, height);
		if(!hide){
			jtf.setEchoChar((char) 0);
		}
		jtf.setOpaque(false);
		add(jtf);
	}
    protected void paintComponent(Graphics g1) {// ��д����������
		super.paintComponent(g1);// ִ�г��෽��
        Graphics2D g = (Graphics2D) g1;

        int width = getWidth();// ��ȡ�����С
        int height = getHeight();
        // �������ģʽ����
        GradientPaint paint = new GradientPaint(0, 0, new Color(70,59,63), 0, height-30,
                Color.white);
        g.setPaint(paint);// ���û�ͼ��������ģʽ
        g.fillRect(0, 0, width, height);// ���ƾ������ؼ�����
    }
    
    public void addKeyListener(KeyListener k){
    	jtf.addKeyListener(k);
    };
    public void setEchoChar(char c){
    	jtf.setEchoChar(c);
    }
    public void setTextFont(Font f){
    	jtf.setFont(f);
    }
    
    public void setLimitLength(int l){
    	limitLength=l;
    }
    
    public int getLimitLength(){
    	return limitLength;
    }
    
    public String getText(){
    	return String.valueOf(jtf.getPassword());
    }
    
    public void setText(String s){
    	jtf.setText(s);
    }
    public void setHorizontalAlignment(int alignment){
    	jtf.setHorizontalAlignment(alignment);
    }
    public void setDocument(Document doc){
    	jtf.setDocument(doc);
    }
}
