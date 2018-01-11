${package}

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
<#list resources?values as resourceObj>
import ${resourceObj.fullClassName};
</#list>
@Configuration

public class ${className} {
	@Bean
    public RestEasyResources restEasyResources(${params}){
        RestEasyResources resources = new RestEasyResources();
        <#list resources?keys as altName>
        resources.add(${altName});
        </#list>
    }
}