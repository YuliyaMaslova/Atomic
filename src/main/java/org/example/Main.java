package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
  private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
  private static final AtomicInteger count3 = new AtomicInteger(0);
  private static final AtomicInteger count4 = new AtomicInteger(0);
  private static final AtomicInteger count5 = new AtomicInteger(0);

  public static void main(String[] args) {
    String[] texts = generateTexts(100_000);
    Thread palindromeThread = new Thread(() -> {
      for (String text : texts) {
        if (isPalindrome(text)) {
          int length = text.length();
          if (length == 3) {
            count3.incrementAndGet();
          } else if (length == 4) {
            count4.incrementAndGet();
          } else if (length == 5) {
            count5.incrementAndGet();
          }
        }
      }
    });
    Thread sameLetterThread = new Thread(() -> {
      for (String text : texts) {
        if (isSameLetter(text)) {
          int length = text.length();
          if (length == 3) {
            count3.incrementAndGet();
          } else if (length == 4) {
            count4.incrementAndGet();
          } else if (length == 5) {
            count5.incrementAndGet();
          }
        }
      }
    });
    Thread ascendingThread = new Thread(() -> {
      for (String text : texts) {
        if (isAscending(text)) {
          int length = text.length();
          if (length == 3) {
            count3.incrementAndGet();
          } else if (length == 4) {
            count4.incrementAndGet();
          } else if (length == 5) {
            count5.incrementAndGet();
          }
        }
      }
    });
    palindromeThread.start();
    sameLetterThread.start();
    ascendingThread.start();
    try {
      palindromeThread.join();
      sameLetterThread.join();
      ascendingThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Красивых слов с длиной 3: " + count3);
    System.out.println("Красивых слов с длиной 4: " + count4);
    System.out.println("Красивых слов с длиной 5: " + count5);
  }

  private static String[] generateTexts(int count) {
    String[] texts = new String[count];
    Random random = new Random();
    for (int i = 0; i < count; i++) {
      texts[i] = generateText(LETTERS, 3 + random.nextInt(3));
    }
    return texts;
  }

  private static String generateText(String letters, int length) {
    Random random = new Random();
    StringBuilder text = new StringBuilder();
    for (int i = 0; i < length; i++) {
      text.append(letters.charAt(random.nextInt(letters.length())));
    }
    return text.toString();
  }

  private static boolean isPalindrome(String text) {
    int length = text.length();
    for (int i = 0; i < length / 2; i++) {
      if (text.charAt(i) != text.charAt(length - i - 1)) {
        return false;
      }
    }
    return true;
  }

  private static boolean isSameLetter(String text) {
    for (int i = 1; i < text.length(); i++) {
      if (text.charAt(i) != text.charAt(0)) {
        return false;
      }
    }
    return true;
  }

  private static boolean isAscending(String text) {
    int length = text.length();
    for (int i = 1; i < length; i++) {
      if (text.charAt(i) < text.charAt(i - 1)) {
        return false;
      }
    }
    return true;
  }
}