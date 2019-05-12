package com.cj.test.util;

import java.util.UUID;
import java.util.regex.Pattern;

public class TestString {

	public static void main(String[] args) {
		String content = "目 录&&第一章 总则&&第二章 建筑物容貌管理&&第三章 道路、公共场所和机动车辆容貌&&管理&&第四章 建设工程施工场地容貌和建筑&&垃圾处置管理&&第五章 户外广告、霓虹灯等设施容貌管理&&第六章 违法建设制止和查处&&第七章 法律责任&&第八章 附则&&第一章　总则&&第一条　为了加强城市市容管理，创造清洁、优美的城市工作、生活环境，促进社会主义物质文明和精神文明建设，根据有关法律、法规，结合本市实际，制定本条例。&&第二条　本条例适用于本市行政区域内实行城市化管理的区域。";
		String zhangRex = "第.{1,7}章"; 
		
		System.out.println(Pattern.compile(zhangRex).matcher(content).find());
	}

	// 插入或者更新数据
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
				itemName = lineTxt.substring(0, lineTxt.indexOf("编") + 1);
			} else if (itemLevel == 1) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("章") + 1);
			} else if (itemLevel == 2) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("节") + 1);
			} else if (itemLevel == 3) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("条") + 1);
				String rowguid = UUID.randomUUID().toString();
				String name = itemName;
				//
				String con = lineTxt.substring(lineTxt.indexOf("条") + 1, lineTxt.length()).trim();
				if (con.indexOf("&&") != -1) {
					String[] str = con.split("&&");
					for (int i = 0; i < str.length; i++) {
						//款的处理
						if (str[i] != "" && !str[i].startsWith("（")) {
							int level = 4;
							String rowguid1 = UUID.randomUUID().toString();
							String name1 = "第" + formatInteger(i + 1) + "款";
							String content = str[i];
							String parentguid = rowguid;
							System.out.println("level=" + level + ",rowguid=" + rowguid1 + ",name1=" + name1
									+ ",content=" + content + ",parentguid=" + parentguid);

						} 
						//存在项，将项的内容放到款的内容里面
						else {
							
						}
					}
				}else {
					//保存一条
				}
			} else if (itemLevel == 4) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("款") + 1);
			} else if (itemLevel == 5) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("项") + 1);
			} else if (itemLevel == 6) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("目") + 1);
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

	static String[] units = { "", "十", "百", "千" };
	static char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };

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
