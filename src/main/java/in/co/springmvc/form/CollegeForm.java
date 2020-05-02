package in.co.springmvc.form;

import in.co.springmvc.dto.BaseDTO;
import in.co.springmvc.dto.CollegeDTO;
import in.co.springmvc.util.Util;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Contains College form elements and their declarative input validations.
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class CollegeForm extends BaseForm {

	/* @NotEmpty */
	private String name;

	/* @NotEmpty */
	private String address;

	/* @NotEmpty */
	private String state;

	/* @NotEmpty */
	private String city;

	/* @Pattern(regexp = "^[7-9][0-9]{9}$") */
	/* @NotEmpty */
	private String mobileNo;

	private String message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/*
	 * public String getPhoneNo() { return phoneNo; }
	 * 
	 * public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
	 */

	public String getMessage() {
		return message;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Populate dto from form
	 */
	@Override
	public BaseDTO getDto() {
		CollegeDTO dto = new CollegeDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setAddress(address);
		dto.setCity(city);
		dto.setState(state);
		dto.setPhoneNo(mobileNo);
		return dto;
	}

	/**
	 * Populate from from dto
	 */
	@Override
	public void populate(BaseDTO bDto) {
		CollegeDTO dto = (CollegeDTO) bDto;
		id = dto.getId();
		name = dto.getName();
		address = dto.getAddress();
		city = dto.getCity();
		state = dto.getState();
		mobileNo = dto.getPhoneNo();

		super.populate(bDto);
	}

}
