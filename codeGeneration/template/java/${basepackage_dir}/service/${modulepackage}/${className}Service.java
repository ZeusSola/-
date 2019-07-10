<#assign className = table.className>
package ${basepackage}.service.${modulepackage};

import ${basepackage}.dao.${modulepackage}.${className}Dao;
import ${basepackage}.domain.entity.${modulepackage}.${className};
import ${basepackage}.service.common.BaseService;
import org.springframework.stereotype.Service;

/**
 * ${table.remarks} Service
 */
@Service
public class ${className}Service extends BaseService<${className}Dao, ${className}> {


}