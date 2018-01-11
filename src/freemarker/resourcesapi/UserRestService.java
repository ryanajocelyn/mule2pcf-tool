package freemarker.resourcesapi;


import java.lang.reflect.Method;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import freemarker.pojo.AnnotationDetails;

@Path("/users")
public class UserRestService {

	@GET
	public Response getUser() {

		return Response.status(200).entity("getUser is called").build();

	}

	@GET
	@Path("/vip")
	public Response getUserVIP() {

		return Response.status(200).entity("getUserVIP is called").build();

	}
	@POST
	@Path("/addBook")
	public String addBook() {return null;}
	
	private void parseToGetAnnotations(){
		AnnotationDetails ad = new AnnotationDetails();
		Path[] annotationArr = this.getClass().getAnnotationsByType(Path.class);
		System.out.println("path of the class = "+annotationArr[0].value());
		System.out.println("annotation type of the class = "+annotationArr[0].annotationType());
		Method[] methodArr =this.getClass().getMethods();
		for(int i=0;i<methodArr.length;i++){
			Method m = methodArr[i];
			if(m.isAnnotationPresent(Path.class)/*&& m.isAnnotationPresent(GET.class)*/){
				System.out.println("method path = "+m.getAnnotationsByType(Path.class)[0].toString());
				System.out.println("method @get = "+m.getAnnotationsByType(GET.class)[0].toString());
				ad.setMethodPath(m.getAnnotationsByType(Path.class).toString());
				ad.setPathAnnotation(annotationArr[0].toString());
				ad.setHttpMethod("HttpMethod.GET");
			}
		}
	}
	
	public static void main(String args[]){
	UserRestService ur = new UserRestService();
	ur.parseToGetAnnotations();
		
	}

}