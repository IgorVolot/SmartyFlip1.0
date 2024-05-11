///*
// *
// *  *   *******************************************************************
// *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
// *  *   *******************************************************************
// *
// */
//
//package smartyflip.trialModules.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import smartyflip.card.model.Card;
//import smartyflip.modules.model.Tag;
//import smartyflip.stacks.model.Stack;
//
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.Set;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@EqualsAndHashCode(of = "trialModuleId")
//@Entity
//@Table(name = "trialModule")
//public class TrialModule {
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    Long trialModuleId;
//
//    @Column(name = "trialModuleName", nullable = false)
//    String trialModuleName;
//
//    String authorName;
//
//    LocalDate dateCreated = LocalDate.now();
//
//    String stackName;
//
//    int cardsAmount;
//
//    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
//    @JoinTable(name = "trialModule_tag",
//            joinColumns = @JoinColumn(name = "trialModule_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_id"))
//    private Set<Tag> tags = new HashSet<>();
//
//
//    @OneToMany(mappedBy = "trialModule", cascade = CascadeType.ALL, orphanRemoval = true)
//    Set<Card> cards;
//
////    public TrialModule(Long trialModuleId) {
////        this.trialModuleId = trialModuleId;
////    }
//
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "stack_id")
//    Stack stack;
//
//
//    public int cardsAmount() {
//        cardsAmount = cards.size();
//        return cardsAmount;
//    }
//}
