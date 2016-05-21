package com.streaming.domain.hls;

import org.apache.commons.lang3.NotImplementedException;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

class HLSStreamInfComparator implements Comparator<HLSStreamInf> {
    private static final String NOT_IMPLMENTED = "This has not been implemented";

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
        throw new NotImplementedException(NOT_IMPLMENTED);
    }

    @Override
    public Comparator<HLSStreamInf> thenComparing(Comparator<? super HLSStreamInf> other) {
        throw new NotImplementedException(NOT_IMPLMENTED);
    }

    @Override
    public <U> Comparator<HLSStreamInf> thenComparing(Function<? super HLSStreamInf, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        throw new NotImplementedException(NOT_IMPLMENTED);
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<HLSStreamInf> thenComparing(Function<? super HLSStreamInf, ? extends U> keyExtractor) {
        throw new NotImplementedException(NOT_IMPLMENTED);
    }

    @Override
    public Comparator<HLSStreamInf> thenComparingInt(ToIntFunction<? super HLSStreamInf> keyExtractor) {
        throw new NotImplementedException(NOT_IMPLMENTED);
    }

    @Override
    public Comparator<HLSStreamInf> thenComparingLong(ToLongFunction<? super HLSStreamInf> keyExtractor) {
        throw new NotImplementedException(NOT_IMPLMENTED);
    }

    @Override
    public Comparator<HLSStreamInf> thenComparingDouble(ToDoubleFunction<? super HLSStreamInf> keyExtractor) {
        throw new NotImplementedException(NOT_IMPLMENTED);
    }
}
