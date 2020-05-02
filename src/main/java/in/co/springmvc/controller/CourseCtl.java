package in.co.springmvc.controller;

import in.co.springmvc.dto.CourseDTO;
import in.co.springmvc.dto.FacultyDTO;
import in.co.springmvc.exception.DuplicateRecordException;
import in.co.springmvc.form.CourseForm;
import in.co.springmvc.service.CourseServiceInt;
import in.co.springmvc.util.DataValidator;
import in.co.springmvc.validator.CourseValidator;

import java.util.Locale;

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
 * Contains navigation logics for Course and Course List Usecases.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@Controller
@RequestMapping(value = "/ctl/Course")
public class CourseCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(CourseCtl.class);

	@Autowired
	private CourseServiceInt service;

	@Autowired
	CourseValidator courseValidator;

	/**
	 * i18n Message source
	 */
	@Autowired
	private MessageSource messageSource;

	/******************************* DISPLAY LOGIC(GET) ************************************/
	/**
	 * Displays Course view.
	 * 
	 * @param id
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String display(@RequestParam(required = false) Long id,
			@ModelAttribute("form") CourseForm form, Model model) {

		log.debug("CourseCtl doDisplay() Start");

		if (id != null && id > 0) {
			form.populate(service.findByPK(id));
		}
		return "Course";
	}

	/******************************* SUBMIT LOGIC(POST) ************************************/
	/**
	 * Submits Course data.
	 * 
	 * @param locale
	 * @param operation
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @return
	 */

	@RequestMapping(method = RequestMethod.POST)
	public String submit(Locale locale,
			@RequestParam(required = false) String operation,
			@ModelAttribute("form") @Valid CourseForm form,
			BindingResult bindingResult, Model model) {

		log.debug("CourseCtl doSubmit() Start");

		/** CALLING CUSTOM VALIDATION **/
		courseValidator.validate(form, bindingResult);

		try {
			if (OP_SAVE.equalsIgnoreCase(operation)) {

				if (bindingResult.hasErrors()) {
					return "Course";
				}

				CourseDTO dto = (CourseDTO) form.getDto();

				if (dto.getId() > 0) {
					service.update(dto);
				} else {
					long id = service.add(dto);
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
				return "redirect:Course/search";
			} else if (OP_CANCEL.equalsIgnoreCase(operation)) {
				CourseDTO dto = (CourseDTO) form.getDto();
				if (dto.getId() > 0) {

					return "redirect:Course/search";
				} else {
					return "redirect:Course";
				}
			}

		} catch (DuplicateRecordException e) {

			String msg = messageSource.getMessage("message.courseExist", null,
					locale);
			model.addAttribute("error", msg);

		}

		return "Course";
	}

	/******************************* ROLE LIST VIEW(GET) ************************************/
	/**
	 * Displays Course List view.
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchList(@ModelAttribute("form") CourseForm form,
			Model model) {

		int i = service.search(new CourseDTO()).size();
		int size = 0;
		if (i % 5 == 0) {

			size = i / 5;
		} else {
			size = (i / 5) + 1;
		}
		model.addAttribute("size", size);

		model.addAttribute(
				"list",
				service.search(new CourseDTO(), form.getPageNo(),
						form.getPageSize()));
		return "CourseList";
	}

	/******************************* COURSE LIST VIEW(POST) **********************************/
	/**
	 * Submits Course List data.
	 * 
	 * @param locale
	 * @param form
	 * @param operation
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search", method = { RequestMethod.POST })
	public String searchList(Locale locale,
			@ModelAttribute("form") CourseForm form,
			@RequestParam(required = false) Integer pageNO,
			@RequestParam(required = false) String operation, Model model) {

		log.debug("in searchList method");

		// Calculate next page number
		int pageNo = form.getPageNo();
		if (pageNO != null && pageNO > 0) {
			pageNo = pageNO;

		}

		if (OP_SEARCH.equalsIgnoreCase(operation)
				|| "Next".equalsIgnoreCase(operation)
				|| "Previous".equalsIgnoreCase(operation)) {

			if (OP_SEARCH.equalsIgnoreCase(operation)) {
				pageNo = 1;
			} else if (OP_NEXT.equalsIgnoreCase(operation)) {
				pageNo++;
			} else if (OP_PREVIOUS.equalsIgnoreCase(operation) && pageNo > 1) {
				pageNo--;
			}

		}

		if (OP_NEW.equals(operation)) {
			return "redirect:/ctl/Course";
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
			CourseDTO dto = (CourseDTO) form.getDto();
			// NO RECORD AND DELETE RECORD
			if (service.search(dto, pageNo, form.getPageSize()).size() == 0) {

				form.setPageNo(pageNo - 1);
				return "CourseList";

			}

		} else if (OP_DELETE.equals(operation) && form.getIds() == null) {
			String msg = messageSource.getMessage("message.select", null,
					locale);
			model.addAttribute("error", msg);

		}

		// Get search attributes
		CourseDTO dto = (CourseDTO) form.getDto();

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
		return "CourseList";
	}

}
