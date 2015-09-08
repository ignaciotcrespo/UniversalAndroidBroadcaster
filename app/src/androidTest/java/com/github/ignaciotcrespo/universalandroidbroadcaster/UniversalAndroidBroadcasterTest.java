package com.github.ignaciotcrespo.universalandroidbroadcaster;

import com.github.ignaciotcrespo.universalandroidbroadcaster.testutils.ContextForTest;

import org.frutilla.annotations.Frutilla;
import org.junit.Before;
import org.junit.Test;

import static com.github.ignaciotcrespo.universalandroidbroadcaster.UniversalAndroidBroadcaster.broadcastObject;
import static com.github.ignaciotcrespo.universalandroidbroadcaster.UniversalAndroidBroadcaster.initialize;

/**
 * Test for {@link UniversalAndroidBroadcaster}
 */
public class UniversalAndroidBroadcasterTest {

    // --- begin: mock data ------------------------------------------------------------------------
    public static final Object OBJECT = new Object();
    public static final String OBJECT_STRING = "mock object";
    public static final String TYPE = "mock type";
    public static final String CONTENT_TYPE = "mock content type";
    public static final String ACTION = "com.github.ignaciotcrespo.universalbroadcaster.ADD_OBJECT";
    public static final String KEY_OBJECT = "object";
    public static final String KEY_TYPE = "type";
    public static final String KEY_CONTENT_TYPE = "content-type";
    // --- end: mock data --------------------------------------------------------------------------

    private MockGroup mMock;

    @Before
    public void setUp() throws Exception {
        mMock = new MockGroup();
    }

    // --- BEGIN TESTS -----------------------------------------------------------------------------

    @Frutilla(
            Given = "broadcaster not initialized",
            When = "I broadcast an object",
            Then = "the object is NOT broadcasted"
    )
    @Test
    public void testBroadcastWithNoInitialization() throws Exception {

        broadcastObject(OBJECT_STRING, TYPE, CONTENT_TYPE);

        mMock.mContext.assertNoBroadcast();
    }

    // ---------------------------------------------------------------------------------------------

    @Frutilla(
            Given = "broadcaster initialized",
            When = "I broadcast an object",
            Then = "the object is successfully broadcasted"
    )
    @Test
    public void testBroadcastObjectInitialized() throws Exception {
        initialize(mMock.mContext);

        broadcastObject(OBJECT_STRING, TYPE, CONTENT_TYPE);

        assertBroadcasted(OBJECT_STRING, TYPE, CONTENT_TYPE);
    }

    // ---------------------------------------------------------------------------------------------

    @Frutilla(
            Given = "broadcaster NOT initialized",
            When = "I broadcast an object using a context",
            Then = "the object is successfully broadcasted"
    )
    @Test
    public void testBroadcastObjectWithContext() throws Exception {
        broadcastObject(mMock.mContext, OBJECT_STRING, TYPE, CONTENT_TYPE);

        assertBroadcasted(OBJECT_STRING, TYPE, CONTENT_TYPE);
    }

    // ---------------------------------------------------------------------------------------------

    @Frutilla(
            Given = "broadcaster initialized",
            When = "I broadcast an object with NO type",
            Then = "the object is successfully broadcasted"
    )
    @Test
    public void testBroadcastObjectNoType() throws Exception {
        initialize(mMock.mContext);

        broadcastObject(OBJECT_STRING, null, CONTENT_TYPE);

        assertBroadcastedWithoutType(OBJECT_STRING, CONTENT_TYPE);

        broadcastObject(OBJECT_STRING, "", CONTENT_TYPE);

        assertBroadcastedWithoutType(OBJECT_STRING, CONTENT_TYPE);

    }

    // ---------------------------------------------------------------------------------------------

    @Frutilla(
            Given = "broadcaster initialized",
            When = {
                    "I broadcast an object with NO type",
                    "AND using a context"
            },
            Then = "the object is successfully broadcasted"
    )
    @Test
    public void testBroadcastObjectNoTypeWithCOntext() throws Exception {
        broadcastObject(mMock.mContext, OBJECT_STRING, null, CONTENT_TYPE);

        assertBroadcastedWithoutType(OBJECT_STRING, CONTENT_TYPE);

        broadcastObject(OBJECT_STRING, "", CONTENT_TYPE);

        assertBroadcastedWithoutType(OBJECT_STRING, CONTENT_TYPE);

    }

    // ---------------------------------------------------------------------------------------------

    @Frutilla(
            Given = "broadcaster initialized",
            When = "I broadcast an object with NO content type",
            Then = "the object is successfully broadcasted"
    )
    @Test
    public void testBroadcastObjectNoContentType() throws Exception {
        initialize(mMock.mContext);

        broadcastObject(OBJECT_STRING, TYPE, null);

        assertBroadcastedWithoutContentType(OBJECT_STRING, TYPE);

        broadcastObject(OBJECT_STRING, TYPE, "");

        assertBroadcastedWithoutContentType(OBJECT_STRING, TYPE);

    }

    // ---------------------------------------------------------------------------------------------

    @Frutilla(
            Given = "broadcaster initialized",
            When = {
                    "I broadcast an object with NO content type",
                    "AND using a context"
            },
            Then = "the object is successfully broadcasted"
    )
    @Test
    public void testBroadcastObjectNoContentTypeWithContext() throws Exception {
        broadcastObject(mMock.mContext, OBJECT_STRING, TYPE, null);

        assertBroadcastedWithoutContentType(OBJECT_STRING, TYPE);

        broadcastObject(OBJECT_STRING, TYPE, "");

        assertBroadcastedWithoutContentType(OBJECT_STRING, TYPE);

    }

    // ---------------------------------------------------------------------------------------------

    @Frutilla(
            Given = "broadcaster initialized",
            When = "I broadcast an object",
            Then = "the object is successfully broadcasted as string"
    )
    @Test
    public void testBroadcastObjectToString() throws Exception {
        initialize(mMock.mContext);

        broadcastObject(OBJECT, TYPE, CONTENT_TYPE);

        assertBroadcasted(OBJECT.toString(), TYPE, CONTENT_TYPE);
    }

    // ---------------------------------------------------------------------------------------------

    @Frutilla(
            Given = "broadcaster initialized",
            When = "I broadcast an object using a context",
            Then = "the object is successfully broadcasted as string"
    )
    @Test
    public void testBroadcastObjectToStringWithContext() throws Exception {
        broadcastObject(mMock.mContext, OBJECT, TYPE, CONTENT_TYPE);

        assertBroadcasted(OBJECT.toString(), TYPE, CONTENT_TYPE);
    }

    // --- END TESTS -------------------------------------------------------------------------------

    private void assertBroadcasted(String objectString, String type, String contentType) {
        mMock.mContext.assertBroadcast(ACTION, KEY_OBJECT, objectString);
        mMock.mContext.assertBroadcast(ACTION, KEY_TYPE, type);
        mMock.mContext.assertBroadcast(ACTION, KEY_CONTENT_TYPE, contentType);
    }

    private void assertBroadcastedWithoutType(String objectString, String contentType) {
        mMock.mContext.assertBroadcast(ACTION, KEY_OBJECT, objectString);
        mMock.mContext.assertBroadcastWithoutExtra(ACTION, KEY_TYPE);
        mMock.mContext.assertBroadcast(ACTION, KEY_CONTENT_TYPE, contentType);
    }

    private void assertBroadcastedWithoutContentType(String objectString, String type) {
        mMock.mContext.assertBroadcast(ACTION, KEY_OBJECT, objectString);
        mMock.mContext.assertBroadcast(ACTION, KEY_TYPE, type);
        mMock.mContext.assertBroadcastWithoutExtra(ACTION, KEY_CONTENT_TYPE);
    }

    private static class MockGroup {
        ContextForTest mContext;

        MockGroup() {
            mContext = new ContextForTest();
        }
    }

}