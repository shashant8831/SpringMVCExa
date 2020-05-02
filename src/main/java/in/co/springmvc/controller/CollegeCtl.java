package in.co.springmvc.controller;

import in.co.springmvc.dto.CollegeDTO;
import in.co.springmvc.exception.DuplicateRecordException;
import in.co.springmvc.form.CollegeForm;
import in.co.springmvc.service.CollegeServiceInt;
import in.co.springmvc.validator.CollegeValidator;

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
 * Contains navigation logics for College and College List useCases.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@Controller
@RequestMapping(value = "/ctl/College")
public class CollegeCtl extends BaseCtl {

	/**
	 * Logger object
	 */
	private static Logger log = Logger.getLogger(CollegeCtl.class);

	/**
	 * College Service
	 */
	@Autowired
	private CollegeServiceInt service;
	@Autowired
	CollegeValidator collegeValidator;

	/**
	 * i18n Message source
	 */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Display Add/Edit College View
	 * 
	 * @param id
	 * @param form
	 * @param model
	 * @return
	 */

	@RequestMapping(method = RequestMethod.GET)
	public String display(Locale locale,
			@RequestParam(required = false) Long id,
			@ModelAttribute("form") CollegeForm form, Model model) {

		log.debug("In CollegeCtl display start " + id);

		if (id != null && id > 0) {
			CollegeDTO dto = service.findByPK(id);
			form.populate(dto);
		}
		return "College";

	}

	/**
	 * Contains submit logic of Add/Edit College View
	 * 
	 * @param operation
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String submit(Locale locale, @RequestParam String operation,
			@ModelAttribute("form") @Valid CollegeForm form,
			BindingResult bindingResult, Model model) {

		log.debug("operation " + form.getOperation());

		/** CALLING CUSTOM VALIDATION **/
		collegeValidator.validate(form, bindingResult);

		CollegeDTO dto = (CollegeDTO) form.getDto();

		try {
			if (OP_SAVE.equalsIgnoreCase(operation)) {
				if (bindingResult.hasErrors()) {
					return "College";
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

				return "redirect:College/search";
			} else if (OP_CANCEL.equalsIgnoreCase(operation)) {
				CollegeDTO dto1 = (CollegeDTO) form.getDto();
				if (dto1.getId() > 0) {

					return "redirect:College/search";
				} else {
					return "redirect:College";
				}
			}

		} catch (DuplicateRecordException e) {
			String msg = messageSource.getMessage("message.collegeExist", null,
					locale);
			model.addAttribute("error", msg);

		}

		return "College";
	}

	/**
	 * Displays College List View
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search", method = { RequestMethod.GET })
	public String searchList(@ModelAttribute("form") CollegeForm form,
			Model model) {
		int i = service.search(new CollegeDTO()).size();
		int size = 0;
		if (i % 5 == 0) {

			size = i / 5;
		} else {
			size = (i / 5) + 1;
		}
		model.addAttribute("size", size);

		model.addAttribute(
				"list",
				service.search(new CollegeDTO(), form.getPageNo(),
						form.getPageSize()));
		return "CollegeList";
	}

	/**
	 * Contains submit logics of College List View
	 * 
	 * @param form
	 * @param operation
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String searchList(Locale locale,
			@ModelAttribute("form") CollegeForm form,
			@RequestParam(required = false) Integer pageNO,
			@RequestParam(required = false) String operation, Model model) {

		log.debug("in collegectl searchList method");

		// Calculate next page number
		int pageNo = form.getPageNo();
		if (pageNO != null && pageNO > 0) {
			pageNo = pageNO;

		}

		if (OP_NEXT.equals(operation)) {
			pageNo++;
		} else if (OP_PREVIOUS.equals(operation)) {
			pageNo--;
		} else if (OP_SEARCH.equals(operation)) {
			pageNo = 1;
		}

		if (OP_NEW.equals(operation)) {
			return "redirect:/ctl/College";

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
			CollegeDTO dto = (CollegeDTO) form.getDto();
			// NO RECORD AND DELETE RECORD
			if (service.search(dto, pageNo, form.getPageSize()).size() == 0) {

				form.setPageNo(pageNo - 1);
				return "CollegeList";

			}

		} else if (OP_DELETE.equals(operation) && form.getIds() == null) {
			String msg = messageSource.getMessage("message.select", null,
					locale);
			model.addAttribute("error", msg);

		}

		// Get search attributes
		CollegeDTO dto = (CollegeDTO) form.getDto();

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

		return "CollegeList";
	}

}
