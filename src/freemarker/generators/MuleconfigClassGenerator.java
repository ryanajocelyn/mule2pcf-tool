package freemarker.generators;

import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import freemarker.base.BaseConfigClassGenerator;
import freemarker.pojo.Resource;
import freemarker.template.Configuration;
import freemarker.template.Template;


public class MuleconfigClassGenerator extends BaseConfigClassGenerator{
	/*private static MuleconfigClassGenerator engine;
	private Template template = null;
	Map<String, Object> dataMap = new HashMap<String, Object>();
	private static final String CLASS_NAME = "MuleConfigClass";
	private static final String FILE_EXTN = ".java";
	private static final String FILE_PATH_SEPARATOR = "\\";
	private static final String PATH = "\\C:\\Users\\212460\\neon-eclipse-workspace\\FreemarkerJavaProject\\src\\freemarker\\output";
	private static final String XML_PATH = "\\C:\\Users\\212460\\neon-eclipse-workspace\\FreemarkerJavaProject\\src\\freemarker\\xmls";
	private static final String PACKAGE = "package freemarker.output;";*/
	HashMap<String,Resource> beanClassMap = new HashMap<String,Resource>();
	private MuleconfigClassGenerator() {
		loadTemplate("muleconfigtemplate.ftl");
		buildData();
	}
/*
	public void loadTemplate(String templateName) {
		Configuration cfg = new Configuration();
		try {
			template = cfg.getTemplate("src/freemarker/templates/"+templateName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

	public static BaseConfigClassGenerator getInstance() {
		//if(engine == null){
			engine = new MuleconfigClassGenerator();
		//}
		return engine;
	}

	private void parseXML(){
		try{
		Set<String> beanNamesSet = new TreeSet<String>();
		//Create a DocumentBuilder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		/*StringBuilder xmlStringBuilder = new StringBuilder();
		xmlStringBuilder.append("<?xml version = "1.0"?> <class> </class>");
		ByteArrayInputStream input =  new ByteArrayInputStream(
		   xmlStringBuilder.toString().getBytes("UTF-8"));*/
		//Create a Document from a file or stream
		Document doc = builder.parse(new File(XML_PATH+"\\http-mule-config.xml"));
		
		//Build XPath
		XPath xPath =  XPathFactory.newInstance().newXPath();

		String expression = "/mule/*[name()='inf-mule-pattern:jersey-http-service']/jersey-resources/component";	        
		NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
		   doc, XPathConstants.NODESET);
		
		for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               Node springObjNode = eElement.getElementsByTagName("spring-object").item(0);
               System.out.println("spring-object bean :" + eElement.getElementsByTagName("spring-object").item(0));

               if (springObjNode.getNodeType() == Node.ELEMENT_NODE) {
            	   Element springObjElement = (Element) springObjNode;
            	   beanNamesSet.add(springObjElement.getAttribute("bean"));
               }
            }
         }
		
		//get classNames
		
		//Create a Document from a file or stream
		Document componentdoc = builder.parse(new File(XML_PATH+"\\component-context.xml"));
		String compXmlExpr = "/beans/bean";
		NodeList beanNodeList = (NodeList) xPath.compile(compXmlExpr).evaluate(
				componentdoc, XPathConstants.NODESET);
		for (int i = 0; i < beanNodeList.getLength(); i++) {
            Node nNode = beanNodeList.item(i);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               String key = eElement.getAttribute("id");
               if(beanNamesSet.contains(key)){
            	   Resource resource = new Resource("", eElement.getAttribute("class"), key);
            	   String fullName = resource.getFullClassName();
            	   resource.setClassName(fullName.substring(fullName.lastIndexOf(".")+1));
            	   beanClassMap.put(key,resource);
               }
            }
         }
		}
		catch (ParserConfigurationException e) {
	         e.printStackTrace();
	      } catch (SAXException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (XPathExpressionException e) {
	         e.printStackTrace();
	      }

	}
	
	private void buildData() {
		parseXML();
		System.out.println("hashmap = "+ beanClassMap.toString());
		System.out.println("Preparing Data");
		dataMap.put("resources",beanClassMap);
		dataMap.put("package", PACKAGE);
		dataMap.put("className", getClassName());
		dataMap.put("params", getParams(beanClassMap));
		System.out.println("Data Created");
	}

	private String getParams(HashMap<String, Resource> beanMap) {
		StringBuffer paramStr = new StringBuffer();
		if(beanMap!=null && beanMap.size()>0){
			Iterator<String> itr = beanMap.keySet().iterator();
			while(itr.hasNext()){
				String k = itr.next();
				Resource res = beanMap.get(k);
				paramStr.append(res.getClassName() + " "+res.getClassVariableName()+",");
			}
		}
		String finalStr = paramStr.substring(0,paramStr.lastIndexOf(","));
		return finalStr;
	}

	/*public void writeFile() {
		Writer file = null;
		//String path =  SpringBootMainGenerator.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		System.out.println("filepath = "+ PATH);
		StringBuffer filepath = new StringBuffer();
		filepath.append(PATH).append(FILE_PATH_SEPARATOR).append(getOutputClassName()).append(FILE_EXTN);
		try {
			file = new FileWriter(new File(filepath.toString()));
			template.process(dataMap, file);
			file.flush();
			System.out.println("Success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/

	/*public static void main(String[] args) {
		MuleconfigClassGenerator.get().writeFile();
	}*/
}