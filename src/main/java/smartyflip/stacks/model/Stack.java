///*
// *
// *  *   *******************************************************************
// *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
// *  *   *******************************************************************
// *
// */
//
//package smartyflip.stacks.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import smartyflip.decks.model.Deck;
//
//import java.util.List;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@EqualsAndHashCode(of = "stackName")
//@Entity
//@Table(name = "stack")
//public class Stack {
//    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String stackName;
//
//    @OneToMany(mappedBy="stack", cascade = CascadeType.ALL, orphanRemoval=true)
//    private List<Deck> decks;
//}