
package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import domain.Message;
import forms.MessageForm;
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
	@RequestMapping(value = "/listR", method = RequestMethod.GET)
	public ModelAndView listRecivied() {
		final ModelAndView res = new ModelAndView("message/list");
		final Actor actor = this.actorService.findByPrincipal();
		final Collection<Message> received = this.messageService.myRecivedMessages(actor.getId());

		res.addObject("messages", received);
		res.addObject("requestURI", "message/actor/listR.do");

		return res;
	}

	@RequestMapping(value = "/listS", method = RequestMethod.GET)
	public ModelAndView listSent() {
		final ModelAndView res = new ModelAndView("message/list");

		final Actor actor = this.actorService.findByPrincipal();
		final Collection<Message> sent = this.messageService.mySendedMessages(actor.getId());

		res.addObject("messages", sent);
		res.addObject("requestURI", "message/actor/listS.do");

		return res;
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm) {
		ModelAndView result;

		result = this.createEditModelAndView(messageForm, null);

		return result;
	}

	protected ModelAndView createReplyEditModelAndView(final MessageForm messageForm) {
		ModelAndView result;

		result = this.createReplyEditModelAndView(messageForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm, final String message) {
		ModelAndView result;

		final Actor principal = this.actorService.findByPrincipal();
		final Collection<Actor> users = this.actorService.findAll();
		users.remove(principal);

		result = new ModelAndView("message/edit");
		result.addObject("messageForm", messageForm);
		result.addObject("message", message);
		result.addObject("reply", false);
		result.addObject("users", users);
		result.addObject("requestURI", "message/actor/create.do");

		return result;
	}
	protected ModelAndView createReplyEditModelAndView(final MessageForm messageForm, final String message) {
		ModelAndView result;

		final Actor principal = this.actorService.findByPrincipal();
		final Collection<Actor> users = this.actorService.findAll();
		users.remove(principal);

		result = new ModelAndView("message/edit");
		result.addObject("messageForm", messageForm);
		result.addObject("message", message);
		result.addObject("reply", true);
		result.addObject("users", users);
		result.addObject("requestURI", "message/actor/createReply.do");

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final MessageForm messageForm = new MessageForm();

		Assert.notNull(messageForm);

		result = this.createEditModelAndView(messageForm);

		result.addObject("messageForm", messageForm);
		final Actor actor = this.actorService.findByPrincipal();
		final Collection<Actor> users = this.actorService.findAll();
		users.remove(actor);
		result.addObject("users", users);
		result.addObject("requestURI", "message/actor/create.do");
		result.addObject("reply", false);
		return result;
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView createReply(final int id) {
		ModelAndView result;
		final Message message = this.messageService.findOne(id);

		final MessageForm messageForm = new MessageForm();
		messageForm.setReceiver(message.getSender());
		Assert.notNull(messageForm);

		result = this.createReplyEditModelAndView(messageForm);

		result.addObject("messageForm", messageForm);

		result.addObject("requestURI", "message/actor/createReply.do");
		result.addObject("reply", true);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "send")
	public ModelAndView send(@Valid final MessageForm messageForm, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(messageForm);
		else

			try {
				final Message message = this.messageService.reconstruct(messageForm);
				this.messageService.sendMessage(message);
				result = new ModelAndView("redirect:listS.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messageForm, "message.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/createReply", method = RequestMethod.POST, params = "send")
	public ModelAndView sendR(@Valid final MessageForm messageForm, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(messageForm);
		else

			try {
				final Message message = this.messageService.reconstruct(messageForm);
				this.messageService.sendMessage(message);
				result = new ModelAndView("redirect:listS.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messageForm, "message.commit.error");
			}
		return result;
	}

	@RequestMapping(value = {
		"/deleteReceived"
	}, method = {
		RequestMethod.GET
	})
	public ModelAndView delete(@RequestParam final int id) {
		final Message message = this.messageService.findOne(id);

		try {
			this.messageService.deleteReceived(message);
		} catch (final Throwable var5) {
			;
		}

		final ModelAndView result = new ModelAndView("redirect:listR.do");
		return result;
	}

	@RequestMapping(value = {
		"/deleteSent"
	}, method = {
		RequestMethod.GET
	})
	public ModelAndView delete2(@RequestParam final int id) {
		final Message message = this.messageService.findOne(id);

		try {
			this.messageService.deleteSent(message);
		} catch (final Throwable var5) {
			;
		}

		final ModelAndView result = new ModelAndView("redirect:listS.do");
		return result;
	}

	//	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	//	public ModelAndView delete(@RequestParam final int id) {
	//		ModelAndView modelAndView;
	//		final Message message = this.messageService.findOne(id);
	//
	//		try {
	//			this.messageService.deleteMessage(message);
	//			modelAndView = new ModelAndView("redirect:list.do");
	//		} catch (final Throwable oops) {
	//			modelAndView = new ModelAndView("redirect:list.do");
	//			System.out.println(oops.getMessage());
	//		}
	//		return modelAndView;
	//
	//	}

}
