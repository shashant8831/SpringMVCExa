package in.co.springmvc.dao;

import in.co.springmvc.dto.CourseDTO;
import in.co.springmvc.exception.DatabaseException;

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
 * Hibernate implementation of Course DAO.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@Repository("courseDAO")
public class CourseDAOHibImpl implements CourseDAOInt {
	private static Logger log = Logger.getLogger(CourseDAOHibImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Adds a Course
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public long add(CourseDTO dto) {

		log.debug("DAO add Started");
		long pk = (Long) sessionFactory.getCurrentSession().save(dto);
		return pk;
	}

	/**
	 * Updates a Course
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */

	public void update(CourseDTO dto) {
		log.debug("DAO update Started");
		sessionFactory.getCurrentSession().update(dto);
	}

	/**
	 * Deletes a Course
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */

	public void delete(long id) {
		log.debug("DAO delete Started");
		CourseDTO dto = new CourseDTO();
		dto.setId(id);
		sessionFactory.getCurrentSession().delete(dto);
	}

	/**
	 * Finds Course by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public CourseDTO findByPK(long pk) {
		System.out.println("COURSE ID: " + pk);
		System.out.println("IN COURSE DAO FIND BY PK");

		Session session = sessionFactory.getCurrentSession();
		CourseDTO dto = (CourseDTO) session.get(CourseDTO.class, pk);
		session.evict(dto);
		return dto;
	}

	/**
	 * Finds Course by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public CourseDTO findByName(String name) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CourseDTO.class).add(
				Restrictions.eq("name", name));
		List list = criteria.list();

		CourseDTO dto = null;
		if (list.size() == 1) {
			dto = (CourseDTO) list.get(0);
			session.evict(dto);
		}

		return dto;
	}

	/**
	 * Searches Course
	 * 
	 * @return list : List of Courses
	 * @param dto
	 *            : Search Parameters
	 * @throws DatabaseException
	 */

	public List search(CourseDTO dto) {
		return search(dto, 0, 0);
	}

	/**
	 * Searches Courses with pagination
	 * 
	 * @return list : List of Courses
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */

	public List search(CourseDTO dto, int pageNo, int pageSize) {

		log.debug("DAO search Started");

		Criteria c = sessionFactory.getCurrentSession().createCriteria(
				CourseDTO.class);

		if (dto.getName() != null && dto.getName().length() > 0) {
			c.add(Restrictions.like("name", dto.getName() + "%"));
		}
		if (dto.getDescription() != null && dto.getDescription().length() > 0) {
			c.add(Restrictions.like("description", dto.getDescription() + "%"));
		}
		if (dto.getDuration() != null) {
			c.add(Restrictions.like("duration", dto.getDuration() + "%"));
			/* c.add(Restrictions.eq("duration", dto.getDuration())); */
		}
		c.addOrder(Order.desc("id"));

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			c.setFirstResult((pageNo - 1) * pageSize);
			c.setMaxResults(pageSize);
		}
		return c.list();
	}

}
