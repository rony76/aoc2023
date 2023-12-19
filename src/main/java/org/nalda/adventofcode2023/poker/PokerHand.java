package org.nalda.adventofcode2023.poker;

import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class PokerHand implements Comparable<PokerHand> {
    private static final int JOKER = 1;

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
                    case 'J' -> JOKER;
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
        POKER {
            @Override
            public boolean check(Map<Integer, Long> cards) {
                // jokerCount can be up to 5!
                final Long jokerCount = cards.getOrDefault(JOKER, 0L);
                return (jokerCount == 5) || cards.values().stream().anyMatch(count -> count + jokerCount == 5);
            }
        },
        FOUR_OF_A_KIND {
            @Override
            public boolean check(Map<Integer, Long> cards) {
                // jokerCount can be up to 3! With 4 (or 5!) jokers, we'dir have a poker
                final Long jokerCount = cards.getOrDefault(JOKER, 0L);
                return cards.entrySet().stream()
                        .filter(entry -> entry.getKey() != JOKER)
                        .anyMatch(entry -> entry.getValue() + jokerCount == 4);
            }
        },
        FULL_HOUSE {
            @Override
            public boolean check(Map<Integer, Long> cards) {
                // jokerCount can be up to 2! (with 3 or more we'dir have a 4 of a kind or a poker!)
                final Long jokerCount = cards.getOrDefault(JOKER, 0L);
                if (jokerCount == 2) {
                    // if it hasn't been identified as a 4 of a kind, it means the other cards all have a count of 1
                    return false;
                }

                if (jokerCount == 1) {
                    return cards.values().stream().filter(count -> count == 2).count() == 2;
                }

                if (cards.size() != 2) {
                    return false;
                }
                return cards.values().stream().anyMatch(count -> count == 3);
            }
        },
        THREE_OF_A_KIND {
            @Override
            public boolean check(Map<Integer, Long> cards) {
                // jokerCount can be up to 2! (with 3 or more we'dir have a 4 of a kind or a poker!)
                final Long jokerCount = cards.getOrDefault(JOKER, 0L);
                return cards.entrySet().stream()
                        .filter(entry -> entry.getKey() != JOKER)
                        .anyMatch(entry -> entry.getValue() + jokerCount == 3);
            }
        },
        TWO_PAIR {
            @Override
            public boolean check(Map<Integer, Long> cards) {
                // jokers don't contribute to two pairs, as at least one pair should already be there...
                // and it would become a three of a kind!
                final long pairs = cards.values().stream().filter(count -> count == 2).count();
                return pairs == 2;
            }
        },
        ONE_PAIR {
            @Override
            public boolean check(Map<Integer, Long> cards) {
                var anyJoker = cards.containsKey(JOKER);
                return anyJoker || cards.values().stream().anyMatch(count -> count == 2);
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
        final String str = Arrays.stream(cards).mapToObj(c -> {
            if (c == JOKER) {
                return "*";
            } else {
                return switch (c) {
                    case 14 -> "A";
                    case 13 -> "K";
                    case 12 -> "Q";
                    case 11 -> "J";
                    case 10 -> "T";
                    default -> Integer.toString(c);
                };
            }
        }).collect(Collectors.joining(""));
        return "%s (%s)".formatted(str, type);
    }
}
