package org.apache.bookkeeper.bookie;

import org.apache.bookkeeper.conf.ServerConfiguration;
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
            System.out.println("Expected exception " + e.getMessage());
        }
        assertEquals("0", Cookie.encodeDirPaths(new String[0]));
    }

    @Test
    public void testGetLedgerDirPathsFromCookie() {
        Cookie.Builder cookieBuilder = Cookie.newBuilder();
        cookieBuilder.setLedgerDirs("2\tdir1\tdir2");
        Cookie cookie1 = cookieBuilder.build();
        String[] dirs = cookie1.getLedgerDirPathsFromCookie();
        assertArrayEquals(new String[] {"dir1", "dir2"} , dirs);

        try {
            cookieBuilder = Cookie.newBuilder();
            Cookie cookie2 = cookieBuilder.build();
            cookie2.getLedgerDirPathsFromCookie();
            fail("Not raising exception");
        } catch (Exception e) {
            System.out.println("Expected exception " + e.getMessage());
        }

        cookieBuilder.setLedgerDirs("");
        Cookie cookie3 = cookieBuilder.build();
        dirs = cookie3.getLedgerDirPathsFromCookie();
        assertEquals("", dirs[0]);
    }
}
