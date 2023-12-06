package org.nalda.adventofcode2023.boatraces;

import java.math.BigInteger;
import java.util.stream.LongStream;

public record Race(long time, BigInteger currentRecord) {

    public long bruteForceToWinningWays() {
        return LongStream.rangeClosed(1, time)
                .mapToObj(this::distanceAfterHoldingButton)
                .filter(distance -> distance.compareTo(currentRecord) > 0)
                .count();
    }

    public long bisectToWinningWays() {
        var lastWinningWay = findLastWinningWay(1, time - 1, "");
        var firstWinningWay = findFirstWinningWay(1, lastWinningWay, "");
        return lastWinningWay - firstWinningWay + 1;
    }

    /*
    * This is based on the following observation:
    * If we call t the total time available, and r the current record and x the time we hold the button,
    * we have the following formula:
    * - speed = x
    * - movementTime = t - x
    * - distance = speed * movementTime = x * (t - x)
    * - for the record r, we want to find all x such that x * (t - x) > r
    *
    * We can rewrite this as:
    * - x * (t - x) > r
    * - x * t - x^2 > r
    * - x^2 - x * t + r < 0
    *
    * This is the equation of a parabola, and we want to find all x such that the parabola is below the x axis.
    *
    * We can find the roots of the parabola using the quadratic formula:
    * - x1 = (t - sqrt(t^2 - 4 * r)) / 2
    * - x2 = (t + sqrt(t^2 - 4 * r)) / 2
    * */
    public long formulaToWinningWays() {
        var t = BigInteger.valueOf(time);
        var r = currentRecord;
        var det = t.multiply(t).subtract(r.multiply(BigInteger.valueOf(4)));
        var sqrt = det.sqrt();
        var x1 = t.subtract(sqrt).divide(BigInteger.valueOf(2));
        var x2 = t.add(sqrt).divide(BigInteger.valueOf(2));
        return x2.subtract(x1).longValue();
    }

    private boolean isWinningPressTime(long pressTime) {
        return distanceAfterHoldingButton(pressTime).compareTo(currentRecord) > 0;
    }

    private long findFirstWinningWay(long start, long end, String prefix) {
        if (end < start) {
            return -1;
        }
        if (end == start) {
            if (!isWinningPressTime(end)) {
                return -1;
            }
            return end;
        }

        var mid = (end - start) / 2 + start;
        var midIsWinning = isWinningPressTime(mid);

        if (midIsWinning) {
            var winningBeforeMid = findFirstWinningWay(start, mid - 1, prefix + " ");
            if (winningBeforeMid == -1) {
                return mid;
            } else {
                return winningBeforeMid;
            }
        } else {
            return findFirstWinningWay(mid + 1, end, prefix + " ");
        }
    }

    private long findLastWinningWay(long start, long end, String prefix) {
        if (end < start) {
            return -1;
        }
        if (end == start) {
            var isWinning = isWinningPressTime(end);
            if (!isWinning) {
                return -1;
            }
            return end;
        }

        var mid = (end - start) / 2 + start;
        var midIsWinning = isWinningPressTime(mid);
        if (midIsWinning) {
            var winningAfterMid = findLastWinningWay(mid + 1, end, prefix + " ");
            if (winningAfterMid == -1) {
                return mid;
            } else {
                return winningAfterMid;
            }
        } else {
            return findLastWinningWay(start, mid - 1, prefix + " ");
        }

    }

    private BigInteger distanceAfterHoldingButton(long pressedButtonTime) {
        var speed = pressedButtonTime;
        var movementTime = time - pressedButtonTime;
        return BigInteger.valueOf(speed).multiply(BigInteger.valueOf(movementTime));
    }
}
