${package}

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
<#list resources?values as resourceObj>
import ${resourceObj.fullClassName};
</#list>
@Configuration

public class ${className} {
	<#list resources?values as resourceObj>
    @Bean
	public ${resourceObj.className} generate${resourceObj.className}(${resourceObj.refParams}){
		${resourceObj.className} ${resourceObj.classVariableName} = new ${resourceObj.className}();
		<#list resourceObj.propList as prop>
		${resourceObj.classVariableName}.set${prop.camelCasePropName}(${prop.value});
		</#list>
		return ${resourceObj.classVariableName};
	}
	</#list>
}