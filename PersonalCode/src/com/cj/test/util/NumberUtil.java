package com.cj.test.util;


public class NumberUtil {

	public static void main(String[] args) {
//		int count = chineseNumber2Int("�������ʮһ���ܺ��ܶ�����");
//		System.out.println(count);
		String str = "�����ʮһ";
		boolean flag = strIsChineseNumber(str);
		System.out.println(flag);
	}

	/**
	 * ����ת�ɴ�д����
	 * 
	 * @param num
	 * @return
	 * @exception/throws [Υ������] [Υ��˵��]
	 * @see [�ࡢ��#��������#��Ա]
	 */

	public String formatInteger(int num) {
		String[] units = { "", "ʮ", "��", "ǧ" };
		char[] numArray = { '��', 'һ', '��', '��', '��', '��', '��', '��', '��', '��' };
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
	 * ���Ĕ���ת����������
	 * 
	 * @param chineseNumber
	 * @return
	 * @exception/throws [Υ������] [Υ��˵��]
	 * @see [�ࡢ��#��������#��Ա]
	 */
	public static int chineseNumber2Int(String chineseNumber) {
		int result = 0;
		int temp = 1;// ���һ����λ�������磺ʮ��
		int count = 0;// �ж��Ƿ���chArr
		char[] cnArr = new char[] { 'һ', '��', '��', '��', '��', '��', '��', '��', '��', '��' };
		char[] chArr = new char[] { 'ʮ', '��', 'ǧ', '��', '��' };
		for (int i = 0; i < chineseNumber.length(); i++) {
			boolean b = true;// �ж��Ƿ���chArr
			char c = chineseNumber.charAt(i);
			for (int j = 0; j < cnArr.length; j++) {// �ǵ�λ��������
				if (c == cnArr[j]) {
					if (0 != count) {// �����һ����λ֮ǰ���Ȱ���һ����λֵ��ӵ������
						result += temp;
						temp = 1;
						count = 0;
					}
					// �±�+1�����Ƕ�Ӧ��ֵ
					temp = j + 1;
					b = false;
					break;
				}
			}
			if (b) {// ��λ{'ʮ','��','ǧ','��','��'}
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
			if (i == chineseNumber.length() - 1) {// ���������һ���ַ�
				result += temp;
			}
		}
		return result;
	}

	/**
	 * �ж��ַ������Ƿ�ֻ����������
	 * 
	 * @param chineseNumber
	 * @return
	 * @exception/throws [Υ������] [Υ��˵��]
	 * @see [�ࡢ��#��������#��Ա]
	 */
	public static boolean strIsChineseNumber(String chineseNumber) {
		String cnStr = "��һ�����������߰˾�ʮ��ǧ��";
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
