package in.co.springmvc.controller;

import in.co.springmvc.dto.RoleDTO;
import in.co.springmvc.dto.UserDTO;
import in.co.springmvc.exception.DuplicateRecordException;
import in.co.springmvc.form.ChangePasswordForm;
import in.co.springmvc.form.MyProfileForm;
import in.co.springmvc.form.UserForm;
import in.co.springmvc.service.RoleServiceInt;
import in.co.springmvc.service.UserServiceInt;
import in.co.springmvc.validator.ChangePasswordValidator;
import in.co.springmvc.validator.MyProfileValidator;
import in.co.springmvc.validator.UserValidator;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Contains navigation logics for User, UserList, MyProfile, ChangePassword,
 * UseCases.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@Controller
@RequestMapping(value = "/ctl/User")
public class UserCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(UserCtl.class);

	@Autowired
	private UserServiceInt service;

	@Autowired
	private RoleServiceInt roleService;

	@Autowired
	UserValidator userValidator;

	@Autowired
	MyProfileValidator myProfileValidator;

	@Autowired
	ChangePasswordValidator changePasswordValidator;

	/**
	 * i18n Message source
	 */
	@Autowired
	private MessageSource messageSource;

	/*************************** PRELOAD ROLE LIST ON USER VIEW **************************/

	/**
	 * Preload Role List
	 */
	@Override
	public void preload(Model model) {
		// Preload Role List
		List list = roleService.search(new RoleDTO());
		model.addAttribute("roleList", list);
	}

	/******************************* DISPLAY LOGIC(GET) ************************************/

	/**
	 * Displayes User view.
	 * 
	 * @param id
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String display(@RequestParam(required = false) Long id,
			@ModelAttribute("form") UserForm form, Model model) {

		log.debug("In UserCtl display start " + id);

		if (id != null && id > 0) {
			UserDTO dto = service.findByPK(id);
			form.populate(dto);
		}
		return "User";

	}

	/******************************* SUBMISSION LOGIC(POST) *******************************/

	/**
	 * Submits User data.
	 * 
	 * @param locale
	 * @param operation
	 * @param form
	 * @param bindingResult
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String submit(Locale locale, @RequestParam String operation,
			@ModelAttribute("form") @Valid UserForm form,
			BindingResult bindingResult, Model model) {

		log.debug("operation " + form.getOperation());

		/** CALLING CUSTOM VALIDATION **/
		userValidator.validate(form, bindingResult);

		UserDTO dto = (UserDTO) form.getDto();

		try {
			if (OP_SAVE.equalsIgnoreCase(operation)) {

				if (bindingResult.hasErrors()) {
					return "User";
				}

				if (dto.getId() > 0) {
					service.update(dto);
				} else {
					Long id = service.add(dto);
					// form.setId(id);
				}

				String msg = messageSource.getMessage("message.success", null,
						locale);
				model.addAttribute("success", msg);

			} else if (OP_DELETE.equalsIgnoreCase(operation)) {
				service.delete(form.getId());

				String msg = messageSource.getMessage("message.success", null,
						locale);
				model.addAttribute("success", msg);
				return "redirect:User/search";
			} else if (OP_CANCEL.equalsIgnoreCase(operation)) {
				UserDTO dto1 = (UserDTO) form.getDto();
				if (dto1.getId() > 0) {

					return "redirect:User/search";
				} else {
					return "redirect:User";
				}
			}

		} catch (DuplicateRecordException e) {
			String msg = messageSource
					.getMessage("message.exist", null, locale);
			model.addAttribute("error", msg);

		}

		return "User";
	}

	/******************************* DISPLAY USER LIST VIEW(GET) ***************************/

	/**
	 * Displays User List
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchList(@ModelAttribute("form") UserForm form,
			@RequestParam(required = false) String operation, Model model) {

		/*
		 * if (OP_NEW.equals(operation)) { return "redirect:User"; } else if
		 * (OP_CANCEL.equals(operation)) { return "redirect:User/search"; }
		 */

		int i = service.search(new UserDTO()).size();
		int size = 0;
		if (i % 5 == 0) {

			size = i / 5;
		} else {
			size = (i / 5) + 1;
		}
		model.addAttribute("size", size);

		model.addAttribute(
				"list",
				service.search(new UserDTO(), form.getPageNo(),
						form.getPageSize()));
		return "UserList";
	}

	/**************** USER LIST VIEW(POST):SEARCH OPERATION ****************/

	/**
	 * Submits User List data.
	 * 
	 * @param locale
	 * @param form
	 * @param operation
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String searchList(Locale locale,
			@ModelAttribute("form") UserForm form,
			@RequestParam(required = false) Integer pageNO,
			@RequestParam(required = false) String operation, Model model) {

		log.debug("in searchList method");

		int pageNo = form.getPageNo();
		if (pageNO != null && pageNO > 0) {
			pageNo = pageNO;

		}

		/*
		 * if (OP_NEXT.equals(operation)) { pageNo++; } else if
		 * (OP_PREVIOUS.equals(operation)) { pageNo--; } else if
		 * (OP_SEARCH.equals(operation)) { pageNo = 1; }
		 */

		if (OP_SEARCH.equalsIgnoreCase(operation)
				|| "Next".equalsIgnoreCase(operation)
				|| "Previous".equalsIgnoreCase(operation)) {

			if (OP_SEARCH.equalsIgnoreCase(operation)) {
				pageNo = 1;
			} else if (OP_NEXT.equalsIgnoreCase(operation)) {
				pageNo++;
			} else if (OP_PREVIOUS.equalsIgnoreCase(operation) && pageNo >= 1) {
				pageNo--;
			}

		}

		if (OP_NEW.equals(operation)) {
			return "redirect:/ctl/User";
		}

		pageNo = (pageNo < 1) ? 1 : pageNo;

		form.setPageNo(pageNo);

		if (OP_DELETE.equals(operation) && form.getIds() != null) {
			for (long id : form.getIds()) {
				service.delete(id);
			}

			String msg = messageSource.getMessage("message.success", null,
					locale);
			model.addAttribute("success", msg);
			// NO RECORD AND DELETE RECORD
			UserDTO dto = (UserDTO) form.getDto();
			if (service.search(dto, pageNo, form.getPageSize()).size() == 0) {

				form.setPageNo(pageNo - 1);
				return "UserList";

			}

			// Select Record Messsage
		} else if (OP_DELETE.equals(operation) && form.getIds() == null) {
			String msg = messageSource.getMessage("message.select", null,
					locale);
			model.addAttribute("error", msg);
		}

		// Get search attributes
		UserDTO dto = (UserDTO) form.getDto();

		model.addAttribute("list",
				service.search(dto, pageNo, form.getPageSize()));
		int i = service.search(dto).size();
		int size = 0;
		if (i % 5 == 0) {
			size = i / 5;
		} else {
			size = (i / 5) + 1;
		}
		if (i == 0) {
			String msg = messageSource.getMessage("error.notFound", null,
					locale);
			model.addAttribute("error", msg);
		}
		model.addAttribute("size", size);

		return "UserList";

	}

	/******************************* DISPLAY LOGIC(GET):MY PROFILE ***********************/

	/**
	 * Displays MyProfile View
	 * 
	 * @param session
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String displayProfile(HttpSession session,
			@ModelAttribute("form") MyProfileForm form, Model model) {
		log.debug("MyProfileCtl start");
		UserDTO dto = (UserDTO) session.getAttribute("user");
		form.populate(dto);
		return "MyProfile";
	}

	/********************** SUBMISSION LOGIC(POST):EDIT MY PROFILE *************************/

	/**
	 * Submits MyProfile
	 * 
	 * @param session
	 * @param form
	 * @param model
	 * @return
	 * @throws DuplicateRecordException
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String submitProfile(Locale locale,
			@ModelAttribute("form") @Valid MyProfileForm form,
			BindingResult bindingResult, Model model)
			throws DuplicateRecordException {

		/** CALLING CUSTOM VALIDATION **/
		myProfileValidator.validate(form, bindingResult);

		if (bindingResult.hasErrors()) {
			return "MyProfile";
		}

		UserDTO dto = service.findByPK(form.getId());
		dto.setFirstName(form.getFirstName());
		dto.setLastName(form.getLastName());
		dto.setDob(form.getDob());
		dto.setMobileNo(form.getMobileNo());
		dto.setGender(form.getGender());

		service.update(dto);
		String msg = messageSource.getMessage("message.success", null, locale);
		model.addAttribute("success", msg);

		return "MyProfile";
	}

	/************************* DISPLAY LOGIC(GET):CHANGE PASSWORD ************************/

	/**
	 * Displays Change Password view.
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/changepassword", method = RequestMethod.GET)
	public String displayChangePassword(
			@ModelAttribute("form") ChangePasswordForm form, Model model) {
		log.debug("displayChangePassword start");
		return "ChangePassword";
	}

	/******************** SUBMISSION LOGIC(POST):CHANGE PASSWORD ***************************/

	/**
	 * Submits Change Password data.
	 * 
	 * @param session
	 * @param form
	 * @param model
	 * @return
	 * @throws DuplicateRecordException
	 */
	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public String submitChangePassword(Locale locale, HttpSession session,
			@ModelAttribute("form") @Valid ChangePasswordForm form,
			BindingResult bindingResult, Model model)
			throws DuplicateRecordException {

		/** CALLING CUSTOM VALIDATION **/
		changePasswordValidator.validate(form, bindingResult);

		if (bindingResult.hasErrors()) {
			return "ChangePassword";
		}

		// New password and confirm password must be same
		if (form.getNewPassword().equals(form.getConfirmPassword())) {

			UserDTO dto = (UserDTO) session.getAttribute("user");
			dto = service.findByPK(dto.getId());

			// Old password must be valid
			if (dto.getPassword().equals(form.getOldPassword())) {
				// Change Password
				dto.setPassword(form.getNewPassword());
				service.update(dto);
				String msg = messageSource.getMessage("message.success", null,
						locale);
				model.addAttribute("success", msg);
			} else {
				String msg = messageSource.getMessage("message.invalidOld",
						null, locale);
				model.addAttribute("error", msg);
				/* model.addAttribute("error", "Old Password is not valid."); */
			}
		}
		return "ChangePassword";
	}

}
