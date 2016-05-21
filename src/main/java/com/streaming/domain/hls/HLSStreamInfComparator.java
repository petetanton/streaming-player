package com.streaming.domain.hls;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class HLSStreamInfComparator implements Comparator<HLSStreamInf> {
    @Override
    public int compare(HLSStreamInf o1, HLSStreamInf o2) {
        if (o1.getBandwidth() > o2.getBandwidth())
            return 1;
        if (o1.getBandwidth() < o2.getBandwidth())
            return -1;
        return 0;
    }

    @Override
    public Comparator<HLSStreamInf> reversed() {
        return null;
    }

    @Override
    public Comparator<HLSStreamInf> thenComparing(Comparator<? super HLSStreamInf> other) {
        return null;
    }

    @Override
    public <U> Comparator<HLSStreamInf> thenComparing(Function<? super HLSStreamInf, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return null;
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<HLSStreamInf> thenComparing(Function<? super HLSStreamInf, ? extends U> keyExtractor) {
        return null;
    }

    @Override
    public Comparator<HLSStreamInf> thenComparingInt(ToIntFunction<? super HLSStreamInf> keyExtractor) {
        return null;
    }

    @Override
    public Comparator<HLSStreamInf> thenComparingLong(ToLongFunction<? super HLSStreamInf> keyExtractor) {
        return null;
    }

    @Override
    public Comparator<HLSStreamInf> thenComparingDouble(ToDoubleFunction<? super HLSStreamInf> keyExtractor) {
        return null;
    }
}
