package com.example.myapplication_java;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test1(){
        String a="人们";
        String b="人";
        String[] c=a.split("");
        Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]");
        for (int i = 0; i < c.length; i++) {
            Matcher matcher=pattern.matcher(c[i]);
            System.out.println(matcher.matches());
        }
        System.out.println(a.length());
        System.out.println(Pattern.matches(" [\\u4e00-\\u9fa5]","人"));
    }


    @Test
    public void test2(){
        Integer num1=100;
        Integer num2=200;
        System.out.println(num1!=num2);

    }

}