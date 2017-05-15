
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Competition;
import repositories.CompetitionRepository;

@Component
@Transactional
public class StringToCompetitionConverter implements Converter<String, Competition> {

	@Autowired
	private CompetitionRepository competitionRepository;


	@Override
	public Competition convert(final String string) {
		Competition competition;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				competition = null;
			else {
				id = Integer.valueOf(string);
				competition = this.competitionRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return competition;

	}

}
