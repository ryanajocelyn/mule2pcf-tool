package freemarker.pojo;

public class Property {
private String name;
private String value;
private String camelCasePropName;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
public String getCamelCasePropName() {
	if(name!=null){
		camelCasePropName = Character.toUpperCase(name.charAt(0))+name.substring(1);
	}
	return camelCasePropName;
}

public Property(String name, String value) {
	super();
	this.name = name;
	this.value = value;
}
@Override
public String toString() {
	return "Property [name=" + name + ", value=" + value + ", camelCasePropName=" + camelCasePropName + "]";
}

}
