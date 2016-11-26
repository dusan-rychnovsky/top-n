package cz.dusanrychnovsky.topn;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Slf4j
public class GenerateTestFile {

  public static void main(String[] args) throws IOException {

    Random rnd = new Random(0);
    int NUM_BLOCKS = 100, MAX_BLOCK_SIZE = 10_000_000, MAX_VALUE_SIZE = 10000;

    File file = new File("src/test/resources/dump");
    try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {

      for (int i = 0; i < NUM_BLOCKS; i++) {
        log.info("Generating block no. " + i);

        // i-th block
        int blockSize = rnd.nextInt(MAX_BLOCK_SIZE);
        for (int j = 0; j < blockSize; j++) {
          out.write(entry(rnd.nextInt(MAX_VALUE_SIZE)));
          out.newLine();
        }

        // i-th largest number
        out.write(entry(MAX_VALUE_SIZE + i));
        out.newLine();
      }
    }

    log.info("DONE");
  }

  private static String entry(int value) {
    return UUID.randomUUID().toString() + " " + value;
  }
}
