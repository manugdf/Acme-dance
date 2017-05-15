
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Admin;
import repositories.AdminRepository;

@Component
@Transactional
public class StringToAdminConverter implements Converter<String, Admin> {

	@Autowired
	private AdminRepository adminRepository;


	@Override
	public Admin convert(final String string) {
		Admin admin;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				admin = null;
			else {
				id = Integer.valueOf(string);
				admin = this.adminRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return admin;

	}

}
