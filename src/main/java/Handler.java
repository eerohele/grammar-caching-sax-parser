package com.github.eerohele.protocols.classpath;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

// http://stackoverflow.com/a/8462301/825783

public class Handler extends URLStreamHandler {
    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        String resource = u.getPath();
        if (!resource.startsWith("/")) resource = "/" + resource;
        return getClass().getResource(resource).openConnection();
    }
}
