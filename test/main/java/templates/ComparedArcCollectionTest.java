package main.java.templates;

import javafx.scene.paint.Color;
import main.java.io.CircosColorConfLoader;
import main.java.models.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComparedArcCollectionTest {

    @Test
    void getArcLengths() {
        ComparedArcCollection test = new ComparedArcCollection(
                new DrosophilaLowresDM3(),
                new DrosophilaLowresDM6()
        );

        long[] expected = {
                22422827, 22570260, 21146708, 23849507, 27905053, 1351857,
                19524, 23542271, 3667352, 23513712, 25286936, 28110227, 32079331, 1348131
        };
        assertArrayEquals(expected, test.getArcLengths());
    }

    @Test
    void getArcNames() {
        ComparedArcCollection test = new ComparedArcCollection(
                new DrosophilaLowresDM3(),
                new DrosophilaLowresDM6()
        );

        String[] expected = {
                "dmX", "dm2l", "dm2r", "dm3l", "dm3r", "dm4",
                "chrM", "chrX", "chrY", "chr2L", "chr2R", "chr3L", "chr3R", "chr4"
        };
        assertArrayEquals(expected, test.getArcNames());
    }

    @Test
    void getSubregions() {
        // chromosome bands on dm2l (2/6 on dm3)
        int dm2l = 120;
        // chromosome bands on chr4 (8/8 on dm6)
        int chr4 = 45;

        ComparedArcCollection test = new ComparedArcCollection(
                new DrosophilaLowresDM3(),
                new DrosophilaLowresDM6()
        );

        assertEquals(dm2l, test.getSubregions(2).size());
        assertEquals(chr4, test.getSubregions(14).size());
    }

    @Test
    void getArcColors() {
        ArcCollection m1 = new DrosophilaLowresDM3();
        ArcCollection m2 = new DrosophilaLowresDM6();

        ComparedArcCollection test = new ComparedArcCollection(m1, m2);

        List<Color> expectedList = new ArrayList();
        expectedList.addAll(Arrays.asList(m1.getArcColors()));
        expectedList.addAll(Arrays.asList(m2.getArcColors()));

        assertEquals(expectedList, Arrays.asList(test.getArcColors()));

    }
}