package in.co.springmvc.dao;

import in.co.springmvc.dto.CollegeDTO;

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
 * Hibernate implementation of College DAO.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@Repository("collegeDAO")
public class CollegeDAOHibImpl implements CollegeDAOInt {

	private static Logger log = Logger.getLogger(CollegeDAOHibImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Adds a College
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public long add(CollegeDTO dto) {

		log.debug("DAO add Started");
		long pk = (Long) sessionFactory.getCurrentSession().save(dto);
		return pk;
	}

	/**
	 * Updates a College
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */

	public void update(CollegeDTO dto) {
		log.debug("DAO update Started");
		sessionFactory.getCurrentSession().update(dto);
	}

	/**
	 * Deletes a College
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */

	public void delete(long id) {
		log.debug("DAO delete Started");
		CollegeDTO dto = new CollegeDTO();
		dto.setId(id);
		sessionFactory.getCurrentSession().delete(dto);
	}

	/**
	 * Finds College by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public CollegeDTO findByPK(long pk) {

		Session session = sessionFactory.getCurrentSession();
		CollegeDTO dto = (CollegeDTO) session.get(CollegeDTO.class, pk);
		session.evict(dto);
		return dto;
	}

	/**
	 * Finds College by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public CollegeDTO findByName(String name) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CollegeDTO.class).add(
				Restrictions.eq("name", name));
		List list = criteria.list();

		CollegeDTO dto = null;
		if (list.size() == 1) {
			dto = (CollegeDTO) list.get(0);
			session.evict(dto);
		}

		return dto;
	}

	/**
	 * Searches Colleges
	 * 
	 * @return list : List of Colleges
	 * @param dto
	 *            : Search Parameters
	 * @throws DatabaseException
	 */

	public List search(CollegeDTO dto) {
		return search(dto, 0, 0);
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
	 * @throws DatabaseException
	 */

	public List search(CollegeDTO dto, int pageNo, int pageSize) {
		log.debug("DAO search Started");

		Criteria c = sessionFactory.getCurrentSession().createCriteria(
				CollegeDTO.class);

		/*
		 * if (dto.getId() > 0) { c.add(Restrictions.eq("id", dto.getId())); }
		 */
		if (dto.getName() != null && dto.getName().length() > 0) {
			c.add(Restrictions.like("name", dto.getName() + "%"));
		}
		if (dto.getAddress() != null && dto.getAddress().length() > 0) {
			c.add(Restrictions.like("address", dto.getAddress() + "%"));
		}
		if (dto.getState() != null && dto.getState().length() > 0) {
			c.add(Restrictions.like("state", dto.getState() + "%"));
		}
		if (dto.getCity() != null && dto.getCity().length() > 0) {
			c.add(Restrictions.like("city", dto.getCity() + "%"));
		}c.addOrder(Order.desc("id"));

		// if page size is greater than zero the apply pagination
		if (pageSize > 0) {
			c.setFirstResult(((pageNo - 1) * pageSize));
			c.setMaxResults(pageSize);
		}

		return c.list();

	}

}
