/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */
package smartyflip.modules.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "moduleId")
@Entity
@Table(name = "module")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer moduleId;
    @Setter
    String moduleName;

    @Setter
    String authorName;

    LocalDateTime dateCreated = LocalDateTime.now();

    @Setter
    String stackName;

    @Setter
    boolean isPublic;

//    boolean bookmark;

    int cardsCount;

    @ElementCollection
    @CollectionTable(name = "tag", joinColumns = @JoinColumn(name = "module_id"))
    @Column(name = "tag")
    Set<String> tags = new HashSet<>();

    public Module(String moduleName, String authorName, String stackName, Set<String> tags, boolean isPublic) {
        this.moduleName = moduleName;
        this.authorName = authorName;
        this.stackName = stackName;
        this.tags = tags;
        this.isPublic = isPublic;
    }

    public void increaseCardsCount() {
        cardsCount++;
    }
    public void decreaseCardsCount() {
        cardsCount--;
    }

    public boolean removeTag(String tag) {
        return tags.remove(tag);
    }

    public boolean addTag(String tag) {
        return tags.add(tag);
    }
}
