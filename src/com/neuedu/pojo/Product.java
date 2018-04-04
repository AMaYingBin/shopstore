package com.neuedu.pojo;

import java.sql.Timestamp;

public class Product {
    private Integer id;
    private String name;
    private String descr;
    private double normalprice;
    private double memberprice;
    private Timestamp pdate;
    private Category category;
	public Product() {
		super();
	}
	public Product(Integer id, String name, String descr, double normalprice, double memberprice, Timestamp pdate,
			Category category) {
		super();
		this.id = id;
		this.name = name;
		this.descr = descr;
		this.normalprice = normalprice;
		this.memberprice = memberprice;
		this.pdate = pdate;
		this.category = category;
	}
	public Product(String name, String descr, double normalprice,  Timestamp pdate,
			Category category) {
		super();
		this.name = name;
		this.descr = descr;
		this.normalprice = normalprice;
		this.memberprice = Math.round((normalprice*0.8)*100)/100 ;
		this.pdate = pdate;
		this.category = category;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((descr == null) ? 0 : descr.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(memberprice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		temp = Double.doubleToLongBits(normalprice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((pdate == null) ? 0 : pdate.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (descr == null) {
			if (other.descr != null)
				return false;
		} else if (!descr.equals(other.descr))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(memberprice) != Double.doubleToLongBits(other.memberprice))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(normalprice) != Double.doubleToLongBits(other.normalprice))
			return false;
		if (pdate == null) {
			if (other.pdate != null)
				return false;
		} else if (!pdate.equals(other.pdate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", descr=" + descr + ", normalprice=" + normalprice
				+ ", memberprice=" + memberprice + ", pdate=" + pdate + ", category=" + category + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public double getNormalprice() {
		return normalprice;
	}
	public void setNormalprice(double normalprice) {
		this.normalprice = normalprice;
	}
	public double getMemberprice() {
		return memberprice;
	}
	public void setMemberprice(double memberprice) {
		this.memberprice = memberprice;
	}
	public Timestamp getPdate() {
		return pdate;
	}
	public void setPdate(Timestamp pdate) {
		this.pdate = pdate;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
    
}
