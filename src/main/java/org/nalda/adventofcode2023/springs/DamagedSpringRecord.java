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

    private abstract class MatcherState {
        protected final int groupIndex;

        protected MatcherState(int groupIndex) {
            this.groupIndex = groupIndex;
        }

        boolean hasFailed() {
            return false;
        }

        abstract boolean isValidFinal();
        abstract MatcherState process(SpringCondition condition);
    }

    private class AcceptsBothState extends MatcherState {
        protected AcceptsBothState(int groupIndex) {
            super(groupIndex);
        }

        @Override
        boolean isValidFinal() {
            return groupIndex == groups.length;
        }

        @Override
        MatcherState process(SpringCondition condition) {
            switch (condition) {
                case UNKNOWN -> {
                    throw new UnsupportedOperationException();
                }
                case OPERATIONAL -> {
                    return this;
                }
                case DAMAGED -> {
                    var group = groups[groupIndex];
                    if (group == 1) {
                        return new NeedsOperationalState(groupIndex + 1);
                    } else {
                        return new NeedsDamagedState(groupIndex, group - 1);
                    }
                }
            }
            throw new UnsupportedOperationException();
        }
    }

    private class NeedsOperationalState extends MatcherState {
        protected NeedsOperationalState(int groupIndex) {
            super(groupIndex);
        }

        @Override
        boolean isValidFinal() {
            return groupIndex == groups.length;
        }

        @Override
        MatcherState process(SpringCondition condition) {
            switch (condition) {
                case UNKNOWN -> {
                    throw new UnsupportedOperationException();
                }
                case OPERATIONAL -> {
                    return new AcceptsBothState(groupIndex);
                }
                case DAMAGED -> {
                    return new FailedState();
                }
            }

            throw new UnsupportedOperationException();
        }
    }

    private class NeedsDamagedState extends MatcherState {
        private final int expectedDamaged;

        private NeedsDamagedState(int groupIndex, int expectedDamaged) {
            super(groupIndex);
            this.expectedDamaged = expectedDamaged;
        }

        @Override
        boolean isValidFinal() {
            return false;
        }

        @Override
        MatcherState process(SpringCondition condition) {
            switch (condition) {
                case UNKNOWN -> {
                    throw new UnsupportedOperationException();
                }
                case OPERATIONAL -> {
                    return new FailedState();
                }
                case DAMAGED -> {
                    if (expectedDamaged == 1) {
                        return new NeedsOperationalState(groupIndex + 1);
                    }
                    return new NeedsDamagedState(groupIndex, expectedDamaged - 1);
                }
            }

            throw new UnsupportedOperationException();
        }
    }
    private class FailedState extends MatcherState {
        protected FailedState() {
            super(-1);
        }

        @Override
        boolean hasFailed() {
            return true;
        }

        @Override
        boolean isValidFinal() {
            return false;
        }

        @Override
        MatcherState process(SpringCondition condition) {
            throw new UnsupportedOperationException();
        }
    }

    public long getNumberOfArrangements() {
        int condIndex = 0;

        MatcherState state = new AcceptsBothState(0);

        while (condIndex < conditions.length) {
            final SpringCondition condition = conditions[condIndex];

            state = state.process(condition);

            if (state.hasFailed()) {
                return 0L;
            }

            condIndex++;
        }

        if (state.isValidFinal()) {
            return 1L;
        }

        return 0L;
    }
}
