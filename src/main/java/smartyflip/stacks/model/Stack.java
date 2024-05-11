/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.model;

import jakarta.persistence.*;
import lombok.*;
import smartyflip.modules.model.Module;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "stackId")
@Entity
@Table(name = "stack")
public class Stack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long stackId;

    @Column
    String stackName;

    int modulesAmount;

    int trialModulesAmount;

    @OneToMany(mappedBy = "stack", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Module> modules;

    public Stack(String stackName, int modulesAmount) {
        this.stackName = stackName;
        this.modulesAmount = modulesAmount;
    }

    public int modulesAmount() {
        return modules.size();
    }
}