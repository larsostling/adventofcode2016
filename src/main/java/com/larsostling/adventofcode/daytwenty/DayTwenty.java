package com.larsostling.adventofcode.daytwenty;

import com.larsostling.adventofcode.Puzzle;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DayTwenty implements Puzzle {
    @Override
    public String solvePartOne(String input) {
        List<IpRange> ipRanges = parseIpRanges(input);

        long lowestIp = 0;
        for (IpRange range : ipRanges) {
            if (lowestIp < range.low) {
                break;
            } else if (lowestIp <= range.high) {
                lowestIp = range.high + 1;
            }
        }
        return String.valueOf(lowestIp);
    }

    @Override
    public String solvePartTwo(String input) {
        List<IpRange> ipRanges = parseIpRanges(input);

        long lowestIp = 0;
        long allowedIps = 0;
        for (IpRange range : ipRanges) {
            if (lowestIp < range.low) {
                allowedIps += range.low - lowestIp;
            }
            if (lowestIp <= range.high) {
                lowestIp = range.high + 1;
            }
        }
        allowedIps += 4294967295L - lowestIp + 1;
        return String.valueOf(allowedIps);
    }

    private static List<IpRange> parseIpRanges(String input) {
        return Arrays.stream(input.split("\n"))
                .map(DayTwenty::parseIpRange)
                .sorted(IpRange::comparator)
                .collect(Collectors.toList());
    }

    private static IpRange parseIpRange(String input) {
        String[] parts = input.split("-");
        return new IpRange(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }

    private static class IpRange {
        private final long low;
        private final long high;

        public IpRange(long low, long high) {
            this.low = low;
            this.high = high;
        }

        public boolean isInRange(long ip) {
            return low <= ip && ip <= high;
        }

        public static int comparator(IpRange a, IpRange b) {
            return a.low > b.low ? 1 : -1;
        }
    }
}
