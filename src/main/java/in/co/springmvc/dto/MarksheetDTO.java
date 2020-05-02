package in.co.springmvc.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Marksheet DTO class encapsulates Marksheet attributes
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */
@Entity
@Table(name = "marksheet")
public class MarksheetDTO extends BaseDTO {

	/**
	 * Rollno of Student
	 */
	@Column(name = "ROLL_NO", length = 50)
	private String rollNo;
	/**
	 * ID of Student
	 */
	@Column(name = "STUDENT_ID", length = 50)
	private long studentId;
	/**
	 * Name of Student
	 */
	@Column(name = "NAME", length = 50)
	private String name;
	/**
	 * Physics marks of Student
	 */
	@Column(name = "PHYSICS", length = 50)
	private Integer physics;
	/**
	 * Chemistry marks of Student
	 */
	@Column(name = "CHEMISTRY", length = 50)
	private Integer chemistry;
	/**
	 * Mathematics marks of Student
	 */
	@Column(name = "MATHS", length = 50)
	private Integer maths;
	
	private Integer total;
	

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * accessor
	 */
	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPhysics() {
		return physics;
	}

	public void setPhysics(Integer physics) {
		this.physics = physics;
	}

	public Integer getChemistry() {
		return chemistry;
	}

	public void setChemistry(Integer chemistry) {
		this.chemistry = chemistry;
	}

	public Integer getMaths() {
		return maths;
	}

	public void setMaths(Integer maths) {
		this.maths = maths;
	}

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return rollNo;
	}

}
