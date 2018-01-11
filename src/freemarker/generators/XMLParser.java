package freemarker.generators;

import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import freemarker.pojo.Method;
import freemarker.template.Configuration;
import freemarker.template.Template;


public class XMLParser {
	private static XMLParser engine;
	private Template template = null;
	Map<String, Object> dataMap = new HashMap<String, Object>();
	private static final String CLASS_NAME = "MuleConfigClass";
	private static final String FILE_EXTN = ".java";
	private static final String FILE_PATH_SEPARATOR = "\\";
	private static final String PATH = "\\C:\\Users\\212460\\neon-eclipse-workspace\\FreemarkerJavaProject\\src\\freemarker\\output";
	private static final String XML_PATH = "\\C:\\Users\\212460\\neon-eclipse-workspace\\FreemarkerJavaProject\\src\\freemarker\\xmls";
	private static final String PACKAGE = "package freemarker.output;";
	HashMap<String,String> beanClassMap = new HashMap<String,String>();
	private XMLParser() {
		
		
	}


	public static XMLParser get() {
		if(engine == null){
			engine = new XMLParser();
		}
		return engine;
	}

	private void parseXML(){
		try{
		Set<String> beanNamesSet = new TreeSet<String>();
		//Create a DocumentBuilder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Create a Document from a file or stream
		Document doc = builder.parse(new File(XML_PATH+"\\sample-mule-config.xml"));
		
		//Build XPath
		XPath xPath =  XPathFactory.newInstance().newXPath();

		//String expression = "/mule/inf-mule-pattern:jersey-http-service/jersey-resources/component";
		System.out.println(((NodeList) xPath.compile("/mule/*[name()='inf-mule-pattern:jersey-http-service']/jersey-resources/component").evaluate(
		   doc, XPathConstants.NODESET)).getLength());
		
		NodeList nodeList = (NodeList) xPath.compile("/mule/*[name()='inf-mule-pattern:jersey-http-service']/jersey-resources/component").evaluate(
		   doc, XPathConstants.NODESET);
		
		for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               System.out.println("spinobj nodelist size = "+ eElement.getElementsByTagName("springObject").getLength());
               Node springObjNode = eElement.getElementsByTagName("springObject").item(0);
               
               if (springObjNode.getNodeType() == Node.ELEMENT_NODE) {
            	   Element springObjElement = (Element) springObjNode;
            	   beanNamesSet.add(springObjElement.getAttribute("bean"));
               }
            }
         }
		System.out.println("set size ="+beanNamesSet.size());
		System.out.println("set ="+beanNamesSet);
		
		
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
	
	public static void main(String[] args) {
		XMLParser.get().parseXML();
	}
}