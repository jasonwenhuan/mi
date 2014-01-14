package com.hyron.presentation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;

import com.hyron.db.domain.Post;
import com.hyron.db.domain.User;
import com.hyron.facade.PostFacade;
import com.hyron.facade.UserFacade;
import com.hyron.javabean.PostBean;
import com.hyron.presentation.utils.State;
import com.hyron.utils.BeanBuild;

/**
 * @author wenhuan
 *
 */
public class PostPresManager{
	private PostFacade postFacade;
	private UserFacade userFacade;
    public List<PostBean> getAllPost(State state){
    	List<Post> posts = postFacade.getAllPost(state);
    	if(posts != null){
    		return BeanBuild.buildPostBean(posts);
    	}else{
    		return null;
    	}
    }
    public PostBean getPostByTitle(String title){
    	Post post= postFacade.getPostByTitle(title);
    	List<Post> posts = new ArrayList<Post>();
    	posts.add(post);
    	return BeanBuild.buildPostBean(posts).get(0);
    }
    public boolean createPost(PostBean postbean){
    	User user = getLoginUser();
    	Post post = new Post();
    	post.setTitle(postbean.getTitle());
    	post.setContent(postbean.getContent());
    	post.setUser(user);
    	post.setCreateTime(new Date());
    	Post newPost= postFacade.createPost(post);
    	if(newPost != null){
    		return true;
    	}else{
    		return false;
    	}
    }
    public boolean validateAuthority(String title){
    	User user = getLoginUser();
    	if(user.getRole().getRolename().equalsIgnoreCase("Administrator") || 
    			user.getRole().getRolename().equalsIgnoreCase("System Administrator")){
    		return true;
    	}else{
    		Post post = postFacade.getPostByTitleAndUser(title,user);
    		if(post != null && post.getCreateTime() != null){
    			return true;
    		}else{
    			return false;
    		}
    	}
    }
    public boolean updatePost(String title,String content){
    	Post post = postFacade.getPostByTitle(title);;
    	post.setTitle(title);
    	post.setContent(content);
    	post.setLastChange(new Date());
    	Post myPost = postFacade.updatePost(post);
    	if(myPost != null){
    		return true;
    	}else{
    		return false;
    	}
    }
    public void deletePostsByTitles(String[] titles){
    	postFacade.deletePostsByTitles(titles);
    }
    public int getPostCount(){
    	int postCount = postFacade.getPostCount();
    	return postCount;
    }
	protected User getLoginUser() {
		HttpSession session = WebContextFactory.get().getSession();
		String userName = (String) session.getAttribute("username");
		return userFacade.getUserByUserName(userName);
	}
	public PostFacade getPostFacade() {
		return postFacade;
	}
	public void setPostFacade(PostFacade postFacade) {
		this.postFacade = postFacade;
	}
	public UserFacade getUserFacade() {
		return userFacade;
	}
	public void setUserFacade(UserFacade userFacade) {
		this.userFacade = userFacade;
	}
    
}
