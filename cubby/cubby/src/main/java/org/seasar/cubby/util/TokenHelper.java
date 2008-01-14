/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.util;

import static org.seasar.cubby.CubbyConstants.ATTR_TOKEN;

import java.math.BigInteger;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.tags.TokenTag;
import org.seasar.cubby.validator.validators.TokenValidator;
import org.seasar.framework.util.LruHashMap;

/**
 * 2重サブミット防止処理のヘルパークラス
 * @see TokenTag
 * @see TokenValidator
 * @author agata
 */
public class TokenHelper {

	/**
	 * トークン用Mapに保持するトークンの個数（1セッションあたり何個のトークンを保持するか？）
	 */
	public static int TOKEN_HISTORY_SIZE = 16;

	/**
	 * デフォルトのトークン用パラメータ名
	 */
    public static final String DEFAULT_TOKEN_NAME = "cubby.token";

    /**
     * トークン生成用のランダムクラス
     */
    private static final Random RANDOM = new Random();
	
    /**
     * ユニークなトークンを生成します。
     * @return ユニークなトークン
     */
    public static String generateGUID() {
        return new BigInteger(165, RANDOM).toString(36).toUpperCase();
    }    

    /**
     * トークン用のマップをセッションから取得します。
     * <p>
     * セッション中にトークン用のマップが存在しない場合、新規に生成します。
     * トークン用のマップは{@link LruHashMap}を使い、トークンの保持上限付きのMapになります。
     * </p>
     * @param session セッション
     * @return トークン用のマップ
     */
	@SuppressWarnings("unchecked")
	public
	static Map<String, String> getTokenMap(HttpSession session) {
		Map<String, String> tokenMap = (Map<String, String>) session.getAttribute(CubbyConstants.ATTR_TOKEN);
		if (tokenMap == null) {
			tokenMap = new LruHashMap(TOKEN_HISTORY_SIZE);
			session.setAttribute(ATTR_TOKEN, tokenMap);
		}
		return tokenMap;
	}
	
	/**
	 * トークンをセッション中のトークン用のMapにセットします。
	 * @param session セッション
	 * @param token トークン文字列
	 */
	public static void setToken(HttpSession session, String token) {
		Map<String, String> tokenMap = getTokenMap(session);
		synchronized (tokenMap) {
			tokenMap.put(token, null);
		}
	}

	/**
	 * パラメータ中のトークン文字列とセッション中のトークン文字列を検証します。
	 * <p>
	 * セッション中に格納されたトークン用のMapのキーに、
	 * 指定されたトークン文字列が含まれるかどうかを判定し、Mapから取り除きます。
	 * </p>
	 * @param session セッション
	 * @param token トークン文字列
	 * @return 指定されたトークン文字列がセッション中に存在したら<code>true</code>、それ以外は<code>false</code>
	 */
	public static boolean validateToken(HttpSession session,
			String token) {
		Map<String, String> tokenMap = getTokenMap(session);
		synchronized (tokenMap) {
			boolean success = tokenMap.containsKey(token);
			tokenMap.remove(token);
			return success;
		}
	}
}
