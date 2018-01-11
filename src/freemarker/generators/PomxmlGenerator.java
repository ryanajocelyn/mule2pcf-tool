package freemarker.generators;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import freemarker.pojo.Dependencies;
import freemarker.pojo.Dependency;
import freemarker.pojo.ParentPom;

public class PomxmlGenerator {

	public static void generateSpringBootPom(String inputfilepath,String outputfilepath,String dependenciesxmlPath ) throws XPathExpressionException {

	   try {
		/*String inputfilepath = "C:\\Users\\212460\\neon-eclipse-workspace\\FreemarkerJavaProject\\src\\freemarker\\poms\\mulepom.xml";
		String outputfilepath = "C:\\Users\\212460\\neon-eclipse-workspace\\FreemarkerJavaProject\\src\\freemarker\\poms\\springbootpom.xml";*/
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(inputfilepath);

		// Build XPath
		XPath xPath = XPathFactory.newInstance().newXPath();
		//("//cricketer[contains(name,'ha')]/name/text()");
		Node dependenciesNode = (Node) xPath.compile("//dependencies").evaluate(doc,
				XPathConstants.NODE);
		System.out.println("count before = "+dependenciesNode.getChildNodes().getLength());
		String compXmlExpr = "//dependency[contains(artifactId,'mule')]/artifactId";
		NodeList beanNodeList = (NodeList) xPath.compile(compXmlExpr).evaluate(doc,
				XPathConstants.NODESET);

		for (int i = 0; i < beanNodeList.getLength(); i++) {
			Node n = beanNodeList.item(i);
			System.out.println("node name = "+beanNodeList.item(i).getNodeName());
			System.out.println(beanNodeList.item(i).getTextContent());
			System.out.println(beanNodeList.item(i).getParentNode().getNodeName());
			//Node dependenciesNode = beanNodeList.item(i).getParentNode().getParentNode();
			System.out.println("dependencies node name = "+dependenciesNode.getNodeName());
			dependenciesNode.removeChild(n.getParentNode());
			//doc.removeChild(n.getParentNode());
		}
		System.out.println("count after mule removal = "+dependenciesNode.getChildNodes().getLength());
		//insert parent
	/*	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.5.2.RELEASE</version>
		<relativePath />
	</parent>*/
		Node parentNode = (Node) xPath.compile("//parent").evaluate(doc,
				XPathConstants.NODE);
		Node siblingNode = null;
		if(parentNode!=null){
			siblingNode = parentNode.getNextSibling();
			System.out.println("<parent> parent tag = "+parentNode.getParentNode().getNodeName());
			Node parentsParentTag = parentNode.getParentNode();
			parentsParentTag.removeChild(parentNode); //confirm whther this is reqd.
		}
		if(dependenciesNode!=null){
			ParentPom p = new ParentPom();
			p.setGroupId("org.springframework.boot");
			p.setArtifactId("spring-boot-starter-parent");
			p.setVersion("1.5.2.RELEASE");

			Element parentElem = doc.createElement("parent");
			Element groupId = doc.createElement("groupId");
			groupId.appendChild(doc.createTextNode(p.getGroupId()));
			Element artifact = doc.createElement("artifactId");
			artifact.appendChild(doc.createTextNode(p.getArtifactId()));
			Element version = doc.createElement("version");
			version.appendChild(doc.createTextNode(p.getVersion()));
			parentElem.appendChild(groupId);
			parentElem.appendChild(artifact);
			if(p.getVersion()!=null){
				parentElem.appendChild(version);
			}
			if(siblingNode!=null){
				doc.getDocumentElement().insertBefore(parentElem, siblingNode);
			}else{
				doc.getDocumentElement().insertBefore(parentElem, dependenciesNode);
			}
		}
		//dependencies.xml unmarshalling to load Dependencies pojo
		Dependencies dependencies = null;
		try {

			File file = new File(dependenciesxmlPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(Dependencies.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			dependencies = (Dependencies) jaxbUnmarshaller.unmarshal(file);
			System.out.println(dependencies);

		  } catch (JAXBException e) {
			e.printStackTrace();
		  }
		
		//adding spring boot dependency nodes
		if(dependencies != null && dependencies.getDependency().length>0){
			System.out.println(dependencies.getDependency().length);
			for(int i=0;i<dependencies.getDependency().length;i++){
				Dependency dependency = (Dependency)dependencies.getDependency()[i];
				System.out.println(dependency.toString());
				Element dependencyElem = doc.createElement("dependency");
				Element groupId = doc.createElement("groupId");
				groupId.appendChild(doc.createTextNode(dependency.getGroupId()));
				Element artifact = doc.createElement("artifactId");
				artifact.appendChild(doc.createTextNode(dependency.getArtifactId()));
				Element version = doc.createElement("version");
				version.appendChild(doc.createTextNode(dependency.getVersion()));
				dependencyElem.appendChild(groupId);
				dependencyElem.appendChild(artifact);
				if(dependency.getVersion()!=null){
					dependencyElem.appendChild(version);
				}
				//dependenciesNode.appendChild(doc.createTextNode(""));
				dependenciesNode.appendChild(dependencyElem);
			}
		}
		System.out.println("count after = "+dependenciesNode.getChildNodes().getLength());
		/*
		for(int i=0;i<dependenciesNode.getChildNodes().getLength();i++){
			Node dependencynode = dependenciesNode.getChildNodes().item(i);
			System.out.println("node name =  "+dependencynode.getNodeName());
			System.out.println("*** text === "+dependencynode.getTextContent());
		}*/
		
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				Result dest = new StreamResult(System.out);
			    transformer.transform(source, dest);
			    System.out.println("==============");
				StreamResult result = new StreamResult(new File(outputfilepath));
				transformer.transform(source, result);
				System.out.println("Done");
	   } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	   } catch (TransformerException tfe) {
		tfe.printStackTrace();
	   } catch (IOException ioe) {
		ioe.printStackTrace();
	   } catch (SAXException sae) {
		sae.printStackTrace();
	   }
	}
}
