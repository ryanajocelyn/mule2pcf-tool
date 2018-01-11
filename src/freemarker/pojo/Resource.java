/**
 * 
 */
package freemarker.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 212460
 *
 */
public class Resource {
	private String className;
	private String fullClassName;
	private String classVariableName;
	private List<Property> propList = new ArrayList<Property>();
	private String refParams ="";
	
	public Resource(String className, String fullClassName, String classVariableName) {
		super();
		this.className = className;
		this.fullClassName = fullClassName;
		this.classVariableName = classVariableName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getFullClassName() {
		return fullClassName;
	}
	public void setFullClassName(String fullClassName) {
		this.fullClassName = fullClassName;
	}
	public String getClassVariableName() {
		return classVariableName;
	}
	public void setClassVariableName(String classVariableName) {
		this.classVariableName = classVariableName;
	}
	public List<Property> getPropList() {
		return propList;
	}
	public void setPropList(List<Property> propList) {
		this.propList = propList;
	}
	public String getRefParams() {
		return refParams;
	}
	public void setRefParams(String refParams) {
		this.refParams = refParams;
	}
	
}
