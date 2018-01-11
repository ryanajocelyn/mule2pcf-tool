package freemarker.generators;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.Template;


public class WebSecurityConfigGenerator {
	private static WebSecurityConfigGenerator engine;
	private Template template = null;
	Map<String, Object> dataMap = new HashMap<String, Object>();
	private static final String CLASS_NAME = "WebSecurityConfiguration";
	private static final String FILE_EXTN = ".java";
	private static final String FILE_PATH_SEPARATOR = "\\";
	private static final String PATH = "\\C:\\Users\\212460\\neon-eclipse-workspace\\FreemarkerJavaProject\\src\\freemarker\\output";
	private static final String PACKAGE = "package freemarker.output;";
	private WebSecurityConfigGenerator() {
		loadTemplate();
		buildData();
	}

	private void loadTemplate() {
		Configuration cfg = new Configuration();
		try {
			template = cfg.getTemplate("src/freemarker/templates/websecurityconfigtemplate.ftl");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static WebSecurityConfigGenerator getInstance() {
		if(engine == null){
			engine = new WebSecurityConfigGenerator();
		}
		return engine;
	}

	private void buildData() {
		System.out.println("Preparing Data");
		dataMap.put("package",PACKAGE);
		dataMap.put("className", CLASS_NAME);
		System.out.println("Data Created");
	}

	public void writeFile() {
		Writer file = null;
		//String path =  SpringBootMainGenerator.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		System.out.println("filepath = "+ PATH);
		StringBuffer filepath = new StringBuffer();
		filepath.append(PATH).append(FILE_PATH_SEPARATOR).append(CLASS_NAME).append(FILE_EXTN);
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
	}

	public static void main(String[] args) {
		WebSecurityConfigGenerator.getInstance().writeFile();
	}
}