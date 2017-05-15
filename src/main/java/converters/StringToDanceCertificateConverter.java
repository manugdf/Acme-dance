
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.DanceCertificate;
import repositories.DanceCertificateRepository;

@Component
@Transactional
public class StringToDanceCertificateConverter implements Converter<String, DanceCertificate> {

	@Autowired
	private DanceCertificateRepository danceCertificateRepository;


	@Override
	public DanceCertificate convert(final String string) {
		DanceCertificate danceCertificate;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				danceCertificate = null;
			else {
				id = Integer.valueOf(string);
				danceCertificate = this.danceCertificateRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return danceCertificate;

	}

}
