package in.co.springmvc.controller;

import in.co.springmvc.dto.RoleDTO;
import in.co.springmvc.dto.UserDTO;
import in.co.springmvc.exception.DatabaseException;
import in.co.springmvc.exception.DuplicateRecordException;
import in.co.springmvc.form.ForgetPasswordForm;
import in.co.springmvc.form.LoginForm;
import in.co.springmvc.form.UserRegistrationForm;
import in.co.springmvc.service.RoleServiceInt;
import in.co.springmvc.service.UserServiceInt;
import in.co.springmvc.validator.UserRegistrationValidator;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
 * Contains navigation logics for Login, Forgot Password and SignUp Usecases.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@Controller
public class LoginCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(LoginCtl.class);

	/**
	 * Operations
	 */
	protected static final String OP_SIGNIN = "SignIn";
	protected static final String OP_SIGNUP = "SignUp";

	@Autowired
	private UserServiceInt service;

	@Autowired
	private RoleServiceInt rService;
	/**
	 * i18n Message source
	 */
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserRegistrationValidator userRegistrationValidator;

	/**
	 * Displays Login View.
	 * 
	 * @param form
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/Login", method = RequestMethod.GET)
	public String display(@ModelAttribute("form") LoginForm form,
			HttpSession session, Model model) {
		log.debug("Login Submit Started");
		session.invalidate();
		return "Login";
	}

	@RequestMapping(value = "/Logout", method = RequestMethod.GET)
	public String logoutDisplay(@ModelAttribute("form") LoginForm form,
			HttpSession session, Model model, Locale locale) {
		log.debug("Logout Submit Started");

		session.invalidate();
		String msg = messageSource.getMessage("message.logout", null, locale);
		model.addAttribute("error", msg);
		return "Login";
	}

	/**
	 * Submits Login data.
	 * 
	 * @param form
	 * @param bindingResult
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/Login", method = { RequestMethod.POST })
	public String submit(Locale locale,
			@ModelAttribute("form") @Valid LoginForm form,
			BindingResult bindingResult, HttpSession session, Model model) {

		log.debug("Login Submit Started");

		System.out.println("result Fail :" + bindingResult.hasErrors());

		if (OP_SIGNIN.equalsIgnoreCase(form.getOperation())) {
			if (bindingResult.hasErrors()) {
				return "Login";
			}

			UserDTO dto = new UserDTO();
			dto.setLogin(form.getEmailId());
			dto.setPassword(form.getPassword());
			System.out.println("Password isme " + form.getPassword());
			dto = service.authenticate(dto);
			// System.out.println(dto.getPassword());
			if (dto != null) {
				session.setAttribute("user", dto);
				session.setAttribute("role", rService.findByPK(dto.getRoleId())
						.getName());
				model.addAttribute("message", "Welcome :" + form.getEmailId());

				return "redirect:/Welcome";
			} else {
				// Get error messages for message respurce
				String msg = messageSource.getMessage("login.error", null,
						locale);
				model.addAttribute("error", msg);
			}
		}

		log.debug("Login Submit End");
		return "Login";

	}

	/**
	 * Displays ForgetPAssword View
	 * 
	 * @param form
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "/ForgetPassword", method = RequestMethod.GET)
	public String display(@ModelAttribute("form") ForgetPasswordForm form,
			Model model) {
		return "ForgetPassword";
	}

	/**
	 * Submits ForgetPassword data.
	 * 
	 * @param locale
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ForgetPassword", method = RequestMethod.POST)
	public String submit(Locale locale,
			@ModelAttribute("form") @Valid ForgetPasswordForm form,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "ForgetPassword";
		}

		try {
			service.forgetPassword(form.getLogin());
			model.addAttribute("success",
					"Password has been sent to your registered Email ID!!");

		} catch (DatabaseException e) {
			String msg = messageSource.getMessage("forgetpass.error", null,
					locale);
			model.addAttribute("error", msg);
		} catch (Exception e) {
			log.error("Critical Issue ", e);
			model.addAttribute("error", " " + e.getMessage());

			return "Error";
		}

		return "ForgetPassword";
	}

	/**
	 * Displays SignUp view
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/SignUp", method = RequestMethod.GET)
	public String display(@ModelAttribute("form") UserRegistrationForm form,
			Model model) {

		return "UserRegistration";
	}

	/**
	 * Submits SignUp data
	 * 
	 * @param locale
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 * @throws DuplicateRecordException
	 */
	@RequestMapping(value = "/SignUp", method = RequestMethod.POST)
	public String submit(Locale locale, @RequestParam String operation,
			@ModelAttribute("form") @Valid UserRegistrationForm form,
			BindingResult bindingResult, Model model,
			HttpServletRequest request, HttpServletResponse response) {

		log.debug("operation " + form.getOperation());

		System.out.println("OPERATION" + form.getOperation());

		userRegistrationValidator.validate(form, bindingResult);

		if (OP_SIGNUP.equalsIgnoreCase(operation)) {

			if (bindingResult.hasErrors()) {
				return "UserRegistration";
			}

			UserDTO dto = (UserDTO) form.getDto();
			dto.setRoleId(RoleDTO.STUDENT);

			try {
				service.registerUser(dto);
				String msg = messageSource.getMessage("message.register", null,
						locale);
				model.addAttribute("success", msg);
				/* return "redirect:Login"; */

			} catch (DuplicateRecordException e) {
				e.printStackTrace();
				String msg = messageSource.getMessage("message.exist", null,
						locale);
				model.addAttribute("error", msg);
				return "UserRegistration";

			}

			catch (Exception e) {
				model.addAttribute("error", " " + e.getMessage());

				return "Error";

			}

		}
		if (OP_CANCEL.equalsIgnoreCase(operation)) {

			return "UserRegistration";

		}
		return "forward:/Login";

	}
}
