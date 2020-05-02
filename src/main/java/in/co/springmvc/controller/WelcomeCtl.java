package in.co.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Contains navigation logics for Welcome.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@Controller
@RequestMapping("/Welcome")
public class WelcomeCtl extends BaseCtl {

	@RequestMapping(method = RequestMethod.GET)
	public String display(ModelMap model) {
		return "Welcome";
	}

}
