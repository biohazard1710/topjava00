package ru.javawebinar.topjava.service;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TimingTestWatcher extends TestWatcher {

    private static final Logger logger = LoggerFactory.getLogger(TimingTestWatcher.class);

    private long startTime;
    private long duration;
    private String testName;
    private static final List<TestResult> results = new ArrayList<>();

    @Override
    protected void starting(Description description) {
        testName = description.getMethodName();
        logger.info("Starting test: {}", testName);
        startTime = System.currentTimeMillis();
    }

    @Override
    protected void finished(Description description) {
        duration = System.currentTimeMillis() - startTime;
        results.add(new TestResult(testName, duration));
        logger.info("Finished test: {} - took {} ms", testName, duration);
    }

    public static List<TestResult> getResults() {
        return results;
    }

    public static class TestResult {
        private String testName;
        private long duration;

        public TestResult(String testName, long duration) {
            this.testName = testName;
            this.duration = duration;
        }

        public String getTestName() {
            return testName;
        }

        public long getDuration() {
            return duration;
        }
    }
}