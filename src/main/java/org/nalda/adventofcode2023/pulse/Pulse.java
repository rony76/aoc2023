package org.nalda.adventofcode2023.pulse;

import lombok.RequiredArgsConstructor;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class Pulse {

    private final Module broadcaster;

    private enum Signal {HIGH, LOW}

    private record TargetedSignal(Module from, Module to, Signal signal) {
        @Override
        public String toString() {
            return "%s -%s-> %s".formatted(from == null ? "button" : from.name, signal.name().toLowerCase(), to.name);
        }
    }

    @RequiredArgsConstructor
    private static abstract class Module {
        private final String name;
        protected List<Module> downstreamModules = new ArrayList<>();
        protected List<Module> upstreamModules = new ArrayList<>();

        protected Stream<TargetedSignal> sendSignals(Signal signal) {
            return downstreamModules.stream().map(n -> new TargetedSignal(this, n, signal));
        }

        public void connectTo(Module downstream) {
            downstreamModules.add(downstream);
            downstream.upstreamModules.add(this);
        }

        public void afterConnectionsBuilt() {
        }

        public abstract Stream<TargetedSignal> processSignal(Signal signal, Module source);
    }

    private static class FlipFlop extends Module {
        private boolean isOn = false;

        public FlipFlop(String name) {
            super(name);
        }

        @Override
        public Stream<TargetedSignal> processSignal(Signal signal, Module source) {
            if (signal.equals(Signal.HIGH)) {
                // ignore this
                return Stream.empty();
            }

            isOn = !isOn;

            return sendSignals(isOn ? Signal.HIGH : Signal.LOW);
        }
    }

    private static class Conjuction extends Module {
        private Signal[] upstreamSignals;

        public Conjuction(String name) {
            super(name);
        }

        @Override
        public void afterConnectionsBuilt() {
            super.afterConnectionsBuilt();
            upstreamSignals = new Signal[upstreamModules.size()];
            Arrays.fill(upstreamSignals, Signal.LOW);
        }

        @Override
        public Stream<TargetedSignal> processSignal(Signal signal, Module source) {
            int idx = upstreamModules.indexOf(source);
            if (idx < 0) {
                throw new IllegalStateException("Unexpected signal from " + source);
            }
            upstreamSignals[idx] = signal;

            boolean allHighs = Arrays.stream(upstreamSignals).allMatch(s -> s.equals(Signal.HIGH));

            return sendSignals(allHighs ? Signal.LOW : Signal.HIGH);
        }
    }

    private static class Broadcaster extends Module {

        public Broadcaster(String name) {
            super(name);
        }

        @Override
        public Stream<TargetedSignal> processSignal(Signal signal, Module source) {
            return sendSignals(signal);
        }
    }

    private static class Sink extends Module {

        public Sink(String name) {
            super(name);
        }

        @Override
        public Stream<TargetedSignal> processSignal(Signal signal, Module source) {
            return Stream.empty();
        }
    }


    public Pulse(List<String> input) {
        broadcaster = buildModuleNetwork(input);
    }

    private Module buildModuleNetwork(List<String> input) {
        Map<String, Module> modulesByName = new HashMap<>(input.size());
        Map<Module, String[]> downstreamModuleNamesByModule = new HashMap<>(input.size());

        for (String line : input) {
            String[] attributesAndConnections = line.split(" -> ");
            Module module;
            String name;
            String attributes = attributesAndConnections[0];
            if (attributes.equals("broadcaster")) {
                name = attributes;
                module = new Broadcaster("broadcaster");
            } else {
                name = attributes.substring(1);
                char type = attributes.charAt(0);
                module = switch (type) {
                    case '%' -> new FlipFlop(name);
                    case '&' -> new Conjuction(name);
                    default -> throw new IllegalArgumentException("Unexpected modujle type " + type);
                };
            }

            modulesByName.put(name, module);

            String[] connections = attributesAndConnections[1].split(",\\s+");
            downstreamModuleNamesByModule.put(module, connections);
        }

        downstreamModuleNamesByModule.forEach((source, connections) -> {
            for (String connection : connections) {
                Module target = modulesByName.get(connection);
                if (target == null) {
                    target = new Sink(connection);
                    modulesByName.put(connection, target);
                }
                source.connectTo(target);
            }
        });

        downstreamModuleNamesByModule.keySet().forEach(Module::afterConnectionsBuilt);

        return modulesByName.get("broadcaster");
    }

    public long calcPulsesProduct() {
        AtomicLong lowCounter = new AtomicLong(0);
        AtomicLong highCounter = new AtomicLong(0);

        for (int i = 0; i < 1000; i++) {
            pressButton(lowCounter, highCounter);
            // System.out.println();
        }

        long high = highCounter.get();
        long low = lowCounter.get();

        System.out.printf("High: %d; Low: %d%n", high, low);

        return high * low;
    }

    private void pressButton(AtomicLong lowCounter, AtomicLong highCounter) {
        Queue<TargetedSignal> signals = new ArrayDeque<>();

        TargetedSignal initialSignal = new TargetedSignal(null, broadcaster, Signal.LOW);
        // System.out.println(initialSignal);
        signals.offer(initialSignal);
        lowCounter.incrementAndGet();

        while (!signals.isEmpty()) {
            TargetedSignal targetedSignal = signals.poll();
            Stream<TargetedSignal> downStreamSignals = targetedSignal.to.processSignal(targetedSignal.signal, targetedSignal.from);
            downStreamSignals
                    .peek(s -> {
                        if (s.signal == Signal.HIGH) {
                            highCounter.incrementAndGet();
                        } else {
                            lowCounter.incrementAndGet();
                        }
                    })
                    // .peek(System.out::println)
                    .forEach(signals::offer);
        }
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtil.getLineList("pulse-input.txt");

        Pulse pulse = new Pulse(input);

        long product = pulse.calcPulsesProduct();

        System.out.println("Product of pulses is: " + product);

    }
}
