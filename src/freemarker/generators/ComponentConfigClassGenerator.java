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
import freemarker.pojo.Property;
import freemarker.pojo.Resource;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class ComponentConfigClassGenerator extends BaseConfigClassGenerator{

	//private static final String CLASS_NAME = "ComponentConfigClass";
	HashMap<String, Resource> beanClassMap = new HashMap<String, Resource>();

	private ComponentConfigClassGenerator() {
		loadTemplate("componentcontextconfigtemplate.ftl");
		buildData();
		
	}

	public static BaseConfigClassGenerator getInstance() {
		//if (engine == null) {
			engine = new ComponentConfigClassGenerator();
		//}
		return engine;
	}

	private void parseXML() {
		try {
			// Create a DocumentBuilder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Build XPath
			XPath xPath = XPathFactory.newInstance().newXPath();
			// get classNames

			// Create a Document from a file or stream
			Document componentdoc = builder.parse(new File(XML_PATH + "\\component-context.xml"));
			String compXmlExpr = "/beans/bean";
			NodeList beanNodeList = (NodeList) xPath.compile(compXmlExpr).evaluate(componentdoc,
					XPathConstants.NODESET);
			for (int i = 0; i < beanNodeList.getLength(); i++) {
				Node nNode = beanNodeList.item(i);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String key = eElement.getAttribute("id");
					Resource resource = new Resource("", eElement.getAttribute("class"), key);
					String fullName = resource.getFullClassName();
					resource.setClassName(fullName.substring(fullName.lastIndexOf(".") + 1));
					beanClassMap.put(key, resource);

					// get properties of a bean
					NodeList propNodeList = eElement.getElementsByTagName("property");
					for (int j = 0; j < propNodeList.getLength(); j++) {
						Node propNode = propNodeList.item(j);
						System.out.println("\npropNode Element :" + propNode.getNodeName());

						if (propNode.getNodeType() == Node.ELEMENT_NODE) {
							Element propElement = (Element) propNode;
							
							if (!propElement.getAttribute("ref").equals("")) {
								Node refBeanNode = (Node) xPath
										.compile("//bean[@id='" + propElement.getAttribute("ref") + "']")
										.evaluate(componentdoc, XPathConstants.NODE);
								Element refBeanElement = (Element) refBeanNode;
								String className = refBeanElement.getAttribute("class")
										.substring(refBeanElement.getAttribute("class").lastIndexOf(".") + 1);
								if(resource.getRefParams()!=null && !resource.getRefParams().equals("")){
									resource.setRefParams(resource.getRefParams()+",");
								}
								resource.setRefParams(resource.getRefParams()+className+" "+refBeanElement.getAttribute("id"));
								/*prop.setValue("generate" + refBeanElement.getAttribute("class")
								.substring(refBeanElement.getAttribute("class").lastIndexOf(".") + 1) + "()");*/
								/*prop.setValue("new " + refBeanElement.getAttribute("class")
										.substring(refBeanElement.getAttribute("class").lastIndexOf(".") + 1) + "()");*/
							} else {
								Property prop = new Property(propElement.getAttribute("name"),
										propElement.getAttribute("value"));
								prop.setValue("\"" + propElement.getAttribute("value") + "\"");
								System.out.println(prop.toString());
								resource.getPropList().add(prop);
							}
							
							beanClassMap.put(key, resource);
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
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
		System.out.println("hashmap = " + beanClassMap.toString());
		System.out.println("Preparing Data");
		dataMap.put("resources", beanClassMap);
		dataMap.put("package", PACKAGE);
		dataMap.put("className", getClassName());
		dataMap.put("params", getParams(beanClassMap));
		System.out.println("Data Created");
	}

	private String getParams(HashMap<String, Resource> beanMap) {
		StringBuffer paramStr = new StringBuffer();
		if (beanMap != null && beanMap.size() > 0) {
			Iterator<String> itr = beanMap.keySet().iterator();
			while (itr.hasNext()) {
				String k = itr.next();
				Resource res = beanMap.get(k);
				paramStr.append(res.getClassName() + " " + res.getClassVariableName() + ",");
			}
		}
		String finalStr = paramStr.substring(0, paramStr.lastIndexOf(","));
		return finalStr;
	}

	

	/*public static void main(String[] args) {
		ComponentConfigClassGenerator.get().writeFile();
	}*/

	
}