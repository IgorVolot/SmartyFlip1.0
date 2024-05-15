/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import smartyflip.card.model.enums.Level;
import smartyflip.modules.model.Module;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "cards")
@Builder
public class Card {
    @Id
    String id;

    String question;

    String answer;

    @Enumerated(EnumType.STRING)
    Level level;

    LocalDateTime dateCreated = LocalDateTime.now();

    Long moduleId;

    String moduleName;

    @Singular
    Set<String> tags;

    int likes;

    int dislike;

    public void addLike() {
        likes++;
    }

    public void addDislike() {
        likes++;
    }

    public boolean removeTag(String tag) {
        return tags.remove(tag);
    }

    public boolean addTag(String tag) {
        return tags.add(tag);
    }

    @ManyToOne
    @JoinColumn(name = "module_module_id")
    Module module;

}
