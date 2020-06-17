package org.apache.bookkeeper.bookie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CookieTestVerify extends CookieTestUtils {
    private Cookie cookie1;
    private Cookie cookie2;
    private boolean expectedTestFail;

    public CookieTestVerify(Cookie cookie1, Cookie cookie2, boolean expectedTestFail) {
        this.cookie1 = cookie1;
        this.cookie2 = cookie2;
        this.expectedTestFail = expectedTestFail;
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {

        return Arrays.asList(new Object[][] {
            {   getCookie(2, null, null, null, null),
                getCookie(2, null, null, null, null),
                true},
            {   getCookie(3, "", "", "", ""),
                getCookie(4, "host", "2\tdir1\tdir2", "2\tdir1\tdir2", "id"),
                true},
            {   getCookie(3, "host", "2\tdir1\tdir2", "2\tdir1\tdir2", "id"),
                getCookie(3, "host", "2\tdir1\tdir2", "2\tdir1\tdir2", "id"),
                false},
            {   getCookie(3, "host", "2\tdir1\tdir2", "2\tdir1\tdir2", "id"),
                getCookie(3, "host", "1\tdir1", "1\tdir2", "id"),
                true},
            {   getCookie(3, "host", "1\tdir1", "1\tdir2", "id"),
                getCookie(3, "host", "2\tdir1\tdir2", "2\tdir1\tdir2", "id"),
                true}
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

    @Test
    public void TestVerifyIsSuperSet() {
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
