package com.github.miemiedev.smt.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.smt.entity.User;
import com.github.miemiedev.smt.repository.UserDao;

@Service
@Transactional(readOnly = false)
@ActiveProfiles("development-web") 
public class AuthService {

    @Autowired
    private UserDao userDao;


    public User getUserById(Long id){
        return userDao.get(id);
    }
    public boolean add(User user){
        return userDao.add(user);
    }
    public List<User> queryByDeptCode(String deptCode, PageBounds pageBounds) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return userDao.queryByDeptCode(deptCode, sdf.parse("2010-03-02"), pageBounds);
    }

    public List<Map<String, Object>> search(Map params ,PageBounds pageBounds){
        return userDao.search(params, pageBounds);
    }

    public List<Map<String, Object>> find(PageBounds pageBounds){
        return userDao.find(pageBounds);
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
