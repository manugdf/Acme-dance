
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.DanceClass;
import repositories.DanceClassRepository;

@Component
@Transactional
public class StringToDanceClassConverter implements Converter<String, DanceClass> {

	@Autowired
	private DanceClassRepository danceClassRepository;


	@Override
	public DanceClass convert(final String string) {
		DanceClass danceClass;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				danceClass = null;
			else {
				id = Integer.valueOf(string);
				danceClass = this.danceClassRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return danceClass;

	}

}
