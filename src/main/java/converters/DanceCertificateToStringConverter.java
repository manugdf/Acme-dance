
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import domain.DanceCertificate;

@Component
@Transactional
public class DanceCertificateToStringConverter implements Converter<DanceCertificate, String> {

	@Override
	public String convert(final DanceCertificate arg0) {
		Assert.notNull(arg0);

		String result;
		result = String.valueOf(arg0.getId());

		return result;
	}

}
