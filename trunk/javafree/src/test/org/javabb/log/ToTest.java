package org.javabb.log;

import org.javabb.infra.Utils;
import org.springframework.web.util.HtmlUtils;

public class ToTest {
	public static void main(String[] args){
		String str="Map<String, Integer> ages = {\"John\" : 35, \"Mary\" : 28, \"Steve\" : 42};";
		System.out.println(HtmlUtils.htmlEscape(str));
	}
}
