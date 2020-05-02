package in.co.springmvc.form;

import in.co.springmvc.dto.BaseDTO;
import in.co.springmvc.dto.TimeTableDTO;
import in.co.springmvc.util.Util;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Contains Time Table form elements and their declarative input validations.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public class TimeTableForm extends BaseForm {

	/**
	 * Course ID
	 */
	@NotNull
	private Long courseId;

	/**
	 * Course Name
	 */
	private String course;
	/**
	 * Subject Name
	 */
	@NotEmpty
	private String subject;
	/**
	 * Date of Exam
	 */
	/* @DateTimeFormat(pattern = "MM/dd/yyyy") */
	@NotEmpty
	/* @Future */
	private String examinationDate;
	/**
	 * Time of Exam
	 */
	@NotEmpty
	private String time;
	/**
	 * Day of Exam
	 */
	private String day;

	/**
	 * Accessor
	 */

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getExaminationDate() {
		return examinationDate;
	}

	public void setExaminationDate(String examinationDate) {
		this.examinationDate = examinationDate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	@Override
	public BaseDTO getDto() {
		TimeTableDTO dto = new TimeTableDTO();
		dto.setId(id);
		if (courseId != null) {
			dto.setCourseId(courseId);
		}
		dto.setCourse(course);
		System.out.println("COURSE IN TIME TABLE FORM " + course);
		dto.setSubject(subject);
		dto.setExaminationDate(Util.getDate(examinationDate));
		dto.setTime(time);
		dto.setDay(day);
		return dto;
	}

	@Override
	public void populate(BaseDTO bDto) {
		TimeTableDTO dto = (TimeTableDTO) bDto;
		id = dto.getId();
		courseId = dto.getCourseId();
		course = dto.getCourse();
		System.out.println("COURSE IN TIME TABLE " + dto.getCourse());
		subject = dto.getSubject();
		examinationDate = Util.getDate(dto.getExaminationDate());
		time = dto.getTime();
		day = dto.getDay();
		createdBy = dto.getCreatedBy();
		modifiedBy = dto.getModifiedBy();
		if (dto.getCreatedDatetime() != null) {
			createdDatetime = dto.getCreatedDatetime().getTime();
		}
		if (dto.getModifiedDatetime() != null) {
			modifiedDatetime = dto.getModifiedDatetime().getTime();
		}

	}

}
