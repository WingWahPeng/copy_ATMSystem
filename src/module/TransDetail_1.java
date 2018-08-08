package module;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import bean.ATMButton;
import bean.ATMPanel;
import bean.Clocker;
import bean.Clocker.Timeout;
import toolkit.MyUtil;

public class TransDetail_1 extends ATMPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton returnCard, backMainWindow, userDefined;
	private Clocker clocker;
	private JLabel tips, transDetail;
	private JScrollPane scr;
	private JTable table;	
	private DefaultTableCellRenderer render, rightRender;
	private Dimension viewSize;
	private JTableHeader header;
	private TableModel model;
	private String transType, transMoney, transDate, targetAccount;
	private ResultSet rs;
	
	
	public TransDetail_1(ResultSet rs){
		super("resource/ATM.png");
		this.rs=rs;
		
		tips=new JLabel("<html>\u672C\u673A\u7F16\u53F7:17091716<br>\u5BA2\u670D\u7535\u8BDD:95566</html>");
		tips.setFont(new Font("宋体", Font.BOLD, 22));
		tips.setBounds(692, 10, 208, 67);
		add(tips);
		
		
		transDetail = new JLabel("<html>历史交易明细</html>");
		transDetail.setFont(new Font("宋体", Font.BOLD, 28));
		transDetail.setBounds(353, 113, 186, 37);
		add(transDetail);
		
		
		returnCard=new ATMButton("退卡");
		returnCard.setBounds(0, 524, 190, 45);
		add(returnCard);
		returnCard.addActionListener(this);
		
		
		userDefined = new ATMButton("自定义");
		userDefined.setBounds(710, 428, 190, 45);
		add(userDefined);
		userDefined.addActionListener(this);
		
		backMainWindow=new ATMButton("回主菜单");
		backMainWindow.setBounds(710, 524, 190, 45);
		add(backMainWindow);
		backMainWindow.addActionListener(this);
		
		
		
		
		clocker=new Clocker();
		clocker.setSize(90, 70);
		clocker.setLocation(592, 10);
		add(clocker);
		clocker.startCountDown();
		clocker.setTimeout(new Timeout(){

			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				MyUtil.panelLeap(TransDetail_1.this, new MainWindow(), null);
			}
			
		});
		
		tableDesign();	
		scrollDesign();
		add(scr);
		
		setSize(900,600);
		setLayout(null);	
	}
	
	
	
	
	
	private void tableDesign(){
		table = new JTable(0, 0);
		table.setRowHeight(30);            //设置每行单元格的高度
		table.setFont(new Font("宋体", Font.BOLD, 18));     //设置表格中字体的大小
		
		
		model = new TableModel();
		table.setModel(model);
		
		
		//设置列宽
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(140);
		table.getColumnModel().getColumn(2).setPreferredWidth(90);
		table.getColumnModel().getColumn(3).setPreferredWidth(170); 
		table.getColumnModel().getColumn(4).setPreferredWidth(220);
		
		
		render = new DefaultTableCellRenderer();
		table.setDefaultRenderer(Object.class, render);
		render.setHorizontalAlignment(JTextField.CENTER);
		
		//设置表头
		header = table.getTableHeader();
		header.setPreferredSize(new Dimension(30, 30));
		header.setFont(new Font("宋体", Font.BOLD, 16));
		header.setForeground(new Color(153, 0, 0));
		
	    //将最后一列设置为靠右对齐
		rightRender = new DefaultTableCellRenderer();
		rightRender.setOpaque(false);
		rightRender.setHorizontalAlignment(JTextField.RIGHT);
		table.getColumnModel().getColumn(3).setCellRenderer(rightRender);
		
		//设置显示范围
		viewSize = new Dimension();
		viewSize.width = table.getColumnModel().getTotalColumnWidth();
		viewSize.height = 10 * table.getRowHeight();
		table.setPreferredScrollableViewportSize(viewSize);
	}
	
	
	
	
	
	private void scrollDesign(){
		scr = new JScrollPane();
		scr.setSize(700, 242);
		scr.setLocation(100, 160);
		scr.setOpaque(false);
		scr.getViewport().setOpaque(false);
		scr.getVerticalScrollBar().setOpaque(false);
		scr.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		scr.setViewportView(table);
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==returnCard){
			
			MyUtil.panelLeap(this, new Welcome(), clocker);
			
		}else if(arg0.getSource()==backMainWindow){
			
			MyUtil.panelLeap(this, new MainWindow(), clocker);
			
		}else{

			MyUtil.panelLeap(this, new TransDetail_2(rs), clocker);

		}
	}
	
	
	
	
	private class TableModel extends AbstractTableModel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String[] tableName={"序号", "交易日期", "交易类型", "交易金额", "转入(出)卡号"};
		@SuppressWarnings("rawtypes")
		private Vector content;
		@SuppressWarnings("rawtypes")
		private Vector<Comparable> vRow;
		private int count=0;
		
		
		@SuppressWarnings("rawtypes")
		public TableModel(){
			content = new Vector();
			readDataBase();
		}
		
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void readDataBase(){	
			try{

				if(!rs.isBeforeFirst())
					rs.beforeFirst();
				while(rs.next()){
					count++;
					transType = rs.getString(3);
					transMoney = rs.getString(4);
					transDate = rs.getString(5);
					targetAccount = rs.getString(6);
					
					vRow = new Vector<Comparable>();
					vRow.add(count+"");
					vRow.add(transDate);
					vRow.add(transType);
					if(transType.equals("存款") || transType.equals("转入"))
						vRow.add(transMoney + " CNY");
					else 
						vRow.add("-" + transMoney +" CNY");
					if(transType.equals("转入") || transType.equals("转出"))
						vRow.add(targetAccount);
					else
						vRow.add("******************");
					
					content.add(vRow.clone());
				}
			}catch(Exception e){
				System.out.println(e.toString());
			}
		}
		
		
		
		public boolean isCellEditable(int row,int column) {       
	        return false;
	    }
		
		
		 @SuppressWarnings({ "rawtypes", "unchecked" })
		public void setValueAt(Object value, int row, int column) {
		        ((Vector)content.get(row)).remove(column);
		        ((Vector)content.get(row)).add(column, value);
		        this.fireTableCellUpdated(row, column);
		    }
		
		public String getColumnName(int column){
			return tableName[column];
		}
		
		
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return tableName.length;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return content.size();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Object getValueAt(int row, int column) {
			// TODO Auto-generated method stub
			return ((Vector)content.get(row)).get(column);
		}
		
	}
}