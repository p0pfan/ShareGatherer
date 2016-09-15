package com.favorcollection.service.Impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favorcollection.pojo.UserInfo;
import com.favorcollection.service.UserService;
import com.favorcollection.service.WeiboToken;

import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
	private static Logger log = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private WeiboToken wt;
	
	@Override
	public UserInfo showUser(AccessToken access) {
		UserInfo iuser =new UserInfo();
		AccessToken token = wt.getAccessToken("c_c1227@163.com");
		String access_token = token.getAccessToken();
		log.info(access_token);
		String uid = token.getUid();
		User user = null;
		Users um = new Users(access_token);
		try {
			user = um.showUserById(uid);
			log.info(user.toString());
		} catch (WeiboException e) {
			log.info(e);
		}
		iuser.setDescription(user.getDescription());
		iuser.setFollowersCount(user.getFollowersCount());
		iuser.setFriendsCount(user.getFriendsCount());
		iuser.setGender(user.getGender());
		iuser.setId(user.getId());
		iuser.setName(user.getName());
		iuser.setProfileImageUrl(user.getProfileImageUrl());
		iuser.setScreenName(user.getScreenName());
		iuser.setStatusesCount(user.getStatusesCount());
		iuser.setUrl(user.getUrl());
		iuser.setUserDomain(user.getUserDomain());
		iuser.setAvatarLarge(user.getavatarLarge());
		
		return iuser;
	}

}
