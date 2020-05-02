package in.co.springmvc.service;

import java.util.List;

import in.co.springmvc.dto.RoleDTO;
import in.co.springmvc.exception.DuplicateRecordException;

/**
 * Role Service interface.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public interface RoleServiceInt {

	/**
	 * Adds a Role
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when Role is already exists
	 */
	public long add(RoleDTO dto) throws DuplicateRecordException;

	/**
	 * Updates a Role
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when updated Role is already exists
	 */
	public void update(RoleDTO dto) throws DuplicateRecordException;

	/**
	 * Deletes a Role
	 * 
	 * @param id
	 */
	public void delete(long id);

	/**
	 * Finds Role by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 */
	public RoleDTO findByName(String name);

	/**
	 * Finds Role by primary key
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 */
	public RoleDTO findByPK(long id);

	/**
	 * Searches Roles
	 * 
	 * @return list : List of Roles
	 * @param dto
	 *            : Search Parameters
	 */
	public List search(RoleDTO dto);

	/**
	 * Searches Roles with pagination
	 * 
	 * @return list : List of Roles
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 */
	public List search(RoleDTO dto, int pageNo, int pageSize);

}
