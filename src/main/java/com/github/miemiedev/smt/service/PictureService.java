package com.github.miemiedev.smt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.smt.entity.Picture;
import com.github.miemiedev.smt.repository.PictureDao;

@Service

public class PictureService  {

	@Autowired
	private PictureDao pictureDao;
	
	public boolean insertPicture(Picture pic) {
	
		 return pictureDao.insertPicture(pic);
			
		
	}
	
	public List<Picture> getpiclist(int size, String type,String latitude,String longtitude,PageBounds pagebounds){
	
		List<Picture> lst = new ArrayList<Picture>() ;
		if(type.equals("popular")) {
			
			
			lst =  pictureDao.getpiclistBypopular(pagebounds);
		}
		if(type.equals("latest")){
			lst =  pictureDao.getpiclistByLatest(pagebounds);
		}
		if(type.equals("nearby")){
			lst =  pictureDao.getpiclistByNearby(latitude,longtitude,pagebounds);
		}
	return lst;	//return pictureDao.getpiclist(size,type,latitude,longtitude,pagebounds);
	}

}
