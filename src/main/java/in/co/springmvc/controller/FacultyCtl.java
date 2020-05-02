package in.co.springmvc.controller;

import in.co.springmvc.dto.CollegeDTO;
import in.co.springmvc.dto.CourseDTO;
import in.co.springmvc.dto.FacultyDTO;
import in.co.springmvc.exception.DuplicateRecordException;
import in.co.springmvc.form.FacultyForm;
import in.co.springmvc.service.CollegeServiceInt;
import in.co.springmvc.service.CourseServiceInt;
import in.co.springmvc.service.FacultyServiceInt;
import in.co.springmvc.validator.FacultyValidator;

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
 * Contains navigation logics for Faculty and Faculty List UseCases.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@Controller
@RequestMapping(value = "/ctl/Faculty")
public class FacultyCtl extends BaseCtl {

	/**
	 * Logger object
	 */
	private static Logger log = Logger.getLogger(FacultyCtl.class);

	@Autowired
	FacultyServiceInt service;

	@Autowired
	CollegeServiceInt collegeService;

	@Autowired
	CourseServiceInt courseService;

	@Autowired
	FacultyValidator facultyValidator;

	/**
	 * i18n Message source
	 */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Preload College List
	 */
	@Override
	public void preload(Model model) {
		model.addAttribute("collegeList",
				collegeService.search(new CollegeDTO()));
		model.addAttribute("courseList", courseService.search(new CourseDTO()));
	}

	/**
	 * Displays Faculty View.
	 * 
	 * @param id
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String display(@RequestParam(required = false) Long id,
			@ModelAttribute("form") FacultyForm form, Model model) {
		if (id != null && id > 0) {
			form.populate(service.findByPK(id));
		}
		return "Faculty";
	}

	/**
	 * Submits Faculty data.
	 * 
	 * @param locale
	 * @param operation
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String submit(Locale locale, @RequestParam String operation,
			@ModelAttribute("form") @Valid FacultyForm form,
			BindingResult bindingResult, Model model) {

		log.debug("operation " + form.getOperation());

		/** CALLING CUSTOM VALIDATION **/
		facultyValidator.validate(form, bindingResult);
		FacultyDTO dto = (FacultyDTO) form.getDto();

		try {

			if (OP_SAVE.equalsIgnoreCase(operation)) {

				if (bindingResult.hasErrors()) {
					return "Faculty";
				}

				if (dto.getId() > 0) {

					service.update(dto);

				} else {
					System.out.println(dto.getEmail());
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

				return "redirect:Faculty/search";
			} else if (OP_CANCEL.equalsIgnoreCase(operation)) {
				/* FacultyDTO dto1 = (FacultyDTO) form.getDto(); */
				if (dto.getId() > 0) {
					return "redirect:Faculty/search";
				} else {
					return "redirect:Faculty";
				}

			}
		} catch (DuplicateRecordException e) {
			String msg = messageSource.getMessage("faculty.exists", null,
					locale);
			model.addAttribute("error", msg);

		}

		return "Faculty";
	}

	/**
	 * Displays Faculty List view.
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchList(@ModelAttribute("form") FacultyForm form,
			Model model) {
		int i = service.search(new FacultyDTO()).size();
		int size = 0;
		if (i % 5 == 0) {

			size = i / 5;
		} else {
			size = (i / 5) + 1;
		}
		model.addAttribute("size", size);

		model.addAttribute(
				"list",
				service.search(new FacultyDTO(), form.getPageNo(),
						form.getPageSize()));
		return "FacultyList";
	}

	/**
	 * Submits Faculty List data.
	 * 
	 * @param locale
	 * @param form
	 * @param operation
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String searchList(Locale locale,
			@ModelAttribute("form") FacultyForm form,
			@RequestParam(required = false) Integer pageNO,
			@RequestParam(required = false) String operation, Model model)
			throws Exception {

		log.debug("in searchList method");

		// Calculate next page number
		int pageNo = form.getPageNo();
		if (pageNO != null && pageNO > 0) {
			pageNo = pageNO;

		}
		if (OP_NEXT.equals(operation)) {
			pageNo++;
		} else if (OP_PREVIOUS.equals(operation)) {
			pageNo--;
		}

		if (OP_NEW.equals(operation)) {
			return "redirect:/ctl/Faculty";
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
			FacultyDTO dto = (FacultyDTO) form.getDto();
			// NO RECORD AND DELETE RECORD
			if (service.search(dto, pageNo, form.getPageSize()).size() == 0) {

				form.setPageNo(pageNo - 1);
				return "FacultyList";

			}

		} else if (OP_DELETE.equals(operation) && form.getIds() == null) {
			String msg = messageSource.getMessage("message.select", null,
					locale);
			model.addAttribute("error", msg);

		}

		// Get search attributes
		FacultyDTO dto = (FacultyDTO) form.getDto();

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
		return "FacultyList";
	}

}
