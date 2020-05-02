package in.co.springmvc.dao;

import in.co.springmvc.dto.CourseDTO;
import in.co.springmvc.exception.DatabaseException;

import java.util.List;

/**
 * Course DAO interface.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public interface CourseDAOInt {
	/**
	 * Adds a Course
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public long add(CourseDTO dto);

	/**
	 * Updates a Course
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public void update(CourseDTO dto);

	/**
	 * Deletes a Course
	 * 
	 * @param id
	 * @throws DatabaseException
	 */
	public void delete(long id);

	/**
	 * Finds Course by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public CourseDTO findByName(String name);

	/**
	 * Finds Course by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public CourseDTO findByPK(long pk);

	/**
	 * Searches Course
	 * 
	 * @return list : List of Roles
	 * @param dto
	 *            : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(CourseDTO dto);

	/**
	 * Searches Course with pagination
	 * 
	 * @return list : List of Roles
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */
	public List search(CourseDTO dto, int pageNo, int pageSize);

}
