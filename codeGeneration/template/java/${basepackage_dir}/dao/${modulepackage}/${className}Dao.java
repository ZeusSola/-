<#assign className = table.className>
package ${basepackage}.dao.${modulepackage};

import ${basepackage}.dao.common.BaseDao;
import ${basepackage}.domain.entity.${modulepackage}.${className};
import org.springframework.stereotype.Component;

/**
 * ${table.remarks} Dao
 */
@Component
public interface ${className}Dao extends BaseDao<${className}> {


}
