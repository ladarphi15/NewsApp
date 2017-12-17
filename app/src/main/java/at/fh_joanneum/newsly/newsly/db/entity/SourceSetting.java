package at.fh_joanneum.newsly.newsly.db.entity;

/**
 * Created by edi on 07/05/2017.
 */

public class SourceSetting implements Setting {

    private String name;
    private long id;
    private boolean active;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
