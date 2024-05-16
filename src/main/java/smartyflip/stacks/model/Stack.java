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
import org.hibernate.annotations.Formula;
import smartyflip.modules.model.Module;

import java.util.HashSet;
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
    @Column(name = "stack_id")
    private Long stackId;

    @Column(name = "stackName", nullable = false, unique = true)
    private String stackName;

    @Formula("(SELECT COUNT(m.module_id) FROM module m WHERE m.stack_id = stack_id)")
    private int modulesAmount;

    @OneToMany(mappedBy = "stack", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Module> modules = new HashSet<>();

    public Stack(String stackName, int modulesAmount) {
        this.stackName = stackName;
        this.modulesAmount = modulesAmount;
    }

}