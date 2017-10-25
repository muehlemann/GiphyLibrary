package com.example.giphy.helper;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

/**
 * Created by muehlemann on 10/25/17.
 *
 */
public class RxSchedulersOverrideRule implements TestRule {

    private final RxJavaSchedulersHook mRxJavaSchedulersHook = new RxJavaSchedulersHook() {
        @Override
        public Scheduler getIOScheduler() {
            return Schedulers.immediate();
        }

        @Override
        public Scheduler getNewThreadScheduler() {
            return Schedulers.immediate();
        }

        @Override
        public Scheduler getComputationScheduler() {
            return Schedulers.immediate();
        }
    };

    private final RxAndroidSchedulersHook mRxAndroidSchedulersHook = new RxAndroidSchedulersHook() {
        @Override
        public Scheduler getMainThreadScheduler() {
            return Schedulers.immediate();
        }
    };

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    Schedulers.reset();

                    RxAndroidPlugins.getInstance().reset();
                    RxAndroidPlugins.getInstance().registerSchedulersHook(mRxAndroidSchedulersHook);

                    RxJavaPlugins.getInstance().reset();
                    RxJavaPlugins.getInstance().registerSchedulersHook(mRxJavaSchedulersHook);

                    base.evaluate();

                    RxAndroidPlugins.getInstance().reset();
                    RxJavaPlugins.getInstance().reset();
                    Schedulers.reset();
                } catch (IllegalStateException ex) {
                    // no-op
                }
            }
        };
    }
}