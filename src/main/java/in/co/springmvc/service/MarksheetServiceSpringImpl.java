package in.co.springmvc.service;

import in.co.springmvc.dao.MarksheetDAOInt;
import in.co.springmvc.dto.MarksheetDTO;
import in.co.springmvc.exception.DuplicateRecordException;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Session facade of Marksheet Service. It is transactional, apply delcarative
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

@Service("marksheetService")
public class MarksheetServiceSpringImpl implements MarksheetServiceInt {

	private static Logger log = Logger
			.getLogger(MarksheetServiceSpringImpl.class);

	@Autowired
	private MarksheetDAOInt dao = null;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long add(MarksheetDTO dto) throws DuplicateRecordException {

		MarksheetDTO dtoExist = dao.findByRollNo(dto.getRollNo());
		if (dtoExist != null) {
			throw new DuplicateRecordException("Roll No allredy exist");
		}

		return dao.add(dto);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long update(MarksheetDTO dto) throws DuplicateRecordException {
		MarksheetDTO dtoExist = findByRollNo(dto.getRollNo());
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Roll No allredy exist");
		}
		return dao.update(dto);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(long id) {
		dao.delete(id);
	}

	@Transactional(readOnly = true)
	public MarksheetDTO findByRollNo(String rollNo) {
		return dao.findByRollNo(rollNo);
	}

	@Transactional(readOnly = true)
	public MarksheetDTO findByPK(long pk) {
		return dao.findByPK(pk);
	}

	@Transactional(readOnly = true)
	public List search(MarksheetDTO dto) {
		return dao.search(dto, 0, 0);
	}

	@Transactional(readOnly = true)
	public List search(MarksheetDTO dto, int pageNo, int pageSize) {
		return dao.search(dto, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public List getMeritList(int pageNo, int pageSize) {
		return dao.getMeritList(pageNo, pageSize);
	}

}
