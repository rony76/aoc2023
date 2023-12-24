package org.nalda.adventofcode2023.pulse;

import lombok.RequiredArgsConstructor;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Pulse {

    private Module broadcaster;
    private Module rx;

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

    private static class Conjunction extends Module {
        private Signal[] upstreamSignals;

        public Conjunction(String name) {
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

        public Broadcaster() {
            super("broadcaster");
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
        buildModuleNetwork(input);
    }

    private void buildModuleNetwork(List<String> input) {
        Map<String, Module> modulesByName = new HashMap<>(input.size());
        Map<Module, String[]> downstreamModuleNamesByModule = new HashMap<>(input.size());

        for (String line : input) {
            String[] attributesAndConnections = line.split(" -> ");
            String attributes = attributesAndConnections[0];
            Module module;
            String name;

            if (attributes.equals("broadcaster")) {
                name = attributes;
                module = broadcaster = new Broadcaster();
            } else {
                name = attributes.substring(1);
                char type = attributes.charAt(0);
                module = switch (type) {
                    case '%' -> new FlipFlop(name);
                    case '&' -> new Conjunction(name);
                    default -> throw new IllegalArgumentException("Unexpected module type " + type);
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
                    if (connection.equals("rx")) {
                        target = rx = new Sink("rx");
                    } else {
                        target = new Sink(connection);
                    }
                    modulesByName.put(connection, target);
                }
                source.connectTo(target);
            }
        });

        downstreamModuleNamesByModule.keySet().forEach(Module::afterConnectionsBuilt);

    }

    public long calcPulsesProduct() {
        AtomicLong lowCounter = new AtomicLong(0);
        AtomicLong highCounter = new AtomicLong(0);

        for (int i = 0; i < 1000; i++) {
            pressButton(ts -> {
                if (ts.signal == Signal.HIGH) {
                    highCounter.incrementAndGet();
                } else {
                    lowCounter.incrementAndGet();
                }
            });
        }

        long high = highCounter.get();
        long low = lowCounter.get();

        System.out.printf("High: %d; Low: %d%n", high, low);

        return high * low;
    }

    public void outputCycles() {
        Map<String, Integer> emissions = new HashMap<>();

        AtomicLong i = new AtomicLong(1);
        while (i.get() < 10_000) {
            pressButton(ts -> {
                if (ts.from != null
                        && ts.from.getClass().equals(Conjunction.class)
                        && ts.signal == Signal.LOW) {
                    Integer prevEmissions = emissions.getOrDefault(ts.from.name, 0);
                    if (prevEmissions < 3) {
                        System.out.printf("Conjunction %s emitted low after %d%n", ts.from.name, i.get());
                        emissions.put(ts.from.name, prevEmissions + 1);
                    }
                }
            });

            i.incrementAndGet();
        }
    }

    private void pressButton(Consumer<TargetedSignal> signalObserver) {
        Queue<TargetedSignal> signals = new ArrayDeque<>();
        TargetedSignal initialSignal = new TargetedSignal(null, broadcaster, Signal.LOW);
        signals.offer(initialSignal);

        while (!signals.isEmpty()) {
            TargetedSignal targetedSignal = signals.poll();
            signalObserver.accept(targetedSignal);
            Stream<TargetedSignal> downStreamSignals = targetedSignal.to.processSignal(targetedSignal.signal, targetedSignal.from);
            downStreamSignals.forEach(signals::offer);
        }
    }

    public void printRxBackwards() {
        Set<Module> alreadyPrinted = new HashSet<>();
        printBackwards(rx, "", alreadyPrinted);
    }

    private void printBackwards(Module module, String indent, Set<Module> alreadyPrinted) {
        System.out.printf("%s%c%s%s%n",
                indent,
                module.getClass().getSimpleName().charAt(0),
                module.name,
                alreadyPrinted.contains(module) ? "*" : "");

        if (!alreadyPrinted.contains(module)) {
            alreadyPrinted.add(module);
            for (Module upstreamModule : module.upstreamModules) {
                printBackwards(upstreamModule, indent + " +- ", alreadyPrinted);
            }
        }
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtil.getLineList("pulse-input.txt");

        Pulse pulse = new Pulse(input);

        long product = pulse.calcPulsesProduct();

        System.out.println("Product of pulses is: " + product);

        pulse = new Pulse(input);
        pulse.printRxBackwards();

        pulse.outputCycles();

    }
}
