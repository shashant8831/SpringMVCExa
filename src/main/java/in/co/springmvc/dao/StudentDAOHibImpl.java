package in.co.springmvc.dao;

import in.co.springmvc.dto.StudentDTO;
import in.co.springmvc.service.CollegeServiceInt;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Hibernate implementation of Student DAO.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@Repository("studentDAO")
public class StudentDAOHibImpl implements StudentDAOInt {
	private static Logger log = Logger.getLogger(StudentDAOHibImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	CollegeServiceInt collegeService;

	/**
	 * Adds a Student
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public long add(StudentDTO dto) {

		log.debug("DAO add Started");

		dto.setCollegeName(collegeService.findByPK(dto.getCollegeId())
				.getName());
		long pk = (Long) sessionFactory.getCurrentSession().save(dto);
		return pk;
	}

	public void update(StudentDTO dto) {
		log.debug("DAO update Started");
		dto.setCollegeName(collegeService.findByPK(dto.getCollegeId())
				.getName());
		sessionFactory.getCurrentSession().update(dto);
	}

	/**
	 * Deletes a Student
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */

	public void delete(long id) {
		log.debug("DAO delete Started");
		StudentDTO dto = new StudentDTO();
		dto.setId(id);
		sessionFactory.getCurrentSession().delete(dto);
	}

	/**
	 * Finds Student by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public StudentDTO findByPK(long pk) {

		Session session = sessionFactory.getCurrentSession();
		StudentDTO dto = (StudentDTO) session.get(StudentDTO.class, pk);
		session.evict(dto);
		return dto;
	}

	/**
	 * Finds Student by Name
	 * 
	 * @param login
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public StudentDTO findByLogin(String login) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(StudentDTO.class).add(
				Restrictions.eq("email", login));
		List list = criteria.list();

		StudentDTO dto = null;
		if (list.size() == 1) {
			dto = (StudentDTO) list.get(0);
			session.evict(dto);
		}

		return dto;
	}

	/**
	 * Searches Students
	 * 
	 * @return list : List of Students
	 * @param dto
	 *            : Search Parameters
	 * @throws DatabaseException
	 */

	public List search(StudentDTO dto) {
		return search(dto, 0, 0);
	}

	/**
	 * Searches Students with pagination
	 * 
	 * @return list : List of Students
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */

	public List search(StudentDTO dto, int pageNo, int pageSize) {

		log.debug("DAO search Started");

		Criteria c = sessionFactory.getCurrentSession().createCriteria(
				StudentDTO.class);

		/*
		 * if (dto.getId() > 0) { c.add(Restrictions.eq("id", dto.getId())); }
		 */
		if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
			c.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
		}
		if (dto.getLastName() != null && dto.getLastName().length() > 0) {
			c.add(Restrictions.like("lastName", dto.getLastName() + "%"));
		}
		if (dto.getDob() != null && dto.getDob().getDate() > 0) {
			c.add(Restrictions.eq("dob", dto.getDob()));
		}
		if (dto.getEmail() != null && dto.getEmail().length() > 0) {
			c.add(Restrictions.like("email", dto.getEmail() + "%"));
		}
		if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
			c.add(Restrictions.like("mobileNo", dto.getMobileNo() + "%"));
		}
		c.addOrder(Order.desc("id"));

		// if page size is greater than zero the apply pagination
		if (pageSize > 0) {
			c.setFirstResult(((pageNo - 1) * pageSize));
			c.setMaxResults(pageSize);
		}

		return c.list();
	}

}
