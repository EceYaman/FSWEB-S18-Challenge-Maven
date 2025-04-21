package com.workintech.fswebs18challengemaven.repository;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Color;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.exceptions.CardException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class CardRepositoryImpl implements CardRepository{
    private final EntityManager entityManager;

    @Autowired
    public CardRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public Card save(Card card) {
        log.info("save started");
        entityManager.persist(card);
        log.info("save ended");
        return card;
    }

    @Override
    public List<Card> findAll() {
        TypedQuery<Card> query = entityManager.createQuery("SELECT c from Card c", Card.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Card update(Card card) {
        return entityManager.merge(card);
    }

    @Override
    public Card findById(long id) {
        Card card = entityManager.find(Card.class,id);
        if(card == null){
            throw new CardException("Card not found: " + id, HttpStatus.NOT_FOUND);
        }
        return card;
    }

    @Override
    public Card remove(long id) {
        Card card = findById(id);
        entityManager.remove(card);
        return card;
    }

    @Override
    public List<Card> findByColor(String color) {
        try {
            Color.valueOf(color);
        } catch (IllegalArgumentException e) {
            throw new CardException("Invalid color: " + color, HttpStatus.BAD_REQUEST);
        }

        TypedQuery<Card> query = entityManager.createQuery("SELECT c from Card c WHERE c.color = :color", Card.class);
        query.setParameter("color", color);
        List<Card> result = query.getResultList();

        if (result.isEmpty()) {
            throw new CardException("No cards found with color: " + color, HttpStatus.NOT_FOUND);
        }

        return result;
    }

    @Override
    public List<Card> findByValue(Integer value) {
        TypedQuery<Card> query = entityManager.createQuery("SELECT c from Card c WHERE c.value = :value ", Card.class);  // Değeri karşılaştırıyoruz.
        query.setParameter("value", value);
        return query.getResultList();
    }

    @Override
    public List<Card> findByType(String type) {
        TypedQuery<Card> query = entityManager.createQuery("SELECT c from Card c WHERE c.type = :type ", Card.class);
        query.setParameter("type", type);
        return query.getResultList();
    }
}
