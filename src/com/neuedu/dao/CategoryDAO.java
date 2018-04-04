package com.neuedu.dao;

import java.util.List;

import com.neuedu.pojo.Category;


public interface CategoryDAO {
	List<Category> FindAll();
	void add(Category category);
    void delete(Integer id,Integer pid);
    void update(Category c);
    Category findById(Integer id);
    void add(String name,String descr);
}
