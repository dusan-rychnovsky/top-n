package cz.dusanrychnovsky.topn;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

@Slf4j
public class StreamTest {

  public static void main(String[] args) throws IOException {

    List<Entry> results = measureDuration(() -> {

      File file = new File("src/test/resources/dump");
      Stream<Entry> entries = Files.lines(Paths.get(file.getPath()))
          .map(Entry::parseFrom);

      int N = 100;
      return TopN.getTopN(
          entries,
          (first, second) -> Integer.compare(first.getCount(), second.getCount()),
          N
      );
    });

    results.forEach(System.out::println);
  }

  private static <T> T measureDuration(Callable<T> block) {

    log.info("----------------------------------------------------");

    long startTimeMs = System.currentTimeMillis();
    log.info("START TIME: " + startTimeMs);

    T result;
    try {
      result = block.call();
    }
    catch (Exception ex) {
      throw new RuntimeException(ex);
    }

    long finishTimeMs = System.currentTimeMillis();
    log.info("FINISH TIME: " + finishTimeMs);

    long duration = finishTimeMs - startTimeMs;
    log.info("DURATION: " + duration + " ms");

    log.info("----------------------------------------------------");

    return result;
  }

  @Value
  private static class Entry {

    String id;
    int count;

    public static Entry parseFrom(String line) {
      String[] tokens = line.split(" ");
      return new Entry(tokens[0], Integer.parseInt(tokens[1]));
    }

    @Override
    public String toString() {
      return id + " " + count;
    }
  }
}
