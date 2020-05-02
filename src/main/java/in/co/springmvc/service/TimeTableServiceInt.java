package in.co.springmvc.service;

import in.co.springmvc.dto.TimeTableDTO;
import in.co.springmvc.exception.ApplicationException;
import in.co.springmvc.exception.DatabaseException;
import in.co.springmvc.exception.DuplicateRecordException;

import java.util.Date;
import java.util.List;

/**
 * Time Table Service interface.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public interface TimeTableServiceInt {

	/**
	 * Adds a Timetable
	 * 
	 * @param dto
	 * @return
	 * @throws DatabaseException
	 * @throws DuplicateRecordException
	 */
	public long add(TimeTableDTO dto) throws DuplicateRecordException,
			DatabaseException;

	/**
	 * Update a Time Table
	 * 
	 * @param dto
	 * @throws DatabaseException
	 * @throws DuplicateRecordException
	 *             : if updated user record is already exist
	 */
	public void update(TimeTableDTO dto) throws DuplicateRecordException,
			DatabaseException;

	/**
	 * Delete a Time Table
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(long id);

	/**
	 * Find Time Table by Course,Subject
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public TimeTableDTO findByCourse(Long courseId, String subject);

	/**
	 * Find Time Table by Examination Date,Course
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public TimeTableDTO findByExaminationDate(Date examinationDate,
			Long courseId);

	/**
	 * Find Time Table by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public TimeTableDTO findByPK(long id);

	/**
	 * Search Role with pagination
	 * 
	 * @return list : List of Time Table
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 */
	public List search(TimeTableDTO dto, int pageNo, int pageSize);

	/**
	 * Search Time Table
	 * 
	 * @return list : List of Time Table
	 * @param dto
	 *            : Search Parameters
	 * @throws ApplicationException
	 */
	public List search(TimeTableDTO dto);

}
