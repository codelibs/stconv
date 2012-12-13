package org.codelibs.stconv.wiki.pukiwiki.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codelibs.stconv.storage.StreamStorage;
import org.codelibs.stconv.wiki.storage.callback.LinkGeneratorCallback;
import org.codelibs.stconv.wiki.storage.impl.CallbackStreamStorageImpl;

public class ConvertPageNameLinkValve extends AbstractWikiValve {

    private String pattern = ".*[A-Z][a-z]+[A-Z][a-z]+.*";

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final StreamStorage storage,
            final BufferedWriter writer, final String line) throws IOException {
        final LinkGeneratorCallback callback = (LinkGeneratorCallback) ((CallbackStreamStorageImpl) storage)
                .getCallback(LinkGeneratorCallback.LINK_GENERATOR_CALLBACK);
        if (callback == null) {
            writeUnmatchLine(writer, line);
            return;
        }

        final Pattern pattern = Pattern.compile("([A-Z][a-z]+([A-Z][a-z]+)+)");
        final Matcher matcher = pattern.matcher(line);
        final StringBuffer buf = new StringBuffer();
        while (matcher.find()) {
            final String pageName = matcher.group(1);
            callback.init();
            callback.addAttribute(LinkGeneratorCallback.PAGE_NAME, pageName);
            matcher.appendReplacement(buf, "<a href=\"" + callback.getUrl()
                    + "\">" + pageName + "</a>");
        }
        matcher.appendTail(buf);

        writer.write(buf.toString());
        writer.newLine();
    }

}
