
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Message;
import services.ActorService;
import services.MessageService;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {

	//Services--------------------------------------------------
	@Autowired
	private ActorService	actorService;

	@Autowired
	private MessageService	messageService;

	//Constructor-----------------------------------------------


	public MessageActorController() {
		super();
	}

	//Methods---------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("message/list");

		final Collection<Message> received = this.messageService.findAllReceived();
		final Collection<Message> sent = this.messageService.findAllSent();

		res.addObject("received", received);
		res.addObject("sent", sent);
		res.addObject("requestURI", "message/actor/list.do");

		return res;
	}
}
