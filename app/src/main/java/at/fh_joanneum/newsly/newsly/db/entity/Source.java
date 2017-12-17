package at.fh_joanneum.newsly.newsly.db.entity;

/**
 * Created by edi on 07/05/2017.
 */

public class Source {
    private long id;
    private String name;
    private String sportLink;
    private String politicsLink;
    private String economyLink;
    private String lifeLink;
    private String educationLink;
    private String cultureLink;

    public Source() {
    }

    public Source(String name, String sportLink, String politicsLink, String economyLink, String lifeLink, String educationLink, String cultureLink) {
        this.name = name;
        this.sportLink = sportLink;
        this.politicsLink = politicsLink;
        this.economyLink = economyLink;
        this.lifeLink = lifeLink;
        this.educationLink = educationLink;
        this.cultureLink = cultureLink;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSportLink() {
        return sportLink;
    }

    public void setSportLink(String sportLink) {
        this.sportLink = sportLink;
    }

    public String getPoliticsLink() {
        return politicsLink;
    }

    public void setPoliticsLink(String politicsLink) {
        this.politicsLink = politicsLink;
    }

    public String getEconomyLink() {
        return economyLink;
    }

    public void setEconomyLink(String economyLink) {
        this.economyLink = economyLink;
    }

    public String getLifeLink() {
        return lifeLink;
    }

    public void setLifeLink(String lifeLink) {
        this.lifeLink = lifeLink;
    }

    public String getEducationLink() {
        return educationLink;
    }

    public void setEducationLink(String educationLink) {
        this.educationLink = educationLink;
    }

    public String getCultureLink() {
        return cultureLink;
    }

    public void setCultureLink(String cultureLink) {
        this.cultureLink = cultureLink;
    }
}
