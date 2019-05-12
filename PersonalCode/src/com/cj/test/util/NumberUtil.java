package com.cj.test.util;


public class NumberUtil {

	public static void main(String[] args) {
//		int count = chineseNumber2Int("第五百四十一章能好受多了是");
//		System.out.println(count);
		String str = "五百四十一";
		boolean flag = strIsChineseNumber(str);
		System.out.println(flag);
	}

	/**
	 * 数字转成大写数字
	 * 
	 * @param num
	 * @return
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */

	public String formatInteger(int num) {
		String[] units = { "", "十", "百", "千" };
		char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };
		char[] val = String.valueOf(num).toCharArray();
		int len = val.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			String m = val[i] + "";
			int n = Integer.valueOf(m);
			boolean isZero = n == 0;
			String unit = units[(len - 1) - i];
			if (isZero) {
				if ('0' == val[i - 1]) {
					// not need process if the last digital bits is 0
					continue;
				} else {
					// no unit for 0
					sb.append(numArray[n]);
				}
			} else {
				sb.append(numArray[n]);
				sb.append(unit);
			}
		}
		return sb.toString();
	}

	/**
	 * 中文數字转阿拉伯数组
	 * 
	 * @param chineseNumber
	 * @return
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static int chineseNumber2Int(String chineseNumber) {
		int result = 0;
		int temp = 1;// 存放一个单位的数字如：十万
		int count = 0;// 判断是否有chArr
		char[] cnArr = new char[] { '一', '二', '三', '四', '五', '六', '七', '八', '九', '零' };
		char[] chArr = new char[] { '十', '百', '千', '万', '亿' };
		for (int i = 0; i < chineseNumber.length(); i++) {
			boolean b = true;// 判断是否是chArr
			char c = chineseNumber.charAt(i);
			for (int j = 0; j < cnArr.length; j++) {// 非单位，即数字
				if (c == cnArr[j]) {
					if (0 != count) {// 添加下一个单位之前，先把上一个单位值添加到结果中
						result += temp;
						temp = 1;
						count = 0;
					}
					// 下标+1，就是对应的值
					temp = j + 1;
					b = false;
					break;
				}
			}
			if (b) {// 单位{'十','百','千','万','亿'}
				for (int j = 0; j < chArr.length; j++) {
					if (c == chArr[j]) {
						switch (j) {
						case 0:
							temp *= 10;
							break;
						case 1:
							temp *= 100;
							break;
						case 2:
							temp *= 1000;
							break;
						case 3:
							temp *= 10000;
							break;
						case 4:
							temp *= 100000000;
							break;
						default:
							break;
						}
						count++;
					}
				}
			}
			if (i == chineseNumber.length() - 1) {// 遍历到最后一个字符
				result += temp;
			}
		}
		return result;
	}

	/**
	 * 判断字符串中是否只有中文数字
	 * 
	 * @param chineseNumber
	 * @return
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean strIsChineseNumber(String chineseNumber) {
		String cnStr = "零一二三四五六七八九十百千万";
		for (int i = 0; i < chineseNumber.length(); i++) {
			char c = chineseNumber.charAt(i);
			System.out.println("c:"+c);
			if(cnStr.indexOf(c) == -1) {
				return false;
			}
		}
		return true;
	}

}
