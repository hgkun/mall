package com.shopping.base.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shopping.base.constant.Conf;
import com.shopping.core.qiniu.common.QiniuException;
import com.shopping.core.qiniu.http.Response;
import com.shopping.core.qiniu.storage.BucketManager;
import com.shopping.core.qiniu.storage.UploadManager;
import com.shopping.core.qiniu.storage.model.FileListing;
import com.shopping.core.qiniu.util.Auth;



/**
 * 

 * @version V1.0
 * @since V1.0
 */
public final class QiniuUtils {
	/**
	 * 日志
	 */
	private static final Logger LOG = LoggerFactory.getLogger(QiniuUtils.class);
	
	private static final Auth auth;
	
	private static final UploadManager uploadManager;
	
	private static final BucketManager bucketManager;
	
	/*public static void main(String[] args) throws QiniuException {
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					File file = new File("C:\\Users\\hongbangzhu\\Desktop\\pic.jpg");
					String uuid = CommonUtils.getUUID();
					String key = CommonUtils.getHashPath(uuid) + uuid + ".jpg";
					upload(file, key);
					System.out.println(key);
				}
			}).start();
		}
	}*/
	
	static {
		auth = Auth.create(Conf.QINIU_ACCESS_KEY, Conf.QINIU_SECRET_KEY);
		uploadManager = new UploadManager();
		bucketManager = new BucketManager(auth);
	}
	
	
	/**
	* 根据前缀获取文件列表的迭代器
	*
	* @param bucket    空间名
	* @param prefix    文件名前缀
	* @param limit     每次迭代的长度限制，最大1000，推荐值 100
	* @param delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
	* @return FileInfo迭代器
	*/
	public static FileListing listFiles(String bucket, String prefix, String marker, int limit, String delimiter) {
		try {
			return bucketManager.listFiles(bucket, prefix, marker, limit, delimiter);
		} catch (QiniuException e) {
			Response r = e.response;
	        // 请求失败时简单状态信息
	        try {
	            // 响应的文本信息
	        	LOG.error(r.bodyString());
	        } catch (QiniuException e1) {}
		}
		return null;
	}
	public static void main(String[] args) throws Exception{
		FileListing fileListing = listFiles(Conf.QINIU_BUCKET_NAME, "article/", null, 1000, null);
		System.out.println(fileListing.items.length);
		System.out.println(fileListing.commonPrefixes.length);
	}
	
	/**
	 * 获取默认token
	 * @return
	 */
	public static String getToken(){
		return getToken(Conf.QINIU_BUCKET_NAME);
	}
	
	/**
	 * 通过空间名称获取token
	 * @param bucketName 空间名称
	 * @return
	 */
	public static String getToken(String bucketName) {
		return auth.uploadToken(bucketName);
	}
	/**
	 * 通过空间名称和key获取token
	 * @param bucketName 空间名称
	 * @return
	 */
	public static String getToken(String bucketName,String key) {
		return auth.uploadToken(bucketName, key);
	}
	/**
	 * 上传图片
	 * @param file 图片文件
	 * @param key 图片路径  static/01/ee/0e/01/000000004d37f03c014d3877d1ce000c.jpg
	 * @param bucketName mmhts
	 * @return
	 */
	public static boolean upload(File file, String key, String bucketName) {
		String token = getToken(bucketName);
		try {
			Response r = uploadManager.put(file, key, token);
			return r.isOK();
		} catch (QiniuException e) {
			Response r = e.response;
	        // 请求失败时简单状态信息
			LOG.error("key:"+key+",bucketName:"+bucketName+"-------"+r.toString(), e);
	        try {
	            // 响应的文本信息
	        	LOG.error(r.bodyString());
	        } catch (QiniuException e1) {}
		}
		return false;
	}
	/**
	 * 上传图片
	 * @param file 图片文件
	 * @param key 图片路径  static/01/ee/0e/01/000000004d37f03c014d3877d1ce000c.jpg
	 * @return
	 */
	public static boolean upload(File file, String key) {
		return upload(file, key, Conf.QINIU_BUCKET_NAME);
	}
	/**
	 * 上传图片
	 * @param file 图片文件
	 * @param key 图片路径  static/01/ee/0e/01/000000004d37f03c014d3877d1ce000c.jpg
	 * @return
	 */
	public static boolean upload(byte[] datas, String key) {
		String token = getToken(Conf.QINIU_BUCKET_NAME);
		try {
			Response r = uploadManager.put(datas, key, token);
			return r.isOK();
		} catch (QiniuException e) {
			Response r = e.response;
	        // 请求失败时简单状态信息
			LOG.error("key:"+key+",bucketName:"+Conf.QINIU_BUCKET_NAME+"-------"+r.toString(), e);
	        try {
	            // 响应的文本信息
	        	LOG.error(r.bodyString());
	        } catch (QiniuException e1) {}
		}
		return false;
	}
	/**
	 * 上传私密文件
	 * @param file 私密文件
	 * @param key 私密文件路径  static/01/ee/0e/01/000000004d37f03c014d3877d1ce000c.jpg
	 * @return
	 */
	public static boolean uploadPrivate(File file, String key) {
		return upload(file, key, Conf.QINIU_PRIVATE_BUCKET_NAME);
	}
	public static boolean uploadPrivate(byte[] datas, String key) {
		String token = getToken(Conf.QINIU_PRIVATE_BUCKET_NAME);
		try {
			Response r = uploadManager.put(datas, key, token);
			return r.isOK();
		} catch (QiniuException e) {
			Response r = e.response;
	        // 请求失败时简单状态信息
			LOG.error("key:"+key+",bucketName:"+Conf.QINIU_PRIVATE_BUCKET_NAME+"-------"+r.toString(), e);
	        try {
	            // 响应的文本信息
	        	LOG.error(r.bodyString());
	        } catch (QiniuException e1) {}
		}
		return false;
	}
	/**
	 * 获取私有空间临时访问地址
	 * @param key 文件路径  static/01/ee/0e/01/000000004d37f03c014d3877d1ce000c.jpg
	 * @param expire 超时时间（秒）
	 * @return 临时访问地址
	 */
	public static String privateUrl(String key, int expire) {
		return auth.privateDownloadUrl(Conf.PRIVATE_HOST + key, expire);
	}
	/**
	 * 获取私有空间临时访问地址(默认时间为60秒)
	 * @param key 文件路径  static/01/ee/0e/01/000000004d37f03c014d3877d1ce000c.jpg
	 * @return 临时访问地址
	 */
	public static String privateUrl(String key) {
		return privateUrl(key, Conf.QINIU_PRIVATE_EXPIRE);
	}
}
