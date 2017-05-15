
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Material;
import repositories.MaterialRepository;

@Component
@Transactional
public class StringToMaterialConverter implements Converter<String, Material> {

	@Autowired
	private MaterialRepository materialRepository;


	@Override
	public Material convert(final String string) {
		Material material;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				material = null;
			else {
				id = Integer.valueOf(string);
				material = this.materialRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return material;

	}

}
