package com.easeye.quartz.quartzmonitor.util;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

public class StrUtil {

	private static final String EMPTY = "";

	public static String convertEncode(String str) {
		byte b[] = new byte[str.length()];
		for (int i = 0; i < str.length(); i++) {
			b[i] = (byte) (-256 + (0xffff - (int) str.charAt(i)));
		}
		try {
			return new String(b, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * (source != null) ? source : ""; 等同于Orocle的nvl
	 * 
	 * @param source
	 */
	public static String nvl(String source) {
		return (source != null) ? source.trim() : "";
	}

	/**
	 * (source != null) ? source : ""; 等同于Orocle的nvl
	 * 
	 * @param source
	 */
	public static String nvl(String source, String defaultString) {
		return (source != null) ? source.trim() : defaultString;
	}

	/**
	 * 转换UTF-8
	 */
	public static String toUtf8(String src) {
		byte[] b = src.getBytes();
		char[] c = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			c[i] = (char) (b[i] & 0x00FF);
		}
		return new String(c);
	}

	/**
	 * 说明：增加字符串,结果并赋值于source
	 * 
	 * @param source
	 * @param separated
	 * @param addStr
	 */
	public static String addToSource(String source, String separated,
			Object addStr) {
		return new StringBuffer(StrUtil.trimToEmpty(source))
				.append(StrUtil.trimToEmpty(separated))
				.append(StrUtil.trimToEmpty(addStr == null ? null : addStr
						.toString())).toString();
	}

	/**
	 * 判断一个字符串是否为空，空格作非空处理。 StringUtils.isEmpty(null) = true
	 * StringUtils.isEmpty("") = true StringUtils.isEmpty(" ") = false
	 * StringUtils.isEmpty("bob") = false StringUtils.isEmpty("  bob  ") = false
	 */
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.length() == 0));
	}

	/**
	 * 判断一个字符串是否非空，空格作非空处理. StringUtils.isNotEmpty(null) = false
	 * StringUtils.isNotEmpty("") = false StringUtils.isNotEmpty(" ") = true
	 * StringUtils.isNotEmpty("bob") = true StringUtils.isNotEmpty("  bob  ") =
	 * true
	 */
	public static boolean isNotEmpty(String str) {
		return ((str != null) && (str.length() > 0));
	}

	/**
	 * 判断一个字符串是否非空，空格作空处理. StringUtils.isNotBlank(null) = false
	 * StringUtils.isNotBlank("") = false StringUtils.isNotBlank(" ") = false
	 * StringUtils.isNotBlank("bob") = true StringUtils.isNotBlank("  bob  ") =
	 * true
	 */
	public static boolean isNotBlank(String str) {
		int strLen;

		if ((str == null) || ((strLen = str.length()) == 0))
			return false;

		for (int i = 0; i < strLen; i++) {
			if ((!Character.isWhitespace(str.charAt(i))))
				return true;
		}

		return false;
	}

	/**
	 * 根据HTML属性及值返回HTML属性及值匹配代码 property="name" value="buttonOk" return
	 * name="buttonOk" property="name" value="" return ""
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static String appendHtmlTagProperty(String property, String value) {
		if (isNotEmpty(value)) {
			return new StringBuffer(" ").append(property).append("=")
					.append("\"").append(value).append("\" ").toString();
		} else
			return "";
	}

	/**
	 * 说明：根据传入的字段串及regex获取最后一个字符
	 * 
	 * @param splitStr
	 * @param regex
	 * @return
	 */
	public static String getLastSplitString(String splitStr, String regex) {
		String[] str = splitStr.split(regex);
		return str[str.length - 1];
	}

	/**
	 * 说明：根据传入的数组字段串及regex获取最后一个字符串数组
	 * 
	 * @param splitStrArray
	 * @param regex
	 * @return
	 */
	public static String[] getLastSplitStringArray(String[] splitStrArray,
			String regex) {
		String[] str = new String[splitStrArray.length];
		for (int i = 0; i < splitStrArray.length; i++) {
			str[i] = getLastSplitString(splitStrArray[i], regex);
		}
		return str;
	}

	// /**
	// * 说明：将数组转换成String
	// * @param array
	// * @param seperator
	// * @return
	// */
	// public static String arrayToString(String[] array,String seperator){
	// if(isArrayEmpty(array))
	// return EMPTY;
	// StringBuffer str = new StringBuffer(array[0]);
	// for(int i=1;i<array.length;i++){
	// str.append(seperator).append(array[i]);
	// }
	// return str.toString();
	// }

	/**
	 * 说明：将数组转换成String
	 * 
	 * @param array
	 * @param seperator
	 * @return
	 */
	public static String arrayToString(Object[] array, String seperator) {
		if (isArrayEmpty(array))
			return EMPTY;
		StringBuffer str = new StringBuffer(String.valueOf(array[0]));
		for (int i = 1; i < array.length; i++) {
			str.append(seperator).append(String.valueOf(array[i]));
		}
		return str.toString();
	}

	/**
	 * 说明：查询数组里匹配的字符串
	 * 
	 * @param array
	 * @param lastString
	 * @return
	 */
	public static String searchArrayByLastString(String[] array,
			String lastString) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].lastIndexOf(lastString) >= 0)
				return array[i];
		}
		return EMPTY;
	}

	/**
	 * 说明：判断数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isArrayEmpty(Object[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据HTML属性及值返回HTML属性及值匹配代码,如果Value为空则由默认值填充 property="name"
	 * value="buttonOk",defaultValue="btn" return name="buttonOk"
	 * property="name" value="" defaultValue="btn" return name="btn"
	 * 
	 * @param property
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static final String appendHtmlTagProperty(String property,
			String value, String defaultValue) {
		return appendHtmlTagProperty(property, isEmpty(value) ? defaultValue
				: value);
		// if(isNotEmpty(value)){
		// return new StringBuffer(" ")
		// .append(property)
		// .append("=")
		// .append("\"")
		// .append(value)
		// .append("\" ").toString();
		// }else{
		// return new StringBuffer(" ")
		// .append(property)
		// .append("=")
		// .append("\"")
		// .append(defaultValue)
		// .append("\" ").toString();
		// }
	}

	/**
	 * 将数组中的字符转换为一个字符串,分隔符默认为"," String[3] s={"a","b","c"}
	 * StrUtil.convString（s)="a,b,c"
	 */
	static public String converString(String[] strToConv) {
		return converString(strToConv, ",");
	}

	/**
	 * 将数组中的字符转换为一个字符串 String[3] s={"a","b","c"}
	 * StrUtil.convString（s,"@")="a@b@c"
	 */
	static public String converString(String[] strToConv, String seperator) {
		String convStr = "";

		if (strToConv != null) {
			for (int i = 0; i < strToConv.length; i++) {
				if (i > 0) {
					convStr = convStr + seperator;
				}

				convStr = convStr + strToConv[i];
			}
		}

		return convStr;
	}

	/**
	 * 去掉一个字符串中的空格，有非空判断处理； StringUtils.trim(null) = null StringUtils.trim("") =
	 * "" StringUtils.trim("     ") = "" StringUtils.trim("    abc    ") = "abc"
	 */
	public static String trim(String str) {
		return ((str == null) ? null : str.trim());
	}

	/**
	 * 判断两个字符串是否相等，有非空处理。 StringUtils.equals(null, null) = true
	 * StringUtils.equals(null, "abc") = false
	 */
	public static boolean equals(String str1, String str2) {
		return ((str1 == null) ? (str2 == null) : str1.equals(str2));
	}

	/**
	 * 判断两个字符串是否相等，有非空处理。忽略大小写 StringUtils.equalsIgnoreCase(null, null) = true
	 * StringUtils.equalsIgnoreCase(null, "abc") = false
	 * StringUtils.equalsIgnoreCase("abc", null) = false
	 * StringUtils.equalsIgnoreCase("abc", "ABC") = true
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		return ((str1 == null) ? (str2 == null) : str1.equalsIgnoreCase(str2));
	}

	/**
	 * 返回要查找的字符串所在位置，有非空处理 StringUtils.indexOf(null, *) = -1
	 * StringUtils.indexOf(*, null) = -1 StringUtils.indexOf("", "") = 0
	 * StringUtils.indexOf("aabaabaa", "a") = 0 StringUtils.indexOf("aabaabaa",
	 * "b") = 2 StringUtils.indexOf("aabaabaa", "ab") = 1
	 * StringUtils.indexOf("aabaabaa", "") = 0
	 */
	public static int indexOf(String str, String searchStr) {
		if ((str == null) || (searchStr == null)) {
			return -1;
		}

		return str.indexOf(searchStr);
	}

	/**
	 * 返回要由指定位置开始查找的字符串所在位置，有非空处理 StringUtils.indexOf("aabaabaa", "a", 0) = 0
	 * StringUtils.indexOf("aabaabaa", "b", 0) = 2
	 * StringUtils.indexOf("aabaabaa", "ab", 0) = 1
	 * StringUtils.indexOf("aabaabaa", "b", 3) = 5
	 * StringUtils.indexOf("aabaabaa", "b", 9) = -1
	 * StringUtils.indexOf("aabaabaa", "b", -1) = 2
	 * StringUtils.indexOf("aabaabaa", "", 2) = 2 StringUtils.indexOf("abc", "",
	 * 9) = 3
	 */
	public static int indexOf(String str, String searchStr, int startPos) {
		if ((str == null) || (searchStr == null)) {
			return -1;
		}

		if ((searchStr.length() == 0) && (startPos >= str.length())) {
			return str.length();
		}

		return str.indexOf(searchStr, startPos);
	}

	/**
	 * 返回指定位置开始的字符串中的所有字符 StringUtils.substring(null, *) = null
	 * StringUtils.substring("", *) = "" StringUtils.substring("abc", 0) = "abc"
	 * StringUtils.substring("abc", 2) = "c" StringUtils.substring("abc", 4) =
	 * "" StringUtils.substring("abc", -2) = "bc" StringUtils.substring("abc",
	 * -4) = "abc"
	 */
	public static String substring(String str, int start) {
		if (str == null) {
			return null;
		}

		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		if (start < 0) {
			start = 0;
		}

		if (start > str.length()) {
			return EMPTY;
		}

		return str.substring(start);
	}

	/**
	 * 返回由开始位置到结束位置之间的子字符串 StringUtils.substring(null, *, *) = null
	 * StringUtils.substring("", * , *) = ""; StringUtils.substring("abc", 0, 2)
	 * = "ab" StringUtils.substring("abc", 2, 0) = ""
	 * StringUtils.substring("abc", 2, 4) = "c" StringUtils.substring("abc", 4,
	 * 6) = "" StringUtils.substring("abc", 2, 2) = ""
	 * StringUtils.substring("abc", -2, -1) = "b" StringUtils.substring("abc",
	 * -4, 2) = "ab"
	 */
	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}

		// handle negatives
		if (end < 0) {
			end = str.length() + end; // remember end is negative
		}

		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		// check length next
		if (end > str.length()) {
			end = str.length();
		}

		// if start is greater than end, return ""
		if (start > end) {
			return EMPTY;
		}

		if (start < 0) {
			start = 0;
		}

		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	/**
	 * 返回指定字符串之前的所有字符 StringUtils.substringBefore(null, *) = null
	 * StringUtils.substringBefore("", *) = ""
	 * StringUtils.substringBefore("abc", "a") = ""
	 * StringUtils.substringBefore("abcba", "b") = "a"
	 * StringUtils.substringBefore("abc", "c") = "ab"
	 * StringUtils.substringBefore("abc", "d") = "abc"
	 * StringUtils.substringBefore("abc", "") = ""
	 * StringUtils.substringBefore("abc", null) = "abc"
	 */
	public static String substringBefore(String str, String separator) {
		if ((str == null) || (separator == null) || (str.length() == 0)) {
			return str;
		}

		if (separator.length() == 0) {
			return EMPTY;
		}

		int pos = str.indexOf(separator);

		if (pos == -1) {
			return str;
		}

		return str.substring(0, pos);
	}

	/**
	 * 返回指定字符串之后的所有字符 StringUtils.substringAfter(null, *) = null
	 * StringUtils.substringAfter("", *) = "" StringUtils.substringAfter(*,
	 * null) = "" StringUtils.substringAfter("abc", "a") = "bc"
	 * StringUtils.substringAfter("abcba", "b") = "cba"
	 * StringUtils.substringAfter("abc", "c") = ""
	 * StringUtils.substringAfter("abc", "d") = ""
	 * StringUtils.substringAfter("abc", "") = "abc"
	 */
	public static String substringAfter(String str, String separator) {
		if ((str == null) || (str.length() == 0)) {
			return str;
		}

		if (separator == null) {
			return EMPTY;
		}

		int pos = str.indexOf(separator);

		if (pos == -1) {
			return EMPTY;
		}

		return str.substring(pos + separator.length());
	}

	/**
	 * 返回最后一个指定字符串之前的所有字符 StringUtils.substringBeforeLast(null, *) = null
	 * StringUtils.substringBeforeLast("", *) = ""
	 * StringUtils.substringBeforeLast("abcba", "b") = "abc"
	 * StringUtils.substringBeforeLast("abc", "c") = "ab"
	 * StringUtils.substringBeforeLast("a", "a") = ""
	 * StringUtils.substringBeforeLast("a", "z") = "a"
	 * StringUtils.substringBeforeLast("a", null) = "a"
	 * StringUtils.substringBeforeLast("a", "") = "a"
	 */
	public static String substringBeforeLast(String str, String separator) {
		if ((str == null) || (separator == null) || (str.length() == 0)
				|| (separator.length() == 0)) {
			return str;
		}

		int pos = str.lastIndexOf(separator);

		if (pos == -1) {
			return str;
		}

		return str.substring(0, pos);
	}

	/**
	 * 返回最后一个指定字符串之后的所有字符 StringUtils.substringAfterLast(null, *) = null
	 * StringUtils.substringAfterLast("", *) = ""
	 * StringUtils.substringAfterLast(*, "") = ""
	 * StringUtils.substringAfterLast(*, null) = ""
	 * StringUtils.substringAfterLast("abc", "a") = "bc"
	 * StringUtils.substringAfterLast("abcba", "b") = "a"
	 * StringUtils.substringAfterLast("abc", "c") = ""
	 * StringUtils.substringAfterLast("a", "a") = ""
	 * StringUtils.substringAfterLast("a", "z") = ""
	 */
	public static String substringAfterLast(String str, String separator) {
		if ((str == null) || (str.length() == 0)) {
			return str;
		}

		if ((separator == null) || (separator.length() == 0)) {
			return EMPTY;
		}

		int pos = str.lastIndexOf(separator);

		if ((pos == -1) || (pos == (str.length() - separator.length()))) {
			return EMPTY;
		}

		return str.substring(pos + separator.length());
	}

	/**
	 * 转换首字母小写，
	 * 
	 * @param str
	 *            StringUtils.uncapitalize(null) = null
	 *            StringUtils.uncapitalize("") = ""
	 *            StringUtils.uncapitalize("Cat") = "cat"
	 *            StringUtils.uncapitalize("CAT") = "cAT"
	 */
	public static String uncapitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuffer(strLen)
				.append(Character.toLowerCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String
	 * returning <code>null</code> if the String is empty ("") after the trim or
	 * if it is <code>null</code>.
	 * 
	 * <p>
	 * The String is trimmed using {@link String#trim()}. Trim removes start and
	 * end characters &lt;= 32. To strip whitespace use
	 * {@link #stripToNull(String)}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.trimToNull(null)          = null
	 * StringUtils.trimToNull("")            = null
	 * StringUtils.trimToNull("     ")       = null
	 * StringUtils.trimToNull("abc")         = "abc"
	 * StringUtils.trimToNull("    abc    ") = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed String, <code>null</code> if only chars &lt;= 32,
	 *         empty or null String input
	 * @since 2.0
	 */
	public static String trimToNull(String str) {
		String ts = trim(str);
		return isEmpty(ts) ? null : ts;
	}

	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String
	 * returning an empty String ("") if the String is empty ("") after the trim
	 * or if it is <code>null</code>.
	 * 
	 * <p>
	 * The String is trimmed using {@link String#trim()}. Trim removes start and
	 * end characters &lt;= 32. To strip whitespace use
	 * {@link #stripToEmpty(String)}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.trimToEmpty(null)          = ""
	 * StringUtils.trimToEmpty("")            = ""
	 * StringUtils.trimToEmpty("     ")       = ""
	 * StringUtils.trimToEmpty("abc")         = "abc"
	 * StringUtils.trimToEmpty("    abc    ") = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed String, or an empty String if <code>null</code> input
	 * @since 2.0
	 */
	public static String trimToEmpty(String str) {
		return str == null ? EMPTY : str.trim();
	}

	public static String trimToEmpty(Object str) {
		return str == null ? EMPTY : str.toString().trim();
	}

	// Stripping
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Strips whitespace from the start and end of a String.
	 * </p>
	 * 
	 * <p>
	 * This is similar to {@link #trim(String)} but removes whitespace.
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.strip(null)     = null
	 * StringUtils.strip("")       = ""
	 * StringUtils.strip("   ")    = ""
	 * StringUtils.strip("abc")    = "abc"
	 * StringUtils.strip("  abc")  = "abc"
	 * StringUtils.strip("abc  ")  = "abc"
	 * StringUtils.strip(" abc ")  = "abc"
	 * StringUtils.strip(" ab c ") = "ab c"
	 * </pre>
	 * 
	 * @param str
	 *            the String to remove whitespace from, may be null
	 * @return the stripped String, <code>null</code> if null String input
	 */
	public static String strip(String str) {
		return strip(str, null);
	}

	/**
	 * <p>
	 * Strips whitespace from the start and end of a String returning
	 * <code>null</code> if the String is empty ("") after the strip.
	 * </p>
	 * 
	 * <p>
	 * This is similar to {@link #trimToNull(String)} but removes whitespace.
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.strip(null)     = null
	 * StringUtils.strip("")       = null
	 * StringUtils.strip("   ")    = null
	 * StringUtils.strip("abc")    = "abc"
	 * StringUtils.strip("  abc")  = "abc"
	 * StringUtils.strip("abc  ")  = "abc"
	 * StringUtils.strip(" abc ")  = "abc"
	 * StringUtils.strip(" ab c ") = "ab c"
	 * </pre>
	 * 
	 * @param str
	 *            the String to be stripped, may be null
	 * @return the stripped String, <code>null</code> if whitespace, empty or
	 *         null String input
	 * @since 2.0
	 */
	public static String stripToNull(String str) {
		if (str == null) {
			return null;
		}
		str = strip(str, null);
		return str.length() == 0 ? null : str;
	}

	/**
	 * <p>
	 * Strips whitespace from the start and end of a String returning an empty
	 * String if <code>null</code> input.
	 * </p>
	 * 
	 * <p>
	 * This is similar to {@link #trimToEmpty(String)} but removes whitespace.
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.strip(null)     = ""
	 * StringUtils.strip("")       = ""
	 * StringUtils.strip("   ")    = ""
	 * StringUtils.strip("abc")    = "abc"
	 * StringUtils.strip("  abc")  = "abc"
	 * StringUtils.strip("abc  ")  = "abc"
	 * StringUtils.strip(" abc ")  = "abc"
	 * StringUtils.strip(" ab c ") = "ab c"
	 * </pre>
	 * 
	 * @param str
	 *            the String to be stripped, may be null
	 * @return the trimmed String, or an empty String if <code>null</code> input
	 * @since 2.0
	 */
	public static String stripToEmpty(String str) {
		return str == null ? EMPTY : strip(str, null);
	}

	/**
	 * <p>
	 * Strips any of a set of characters from the start and end of a String.
	 * This is similar to {@link String#trim()} but allows the characters to be
	 * stripped to be controlled.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. An empty
	 * string ("") input returns the empty string.
	 * </p>
	 * 
	 * <p>
	 * If the stripChars String is <code>null</code>, whitespace is stripped as
	 * defined by {@link Character#isWhitespace(char)}. Alternatively use
	 * {@link #strip(String)}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.strip(null, *)          = null
	 * StringUtils.strip("", *)            = ""
	 * StringUtils.strip("abc", null)      = "abc"
	 * StringUtils.strip("  abc", null)    = "abc"
	 * StringUtils.strip("abc  ", null)    = "abc"
	 * StringUtils.strip(" abc ", null)    = "abc"
	 * StringUtils.strip("  abcyx", "xyz") = "  abc"
	 * </pre>
	 * 
	 * @param str
	 *            the String to remove characters from, may be null
	 * @param stripChars
	 *            the characters to remove, null treated as whitespace
	 * @return the stripped String, <code>null</code> if null String input
	 */
	public static String strip(String str, String stripChars) {
		if (isEmpty(str)) {
			return str;
		}
		str = stripStart(str, stripChars);
		return stripEnd(str, stripChars);
	}

	/**
	 * <p>
	 * Strips any of a set of characters from the start of a String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. An empty
	 * string ("") input returns the empty string.
	 * </p>
	 * 
	 * <p>
	 * If the stripChars String is <code>null</code>, whitespace is stripped as
	 * defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.stripStart(null, *)          = null
	 * StringUtils.stripStart("", *)            = ""
	 * StringUtils.stripStart("abc", "")        = "abc"
	 * StringUtils.stripStart("abc", null)      = "abc"
	 * StringUtils.stripStart("  abc", null)    = "abc"
	 * StringUtils.stripStart("abc  ", null)    = "abc  "
	 * StringUtils.stripStart(" abc ", null)    = "abc "
	 * StringUtils.stripStart("yxabc  ", "xyz") = "abc  "
	 * </pre>
	 * 
	 * @param str
	 *            the String to remove characters from, may be null
	 * @param stripChars
	 *            the characters to remove, null treated as whitespace
	 * @return the stripped String, <code>null</code> if null String input
	 */
	public static String stripStart(String str, String stripChars) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		int start = 0;
		if (stripChars == null) {
			while ((start != strLen)
					&& Character.isWhitespace(str.charAt(start))) {
				start++;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((start != strLen)
					&& (stripChars.indexOf(str.charAt(start)) != -1)) {
				start++;
			}
		}
		return str.substring(start);
	}

	/**
	 * <p>
	 * Strips any of a set of characters from the end of a String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. An empty
	 * string ("") input returns the empty string.
	 * </p>
	 * 
	 * <p>
	 * If the stripChars String is <code>null</code>, whitespace is stripped as
	 * defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.stripEnd(null, *)          = null
	 * StringUtils.stripEnd("", *)            = ""
	 * StringUtils.stripEnd("abc", "")        = "abc"
	 * StringUtils.stripEnd("abc", null)      = "abc"
	 * StringUtils.stripEnd("  abc", null)    = "  abc"
	 * StringUtils.stripEnd("abc  ", null)    = "abc"
	 * StringUtils.stripEnd(" abc ", null)    = " abc"
	 * StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
	 * </pre>
	 * 
	 * @param str
	 *            the String to remove characters from, may be null
	 * @param stripChars
	 *            the characters to remove, null treated as whitespace
	 * @return the stripped String, <code>null</code> if null String input
	 */
	public static String stripEnd(String str, String stripChars) {
		int end;
		if (str == null || (end = str.length()) == 0) {
			return str;
		}

		if (stripChars == null) {
			while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
				end--;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((end != 0)
					&& (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
				end--;
			}
		}
		return str.substring(0, end);
	}

	// StripAll
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Strips whitespace from the start and end of every String in an array.
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <p>
	 * A new array is returned each time, except for length zero. A
	 * <code>null</code> array will return <code>null</code>. An empty array
	 * will return itself. A <code>null</code> array entry will be ignored.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.stripAll(null)             = null
	 * StringUtils.stripAll([])               = []
	 * StringUtils.stripAll(["abc", "  abc"]) = ["abc", "abc"]
	 * StringUtils.stripAll(["abc  ", null])  = ["abc", null]
	 * </pre>
	 * 
	 * @param strs
	 *            the array to remove whitespace from, may be null
	 * @return the stripped Strings, <code>null</code> if null array input
	 */
	public static String[] stripAll(String[] strs) {
		return stripAll(strs, null);
	}

	/**
	 * <p>
	 * Strips any of a set of characters from the start and end of every String
	 * in an array.
	 * </p>
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
	 * 
	 * <p>
	 * A new array is returned each time, except for length zero. A
	 * <code>null</code> array will return <code>null</code>. An empty array
	 * will return itself. A <code>null</code> array entry will be ignored. A
	 * <code>null</code> stripChars will strip whitespace as defined by
	 * {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.stripAll(null, *)                = null
	 * StringUtils.stripAll([], *)                  = []
	 * StringUtils.stripAll(["abc", "  abc"], null) = ["abc", "abc"]
	 * StringUtils.stripAll(["abc  ", null], null)  = ["abc", null]
	 * StringUtils.stripAll(["abc  ", null], "yz")  = ["abc  ", null]
	 * StringUtils.stripAll(["yabcz", null], "yz")  = ["abc", null]
	 * </pre>
	 * 
	 * @param strs
	 *            the array to remove characters from, may be null
	 * @param stripChars
	 *            the characters to remove, null treated as whitespace
	 * @return the stripped Strings, <code>null</code> if null array input
	 */
	public static String[] stripAll(String[] strs, String stripChars) {
		int strsLen;
		if (strs == null || (strsLen = strs.length) == 0) {
			return strs;
		}
		String[] newArr = new String[strsLen];
		for (int i = 0; i < strsLen; i++) {
			newArr[i] = strip(strs[i], stripChars);
		}
		return newArr;
	}

	// IndexOf
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Finds the first index within a String, handling <code>null</code>. This
	 * method uses {@link String#indexOf(int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.indexOf(null, *)         = -1
	 * StringUtils.indexOf("", *)           = -1
	 * StringUtils.indexOf("aabaabaa", 'a') = 0
	 * StringUtils.indexOf("aabaabaa", 'b') = 2
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @return the first index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int indexOf(String str, char searchChar) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.indexOf(searchChar);
	}

	/**
	 * <p>
	 * Finds the first index within a String from a start position, handling
	 * <code>null</code>. This method uses {@link String#indexOf(int, int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>-1</code>. A
	 * negative start position is treated as zero. A start position greater than
	 * the string length returns <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.indexOf(null, *, *)          = -1
	 * StringUtils.indexOf("", *, *)            = -1
	 * StringUtils.indexOf("aabaabaa", 'b', 0)  = 2
	 * StringUtils.indexOf("aabaabaa", 'b', 3)  = 5
	 * StringUtils.indexOf("aabaabaa", 'b', 9)  = -1
	 * StringUtils.indexOf("aabaabaa", 'b', -1) = 2
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @param startPos
	 *            the start position, negative treated as zero
	 * @return the first index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int indexOf(String str, char searchChar, int startPos) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.indexOf(searchChar, startPos);
	}

	// LastIndexOf
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Finds the last index within a String, handling <code>null</code>. This
	 * method uses {@link String#lastIndexOf(int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.lastIndexOf(null, *)         = -1
	 * StringUtils.lastIndexOf("", *)           = -1
	 * StringUtils.lastIndexOf("aabaabaa", 'a') = 7
	 * StringUtils.lastIndexOf("aabaabaa", 'b') = 5
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @return the last index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int lastIndexOf(String str, char searchChar) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.lastIndexOf(searchChar);
	}

	/**
	 * <p>
	 * Finds the last index within a String from a start position, handling
	 * <code>null</code>. This method uses {@link String#lastIndexOf(int, int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>-1</code>. A
	 * negative start position returns <code>-1</code>. A start position greater
	 * than the string length searches the whole string.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.lastIndexOf(null, *, *)          = -1
	 * StringUtils.lastIndexOf("", *,  *)           = -1
	 * StringUtils.lastIndexOf("aabaabaa", 'b', 8)  = 5
	 * StringUtils.lastIndexOf("aabaabaa", 'b', 4)  = 2
	 * StringUtils.lastIndexOf("aabaabaa", 'b', 0)  = -1
	 * StringUtils.lastIndexOf("aabaabaa", 'b', 9)  = 5
	 * StringUtils.lastIndexOf("aabaabaa", 'b', -1) = -1
	 * StringUtils.lastIndexOf("aabaabaa", 'a', 0)  = 0
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @param startPos
	 *            the start position
	 * @return the last index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int lastIndexOf(String str, char searchChar, int startPos) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.lastIndexOf(searchChar, startPos);
	}

	/**
	 * <p>
	 * Finds the last index within a String, handling <code>null</code>. This
	 * method uses {@link String#lastIndexOf(String)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.lastIndexOf(null, *)          = -1
	 * StringUtils.lastIndexOf(*, null)          = -1
	 * StringUtils.lastIndexOf("", "")           = 0
	 * StringUtils.lastIndexOf("aabaabaa", "a")  = 0
	 * StringUtils.lastIndexOf("aabaabaa", "b")  = 2
	 * StringUtils.lastIndexOf("aabaabaa", "ab") = 1
	 * StringUtils.lastIndexOf("aabaabaa", "")   = 8
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @return the last index of the search String, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int lastIndexOf(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return -1;
		}
		return str.lastIndexOf(searchStr);
	}

	/**
	 * <p>
	 * Finds the first index within a String, handling <code>null</code>. This
	 * method uses {@link String#lastIndexOf(String, int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A negative start
	 * position returns <code>-1</code>. An empty ("") search String always
	 * matches unless the start position is negative. A start position greater
	 * than the string length searches the whole string.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.lastIndexOf(null, *, *)          = -1
	 * StringUtils.lastIndexOf(*, null, *)          = -1
	 * StringUtils.lastIndexOf("aabaabaa", "a", 8)  = 7
	 * StringUtils.lastIndexOf("aabaabaa", "b", 8)  = 5
	 * StringUtils.lastIndexOf("aabaabaa", "ab", 8) = 4
	 * StringUtils.lastIndexOf("aabaabaa", "b", 9)  = 5
	 * StringUtils.lastIndexOf("aabaabaa", "b", -1) = -1
	 * StringUtils.lastIndexOf("aabaabaa", "a", 0)  = 0
	 * StringUtils.lastIndexOf("aabaabaa", "b", 0)  = -1
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @param startPos
	 *            the start position, negative treated as zero
	 * @return the first index of the search String, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int lastIndexOf(String str, String searchStr, int startPos) {
		if (str == null || searchStr == null) {
			return -1;
		}
		return str.lastIndexOf(searchStr, startPos);
	}

	// Contains
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks if String contains a search character, handling <code>null</code>.
	 * This method uses {@link String#indexOf(int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>false</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.contains(null, *)    = false
	 * StringUtils.contains("", *)      = false
	 * StringUtils.contains("abc", 'a') = true
	 * StringUtils.contains("abc", 'z') = false
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @return true if the String contains the search character, false if not or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static boolean contains(String str, char searchChar) {
		if (isEmpty(str)) {
			return false;
		}
		return str.indexOf(searchChar) >= 0;
	}

	/**
	 * <p>
	 * Checks if String contains a search String, handling <code>null</code>.
	 * This method uses {@link String#indexOf(int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>false</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.contains(null, *)     = false
	 * StringUtils.contains(*, null)     = false
	 * StringUtils.contains("", "")      = true
	 * StringUtils.contains("abc", "")   = true
	 * StringUtils.contains("abc", "a")  = true
	 * StringUtils.contains("abc", "z")  = false
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @return true if the String contains the search String, false if not or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static boolean contains(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return str.indexOf(searchStr) >= 0;
	}

	public static String join(String seperator, Iterator<?> objects) {
		StringBuffer buf = new StringBuffer();
		if (objects.hasNext())
			buf.append(objects.next());
		while (objects.hasNext()) {
			buf.append(seperator).append(objects.next());
		}
		return buf.toString();
	}
	
	public static String join_quotes(String seperator, String[] values) {
		StringBuffer buf = new StringBuffer();
		boolean isFirst = true;
		
		for (String value : values) {
			if (isFirst) {
				buf.append("'").append(value).append("'");
				isFirst = false;
			} else {
				buf.append(seperator).append("'").append(value).append("'");
			}
		}
		
		return buf.toString();
	}
	
	public static String join(String seperator, Object... objects) {
		StringBuffer buf = new StringBuffer();
	    boolean isFirst = true;
		for (Object object : objects) {
			if (isFirst) {
				buf.append(object);
				isFirst = false;
			}
			else {
				buf.append(seperator).append(object);
			}
		}
		return buf.toString();
	}
	
	public static String join(String seperator, List<Object> objects) {
		StringBuffer buf = new StringBuffer();
	    boolean isFirst = true;
		for (Object object : objects) {
			if (isFirst) {
				buf.append(object == null ? "" : object);
				isFirst = false;
			}
			else {
				buf.append(seperator).append(object == null ? "" : object);
			}
		}
		return buf.toString();
	}
	
	public static String joinForListStr(String seperator, List<String> objects) {
		StringBuffer buf = new StringBuffer();
	    boolean isFirst = true;
		for (String object : objects) {
			if (isFirst) {
				buf.append(object == null ? "" : object);
				isFirst = false;
			}
			else {
				buf.append(seperator).append(object == null ? "" : object);
			}
		}
		return buf.toString();
	}
	
	// Count matches
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Counts how many times the substring appears in the larger String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String input returns <code>0</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.countMatches(null, *)       = 0
	 * StringUtils.countMatches("", *)         = 0
	 * StringUtils.countMatches("abba", null)  = 0
	 * StringUtils.countMatches("abba", "")    = 0
	 * StringUtils.countMatches("abba", "a")   = 2
	 * StringUtils.countMatches("abba", "ab")  = 1
	 * StringUtils.countMatches("abba", "xxx") = 0
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param sub
	 *            the substring to count, may be null
	 * @return the number of occurrences, 0 if either String is
	 *         <code>null</code>
	 */
	public static int countMatches(String str, String sub) {
		if (isEmpty(str) || isEmpty(sub)) {
			return 0;
		}
		int count = 0;
		int idx = 0;
		while ((idx = str.indexOf(sub, idx)) != -1) {
			count++;
			idx += sub.length();
		}
		return count;
	}

	/**
	 * <p>
	 * 把字符串的部分替换为另一个字符串
	 * </p>
	 * replace("aaCCbb","CC","TTTT"); //return "aaTTTTbb"
	 * 
	 * @param value
	 *            待替换字符串.
	 * @param key
	 *            待替换部分.
	 * @param replaceValue
	 *            目标值.
	 */
	public static String replace(String value, String key, String replaceValue) {
		if (value != null && key != null && replaceValue != null) {
			int pos = value.indexOf(key);
			if (pos >= 0) {
				int length = value.length();
				int start = pos;
				int end = pos + key.length();

				if (length == key.length()) {
					value = replaceValue;
				} else if (end == length) {
					value = value.substring(0, start) + replaceValue;
				} else {
					value = value.substring(0, start) + replaceValue
							+ replace(value.substring(end), key, replaceValue);
				}
			}
		}
		return value;
	}

	/**
	 * 获取value中分隔符delimiter的个数
	 * @param value
	 * @param delimiter
	 * @return
	 */
	public static int getDelimiterNum(String value, char delimiter) {
		int num = 0;
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (delimiter == chars[i]) {
				num++;
			}
		}
		return num;
	}
	
	/**
     * 功能: Count a char in the whole String.
     * @param sToSearch
     * @param cSearchFor
     * @return int 0 means not found.
     */
    public static int countCharInString(String sToSearch, char cSearchFor) {
        if (sToSearch == null) return 0;
        int curBegIndex = 0, iLength = 1, iCounter = 0;
        while ((curBegIndex = sToSearch.indexOf(cSearchFor, curBegIndex) + iLength) > 0) {

            iCounter++;
        }
        return iCounter;
    }
    
    /**
     * 功能: null return Dft, else trimed string.
     * @param string
     * @param Dft
     * @return
     */
    public static String trimString(String string, String Dft) {
        string = (null == string) ? (Dft) : (string.trim());
        return string;
    }
	
}