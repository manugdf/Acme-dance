
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import domain.Schedule;

@Component
@Transactional
public class ScheduleToStringConverter implements Converter<Schedule, String> {

	@Override
	public String convert(final Schedule arg0) {
		Assert.notNull(arg0);

		String result;
		result = String.valueOf(arg0.getId());

		return result;
	}

}
