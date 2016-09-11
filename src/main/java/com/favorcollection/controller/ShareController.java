package com.favorcollection.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.favorcollection.model.GatheredInfo;
import com.favorcollection.service.PostService;

@RestController
@RequestMapping("/share")
public class ShareController {
	@Autowired
	private PostService postService;
	
	@RequestMapping(value = "/all" , method = RequestMethod.GET)
	public @ResponseBody List<GatheredInfo> showShares(){
		return postService.showShares();
	}

}
