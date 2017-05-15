
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import domain.PartnerInvitation;

@Component
@Transactional
public class PatnerInvitationToStringConverter implements Converter<PartnerInvitation, String> {

	@Override
	public String convert(final PartnerInvitation arg0) {
		Assert.notNull(arg0);

		String result;
		result = String.valueOf(arg0.getId());

		return result;
	}

}
