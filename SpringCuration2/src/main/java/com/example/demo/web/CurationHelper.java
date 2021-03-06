package com.example.demo.web;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;


public class CurationHelper {

	@Autowired
	private static UserRepository userRepository;

  /**
   * 生のパスワードを引数にとってMD5で暗号化して返すメソッド
 * @param password
 * @return　暗号化されたパスワード
 */
	public static String encryption(String password){
	    //ハッシュ生成前にバイト配列に置き換える際のCharset
	    Charset charset = StandardCharsets.UTF_8;
	    //ハッシュアルゴリズム
	    String algorithm = "MD5";

	    //ハッシュ生成処理
	    byte[] bytes = null;
		try {
			bytes = MessageDigest.getInstance(algorithm).digest(password.getBytes(charset));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    String result = DatatypeConverter.printHexBinary(bytes);
	    //標準出力
	    // System.out.println(result);
	    return result;
	  }



//	sessionに保存されている値を取り出し、破棄する
	public static Object cutSessionAttribute(HttpSession session, String str) {
		Object test = session.getAttribute(str);
		session.removeAttribute(str);

		return test;
	}

	public static User getAuthUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		return userRepository.findByName(principal.getUsername());
	}
	
}
