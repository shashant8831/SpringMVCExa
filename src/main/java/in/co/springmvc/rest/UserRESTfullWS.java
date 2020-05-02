package in.co.springmvc.rest;

import java.util.List;

import in.co.springmvc.controller.BaseCtl;
import in.co.springmvc.dto.UserDTO;
import in.co.springmvc.form.UserForm;
import in.co.springmvc.service.UserServiceInt;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User RESTFul Web Service.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@Controller
@RequestMapping(value = "/rest/User")
public class UserRESTfullWS extends BaseCtl {

	private static Logger log = Logger.getLogger(UserRESTfullWS.class);

	@Autowired
	private UserServiceInt service;

	/**
	 * Gets User information
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public UserDTO get(@PathVariable long id) {
		log.debug("UserCtl doDisplay() Start");
		UserDTO dto = service.findByPK(id);
		return dto;
	}

	/**
	 * Gets User list
	 * 
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public List<UserDTO> getList(UserForm form) {
		UserDTO dto = (UserDTO) form.getDto();
		return service.search(dto, form.getPageNo(), form.getPageSize());
	}

	/**
	 * Deletes a User
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	public UserDTO delete(@PathVariable long id) throws Exception {
		UserDTO dto = service.findByPK(id);
		service.delete(dto.getId());
		return dto;

	}

}
