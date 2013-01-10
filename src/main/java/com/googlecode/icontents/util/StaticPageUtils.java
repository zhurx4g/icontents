package com.googlecode.icontents.util;

import java.io.File;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.googlecode.icontents.bean.Article;
import com.googlecode.icontents.bean.Catalog;
import com.googlecode.icontents.service.ArticleService;
import com.googlecode.icontents.service.CatalogService;


public class StaticPageUtils {
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		
		VelocityEngine ve = new VelocityEngine();
		ve.init("src/main/conf/velocity.properties");
//		ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "E:\\home\\workspace\\tinyblog_i\\src\\main\\velocity\\"); 
		
		CatalogService catalogService = context.getBean(CatalogService.class);
		List<Catalog> catalogList = catalogService.select(0, Integer.MAX_VALUE);
		
		ArticleService articleService = context.getBean(ArticleService.class);
		///dddd
		List<Article> articleList = articleService.select(0, Integer.MAX_VALUE);
		
		for(Article article:articleList){
			Template template = ve.getTemplate("templates\\index.vm");
			VelocityContext velocityContext = new VelocityContext();
	
			velocityContext.put("skin", "ibm");
			velocityContext.put("contextPath", "");
			velocityContext.put("article", articleService.getObjectById(article.getId(), true));
			velocityContext.put("catalogList", catalogList);
			StringWriter writer = new StringWriter();
			template.merge(velocityContext, writer);
	
			System.out.println("E:\\home\\workspace\\tinyblog_i\\src\\main\\webapp\\"+"article\\"+article.getId()+".html");
//			System.out.println(writer.toString());
			FileUtils.writeStringToFile(new File("E:\\home\\workspace\\tinyblog_i\\src\\main\\webapp\\"+"article\\"+article.getId()+".html"), writer.toString(), "utf-8");
		}
	}

}
