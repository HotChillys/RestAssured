package com.cydeo.tests.day08_hamcrest;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class HamcrestMatchersIntro {

    @Test
    public void numbersTest(){

        assertThat(1+3, is(4));
        assertThat(5+5, equalTo(10));
        assertThat(55, is(equalTo(10)));

        assertThat(100+4, is(greaterThan(99)));
        //assertTrue(100+4 > 99); Junit

        int num = 10+2;
        assertThat(num, is(not(10)));
        assertThat(num, is(not(equalTo(9))));

    }

    @Test
    public void stringTest(){

        String word = "wooden spoon";
        assertThat(word, is("wooden spoon"));
        assertThat(word, equalTo("wooden spoon"));
        assertThat(word, startsWith("wood"));
        assertThat(word, startsWithIgnoringCase("WOODe"));
        assertThat("fail or pass message",word, endsWith("spoon"));
        assertThat(word, containsString("den"));
        assertThat(word, containsStringIgnoringCase("sPOOn"));
        String str = " ";
        assertThat(str, is(blankString()));
        assertThat(str.replace(" ", ""), is(emptyOrNullString()));
        assertThat(str.trim(), is(emptyOrNullString()));

    }

    @Test
    public void listTest(){

        List<Integer> nums = Arrays.asList(5, 20, 1, 54, 0);
        List<String> words = Arrays.asList("java", "selenium", "git");

        assertThat(nums, hasSize(5));
        assertThat(words, hasSize(3));
        assertThat(nums, hasItem(5));
        assertThat(words, hasItem("git"));
        assertThat(nums, containsInAnyOrder(54, 1, 0, 20, 5));
        assertThat(nums, everyItem(greaterThanOrEqualTo(0)));
        assertThat(words, everyItem(not(blankString())));

    }

}
