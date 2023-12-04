package org.nalda.adventofcode2023.engine;

import java.util.LinkedList;
import java.util.OptionalLong;

class GearFrame {
    private final ThreeLines threeLines;
    private final int lineLength;
    private final int index;
    private final LinkedList<Long> partNumbers = new LinkedList<>();

    public GearFrame(ThreeLines threeLines, int index) {
        this.threeLines = threeLines;
        this.index = index;
        lineLength = threeLines.getLineLength();
    }

    public OptionalLong findGearRatio() {
        lookForPartNumberGoingLeft(threeLines.centralLine(), index - 1);
        lookForPartNumberGoingRight(threeLines.centralLine(), index + 1);

        if (Character.isDigit(threeLines.topLine().charAt(index))) {
            lookForPartNumberAroundIndex(threeLines.topLine());
        } else {
            lookForPartNumberGoingLeft(threeLines.topLine(), index - 1);
            lookForPartNumberGoingRight(threeLines.topLine(), index + 1);
        }

        if (Character.isDigit(threeLines.bottomLine().charAt(index))) {
            lookForPartNumberAroundIndex(threeLines.bottomLine());
        } else {
            lookForPartNumberGoingLeft(threeLines.bottomLine(), index - 1);
            lookForPartNumberGoingRight(threeLines.bottomLine(), index + 1);
        }

        return calcRatioOnlyIfTwoPartsHaveBeenDetected();
    }

    private OptionalLong calcRatioOnlyIfTwoPartsHaveBeenDetected() {
        if (partNumbers.size() == 2) {
            return OptionalLong.of(partNumbers.get(0) * partNumbers.get(1));
        }
        return OptionalLong.empty();
    }

    private void lookForPartNumberAroundIndex(String line) {
        int left = index - 1;
        while (left >= 0 && Character.isDigit(line.charAt(left))) {
            left--;
        }
        left++;

        int right = index + 1;
        while (right < lineLength && Character.isDigit(line.charAt(right))) {
            right++;
        }
        right--;

        partNumbers.add(Long.parseLong(line.substring(left, right + 1)));
    }

    private void lookForPartNumberGoingRight(String line, int startIndex) {
        int right = startIndex;
        while (right < lineLength && Character.isDigit(line.charAt(right))) {
            right++;
        }

        right--;
        if (right >= startIndex) {
            partNumbers.add(Long.parseLong(line.substring(startIndex, right + 1)));
        }
    }

    private void lookForPartNumberGoingLeft(String line, int startIndex) {
        int left = startIndex;
        while (left >= 0 && Character.isDigit(line.charAt(left))) {
            left--;
        }

        left++;
        if (left <= startIndex) {
            partNumbers.add(Long.parseLong(line.substring(left, startIndex + 1)));
        }
    }
}
