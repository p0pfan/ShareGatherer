package com.favorcollection.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;

import sun.misc.BASE64Encoder;
import weibo4j.util.WeiboConfig;


/**
* @ClassName: Weibo 
* @Description: 微博登录验证
* @author cloudroc
* @date 2016年4月19日 下午4:18:06 
*
 */
public class Weibo {
	
	private static final Logger logger = Logger.getLogger(Weibo.class);
	
	private static DefaultHttpClient httpclient = null;// HttpClient对象
	
		
	private static final String preLoginUrl = WeiboConfig.getValue("preLoginUrl");

	private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36";
	
	private static final String uploadUrl = "http://picupload.service.weibo.com/interface/pic_upload.php?&mime=image%2Fjpeg&data=base64&url=0&markpos=1&logo=&nick=0&marks=1&app=miniblog";
	private static final String login = WeiboConfig.getValue("loginUrl");
	private static final String userName = WeiboConfig.getValue("username");
	private static final String passWord = WeiboConfig.getValue("password");
	private static final String authorize = WeiboConfig.getValue("authorizeURL");
	
	private static final String CALLBACK_URL = WeiboConfig.getValue("redirect_URI");
	private static final String APP_KEY = WeiboConfig.getValue("client_ID");
	
	public static String getTicketFromWeibo(){
		
		boolean flag = true;
		
		String loginUrl = login +   
                System.currentTimeMillis();
		
		httpclient = new DefaultHttpClient();
		// 设定cookie策略
		HttpClientParams.setCookiePolicy(httpclient.getParams(),CookiePolicy.BROWSER_COMPATIBILITY);
		
		String preJosn = getPreLoginJson();
		JSONObject preObj= new JSONObject(preJosn);
		
		String pubkey = preObj.getString("pubkey");  
        String servertime = String.valueOf(preObj.getLong("servertime"));  
        String nonce = preObj.getString("nonce");  
        String rsakv = preObj.getString("rsakv");
        
        HttpPost httppost = new HttpPost(loginUrl);
        httppost.setHeader("Accept", "*/*");
		httppost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4,zh-TW;q=0.2");
		httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httppost.setHeader("Host", "login.sina.com.cn");
		httppost.setHeader("Origin", "http://weibo.com");
		
		List<NameValuePair> formparams =new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("entry", "weibo"));
        formparams.add(new BasicNameValuePair("password", passWord));
        
        formparams.add(new BasicNameValuePair("entry", "weibo"));  
        formparams.add(new BasicNameValuePair("gateway", "1"));  
        formparams.add(new BasicNameValuePair("from", ""));  
        formparams.add(new BasicNameValuePair("savestate", "7"));  
        formparams.add(new BasicNameValuePair("useticket", "1"));  
        formparams.add(new BasicNameValuePair("pagerefer", "http://s.weibo.com/weibo/%25E6%2596%2587%25E7%25AB%25A0%25E5%2590%258C%25E5%25AD%25A6?topnav=1&wvr=6&b=1"));  
        formparams.add(new BasicNameValuePair("vsnf", "1"));  
        formparams.add(new BasicNameValuePair("su", getEncodeUserName(userName)));  
        formparams.add(new BasicNameValuePair("service", "miniblog"));  
        formparams.add(new BasicNameValuePair("servertime", servertime));  
        formparams.add(new BasicNameValuePair("nonce", nonce));  
        formparams.add(new BasicNameValuePair("pwencode", "rsa2"));  
        formparams.add(new BasicNameValuePair("rsakv", rsakv));  
        formparams.add(new BasicNameValuePair("sp", getSP(passWord, pubkey, servertime, nonce)));  
        formparams.add(new BasicNameValuePair("encoding", "UTF-8"));  
        formparams.add(new BasicNameValuePair("sr", "1366*768"));  
        formparams.add(new BasicNameValuePair("prelt", "1011"));  
        formparams.add(new BasicNameValuePair("url", "http://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack"));  
        formparams.add(new BasicNameValuePair("domain", "weibo.com"));  
        formparams.add(new BasicNameValuePair("returntype", "META"));  
        String ticket ="";
        try {
			httppost.setEntity(new UrlEncodedFormEntity(formparams,  HTTP.UTF_8));
			
			//获取登陆应答内容
			HttpResponse response = httpclient.execute(httppost);
			
			logger.info("登录返回状态码："+response.getStatusLine().getStatusCode());
			
			HttpEntity httpEntity = response.getEntity(); 
			String responseStr = EntityUtils.toString(httpEntity);
			System.out.println(responseStr);
			String loginHtml1 =  new String(responseStr.getBytes("iso8859-1"), "GBK");
			
			logger.info(userName+"-登录请求第1次返回："+loginHtml1);
			
			String loginUrl2 = "" ;
			String loginHtml2 = "" ;
			
			Pattern pattern1 = Pattern.compile("location.replace\\('(.*?)'\\);");
			Matcher m1 = pattern1.matcher(loginHtml1);
			if (m1.find()) {
				loginUrl2=m1.group(1);
				logger.info("第2次登录请求url："+loginUrl2);
			}
			String[] params =StringUtils.split(loginUrl2, "&");
			logger.info(Arrays.toString(params));
			ticket=params[params.length-2];
//			
//			//以下几段httpget写的很冗余，可以抽象为方法..
//			HttpGet loginGet2 = new HttpGet(loginUrl2);
//			
//			try {
//				HttpResponse response2 = httpclient.execute(loginGet2);
//				HttpEntity httpEntity2 = response2.getEntity();
//				loginHtml2 = EntityUtils.toString(httpEntity2);
//				
//				logger.info(userName+"-登录请求第2次返回："+loginHtml2);
//				
//			} catch (ClientProtocolException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} finally {
//				loginGet2.abort();
//			}
//			
//			
//			String loginUrl3 = "" ;
//			String loginHtml3 = "" ;
//			
//			//"userdomain":"?wvr=5&lf=reg"}
//			Pattern pattern3 = Pattern.compile("\"userdomain\":\"(.*?)\"}");
//			Matcher m3 = pattern3.matcher(loginHtml2);
//			if (m3.find()) {
//				loginUrl3="http://weibo.com/"+m3.group(1);
//				logger.info("第3次登录请求url："+loginUrl3);
//			}
//			
//			HttpGet loginGet3 = new HttpGet(loginUrl3);
//			
//			try {
//				HttpResponse response3 = httpclient.execute(loginGet3);
//				HttpEntity httpEntity3 = response3.getEntity();
//				loginHtml3 = EntityUtils.toString(httpEntity3);
//				
//				logger.info(userName+"-登录请求第3次返回："+loginHtml3);
//				
//			} catch (ClientProtocolException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} finally {
//				loginGet3.abort();
//			}
//			
//			logger.info(userName+" 登录成功...");
//			
		}catch (Exception e) {
			e.printStackTrace();
			return ticket;
		} finally {
			httppost.abort();
		}
        logger.info(ticket);
		return ticket;
	}
	
	public static String getCode(){
//		String url = "https://api.weibo.com/oauth2/authorize?client_id="+APP_KEY+"&redirect_uri=https://api.weibo.com/oauth2/default.html&response_type=code";
//		String url = "https://api.weibo.com/oauth2/authorize";
		String url = 
				"https://api.weibo.com/oauth2/authorize?client_id=" + APP_KEY + "&redirect_uri=" + CALLBACK_URL + "&from=sina&response_type=code";
		String ticlets = getTicketFromWeibo();
		String ticket = ticlets.substring(7, ticlets.length());
		logger.info(ticket);
		HttpPost httppost = new HttpPost(authorize);
//		httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httppost.setHeader("User-Agent", userAgent);
		httppost.setHeader("Referer", url);
		httppost.setHeader("Host", "api.weibo.com");
		
		List<NameValuePair> formparams =new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("action", "submit"  ));
		formparams.add(new BasicNameValuePair("display", "default"  ));
		formparams.add(new BasicNameValuePair("withOfficalFlag", "0"  ));
		formparams.add(new BasicNameValuePair("quick_auth", "null"  ));
		formparams.add(new BasicNameValuePair("withOfficalAccount", ""  ));
		formparams.add(new BasicNameValuePair("scope", ""));
		formparams.add(new BasicNameValuePair("ticket", ticket  ));
		formparams.add(new BasicNameValuePair("isLoginSina", ""  ));
		formparams.add(new BasicNameValuePair("response_type", "code"  ));
		formparams.add(new BasicNameValuePair("regCallback", "https://api.weibo.com/2/oauth2/authorize?client_id="+APP_KEY+"&response_type=code&display=default&redirect_uri="+CALLBACK_URL+"&from=&with_cookie="  ));
		formparams.add(new BasicNameValuePair("redirect_uri",CALLBACK_URL  ));
		formparams.add(new BasicNameValuePair("client_id",APP_KEY  ));
		formparams.add(new BasicNameValuePair("appkey62", "52laFx"  ));
		formparams.add(new BasicNameValuePair("state", ""  ));
		formparams.add(new BasicNameValuePair("verifyToken", "null"  ));
		formparams.add(new BasicNameValuePair("from", ""  ));
		formparams.add(new BasicNameValuePair("switchLogin","0"  ));
		formparams.add(new BasicNameValuePair("userId",userName  ));
		formparams.add(new BasicNameValuePair("passwd",passWord));
		String code ="";
		String responseStr = "";
		try {
			httppost.setEntity(new UrlEncodedFormEntity(formparams,  HTTP.UTF_8));
			
			//获取登陆应答内容
			HttpResponse response = httpclient.execute(httppost);
			
			logger.info("登录返回状态码："+response.getStatusLine().getStatusCode());
			
			for(int i = 0;i<response.getAllHeaders().length;i++){
				System.out.println(response.getAllHeaders()[i].getName()+"   "+response.getAllHeaders()[i].getValue());
			}
			String codeContains = response.getFirstHeader("Location").getValue();
			code = codeContains.split("code=")[1];
			logger.info("code is:"+code);
			
		}catch (Exception e) {
			e.printStackTrace();
			return responseStr;
		} finally {
			httppost.abort();
		}
		logger.info(code);
		return code;
	}
	
	private static String getPreLoginJson(){
		
		String json = "" ;
		
		HttpGet httpget = new HttpGet(preLoginUrl.toString());
		httpget.setHeader("User-Agent", userAgent);
		// Create a response handler
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = "";
		try {
			responseBody = httpclient.execute(httpget, responseHandler);
		} catch (Exception e) {
			e.printStackTrace();
			responseBody = null;
		} finally {
			httpget.abort();
		}
		
		Pattern pattern1 = Pattern.compile("\\((.*)\\)");
		Matcher m1 = pattern1.matcher(responseBody);
		if (m1.find()) {
			json=m1.group(1);
			logger.info("预登录信息："+json);
		}
		
		return json;
	}
	
	private static String getEncodeUserName(String userName){
		try {
			userName = java.net.URLEncoder.encode(userName, "UTF-8");
			byte[] isoret = userName.getBytes("UTF-8");
			return Base64.encodeBase64String(isoret);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	private static String getSP(String pwd,String pubkey,String servertime,String nonce) {  
        String t = "10001";  
        String message = servertime + "\t" + nonce + "\n" + pwd;  
        String result = null;  
        try {  
            result = rsa(pubkey, t , message);  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (InvalidKeySpecException e) {  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }    
        logger.info("RSA加密后的密码：" + result);  
        return result;  
    }
	
	private static String rsa(String pubkey, String exponentHex, String pwd)  
            throws IllegalBlockSizeException, BadPaddingException,  
            NoSuchAlgorithmException, InvalidKeySpecException,  
            NoSuchPaddingException, InvalidKeyException,  
            UnsupportedEncodingException {  
        KeyFactory factory = KeyFactory.getInstance("RSA");  
  
        BigInteger m = new BigInteger(pubkey, 16);  
        BigInteger e = new BigInteger(exponentHex, 16);  
        RSAPublicKeySpec spec = new RSAPublicKeySpec(m, e);  
  
        //创建公钥  
        RSAPublicKey pub = (RSAPublicKey) factory.generatePublic(spec);  
        Cipher enc = Cipher.getInstance("RSA");  
        enc.init(Cipher.ENCRYPT_MODE, pub);  
  
        byte[] encryptedContentKey = enc.doFinal(pwd.getBytes("UTF-8"));  
  
        return new String(encodeHex(encryptedContentKey));  
    }
	
	protected static char[] encodeHex(final byte[] data, final char[] toDigits) {  
        final int l = data.length;  
        final char[] out = new char[l << 1];  
          
        for (int i = 0, j = 0; i < l; i++) {  
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];  
            out[j++] = toDigits[0x0F & data[i]];  
        }  
        return out;  
    }  
  
    public static char[] encodeHex(final byte[] data, final boolean toLowerCase) {  
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);  
    }  
  
    public static char[] encodeHex(final byte[] data) {  
        return encodeHex(data, true);  
    }
    
    private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5',  
        '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  

    private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5',  
        '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

public static void main(String[] args) throws IOException {
	getCode();
}}