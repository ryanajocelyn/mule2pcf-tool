package freemarker.pojo;

import javax.xml.bind.annotation.XmlElement;


public class Dependency {
	private String artifactId;
	private String version;
	private String groupId;
	private String scope;
	public String getArtifactId() {
		return artifactId;
	}
	@XmlElement
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}
	public String getVersion() {
		return version;
	}
	@XmlElement
	public void setVersion(String version) {
		this.version = version;
	}
	public String getGroupId() {
		return groupId;
	}
	@XmlElement
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getScope() {
		return scope;
	}
	@XmlElement
	public void setScope(String scope) {
		this.scope = scope;
	}
	@Override
	public String toString() {
		return "Dependency [artifactId=" + artifactId + ", version=" + version + ", groupId=" + groupId + ", scope="
				+ scope + "]";
	}
	
}
