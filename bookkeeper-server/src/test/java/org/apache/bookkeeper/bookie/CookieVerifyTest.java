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
        Cookie[] cookies1 = {
                getCookie(2, null, null, null, null),
                getCookie(3, "", "", "", ""),
                getCookie(3, "192.168.1.1:80", "2\tdir1\tdir2", "2\tdir1\tdir2",
                        "id")
        };

        return Arrays.asList(new Object[][] {
                {   cookies1[0], cookies1[0], true},
                {   cookies1[0], cookies1[2], true},
                {   cookies1[1], cookies1[1], false},
                {   cookies1[1], cookies1[2], true},
                {   cookies1[2], cookies1[2], false},
                {   cookies1[2], cookies1[1], true},

                //added after coverage analysis
                { cookies1[1], cookies1[0], true},
                { cookies1[2],  getCookie(3, "192.168.1.1:80", "2\tdir1\tdir2",
                        "2\tdir1\tdir2", "id2"), true},

                //added after mutation analysis
                {cookies1[2], getCookie(3, "192.168.1.1:80", "2\tdir1\tdir2",
                        "2\tdir1\tdir3", "id"), true},
                {cookies1[2], getCookie(2, "192.168.1.1:80", "2\tdir1\tdir2",
                        "2\tdir1\tdir3", "id2"), true},
                {
                    getCookie(2, "192.168.1.1:80", "2\tdir1\tdir2",
                        "2\tdir1\tdir3", "id2"),
                        getCookie(2, "192.168.1.1:80", "2\tdir1\tdir2",
                        "2\tdir1\tdir3", "id2"), true}
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
