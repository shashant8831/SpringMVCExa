package in.co.springmvc.dao;

import java.util.List;

import in.co.springmvc.dto.StudentDTO;

/**
 * Student DAO interface.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public interface StudentDAOInt {
	/**
	 * Adds a Student
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public long add(StudentDTO dto);

	/**
	 * Updates a Student
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public void update(StudentDTO dto);

	/**
	 * Deletes a Student
	 * 
	 * @param id
	 * @throws DatabaseException
	 */
	public void delete(long id);

	/**
	 * Finds Student by Name
	 * 
	 * @param login
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public StudentDTO findByLogin(String login);

	/**
	 * Finds Student by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public StudentDTO findByPK(long pk);

	/**
	 * Searches Students
	 * 
	 * @return list : List of Students
	 * @param dto
	 *            : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(StudentDTO dto);

	/**
	 * Searches Students with pagination
	 * 
	 * @return list : List of Students
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */
	public List search(StudentDTO dto, int pageNo, int pageSize);

}
