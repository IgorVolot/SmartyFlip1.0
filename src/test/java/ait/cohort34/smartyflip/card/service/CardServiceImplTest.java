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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CardServiceImpl cardService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

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

    @Test
    void testFindCardById_CardNotPresent() {
        // Arrange
        Integer cardId = 1;

        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CardNotFoundException.class, () -> cardService.findCardById(cardId));
        verify(cardRepository, times(1)).findById(cardId);
    }
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

    @Test
    void testAddLikeCardNotPresent() {
        // Arrange
        Integer cardId = 1;

        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CardNotFoundException.class, () -> cardService.addLike(cardId));
        verify(cardRepository, times(1)).findById(cardId);
    }

    @Test
    void testEditCard_CardPresent() {
        // Arrange
        Integer cardId = 1;
        NewCardDto newCardDto = new NewCardDto();
        Card card = new Card();
        CardDto cardDto = new CardDto();
        cardDto.setCardId(cardId);

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
        when(modelMapper.map(currentCard, NewCardDto.class)).thenReturn(newCardDto);
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
