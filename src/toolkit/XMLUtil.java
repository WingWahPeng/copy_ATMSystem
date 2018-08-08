package toolkit;

/**
 * xml操作工具类，xml文件数据读取实现类
 * xml dom的运用
 */
import java.io.File;

import javax.xml.parsers.*;

import org.w3c.dom.*;

public class XMLUtil {
	public static String parse(String tag){
		try{

//创建文档对象
			DocumentBuilderFactory dFactory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=dFactory.newDocumentBuilder();			
			Document doc=builder.parse(new File("config.xml"));
//获取包含类名的文本节点
//NodeList 对象代表一个有序的节点列表。			
			NodeList nl=doc.getElementsByTagName(tag);	
			Node tagNode=nl.item(0).getFirstChild();		
			String value=tagNode.getNodeValue();
			return value;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
