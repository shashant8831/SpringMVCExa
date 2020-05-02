package in.co.springmvc.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Course DTO class encapsulates Course attributes
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */
@Entity
@Table(name = "course")
public class CourseDTO extends BaseDTO {

	/**
	 * Course Name
	 */
	@Column(name = "NAME", length = 50)
	private String name;

	/**
	 * Course Description
	 */
	@Column(name = "DESCRIPTION", length = 50)
	private String description;

	/**
	 * Course Duration
	 */
	@Column(name = "DURATION", length = 50)
	private String duration;

	/**
	 * accessor
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return name + "";
	}

}
