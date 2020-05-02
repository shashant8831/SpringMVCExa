package in.co.springmvc.dao;

import in.co.springmvc.dto.UserDTO;

import java.util.List;

/**
 * User DAO interface.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public interface UserDAOInt {
	/**
	 * Adds a User
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public long add(UserDTO dto);

	/**
	 * Updates a User
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public void update(UserDTO dto);

	/**
	 * Deletes a User
	 * 
	 * @param id
	 * @throws DatabaseException
	 */
	public void delete(long id);

	/**
	 * Finds User by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public UserDTO findByName(String name);

	/**
	 * Finds User by Login
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public UserDTO findByLogin(String login);

	/**
	 * Finds User by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public UserDTO findByPK(long pk);

	/**
	 * Searches Users
	 * 
	 * @return list : List of Users
	 * @param dto
	 *            : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(UserDTO dto);

	/**
	 * Searches Users with pagination
	 * 
	 * @return list : List of Users
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */
	public List search(UserDTO dto, int pageNo, int pageSize);

	/**
	 * User Authentication
	 * 
	 * @return dto : Contains User's information
	 * @param login
	 *            : User Login
	 * @param password
	 *            : User Password
	 * @throws ApplicationException
	 */
	public UserDTO authenticate(String login, String password);

	/**
	 * Register a User
	 * 
	 * @param dto
	 * @return
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	// public long registerUser(UserDTO dto) ;

	/**
	 * forget password
	 */
	// public boolean forgetPassword(String login);
}
