
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import domain.DanceSchool;

@Component
@Transactional
public class DanceSchoolToStringConverter implements Converter<DanceSchool, String> {

	@Override
	public String convert(final DanceSchool arg0) {
		Assert.notNull(arg0);

		String result;
		result = String.valueOf(arg0.getId());

		return result;
	}

}
