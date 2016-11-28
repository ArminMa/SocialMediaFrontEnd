package org.kth.controller.beans;

import org.kth.controller.handlers.UserHandler;
import org.kth.model.pojo.PostPojo;
import org.kth.model.pojo.UserPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class ViewLogBean {
	public static final Logger logger1 = LoggerFactory.getLogger( UserHandler.class );
	private List<PostPojo> posts;

	public ViewLogBean() {
		this.posts = new ArrayList<>();
	}

	public List<PostPojo> getPosts() {
		return posts;
	}

	public void setPosts(List<PostPojo> posts) {
		this.posts = posts;
	}

	public void getLog(UserPojo user){
		posts.clear();
		List<PostPojo> postPojos = UserHandler.getLog(user.getUsername());
		if(postPojos != null){
			posts.addAll(postPojos);
		}
	}
}
