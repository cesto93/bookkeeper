package org.apache.bookkeeper.bookie;

import org.apache.bookkeeper.conf.ServerConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CookieTest {

    private Cookie.Builder builder;

    @Before
    public void setBuilder () {
        builder = Cookie.newBuilder();
    }

    @Test
    public void testEncodeDirPaths() {
        String[] dirs = {"dir1", "dir2"};
        assertEquals("2\tdir1\tdir2", Cookie.encodeDirPaths(dirs));
        try {
            Cookie.encodeDirPaths(null);
            fail("Not raising exception");
        } catch (Exception e) {
            System.out.println("Expected exception: " + e.getMessage());
        }
        assertEquals("0", Cookie.encodeDirPaths(new String[0]));
    }

    @Test
    public void testGetLedgerDirPathsFromCookie() {
        builder.setLedgerDirs("2\tdir1\tdir2");
        Cookie cookie1 = builder.build();
        String[] dirs = cookie1.getLedgerDirPathsFromCookie();
        assertArrayEquals(new String[] {"dir1", "dir2"} , dirs);

        try {
            builder = Cookie.newBuilder();
            Cookie cookie2 = builder.build();
            cookie2.getLedgerDirPathsFromCookie();
            fail("Not raising exception");
        } catch (Exception e) {
            System.out.println("Expected exception: " + e.getMessage());
        }

        builder.setLedgerDirs("");
        Cookie cookie3 = builder.build();
        dirs = cookie3.getLedgerDirPathsFromCookie();
        assertEquals("", dirs[0]);
    }
}
