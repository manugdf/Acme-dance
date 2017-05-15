
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import domain.Material;

@Component
@Transactional
public class MaterialToStringConverter implements Converter<Material, String> {

	@Override
	public String convert(final Material arg0) {
		Assert.notNull(arg0);

		String result;
		result = String.valueOf(arg0.getId());

		return result;
	}

}
