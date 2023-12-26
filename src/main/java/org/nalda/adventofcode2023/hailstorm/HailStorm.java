package org.nalda.adventofcode2023.hailstorm;

import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

public class HailStorm {
    private final long minCoord;
    private final long maxCoord;

    public boolean willCrossPath(HailVector hv1, HailVector hv2) {
        if (hv1.m() == hv2.m()) {
            System.out.println("Parallel vectors");
            return false;
        }
        var x = -(hv2.q() - hv1.q()) / (hv2.m() - hv1.m());
        var y = hv1.m() * x + hv1.q();

        if (x < minCoord || x > maxCoord) {
            System.out.println("They meet outside the x-interval");
            return false;
        }

        if (y < minCoord || y > maxCoord) {
            System.out.println("They meet outside the y-interval");
            return false;
        }

        if (hv1.isXInThePast(x)) {
            System.out.println("They meet in the past for " + hv1);
            return false;
        }

        if (hv2.isXInThePast(x)) {
            System.out.println("They meet in the past for " + hv2);
            return false;
        }

        System.out.printf("They meet at <%f, %f>%n", x, y);
        return true;
    }

    record HailVector(long px, long py, long vx, long vy) {
        public double m() {
            return (double) vy / vx;
        }

        public double q() {
            return (double) py - px * m();
        }

        public boolean isXInThePast(double x) {
            return vx > 0 ? x < px : x > px;
        }

        public static HailVector fromString(String s) {
            // 226812424329784, 313674772492962, 250967806511035 @ -20, -101, 43
            String[] coords = s.replace('@', ',').split("\\s*,\\s+");
            return new HailVector(
                    Long.parseLong(coords[0]),
                    Long.parseLong(coords[1]),
                    Long.parseLong(coords[3]),
                    Long.parseLong(coords[4])
            );
        }
    }

    public HailStorm(long minCoord, long maxCoord) {
        this.minCoord = minCoord;
        this.maxCoord = maxCoord;
    }

    public static void main(String[] args) {
        List<HailVector> hailVectors = ResourceUtil.getLineStream("hailstorm-input.txt")
                .map(HailVector::fromString)
                .toList();

        HailStorm hailStorm = new HailStorm(200000000000000L, 400000000000000L);
        long count = hailStorm.countCollisions(hailVectors);

        System.out.println("Count of collisions: " + count);
    }

    private long countCollisions(List<HailVector> hailVectors) {
        long result = 0;

        for (int i1 = 0; i1 < hailVectors.size(); i1++) {
            HailVector hv1 = hailVectors.get(i1);
            for (int i2 = i1 + 1; i2 < hailVectors.size(); i2++) {
                HailVector hv2 = hailVectors.get(i2);
                if (willCrossPath(hv1, hv2)) {
                    result++;
                }
            }
        }
        return result;
    }
}
