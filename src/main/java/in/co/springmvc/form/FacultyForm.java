package in.co.springmvc.form;

import in.co.springmvc.dto.BaseDTO;
import in.co.springmvc.dto.FacultyDTO;
import in.co.springmvc.util.Util;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Contains Faculty form elements and their declarative input validations.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class FacultyForm extends BaseForm {

	/**
	 * First Name of Faculty
	 */
	/*@NotEmpty*/
	private String firstName;
	/**
	 * Last Name of Faculty
	 */
	/*@NotEmpty*/
	private String lastName;
	/**
	 * Date of Birth of Faculty
	 */
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@NotNull
	private Date dob;
	/**
	 * Qualification of Faculty
	 */
	/*
	 * @NotEmpty private String qualification;
	 */
	/**
	 * Email of Faculty
	 */
	/*@NotEmpty*/
	private String email;
	/**
	 * MobileNo of Faculty
	 */
	/*@NotEmpty*/
	private String mobileNo;
	/**
	 * Address of Faculty
	 */
	/*
	 * @NotEmpty private String address;
	 */
	/**
	 * Primary Subject of Faculty
	 */
	@NotEmpty
	private String primarySubject;
	/**
	 * Secondary Subject of Faculty
	 */
	@NotEmpty
	private String secondarySubject;
	/**
	 * CollegeId of Faculty
	 */
	@NotNull
	private Long collegeId;
	/**
	 * College Name of Faculty
	 */
	private String collegeName;
	/**
	 * CourseId of Faculty
	 */
	@NotNull
	private Long courseId = 0L;
	/**
	 * Course Name of Faculty
	 */
	private String courseName;

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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	/*
	 * public String getQualification() { return qualification; }
	 * 
	 * public void setQualification(String qualification) { this.qualification =
	 * qualification; }
	 */

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/*
	 * public String getAddress() { return address; }
	 * 
	 * public void setAddress(String address) { this.address = address; }
	 */

	public String getPrimarySubject() {
		return primarySubject;
	}

	public void setPrimarySubject(String primarySubject) {
		this.primarySubject = primarySubject;
	}

	public String getSecondarySubject() {
		return secondarySubject;
	}

	public void setSecondarySubject(String secondarySubject) {
		this.secondarySubject = secondarySubject;
	}

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

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	@Override
	public BaseDTO getDto() {
		FacultyDTO dto = new FacultyDTO();
		dto.setId(id);
		if (collegeId != null) {
			dto.setCollegeId(collegeId);
		}
		if (courseId != null) {
			dto.setCourseId(courseId);
		}
		dto.setFirstName(Util.getCapital(firstName));
		dto.setLastName(Util.getCapital(lastName));

		dto.setPrimarySubject(primarySubject);
		dto.setSecondarySubject(secondarySubject);
		dto.setMobileNo(mobileNo);
		dto.setEmail(email);
		/* dto.setQualification(qualification); */
		dto.setDob(dob);
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		System.out.println("inside facutly form get dto method");
		return dto;
	}

	@Override
	public void populate(BaseDTO bDto1) {
		FacultyDTO bDto = (FacultyDTO) bDto1;
		id = bDto.getId();
		collegeId = bDto.getCollegeId();
		courseId = bDto.getCourseId();
		firstName = Util.getCapital(bDto.getFirstName());
		lastName = Util.getCapital(bDto.getLastName());
		primarySubject = bDto.getPrimarySubject();
		secondarySubject = bDto.getSecondarySubject();
		mobileNo = bDto.getMobileNo();
		email = bDto.getEmail();
		/* qualification = bDto.getQualification(); */
		dob = bDto.getDob();

		/* address = bDto.getAddress(); */
		createdBy = bDto.getCreatedBy();
		modifiedBy = bDto.getModifiedBy();
		if (bDto.getCreatedDatetime() != null) {
			createdDatetime = bDto.getCreatedDatetime().getTime();
		}
		if (bDto.getModifiedDatetime() != null) {
			modifiedDatetime = bDto.getModifiedDatetime().getTime();
		}
	}

}
