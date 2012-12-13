package org.codelibs.stconv.hatena.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConvertTimeCaptionValve extends AbstractHatenaValve {

    private String pattern = "^\\*{1}[0-9]{10}\\*.*?";

    private static final String HH_MS = "HH:mm";

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        // TODO get time
        final long time = Long
                .parseLong(line.substring(1, line.indexOf("*", 1)));
        final String strTime = getTimeAsString(time);
        writer.write("<p><span class=\"sanchor\">■</span> <span class=\"h3\">"
                + line.substring(line.indexOf("*", 1) + 1)
                + " <span class=\"timestamp\">" + strTime + "</span>"
                + "</span></p>");
        writer.newLine();
    }

    /**
     * @param time
     * @return
     */
    private String getTimeAsString(final long time) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time * 1000L);
        //		log.debug("aaa "+cal.getTime().getTime());
        //		log.debug("time "+cal.getTime().toLocaleString());
        final SimpleDateFormat sdf = new SimpleDateFormat(HH_MS);
        return sdf.format(cal.getTime());
    }

}
