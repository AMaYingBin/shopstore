package com.neuedu.dao;

import java.util.List;

import com.neuedu.pojo.Admin;


public interface AdminDAO {
   List<Admin> findAll();
   void add(Admin admin);
   void delete(Integer id);
   void update(Admin admin);
   Admin findById(Integer id);
}
