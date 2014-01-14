package com.hyron.db.dao;

import java.util.Date;
import java.util.List;

import com.hyron.db.HibernateTestBase;
import com.hyron.db.dao.PostHibernateDao;
import com.hyron.db.dao.UserHibernateDao;
import com.hyron.db.domain.Post;
import com.hyron.db.domain.User;

public class PostTest extends HibernateTestBase{
	public PostHibernateDao getDao(){
		return (PostHibernateDao) applicationContext.getBean("postHibernateDao");
	}
	public UserHibernateDao getUserDao(){
		return (UserHibernateDao) applicationContext.getBean("userHibernateDao");
	}
   /* public void testAddPost(){
    	Post post = new Post();
    	User user = getUserDao().getUserById(3);
    	post.setUser(user);
    	post.setTitle("c第5篇帖子");
    	post.setContent("这是第一篇帖子");
    	post.setCreateTime(new Date());
    	post.setLastChange(new Date());
    	getDao().createPost(post);
    }*/
    /*public void testGetAllPost(){
    	List<Post> posts = getDao().getAllPost();
    	for(Post p : posts){
    		System.out.println(p.getTitle());
    	}
    }*/
	/*public void testGetPostByTitle(){
		Post posts = getDao().getPostByTitle("第1篇帖子");
		System.out.println(posts.getContent()+ "hahah" +posts.getUser());
	}*/
	/*public void testDeletePostByTitles(){
		String[] s = new String[2];
		s[0] = "这是第一";
		s[1] = "这是第二";
		getDao().deletePostsByTitles(s);
	}*/
	/*public void testValidateUserAuthority(){
		User user = getUserDao().getUserById(9);
		Post post = getDao().getPostByTitleAndUser("1", user);
		System.out.print(post);
	}*/
	/*public void testUpdatePost(){
		Post post = getDao().getPostByTitle("1");
		post.setTitle("111");
		post.setContent("hahha");
		post.setLastChange(new Date());
		Post mypost = getDao().updatePost(post);
		System.out.print(mypost);
	}*/
	public void testGetPostCount(){
		int n = getDao().getPostCount();
		System.out.println(n);
	}
}
