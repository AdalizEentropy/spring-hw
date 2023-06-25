package ru.nspk.card.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import ru.nspk.card.CardProperties;
import ru.nspk.card.model.Card;
import ru.nspk.card.model.CardProgram;

@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardProperties properties;
    private long cardNumberCounter = 1;

    public Optional<Card> createCard(CardProgram program) {
        if (!properties.addCard()) {
            return Optional.empty();
        }

        // сделала максимально просто и без пропертей
        var cardNumber = program.getProgramPrefix() + String.format("%08d", getNext());
        return Optional.of(new Card(cardNumber, program));
    }

    private long getNext() {
        return cardNumberCounter++;
    }
}
