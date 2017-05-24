package services;

import domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.ReviewRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class ReviewService {
    //Repository
    @Autowired
    private ReviewRepository reviewRepository;

    //Services
    @Autowired
    private AlumnService alumnService;

    //Constructor
    public ReviewService(){super();}

    //CRUD METHODS
    public Review create(){
        Review res = new Review();

        res.setAlumn(alumnService.findByPrincipal());
        return res;
    }

    public Collection<Review> findAll() {
        return this.reviewRepository.findAll();
    }

    public Review findOne(final int id) {
        Review result;
        result = this.reviewRepository.findOne(id);
        return result;
    }

    public Review save(Review review) {
        Assert.notNull(review);

        return this.reviewRepository.save(review);
    }

    public void delete(Review review) {
        Assert.notNull(review);
        this.reviewRepository.delete(review);
    }

    //Other methods
    public Collection<Review> findReviewByTeacher(int teacherId){
        return reviewRepository.findReviewByTeacher(teacherId);
    }
}
