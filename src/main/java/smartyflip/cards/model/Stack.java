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
//import lombok.AllArgsConstructor;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Entity
//@Table(name = "stack")
//@EqualsAndHashCode(of = "stackName")
//public class Stack {
//    @Id
//    private String stackName;
//
//    @Transient
//    private int modulesCount;
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "stackName")
//    private List<Module> modules;
//
//    public void addModule(Module module) {
//        if (modules == null) {
//            modules = new ArrayList<>();
//        }
//        modules.add(module);
//        modulesCount = modules.size();
//    }
//
//    public void removeModule(Module module) {
//        if (modules != null) {
//            modules.remove(module);
//            modulesCount = modules.size();
//        }
//    }
//}
