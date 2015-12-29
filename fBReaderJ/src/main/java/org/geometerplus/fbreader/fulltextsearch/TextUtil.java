package org.geometerplus.fbreader.fulltextsearch;

import java.text.BreakIterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by gordon on 12/28/15.
 */
public abstract class TextUtil {

    // An iterator to break the text
    private static BreakIterator mBreakIter  = BreakIterator.getWordInstance();// = BreakIterator.getWordInstance(Locale.CHINA);

    private static final String FORMAT_HIGHTLIGHT_START = "<font color='red'>";
    private static final String FORMAT_HIGHTLIGHT_END = "</font>";

    private static boolean isChineseByREG(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }

    private static boolean isChineaseCharacter (Character c) {
        int code = (int) c;

        return (code >= 0x4e00 && code <= 0x9fbf);
    }

    /* Retrieve all words from a text */
    public static synchronized String getWordsFromText(String txt, List<String> words) {
        mBreakIter.setText(txt);
        int start = mBreakIter.first();
        int end = mBreakIter.next();

        StringBuffer buf = new StringBuffer();
        if (words!=null) words.clear();

        while (end != BreakIterator.DONE) {
            String word = txt.substring(start,end).trim();

            // Only index chinese characters, and digits
            if (word.length()!=0  &&
                    ( isChineseByREG(word) || Character.isDigit(word.charAt(0)))) {
                if (words!=null) words.add(word);
                buf.append(txt.substring(start, end));
                buf.append(" ");
            }
            start = end;
            end = mBreakIter.next();
        }

        if (buf.length() > 0)
            buf.deleteCharAt(buf.length()-1);

        return buf.toString();
    }

    public static synchronized  String getSearchPhases(String input, List<String> phases) {
        // Add space between chinese words
        // Put " " around continual Chinese words

        input = input + "."; // make sure there will be an end token
        StringBuffer buf = new StringBuffer();
        phases.clear();

        mBreakIter.setText(input);
        int start = mBreakIter.first();
        int end = mBreakIter.next();

        int state = 0; // 0 - nothing, 1 - in continuous chinese characters

        int i=0;
        int lastStart=0; // start of last phase

        while (end != BreakIterator.DONE) {
            String token = input.substring(start,end).trim();

            boolean isCn = isChineseByREG(token);

            boolean isDigit = token.length()>0 && Character.isDigit(token.charAt(0));

            switch (state) {
                case 0:
                    if (isCn) {
                        state = 1;
                        lastStart = start;
                        if (buf.length() > 0)
                            buf.append(" \"");
                        else buf.append("\"");

                        buf.append(token);
                    } else if (isDigit) {
                        buf.append(token).append(" ");
                    }
                    start = end;
                    end = mBreakIter.next();
                    break;
                case 1: // in Chinese
                    if (isCn) {
                        buf.append(' ').append(token);
                        start=end;
                        end = mBreakIter.next();
                    } else {
                        // end of phase
                        state = 0;
                        phases.add (input.substring(lastStart,start));
                        buf.append("\" ");
                    }
                    break;
            }
        }

        return buf.toString();
    }


    public static String getHtmlHighlightedText (String origText, List<String> phases) {

        StringBuffer buf = new StringBuffer(origText);

        for (String phase: phases) {
            int i = 0;
            while ((i = buf.indexOf(phase, i)) >= 0) {
                String formated_word = FORMAT_HIGHTLIGHT_START + phase + FORMAT_HIGHTLIGHT_END;
                buf.replace(i, i + phase.length(), formated_word);
                i = i + formated_word.length();
            }
        }

        return new String (buf);
    }
}
