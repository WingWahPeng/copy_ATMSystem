package bean;
/**
 * 所有面板的父类
 */
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ATMPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String backgroundPath;	//背景图片路径，注意相对路径与绝对路径的区别
	
	public ATMPanel(String backgroundPath){
		this.backgroundPath=backgroundPath;
		setSize(900, 600);
		setLayout(null);
	}
	
//为面板设置背景图片	
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		Image bgImg = null;
		try {
			bgImg = ImageIO.read(new File(backgroundPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		arg0.drawImage(bgImg, 0, 0, null);	//绘制图片
	}
}
