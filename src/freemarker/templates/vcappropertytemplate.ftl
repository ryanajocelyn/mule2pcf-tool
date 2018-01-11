<#list properties?keys as propname>
<#if propname?contains("#")>
${propname}
<#else>
${propname}=${properties[propname]}
</#if>
</#list>
