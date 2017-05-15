
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.CensoredWords;
import repositories.CensoredWordsRepository;

@Component
@Transactional
public class StringToCensoredWordsConverter implements Converter<String, CensoredWords> {

	@Autowired
	private CensoredWordsRepository censoredWordsRepository;


	@Override
	public CensoredWords convert(final String string) {
		CensoredWords censoredWords;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				censoredWords = null;
			else {
				id = Integer.valueOf(string);
				censoredWords = this.censoredWordsRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return censoredWords;

	}

}
