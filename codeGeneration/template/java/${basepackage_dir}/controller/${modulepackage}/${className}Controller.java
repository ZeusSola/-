<#assign className = table.className>
package ${basepackage}.controller.${modulepackage};

import ${basepackage}.controller.common.BaseController;
import ${basepackage}.domain.common.Page;
import ${basepackage}.domain.common.ReturnJson;
import ${basepackage}.domain.entity.${modulepackage}.${className};
import ${basepackage}.service.${modulepackage}.${className}Service;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ${table.remarks} Controller
 */
@RestController
@RequestMapping(value = "/${modulepackage}/${className}")
public class ${className}Controller extends BaseController {
    @Autowired
    ${className}Service ${table.getClassNameFirstLower()}Service;

    /**
     * 查询单条记录详情
     * @param param 需要id
     * @return
     */
    @RequestMapping("/view")
    @ResponseBody
    public ReturnJson view(@RequestBody ${className} param){
        try {
        if(StringUtils.isBlank(param.getId())){
                return ReturnJson.Faild;
            }
            ${className} returnEntity = ${table.getClassNameFirstLower()}Service.view(param.getId());
            return new ReturnJson("操作成功",returnEntity);
        }catch (Exception e){
            logger.error("操作异常信息："+ExceptionUtils.getStackTrace(e));
            return ReturnJson.Faild;
        }
    }

    /**
     * 查询集合
     * @param param
     * @return
     */
    @RequestMapping("/findList")
    @ResponseBody
    public ReturnJson findList(${className} param){
        try {
            List<${className}> resultList = ${table.getClassNameFirstLower()}Service.findList(param);
            return new ReturnJson("操作成功",resultList);
        }catch (Exception e){
            logger.error("操作异常信息："+ExceptionUtils.getStackTrace(e));
            return ReturnJson.Faild;
        }
    }

    /**
     * 分页查询集合
     * @param pageNo
     * @param pageSize
     * @param param
     * @return
     */
    @RequestMapping("/findListByPage")
    @ResponseBody
    public ReturnJson findListByPage(@RequestParam("pageNo")Integer pageNo,@RequestParam("pageSize")Integer pageSize,${className} param){
        try {
            Page<${className}> page = new Page<>(pageNo, pageSize);
            page.setParameEntity(param);
            Page<${className}> resultList = ${table.getClassNameFirstLower()}Service.findListByPage(page);
            return new ReturnJson("操作成功",resultList);
        }catch (Exception e){
            logger.error("操作异常信息："+ExceptionUtils.getStackTrace(e));
            return ReturnJson.Faild;
        }
    }

    /**
     * 新增、修改（区分：是否存在主键id）
     * @param param
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ReturnJson save(${className} param){
        try {
            int num = ${table.getClassNameFirstLower()}Service.save(param);
            if(num==0){
                return new ReturnJson("操作失败", false);
            }else{
                return ReturnJson.Success;
            }
        }catch (Exception e){
            logger.error("操作异常信息："+ ExceptionUtils.getStackTrace(e));
            return new ReturnJson("操作失败", false);
        }
    }

    /**
     * 删除数据（物理删除）
     * @param  param 需要id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ReturnJson delete(@RequestBody ${className} param){
        try {
            if(StringUtils.isBlank(param.getId())){
                return ReturnJson.Faild;
            }
            int num = ${table.getClassNameFirstLower()}Service.delete(param.getId());
            if(num==0){
                return new ReturnJson("操作失败", false);
            }else{
                return ReturnJson.Success;
            }
        }catch (Exception e){
            logger.error("操作异常信息："+ExceptionUtils.getStackTrace(e));
            return ReturnJson.Faild;
        }
    }


}


