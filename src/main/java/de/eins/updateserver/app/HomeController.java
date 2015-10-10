package de.eins.updateserver.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@Autowired
	AppRepository appRepository;

	@RequestMapping(value = "")
	public String home(final ModelMap model) {
		model.addAttribute("apps", appRepository.findAll());
		return "home";
	}
}
