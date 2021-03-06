
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Actor;
import repositories.ActorRepository;

@Component
@Transactional
public class StringToActorConverter implements Converter<String, Actor> {

	@Autowired
	private ActorRepository actorRepository;


	@Override
	public Actor convert(final String string) {
		Actor actor;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				actor = null;
			else {
				id = Integer.valueOf(string);
				actor = this.actorRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return actor;

	}

}
