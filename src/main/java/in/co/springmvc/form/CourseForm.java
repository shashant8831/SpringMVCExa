package in.co.springmvc.form;

import in.co.springmvc.dto.BaseDTO;
import in.co.springmvc.dto.CourseDTO;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Contains Course form elements and their declarative input validations.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class CourseForm extends BaseForm {

	/*@NotEmpty*/
	private String courseName;
	/*@NotEmpty*/
	private String description;

	/*@NotEmpty*/
	/*@Min(1)
	@Max(5)*/
	private String duration;

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
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

	@Override
	public BaseDTO getDto() {
		CourseDTO dto = new CourseDTO();
		dto.setId(id);
		dto.setName(courseName);
		dto.setDescription(description);
		dto.setDuration(duration);
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		return dto;
	}

	@Override
	public void populate(BaseDTO bDto) {
		CourseDTO dto = (CourseDTO) bDto;
		id = dto.getId();
		courseName = dto.getName();
		description = dto.getDescription();
		duration = dto.getDuration();
		createdBy = dto.getCreatedBy();
		modifiedBy = dto.getModifiedBy();
		createdDatetime = dto.getCreatedDatetime().getTime();
		modifiedDatetime = dto.getModifiedDatetime().getTime();

		super.populate(bDto);
	}

}
