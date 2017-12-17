package at.fh_joanneum.newsly.newsly.db.entity;

import at.fh_joanneum.newsly.newsly.ressorts.RessortCategory;

/**
 * Created by edi on 06/05/2017.
 */

public class RessortSetting implements Setting {
    private long id;
    private RessortCategory category;
    private boolean active;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RessortCategory getCategory() {
        return category;
    }

    public void setCategory(RessortCategory category) {
        this.category = category;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public String getName() {
        return category.getValue();
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
