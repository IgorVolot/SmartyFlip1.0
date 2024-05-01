/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package ait.cohort34.smartyflip.card.service;

import ait.cohort34.smartyflip.card.dao.CardRepository;
import ait.cohort34.smartyflip.card.dto.CardDto;
import ait.cohort34.smartyflip.card.dto.NewCardDto;
import ait.cohort34.smartyflip.card.dto.exceptions.CardNotFoundException;
import ait.cohort34.smartyflip.card.model.Card;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardServiceImplTest {

    /**
     * Represents a repository for managing Card objects.
     */
    @Mock
    private CardRepository cardRepository;

    /**
     * Represents a Mock instance of ModelMapper used for testing purposes.
     * Note: This documentation is specifically for the variable declaration and does not cover the functionality or usage of ModelMapper.
     */
    @Mock
    private ModelMapper modelMapper;

    /**
     * This variable is an instance of the CardServiceImpl class which implements the CardService interface.
     * It provides methods to add, find, edit, delete, and perform operations on cards.
     * <p>
     * Injection annotations: @InjectMocks
     * <p>
     * Class Fields:
     * - cardRepository: Represents a repository for the Card entity. The repository extends the JpaRepository interface for performing CRUD operations.
     * - modelMapper: The model mapper used for mapping between different object types.
     * <p>
     * Class Methods:
     * - addCard: Adds a new card to the system.
     * - findCardById: Finds a card by its ID.
     * - addLike: Adds a like to a card identified by the given ID.
     * - editCard: Edits a card with the given cardId using the provided newCardDto.
     * - editBookmark: Edits the bookmark status of a card with the given cardId.
     * - deleteCard: Deletes a card with the given cardId from the repository.
     * - getCardById: Retrieves a card from the repository based on the given card ID.
     * - mapToDto: Maps a Card object to a CardDto object using ModelMapper.
     */
    @InjectMocks
    private CardServiceImpl cardService;

    /**
     * Represents an AutoCloseable variable.
     * <p>
     * This variable is used to manage resources that need to be manually closed after use.
     * It is implemented by classes that represent resources which follow the automatic resource management pattern.
     *
     * @see AutoCloseable
     */
    private AutoCloseable autoCloseable;

    /**
     * Sets up the necessary objects and dependencies for each test case.
     * This method is annotated with @BeforeEach and is executed before each test case in the test class.
     */
    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    /**
     * Tears down the test environment after each test case.
     *
     * @throws Exception if an error occurs during teardown.
     */
    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

    /**
     * This method tests the functionality of the addCard method in the CardService class.
     * It performs the following steps:
     * 1. Arrange: Create necessary objects and mock dependencies for the test.
     * 2. Act: Call the addCard method with a NewCardDto object.
     * 3. Assert: Verify that the expected method invocations are made and the result is not null and matches the expected value.
     */
    @Test
    void testAddCard() {
        // Arrange
        NewCardDto newCardDto = new NewCardDto();
        Card card = new Card();
        CardDto cardDto = new CardDto();

        when(modelMapper.map(newCardDto, Card.class)).thenReturn(card);
        when(cardRepository.save(card)).thenReturn(card);
        when(modelMapper.map(card, CardDto.class)).thenReturn(cardDto);

        // Act
        CardDto result = cardService.addCard(newCardDto);

        // Assert
        verify(modelMapper, times(1)).map(newCardDto, Card.class);
        verify(cardRepository, times(1)).save(card);
        verify(modelMapper, times(1)).map(card, CardDto.class);

        assertNotNull(result);
        assertEquals(cardDto, result);
    }

    /**
     * This method tests the functionality of the findCardById method in the CardService class.
     * It performs the following steps:
     * 1. Arrange: Create necessary objects and mock dependencies for the test.
     * 2. Act: Call the findCardById method with a cardId.
     * 3. Assert: Verify that the expected method invocations are made and the result is not null and matches the expected value.
     */
    @Test
    void testFindCardById_CardPresent() {
        // Arrange
        Integer cardId = 1;
        Card card = new Card();
        CardDto cardDto = new CardDto();
        cardDto.setCardId(cardId);

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
        when(modelMapper.map(card, CardDto.class)).thenReturn(cardDto);

        // Act
        CardDto result = cardService.findCardById(cardId);

        // Assert
        verify(cardRepository, times(1)).findById(cardId);
        verify(modelMapper, times(1)).map(card, CardDto.class);

        assertNotNull(result);
        assertEquals(cardDto, result);
    }

    /**
     * This method tests the functionality of the findCardById method in the CardService class when the card is not present.
     * <p>
     * It performs the following steps:
     * 1. Arrange: Set up necessary objects and dependencies for the test.
     * - Create an Integer object 'cardId' and initialize it with the card ID.
     * - Mock the behavior of cardRepository.findById(cardId) method to return an empty Optional.
     * 2. Act & Assert: Verify that the CardNotFoundException is thrown when calling cardService.findCardById(cardId).
     * - Use assertThrows method to assert the exception is thrown.
     * 3. Verify: Verify that the cardRepository.findById(cardId) method is called exactly once.
     */
    @Test
    void testFindCardById_CardNotPresent() {
        // Arrange
        Integer cardId = 1;

        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CardNotFoundException.class, () -> cardService.findCardById(cardId));
        verify(cardRepository, times(1)).findById(cardId);
    }

    /**
     * Adds a like to a card identified by the given ID. If the card is not found, a CardNotFoundException is thrown.
     * If the card already has likes, the number of likes is incremented. Otherwise, the likes are set to 1.
     *
     * @throws CardNotFoundException If the card with the given ID is not found.
     */
    @Test
    void testAddLikeFirstTime() {
        // Arrange
        Integer cardId = 1;
        Card card = new Card();

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        // Act
        cardService.addLike(cardId);

        // Assert
        verify(cardRepository, times(1)).findById(cardId);

        assertEquals(1, card.getLikes());
        verify(cardRepository, times(1)).save(card);
    }

    /**
     * Adds a like to a card identified by the given ID. If the card is not found, a CardNotFoundException is thrown.
     * If the card already has likes, the number of likes is incremented. Otherwise, the likes are set to 1.
     *
     * @throws CardNotFoundException If the card with the given ID is not found.
     */
    @Test
    void testAddLikeIncrement() {
        // Arrange
        Integer cardId = 1;
        Card card = new Card();
        card.setLikes(1);

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        // Act
        cardService.addLike(cardId);

        // Assert
        verify(cardRepository, times(1)).findById(cardId);

        assertEquals(2, card.getLikes());
        verify(cardRepository, times(1)).save(card);
    }

    /**
     * This method tests the functionality of the addLike method in the CardService class when the card is not present.
     * <p>
     * It performs the following steps:
     * 1. Arrange: Set up necessary objects and dependencies for the test.
     * - Create an Integer object 'cardId' and initialize it with the card ID.
     * - Mock the behavior of cardRepository.findById(cardId) method to return an empty Optional.
     * 2. Act & Assert: Verify that the CardNotFoundException is thrown when calling cardService.addLike(cardId).
     * - Use assertThrows method to assert the exception is thrown.
     * 3. Verify: Verify that the cardRepository.findById(cardId) method is called exactly once.
     *
     * @throws CardNotFoundException If the card with the given ID is not found.
     */
    @Test
    void testAddLikeCardNotPresent() {
        // Arrange
        Integer cardId = 1;

        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CardNotFoundException.class, () -> cardService.addLike(cardId));
        verify(cardRepository, times(1)).findById(cardId);
    }

    /**
     * This method tests the functionality of the editCard method in the CardService class
     * when the card is present. It performs the following steps:
     * <p>
     * 1. Arrange: Set up the necessary objects and mock dependencies for the test.
     * - Create an Integer object 'cardId' and initialize it with the card ID.
     * - Create a NewCardDto object 'newCardDto'.
     * - Create a Card object 'card'.
     * - Create a CardDto object 'cardDto' and set its cardId to the 'cardId'.
     * - Mock the behavior of cardRepository.findById(cardId) to return an Optional containing the 'card'.
     * - Mock the behavior of modelMapper.map(card, NewCardDto.class) to return the 'newCardDto'.
     * - Mock the behavior of cardRepository.save(card) to return the 'card'.
     * - Mock the behavior of modelMapper.map(card, CardDto.class) to return the 'cardDto'.
     * <p>
     * 2. Act: Call the editCard method with the 'cardId' and 'newCardDto'.
     * <p>
     * 3. Assert: Verify that the expected method invocations are made and the result is not null
     * and matches the expected value.
     * - Verify that cardRepository.findById(cardId) is called exactly once.
     * - Verify that cardRepository.save(card) is called exactly once.
     * - Verify that modelMapper.map(card, CardDto.class) is called exactly once.
     * - Verify that the result is not null.
     * - Verify that the result is equal to 'cardDto'.
     */
    @Test
    void testEditCard_CardPresent() {
        // Arrange
        Integer cardId = 1;
        NewCardDto newCardDto = new NewCardDto();
        Card card = new Card();
        CardDto cardDto = new CardDto();
        cardDto.setCardId(cardId);

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
        when(modelMapper.map(card, NewCardDto.class)).thenReturn(newCardDto);
        when(cardRepository.save(card)).thenReturn(card);
        when(modelMapper.map(card, CardDto.class)).thenReturn(cardDto);

        // Act
        CardDto result = cardService.editCard(cardId, newCardDto);

        // Assert
        verify(cardRepository, times(1)).findById(cardId);
        verify(cardRepository, times(1)).save(card);
        verify(modelMapper, times(1)).map(card, CardDto.class);

        assertNotNull(result);
        assertEquals(cardDto, result);
    }

    /**
     * This method tests the functionality of the editCard method in the CardService class when the card is not present.
     * <p>
     * It performs the following steps:
     * 1. Arrange: Set up necessary objects and dependencies for the test.
     * - Create an Integer object 'cardId' and initialize it with the card ID.
     * - Create a NewCardDto object 'newCardDto'.
     * - Mock the behavior of cardRepository.findById(cardId) method to return an empty Optional.
     * <p>
     * 2. Act & Assert: Verify that the CardNotFoundException is thrown when calling cardService.editCard(cardId, newCardDto).
     * - Use assertThrows method to assert that the exception is thrown.
     * <p>
     * 3. Verify: Verify that the cardRepository.findById(cardId) method is called exactly once.
     */
    @Test
    void testEditCard_CardNotPresent() {
        // Arrange
        Integer cardId = 1;
        NewCardDto newCardDto = new NewCardDto();

        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CardNotFoundException.class, () -> cardService.editCard(cardId, newCardDto));
        verify(cardRepository, times(1)).findById(cardId);
    }

    /**
     * This method tests the editBookmark method of the CardService class when the card is present and the bookmark value is true.
     * It performs the following steps:
     * 1. Arrange: Create the necessary objects and mock dependencies for the test.
     * - Create an Integer object 'cardId' and initialize it with the card ID.
     * - Create a Card object 'card'.
     * - Create a CardDto object 'cardDto' and set its cardId to 'cardId' and bookmark value to true.
     * - Mock the behavior of cardRepository.findById(cardId) to return an Optional containing the 'card'.
     * 2. Act: Call the editBookmark method of cardService with 'cardId', true, and 'cardDto'.
     * 3. Assert: Verify that the expected method invocations are made and the result is not null and matches the expected value.
     * - Verify that cardRepository.findById(cardId) is called exactly once.
     * - Verify that cardRepository.save(card) is called exactly once.
     * - Verify that modelMapper.map(card, CardDto.class) is called exactly once.
     * - Verify that the result is not null.
     * - Verify that the result is equal to 'cardDto'.
     */
    @Test
    void testEditBookmark_CardPresent_And_BookmarkTrue() {
        // Arrange
        Integer cardId = 1;
        Card card = new Card();
        CardDto cardDto = new CardDto();
        cardDto.setCardId(cardId);
        cardDto.setBookmark(true);

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        // Act
        CardDto result = cardService.editBookmark(cardId, true, cardDto);

        // Assert
        verify(cardRepository, times(1)).findById(cardId);
        verify(cardRepository, times(1)).save(card);
        verify(modelMapper, times(1)).map(card, CardDto.class);

        assertNotNull(result);
        assertEquals(cardDto, result);
        assertEquals(cardDto.isBookmark(), result.isBookmark());
    }

    /**
     * Method to test the editBookmark method of the CardService class when a card is present and bookmark flag is set to false.
     * <p>
     * The method performs the following steps:
     * 1. Initializes cardId variable with value 1.
     * 2. Creates a new Card object.
     * 3. Creates a new CardDto object and sets its cardId and bookmark fields based on provided values.
     * 4. Mocks the cardRepository.findById method to return Optional.of(card) when called with cardId.
     * 5. Calls the editBookmark method of cardService, passing cardId, false, and cardDto as parameters.
     * 6. Verifies that cardRepository.findById method was called exactly once with cardId.
     * 7. Verifies that cardRepository.save method was called exactly once with card.
     * 8. Verifies that modelMapper.map method was called exactly once with card and CardDto.class.
     * 9. Asserts that the result is not null.
     * 10. Asserts that the returned result is equal to cardDto.
     * 11. Asserts that the returned result's bookmark field is equal to cardDto's bookmark field.
     */
    @Test
    void testEditBookmark_CardPresent_And_BookmarkFalse() {
        // Arrange
        Integer cardId = 1;
        Card card = new Card();
        CardDto cardDto = new CardDto();
        cardDto.setCardId(cardId);
        cardDto.setBookmark(false);

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        // Act
        CardDto result = cardService.editBookmark(cardId, false, cardDto);

        // Assert
        verify(cardRepository, times(1)).findById(cardId);
        verify(cardRepository, times(1)).save(card);
        verify(modelMapper, times(1)).map(card, CardDto.class);

        assertNotNull(result);
        assertEquals(cardDto, result);
        assertEquals(cardDto.isBookmark(), result.isBookmark());
    }

    /**
     * This method tests the behavior of the editBookmark method in the CardService class
     * when the specified card is not present in the database.
     * <p>
     * It performs the following steps:
     * 1. Creates a cardId variable and sets it to 1.
     * 2. Creates a CardDto object.
     * 3. Configures the behavior of the cardRepository mock object to return an empty Optional when the findById method is called with the cardId.
     * 4. Calls the editBookmark method of the cardService instance with the cardId, a boolean value of true, and the cardDto.
     * 5. Asserts that the method throws a CardNotFoundException.
     * 6. Verifies that the findById method of the cardRepository mock object is called exactly once with the cardId.
     */
    @Test
    void testEditBookmark_CardNotPresent() {
        // Arrange
        Integer cardId = 1;
        CardDto cardDto = new CardDto();

        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CardNotFoundException.class, () -> cardService.editBookmark(cardId, true, cardDto));
        verify(cardRepository, times(1)).findById(cardId);
    }

    /**
     * Test method for {@link CardService#deleteCard(Integer)} when card is present.
     * This method tests the functionality of deleting a card when the card is present in the database.
     *
     * <p>
     * The test performs the following steps:
     * <ul>
     *   <li>Arranges the necessary objects and data for the test.</li>
     *   <li>Mocks the behavior of the {@code cardRepository} to return the specified card for the given card ID.</li>
     *   <li>Invokes the {@code deleteCard} method of the {@code cardService} with the specified card ID.</li>
     *   <li>Verifies that the {@code findById} method of the {@code cardRepository} is called exactly once with the specified card ID.</li>
     *   <li>Verifies that the {@code deleteById} method of the {@code cardRepository} is called exactly once with the specified card ID.</li>
     *   <li>Asserts that the result of the {@code deleteCard} method is not null.</li>
     *   <li>Asserts that the result of the {@code deleteCard} method is equal to the expected card DTO.</li>
     * </ul>
     * </p>
     */
    @Test
    void testDeleteCard_CardPresent() {
        // Arrange
        Integer cardId = 1;
        Card card = new Card();
        CardDto cardDto = new CardDto();
        cardDto.setCardId(cardId);

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        // Act
        CardDto result = cardService.deleteCard(cardId);

        // Assert
        verify(cardRepository, times(1)).findById(cardId);
        verify(cardRepository, times(1)).deleteById(cardId);

        assertNotNull(result);
        assertEquals(cardDto, result);
    }

    /**
     * Unit test for {@link CardService#deleteCard(Integer)}.
     * Verifies the behavior when attempting to delete a card that is not present in the card repository.
     */
    @Test
    void testDeleteCard_CardNotPresent() {
        // Arrange
        Integer cardId = 1;

        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CardNotFoundException.class, () -> cardService.deleteCard(cardId));
        verify(cardRepository, times(1)).findById(cardId);
    }
}
