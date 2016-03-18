package org.car.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5{
	private static MD5 md5;
	
	// 产生一个MD5实例
		public static MD5 getInstance() {
			if (null != md5){
				return md5;}
			else {
				makeInstance();
				return md5;
			}
			
		}

		// 保证同一时间只有一个线程在使用MD5加密
		private static synchronized void makeInstance() {
			if (null == md5)
				md5 = new MD5();
		}
		
		
	  /**
     * 对字符串进行MD5签名
     * 
     * @param text
     *            明文
     * @param charset
     *            字符编码
     * 
     * @return 密文
     */
    public String getMd5(String text,String charset) {
    	return DigestUtils.md5Hex(getContentBytes(text, charset));

    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }

        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    public static void main(String[] args) {
		System.out.println(MD5.getInstance().getMd5("123456","UTF-8"));	
	}

}
