
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import domain.Competition;

@Component
@Transactional
public class CompetitionToStringConverter implements Converter<Competition, String> {

	@Override
	public String convert(final Competition arg0) {
		Assert.notNull(arg0);

		String result;
		result = String.valueOf(arg0.getId());

		return result;
	}

}
