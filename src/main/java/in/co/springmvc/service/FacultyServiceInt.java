package in.co.springmvc.service;

import in.co.springmvc.dto.FacultyDTO;
import in.co.springmvc.exception.DuplicateRecordException;

import java.util.List;

/**
 * Faculty Service interface.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public interface FacultyServiceInt {

	/**
	 * Adds a Faculty
	 * 
	 * @param dto
	 * @throws DuplicateRecordException
	 *             : throws when faculty is already exists
	 */
	public long add(FacultyDTO dto) throws DuplicateRecordException;

	/**
	 * Updates a Faculty
	 * 
	 * @param dto
	 * @throws DuplicateRecordException
	 *             : throws when updated Faculty is already exists
	 */
	public void update(FacultyDTO dto) throws DuplicateRecordException;

	/**
	 * Deletes a Faculty
	 * 
	 * @param id
	 */
	public void delete(long id);

	/**
	 * Finds Faculty by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 */
	public FacultyDTO findByLogin(String login);

	/**
	 * Finds Faculty by primary key
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 */
	public FacultyDTO findByPK(long id);

	/**
	 * Searches Faculty
	 * 
	 * @return list : List of Faculty
	 * @param dto
	 *            : Search Parameters
	 */
	public List search(FacultyDTO dto);

	/**
	 * Searches Faculty with pagination
	 * 
	 * @return list : List of Faculty
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 */
	public List search(FacultyDTO dto, int pageNo, int pageSize);

}
