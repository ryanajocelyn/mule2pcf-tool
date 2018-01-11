package freemarker.generators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import freemarker.base.BaseConfigClassGenerator;
import freemarker.pojo.Resource;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class PropertyFileGenerator extends BaseConfigClassGenerator{
	
	HashMap<String,String> propMap = new LinkedHashMap<String,String>();
	
	
	public PropertyFileGenerator() {
		loadTemplate("vcappropertytemplate.ftl");
		buildData();
	}
	
	public static BaseConfigClassGenerator getInstance() {
		//if(engine == null){
			engine = new PropertyFileGenerator();
		//}
		return engine;
	}
	
	private void buildData() {
		copyFileUsingStream();
		parseFile();
		System.out.println("hashmap = "+ propMap.toString());
		System.out.println("Preparing Data");
		dataMap.put("properties",propMap);
		System.out.println("Data Created");
	}
	
	private void parseFile(){
		BufferedReader br = null;
		try{
		File f = new File(INPUT_PROP_PATH);
		if(f.exists()){
		br = new BufferedReader(new FileReader(f));
		String in = null;
		while((in=br.readLine())!=null){
			if(in.contains("=")){
				String[] str = in.split("=");
				String value = str[1];
				if(value.contains("${")){//mule registry values converted to vcap
					value="${vcap.services.credentials."+str[0]+"}";
				}
				propMap.put(str[0], value);
			}else if(in.contains("#")){//comments are as such included
				propMap.put(in, "");
			}
		}
		}
		}
		 catch (IOException e) {
	         e.printStackTrace();
	      }finally{
	    	  try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	}

	private static void copyFileUsingStream() {
		//File source = new File("D:/212460/Express Scripts/MuleToPCF/EPrescribingGatewayMule/src/main/resources/prod/application.properties");
		//File dest = new File("D:/212460/Express Scripts/MuleToPCF/pharmacy-api/src/resources/properties/cloud/application.properties");
		File source = new File(INPUT_PROD_PROP_PATH);
		File dest = new File(OUTPUT_CLOUD_PROP_PATH);
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    }catch(IOException e){
	    	System.out.println("IOexception occured");
	    }
	    finally {
	       
	        try {
				os.close();
				 is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}

}
