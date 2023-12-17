package org.nalda.adventofcode2023.hash;

import lombok.AllArgsConstructor;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class Hash {
    public long stepSum(String s) {
        return Arrays.stream(s.split(","))
                .mapToLong(this::stepValue)
                .sum();
    }

    int stepValue(String s) {
        long result = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = s.charAt(i);
            result += ascii;
            result *= 17;
            result %= 256;
        }
        return (int) result;
    }

    public static void main(String[] args) {
        final Hash hash = new Hash();
        String line = ResourceUtil.getLineList("hash-input.txt").get(0);

        long sum = hash.stepSum(line);
        System.out.println("Sum of steps' hash: " + sum);

        long power = hash.calculateFocusingPower(line);
        System.out.println("Focusing power: " + power);
    }

    private record Lens(String label, int focalLength) {
    }

    private static class Box {
        private final int index;
        private final LinkedList<Lens> lenses = new LinkedList<>();

        private Box(int index) {
            this.index = index;
        }

        public void setLens(String label, int focalLength) {
            for (var it = lenses.listIterator(); it.hasNext(); ) {
                Lens lens = it.next();
                if (lens.label().equals(label)) {
                    it.set(new Lens(label, focalLength));
                    return;
                }
            }
            lenses.add(new Lens(label, focalLength));
        }

        public void removeLens(String label) {
            for (var it = lenses.listIterator(); it.hasNext(); ) {
                Lens lens = it.next();
                if (lens.label().equals(label)) {
                    it.remove();
                    return;
                }
            }
        }

        public long getFocusingPower() {
            int lensIndex = 1;
            long total = 0;
            for (Lens lens : lenses) {
                total += (long) (index + 1) * lensIndex * lens.focalLength();
                lensIndex++;
            }

            return total;
        }
    }

    @AllArgsConstructor
    private class InitializationStep {

        private Box getBox(Box[] grid) {
            int boxIndex = getBoxIndex();
            if (grid[boxIndex] == null) {
                grid[boxIndex] = new Box(boxIndex);
            }
            return grid[boxIndex];
        }

        private void execute(Box[] grid) {
            Box box = getBox(grid);
            switch (operation) {
                case ASSIGN:
                    box.setLens(label, focalLength);
                    break;
                case REMOVE:
                    box.removeLens(label);
                    break;
            }
        }

        private enum Operation {
            ASSIGN, REMOVE
        }

        private final String label;
        private final Operation operation;
        private final Integer focalLength;

        public int getBoxIndex() {
            return stepValue(label);
        }
    }

    public InitializationStep parseStep(String s) {
        final String[] labelAndOp = s.split("[=-]");
        String label = labelAndOp[0];
        if (labelAndOp.length == 1) {
            return new InitializationStep(label, InitializationStep.Operation.REMOVE, null);
        } else {
            return new InitializationStep(label, InitializationStep.Operation.ASSIGN, Integer.parseInt(labelAndOp[1]));
        }
    }


    public long calculateFocusingPower(String input) {
        Box[] grid = new Box[256];
        Arrays.stream(input.split(","))
                .map(this::parseStep)
                .forEach(step -> step.execute(grid));

        return Arrays.stream(grid)
                .filter(Objects::nonNull)
                .mapToLong(Box::getFocusingPower)
                .sum();
    }

}
