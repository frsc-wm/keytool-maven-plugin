package org.codehaus.mojo.keytool.requests;

import org.codehaus.mojo.keytool.KeyToolResult;

import java.io.File;
import java.net.URL;

/**
 * Test the {@link KeyToolImportCertificateRequest}.
 *
 * @author tchemit <chemit@codelutin.com>
 * @since 1.0
 */
public class KeyToolImportCertificateRequestIT
    extends AbstractKeyToolRequestIT
{

    public void testSimpleRequest()
        throws Exception
    {

        URL keyStoreURL = getKeyStoreURL( "simple" );
        assertNotNull( keyStoreURL );
        File keyStore = new File( workingDirectory, "testSimpleRequest-keystore" );
        assertFalse( keyStore.exists() );
        copyURLToFile( keyStoreURL, keyStore );
        assertTrue( keyStore.exists() );

        URL certificateURL = getCertificateURL( "simple" );
        assertNotNull( certificateURL );
        File file = new File( workingDirectory, "testSimpleRequest-certificate" );
        assertFalse( file.exists() );
        copyURLToFile( certificateURL, file );
        assertTrue( file.exists() );

        KeyToolImportCertificateRequest request = new KeyToolImportCertificateRequest();
        request.setAlias( "foo_alias2" );
        request.setStoretype( "jks" );
        request.setStorepass( "changeit" );
        request.setKeystore( keyStore.getAbsolutePath() );
        request.setNoprompt( true );
        request.setVerbose( true );
        request.setTrustcacerts( true );
        request.setFile( file.getAbsolutePath() );
        request.setKeypass( "new-passwd" );

        KeyToolResult keyToolResult = executeKeyToolRequest( request );

        assertKeyToolResult( keyToolResult,
                             new String[]{ "-importcert", "-v", "-keystore", keyStore.getAbsolutePath(), "-storepass",
                                 "changeit", "-storetype", "jks", "-alias", "foo_alias2", "-noprompt", "-trustcacerts",
                                 "-file", file.getAbsolutePath(), "-keypass", "new-passwd" }, 0 );

        assertTrue( file.exists() );
    }

}