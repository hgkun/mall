package com.shopping.core.qiniu.storage;

import java.io.File;

/**
 *
 */
public interface RecordKeyGenerator {
    /**
     * 根据服务器的key和本地文件名生成持久化纪录的key
     *
     * @param key  服务器的key
     * @param file 本地文件名
     * @return 持久化上传纪录的key
     */
    String gen(String key, File file);
}
