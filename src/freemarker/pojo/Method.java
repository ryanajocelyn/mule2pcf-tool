/**
 * 
 */
package freemarker.pojo;

/**
 * @author 212460
 *
 */
public class Method {
	private String methodName;
	private String returnType;
	private String accessSpecifier;
	private String modifiers;
	private String params;
	private String body;

	public Method(String methodName, String returnType, String accessSpecifier, String modifiers, String params,
			String body) {
		super();
		this.methodName = methodName;
		this.returnType = returnType;
		this.accessSpecifier = accessSpecifier;
		this.modifiers = modifiers;
		this.params = params;
		this.body = body;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getAccessSpecifier() {
		return accessSpecifier;
	}

	public void setAccessSpecifier(String accessSpecifier) {
		this.accessSpecifier = accessSpecifier;
	}

	public String getModifiers() {
		return modifiers;
	}

	public void setModifiers(String modifiers) {
		this.modifiers = modifiers;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
