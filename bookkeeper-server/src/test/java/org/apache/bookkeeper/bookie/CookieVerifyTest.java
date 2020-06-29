package org.apache.bookkeeper.bookie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class CookieVerifyTest extends CookieTestUtils {
    private Cookie cookie1;
    private Cookie cookie2;
    private boolean expectedTestFail;

    public CookieVerifyTest(Cookie cookie1, Cookie cookie2, boolean expectedTestFail) {
        this.cookie1 = cookie1;
        this.cookie2 = cookie2;
        this.expectedTestFail = expectedTestFail;
    }

    @Parameters
    public static Collection verifyParam() {
        final String valid_host = "192.168.1.1:80";
        final String id1 = "id";
        final String id2 = "id2";
        Cookie[] cookies = {
                getCookie(2, null, null, null, null),
                getCookie(3, "", "", "", ""),
                getCookie(3, valid_host, "2\tdir1\tdir2", "2\tdir1\tdir2", id1)
        };

        return Arrays.asList(new Object[][] {
                {cookies[0], cookies[0], true},
                {cookies[0], cookies[2], true},
                {cookies[1], cookies[1], false},
                {cookies[1], cookies[2], true},
                {cookies[2], cookies[2], false},
                {cookies[2], cookies[1], true},

                //added after coverage analysis
                {cookies[1], cookies[0], true},
                {getCookie(3, valid_host, "2\tdir1\tdir2", "2\tdir1\tdir2", id1),
                getCookie(3, valid_host, "2\tdir1\tdir2", "2\tdir1\tdir2", id2), true},

                //added after mutation analysis
                {getCookie(3, valid_host, "2\tdir1\tdir2", "2\tdir1\tdir2", id1),
                getCookie(3, valid_host, "2\tdir1\tdir2", "2\tdir1\tdir3", id1), true},

                {getCookie(3, valid_host, "2\tdir1\tdir2", "2\tdir1\tdir2", id1),
                getCookie(2, valid_host,"2\tdir1\tdir2","2\tdir1\tdir3", id2), true},

                {getCookie(2, valid_host, "2\tdir1\tdir2", "2\tdir1\tdir3", id2),
                getCookie(2, valid_host,"2\tdir1\tdir2","2\tdir1\tdir3", id2), true}
        });
    }

    @Test
    public void TestVerify() {
        boolean testFail = false;
        try {
            cookie1.verify(cookie2);
        } catch (Exception e) {
            System.out.println("Expected exception: " + e.getMessage());
            testFail = true;
        }
        assertEquals(expectedTestFail, testFail);
    }
}
