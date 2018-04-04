package com.neuedu.test;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.neuedu.dao.impl.CategoryDAOImpl;
import com.neuedu.dao.impl.ProductDAOImpl;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.util.DBUtil;

public class TestProductDao {
	public static void main(String[] args) {

		/*
		 * CategoryDAOImpl categoryDAOImpl=new CategoryDAOImpl(); Category
		 * category=categoryDAOImpl.findById(42); productdao.add(new Product("�����˶���",
		 * "�����˶���",170, 169, null, category));
		 */
		/*
		 * for (Product product : products) { System.out.println(product); }
		 */
		ProductDAOImpl productdao = new ProductDAOImpl();
		List<Product> products = productdao.findAll();
		Random r=new Random();
		CategoryDAOImpl cdao=new CategoryDAOImpl();
		List<Category> cs = cdao.FindAll();
		Category[] arrayC=new Category[cs.size()];
		for(int i=0;i<cs.size();i++) {
			arrayC[i]=cs.get(i);
		}
		Connection conn=DBUtil.getConnection();
		for(int i=1;i<=1000;i++){
			productdao.add(conn,new Product(
					"������Ʒ"+i,
					"������Ʒ"+i+"����",
					r.nextInt(5000)-r.nextInt(10),
					ss("2016-01-01", "2018-03-30"),
					arrayC[r.nextInt(cs.size())])
			);
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	}
	 private static Date randomDate(String beginDate, String endDate) {  
	        try {  
	            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	            Date start = format.parse(beginDate);// ���쿪ʼ����  
	            Date end = format.parse(endDate);// �����������  
	            // getTime()��ʾ������ 1970 �� 1 �� 1 �� 00:00:00 GMT ������ Date �����ʾ�ĺ�������  
	            if (start.getTime() >= end.getTime()) {  
	                return null;  
	            }  
	            long date = random(start.getTime(), end.getTime());  
	  
	            return new Date(date);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return null;  
	    }  
	  
	    private static long random(long begin, long end) {  
	        long rtn = begin + (long) (Math.random() * (end - begin));  
	        // ������ص��ǿ�ʼʱ��ͽ���ʱ�䣬��ݹ���ñ������������ֵ  
	        if (rtn == begin || rtn == end) {  
	            return random(begin, end);  
	        }  
	        return rtn;  
	    } 
	    public static Timestamp ss(String beginDate, String endDate) {
	    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    	Timestamp ts = new Timestamp(System.currentTimeMillis());
	    	ts = Timestamp.valueOf(format.format(randomDate("2016-01-01 00:00:00", "2018-03-30 00:00:00")));
			return ts;
	    	
	    }
	    
}
