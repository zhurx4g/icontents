package com.googlecode.icontents;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.googlecode.icontents.bean.Article;
import com.googlecode.icontents.service.ArticleService;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
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
		System.out.println("------------------------------------------------->");

		System.out.println(articleService.insert(article));
		System.out.println("newId:"+article.getId());
//		UserService userService = context.getBean(UserService.class);
//		
//		System.out.println(userService.select(0, 20));
		
//		Article art = articleService.getArticleById(709941434);
		
//		System.out.println(art);
//		System.out.println(art.getHeaderComponentList());
//		System.out.println(art.getLeftComponentList());
//		List<Article> articleList = articleService.select(0, 10);
////		
//		System.out.println(articleList.size());
//		for(Article art:articleList){
//		    System.out.println(art);
////		    art.setTitle(art.getTitle()+"xxxx");
//		    articleService.update(art);
//		}
//		System.out.println(articleService.getArticleById(1360932585));
		//NumberUtils.toInt("123");
	}

}
