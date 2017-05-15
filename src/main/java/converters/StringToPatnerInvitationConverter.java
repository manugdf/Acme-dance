
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.PartnerInvitation;
import repositories.PartnerInvitationRepository;

@Component
@Transactional
public class StringToPatnerInvitationConverter implements Converter<String, PartnerInvitation> {

	@Autowired
	private PartnerInvitationRepository partnerInvitationRepository;


	@Override
	public PartnerInvitation convert(final String string) {
		final PartnerInvitation invitation;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				invitation = null;
			else {
				id = Integer.valueOf(string);
				invitation = this.partnerInvitationRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return invitation;

	}

}
