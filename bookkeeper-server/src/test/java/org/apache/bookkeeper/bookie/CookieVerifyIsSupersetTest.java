package org.apache.bookkeeper.bookie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CookieVerifyIsSupersetTest extends CookieTestUtils {
    private Cookie cookie1;
    private Cookie cookie2;
    private boolean expectedTestFail;

    public CookieVerifyIsSupersetTest(Cookie cookie1, Cookie cookie2, boolean expectedTestFail) {
        this.cookie1 = cookie1;
        this.cookie2 = cookie2;
        this.expectedTestFail = expectedTestFail;
    }

    @Parameters
    public static Collection verifySupersetParam() {
        Cookie[] cookies1 = {
                getCookie(2, null, null, null, null),
                getCookie(3, "", "", "", ""),
                getCookie(3, "host", "2\tdir1\tdir2", "2\tdir1\tdir2",
                        "id")
        };

        return Arrays.asList(new Object[][] {
                {   cookies1[0], cookies1[0], true},
                {   cookies1[0], cookies1[2], true},
                {   cookies1[1], cookies1[1], false},
                {   cookies1[1], cookies1[2], true},
                {   cookies1[2], cookies1[2], false},
                {   cookies1[2], cookies1[1], true},
                {   cookies1[2],
                        getCookie(3, "host", "2\tdir1\tdir2", "1\tdir2", "id"),
                        false},
                {   cookies1[2],
                        getCookie(3, "host", "2\tdir1\tdir2", "3\tdir1\tdir2\tdir3",
                                "id"),
                        true}
        });
    }

    @Test
    public void TestVerifyIsSuperSet() {
        boolean testFail = false;
        try {
            cookie1.verifyIsSuperSet(cookie2);
        } catch (Exception e) {
            System.out.println("Expected exception: " + e.getMessage());
            testFail = true;
        }
        assertEquals(expectedTestFail, testFail);
    }
}
