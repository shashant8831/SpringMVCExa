package in.co.springmvc.service;

import java.util.List;

import in.co.springmvc.dto.MarksheetDTO;
import in.co.springmvc.exception.DuplicateRecordException;

/**
 * Marksheet Service interface.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public interface MarksheetServiceInt {

	/**
	 * Adds a Marksheet
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when Marksheet is already exists
	 */
	public long add(MarksheetDTO dto) throws DuplicateRecordException;

	/**
	 * Updates a Marksheet
	 * 
	 * @param dto
	 * @return
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when updated Marksheet is already exists
	 */
	public long update(MarksheetDTO dto) throws DuplicateRecordException;

	/**
	 * Deletes a Marksheet
	 * 
	 * @param id
	 */
	public void delete(long id);

	/**
	 * Finds Marksheet by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 */
	public MarksheetDTO findByRollNo(String rollNo);

	/**
	 * Finds Marksheet by primary key
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 */
	public MarksheetDTO findByPK(long id);

	/**
	 * Searches Marksheets
	 * 
	 * @return list : List of Marksheets
	 * @param dto
	 *            : Search Parameters
	 */
	public List search(MarksheetDTO dto);

	/**
	 * Searches Marksheets with pagination
	 * 
	 * @return list : List of Marksheets
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 */
	public List search(MarksheetDTO dto, int pageNo, int pageSize);

	public List getMeritList(int i, int j);

}
