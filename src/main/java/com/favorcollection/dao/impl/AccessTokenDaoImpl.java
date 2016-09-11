package com.favorcollection.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.favorcollection.dao.AccessTokenDao;
import com.favorcollection.model.AccessTokens;

@Repository("accessTokenImpl")
public class AccessTokenDaoImpl implements AccessTokenDao {
	
	 @Resource  
	 private MongoTemplate mongoTemplate; 

	@Override
	public void insert(AccessTokens object, String collectionName) {
		mongoTemplate.insert(object, collectionName); 
	}

	@Override
	public AccessTokens findOne(Map<String, Object> params, String collectionName) {
		return mongoTemplate.findOne(new Query(Criteria.where("admin").is(params.get("admin"))), AccessTokens.class,collectionName); 
	}

	@Override
	public List<AccessTokens> findAll(Map<String, Object> params, String collectionName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Map<String, Object> params, String collectionName) {
        mongoTemplate.upsert(new Query(Criteria.where("admin").is(params.get("admin"))), new Update().set("AccessToken", params.get("AccessToken")), AccessTokens.class,collectionName);  
        mongoTemplate.upsert(new Query(Criteria.where("admin").is(params.get("admin"))), new Update().set("CeateTime", params.get("CreateTime")), AccessTokens.class,collectionName);
	}

	@Override
	public void createCollection(String collectionName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Map<String, Object> params, String collectionName) {
		// TODO Auto-generated method stub
		
	}

}
