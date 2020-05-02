package in.co.springmvc.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Time Table DTO class encapsulates Time Table attributes
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */
@Entity
@Table(name = "timetable")
public class TimeTableDTO extends BaseDTO {

	/**
	 * Course ID
	 */
	@Column(name = "COURSE_ID", length = 50)
	private long courseId;

	/**
	 * Course Name
	 */
	@Column(name = "COURSE_NAME", length = 50)
	private String course;
	/**
	 * Subject Name
	 */
	@Column(name = "SUBJECT", length = 50)
	private String subject;
	/**
	 * Date of Exam
	 */
	@Column(name = "EXAMINATION_DATE", length = 50)
	private Date examinationDate;
	/**
	 * Time of Exam
	 */
	@Column(name = "TIME", length = 50)
	private String time;
	/**
	 * Day of Exam
	 */
	@Column(name = "DAY", length = 50)
	private String day;

	/**
	 * accessor
	 */
	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
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

	public Date getExaminationDate() {
		return examinationDate;
	}

	public void setExaminationDate(Date examinationDate) {
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

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return course;
	}

}
