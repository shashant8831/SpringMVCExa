package in.co.springmvc.service;

import in.co.springmvc.dao.UserDAOInt;
import in.co.springmvc.dto.UserDTO;
import in.co.springmvc.exception.ApplicationException;
import in.co.springmvc.exception.DatabaseException;
import in.co.springmvc.exception.DuplicateRecordException;
import in.co.springmvc.util.EmailBuilder;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Session facade of User Service. It is transactional, apply delcarative
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

@Service("userService")
public class UserServiceSpringImpl implements UserServiceInt {

	@Autowired
	private UserDAOInt dao;
	/*
	 * @Autowired private RoleDAOInt RoleDAO;
	 */
	@Autowired
	private JavaMailSenderImpl mailSender;

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * Adds a User
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long add(UserDTO dto) throws DuplicateRecordException {

		UserDTO dtoExist = dao.findByLogin(dto.getLogin());
		if (dtoExist != null) {
			throw new DuplicateRecordException("User already exist");
		}
		return dao.add(dto);
	}

	/**
	 * Updates a User
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when updated User is already exists
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(UserDTO dto) throws DuplicateRecordException {
		UserDTO dtoExist = dao.findByLogin(dto.getLogin());
		// Check if updated User is already exists
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Login Id already exists");
		}
		dao.update(dto);
	}

	/**
	 * Deletes a User
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
	public UserDTO findByPK(long id) {
		return dao.findByPK(id);
	}

	/**
	 * Finds User by Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	@Transactional(readOnly = true)
	public UserDTO findByName(String name) {
		return dao.findByName(name);
	}

	/**
	 * Searches Users
	 * 
	 * @return list : List of Users
	 * @param dto
	 *            : Search Parameters
	 * @throws ApplicationException
	 */
	@Transactional(readOnly = true)
	public List search(UserDTO dto) {
		return dao.search(dto);
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
	 * @throws ApplicationException
	 */
	@Transactional(readOnly = true)
	public List search(UserDTO dto, int pageNo, int pageSize) {
		return dao.search(dto, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public UserDTO authenticate(UserDTO dto) {
		UserDTO dtoExist = dao.authenticate(dto.getLogin(), dto.getPassword());

		if (dtoExist != null
				&& dtoExist.getPassword().equals(dto.getPassword())) {
			// Set last login date
			// dtoExist.setLastLogin(new Timestamp(new Date().getTime()));

			dtoExist.setLastLogin(new Timestamp(new Date().getTime()));
			dao.update(dtoExist);
			return dtoExist;
		}
		return dtoExist;
	}

	@Transactional(readOnly = true)
	public UserDTO findByLogin(String login) {
		// log.debug("Service findByLogin Started");
		UserDTO dto = dao.findByLogin(login);
		return dto;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Long registerUser(UserDTO dto) throws DuplicateRecordException {

		Long id = add(dto);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		MimeMessage msg = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(msg);
			helper.setTo(dto.getLogin());
			helper.setSubject("Registration is successful for ORS Project SUNRAYS Technologies.");
			// use the true flag to indicate the text included is HTML
			helper.setText(message, true);
			mailSender.send(msg);
		} catch (MessagingException e) {
			System.out.println("Mail Sending Failed");
			e.printStackTrace();
		}

		return id;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean changePassword(Long id, String oldPassword,
			String newPassword) throws ApplicationException {
		UserDTO dto = findByPK(id);
		if (oldPassword.equals(dto.getPassword())) {
			dto.setPassword(newPassword);
			dao.update(dto);
			return true;
		} else {
			return false;
		}

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public Boolean forgetPassword(String login) throws DatabaseException,
			ApplicationException {
		// log.debug("Service forgetPassword Started");

		UserDTO dtoExist = dao.findByLogin(login);

		if (dtoExist != null) {

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("firstName", dtoExist.getFirstName());
			map.put("lastName", dtoExist.getLastName());
			map.put("login", dtoExist.getLogin());
			map.put("password", dtoExist.getPassword());

			String message = EmailBuilder.getForgetPasswordMessage(map);
           
			MimeMessage msg = mailSender.createMimeMessage();

			try {
				MimeMessageHelper helper = new MimeMessageHelper(msg);
				helper.setTo(login);
				helper.setSubject("ORS Password reset");
				// use the true flag to indicate the text included is HTML
				helper.setText(message, true);
				mailSender.send(msg);
			} catch (MessagingException e) {
				e.printStackTrace();
				throw new ApplicationException(
						"Internet Connection Not working");
			}
		} else {
			throw new DatabaseException("Email Id does not exists");
		}
		return true;
	}

	/*
	 * @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 * public void registerUser(UserDTO dto) {
	 * 
	 * 
	 * HashMap<String, String> map=new HashMap<String,String>();
	 * map.put("login", dto.getLogin()); map.put("password", dto.getPassword());
	 * String message=EmailBuilder.getUserRegistrationMessage(map);
	 * 
	 * MimeMessage msg=mailSender.createMimeMessage();
	 * 
	 * MimeMessageHelper helper; try { helper=new MimeMessageHelper(msg,true);
	 * 
	 * helper.setTo(dto.getLogin()); helper.setSubject(
	 * "Registration is successful for ORS Project SUNRAYS Technologies");
	 * helper.setText(message,true); mailSender.send(msg); } catch (Exception e)
	 * {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * }
	 */

}
