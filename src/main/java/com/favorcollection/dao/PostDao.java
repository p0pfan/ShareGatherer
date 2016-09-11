package com.favorcollection.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.favorcollection.model.GatheredInfo;

@Repository
public interface PostDao extends MongoRepository<GatheredInfo,String> {
	
	

}
