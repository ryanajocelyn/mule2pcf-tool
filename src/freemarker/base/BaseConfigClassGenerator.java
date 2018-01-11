package freemarker.base;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import freemarker.generators.ComponentConfigClassGenerator;
import freemarker.generators.MuleconfigClassGenerator;
import freemarker.generators.PomxmlGenerator;
import freemarker.generators.PropertyFileGenerator;
import freemarker.generators.SpringBootMainGenerator;
import freemarker.generators.WebSecurityConfigGenerator2;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class BaseConfigClassGenerator {

	public static final String FILE_EXTN = ".java";
	public static final String FILE_PATH_SEPARATOR = "\\";
	
	public static final String PATH = "\\C:\\Users\\212460\\neon-eclipse-workspace\\FreemarkerJavaProject\\src\\freemarker\\output";
	//public static final String PATH = "..\\output";
	public static final String PACKAGE = "package freemarker.output;";
	public static final String XML_PATH = "\\C:\\Users\\212460\\neon-eclipse-workspace\\FreemarkerJavaProject\\src\\freemarker\\xmls";
	public static final String API_PATH = "\\C:\\Users\\212460\\neon-eclipse-workspace\\FreemarkerJavaProject\\src\\freemarker\\resourcesapi";
	public static final String API_PATH_PACKAGE = "freemarker.resourcesapi.";
	
	//properties file
	public static final String OUTPUT_CLOUD_PROP_PATH = "C:/Users/212460/neon-eclipse-workspace/FreemarkerJavaProject/src/freemarker/output/properties/cloud/application.properties";
	public static final String OUTPUT_PROP_PATH = "C:/Users/212460/neon-eclipse-workspace/FreemarkerJavaProject/src/freemarker/output/properties/dev/application.properties";
	//public static final String OUTPUT_CLOUD_PROP_PATH = "C:/Users/212460/neon-eclipse-workspace/SpringBootSampleProject/src/resources/properties/cloud/application.properties";
	//public static final String OUTPUT_PROP_PATH = "C:/Users/212460/neon-eclipse-workspace/SpringBootSampleProject/src/resources/properties/dev/application.properties";
	/*public static final String INPUT_PROP_PATH = "D:/212460/Express Scripts/MuleToPCF/EPrescribingGatewayMule/src/main/resources/properties/dev/application.properties";
	public static final String INPUT_PROD_PROP_PATH = "D:/212460/Express Scripts/MuleToPCF/EPrescribingGatewayMule/src/main/resources/properties/prod/application.properties";*/
	public static final String INPUT_PROP_PATH = "C:/Users/212460/neon-eclipse-workspace/FreemarkerJavaProject/src/freemarker/properties/dev/application.properties";
	public static final String INPUT_PROD_PROP_PATH = "C:/Users/212460/neon-eclipse-workspace/FreemarkerJavaProject/src/freemarker/properties/prod/application.properties";
	
	//pom files
	//public static final String INPUT_POM_FILE = "C:\\Users\\212460\\neon-eclipse-workspace\\FreemarkerJavaProject\\src\\freemarker\\poms\\mulepom.xml";
	//public static final String OUTPUT_POM_FILE = "C:\\Users\\212460\\neon-eclipse-workspace\\FreemarkerJavaProject\\src\\freemarker\\poms\\springbootpom.xml";
	//public static final String INPUT_POM_FILE = "C:/Users/212460/neon-eclipse-workspace/MuleSampleProject/pom.xml";
	//public static final String OUTPUT_POM_FILE = "C:/Users/212460/neon-eclipse-workspace/SpringBootSampleProject/pom.xml";
	public static final String INPUT_POM_FILE = "C:/Users/212460/neon-eclipse-workspace/FreemarkerJavaProject/src/freemarker/poms/mule-pom.xml";
	public static final String OUTPUT_POM_FILE = "C:/Users/212460/neon-eclipse-workspace/FreemarkerJavaProject/src/freemarker/output/pom/springboot-pom.xml";
	
	public static final String DEPENDENCIES_XML_FILE = "C:\\Users\\212460\\neon-eclipse-workspace\\FreemarkerJavaProject\\src\\freemarker\\poms\\dependencies.xml";
	public static BaseConfigClassGenerator engine;
	public Template template = null;
	public Map<String, Object> dataMap = new HashMap<String, Object>();
	
	public static Map<Class, String> outputclassNamesMap = new HashMap<Class,String>();
	public static Map<Class, String> classNamesMap = new HashMap<Class,String>();
	
	static{
		outputclassNamesMap.put(ComponentConfigClassGenerator.class,PATH+FILE_PATH_SEPARATOR+"ComponentConfigClass"+FILE_EXTN);
		outputclassNamesMap.put(SpringBootMainGenerator.class, PATH+FILE_PATH_SEPARATOR+"SpringBootMain"+FILE_EXTN);
		outputclassNamesMap.put(MuleconfigClassGenerator.class, PATH+FILE_PATH_SEPARATOR+"MuleConfigClass"+FILE_EXTN);
		outputclassNamesMap.put(WebSecurityConfigGenerator2.class, PATH+FILE_PATH_SEPARATOR+"WebSecurityConfiguration2"+FILE_EXTN);
		outputclassNamesMap.put(PropertyFileGenerator.class, OUTPUT_PROP_PATH);
	}
	
	static{
		classNamesMap.put(ComponentConfigClassGenerator.class,"ComponentConfigClass");
		classNamesMap.put(SpringBootMainGenerator.class, "SpringBootMain");
		classNamesMap.put(MuleconfigClassGenerator.class, "MuleConfigClass");
		classNamesMap.put(WebSecurityConfigGenerator2.class, "WebSecurityConfiguration2");
	}
	
	public String getOutputClassName() {
		
		return outputclassNamesMap.get(getClass());
	}
	public String getClassName() {
		
		return classNamesMap.get(getClass());
	}
	
	public void loadTemplate(String templateName) {
		Configuration cfg = new Configuration();
		try {
			template = cfg.getTemplate("src/freemarker/templates/"+templateName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeFile() {
		Writer file = null;
		// String path =
		// SpringBootMainGenerator.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		System.out.println("filepath = " + PATH);
		//StringBuffer filepath = new StringBuffer();
		//filepath.append(PATH).append(FILE_PATH_SEPARATOR).append(getOutputClassName()).append(FILE_EXTN);
		try {
			file = new FileWriter(new File(getOutputClassName()));
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
	}

	public static void main(String[] args) { 
		SpringBootMainGenerator.getInstance().writeFile();
		System.out.println("----springbootmain class over----");
		ComponentConfigClassGenerator.getInstance().writeFile();
		System.out.println("----componentconfig class over----");
		MuleconfigClassGenerator.getInstance().writeFile();
		System.out.println("----muleconfig class over----");
		WebSecurityConfigGenerator2.getInstance().writeFile();
		System.out.println("----websecurityconfig class over----");
		PropertyFileGenerator.getInstance().writeFile();
		System.out.println("----property file copy/write over----");
		try {
			PomxmlGenerator.generateSpringBootPom(INPUT_POM_FILE, OUTPUT_POM_FILE,DEPENDENCIES_XML_FILE);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
