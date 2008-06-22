package org.seasar.cubby.cubbitter.action;

import java.util.HashMap;
import java.util.Map;

public class Conv {
	public static Map<String,Object>[] convertArrayToCommentMaps(Object[][] obj){
		Map<String,Object> maps[] = new Map[obj.length];
		for(int i=0; i<obj.length; i++){
			maps[i] = new HashMap<String,Object>();
			maps[i].put("memberId", obj[i][0]);
			maps[i].put("memberName", obj[i][1]);
			maps[i].put("fullName", obj[i][2]);
			maps[i].put("commentid", obj[i][3]);
			maps[i].put("comment", obj[i][4]);
			maps[i].put("posttime", obj[i][5]);
			maps[i].put("open", obj[i][6]);
			if(obj[i].length > 7){
				maps[i].put("favorite", obj[i][7]);
			}
		}
		return maps;
	}
	public static Map<String,Object>[] convertArrayToMemberMaps(Object[][] obj){
		Map<String,Object> maps[] = new Map[obj.length];
		for(int i=0; i<obj.length; i++){
			maps[i] = new HashMap<String,Object>();
			maps[i].put("memberId", obj[i][0]);
			maps[i].put("memberName", obj[i][1]);
			maps[i].put("fullName", obj[i][2]);
			//maps[i].put("password", obj[i][3]);
			//maps[i].put("locale", obj[i][4]);
			//maps[i].put("open", obj[i][5]);
			//maps[i].put("email", obj[i][6]);
			//maps[i].put("web", obj[i][7]);
			//maps[i].put("biography", obj[i][8]);
			//maps[i].put("location", obj[i][9]);
			if(obj[i].length>3){
				maps[i].put("request", obj[i][3]);
			}
			if(obj[i].length>9){
				maps[i].put("open", obj[i][4]);
				maps[i].put("web", obj[i][5]);
				maps[i].put("biography", obj[i][6]);
				maps[i].put("location", obj[i][7]);
				maps[i].put("comment", obj[i][8]);
				maps[i].put("postTime", obj[i][9]);
				//maps[i].put("hideComment", obj[i][12]);
			}
		}
		return maps;	
	}
}
