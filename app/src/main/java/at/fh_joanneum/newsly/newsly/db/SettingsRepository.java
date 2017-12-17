package at.fh_joanneum.newsly.newsly.db;

import java.util.Collection;

public interface SettingsRepository<T> {

    void updateState(final long id, final boolean active);

    Collection<T> findAllSettings();

    Collection<T> findAllActiveSettings();
}
