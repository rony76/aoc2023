package org.nalda.adventofcode2023.poker;

import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class PokerHand implements Comparable<PokerHand> {
    private PokerHand(HandType type, int[] cards) {
        this.type = type;
        this.cards = cards;
    }

    private static int[] mapCards(String cards) {
        return cards.chars()
                .map(c -> switch (c) {
                    case 'A' -> 14;
                    case 'K' -> 13;
                    case 'Q' -> 12;
                    case 'J' -> 11;
                    case 'T' -> 10;
                    default -> c - '0';
                })
                .toArray();
    }

    private static HandType determineType(int[] cards) {
        final Map<Integer, Long> occurrenceMap = Arrays.stream(cards)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return Arrays.stream(HandType.values())
                .filter(handType -> handType.check(occurrenceMap))
                .findFirst()
                .orElse(HandType.HIGH_CARD);
    }

    public static PokerHand of(String cardString) {
        var cards = mapCards(cardString);
        var type = determineType(cards);

        return new PokerHand(type, cards);
    }

    private interface HandTypeChecker {
        boolean check(Map<Integer, Long> cards);
    }

    public enum HandType implements HandTypeChecker {
        FIVE_OF_A_KIND {
            @Override
            public boolean check(Map<Integer, Long> cards) {
                return cards.size() == 1;
            }
        },
        FOUR_OF_A_KIND {
            @Override
            public boolean check(Map<Integer, Long> cards) {
                return cards.values().stream().anyMatch(count -> count == 4);
            }
        },
        FULL_HOUSE {
            @Override
            public boolean check(Map<Integer, Long> cards) {
                if (cards.size() != 2) {
                    return false;
                }
                return cards.values().stream().anyMatch(count -> count == 3);
            }
        },
        THREE_OF_A_KIND {
            @Override
            public boolean check(Map<Integer, Long> cards) {
                if (cards.size() != 3) {
                    return false;
                }
                return cards.values().stream().anyMatch(count -> count == 3);
            }
        },
        TWO_PAIR {
            @Override
            public boolean check(Map<Integer, Long> cards) {
                return cards.values().stream().filter(count -> count == 2).count() == 2;
            }
        },
        ONE_PAIR {
            @Override
            public boolean check(Map<Integer, Long> cards) {
                return cards.values().stream().anyMatch(count -> count == 2);
            }
        },
        HIGH_CARD {
            @Override
            public boolean check(Map<Integer, Long> cards) {
                return true;
            }
        };
    }

    final int cards[];
    final HandType type;


    @Override
    public int compareTo(PokerHand o) {
        final int typeComparison = type.compareTo(o.type);
        if (typeComparison != 0) {
            return -typeComparison;
        }

        for (int i = 0; i < cards.length; i++) {
            final int cardComparison = Integer.compare(cards[i], o.cards[i]);
            if (cardComparison != 0) {
                return cardComparison;
            }
        }

        return 0;
    }

    @Override
    public String toString() {
        return "%s (%s)".formatted(Arrays.toString(cards), type);
    }
}
