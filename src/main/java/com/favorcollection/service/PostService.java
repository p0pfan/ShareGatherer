package com.favorcollection.service;

import java.util.List;

import com.favorcollection.model.GatheredInfo;
import com.favorcollection.pojo.EmailSource;

public interface PostService {
		public void storeEmailSource(EmailSource info);
		public List<GatheredInfo> showShares();

}
