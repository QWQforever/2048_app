package com.example.myapplication_java;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.myapplication_java", appContext.getPackageName());
    }

    @Test
    public void test1(){
        RankDao rankDao =new RankDao();
        List<Rank> rank_list = rankDao.get_rank_list();
        for (int i = 0; i < rank_list.size(); i++) {
            System.out.println(rank_list.get(i));
        }
        System.out.println("111111");
    }
}