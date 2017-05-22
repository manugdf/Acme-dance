package forms;


import domain.DanceSchool;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class DanceClassForm {
    private String	style;
    private int		maxAlumns;
    private double	monthlyPrice;
    private double	yearlyPrice;
    private String	description;
    private DanceSchool danceSchool;

    @Min(1)
    public int getMaxAlumns() {
        return this.maxAlumns;
    }
    public void setMaxAlumns(final int maxAlumns) {
        this.maxAlumns = maxAlumns;
    }

    @NotBlank
    @NotNull
    public String getStyle() {
        return this.style;
    }
    public void setStyle(String style) {
        this.style = style;
    }


    @Min(0)
    public double getMonthlyPrice() {
        return this.monthlyPrice;
    }
    public void setMonthlyPrice(final double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    @Min(0)
    public double getYearlyPrice() {
        return this.yearlyPrice;
    }
    public void setYearlyPrice(final double yearlyPrice) {
        this.yearlyPrice = yearlyPrice;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(final String description) {
        this.description = description;
    }

    @NotNull
    public DanceSchool getDanceSchool() {
        return this.danceSchool;
    }
    public void setDanceSchool(final DanceSchool danceSchool) {
        this.danceSchool = danceSchool;
    }
}
