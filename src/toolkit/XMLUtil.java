package toolkit;

/**
 * xml���������࣬xml�ļ����ݶ�ȡʵ����
 * xml dom������
 */
import java.io.File;

import javax.xml.parsers.*;

import org.w3c.dom.*;

public class XMLUtil {
	public static String parse(String tag){
		try{

//�����ĵ�����
			DocumentBuilderFactory dFactory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=dFactory.newDocumentBuilder();			
			Document doc=builder.parse(new File("config.xml"));
//��ȡ�����������ı��ڵ�
//NodeList �������һ������Ľڵ��б�			
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
