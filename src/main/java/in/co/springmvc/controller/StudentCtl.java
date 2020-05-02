package in.co.springmvc.controller;

import in.co.springmvc.dto.CollegeDTO;
import in.co.springmvc.dto.StudentDTO;
import in.co.springmvc.form.StudentForm;
import in.co.springmvc.service.CollegeServiceInt;
import in.co.springmvc.service.StudentServiceInt;
import in.co.springmvc.validator.StudentValidator;

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
 * Contains navigation logics for Student and Student List UseCases.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@Controller
@RequestMapping(value = "/ctl/Student")
public class StudentCtl extends BaseCtl {

	/**
	 * Logger object
	 */
	private static Logger log = Logger.getLogger(StudentCtl.class);
	@Autowired
	StudentServiceInt service;

	@Autowired
	CollegeServiceInt collegeService;
	@Autowired
	StudentValidator studentValidator;
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
	}

	/**
	 * Displays Student View.
	 * 
	 * @param id
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String display(@RequestParam(required = false) Long id,
			@ModelAttribute("form") StudentForm form, Model model) {
		if (id != null && id > 0) {
			form.populate(service.findByPK(id));
		}
		return "Student";
	}

	/**
	 * Submits Student data.
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
			@ModelAttribute("form") @Valid StudentForm form,
			BindingResult bindingResult, Model model, HttpSession session) {

		log.debug("operation " + form.getOperation());
		/** CALLING CUSTOM VALIDATION **/
		studentValidator.validate(form, bindingResult);
		StudentDTO dto = (StudentDTO) form.getDto();
		try {
			if (OP_SAVE.equalsIgnoreCase(operation)) {

				if (bindingResult.hasErrors()) {
					return "Student";
				}

				CollegeDTO cdto = collegeService.findByPK(form.getCollegeId());
				dto.setCollegeName(cdto.getName());

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

				return "redirect:Student/search";
			} else if (OP_CANCEL.equalsIgnoreCase(operation)) {
				if (dto.getId() > 0) {
					return "redirect:Student/search";
				} else {
					return "redirect:Student";
				}

			}

		} catch (Exception e) {
			String msg = messageSource.getMessage("message.studentExist", null,
					locale);
			model.addAttribute("error", msg);

		}

		return "Student";
	}

	/**
	 * Displays Student List view.
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchList(@ModelAttribute("form") StudentForm form,
			Model model) {
		int i = service.search(new StudentDTO()).size();
		int size = 0;
		if (i % 5 == 0) {

			size = i / 5;
		} else {
			size = (i / 5) + 1;
		}
		model.addAttribute("size", size);

		model.addAttribute(
				"list",
				service.search(new StudentDTO(), form.getPageNo(),
						form.getPageSize()));
		return "StudentList";
	}

	/**
	 * Submits Student List data.
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
			@ModelAttribute("form") StudentForm form,
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
			return "redirect:/ctl/Student";
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
			StudentDTO dto = (StudentDTO) form.getDto();
			// NO RECORD AND DELETE RECORD
			if (service.search(dto, pageNo, form.getPageSize()).size() == 0) {

				form.setPageNo(pageNo - 1);
				return "StudentList";

			}

		} else if (OP_DELETE.equals(operation) && form.getIds() == null) {
			String msg = messageSource.getMessage("message.select", null,
					locale);
			model.addAttribute("error", msg);

		}

		// Get search attributes
		StudentDTO dto = (StudentDTO) form.getDto();

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
		return "StudentList";
	}

}
