package com.workintech.fswebs18challengemaven.controller;

import com.workintech.fswebs18challengemaven.repository.CardRepository;
import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Color;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.exceptions.CardException;
import com.workintech.fswebs18challengemaven.util.CardValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardRepository cardRepository;

    public CardController(CardRepository cardRepository){
        this.cardRepository=cardRepository;
    }

    @PostMapping
    public Card save(@RequestBody Card card){
        CardValidation.validateCard(card);
        return cardRepository.save(card);
    }
    @GetMapping
    public List<Card> findAll(){
        return cardRepository.findAll();
    }

    @PutMapping("/")
    public ResponseEntity<Card> updateCard(@RequestBody Card card) {
        if (card.getColor() == null || card.getName() == null) {
            throw new CardException("Color and Name are required", HttpStatus.BAD_REQUEST);
        }
        Card updatedCard = cardRepository.update(card);
        return new ResponseEntity<>(updatedCard, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public Card remove(@PathVariable long id){
        return cardRepository.remove(id);
    }

    @GetMapping("/byColor/{color}")
    public List<Card> getByColorType(@PathVariable("color") String color) {
        try {
            throw new CardException("Card not found", HttpStatus.NOT_FOUND);
        } catch (CardException ex) {
            throw new CardException(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byValue/{value}")
    public List<Card> getByValue(@PathVariable("value") Integer value) {
        return cardRepository.findByValue(value);
    }

    @GetMapping("/byType/{type}")
    public List<Card> getByType(@PathVariable("type") String type) {
        return cardRepository.findByType(type);
    }
}
