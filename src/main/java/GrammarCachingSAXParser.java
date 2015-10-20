package com.github.eerohele;

import java.io.File;

import org.apache.xerces.parsers.SAXParser;
import org.apache.xerces.util.XMLCatalogResolver;
import org.apache.xerces.util.SymbolTable;

import org.dita.dost.reader.GrammarPoolManager;

/**
 * A SAXParser implementation that uses the DITA-OT grammar pool to cache
 * XML grammars (DTD, XSD) and XMLCatalogResolver to resolve XML catalog files.
 * <p>
 * If you use Saxonica's S9API and you want to parse many XML files with
 * associated grammars, you'll want to set this class as Saxon's source parser
 * class via the SOURCE_PARSER_CLASS configuration property[1].
 * <p>
 * Otherwise, Saxon will parse the grammar associated with the document every
 * time it parses a new XML file, which will result in a massive performance
 * hit.
 *
 * [1] http://www.saxonica.com/documentation9.0/javadoc/net/sf/saxon/FeatureKeys.html#SOURCE_PARSER_CLASS
 *
 * @author Eero Helenius
 * @version 0.1.0
 */

public final class GrammarCachingSAXParser extends SAXParser {
    static {
        System.setProperty("java.protocol.handler.pkgs",
                           "com.github.eerohele.protocols");
    }

    private static final String SEMICOLON = ";";

    private static final String[] getCatalogFiles() {
        String xmlCatalogFiles = System.getProperty("xml.catalog.files");

        if (xmlCatalogFiles != null) {
            return xmlCatalogFiles.split(SEMICOLON);
        } else {
            return new String [] { "classpath:catalog.xml" };
        }
    }

    public GrammarCachingSAXParser() throws Throwable {
        super(new SymbolTable(), GrammarPoolManager.getGrammarPool());
        String[] catalogFiles = GrammarCachingSAXParser.getCatalogFiles();
        XMLCatalogResolver resolver = new XMLCatalogResolver(catalogFiles);
        resolver.setPreferPublic(true);
        this.setEntityResolver(resolver);
    }
}
