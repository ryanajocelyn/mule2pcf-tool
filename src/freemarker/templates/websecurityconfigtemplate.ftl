${package}

<#--
//import java.util.ArrayList;
-->
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpMethod;
import com.esrx.inf.spring.boot.autoconfigure.security.HttpSecurityConfigurer;

@Component

public class ${className} implements HttpSecurityConfigurer {
	
    @Override
	public void configure(HttpSecurity http) throws Exception{
		//List<String> list = new Arraylist<String>();
		//AffirmativeBased adm = new AffirmativeBased(list);
		http.authorizeRequests()
		//.accessDecisionManager(adm)
		.antMatchers(HttpMethod.GET,"**/*").permitAll()
		.antMatchers(HttpMethod.POST,"**/*").permitAll();
		<#--
		//.antMatchers(HttpMethod.POST,"/v1/benefit/**").hasRole("ADMIN")
		//.antMatchers(HttpMethod.GET,"/swaggew/**").permitAll();
		-->
	}
	
}