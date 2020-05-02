package in.co.springmvc.dao;

import in.co.springmvc.dto.RoleDTO;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Hibernate implementation of Role DAO.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@Repository("roleDAO")
public class RoleDAOHibImpl implements RoleDAOInt {

	private static Logger log = Logger.getLogger(RoleDAOHibImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Adds a Role
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public long add(RoleDTO dto) {

		log.debug("DAO add Started");
		long pk = (Long) sessionFactory.getCurrentSession().save(dto);
		return pk;
	}

	/**
	 * Updates a Role
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */

	public void update(RoleDTO dto) {
		log.debug("DAO update Started");
		sessionFactory.getCurrentSession().update(dto);
	}

	/**
	 * Deletes a Role
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */

	public void delete(long id) {
		log.debug("DAO delete Started");
		RoleDTO dto = new RoleDTO();
		dto.setId(id);
		sessionFactory.getCurrentSession().delete(dto);
	}

	/**
	 * Finds Role by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public RoleDTO findByPK(long pk) {
		System.out.println("ROLE ID: " + pk);
		System.out.println("IN ROLE DAO FIND BY PK");

		Session session = sessionFactory.getCurrentSession();
		RoleDTO dto = (RoleDTO) session.get(RoleDTO.class, pk);
		session.evict(dto);
		return dto;
	}

	/**
	 * Finds Role by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public RoleDTO findByName(String name) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(RoleDTO.class).add(
				Restrictions.eq("name", name));
		List list = criteria.list();

		RoleDTO dto = null;
		if (list.size() == 1) {
			dto = (RoleDTO) list.get(0);
			session.evict(dto);
		}

		return dto;
	}

	/**
	 * Searches Roles
	 * 
	 * @return list : List of Roles
	 * @param dto
	 *            : Search Parameters
	 * @throws DatabaseException
	 */

	public List search(RoleDTO dto) {
		return search(dto, 0, 0);
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
	 * @throws DatabaseException
	 */

	public List search(RoleDTO dto, int pageNo, int pageSize) {

		log.debug("DAO search Started");

		Criteria c = sessionFactory.getCurrentSession().createCriteria(
				RoleDTO.class);

		/*if (dto != null && dto.getId() > 0) {
			c.add(Restrictions.eq("id", dto.getId()));
		}*/
		if (dto.getName() != null && dto.getName().length() > 0) {
			c.add(Restrictions.like("name", dto.getName() + "%"));
		}
		if (dto.getDescription() != null && dto.getDescription().length() > 0) {
			c.add(Restrictions.like("description", dto.getDescription() + "%"));
		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			c.setFirstResult((pageNo - 1) * pageSize);
			c.setMaxResults(pageSize);
		}
		return c.list();
	}

}
