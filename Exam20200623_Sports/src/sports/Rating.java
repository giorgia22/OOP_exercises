package sports;

public class Rating {
    String comment, user;
    Integer stars;

    public Rating(String user, String comment, Integer stars) {
        this.user = user;
        this.comment = comment;
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
