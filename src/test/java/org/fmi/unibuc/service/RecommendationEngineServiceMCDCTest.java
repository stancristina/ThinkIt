package org.fmi.unibuc.service;

import org.fmi.unibuc.repository.*;
import org.fmi.unibuc.service.recommandation.engine.RecommendationEngineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RecommendationEngineServiceMCDCTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private WordRepository wordRepository;

    @Mock
    private SimilarityRepository similarityRepository;

    @InjectMocks
    private RecommendationEngineService recommendationEngineService;

    @BeforeEach
    public void setRecommendationEngineService() {
        MockitoAnnotations.initMocks(this);
    }

    /**
    * Tests decision on line 238, having C1 true and C2 false
    **/
    @Test
    public void test238C1TrueC2False() {
        String s1 = null;
        String s2 = "abcd";

        int res = recommendationEngineService.editDistance(s1, s2);

        Assertions.assertEquals(0, res);
    }

    /**
     * Tests decision on line 238, having C1 false and C2 true
     **/
    @Test
    public void test238C1FalseC2True() {
        String s1 = "abcd";
        String s2 = null;

        int res = recommendationEngineService.editDistance(s1, s2);

        Assertions.assertEquals(0, res);
    }

    /**
     * Test decision on line 238, having C1 false and C2 false.
     * By picking s1 with more than 1 character, the decision at line 246 will be fully covered.
     * By picking s2 with more than 1 character, the decision at line 248 will be fully covered.
     * By picking s1 with more than 1 character, the decision at line 249 will be true for the first i iteration and
     *      false for the second one.
     * By picking s2 with more than 1 character, the decision at line 252 will be false for the first j iteration and
     *      true for the rest of iterations.
     * By picking s1 and s2 with one identical letter on a position greater than 0 and one different on a position
     *      greater than 0, the decision at line 254 will be fully covered.
     * By picking s1 with more than 1 character, the decision at line 262 will be false for the first iteration and
     *      true for the rest of iterations.
     */
    @Test
    public void testEditDistanceWithNonNullInputs() {
        String s1 = "dummyString1";
        String s2 = "dunnyString2";

        int res = recommendationEngineService.editDistance(s1, s2);

        Assertions.assertEquals(3, res);
    }

}
