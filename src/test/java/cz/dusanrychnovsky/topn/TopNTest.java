package cz.dusanrychnovsky.topn;

import lombok.Value;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class TopNTest {

  @Test
  public void returnsTopNItems() {

    List<Entry> frequencies = asList(
        new Entry("Jiří",	298728),
        new Entry("Jan", 294354),
        new Entry("Petr",	272439),
        new Entry("Marie", 266961),
        new Entry("Jana", 266160),
        new Entry("Josef", 216078),
        new Entry("Pavel", 201787),
        new Entry("Martin", 187420),
        new Entry("Tomáš", 180370),
        new Entry("Jaroslav", 175760),
        new Entry("Eva", 153453),
        new Entry("Miroslav", 149655),
        new Entry("Hana", 146686),
        new Entry("Anna", 135880),
        new Entry("Zdeněk", 128019),
        new Entry("Václav", 125647),
        new Entry("František", 125605),
        new Entry("Michal", 122740),
        new Entry("Lenka", 118822),
        new Entry("Kateřina", 117478)
    );

    List<Entry> shuffledFrequencies = shuffle(frequencies, new Random(0));

    List<Entry> expectedTop5Frequencies = frequencies.subList(0, 5);

    List<Entry> actualTop5Frequencies = TopN.getTopN(
        shuffledFrequencies,
        (first, second) -> Integer.compare(first.getCount(), second.getCount()),
        5
    );

    assertEquals(expectedTop5Frequencies, actualTop5Frequencies);
  }

  private <T> List<T> shuffle(List<T> list, Random rnd) {

    List<T> result = new ArrayList<T>(list.size());
    result.addAll(list);

    Collections.shuffle(result, rnd);
    return result;
  }

  @Value
  private static class Entry {
    String name;
    int count;
  }
}
