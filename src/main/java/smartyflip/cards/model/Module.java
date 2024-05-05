///*
// *
// *  *   *******************************************************************
// *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
// *  *   *******************************************************************
// *
// */
//
//package smartyflip.cards.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//import java.util.HashSet;
//import java.util.Set;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "module")
//@EqualsAndHashCode(of = "moduleId")
//public class Module {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer moduleId;
//    @Setter
//    private String moduleName;
//    @Setter
//    private String authorName;
//    private LocalDateTime dateCreated;
//    private String stackName;
//    @Setter
//    private Boolean isPublic;
//    @Transient
//    private int cardsCount;
//    @ElementCollection
//    private Set<String> tags = new HashSet<>();
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "moduleId")
//    private Set<Card> cards;
//
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "stackName", insertable = false, updatable = false)
//    private Stack stack;
//
//    public void addCard(Card card) {
//        if (cards == null) {
//            cards = new HashSet<>();
//        }
//        cards.add(card);
//        cardsCount = cards.size();
//    }
//
//    public Module(String moduleName, String authorName, String stackName, Boolean isPublic) {
//        this.moduleName = moduleName;
//        this.authorName = authorName;
//        this.stackName = stackName;
//        this.isPublic = isPublic;
//        this.dateCreated = LocalDateTime.now();
//    }
//
//    boolean addTag (String tag) {
//        if (tags == null) {
//            tags = new HashSet<>();
//        }
//        return tags.add(tag);
//    }
//
//    boolean deleteTag (String tag) {
//        if (tags == null) {
//            return false;
//        }
//        return tags.remove(tag);
//    }
//
//}