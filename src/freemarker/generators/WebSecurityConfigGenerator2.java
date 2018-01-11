package freemarker.generators;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;

import freemarker.base.BaseConfigClassGenerator;
import freemarker.pojo.AnnotationDetails;
import freemarker.template.Configuration;
import freemarker.template.Template;


public class WebSecurityConfigGenerator2 extends BaseConfigClassGenerator{
	/*private static WebSecurityConfigGenerator2 engine;
	private Template template = null;
	Map<String, Object> dataMap = new HashMap<String, Object>();
	private static final String CLASS_NAME = "WebSecurityConfiguration2";
	private static final String FILE_EXTN = ".java";
	private static final String FILE_PATH_SEPARATOR = "\\";
	private static final String PATH = "\\C:\\Users\\212460\\neon-eclipse-workspace\\FreemarkerJavaProject\\src\\freemarker\\output";
	private static final String PACKAGE = "package freemarker.output;";*/
	
	public List<AnnotationDetails> annotationsList = new ArrayList<AnnotationDetails>();
	private WebSecurityConfigGenerator2() {
		loadTemplate("websecurityconfigtemplate2.ftl");
		buildData();
	}

	/*public void loadTemplate(String template) {
		Configuration cfg = new Configuration();
		try {
			template = cfg.getTemplate("src/freemarker/templates/"+template);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

	public static BaseConfigClassGenerator getInstance() {
		//if(engine == null){
			engine = new WebSecurityConfigGenerator2();
		//}
		return engine;
	}
	private void readAnnotations(){
		 File directory = new File(API_PATH);
	        //get all the files from a directory
	        File[] fList = directory.listFiles();
	     for (File file : fList){
	            if (file.isFile()){
	                System.out.println(file.getName());

	                String fileName = file.getName().substring(0, file.getName().indexOf("."));//.replace(".java", ".class");
	                parseToGetAnnotations(fileName);

	            }
	        }
	}

	private void parseToGetAnnotations(String className) {
		Class<?> cl;
		try {
			if(className!=null){
			cl = Class.forName(API_PATH_PACKAGE+className);
			//cl=	Thread.currentThread().getContextClassLoader().loadClass(className);
			AnnotationDetails ad;
			
			Path[] annotationArr = (Path[]) cl.getAnnotationsByType(Path.class);
			System.out.println("path of the class = " + annotationArr[0].value());
			System.out.println("annotation type of the class = " + annotationArr[0].annotationType());
			Method[] methodArr = cl.getDeclaredMethods();
			for (int i = 0; i < methodArr.length; i++) {
				Method m = methodArr[i];
				if (m.isAnnotationPresent(Path.class)) {
					 ad = new AnnotationDetails();
					 ad.setClassName(className);
					System.out.println("method path = " + m.getAnnotationsByType(Path.class)[0].toString());
					ad.setMethodPath(m.getAnnotationsByType(Path.class)[0].value());
					ad.setPathAnnotation(annotationArr[0].value());
					if(m.isAnnotationPresent(GET.class)){
						ad.setHttpMethod("HttpMethod.GET");
					}
					else if(m.isAnnotationPresent(POST.class) ){
						ad.setHttpMethod("HttpMethod.POST");
					}
					else if(m.isAnnotationPresent(PUT.class) ){
						ad.setHttpMethod("HttpMethod.PUT");
					}
					else if(m.isAnnotationPresent(DELETE.class) ){
						ad.setHttpMethod("HttpMethod.DELETE");
					}
					ad.setFullPath(ad.getPathAnnotation()+ad.getMethodPath());
					System.out.println("full path = "+ad.getFullPath());
					annotationsList.add(ad);
				}
			}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("No class found");
			e.printStackTrace();
		}
	}
	
	
	private void buildData() {
		readAnnotations();
		System.out.println("Preparing Data");
		dataMap.put("package",PACKAGE);
		dataMap.put("className", getClassName());
		dataMap.put("pathMethodList", annotationsList);
		System.out.println("Data Created");
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
		WebSecurityConfigGenerator2.getInstance().writeFile();
		//WebSecurityConfigGenerator2.getInstance().readAnnotations();
	}*/
}