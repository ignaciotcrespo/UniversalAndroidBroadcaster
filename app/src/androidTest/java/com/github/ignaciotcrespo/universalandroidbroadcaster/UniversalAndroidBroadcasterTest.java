package com.github.ignaciotcrespo.universalandroidbroadcaster;

import com.github.ignaciotcrespo.universalandroidbroadcaster.testutils.ContextForTest;

import junit.framework.TestCase;

import org.frutilla.annotations.Frutilla;

import static com.github.ignaciotcrespo.universalandroidbroadcaster.UniversalAndroidBroadcaster.broadcastObject;
import static com.github.ignaciotcrespo.universalandroidbroadcaster.UniversalAndroidBroadcaster.initialize;

/**
 * Test for {@link UniversalAndroidBroadcaster}
 */
public class UniversalAndroidBroadcasterTest extends TestCase {

    private MockGroup mMock;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMock = new MockGroup();
    }

    // --- BEGIN TESTS -----------------------------------------------------------------------------

    @Frutilla(
            Given = "broadcaster not initialized",
            When = "I broadcast an object",
            Then = "the object is NOT broadcasted"
    )
    public void testBroadcastWithNoInitialization() throws Exception {

        broadcastObject(Mocks.OBJECT, Mocks.TYPE, Mocks.CONTENT_TYPE);

        mMock.mContext.assertNoBroadcast();
    }

    // ---------------------------------------------------------------------------------------------

    @Frutilla(
            Given = "broadcaster initialized",
            When = "I broadcast an object",
            Then = "the object is successfully broadcasted"
    )
    public void testBroadcastObjectInitialized() throws Exception {
        initialize(mMock.mContext);

        broadcastObject(Mocks.OBJECT, Mocks.TYPE, Mocks.CONTENT_TYPE);

        mMock.mContext.assertBroadcast(Mocks.ACTION, Mocks.KEY_OBJECT, Mocks.OBJECT);
    }

    // --- END TESTS -------------------------------------------------------------------------------

    private static class MockGroup {
        ContextForTest mContext;

        MockGroup() {
            mContext = new ContextForTest();
        }
    }

    private static class Mocks {

        public static final String OBJECT = "mock object";
        public static final String TYPE = "mock type";
        public static final String CONTENT_TYPE = "mock content type";
        public static final String ACTION = "com.github.ignaciotcrespo.universalbroadcaster.ADD_OBJECT";
        public static final String KEY_OBJECT = "object";
    }
}