package com.fu.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.fu.pojo.Image;
import com.fu.pojo.ImageMessage;
import com.fu.pojo.Music;
import com.fu.pojo.MusicMessage;
import com.fu.pojo.News;
import com.fu.pojo.NewsMessage;
import com.fu.pojo.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class MessageUtil {
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE = "scancode_push";
	
	/**
	 * xml转为map集合
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String,String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();
		
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		
		List<Element> list = root.elements();
		
		for(Element e:list){
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	
	
	/**
	 * 将文本消息对象转为Xml
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	/**
	 * 文本消息的组装
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName,String fromUserName,String content){
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}
	
	
	/**
	 * 将图片消息对象转为Xml
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml (ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	/**
	 * 图片消息组装
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		Image image = new Image();
		image.setMediaId("U_JLM8efQMT6aw7WhAjGgWtjBV1QjtRx7jeXSECQTV7WsCdq01CuiajTCaoUNLUx");
		
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setToUserName(fromUserName);
		imageMessage.setFromUserName(toUserName);
		imageMessage.setImage(image);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		
		return imageMessageToXml(imageMessage);
	}
	
	
	/**
	 * 主菜单
	 * @return
	 */
	public static String menuText(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注，这里有最新比赛结果、篮球周边新闻、篮球技术交流。\n");
		sb.append("回复编号体验相应功能：\n\n");
		sb.append("1、虎扑篮球官网\n");
		sb.append("2、NBA中国官方网站\n");
		sb.append("3、回复图片消息\n");
		sb.append("4、回复音乐消息\n");
		sb.append("5、发送任意消息回复翻译结果（中译英or英译中）\n\n");
		sb.append("回复?调出此菜单");
		return sb.toString();
	}
	/**
	 * 菜单1
	 * @return
	 */
	public static String firstMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("虎扑篮球官网\n");
		sb.append("https://nba.hupu.com");
		return sb.toString();
	}
	/**
	 * 菜单2 
	 * @return
	 */
	public static String secondMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("NBA中国官方网站\n");
		sb.append("http://china.nba.com");
		return sb.toString();
	}
	
	
	/**
	 * 图文消息转xml
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		return xstream.toXML(newsMessage);
	}
	/**
	 * 图文消息的组装
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,String fromUserName){
		 NewsMessage newsMessage = new NewsMessage();
		 
		 List<News> articles = new ArrayList<News>();
		
		 News news = new News();
		 news.setTitle("NBA中国官网");
		 news.setDescription("详细NBA信息请移步NBA官网");
		 news.setPicUrl("http://3608e3b8.nat123.cc/WeChat/picture/all-star.jpg");
		 news.setUrl("http://china.nba.com");
		 articles.add(news);
		 
		 //------这里list转xml有问题------------
		/* News news2 = new News();
		 news.setTitle("虎扑中国官网");
		 news.setDescription("详细NBA信息请移步虎扑NBA官网");
		 news.setPicUrl("http://3608e3b8.nat123.cc/WeChat/picture/hupu.PNG");
		 news.setUrl("https://nba.hupu.com");
		 articles.add(news2);*/
		 
		 newsMessage.setFromUserName(toUserName);
		 newsMessage.setToUserName(fromUserName);
		 newsMessage.setCreateTime(new Date().getTime());
		 newsMessage.setMsgType(MESSAGE_NEWS);
		 newsMessage.setArticleCount(articles.size());
		 newsMessage.setArticles(articles);
		 
		 return newsMessageToXml(newsMessage);
	}
	
	
	/**
	 * 音乐消息转Xml
	 * @param musicMessage
	 * @return
	 */
	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
	/**
	 * 音乐消息的组装
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName,String fromUserName){
		Music music = new Music();
		music.setTitle("Future Sounds Like Us");
		music.setThumbMediaId("szyECUKkPGHkuKL-ZmyoxGL0VJsdH_L1DVQEm8hH0RvttZBBpAnje1sThuLgooW7");
		music.setDescription("伊丽莎白·史旺");
		music.setHQMusicUrl("http://3608e3b8.nat123.cc/WeChat/music/Future Sounds Like Us.mp3");
		music.setMusicUrl("http://3608e3b8.nat123.cc/WeChat/music/Future Sounds Like Us.mp3");
		
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setToUserName(fromUserName);
		musicMessage.setFromUserName(toUserName);
		musicMessage.setMusic(music);
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		
		return musicMessageToXml(musicMessage);
	}
}
