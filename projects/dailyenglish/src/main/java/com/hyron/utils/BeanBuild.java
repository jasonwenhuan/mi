package com.hyron.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.hyron.db.domain.Appoint;
import com.hyron.db.domain.Client;
import com.hyron.db.domain.News;
import com.hyron.db.domain.Post;
import com.hyron.db.domain.Tourgroup;
import com.hyron.db.domain.User;
import com.hyron.javabean.AppointBean;
import com.hyron.javabean.ClientBean;
import com.hyron.javabean.NewsBean;
import com.hyron.javabean.PostBean;
import com.hyron.javabean.TourgroupBean;
import com.hyron.javabean.UserBean;

public class BeanBuild {
	public static List<UserBean> buildUserBean(List<User> users){
		List<UserBean> beans = new ArrayList<UserBean>();
		for (User user : users) {
	   		 UserBean ub = new UserBean();
	   		 ub.setNickName(user.getNickname());
	   		 if(user.getUsername() != null){
	   			 ub.setUserName(user.getUsername());
	   		 }
	   		 ub.setPassword(user.getPassword());
	   		 ub.setRole(user.getRole().getRoleid());
	   		 ub.setEmail(user.getEmail());
	   		 ub.setSex(user.getSex());
	   		 ub.setRegisterDate(user.getRegisterdate());
	   		 beans.add(ub);
	   	 }
		return beans;
	}
	public static List<PostBean> buildPostBean(List<Post> posts){
		List<PostBean> postBeans = new ArrayList<PostBean>();
		for(Post p : posts){
			PostBean pb = new PostBean();
			pb.setPostId(p.getPostId());
			pb.setAuthor(p.getUser().getNickname());
			if(p.getTitle() != null){
				pb.setTitle(p.getTitle());
			}
			if(p.getContent() != null){
				pb.setContent(p.getContent());
			}
			if(p.getCreateTime() != null){
				pb.setCreatetime(p.getCreateTime());
			}
			if(p.getLastChange() != null){
				pb.setLastchange(p.getLastChange());
			}
			postBeans.add(pb);
		}
		return postBeans;
	}
	public static List<NewsBean> buildNewsBean(List<News> news){
		List<NewsBean> newsList = new ArrayList<NewsBean>() ;
			for(News newsEach : news){
				NewsBean newsBean = new NewsBean();
				newsBean.setId(newsEach.getId());
				newsBean.setNewsAuthor(newsEach.getAuthor());
				newsBean.setNewsContent(newsEach.getContent());
				newsBean.setNewsTitle(newsEach.getTitle());
				newsBean.setNewsType(newsEach.getType());
				newsBean.setNewsPublishDate(newsEach.getPublishDate());
				newsList.add(newsBean);
			}
			return newsList;
	}
	
	public static Tourgroup buildTourgroup(TourgroupBean tb){
		Tourgroup tr = new Tourgroup();
		if(tb.getDetail() != null){
			tr.setDetail(tb.getDetail());
		}
		if(tb.getName() != null){
			tr.setName(tb.getName());
		}
		if(tb.getId() != null){
			tr.setId(tb.getId());
		}
		return tr;
	}
	
	public static List<TourgroupBean> buildTourgroupBeans(List<Tourgroup> tgs){
		List<TourgroupBean> tbs = new ArrayList<TourgroupBean>();
		for(int i=0;i<tgs.size();i++){
			TourgroupBean tb = new TourgroupBean();
			Tourgroup tr = tgs.get(i);
			if(tr.getCreator() != null){
				tb.setCreator(tr.getCreator().getNickname());
			}
			if(tr.getDetail() != null){
				tb.setDetail(tr.getDetail());
			}
			if(tr.getName() != null){
				tb.setName(tr.getName());
			}
			if(tr.getId() != null){
				tb.setId(tr.getId());
			}
			tbs.add(tb);
		}
		return tbs;
	}
	
	public static TourgroupBean buildTourgroupBean(Tourgroup tr){
		TourgroupBean tb = new TourgroupBean();
		if(tr.getId() != null){
			tb.setId(tr.getId());
		}
		if(tr.getCreator() != null){
			tb.setCreator(tr.getCreator().getNickname());
		}
		if(tr.getDetail() != null){
			tb.setDetail(tr.getDetail());
		}
		if(tr.getName() != null){
			tb.setName(tr.getName());
		}
		return tb;
	}
	
	public static Client buildClient(ClientBean tb){
		Client tr = new Client();
		if(tb.getAge() != null){
			tr.setAge(tb.getAge());
		}
		if(tb.getEmail() != null){
			tr.setEmail(tb.getEmail());
		}
		if(tb.getId() != null){
			tr.setId(tb.getId());
		}
		if(tb.getName() != null){
			tr.setName(tb.getName());
		}
		if(tb.getRemark() != null){
			tr.setRemark(tb.getRemark());
		}
		return tr;
	}
	
	public static List<ClientBean> buildClientBeans(List<Client> tgs){
		List<ClientBean> tbs = new ArrayList<ClientBean>();
		for(int i=0;i<tgs.size();i++){
			ClientBean tb = new ClientBean();
			Client tr = tgs.get(i);
			if(tr.getAge() != null){
				tb.setAge(tr.getAge());
			}
			if(tr.getCreator() != null){
				tb.setCreator(tr.getCreator().getNickname());
			}
			if(tr.getEmail() != null){
				tb.setEmail(tr.getEmail());
			}
			if(tr.getId() != null){
				tb.setId(tr.getId());
			}
			if(tr.getName() != null){
				tb.setName(tr.getName());
			}
			if(tr.getRemark() != null){
				tb.setRemark(tr.getRemark());
			}
			tbs.add(tb);
		}
		return tbs;
	}
	
	public static ClientBean buildClientBean(Client tr){
		ClientBean tb = new ClientBean();
		if(tr.getAge() != null){
			tb.setAge(tr.getAge());
		}
		if(tr.getCreator() != null){
			tb.setCreator(tr.getCreator().getNickname());
		}
		if(tr.getEmail() != null){
			tb.setEmail(tr.getEmail());
		}
		if(tr.getId() != null){
			tb.setId(tr.getId());
		}
		if(tr.getName() != null){
			tb.setName(tr.getName());
		}
		if(tr.getRemark() != null){
			tb.setRemark(tr.getRemark());
		}
		return tb;
	} 
	
	
	public static Appoint buildAppoint(AppointBean tb){
		Appoint tr = new Appoint();
		if(tb.getCreateDate() != null){
			tr.setCreateDate(tb.getCreateDate());
		}else{
			tr.setCreateDate(new Date());
		}
		if(tb.getDetail() != null){
			tr.setDetail(tb.getDetail());
		}
		if(tb.getId() != null){
			tr.setId(tb.getId());
		}
		if(tb.getModifiDate() != null){
			tr.setModifiDate(tb.getModifiDate());
		}
		if(tb.getName() != null){
			tr.setName(tb.getName());
		}
		return tr;
	}
	
	public static List<AppointBean> buildAppointBeans(List<Appoint> tgs){
		List<AppointBean> tbs = new ArrayList<AppointBean>();
		for(int i=0;i<tgs.size();i++){
			AppointBean tb = new AppointBean();
			Appoint tr = tgs.get(i);
			if(tr.getClient() != null){
				tb.setClient(tr.getClient().getName());
			}
			if(tr.getCreateDate() != null){
				tb.setCreateDate(tr.getCreateDate());
			}
			if(tr.getCreator() != null){
				tb.setCreator(tr.getCreator().getNickname());
			}
			if(tr.getDetail() != null){
				tb.setDetail(tr.getDetail());
			}
			if(tr.getId() != null){
				tb.setId(tr.getId());
			}
			if(tr.getModifiDate() != null){
				tb.setModifiDate(tr.getModifiDate());
			}
			if(tr.getName() != null){
				tb.setName(tr.getName());
			}
			if(tr.getTourgroup() != null){
				tb.setTourgroup(tr.getTourgroup().getName());
			}
			tbs.add(tb);
		}
		return tbs;
	}
	
	public static AppointBean buildAppointBean(Appoint tr){
		if(tr == null){
			return null;
		}
		AppointBean tb = new AppointBean();
		if(tr.getClient() != null){
			tb.setClient(tr.getClient().getName());
		}
		if(tr.getCreateDate() != null){
			tb.setCreateDate(tr.getCreateDate());
		}
		if(tr.getCreator() != null){
			tb.setCreator(tr.getCreator().getNickname());
		}
		if(tr.getDetail() != null){
			tb.setDetail(tr.getDetail());
		}
		if(tr.getId() != null){
			tb.setId(tr.getId());
		}
		if(tr.getModifiDate() != null){
			tb.setModifiDate(tr.getModifiDate());
		}
		if(tr.getName() != null){
			tb.setName(tr.getName());
		}
		if(tr.getTourgroup() != null){
			tb.setTourgroup(tr.getTourgroup().getName());
		}
		return tb;
	}
}
