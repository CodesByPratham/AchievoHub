package model;

/**
 * @author PRATHAM
 */
import java.sql.Date;

public class Achievement {

    private int achievementId;
    private int userId;
    private String title;
    private String organization;
    private String website;
    private String category;
    private String type;
    private String method;
    private String duration;
    private String description;
    private Date dateAchieved;
    private String image;   
    private Date addedOn;
    private User user;

    public Achievement() {
    }

    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateAchieved() {
        return dateAchieved;
    }

    public void setDateAchieved(Date dateAchieved) {
        this.dateAchieved = dateAchieved;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Override toString method to print object in a readable format (Optional)
    @Override
    public String toString() {
        return "Achievement{"
                + "achievementId=" + achievementId
                + ", userId=" + userId
                + ", title='" + title + '\''
                + ", organization='" + organization + '\''
                + ", category='" + category + '\''
                + ", type='" + type + '\''
                + ", method='" + method + '\''
                + ", duration='" + duration + '\''
                + ", description='" + description + '\''
                + ", dateAchieved=" + dateAchieved
                + ", image='" + image + '\''
                + ", addedOn=" + addedOn
                + '}';
    }
}
