package org.apache.bookkeeper.bookie;

public class CookieTestUtils {

    public static Cookie getCookie(int layoutVersion, String bookieHost, String journalDirs, String ledgedDirs,
                                   String instanceId ) {
        Cookie.Builder  cBuilder = Cookie.newBuilder();
        cBuilder.setLedgerDirs(ledgedDirs);
        cBuilder.setBookieHost(bookieHost);
        cBuilder.setInstanceId(instanceId);
        cBuilder.setJournalDirs(journalDirs);
        cBuilder.setLayoutVersion(layoutVersion);
        return cBuilder.build();
    }
}
