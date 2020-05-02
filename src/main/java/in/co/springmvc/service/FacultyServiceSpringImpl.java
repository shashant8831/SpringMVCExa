package in.co.springmvc.service;

import in.co.springmvc.dao.FacultyDAOInt;
import in.co.springmvc.dto.FacultyDTO;
import in.co.springmvc.exception.ApplicationException;
import in.co.springmvc.exception.DuplicateRecordException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Session facade of Faculty Service. It is transactional, apply delcarative
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

@Service("facultyService")
public class FacultyServiceSpringImpl implements FacultyServiceInt {

	@Autowired
	private FacultyDAOInt dao;

	/**
	 * Adds a Faculty
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long add(FacultyDTO dto) throws DuplicateRecordException {
		System.out.println("IN FACULTY SERVICE SPRING IMPL");
		System.out.println(dto.getEmail());
		FacultyDTO dtoExist = dao.findByLogin(dto.getEmail());
		System.out.println("DO EXISTS" + dtoExist);
		if (dtoExist != null) {
			throw new DuplicateRecordException("Faculty Email Already Exists");
		}
		return dao.add(dto);
	}

	/**
	 * Updates a Faculty
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when updated Faculty is already exists
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(FacultyDTO dto) throws DuplicateRecordException {
		FacultyDTO dtoExist = dao.findByLogin(dto.getEmail());
		// Check if updated Student is already exists
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Faculty Email Already Exists");
		}
		dao.update(dto);
	}

	/**
	 * Deletes a Faculty
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(long id) {
		dao.delete(id);
	}

	/**
	 * Finds Faculty by Primary Key
	 */
	@Transactional(readOnly = true)
	public FacultyDTO findByPK(long id) {
		return dao.findByPK(id);
	}

	/**
	 * Finds Faculty by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	@Transactional(readOnly = true)
	public FacultyDTO findByLogin(String login) {
		return dao.findByLogin(login);
	}

	/**
	 * Searches Faculty
	 * 
	 * @return list : List of Faculty
	 * @param dto
	 *            : Search Parameters
	 * @throws ApplicationException
	 */
	@Transactional(readOnly = true)
	public List search(FacultyDTO dto) {
		return dao.search(dto);
	}

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
	 * @throws ApplicationException
	 */
	@Transactional(readOnly = true)
	public List search(FacultyDTO dto, int pageNo, int pageSize) {
		return dao.search(dto, pageNo, pageSize);
	}

}
