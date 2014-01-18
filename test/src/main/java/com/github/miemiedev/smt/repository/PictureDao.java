package com.github.miemiedev.smt.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.smt.entity.Picture;

@MyBatisRepository
public interface PictureDao {
  boolean insertPicture(Picture pic);
  List<Picture> getpiclist(@Param("size")String  size,@Param("type")String type,@Param("latitude")String latitude,@Param("longtitude")String longtitude,PageBounds pageBounds);

  List<Picture> getpiclistBypopular(PageBounds pageBounds);
  List<Picture> getpiclistByLatest(PageBounds pageBounds);
  List<Picture> getpiclistByNearby(@Param("latitude")String latitude,@Param("longtitude")String longtitude,PageBounds pageBounds);
}
