
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import domain.DanceTest;

@Component
@Transactional
public class DanceTestToStringConverter implements Converter<DanceTest, String> {

	@Override
	public String convert(final DanceTest arg0) {
		Assert.notNull(arg0);

		String result;
		result = String.valueOf(arg0.getId());

		return result;
	}

}
