package com.shopping.core.qiniu.storage.model;

import com.shopping.core.qiniu.util.StringUtils;

/**
 * 
 */
public class FileListing {
    public FileInfo[] items;
    public String marker;
    public String[] commonPrefixes;

    public boolean isEOF() {
        return StringUtils.isNullOrEmpty(marker);
    }
}
