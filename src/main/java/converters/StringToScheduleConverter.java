
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Schedule;
import repositories.ScheduleRepository;

@Component
@Transactional
public class StringToScheduleConverter implements Converter<String, Schedule> {

	@Autowired
	private ScheduleRepository scheduleRepository;


	@Override
	public Schedule convert(final String string) {
		final Schedule schedule;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				schedule = null;
			else {
				id = Integer.valueOf(string);
				schedule = this.scheduleRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return schedule;

	}

}
