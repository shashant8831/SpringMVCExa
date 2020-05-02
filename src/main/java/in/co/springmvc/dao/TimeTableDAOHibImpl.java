package in.co.springmvc.dao;

import in.co.springmvc.dto.TimeTableDTO;
import in.co.springmvc.service.CourseServiceInt;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Hibernate implementation of Time Table DAO.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@Repository("timetableDAO")
public class TimeTableDAOHibImpl implements TimeTableDAOInt {

	private static Logger log = Logger.getLogger(TimeTableDAOHibImpl.class);

	@Autowired
	CourseServiceInt courseService;

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public long add(TimeTableDTO dto) {
		log.debug("DAO add Started");
		System.out.println("COURSE NAME IN ADD TIME TABLE DAO "
				+ dto.getCourse());

		dto.setCourse(courseService.findByPK(dto.getCourseId()).getName());
		System.out.println("COURSE NAME IN ADD TIME TABLE DAO "
				+ dto.getCourse());

		Session session = sessionFactory.getCurrentSession();
		session.save(dto);
		return dto.getId();
		/*
		 * long pk = (Long) sessionFactory.getCurrentSession().save(dto); return
		 * pk;
		 */
	}

	public void update(TimeTableDTO dto) {
		log.debug("DAO update Started");
		System.out.println("COURSE NAME IN ADD TIME TABLE DAO "
				+ dto.getCourse());

		dto.setCourse(courseService.findByPK(dto.getCourseId()).getName());

		System.out.println("COURSE NAME IN ADD TIME TABLE DAO "
				+ dto.getCourse());

		/* sessionFactory.getCurrentSession().update(dto); */
		Session session = sessionFactory.getCurrentSession();
		session.update(dto);
	}

	public void delete(long id) {
		log.debug("DAO delete Started");
		/*
		 * TimeTableDTO dto = new TimeTableDTO(); dto.setId(id);
		 * sessionFactory.getCurrentSession().delete(dto);
		 */

		TimeTableDTO dto = new TimeTableDTO();
		dto = findByPK(id);
		Session session = sessionFactory.getCurrentSession();
		session.delete(dto);

	}

	public TimeTableDTO findByCourse(Long courseId, String subject) {
		log.debug("Model findByCourse Started");

		TimeTableDTO dto = null;
		Session session = null;

		try {
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);

			criteria.add(Restrictions.eq("courseId", courseId));
			criteria.add(Restrictions.eq("subject", subject));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (TimeTableDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();

		} finally {
			System.out.println("FIND BY COURSE FINALLY");
		}

		return dto;
	}

	public TimeTableDTO findByExaminationDate(Date examinationDate,
			Long courseId) {
		log.debug("Model findByExaminatinDate Started");
		TimeTableDTO dto = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			criteria.add(Restrictions.eq("examinationDate", examinationDate));
			criteria.add(Restrictions.eq("courseId", courseId));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (TimeTableDTO) list.get(0);

			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			System.out.println("FIND BY COURSE FINALLY");
		}
		log.debug("Model findByExaminationDate End");
		return dto;
	}

	public TimeTableDTO findByPK(long pk) {
		Session session = sessionFactory.getCurrentSession();
		TimeTableDTO dto = (TimeTableDTO) session.get(TimeTableDTO.class, pk);
		/* session.evict(dto); */
		return dto;
	}

	public List search(TimeTableDTO dto) {
		return search(dto, 0, 0);
	}

	public List search(TimeTableDTO dto, int pageNo, int pageSize) {
		log.debug("DAO search Started");

		Criteria c = sessionFactory.getCurrentSession().createCriteria(
				TimeTableDTO.class);

		/*
		 * if (dto.getId() > 0) { c.add(Restrictions.eq("id", dto.getId())); }
		 */
		if (dto.getCourse() != null && dto.getCourse().length() > 0) {
			c.add(Restrictions.like("course", dto.getCourse() + "%"));
		}
		if (dto.getCourseId() > 0) {
			c.add(Restrictions.eq("courseId", dto.getCourseId()));
		}
		if (dto.getSubject() != null && dto.getSubject().length() > 0) {
			c.add(Restrictions.like("subject", dto.getSubject() + "%"));
		}
		if (dto.getExaminationDate() != null) {
			c.add(Restrictions.eq("examinationDate", dto.getExaminationDate()));
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
