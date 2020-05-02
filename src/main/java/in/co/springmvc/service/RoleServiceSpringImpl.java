package in.co.springmvc.service;

import in.co.springmvc.dao.RoleDAOInt;
import in.co.springmvc.dto.RoleDTO;
import in.co.springmvc.exception.DuplicateRecordException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Session facade of Role Service. It is transactional, apply delcarative
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
@Service("roleService")
public class RoleServiceSpringImpl implements RoleServiceInt {

	@Autowired
	private RoleDAOInt dao;

	/**
	 * Adds a Role
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long add(RoleDTO dto) throws DuplicateRecordException {

		System.out.println("ROLE NAME" + dto.getName());
		RoleDTO dtoExist = dao.findByName(dto.getName());
		System.out.println("ROLE NAME" + dto.getName());
		if (dtoExist != null) {
			throw new DuplicateRecordException("Role Name Already Exists");
		}
		return dao.add(dto);
	}

	/**
	 * Updates a Role
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when updated Role is already exists
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(RoleDTO dto) throws DuplicateRecordException {
		RoleDTO dtoExist = dao.findByName(dto.getName());
		// Check if updated Role is already exists
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Duplicate Name");
		}
		dao.update(dto);
	}

	/**
	 * Deletes a Role
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(long id) {
		dao.delete(id);
	}

	/**
	 * Finds record by Primary Key
	 */
	@Transactional(readOnly = true)
	public RoleDTO findByPK(long id) {
		return dao.findByPK(id);
	}

	/**
	 * Finds Role by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	@Transactional(readOnly = true)
	public RoleDTO findByName(String name) {
		return dao.findByName(name);
	}

	/**
	 * Searches Roles
	 * 
	 * @return list : List of Roles
	 * @param dto
	 *            : Search Parameters
	 * @throws ApplicationException
	 */
	@Transactional(readOnly = true)
	public List search(RoleDTO dto) {
		return dao.search(dto);
	}

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
	 * @throws ApplicationException
	 */

	@Transactional(readOnly = true)
	public List search(RoleDTO dto, int pageNo, int pageSize) {
		return dao.search(dto, pageNo, pageSize);
	}

}
