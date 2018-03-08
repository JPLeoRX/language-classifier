package com.tekleo.language_classifier;

import com.tekleo.language_classifier.dictionaries.Language;
import com.tekleo.language_classifier.dictionaries.LanguageEvaluator;
import com.tekleo.language_classifier.dictionaries.Word;
import com.tekleo.language_classifier.dictionaries.WordEvaluator;

import java.util.Arrays;

public class ExampleWordPipeline {
    public static void main(String[] args) {
        // Create word when reading a file
        Word word = new Word("hello", Language.ENGLISH);

        // Get NN input-output
        double[] i = word.getPair().getKey();
        double[] o = word.getPair().getValue();
        System.out.println(Arrays.toString(i));
        System.out.println(Arrays.toString(o));

        // Cast back to word
        WordEvaluator wordEvaluator = new WordEvaluator(i);
        LanguageEvaluator languageEvaluator = new LanguageEvaluator(o);
        System.out.println(wordEvaluator.getWord());
        System.out.println(languageEvaluator.getProbabilities());
    }
}
