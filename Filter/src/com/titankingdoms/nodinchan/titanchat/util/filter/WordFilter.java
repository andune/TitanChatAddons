package com.titankingdoms.nodinchan.titanchat.util.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFilter {
	
	private final Pattern letters;
	private final Pattern pattern;
	
	public WordFilter(String word) {
		this.letters = Pattern.compile(".");
		this.pattern = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
	}
	
	public String replaceAll(String message, String censor) {
		StringBuilder str = new StringBuilder(message);
		Matcher matcher = pattern.matcher(message);
		
		while (matcher.find())
			str.replace(matcher.start(), matcher.end(), letters.matcher(matcher.group()).replaceAll(censor));
		
		return str.toString();
	}
}