
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Teacher;
import repositories.TeacherRepository;

@Component
@Transactional
public class StringToTeacherConverter implements Converter<String, Teacher> {

	@Autowired
	private TeacherRepository teacherRepository;


	@Override
	public Teacher convert(final String string) {
		Teacher teacher;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				teacher = null;
			else {
				id = Integer.valueOf(string);
				teacher = this.teacherRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return teacher;

	}

}
