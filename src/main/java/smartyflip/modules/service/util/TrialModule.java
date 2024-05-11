/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.service.util;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import smartyflip.modules.model.Module;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("TRIAL")
public class TrialModule extends Module {
    @Setter
    @Getter
    private int accessDurationDays;  // Duration in days for which the trial is valid
    @Setter
    @Getter
    private String accessLevel;      // Describes the level of access (e.g., "basic", "premium-preview")

    // RESERVED OPTIONS
    private boolean isFeatureLimited;  // Indicates if some features are restricted

    public boolean isFeatureLimited() {
        return isFeatureLimited;
    }

    public void setFeatureLimited(boolean isFeatureLimited) {
        this.isFeatureLimited = isFeatureLimited;
    }
}