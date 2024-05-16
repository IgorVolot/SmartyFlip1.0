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
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedDate;
import smartyflip.accounting.model.UserAccount;
import smartyflip.card.model.Card;
import smartyflip.stacks.model.Stack;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "moduleId")
@Entity
@Table(name = "module")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "module_id")
    private Long moduleId;

    @Column(name = "moduleName", nullable = false)
    private String moduleName;

    @Column(name = "userName", nullable = false)
    private String userName;

    @CreatedDate
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    @Column(name = "stackName", nullable = false)
    private String stackName;

    @Formula("(SELECT COUNT(c.card_id) FROM card c WHERE c.module_id = module_id)")
    private int cardsAmount;

    @ManyToOne
    @JoinColumn(name = "users_username", nullable = false)
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "stack_id")
    private Stack stack;


    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "module_tag",
            joinColumns = @JoinColumn(name = "module_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();


    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Card> cards = new HashSet<>();

    public void addTag(Tag tag) {
        if (tags == null) {
            tags = new HashSet<>();
        }
        if (!tags.contains(tag)) {
            tags.add(tag);
            tag.getModules().add(this); // Ensure the bidirectional link is established
        }
    }

    public void addTags(Collection<Tag> tags) {
        for (Tag tag : tags) {
            this.addTag(tag);
        }
    }

    public void removeTag(Tag tag) {
        if (tags != null && tags.contains(tag)) {
            tags.remove(tag);
            tag.getModules().remove(this); // Clean up the reverse link
        }
    }
}
