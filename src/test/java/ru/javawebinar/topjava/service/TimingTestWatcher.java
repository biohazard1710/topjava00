package ru.javawebinar.topjava.service;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TimingTestWatcher extends TestWatcher {

    private static final Logger logger = LoggerFactory.getLogger(TimingTestWatcher.class);

    private long starTime;
    private long duration;
    private static final List<TestResult> results = new ArrayList<>();

    @Override
    protected void starting(Description description) {
        logger.info("Starting test: {}", description.getMethodName());
        starTime = System.currentTimeMillis();
    }

    @Override
    protected void finished(Description description) {
        duration = System.currentTimeMillis() - starTime;
        results.add(new TestResult(description.getMethodName(), duration));
        logger.info("Finished test: {} - took {} ms", description.getMethodName(), duration);
    }

    public static List<TestResult> getResults() {
        return results;
    }

    public static class TestResult {
        private final String testName;
        private final long duration;

        public TestResult(String testName, long duration) {
            this.testName = testName;
            this.duration = duration;
        }

        public String getResultTestName() {
            return testName;
        }

        public long getResultDuration() {
            return duration;
        }
    }
}
