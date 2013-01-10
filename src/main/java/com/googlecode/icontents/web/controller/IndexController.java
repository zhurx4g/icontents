package com.googlecode.icontents.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.icontents.Constants;
import com.googlecode.icontents.bean.Article;
import com.googlecode.icontents.bean.Config;
import com.googlecode.icontents.service.ArticleService;
import com.googlecode.icontents.service.CatalogService;
import com.googlecode.icontents.service.ConfigService;
import com.googlecode.icontents.web.module.impl.LoginModule;
import com.googlecode.module.annotation.Modules;
import com.googlecode.module.impl.DefaultViewModule;
import com.googlecode.module.web.controller.AbstractController;

/**
 * 前端页面展示入口
 *
 * @author zhurx
 */
@Controller
public class IndexController extends AbstractController {
    
    private Log logger = LogFactory.getLog(getClass());
    
	@Resource
	private ArticleService articleService;
	
	@Resource
	private CatalogService catalogService;
	
	@Resource
	private ConfigService configService;
	
    @RequestMapping("/login.do")
    @Modules(value = { LoginModule.class }, view = DefaultViewModule.class)
    public ModelAndView login(HttpServletRequest r, HttpServletResponse p, ModelAndView m) {
        return $(r, p, m);
    }
    
	@ModelAttribute("data") 
	@RequestMapping(value="/index.do")
	public ModelAndView index(HttpServletRequest request, @RequestParam("articleId") long articleId, @RequestParam(value = "articleAlias", required=false) String articleAlias, ModelMap model) throws Exception {
	    model.addAttribute("contextPath", request.getContextPath());
	    
	    List<Config> configList = configService.getConfig();
	    Map<String,String> configMap = new HashMap<String, String>();
        for(Config config:configList){
            configMap.put(config.getKey(), config.getValue());
        }
        
	    Article article = null;
	    if(StringUtils.isNotBlank(articleAlias)){
	        article = articleService.getObjectByAlias(articleAlias,true);
	    }else if(articleId!=Constants.ERROR){
	        article = articleService.getObjectById(articleId,true);
	    }
	    
	    if(articleId==Constants.ERROR||article == null){
	        articleId = NumberUtils.toLong(configMap.get(Config.INDEX), -1);
	        article = articleService.getObjectById(articleId,true);
	    }
	    
	    logger.error("articleId:" + articleId);
	    logger.error("article:" + article.getName());
	    model.addAttribute("article", article);
	    model.addAttribute("catalogList", catalogService.select(0, Integer.MAX_VALUE));
        model.addAllAttributes(configMap);
	    return new ModelAndView("index", model);
	}
}
