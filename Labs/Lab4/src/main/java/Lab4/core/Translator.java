package Lab4.core;

import Lab4.exceptions.FileReadException;
import Lab4.exceptions.InvalidFileFormatException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Translator {
    private final Map<String, String> dictionary;
    private List<String> sortedKeys;

    public Translator() {
        this.dictionary = new HashMap<>();
    }

    public void loadDictionary(String filePath) throws FileReadException, InvalidFileFormatException {
        dictionary.clear();

        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                throw new FileReadException("Dictionary file does not exist: " + filePath);
            }

            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) continue;

                if (!line.contains("|")) {
                    throw new InvalidFileFormatException(
                            "Invalid format at line " + (i + 1) + ": missing '|' separator"
                    );
                }

                String[] parts = line.split("\\|", 2);
                if (parts.length != 2) {
                    throw new InvalidFileFormatException(
                            "Invalid format at line " + (i + 1) + ": expected exactly one '|' separator"
                    );
                }

                String word = parts[0].trim().toLowerCase();
                String translation = parts[1].trim();

                if (word.isEmpty()) {
                    throw new InvalidFileFormatException("Empty word at line " + (i + 1));
                }

                if (translation.isEmpty()) {
                    throw new InvalidFileFormatException("Empty translation at line " + (i + 1));
                }

                dictionary.put(word, translation);
            }

            if (dictionary.isEmpty()) {
                throw new InvalidFileFormatException("Dictionary file is empty");
            }

            sortedKeys = dictionary.keySet().stream()
                    .sorted((k1, k2) -> {
                        int wordCount1 = k1.split("\\s+").length;
                        int wordCount2 = k2.split("\\s+").length;
                        if (wordCount2 != wordCount1) {
                            return Integer.compare(wordCount2, wordCount1);
                        }
                        return Integer.compare(k2.length(), k1.length());
                    })
                    .collect(Collectors.toList());

            System.out.println("Dictionary loaded: " + dictionary.size() + " entries");

        } catch (IOException e) {
            throw new FileReadException("Cannot read dictionary file: " + e.getMessage(), e);
        }
    }

    public String translateFile(String filePath) throws FileReadException {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                throw new FileReadException("Input file does not exist: " + filePath);
            }

            String content = Files.readString(path, StandardCharsets.UTF_8);
            return translateText(content);
        } catch (IOException e) {
            throw new FileReadException("Cannot read input file: " + e.getMessage(), e);
        }
    }

    public String translateText(String text) {
        if (dictionary.isEmpty()) {
            return text;
        }

        StringBuilder result = new StringBuilder();
        String[] words = text.split("\\s+");
        int i = 0;

        while (i < words.length) {
            if (i > 0) {
                result.append(" ");
            }

            String bestMatch = null;
            String bestTranslation = null;
            int bestWordCount = 0;

            for (String dictionaryPhrase : sortedKeys) {
                int phraseWordCount = dictionaryPhrase.split("\\s+").length;

                if (i + phraseWordCount > words.length) {
                    continue;
                }

                String textPhrase = buildPhrase(words, i, phraseWordCount);

                if (textPhrase.equalsIgnoreCase(dictionaryPhrase)) {
                    if (bestMatch == null || phraseWordCount > bestWordCount) {
                        bestMatch = dictionaryPhrase;
                        bestTranslation = dictionary.get(dictionaryPhrase);
                        bestWordCount = phraseWordCount;
                    }
                }
            }

            if (bestMatch != null) {
                result.append(bestTranslation);
                i += bestWordCount;
            } else {
                result.append(words[i]);
                i++;
            }
        }

        return result.toString();
    }

    private String buildPhrase(String[] words, int startIndex, int wordCount) {
        StringBuilder phrase = new StringBuilder();
        for (int i = startIndex; i < startIndex + wordCount; i++) {
            if (i > startIndex) phrase.append(" ");
            phrase.append(words[i]);
        }
        return phrase.toString();
    }

    public int getDictionarySize() {
        return dictionary.size();
    }

    public boolean isDictionaryLoaded() {
        return !dictionary.isEmpty();
    }
}