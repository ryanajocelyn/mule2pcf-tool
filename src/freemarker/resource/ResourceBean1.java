package freemarker.resource;

import java.util.List;

import freemarker.pojo.Property;

public class ResourceBean1 {
	private String name;
	private String address;
	private ResourceBean4 locationDetail;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public ResourceBean4 getLocationDetail() {
		return locationDetail;
	}
	public void setLocationDetail(ResourceBean4 locationDetail) {
		this.locationDetail = locationDetail;
	}


}
