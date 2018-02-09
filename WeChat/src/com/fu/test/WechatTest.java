package com.fu.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import com.fu.pojo.AccessToken;
import com.fu.translate.Query;
import com.fu.translate.Result;
import com.fu.util.MessageUtil;
import com.fu.util.TranslateUtil;
import com.fu.util.WeixinUtil;

import net.sf.json.JSONObject;

public class WechatTest {

	public static void main(String[] args) {
		//获取accessToken
		AccessToken accessToken = WeixinUtil.getAccessToken();
		
		//上传文件获得media_id
		String path = "C:/Users/FXZ/Pictures/Saved Pictures/hello.jpeg";
		try {
			String media_id = WeixinUtil.upload(path, accessToken.getToken(), "image");
			System.out.println(accessToken.getToken());
			System.out.println(media_id);
		} catch (KeyManagementException e) { 
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//创建菜单
		String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
		int result = WeixinUtil.createMenu(accessToken.getToken(), menu);
		if(result == 0){
			System.out.println("创建菜单成功");
		}else{
			System.out.println(result);
		}
		
		//百度翻译API
		Query query = null;
		try {
			query = TranslateUtil.initQuery("hello world");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Result transResult = TranslateUtil.getResult(query);
		
	}

}
