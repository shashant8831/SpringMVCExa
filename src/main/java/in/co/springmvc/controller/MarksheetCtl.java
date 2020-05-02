package in.co.springmvc.controller;

import in.co.springmvc.dto.MarksheetDTO;
import in.co.springmvc.dto.StudentDTO;
import in.co.springmvc.exception.DuplicateRecordException;
import in.co.springmvc.form.MarksheetForm;
import in.co.springmvc.service.MarksheetServiceInt;
import in.co.springmvc.service.StudentServiceInt;
import in.co.springmvc.validator.MarksheetValidator;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
import org.springframework.web.servlet.ModelAndView;

/**
 * Contains navigation logics for Marksheet,Marksheet List,Marksheet Merit
 * List,Get Marksheet Usecases.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@Controller
@RequestMapping(value = "/ctl/Marksheet")
public class MarksheetCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(MarksheetCtl.class);

	@Autowired
	private MarksheetServiceInt service;

	@Autowired
	MarksheetValidator marksheetValidator;

	@Autowired
	ServletContext servletContext;

	/**
	 * i18n Message source
	 */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Display Add/Edit Marksheet View
	 * 
	 * @param id
	 * @param form
	 * @param model
	 * @return
	 */
	@Autowired
	StudentServiceInt sService;

	@Override
	public void preload(Model model) {
		model.addAttribute("studentList", sService.search(new StudentDTO()));
	}

	@RequestMapping(method = RequestMethod.GET)
	public String display(Locale locale,
			@RequestParam(required = false) Long id,
			@ModelAttribute("form") MarksheetForm form, Model model) {

		log.debug("In MarksheetCtl display start " + id);

		if (id != null && id > 0) {
			MarksheetDTO dto = service.findByPK(id);
			form.populate(dto);
		}
		return "Marksheet";

	}

	@RequestMapping(method = RequestMethod.POST)
	public String submit(Locale locale, @RequestParam String operation,
			@ModelAttribute("form") @Valid MarksheetForm form,
			BindingResult bindingResult, Model model) {

		log.debug("operation " + form.getOperation());

		/** CALLING CUSTOM VALIDATION **/
		marksheetValidator.validate(form, bindingResult);

		MarksheetDTO dto = (MarksheetDTO) form.getDto();

		try {
			if (OP_SAVE.equalsIgnoreCase(operation)) {

				if (bindingResult.hasErrors()) {
					return "Marksheet";
				}

				dto.setName(sService.findByPK(dto.getStudentId())
						.getFirstName());

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

				return "redirect:Marksheet/search";
			} else if (OP_CANCEL.equalsIgnoreCase(operation)) {

				if (dto.getId() > 0) {
					return "redirect:Marksheet/search";
				} else {
					return "redirect:Marksheet";
				}
			}

		} catch (DuplicateRecordException e) {
			String msg = messageSource.getMessage("message.RollNoExist", null,
					locale);
			model.addAttribute("error", msg);

		}

		return "Marksheet";
	}

	@RequestMapping(value = "/search", method = { RequestMethod.GET })
	public String searchList(@ModelAttribute("form") MarksheetForm form,
			Model model) {

		int i = service.search(new MarksheetDTO()).size();
		int size = 0;
		if (i % 5 == 0) {

			size = i / 5;
		} else {
			size = (i / 5) + 1;
		}
		model.addAttribute("size", size);

		model.addAttribute(
				"list",
				service.search(new MarksheetDTO(), form.getPageNo(),
						form.getPageSize()));
		return "MarksheetList";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String searchList(Locale locale,
			@ModelAttribute("form") MarksheetForm form,
			@RequestParam(required = false) Integer pageNO,
			@RequestParam(required = false) String operation, Model model) {

		log.debug("in Marksheetctl searchList method");

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
			return "redirect:/ctl/Marksheet";
		}

		if (OP_BACK.equals(operation)) {
			return "redirect:/Welcome";
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
			MarksheetDTO dto = (MarksheetDTO) form.getDto();
			// NO RECORD AND DELETE RECORD
			if (service.search(dto, pageNo, form.getPageSize()).size() == 0) {

				form.setPageNo(pageNo - 1);
				return "MarksheetList";

			}

		} else if (OP_DELETE.equals(operation) && form.getIds() == null) {
			String msg = messageSource.getMessage("message.select", null,
					locale);
			model.addAttribute("error", msg);

		}

		// Get search attributes
		MarksheetDTO dto = (MarksheetDTO) form.getDto();

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
		return "MarksheetList";
	}

	@RequestMapping(value = "/meritlist", method = RequestMethod.GET)
	public String getMeritList(@ModelAttribute("form") MarksheetForm form,
			Model model) {
		List meritList = service.getMeritList(0, 0);
		model.addAttribute("list", meritList);
		return "GetMeritList";
	}

	/*
	 * @RequestMapping(value = "/meritlist", method = RequestMethod.POST) public
	 * String getMeritList(Locale locale,
	 * 
	 * @ModelAttribute("form") MarksheetForm form,
	 * 
	 * @RequestParam(required = false) String operation, BindingResult
	 * bindingResult, Model model) {
	 * 
	 * List meritList = service.getMeritList(0, 0); model.addAttribute("list",
	 * meritList);
	 * 
	 * if (OP_BACK.equals(operation)) { return "redirect:/ctl/Welcome"; } return
	 * "GetMeritList"; }
	 */

	/**
	 * Gets Marksheet.
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String getMarksheet(@ModelAttribute("form") MarksheetForm form,
			Model model) {

		return "GetMarksheet";
	}

	@RequestMapping(value = "/get", method = { RequestMethod.POST })
	public String getMarksheet(Locale locale,
			@ModelAttribute("form") MarksheetForm form,
			@RequestParam(required = false) String operation,
			BindingResult bindingResult, Model model) {

		/*
		 * if (form.getRollNo() == null) { model.addAttribute("error",
		 * "Enter roll no to get marksheet "); }
		 * 
		 * if (bindingResult.hasErrors()) { return "Marksheet"; }
		 */

		MarksheetDTO dto = service.findByRollNo(form.getRollNo());

		if (dto != null) {
			form.populate(dto);
		} else {
			model.addAttribute("error", "Roll Number Not found");
		}
		return "GetMarksheet";
	}

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public ModelAndView reportMeritList(ModelAndView modelAndView) {

		List meritList = service.getMeritList(0, 0);
		System.out.println(meritList);
		java.util.Iterator it = meritList.iterator();
		while (it.hasNext()) {
			MarksheetDTO dto = (MarksheetDTO) it.next();
			dto.setTotal(dto.getPhysics() + dto.getChemistry() + dto.getMaths());
		}

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
				meritList);

		Map parameter = new HashMap();

		String footer = "Copyrights (c) "
				+ Calendar.getInstance().get(Calendar.YEAR)
				+ " Rays Technologies";
		parameter.put("FOOTER", (String) footer);
		parameter.put("CONTEXT", servletContext.getRealPath(""));
		parameter.put("datasource", dataSource);

		modelAndView = new ModelAndView("MeritList", parameter);

		return modelAndView;
	}
}
