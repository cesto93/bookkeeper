package org.apache.bookkeeper.bookie;

import org.apache.bookkeeper.conf.ServerConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CookieTest extends  CookieTestUtils{

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

    @Test
    public void testIsBookieHostCreatedFromIp() throws IOException {
        builder.setBookieHost("192.168.1.1:80");
        Cookie fromIp = builder.build();
        assertTrue(fromIp.isBookieHostCreatedFromIp());
        builder.setBookieHost("www.google.com:80");
        Cookie fromURL = builder.build();
        assertFalse(fromURL.isBookieHostCreatedFromIp());

        try {
            builder.setBookieHost("msg");
            builder.build().isBookieHostCreatedFromIp();
            fail();
        } catch (IOException e) {
            System.out.println("Expected exception: " + e.getMessage());
        }
        try {
            builder.setBookieHost("192.168.1.1:nAn");
            builder.build().isBookieHostCreatedFromIp();
            fail();
        } catch (IOException e) {
            System.out.println("Expected exception: " + e.getMessage());
        }

    }

    @Test public void testToString() {
        Cookie cookie3 = getCookie(3, "192.168.1.1:80", "2\tdir1\tdir2",
                                    "2\tdir1\tdir2", "id");
        assertEquals("4\n192.168.1.1:80\n2\tdir1\tdir2\n2\tdir1\tdir2\n", cookie3.toString());
        Cookie cookie4 = getCookie(4, "192.168.1.1:80", "2\tdir1\tdir2",
                "2\tdir1\tdir2", "id");
        assertEquals("4\n" +
                "bookieHost: \"192.168.1.1:80\"\n" +
                "journalDir: \"2\\tdir1\\tdir2\"\n" +
                "ledgerDirs: \"2\\tdir1\\tdir2\"\n" +
                "instanceId: \"id\"\n", cookie4.toString());
    }
}
