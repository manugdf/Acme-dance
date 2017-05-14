
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Teacher extends Actor {

	private double	averageScore;
	private String	picture;
	private String	presentationVideo;


	public double getAverageScore() {
		return this.averageScore;
	}
	public void setAverageScore(final double averageScore) {
		this.averageScore = averageScore;
	}

	@URL
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@URL
	public String getPresentationVideo() {
		return this.presentationVideo;
	}
	public void setPresentationVideo(final String presentationVideo) {
		this.presentationVideo = presentationVideo;
	}


	//Relationship

	private Collection<Review>				reviews;
	private Collection<DanceCertificate>	danceCertificate;
	private Collection<DanceClass>			danceClasses;
	private Collection<Material>			materials;


	@ElementCollection
	@NotNull
	@OneToMany(mappedBy = "teacher")
	public Collection<Review> getReviews() {
		return this.reviews;
	}
	public void setReviews(final Collection<Review> reviews) {
		this.reviews = reviews;
	}

	@ElementCollection
	@NotNull
	@OneToMany(mappedBy = "teacher")
	public Collection<DanceCertificate> getDanceCertificate() {
		return this.danceCertificate;
	}
	public void setDanceCertificate(final Collection<DanceCertificate> danceCertificate) {
		this.danceCertificate = danceCertificate;
	}

	@ElementCollection
	@NotNull
	@ManyToMany(mappedBy = "teachers")
	public Collection<DanceClass> getDanceClasses() {
		return this.danceClasses;
	}
	public void setDanceClasses(final Collection<DanceClass> danceClasses) {
		this.danceClasses = danceClasses;
	}

	@ElementCollection
	@NotNull
	@OneToMany(mappedBy = "teacher")
	public Collection<Material> getMaterials() {
		return this.materials;
	}
	public void setMaterials(final Collection<Material> materials) {
		this.materials = materials;
	}

}
