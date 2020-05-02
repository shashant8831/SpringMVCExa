package in.co.springmvc.dao;

import java.util.List;

import in.co.springmvc.dto.MarksheetDTO;


/**
 * Marksheet DAO interface.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public interface MarksheetDAOInt {
	/**
	 * Adds a Marksheet
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public long add(MarksheetDTO dto);

	/**
	 * Updates a Marksheet
	 * 
	 * @param dto
	 * @return 
	 * @throws DatabaseException
	 */
	public long update(MarksheetDTO dto);

	/**
	 * Deletes a Marksheet
	 * 
	 * @param id
	 * @throws DatabaseException
	 */
	public void delete(long id);

	/**
	 * Finds Marksheet by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public MarksheetDTO findByRollNo(String rollNo);

	/**
	 * Finds Marksheet by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public MarksheetDTO findByPK(long pk);

	/**
	 * Searches Marksheets
	 * 
	 * @return list : List of Marksheets
	 * @param dto
	 *            : Search Parameters
	 * @throws DatabaseException
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
	 * @throws DatabaseException
	 */
	public List search(MarksheetDTO dto, int pageNo, int pageSize);
	 
	/**
     * Gets Merit List.
     * 
     * @param pageNo
     * @param pageSize
     * @return
     */

	public List getMeritList(int pageNo, int pageSize);


}
