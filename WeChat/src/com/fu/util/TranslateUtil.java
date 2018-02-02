package com.fu.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fu.translate.Query;
import com.fu.translate.Result;
import com.fu.translate.TransResult;

import net.sf.json.JSONObject;

public class TranslateUtil {
	private static final String APPID = "20180126000118927";
	private static final String SECRET_KEY = "0eeGojVR22KTmS8ibqua";
	
	private static final String ENGLISH_LANGUAGE = "en";
	private static final String CHINESE_LANGUAGE = "zh";
	private static final String AUTO_LANGUAGE = "auto";
	
	private static final String TRANSLATE_URL = "http://api.fanyi.baidu.com/api/trans/vip/translate";
	
	/**
	 * 判断输入为汉语还是英语
	 * @param keyWord
	 * @return
	 */
	public static boolean chineseOrEnglish(String keyWord){
		byte[] bytes = keyWord.getBytes();
		int i = bytes.length;
		int j = keyWord.length();
		return i==j;
	}
	
	/**
	 * 组装查询
	 * @param keyWord
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static Query initQuery(String keyWord) throws UnsupportedEncodingException{
		Query query = new Query();
		
		query.setQ(URLEncoder.encode(keyWord, "utf-8"));
		
		query.setFrom(AUTO_LANGUAGE);
		
		if(chineseOrEnglish(keyWord)){
			query.setTo(CHINESE_LANGUAGE);
		}else{
			query.setTo(ENGLISH_LANGUAGE);
		}
		
		query.setAppid(APPID);
		
		long salt = new Date().getTime();
		query.setSalt(String.valueOf(salt));
		
		StringBuilder sb = new StringBuilder();
		sb.append(APPID).append(new String(keyWord.getBytes(),"utf-8")).append(salt).append(SECRET_KEY);
		String sign = sb.toString();
		sign = new String(sign.getBytes(),"utf-8");
		sign = MD5.md5(sign);
		query.setSign(sign);
		
		return query;
	}
	
	/**
	 * 调用百度翻译API获得结果
	 * @param query
	 * @return
	 */
	public static Result getResult(Query query){
		String queryString = JSONObject.fromObject(query).toString();
		queryString = queryString.replace("\":\"", "=").replace("\",\"", "&").replace("{\"", "?");		
		queryString = queryString.substring(0, queryString.length()-2);
		
		//这种方法会返回errCode:52003  未授权用户  检查您的 appid 是否正确，或者服务是否开通??????
		//JSONObject jsonObject = WeixinUtil.doPostStr(TRANSLATE_URL, queryString);
		
		JSONObject jsonObject = WeixinUtil.doGetStr(TRANSLATE_URL + queryString);
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("trans_result", TransResult.class);		
		Result result = (Result) JSONObject.toBean(jsonObject,Result.class,classMap);
		
		return result;
	}
}
