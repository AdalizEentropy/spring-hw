package ru.nspk.card.service;

import java.util.Optional;
import ru.nspk.card.model.Card;
import ru.nspk.card.model.CardProgram;

public interface CardService {

    Optional<Card> createCard(CardProgram program);
}
