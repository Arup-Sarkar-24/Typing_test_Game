package com.cisco.product;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class TypingSpeedTest {
    private List<String> sentences;
    private int lineNo;
    private List<String> randomSentences;

    public TypingSpeedTest(List<String> sentences) {
        this.sentences = sentences;
        this.lineNo = 0;
        this.randomSentences = new ArrayList<>();
    }

    public void displaySentences() {
        System.out.println("Welcome to the Typing Speed Test Game!");
        System.out.println("You have to type the given sentences correctly:");
        for (int i = 0; i < sentences.size(); i++) {
            System.out.println("Sentence " + (i + 1) + ": " + sentences.get(i));
        }
    }

    public void getLineNumber() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter how many sentences you want to type: ");
                int lineNumber = Integer.parseInt(scanner.nextLine());
                if (lineNumber <= 0) {
                    System.out.println("Please enter a positive integer.");
                } else {
                    this.lineNo = lineNumber;
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public List<String> compareSentences(String sentence, String inputSentence) {
        List<String> mistakes = new ArrayList<>();
        String[] inputWords = inputSentence.toLowerCase().replaceAll("[\\p{Punct}]", "").split("\\s+");
        String[] sentenceWords = sentence.toLowerCase().replaceAll("[\\p{Punct}]", "").split("\\s+");
        int maxLength = Math.max(inputWords.length, sentenceWords.length);

        for (int i = 0; i < maxLength; i++) {
            if (i < inputWords.length && i < sentenceWords.length) {
                if (!inputWords[i].equals(sentenceWords[i])) {
                    mistakes.add("replace " + sentenceWords[i] + " with " + inputWords[i]);
                }
            } else if (i < inputWords.length) {
                mistakes.add("add " + inputWords[i]);
            } else if (i < sentenceWords.length) {
                mistakes.add("remove " + sentenceWords[i]);
            }
        }
        return mistakes;
    }

    public long measureTime() {
        return System.currentTimeMillis();
    }

    public int[] calculateElapsedTime(long startTime) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        int minutes = (int) (elapsedTime / 60000);
        int seconds = (int) ((elapsedTime % 60000) / 1000);
        return new int[]{minutes, seconds};
    }

    public void selectRandomSentences() {
        Random random = new Random();
        List<String> copy = new ArrayList<>(sentences);
        for (int i = 0; i < lineNo; i++) {
            int randomIndex = random.nextInt(copy.size());
            randomSentences.add(copy.get(randomIndex));
            copy.remove(randomIndex);
        }
    }

    public void runTypingSpeedTest() {
        displaySentences();
        getLineNumber();
        selectRandomSentences();

        long totalStartTime = measureTime();
        int correctTyping = 0;
        int wrongTyping = 0;

        Scanner scanner = new Scanner(System.in);

        for (int n = 0; n < lineNo; n++) {
            String sentenceToType = randomSentences.get(n);
            System.out.println("\nSentence " + (n + 1) + ": " + sentenceToType);

            long startTime = measureTime();

            System.out.print("Enter the sentence: ");
            String inputSentence = scanner.nextLine();

            if (inputSentence.strip().equalsIgnoreCase(sentenceToType.strip())) {
                System.out.println("\nGood job! Next one.\n");
                correctTyping++;
            } else {
                System.out.println("\nWrong.\n");
                wrongTyping++;
                System.out.println("Mistakes found:");
                List<String> mistakes = compareSentences(sentenceToType, inputSentence);
                for (String mistake : mistakes) {
                    System.out.println("Word to " + mistake);
                }
            }

            int[] elapsed = calculateElapsedTime(startTime);
            System.out.println("\nElapsed time for sentence " + (n + 1) + ": " + elapsed[0] + ":" + String.format("%02d", elapsed[1]));
        }

        int[] totalElapsed = calculateElapsedTime(totalStartTime);
        System.out.println("\nTotal runtime: " + totalElapsed[0] + ":" + String.format("%02d", totalElapsed[1]) + " minutes");
        System.out.println("Total correct sentences typed: " + correctTyping);
        System.out.println("Total wrong sentences typed: " + wrongTyping);

        if (correctTyping >= lineNo / 2) {
            System.out.println("Pass");
        } else {
            System.out.println("Fail");
        }
    }

    public static void main(String[] args) {
        List<String> sentences = new ArrayList<>();
        sentences.add("The quick brown fox jumps over the lazy dog.");
        sentences.add("The five boxing wizards jump quickly.");
        sentences.add("Pack my box with five dozen liquor jugs.");
        sentences.add("Mr. Jock, TV quiz PhD, bags few lynx.");
        sentences.add("How quickly daft jumping zebras vex.");
        sentences.add("Waltz, bad nymph, for quick jigs vex.");
        sentences.add("Jazzed phantoms blew my crux vow.");
        sentences.add("Five quacking zephyrs jolt my wax bed.");
        sentences.add("Blowzy night-frumps vex'd Jack Q.");
        sentences.add("Big fjords vex quick waltz nymph.");

        TypingSpeedTest typingTest = new TypingSpeedTest(sentences);
        typingTest.runTypingSpeedTest();
    }
}
