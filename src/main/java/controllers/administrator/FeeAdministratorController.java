package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Fee;
import domain.Manager;
import services.FeeService;

@Controller
@RequestMapping("/fee/administrator")
public class FeeAdministratorController {
	
	@Autowired
	private FeeService feeService;
	
	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView res = new ModelAndView("fee/edit");
		Fee fee=feeService.selectFee();
		Assert.notNull(fee);
		
		res.addObject("requestUri", "fee/administrator/edit.do");
		res.addObject("fee", fee);
		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(Fee fee, final BindingResult bindingResult) {
		ModelAndView res;

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			res = new ModelAndView("fee/edit");
			res.addObject("fee", fee);
		} else {
			try {
				res = edit();
				res.addObject("message", "fee.updatedFee");
				final Fee feeAux = this.feeService.reconstruct(fee, bindingResult);

				feeService.editFee(feeAux);
			} catch (final Throwable oops) {
				res = new ModelAndView("fee/edit");
				res.addObject("fee", fee);
			}
		}
		return res;
	}

}
