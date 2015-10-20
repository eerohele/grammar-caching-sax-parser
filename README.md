# Grammar-caching SAX Parser

If you use the [Saxon 9 Java API][s9api] and you need to wrangle lots of XML
files that have associated DTDs, you'll want to cache the DTDs instead of
parsing them again and again every time. Otherwise, you're going to take a
massive performance hit.

For example, with the [DITA 1.2 specification][dita-1.2-specification], which is
around 750 DITA XML files, processing with grammar caching is about 70x faster
than without.

[DITA Open Toolkit's][dita-ot] grammar pool utilities do all the heavy lifting
here: the `GrammarCachingSAXParser` class just implements the `SAXParser`
interface in a way that leverages DITA-OT's `GrammarPoolManager`.

## Use with Saxon

```java
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.lib.FeatureKeys;
import com.github.eerohele.GrammarCachingSAXParser;

...
    Processor processor = new Processor(false);
    processor.setConfigurationProperty(FeatureKeys.SOURCE_PARSER_CLASS, GrammarCachingSAXParser.getName());
...
```

## Specifying the location of your catalogs

By default, the `GrammarCachingSAXParser` looks for a file called `catalog.xml`
in your classpath. Use the `xml.catalog.files` system property to use something
else. For example:

```bash
-Dxml.catalog.files=/etc/xml/catalog-one.xml;/etc/catalog-two.xml
```

[dita-1.2-specification]: http://docs.oasis-open.org/dita/v1.2/spec/DITA1.2-spec.zip
[dita-ot]: http://www.dita-ot.org
[s9api]: http://www.saxonica.com/documentation/index.html#!javadoc
