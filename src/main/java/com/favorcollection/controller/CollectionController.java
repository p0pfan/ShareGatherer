package com.favorcollection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.favorcollection.pojo.EmailSource;
import com.favorcollection.service.Impl.PostServiceImpl;

@RestController
@RequestMapping("/source")
public class CollectionController {
	@Autowired
	private PostServiceImpl postService;
	
	@RequestMapping(value = "/share", method = RequestMethod.POST)
	@ResponseBody
	public void shareInfo(@RequestBody EmailSource shared){
		postService.storeEmailSource(shared);
	}
	

}
