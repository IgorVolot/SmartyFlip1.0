/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import smartyflip.card.model.Tag;

@Repository
public interface TagRepository extends MongoRepository<Tag, String> {

    Tag findByTagName(String tagName);

}
