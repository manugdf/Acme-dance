
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.CompetitionPlanner;
import repositories.CompetitionPlannerRepository;

@Component
@Transactional
public class StringToCompetitionPlannerConverter implements Converter<String, CompetitionPlanner> {

	@Autowired
	private CompetitionPlannerRepository competitionPlannerRepository;


	@Override
	public CompetitionPlanner convert(final String string) {
		CompetitionPlanner competitionPlanner;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				competitionPlanner = null;
			else {
				id = Integer.valueOf(string);
				competitionPlanner = this.competitionPlannerRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return competitionPlanner;

	}

}
