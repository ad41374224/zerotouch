package com.zerotouch.EntityScan.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "Address")
public class UserAddress extends SuperBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_SEQ")
	@SequenceGenerator(name = "ADDRESS_SEQ", sequenceName = "ADDRESS_ID_SEQ", allocationSize = 1, initialValue = 10001)
	private Integer id;
	
	@Column(name = "house_building_no", nullable = false)
	private String houseNo;
	
	@Column(name = "block_street_no")
	private String streetBlockNo;
	
	@Column(name = "city_name", nullable = false)
	private String city;
	
	@Column(name = "state_name", nullable = false)
	private String state;
	
	@Column(name = "Country_name", nullable = false)
	private String country;
	
	@Column(name = "created_on", nullable = false)
	private Date createdOn;
	
	@Column(name = "updated_on")
	private Date updatedOn;
	
	public UserAddress() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getStreetBlockNo() {
		return streetBlockNo;
	}

	public void setStreetBlockNo(String streetBlockNo) {
		this.streetBlockNo = streetBlockNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

}
