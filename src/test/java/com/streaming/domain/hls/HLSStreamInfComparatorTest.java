package com.streaming.domain.hls;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Comparator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HLSStreamInfComparatorTest {

    private HLSStreamInfComparator underTest = new HLSStreamInfComparator();
    @Mock
    private HLSStreamInf s1;
    @Mock
    private HLSStreamInf s2;

    @Before
    public void setup() {
        when(s1.getBandwidth()).thenReturn(100000);
        when(s2.getBandwidth()).thenReturn(1000001);
    }

    @Test
    public void testCompare() throws Exception {
        assertEquals(-1, underTest.compare(s1, s2));
        assertEquals(1, underTest.compare(s2, s1));
        assertEquals(0, underTest.compare(s1, s1));
        assertEquals(0, underTest.compare(s2, s2));
    }

    @Test(expected = NotImplementedException.class)
    public void testReversed() throws Exception {
        underTest.reversed();
    }

    @Test(expected = NotImplementedException.class)
    public void testThenComparing() throws Exception {
        underTest.thenComparing(new Comparator<HLSStreamInf>() {
            @Override
            public int compare(HLSStreamInf o1, HLSStreamInf o2) {
                return 0;
            }
        });
    }
}