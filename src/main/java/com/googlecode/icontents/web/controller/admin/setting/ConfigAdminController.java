package com.googlecode.icontents.web.controller.admin.setting;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.icontents.bean.Config;
import com.googlecode.icontents.bean.User;
import com.googlecode.icontents.service.ConfigService;
import com.googlecode.icontents.web.ActionsConstant;
import com.googlecode.icontents.web.controller.AbstractController;

@Controller
@RequestMapping(value="admin/setting")
public class ConfigAdminController extends AbstractController {

    @Resource
    private ConfigService configService;
    
    @RequestMapping(value="configAdmin.do")
    protected ModelAndView handleAdminRequestInternal(HttpServletRequest request, HttpServletResponse response,
            User adminUser, Map<String, Object> model) throws Exception {
        String action = ServletRequestUtils.getStringParameter(request, ActionsConstant.ACTION, ActionsConstant.LIST);
        if(ActionsConstant.UPDATE.equalsIgnoreCase(action)){
            Enumeration<?> enumer = request.getParameterNames();
            while(enumer.hasMoreElements()) {
                String key = (String)enumer.nextElement();
                Config config = new Config(key, ServletRequestUtils.getStringParameter(request, key, StringUtils.EMPTY));
                logger.error("key:" + config.getKey()+ "  value:" + config.getValue());
                configService.update(config);
            }
        }
        
        List<Config> configList = configService.selectAll();
        Map<String,String> configMap = new HashMap<String, String>();
        
        for(Config config:configList){
            if(config.getKey().startsWith("config_"))
                configMap.put(config.getKey(), config.getValue());
        }
        model.put("configList", configList);
        model.put("configMap", configMap);
        return new ModelAndView("admin/setting/configAdmin",model);
    }
}
