<#assign className = table.className>
package ${basepackage}.domain.vo.${modulepackage};

import ${basepackage}.domain.common.BaseEntity;

/**
 * ${table.remarks} Vo
 */
public class ${className}Vo extends BaseEntity<${className}Vo> {

<@generateFields/>

<@generateGetSetProperties/>

}

<#macro generateFields>
    <#list table.columns as column>
    <#if !column.isRemoveField(column.columnNameLower)>
    private ${column.getSimpleJavaType()} ${column.columnNameLower};   //${column.columnAlias!}
    </#if>
    </#list>

</#macro>

<#macro generateGetSetProperties>
<#list table.columns as column>
<#if !column.isRemoveField(column.columnNameLower)>
    public ${column.getSimpleJavaType()} get${column.columnName}() {
        return this.${column.columnNameLower};
    }

    public void set${column.columnName}(${column.getSimpleJavaType()} value) {
        this.${column.columnNameLower} = value;
    }

</#if>
</#list>

</#macro>


