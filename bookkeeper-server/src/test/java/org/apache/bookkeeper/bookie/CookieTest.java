package org.apache.bookkeeper.bookie;

import org.junit.Test;

import static org.junit.Assert.*;

public class CookieTest {

    @Test
    public void testEncodeDirPaths() {
        String[] dirs = {"dir1", "dir2"};
        assertEquals("2\tdir1\tdir2", Cookie.encodeDirPaths(dirs));
        try {
            Cookie.encodeDirPaths(null);
            fail("Not raising exception");
        } catch (Exception e) {

        }
        assertEquals("0", Cookie.encodeDirPaths(new String[0]));
    }
}
