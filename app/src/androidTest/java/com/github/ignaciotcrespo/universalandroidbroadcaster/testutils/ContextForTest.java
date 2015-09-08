package com.github.ignaciotcrespo.universalandroidbroadcaster.testutils;

import android.content.Intent;
import android.test.mock.MockContext;

import org.junit.Assert;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class ContextForTest extends MockContext {

    private Intent mBroadcasted;

    @Override
    public String getPackageName() {
        return "com.github.ignaciotcrespo.universalandroidbroadcaster";
    }

    @Override
    public void sendBroadcast(Intent intent) {
        mBroadcasted = intent;
    }

    public void assertNoBroadcast() {
        Assert.assertNull("Expected no broadcast, but found " + mBroadcasted, mBroadcasted);
    }

    public void assertBroadcast(String action, String extraKey, Object extraValue) {
        assertEquals(action, mBroadcasted.getAction());
        assertEquals(extraValue, mBroadcasted.getExtras().get(extraKey));
    }

    public void assertBroadcastWithoutExtra(String action, String extraKey) {
        assertEquals(action, mBroadcasted.getAction());
        assertFalse(mBroadcasted.getExtras().containsKey(extraKey));
    }

}
