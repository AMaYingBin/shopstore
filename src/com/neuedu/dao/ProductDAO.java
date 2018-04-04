package com.neuedu.dao;

import java.util.List;

import com.neuedu.pojo.Admin;
import com.neuedu.pojo.Product;

public interface ProductDAO {
	List<Product>  findAll();
	void add(Product p);
	Product findById(Integer id);
	void delete(Integer id);
	void update(Product product);

}
