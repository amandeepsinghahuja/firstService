package com.easystub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.easystub.model.SomaParameters;

@Controller
public class MainController {

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView getValues() {
		ModelAndView md = new ModelAndView("easyStubMain");
		return md;
	}

	@RequestMapping(value = "/newStub", method = RequestMethod.GET)
	public ModelAndView getCertDetails() {
		ModelAndView md = new ModelAndView("createNewStub");
		/*md.addObject("domains",SomaParameters.getSpObject().getDomains());*/
		return md;
	}
	
	@RequestMapping(value = "/cryptoCertUpdate", method = RequestMethod.GET)
	public ModelAndView upValCred() {
		ModelAndView md = new ModelAndView("cryptoCertUpdate");
		md.addObject("domains",SomaParameters.getSpObject().getDomains());
		return md;
	}

	@RequestMapping(value = "/fileUploadHomeNew", method = RequestMethod.GET )
	public ModelMap uploadFile()
	{
		ModelMap mp= new ModelMap("fileUploadHomeNew");
		mp.addAttribute("domains",SomaParameters.getSpObject().getDomains());
		return mp;
	}
	
	@RequestMapping(value = "/checkDomainStatus", method = RequestMethod.GET )
	public ModelMap checkDomainStatus()
	{
		ModelMap mp= new ModelMap("checkDomainStatus");
		return mp;
	}
	
	@RequestMapping(value = "/checkDomainHealth", method = RequestMethod.GET )
	public ModelMap checkDomainHealth()
	{
		ModelMap mp= new ModelMap("checkDomainHealth");
		return mp;
	}


}