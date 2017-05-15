
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Award;
import repositories.AwardRepository;

@Component
@Transactional
public class StringToAwardConverter implements Converter<String, Award> {

	@Autowired
	private AwardRepository awardRepository;


	@Override
	public Award convert(final String string) {
		Award award;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				award = null;
			else {
				id = Integer.valueOf(string);
				award = this.awardRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return award;

	}

}
