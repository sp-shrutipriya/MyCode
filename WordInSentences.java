/*

Problem Statement:

Write a program the reads in a text file and for each word that is present in the file, writes out the sentence numbers that that word occurred in.
So if the word "jump" occurs in sentences 2 and 10, print out:
foo: 1, 3
jump: 2, 10
bar: 3, 19
Please note we'd like the *sentence* number not line number. Your code should be able to handle abbreviations with embedded points like i.e. or Dr. or Mr. or e.g.

*/

// JAVA program to print all non case-sensitive words and the sentences in which they occur
// The program takes into account {.?!} as end of statements and does not handle abbreviations with embedded points like i.e. or Dr. or Mr. or e.g.

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordInSentences {

    public static String readFileAsString(String fileName)throws Exception
    {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    // Find all the words without case-sensitivity and the sentences in which they occur
    public static Map<String, List<Integer>> findWordsInSentences(String str) {

        // Finding individual sentences
        String[] sentences = str.split("[.?!]+");

        // Final map of words in sentences
        Map<String, List<Integer>> mapWordsSentences = new HashMap<>();

        for (int i = 0; i < sentences.length; i++) {

            // Individual words in each sentence
            String[] words = sentences[i].split(" ");

            for (int j = 0; j < words.length; j++) {

                // Assuming that we need to find the words in sentences without taking case-sensitivity in consideration
                // If we need to find case-sensitive words in sentences, just don't convert the word to lower case
                String word = words[j].toLowerCase();

                // List of sentences for a particular word
                List<Integer> sentenceNumbers = new ArrayList<>();

                if (mapWordsSentences.containsKey(word)) {

                    sentenceNumbers = mapWordsSentences.get(word);

                    if (!sentenceNumbers.contains(i)) {

                        sentenceNumbers.add(i);
                        mapWordsSentences.put(word, sentenceNumbers);
                    }

                } else {

                    sentenceNumbers.add(i);
                    mapWordsSentences.put(word, sentenceNumbers);
                }
            }
        }

        return mapWordsSentences;
    }

    // Print in the desired format
    public static void printWordsInSentences(Map<String, List<Integer>> map) {

        for (String word : map.keySet()) {

            if (word.length() > 0) {
                System.out.print(word + " : ");

                int noOfSentences = map.get(word).size();

                for (int i = 0; i < noOfSentences; i++) {

                    if (i == noOfSentences - 1) {
                        System.out.print(map.get(word).get(i) + 1);
                    } else {
                        System.out.print((map.get(word).get(i) + 1) + ", ");
                    }
                }

                System.out.println();
            }
        }
    }

    // Driver program
    public static void main(String[] args) throws Exception {

        String str = readFileAsString("C:\\...\\Input.txt");

        Map<String, List<Integer>> wordsInSentencesMap = findWordsInSentences(str);

        printWordsInSentences(wordsInSentencesMap);
    }
}

/*

Input text file contents:
I am a Girl I am a. Is Shruti solving? solving i is a nice Solving thing! I The Thing we do should be the thing we like.

Output: case-sensitive
a : 1, 3
solving : 2, 3
be : 4
Solving : 3
like : 4
I : 1, 4
i : 3
Is : 2
is : 3
do : 4
am : 1
nice : 3
we : 4
The : 4
the : 4
should : 4
Shruti : 2
Girl : 1
thing : 3, 4
Thing : 4

Output: without case-sensitive
a : 1, 3
shruti : 2
solving : 2, 3
be : 4
like : 4
i : 1, 3, 4
is : 2, 3
do : 4
am : 1
girl : 1
nice : 3
we : 4
the : 4
should : 4
thing : 3, 4

*/
