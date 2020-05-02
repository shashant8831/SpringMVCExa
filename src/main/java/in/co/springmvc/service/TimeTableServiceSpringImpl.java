package in.co.springmvc.service;

import in.co.springmvc.dao.TimeTableDAOInt;
import in.co.springmvc.dto.TimeTableDTO;
import in.co.springmvc.exception.ApplicationException;
import in.co.springmvc.exception.DatabaseException;
import in.co.springmvc.exception.DuplicateRecordException;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Session facade of Time Table Service. It is transactional, apply delcarative
 * transactions with help of Spring AOP.
 * 
 * If unchecked exception is propagated from a method then transaction will be
 * rolled back.
 * 
 * Default propogation value is Propagation.REQUIRED and readOnly = false
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@Service("timetableService")
public class TimeTableServiceSpringImpl implements TimeTableServiceInt {

	@Autowired
	private TimeTableDAOInt dao;

	/*
	 * public void setDao(TimeTableDAOInt dao) { this.dao = dao; }
	 */

	/**
	 * Adds a Time Table
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long add(TimeTableDTO dto) throws DuplicateRecordException,
			DatabaseException {

		TimeTableDTO duplicateCourse = dao.findByCourse(dto.getCourseId(),
				dto.getSubject());
		System.out.println("****COURSE SUBJECT**** " + dto.getCourseId()
				+ dto.getSubject());
		if (duplicateCourse != null) {
			throw new DuplicateRecordException(
					"Exam is already been scheduled for this subject in this course");
		}

		TimeTableDTO duplicateExamDate = dao.findByExaminationDate(
				dto.getExaminationDate(), dto.getCourseId());
		System.out.println("****DATE COURSE**** " + dto.getExaminationDate()
				+ dto.getCourseId());
		if (duplicateExamDate != null) {
			throw new DatabaseException(
					"This date is already been occupied for this course");

		}

		return dao.add(dto);
	}

	/**
	 * Updates a Time Table
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when updated Faculty is already exists
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(TimeTableDTO dto) throws DuplicateRecordException,
			DatabaseException {

		TimeTableDTO duplicateCourse = dao.findByCourse(dto.getCourseId(),
				dto.getSubject());
		if (duplicateCourse != null) {
			throw new DuplicateRecordException(
					"Exam is already been scheduled for this subject in this course");
		}
		TimeTableDTO duplicateExamDate = dao.findByExaminationDate(
				dto.getExaminationDate(), dto.getCourseId());
		if (duplicateExamDate != null) {
			throw new DatabaseException(
					"This date is already been occupied for this course");

		}

		dao.update(dto);
	}

	/**
	 * Deletes a Time Table
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(long id) {
		dao.delete(id);

	}

	/**
	 * Finds Time Table by Course
	 */
	@Transactional(readOnly = true)
	public TimeTableDTO findByCourse(Long courseId, String subject) {
		return dao.findByCourse(courseId, subject);
	}

	/**
	 * Finds Time Table by Examination Date
	 */
	@Transactional(readOnly = true)
	public TimeTableDTO findByExaminationDate(Date examinationDate,
			Long courseId) {

		return dao.findByExaminationDate(examinationDate, courseId);
	}

	/**
	 * Finds Time Table by Primary Key
	 */
	@Transactional(readOnly = true)
	public TimeTableDTO findByPK(long id) {
		return dao.findByPK(id);
	}

	/**
	 * Searches Time Table with pagination
	 * 
	 * @return list : List of Time Table
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 */
	@Transactional(readOnly = true)
	public List search(TimeTableDTO dto, int pageNo, int pageSize) {
		return dao.search(dto, pageNo, pageSize);
	}

	/**
	 * Searches Time Table
	 * 
	 * @return list : List of Time Table
	 * @param dto
	 *            : Search Parameters
	 * @throws ApplicationException
	 */
	@Transactional(readOnly = true)
	public List search(TimeTableDTO dto) {
		return dao.search(dto);
	}

}
