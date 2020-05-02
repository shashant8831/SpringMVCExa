package in.co.springmvc.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Faculty DTO class encapsulates Faculty attributes
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@Entity
@Table(name = "faculty")
public class FacultyDTO extends BaseDTO {

	/**
	 * First Name of Faculty
	 */
	@Column(name = "FIRST_NAME", length = 50)
	private String firstName;
	/**
	 * Last Name of Faculty
	 */
	@Column(name = "LAST_NAME", length = 50)
	private String lastName;
	/**
	 * Date of Birth of Faculty
	 */
	@Column(name = "DOB", length = 50)
	private Date dob;
	/**
	 * Qualification of Faculty
	 */
	@Column(name = "QUALIFICATION", length = 50)
	private String qualification;
	/**
	 * Email of Faculty
	 */
	@Column(name = "EMAIL", length = 50)
	private String email;
	/**
	 * MobileNo of Faculty
	 */
	@Column(name = "MOBILE_NO", length = 50)
	private String mobileNo;
	/**
	 * Address of Faculty
	 */
	@Column(name = "ADDRESS", length = 50)
	private String address;
	/**
	 * Primary Subject of Faculty
	 */
	@Column(name = "PRIMARY_SUBJECT", length = 50)
	private String primarySubject;
	/**
	 * Secondary Subject of Faculty
	 */
	@Column(name = "SECONDARY_SUBJECT", length = 50)
	private String secondarySubject;
	/**
	 * CollegeId of Faculty
	 */
	@Column(name = "COLLEGE_ID", length = 50)
	private long collegeId;
	/**
	 * College Name of Faculty
	 */
	@Column(name = "COLLEGE_NAME", length = 50)
	private String collegeName;
	/**
	 * CourseId of Faculty
	 */
	@Column(name = "COURSE_ID", length = 50)
	private long courseId;
	/**
	 * Course Name of Faculty
	 */
	@Column(name = "COURSE_NAME", length = 50)
	private String courseName;

	/**
	 * accessor
	 */

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

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

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

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return firstName + " " + lastName;
	}

}
