package org.geometerplus.fbreader.egwbooks;

/**
 * Created by gordon on 12/23/15.
 */
public abstract class EGWUtilities {

    public static String extractTitleFromSortName(String fileName) {
        // The file name could be [number.]Name[.FileExt]

        String ret = fileName;

        // Get the start of substring -> i,  removing the prefix digits and "." if exist
        int i = 0;
        while (i < fileName.length() &&
                (Character.isDigit(fileName.charAt(i)) ||
                 fileName.charAt(i) == '.'))
            i++;

        if (i >= fileName.length())
            // All digits? Just return original
            return fileName;

        // Get the end of substring -> j, removing the file extention if exists.
        int j = fileName.lastIndexOf('.');
        if (j < i) // not an extention
            j = fileName.length();

        // Return substring from i to j
        return fileName.substring(i, j  ); // (j+1) excluded
    }

    public static String extractTitleFromPath (String path) {
        // The file name could be [number.]Name[.FileExt]

        int i = path.lastIndexOf("/");
        if (i < 0) return path;
        else {
            if (path.charAt(path.length() - 1) == '/')
                return extractTitleFromSortName(path.substring(i + 1, path.length()));
            else
                return extractTitleFromSortName(path.substring(i + 1));
        }
    }
}
