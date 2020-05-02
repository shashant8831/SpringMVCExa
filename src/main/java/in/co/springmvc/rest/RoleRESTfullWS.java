package in.co.springmvc.rest;

import in.co.springmvc.controller.BaseCtl;
import in.co.springmvc.dto.RoleDTO;
import in.co.springmvc.form.RoleForm;
import in.co.springmvc.service.RoleServiceInt;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Role RESTFull Web Service
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@RestController
@RequestMapping(value = "/rest/Role")
public class RoleRESTfullWS extends BaseCtl {

	private static Logger log = Logger.getLogger(RoleRESTfullWS.class);

	@Autowired
	private RoleServiceInt service;

	/**
	 * Gets Role information
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public RoleDTO get(@PathVariable long id) {
		log.debug("RoleCtl doDisplay() Start");
		RoleDTO dto = service.findByPK(id);
		return dto;
	}

	/**
	 * Deletes a Role
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	public RoleDTO delete(@PathVariable long id) throws Exception {
		RoleDTO dto = service.findByPK(id);
		service.delete(id);
		return dto;

	}

	/**
	 * Gets role list
	 * 
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public List getList(RoleForm form) {
		RoleDTO dto = (RoleDTO) form.getDto();
		return service.search(dto, form.getPageNo(), form.getPageSize());
	}

}
