package com.neuedu.dao;

import java.util.List;

import com.neuedu.pojo.User;


public interface UserDAO {
	
  List<User> findAll();
  void add(User user);
  void delete(Integer id);
  void update(User user);
  User findById(Integer id);
}
