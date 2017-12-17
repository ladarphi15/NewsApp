package at.fh_joanneum.newsly.newsly;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import at.fh_joanneum.newsly.newsly.db.RessortSettingsRepository;
import at.fh_joanneum.newsly.newsly.db.SourceRepository;
import at.fh_joanneum.newsly.newsly.db.SourceSettingsRepository;
import at.fh_joanneum.newsly.newsly.db.entity.LinkSourceRessort;
import at.fh_joanneum.newsly.newsly.db.entity.RessortSetting;
import at.fh_joanneum.newsly.newsly.db.entity.Source;
import at.fh_joanneum.newsly.newsly.db.entity.SourceSetting;

/**
 * Created by edi on 07/05/2017.
 */

class RessortService {
    private final SourceSettingsRepository sourceSettingsRepository;
    private final RessortSettingsRepository ressortSettingsRepository;
    private final SourceRepository sourceRepository;

    RessortService(final Context context) {
        this.sourceSettingsRepository = new SourceSettingsRepository(context);
        this.ressortSettingsRepository = new RessortSettingsRepository(context);
        this.sourceRepository = new SourceRepository(context);
    }

    public List<LinkSourceRessort> getAllFeasibleLinks() {
        final Collection<SourceSetting> sourceSettings = sourceSettingsRepository.findAllActiveSettings();
        final Collection<RessortSetting> ressortSettings = ressortSettingsRepository.findAllActiveSettings();
        final List<String> names = new ArrayList<>();
        for (SourceSetting setting : sourceSettings) {
            names.add(setting.getName());
        }

        final List<Source> sources = sourceRepository.findAllSourceByNames(names);

        final List<LinkSourceRessort> linkSourceRessorts = new ArrayList<>();
        // @richi: einfach nicht beachten... war zu faul
        for (Source source : sources) {
            for (RessortSetting ressortSetting : ressortSettings) {
                if (ressortSetting.getCategory() == null || !ressortSetting.isActive()) {
                    continue;
                }
                switch (ressortSetting.getCategory()) {
                    case CULTURE:
                        if (source.getCultureLink() != null)
                            linkSourceRessorts.add(new LinkSourceRessort(source.getCultureLink(), source.getName(), ressortSetting.getCategory().getValue()));
                        break;
                    case ECONOMY:
                        if (source.getEconomyLink() != null)
                            linkSourceRessorts.add(new LinkSourceRessort(source.getEconomyLink(), source.getName(), ressortSetting.getCategory().getValue()));
                        break;
                    case EDUCATION:
                        if (source.getEducationLink() != null)
                            linkSourceRessorts.add(new LinkSourceRessort(source.getEducationLink(), source.getName(), ressortSetting.getCategory().getValue()));
                        break;
                    case LIFE:
                        if (source.getLifeLink() != null)
                            linkSourceRessorts.add(new LinkSourceRessort(source.getLifeLink(), source.getName(), ressortSetting.getCategory().getValue()));
                        break;
                    case POLITICS:
                        if (source.getPoliticsLink() != null)
                            linkSourceRessorts.add(new LinkSourceRessort(source.getPoliticsLink(), source.getName(), ressortSetting.getCategory().getValue()));
                        break;
                    case SPORT:
                        if (source.getSportLink() != null)
                            linkSourceRessorts.add(new LinkSourceRessort(source.getSportLink(), source.getName(), ressortSetting.getCategory().getValue()));
                        break;
                    default:
                        throw new IllegalStateException("Requesting Unsupported Category");
                }
            }
        }

        return linkSourceRessorts;
    }
}
