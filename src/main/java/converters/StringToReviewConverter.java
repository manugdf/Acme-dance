
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Review;
import repositories.ReviewRepository;

@Component
@Transactional
public class StringToReviewConverter implements Converter<String, Review> {

	@Autowired
	private ReviewRepository reviewRepository;


	@Override
	public Review convert(final String string) {
		Review review;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				review = null;
			else {
				id = Integer.valueOf(string);
				review = this.reviewRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return review;

	}

}
