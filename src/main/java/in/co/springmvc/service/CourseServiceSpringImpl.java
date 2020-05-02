package in.co.springmvc.service;

import in.co.springmvc.dao.CourseDAOInt;
import in.co.springmvc.dto.CourseDTO;
import in.co.springmvc.exception.DuplicateRecordException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Session facade of Course Service. It is transactional, apply delcarative
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
@Service("courseService")
public class CourseServiceSpringImpl implements CourseServiceInt {

	@Autowired
	private CourseDAOInt dao;

	/**
	 * Adds a Course
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long add(CourseDTO dto) throws DuplicateRecordException {

		System.out.println("COURSE NAME" + dto.getName());
		CourseDTO dtoExist = dao.findByName(dto.getName());
		if (dtoExist != null) {
			throw new DuplicateRecordException("Course Name Already Exists");
		}
		return dao.add(dto);
	}

	/**
	 * Updates a Course
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when updated Course is already exists
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(CourseDTO dto) throws DuplicateRecordException {
		CourseDTO dtoExist = dao.findByName(dto.getName());
		// Check if updated Course is already exists
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Course Name Already Exists");
		}
		dao.update(dto);
	}

	/**
	 * Deletes a Course
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(long id) {
		dao.delete(id);
	}

	/**
	 * Finds Course by Primary Key
	 */
	@Transactional(readOnly = true)
	public CourseDTO findByPK(long id) {
		return dao.findByPK(id);
	}

	/**
	 * Finds Course by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	@Transactional(readOnly = true)
	public CourseDTO findByName(String name) {
		return dao.findByName(name);
	}

	/**
	 * Searches Courses
	 * 
	 * @return list : List of Courses
	 * @param dto
	 *            : Search Parameters
	 * @throws ApplicationException
	 */
	@Transactional(readOnly = true)
	public List search(CourseDTO dto) {
		return dao.search(dto);
	}

	/**
	 * Searches Courses with pagination
	 * 
	 * @return list : List of Courses
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 */

	@Transactional(readOnly = true)
	public List search(CourseDTO dto, int pageNo, int pageSize) {
		return dao.search(dto, pageNo, pageSize);
	}

}
