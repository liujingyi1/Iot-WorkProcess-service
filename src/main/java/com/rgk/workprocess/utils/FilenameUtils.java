package com.rgk.workprocess.utils;

import org.springframework.util.StringUtils;

public class FilenameUtils {
    public static String getBaseName(String fileName) {
        String basename = StringUtils.getFilename(fileName);
        if (basename.contains(".")) {
            basename = (basename.split("\\."))[0];
        }
        return basename;
    }

    public static String getExtension(String fileName) {
        return StringUtils.getFilenameExtension(fileName);
    }
}
