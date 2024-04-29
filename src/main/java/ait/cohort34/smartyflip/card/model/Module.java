package ait.cohort34.smartyflip.card.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "moduleId")
@Entity
@Table(name = "module")
public class Module {
    @Id
    Long moduleId;
    @Column(name = "module_name")
    String moduleName;
    String authorName;
    LocalDateTime lastUpdate;
    String stackName;
    boolean isPublic;
    Integer cardsCount;

    @ElementCollection
    @CollectionTable(name = "tag", joinColumns = @JoinColumn(name = "module_id"))
    @Column(name = "tag")
    Set<String> tags;
}
