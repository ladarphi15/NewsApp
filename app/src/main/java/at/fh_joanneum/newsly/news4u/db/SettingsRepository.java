package at.fh_joanneum.newsly.news4u.db;

import java.util.Collection;

public interface SettingsRepository<T> {

    void updateState(final long id, final boolean active);

    Collection<T> findAllSettings();

    Collection<T> findAllActiveSettings();
}
