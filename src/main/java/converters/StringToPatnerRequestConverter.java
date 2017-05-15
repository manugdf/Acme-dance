
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.PartnerRequest;
import repositories.PartnerRequestRepository;

@Component
@Transactional
public class StringToPatnerRequestConverter implements Converter<String, PartnerRequest> {

	@Autowired
	private PartnerRequestRepository partnerRequestRepository;


	@Override
	public PartnerRequest convert(final String string) {
		final PartnerRequest request;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				request = null;
			else {
				id = Integer.valueOf(string);
				request = this.partnerRequestRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return request;

	}

}
