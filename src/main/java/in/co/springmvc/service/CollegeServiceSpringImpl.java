package in.co.springmvc.service;

import in.co.springmvc.dao.CollegeDAOInt;
import in.co.springmvc.dto.CollegeDTO;
import in.co.springmvc.exception.DuplicateRecordException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Session facade of College Service. It is transactional, apply delcarative
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

@Service("collegeService")
public class CollegeServiceSpringImpl implements CollegeServiceInt {

	@Autowired
	private CollegeDAOInt dao;

	/**
	 * Adds a College
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long add(CollegeDTO dto) throws DuplicateRecordException {

		CollegeDTO dtoExist = dao.findByName(dto.getName());
		if (dtoExist != null) {
			throw new DuplicateRecordException("College Name Already Exists");
		}
		return dao.add(dto);
	}

	/**
	 * Updates a College
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when updated College is already exists
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(CollegeDTO dto) throws DuplicateRecordException {
		CollegeDTO dtoExist = dao.findByName(dto.getName());
		// Check if updated College is already exists
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Duplicate College Name");
		}
		dao.update(dto);
	}

	/**
	 * Deletes a College
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
	public CollegeDTO findByPK(long id) {
		return dao.findByPK(id);
	}

	/**
	 * Finds College by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	@Transactional(readOnly = true)
	public CollegeDTO findByName(String name) {
		return dao.findByName(name);
	}

	/**
	 * Searches Colleges
	 * 
	 * @return list : List of Colleges
	 * @param dto
	 *            : Search Parameters
	 * @throws ApplicationException
	 */
	@Transactional(readOnly = true)
	public List search(CollegeDTO dto) {
		return dao.search(dto);
	}

	/**
	 * Searches Colleges with pagination
	 * 
	 * @return list : List of Colleges
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 */
	@Transactional(readOnly = true)
	public List search(CollegeDTO dto, int pageNo, int pageSize) {
		return dao.search(dto, pageNo, pageSize);
	}

}
