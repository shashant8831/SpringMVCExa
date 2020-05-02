package in.co.springmvc.dao;

import in.co.springmvc.dto.UserDTO;
import in.co.springmvc.exception.DatabaseException;
import in.co.springmvc.service.RoleServiceInt;

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
 * Hibernate implementation of User DAO.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@Repository("userDAO")
public class UserDAOHibImpl implements UserDAOInt {

	private static Logger log = Logger.getLogger(UserDAOHibImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	/*
	 * @Autowired private JavaMailSenderImpl mailSender;
	 */

	@Autowired
	RoleServiceInt roleService;

	/**
	 * Adds a User
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public long add(UserDTO dto) {

		log.debug("DAO add Started");
		dto.setRoleName(roleService.findByPK(dto.getRoleId()).getName());

		long pk = (Long) sessionFactory.getCurrentSession().save(dto);
		return pk;
	}

	/**
	 * Updates a User
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */

	public void update(UserDTO dto) {

		log.debug("DAO update Started");
		/*
		 * RoleDTO roleDTO = service.findByPK(dto.getRoleId());
		 * dto.setRoleName(roleDTO.getName());
		 */
		dto.setRoleName(roleService.findByPK(dto.getRoleId()).getName());
		sessionFactory.getCurrentSession().update(dto);
	}

	/**
	 * Deletes a User
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */

	public void delete(long id) {
		log.debug("DAO delete Started");
		UserDTO dto = new UserDTO();
		dto.setId(id);
		sessionFactory.getCurrentSession().delete(dto);
	}

	/**
	 * Finds User by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public UserDTO findByPK(long pk) {

		Session session = sessionFactory.getCurrentSession();
		UserDTO dto = (UserDTO) session.get(UserDTO.class, pk);
		session.evict(dto);
		return dto;
	}

	/**
	 * Finds User by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public UserDTO findByName(String name) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserDTO.class).add(
				Restrictions.eq("name", name));
		List list = criteria.list();

		UserDTO dto = null;
		if (list.size() == 1) {
			dto = (UserDTO) list.get(0);
			session.evict(dto);
		}

		return dto;
	}

	/**
	 * Searches Users
	 * 
	 * @return list : List of Users
	 * @param dto
	 *            : Search Parameters
	 * @throws DatabaseException
	 */

	public List search(UserDTO dto) {
		return search(dto, 0, 0);
	}

	/**
	 * Searches Users with pagination
	 * 
	 * @return list : List of Users
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */

	public List search(UserDTO dto, int pageNo, int pageSize) {

		log.debug("DAO search Started");

		Criteria c = sessionFactory.getCurrentSession().createCriteria(
				UserDTO.class);

		/*
		 * if (dto.getId() > 0) { c.add(Restrictions.eq("id", dto.getId())); }
		 */
		if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
			c.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
		}
		if (dto.getLastName() != null && dto.getLastName().length() > 0) {
			c.add(Restrictions.like("lastName", dto.getLastName() + "%"));
		}
		if (dto.getLogin() != null && dto.getLogin().length() > 0) {
			c.add(Restrictions.like("login", dto.getLogin() + "%"));
		}
		if (dto.getPassword() != null && dto.getPassword().length() > 0) {
			c.add(Restrictions.like("password", dto.getPassword() + "%"));
		}
		if (dto.getGender() != null && dto.getGender().length() > 0) {
			c.add(Restrictions.like("gender", dto.getGender() + "%"));
		}
		if (dto.getDob() != null && dto.getDob().getDate() > 0) {
			c.add(Restrictions.eq("dob", dto.getDob()));
		}
		if (dto.getLastLogin() != null && dto.getLastLogin().getTime() > 0) {
			c.add(Restrictions.eq("lastLogin", dto.getLastLogin()));
		}
		if (dto.getRoleId() > 0) {
			c.add(Restrictions.eq("roleId", dto.getRoleId()));
		}
		if (dto.getUnSuccessfulLogin() > 0) {
			c.add(Restrictions.eq("unSuccessfulLogin",
					dto.getUnSuccessfulLogin()));
		}
		c.addOrder(Order.desc("id"));
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			c.setFirstResult((pageNo - 1) * pageSize);
			c.setMaxResults(pageSize);
		}
		return c.list();
	}

	public UserDTO findByLogin(String login) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserDTO.class).add(
				Restrictions.eq("login", login));
		List list = criteria.list();

		UserDTO dto = null;
		if (list.size() == 1) {
			dto = (UserDTO) list.get(0);
			session.evict(dto);
		}

		return dto;

	}

	/**
	 * @param login
	 *            : String login
	 * @param password
	 *            : password
	 * @throws DatabaseException
	 */
	public UserDTO authenticate(String login, String password) {
		Session session = sessionFactory.getCurrentSession();
		/*
		 * Query q = session
		 * .createQuery("from UserDTO where login=? and password=? ");
		 * q.setString(0, login); q.setString(1, password); List list =
		 * q.list(); UserDTO dto = null; if (list.size() > 0) { dto = (UserDTO)
		 * list.get(0); } else { dto = null; }
		 */
		Criteria criteria = session.createCriteria(UserDTO.class);
		criteria.add(Restrictions.eq("login", login));
		criteria.add(Restrictions.eq("password", password));
		List list = criteria.list();

		UserDTO dto = null;
		if (list.size() == 1) {
			dto = (UserDTO) list.get(0);
			session.evict(dto);
		}

		return dto;
	}
	/*
	 * @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 * public long registerUser(UserDTO dto) { long pk = add(dto);
	 * 
	 * HashMap<String, String> map = new HashMap<String, String>();
	 * map.put("login", dto.getLogin()); map.put("password", dto.getPassword());
	 * String message = EmailBuilder.getUserRegistrationMessage(map);
	 * 
	 * MimeMessage msg = mailSender.createMimeMessage();
	 * 
	 * MimeMessageHelper helper; try { helper = new MimeMessageHelper(msg,
	 * true);
	 * 
	 * helper.setTo(dto.getLogin()); helper.setSubject(
	 * "Registration is successful for ORS Project SUNRAYS Technologies");
	 * helper.setText(message, true); mailSender.send(msg); } catch (Exception
	 * e) {
	 * 
	 * e.printStackTrace(); } return pk;
	 * 
	 * }
	 */

	/**
	 * Send the password of User to his Email
	 * 
	 * @return boolean : true if success otherwise false
	 * @param login
	 *            : User Login
	 * @throws ApplicationException
	 * @throws RecordNotFoundException
	 *             : if user not found
	 */
	/*
	 * @Override
	 * 
	 * @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 * public boolean forgetPassword(String login) { UserDTO userData =
	 * findByLogin(login); boolean flag = false; System.out.println(login); if
	 * (userData == null) { throw new
	 * RecordNotFoundException("Email Id Does not matched.");
	 * 
	 * }
	 * 
	 * HashMap<String, String> map = new HashMap<String, String>();
	 * map.put("login", userData.getLogin()); map.put("password",
	 * userData.getPassword()); map.put("firstName", userData.getFirstName());
	 * map.put("lastName", userData.getLastName()); String message =
	 * EmailBuilder.getForgetPasswordMessage(map); MimeMessage msg=
	 * mailSender.createMimeMessage(); MimeMessageHelper helper; try {
	 * helper=new MimeMessageHelper(msg,true);
	 * helper.setSubject("SUNARYS ORS Password reset"); helper.setText(message,
	 * true);
	 * 
	 * mailSender.send(msg); flag = true; } catch (MessagingException e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * return flag;
	 * 
	 * 
	 * 
	 * }
	 */

}
