/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.tags.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import smartyflip.tags.model.Tag;

public interface TagRepository extends JpaRepository<Tag, String> {
}
