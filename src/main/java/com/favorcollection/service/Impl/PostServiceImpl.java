package com.favorcollection.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favorcollection.dao.PostDao;
import com.favorcollection.model.GatheredInfo;
import com.favorcollection.pojo.EmailSource;
import com.favorcollection.service.PostService;

@Service("postService")
public class PostServiceImpl implements PostService {
	private static final int FROM_EMAIL = 1;
	
	@Autowired
	private PostDao postDao;
	
	@Override
	public void storeEmailSource(EmailSource info) {
		GatheredInfo ginfo = new GatheredInfo();
		Date date = new Date();
		ginfo.setContent(info.getContent());
		ginfo.setPoster(info.getFrom());
		ginfo.setRecieveTime(date);
		ginfo.setSourceType(FROM_EMAIL);
		List<String> labels = new ArrayList<>();
		String [] tempLabels = info.getSubject().split(" ");
		
		for(int i = 0; i<tempLabels.length; i++){
			String temp = tempLabels[i];
			if(temp != " "){
				labels.add(temp);
			}
				
		}
		ginfo.setLabels(labels);
		
		postDao.insert(ginfo);
	}

	@Override
	public List<GatheredInfo> showShares() {
		return postDao.findAll();
	}
	
	

}
