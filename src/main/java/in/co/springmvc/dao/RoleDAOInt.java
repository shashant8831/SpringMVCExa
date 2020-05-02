package in.co.springmvc.dao;

import java.util.List;

import in.co.springmvc.dto.RoleDTO;

/**
 * Role DAO interface.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public interface RoleDAOInt {

	/**
	 * Adds a Role
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public long add(RoleDTO dto);

	/**
	 * Updates a Role
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public void update(RoleDTO dto);

	/**
	 * Deletes a Role
	 * 
	 * @param id
	 * @throws DatabaseException
	 */
	public void delete(long id);

	/**
	 * Finds Role by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public RoleDTO findByName(String name);

	/**
	 * Finds Role by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public RoleDTO findByPK(long pk);

	/**
	 * Searches Roles
	 * 
	 * @return list : List of Roles
	 * @param dto
	 *            : Search Parameters
	 * @throws DatabaseException
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
	 * @throws DatabaseException
	 */
	public List search(RoleDTO dto, int pageNo, int pageSize);

}
