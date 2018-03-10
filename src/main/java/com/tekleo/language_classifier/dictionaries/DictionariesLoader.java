package com.tekleo.language_classifier.dictionaries;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Special async loader to load all existing dictionaries in parallel
 * We use {@link ExecutorService} to achieve multithreading in this task
 * @author Leo Ertuna
 * @since 08.03.2018 22:08
 */
public class DictionariesLoader {
    /**
     * File names of dictionaries
     */
    private static final String[] FILES = new String[]{
            "Czech.dic",
//            "English.dic",
//            "German.dic",
//            "Russian.dic",
//            "Turkish.dic",
//            "Ukrainian.dic"
    };

    /**
     * Languages of dictionaries
     */
    private static final Language[] LANGUAGES = new Language[]{
            Language.CZECH,
//            Language.ENGLISH,
//            Language.GERMAN,
//            Language.RUSSIAN,
//            Language.TURKISH,
//            Language.UKRAINIAN
    };

    /**
     * Public loader, returns the list of dictionaries
     * @return all loaded dictionaries
     */
    public static List<Dictionary> load() {
        try {
            // Create new list
            System.out.println("Started to load all dictionaries");
            List<Dictionary> dictionaries = new ArrayList<>(FILES.length);

            // Create executor service
            ExecutorService service = Executors.newFixedThreadPool(FILES.length);

            // Create tasks
            List<Callable<Dictionary>> tasks = getLoadCallables();

            // Execute tasks
            List<Future<Dictionary>> answers = service.invokeAll(tasks);

            // Collect results from futures
            for (Future<Dictionary> future : answers)
                dictionaries.add(future.get());

            // Return
            System.out.println("Finished loading all dictionaries");
            return dictionaries;
        }

        catch (Exception e) {
            // Rethrow exception
            throw new RuntimeException("Unable to load dictionaries, due to ", e);
        }

    }

    /**
     * Private helper to create all loader tasks
     * @return callables for each dictionary
     */
    private static List<Callable<Dictionary>> getLoadCallables() {
        List<Callable<Dictionary>> todo = new ArrayList<>(FILES.length);
        for (int i = 0; i < FILES.length; i++)
            todo.add(new LoadCallable(FILES[i], LANGUAGES[i]));
        return todo;
    }

    /**
     * The callable class which loads the dictionary based on its filename and language
     */
    private static class LoadCallable implements Callable<Dictionary> {
        private String filepath;
        private Language language;

        public LoadCallable(String filepath, Language language) {
            this.filepath = filepath;
            this.language = language;
        }

        @Override
        public Dictionary call() throws Exception {
            System.out.println("Stared to load dictionary " + filepath);
            Dictionary dictionary = new Dictionary(filepath, language);
            System.out.println("Finished loading dictionary " + filepath);
            return dictionary;
        }
    }
}