package com.cj.test.util;

import java.util.UUID;
import java.util.regex.Pattern;

public class TestString {

	public static void main(String[] args) {
		String content = "Ŀ ¼&&��һ�� ����&&�ڶ��� ��������ò����&&������ ��·�����������ͻ���������ò&&����&&������ ���蹤��ʩ��������ò�ͽ���&&�������ù���&&������ �����桢�޺�Ƶ���ʩ��ò����&&������ Υ��������ֹ�Ͳ鴦&&������ ��������&&�ڰ��� ����&&��һ�¡�����&&��һ����Ϊ�˼�ǿ�������ݹ���������ࡢ�����ĳ��й�������������ٽ�����������������;����������裬�����йط��ɡ����棬��ϱ���ʵ�ʣ��ƶ���������&&�ڶ����������������ڱ�������������ʵ�г��л����������";
		String zhangRex = "��.{1,7}��"; 
		
		System.out.println(Pattern.compile(zhangRex).matcher(content).find());
	}

	// ������߸�������
	public static void operateData(int itemLevel, String lineTxt) {
		switch (itemLevel) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			String itemName = "";
			if (itemLevel == 0) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("��") + 1);
			} else if (itemLevel == 1) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("��") + 1);
			} else if (itemLevel == 2) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("��") + 1);
			} else if (itemLevel == 3) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("��") + 1);
				String rowguid = UUID.randomUUID().toString();
				String name = itemName;
				//
				String con = lineTxt.substring(lineTxt.indexOf("��") + 1, lineTxt.length()).trim();
				if (con.indexOf("&&") != -1) {
					String[] str = con.split("&&");
					for (int i = 0; i < str.length; i++) {
						//��Ĵ���
						if (str[i] != "" && !str[i].startsWith("��")) {
							int level = 4;
							String rowguid1 = UUID.randomUUID().toString();
							String name1 = "��" + formatInteger(i + 1) + "��";
							String content = str[i];
							String parentguid = rowguid;
							System.out.println("level=" + level + ",rowguid=" + rowguid1 + ",name1=" + name1
									+ ",content=" + content + ",parentguid=" + parentguid);

						} 
						//�������������ݷŵ������������
						else {
							
						}
					}
				}else {
					//����һ��
				}
			} else if (itemLevel == 4) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("��") + 1);
			} else if (itemLevel == 5) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("��") + 1);
			} else if (itemLevel == 6) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("Ŀ") + 1);
			}

			break;
		case 7:

			break;
		case 8:
			break;
		default:
			break;
		}
	}

	static String[] units = { "", "ʮ", "��", "ǧ" };
	static char[] numArray = { '��', 'һ', '��', '��', '��', '��', '��', '��', '��', '��' };

	public static String formatInteger(int num) {
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
}
