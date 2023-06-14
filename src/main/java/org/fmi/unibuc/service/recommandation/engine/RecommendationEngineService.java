package org.fmi.unibuc.service.recommandation.engine;

import org.fmi.unibuc.domain.*;
import org.fmi.unibuc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class RecommendationEngineService {

    public static final String ENGLISH = "ENGLISH";

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private SimilarityRepository similarityRepository;

    private Map<String, Integer> wordFreqMap;

    public void recalculateSimilarities() {

        wordFreqMap = new HashMap<>();
        List<Word> wordList = wordRepository.findAll();
        for (Word word : wordList) {
            wordFreqMap.putIfAbsent(word.getValue(), word.getFrequency());
        }

        Map<Course, List<String>> data = prepareData();

        similarityRepository.deleteAll();

        calculateSimilarities(data);

    }

    private int countOccurrences(List<String> list, String item) {
        int counter = 0;
        for (int i = 0; i < list.size(); i++) {
            String currentData = list.get(i).toLowerCase();
            double similarity = similarity(currentData, item);
            if (similarity >= 0.75) {
                counter++;
            }
        }
        return counter;
    }

    private void calculateSimilarities(Map<Course, List<String>> data) {
        for (Course courseA : data.keySet()) {
            for (Course courseB : data.keySet()) {

                if (courseA.getId() >= courseB.getId()) {
                    continue;
                }

                Set<String> allWords = new HashSet<>();
                allWords.addAll(data.get(courseA));
                allWords.addAll(data.get(courseB));

                List<Integer> courseAFreqCounter = new ArrayList<>();
                List<Integer> courseBFreqCounter = new ArrayList<>();

                for (String wordValue : allWords) {
                    courseAFreqCounter.add(countOccurrences(
                        data.get(courseA),
                        wordValue));
                }

                for (String wordValue : allWords) {
                    courseBFreqCounter.add(countOccurrences(
                        data.get(courseB),
                        wordValue));
                }

                Similarity similarity = new Similarity();
                similarity.setCourseA(courseA);
                similarity.setCourseB(courseB);
                BigDecimal similarityValue = calculateCosinesSimilarity(courseAFreqCounter, courseBFreqCounter);
                similarity.setValue(similarityValue);
                similarityRepository.save(similarity);

                Similarity similarityTwo = new Similarity();
                similarityTwo.setCourseB(courseA);
                similarityTwo.setCourseA(courseB);
                similarityTwo.setValue(similarityValue);
                similarityRepository.save(similarityTwo);
            }
        }

    }

    private BigDecimal calculateCosinesSimilarity(List<Integer> listA, List<Integer> listB) {
        double result, scalarProduct = 0, normA = 0, normB = 0;
        for (int i = 0; i < listA.size(); i++) {
            scalarProduct += listA.get(i) * listB.get(i);
            normA += listA.get(i) * listA.get(i);
            normB += listB.get(i) * listB.get(i);
        }

        result = scalarProduct / (Math.sqrt(normA) * Math.sqrt(normB));

        return BigDecimal.valueOf(result);
    }

    private Map<Course, List<String>> prepareData() {

        Map<Course, List<String>> courseMap = new HashMap<>();

        List<Course> courseList = courseRepository.findAll();
        for (Course course : courseList) {
            List<String> values = new ArrayList<>();

            //Adding the title
            values.addAll(getTitleNormalized(course));

            //Adding the title of a category
            values.addAll(getCategoryTitleNormalized(course));

            List<String> toFilter = new ArrayList<>();

            //Adding the description of course
            toFilter.addAll(getDescriptionNormalized(course));

            //Adding the title of all chapters
            toFilter.addAll(getChapterTitlesNormalized(course));

            //Adding the title of all chapters
            toFilter.addAll(getChapterDescriptionsNormalized(course));

            //Adding the title of all lessons
            toFilter.addAll(getLessonsTitlesNormalized(course));

            // Filter and keep only the most important 20 words
            List<String> filteredValues = toFilter.stream().map(p -> {
                Word word = new Word();
                word.setValue(p);
                word.setFrequency(wordFreqMap.getOrDefault(p, 1));
                return word;
            }).sorted(Comparator.comparingInt(Word::getFrequency))
                .limit(30).map(Word::getValue).collect(Collectors.toList());

            values.addAll(filteredValues);

            courseMap.put(course, values);
        }

        return courseMap;
    }

    private List<String> getTitleNormalized(Course course) {
        return Arrays.stream(course.getTitle().split("[ !\\\"\\\\#$%&'()*+,-./:;<=>?@\\\\[\\\\]^_`{|}~]+"))
            .map(String::toLowerCase)
            .distinct()
            .collect(Collectors.toList());
    }

    private List<String> getDescriptionNormalized(Course course) {
        return Arrays.stream(course.getDescription().split("[ !\\\"\\\\#$%&'()*+,-./:;<=>?@\\\\[\\\\]^_`{|}~]+"))
            .map(String::toLowerCase)
            .distinct()
            .collect(Collectors.toList());
    }

    private List<String> getChapterTitlesNormalized(Course course) {
        List<String> results = new ArrayList<>();
        for(Chapter chapter : chapterRepository.findAllByCourseOrderByOrderNrAsc(course)) {
            List<String> oneChapterData = Arrays.stream(chapter.getTitle().split("[ !\\\"\\\\#$%&'()*+,-./:;<=>?@\\\\[\\\\]^_`{|}~]+"))
                .map(String::toLowerCase)
                .distinct()
                .collect(Collectors.toList());
            results.addAll(oneChapterData);
        }
        return results;
    }

    private List<String> getChapterDescriptionsNormalized(Course course) {
        List<String> results = new ArrayList<>();
        for(Chapter chapter : chapterRepository.findAllByCourseOrderByOrderNrAsc(course)) {
            List<String> oneChapterData = Arrays.stream(chapter.getDescription().split("[ !\\\"\\\\#$%&'()*+,-./:;<=>?@\\\\[\\\\]^_`{|}~]+"))
                .map(String::toLowerCase)
                .distinct()
                .collect(Collectors.toList());
            results.addAll(oneChapterData);
        }
        return results;
    }

    private List<String> getLessonsTitlesNormalized(Course course) {
        List<String> results = new ArrayList<>();
        for(Chapter chapter : chapterRepository.findAllByCourseOrderByOrderNrAsc(course)) {
            for(Lesson lesson : lessonRepository.findAllByChapterOrderByOrderAsc(chapter)) {
                List<String> oneChapterData = Arrays.stream(lesson.getTitle().split("[ !\\\"\\\\#$%&'()*+,-./:;<=>?@\\\\[\\\\]^_`{|}~]+"))
                    .map(String::toLowerCase)
                    .distinct()
                    .collect(Collectors.toList());
                results.addAll(oneChapterData);
            }
        }
        return results;
    }

    private List<String> getCategoryTitleNormalized(Course course) {
        return Arrays.stream(course.getCategory().getTitle().split("[ !\\\"\\\\#$%&'()*+,-./:;<=>?@\\\\[\\\\]^_`{|}~]+"))
            .map(String::toLowerCase)
            .distinct()
            .collect(Collectors.toList());
    }

    private double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2; shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /* both strings are zero length */ }
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

    }

    public int editDistance(String s1, String s2) {
        if(s1 == null || s2 == null) {
            return 0;
        }

        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }



}
