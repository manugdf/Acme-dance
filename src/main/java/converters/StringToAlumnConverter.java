
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Alumn;
import repositories.AlumnRepository;

@Component
@Transactional
public class StringToAlumnConverter implements Converter<String, Alumn> {

	@Autowired
	private AlumnRepository alumnRepository;


	@Override
	public Alumn convert(final String string) {
		final Alumn alumn;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				alumn = null;
			else {
				id = Integer.valueOf(string);
				alumn = this.alumnRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return alumn;

	}

}
