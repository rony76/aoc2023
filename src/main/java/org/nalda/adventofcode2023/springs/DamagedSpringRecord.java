package org.nalda.adventofcode2023.springs;

import lombok.Getter;

@Getter
public class DamagedSpringRecord {

    private final SpringCondition[] conditions;
    private final int[] groups;

    public DamagedSpringRecord(SpringCondition[] conditions, int[] groups) {
        this.conditions = conditions;
        this.groups = groups;
    }

    private enum MatcherState {
        ACCEPTS_BOTH,
        NEEDS_OPERATIONAL,
        NEEDS_DAMAGED
    }

    public long getNumberOfArrangements() {
        int condIndex = 0;
        int groupIndex = 0;
        int expectedDamaged = 0;
        boolean failed = false;

        MatcherState state = MatcherState.ACCEPTS_BOTH;

        while (!failed && condIndex < conditions.length) {
            final SpringCondition condition = conditions[condIndex];

            switch (state) {
                case ACCEPTS_BOTH -> {
                    switch (condition) {
                        case UNKNOWN -> {
                            throw new UnsupportedOperationException();
                        }
                        case OPERATIONAL -> {
                            // it's fine, we can stay here...
                        }
                        case DAMAGED -> {
                            var group = groups[groupIndex];
                            if (group == 1) {
                                groupIndex++;
                                state = MatcherState.NEEDS_OPERATIONAL;
                            } else {
                                state = MatcherState.NEEDS_DAMAGED;
                                expectedDamaged = group - 1;
                            }
                        }
                    }
                }
                case NEEDS_DAMAGED -> {
                    switch (condition) {
                        case UNKNOWN -> {
                            throw new UnsupportedOperationException();
                        }
                        case OPERATIONAL -> {
                            failed = true;
                        }
                        case DAMAGED -> {
                            expectedDamaged--;
                            if (expectedDamaged == 0) {
                                groupIndex++;
                                state = MatcherState.NEEDS_OPERATIONAL;
                            }
                        }
                    }
                }

                case NEEDS_OPERATIONAL -> {
                    switch (condition) {
                        case UNKNOWN -> {
                            throw new UnsupportedOperationException();
                        }
                        case OPERATIONAL -> {
                            state = MatcherState.ACCEPTS_BOTH;
                        }
                        case DAMAGED -> {
                            failed = true;
                        }
                    }
                }
            }

            condIndex++;
        }

        if (failed) {
            return 0L;
        }

        if (groupIndex == groups.length && isValidFinalState(state)) {
            return 1L;
        }

        return 0L;
    }

    private boolean isValidFinalState(MatcherState state) {
        return state == MatcherState.ACCEPTS_BOTH || state == MatcherState.NEEDS_OPERATIONAL;
    }
}
