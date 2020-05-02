package in.co.springmvc.form;

import in.co.springmvc.dto.BaseDTO;
import in.co.springmvc.dto.StudentDTO;
import in.co.springmvc.util.Util;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Contains Student form elements and their declarative input validations.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class StudentForm extends BaseForm {

	/* @Pattern(regexp = "[a-zA-Z]+") */
	/*@NotEmpty*/
	private String firstName;

	/*
	 * @Pattern(regexp = "[a-zA-Z]+", message =
	 * "Last Name is required and should be contain only alphabets.")
	 */
	/*@NotEmpty*/
	private String lastName;

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@NotNull
	private Date dob;

	/* @NotEmpty */
	/*
	 * @Pattern(regexp = "^[7-9][0-9]{9}$", message =
	 * "Mobile No is required and Should be start with 7,8,9")
	 */
	/*@NotEmpty*/
	private String mobileNo;

	/*@NotEmpty*/
	/* @Email */
	private String email;

	private String collegeName;

	@NotNull
	private Long collegeId;

	public Long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Long collegeId) {
		this.collegeId = collegeId;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/*
	 * public String getDob() { return dob; }
	 * 
	 * public void setDob(String dob) { this.dob = dob; }
	 */

	/**
	 * @return
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public BaseDTO getDto() {

		StudentDTO dto = new StudentDTO();
		dto.setId(id);
		dto.setFirstName(Util.getCapital(firstName));
		dto.setLastName(Util.getCapital(lastName));
		dto.setDob(dob);
		dto.setMobileNo(mobileNo);
		dto.setEmail(email);
		dto.setCollegeId(collegeId);
		return dto;
	}

	@Override
	public void populate(BaseDTO bDto) {
		StudentDTO dto = (StudentDTO) bDto;
		id = dto.getId();
		firstName = Util.getCapital(dto.getFirstName());
		lastName = Util.getCapital(dto.getLastName());
		dob = dto.getDob();
		mobileNo = dto.getMobileNo();
		email = dto.getEmail();
		collegeId = dto.getCollegeId();
	}

}
