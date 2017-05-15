
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import domain.Teacher;

@Component
@Transactional
public class TeacherToStringConverter implements Converter<Teacher, String> {

	@Override
	public String convert(final Teacher arg0) {
		Assert.notNull(arg0);

		String result;
		result = String.valueOf(arg0.getId());

		return result;
	}

}
