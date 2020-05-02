package in.co.springmvc.service;

import in.co.springmvc.dto.CourseDTO;
import in.co.springmvc.dto.RoleDTO;
import in.co.springmvc.exception.DuplicateRecordException;

import java.util.List;

/**
 * Course Service interface.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public interface CourseServiceInt {

	/**
	 * Adds a Course
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when Role is already exists
	 */
	public long add(CourseDTO dto) throws DuplicateRecordException;

	/**
	 * Updates a Course
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when updated Role is already exists
	 */
	public void update(CourseDTO dto) throws DuplicateRecordException;

	/**
	 * Deletes a Course
	 * 
	 * @param id
	 */
	public void delete(long id);

	/**
	 * Finds Course by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 */
	public CourseDTO findByName(String name);

	/**
	 * Finds Course by primary key
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 */
	public CourseDTO findByPK(long id);

	/**
	 * Searches Courses
	 * 
	 * @return list : List of Courses
	 * @param dto
	 *            : Search Parameters
	 */
	public List search(CourseDTO dto);

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
	 */
	public List search(CourseDTO dto, int pageNo, int pageSize);

}
