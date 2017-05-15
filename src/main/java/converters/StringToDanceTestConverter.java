
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.DanceTest;
import repositories.DanceTestRepository;

@Component
@Transactional
public class StringToDanceTestConverter implements Converter<String, DanceTest> {

	@Autowired
	private DanceTestRepository danceTestRepository;


	@Override
	public DanceTest convert(final String string) {
		DanceTest danceTest;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				danceTest = null;
			else {
				id = Integer.valueOf(string);
				danceTest = this.danceTestRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return danceTest;

	}

}
