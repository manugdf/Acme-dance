
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Message;
import repositories.MessageRepository;

@Component
@Transactional
public class StringToMessageConverter implements Converter<String, Message> {

	@Autowired
	private MessageRepository messageRepository;


	@Override
	public Message convert(final String string) {
		Message message;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				message = null;
			else {
				id = Integer.valueOf(string);
				message = this.messageRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return message;

	}

}
