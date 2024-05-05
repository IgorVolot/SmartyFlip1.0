/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.cards.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "cardId")
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardId;

    @Setter
    private String question;

    @Setter
    private String answer;

    @Setter
    private LocalDateTime dateCreated = LocalDateTime.now();

    @Setter
    private String level;

//    /**
//     * This variable represents the number of likes a modules has received.
//     * It is an instance of the Integer class, which is a wrapper class for the primitive type int.
//     * The value of this variable can be null, indicating that the modules has not received any likes yet.
//     * To access or modify the value of this variable, use the appropriate getters and setters methods provided by the Card class.
//     *
//     * Example usage:
//     *
//     * Card modules = new Card();
//     * modules.setLikes(10); // Set the number of likes to 10
//     * int likes = modules.getLikes(); // Get the number of likes
//     *
//     * Note: The Card class is defined in the same package, so you can directly access and modify the likes variable without any additional imports.
//     */
//    @Setter
//    private Integer likes;
//
//    /**
//     * The bookmark variable represents whether a modules has been bookmarked or not.
//     *
//     * If the value of bookmark is true, it means that the modules has been bookmarked.
//     * If the value of bookmark is false, it means that the modules has not been bookmarked.
//     */
//    @Setter
//    private boolean bookmark;

//    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer moduleId;

    @Setter
    String stackName;

//    public Card(String question, String answer, String level, Integer moduleId, String stackName) {
//        this();
//        this.question = question;
//        this.answer = answer;
//        this.level = level;
//        this.moduleId = moduleId;
//        this.stackName = stackName;
//    }
//
//    public Card() {
//        this.lastUpdate = LocalDateTime.now();
//        this.likes = 0;
//        this.bookmark = false;
//    }


//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "moduleId")
////    @Column(insertable = false, updatable = false)
//    private Module module; // the related Module entity


    @Getter
    @Setter
    private String moduleName;

    public Card(String question, String answer, String level) {
        this.question = question;
        this.answer = answer;
        this.level = level;
//        this.likes = 0;
//        this.bookmark = false;
    }

//    public void addLike() {
//        if ( likes == null ) {
//            likes = 0;
//        }
//        likes++;
//    }
}
