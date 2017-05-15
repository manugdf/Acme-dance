
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.DanceSchool;
import repositories.DanceSchoolRepository;

@Component
@Transactional
public class StringToDanceSchoolConverter implements Converter<String, DanceSchool> {

	@Autowired
	private DanceSchoolRepository danceSchoolRepository;


	@Override
	public DanceSchool convert(final String string) {
		DanceSchool danceSchool;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				danceSchool = null;
			else {
				id = Integer.valueOf(string);
				danceSchool = this.danceSchoolRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return danceSchool;

	}

}
