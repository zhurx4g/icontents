package com.googlecode.icontents;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.googlecode.icontents.bean.Article;
import com.googlecode.icontents.service.ArticleService;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
		
//		UserService userService = context.getBean(UserService.class);
//		
//		User user = userService.getUserByUserName("root");
//		
//		System.out.println(user);
//		
//		System.out.println(DigestUtils.md5Hex(DigestUtils.md5Hex("root")));
		
		ArticleService articleService = context.getBean(ArticleService.class);
		
		Article article = articleService.getObjectById(1);
//		article.setId(-1);
//		System.out.println(article);
//		article.setTitle("-eeeeeeeeeeeeeeeeeeee--->");
//		List<ArticleComponent> cccList = new ArrayList<ArticleComponent>();
//		cccList.add(new NavComponent());
//		article.setBodyComponentList(cccList);
//		articleService.update(article);
		List<Article> articleList = articleService.select(0, 10);
////		
//		System.out.println(articleList.size());
		for(Article art:articleList){
		    System.out.println("[新闻]" + art.getTitle());
		}
//		System.out.println(articleService.getArticleById(1360932585));
		//NumberUtils.toInt("123");
	}

}
