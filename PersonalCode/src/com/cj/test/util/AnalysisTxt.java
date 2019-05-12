package com.cj.test.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import com.cj.test.pojo.ZcLawIteminfo;

public class AnalysisTxt {

	public static void main(String[] args) {
		try {
			StructData("");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 结构化文件
	 * 
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static void StructData(String fileContent) throws UnsupportedEncodingException, IOException {
		// 结构化数据
		ZcLawIteminfo record = null;

		if (fileContent != null && fileContent != "") {
			String[] contentStr = fileContent.split("&&");
			for (String lineTxt : contentStr) {
				if (lineTxt != null && lineTxt != "") {
					System.out.println("lineTxt:" + lineTxt);
					int itemLevel = matchTxtLevel(lineTxt);
					operateData(record, itemLevel, lineTxt);
				}
			}
		} else {
			System.out.println("不存在文件内容");
		}
	}

	// 插入或者更新数据
	public static void operateData(ZcLawIteminfo record, int itemLevel, String lineTxt) {
		switch (itemLevel) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			String itemName = "";

			record = new ZcLawIteminfo();
			record.setRowguid(UUID.randomUUID().toString());
			record.setItemLevel(itemLevel);
			if (itemLevel == 0) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("编") + 1);
			} else if (itemLevel == 1) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("章") + 1);
			} else if (itemLevel == 2) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("节") + 1);
			} else if (itemLevel == 3) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("条") + 1);
			} else if (itemLevel == 4) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("款") + 1);
			} else if (itemLevel == 5) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("项") + 1);
			} else if (itemLevel == 6) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("目") + 1);
			}

			break;
		case 7:

			if (record != null) {
				if (record.getItemContent() != null && record.getItemContent() != "") {
					record.setItemContent(record.getItemContent() + "&&" + lineTxt);
				} else {
					record.setItemContent(lineTxt);
				}

			}
			break;
		case 8:
			break;
		default:
			break;
		}
	}

	// 匹配层级
	public static int matchTxtLevel(String lineTxt) {
		String txt = lineTxt.trim();
		if (txt != null && txt != "") {
			// 编:0
			if (txt.indexOf("第") != -1 && txt.indexOf("编") != -1 && txt.startsWith("第")
					&& txt.substring(txt.indexOf("第"), txt.indexOf("编")).length() <= 7) {
				return 0;
			}
			// 章:1
			else if (txt.indexOf("第") != -1 && txt.indexOf("章") != -1 && txt.startsWith("第")
					&& txt.substring(txt.indexOf("第"), txt.indexOf("章")).length() <= 7) {
				return 1;
			}
			// 节:2
			else if (txt.indexOf("第") != -1 && txt.indexOf("节") != -1 && txt.startsWith("第")
					&& txt.substring(txt.indexOf("第"), txt.indexOf("节")).length() <= 7) {
				return 2;
			}
			// 条:3
			else if (txt.indexOf("第") != -1 && txt.indexOf("条") != -1 && txt.startsWith("第")
					&& txt.substring(txt.indexOf("第"), txt.indexOf("条")).length() <= 7) {
				return 3;
			}
			// 款:4
			else if (txt.indexOf("第") != -1 && txt.indexOf("款") != -1 && txt.startsWith("第")
					&& txt.substring(txt.indexOf("第"), txt.indexOf("款")).length() <= 7) {
				return 4;
			}
			// 项:5
			else if (txt.indexOf("第") != -1 && txt.indexOf("项") != -1 && txt.startsWith("第")
					&& txt.substring(txt.indexOf("第"), txt.indexOf("项")).length() <= 7) {
				return 5;
			}
			// 目:6
			else if (txt.indexOf("第") != -1 && txt.indexOf("目") != -1 && txt.startsWith("第")
					&& txt.substring(txt.indexOf("第"), txt.indexOf("目")).length() <= 7) {
				return 6;
			}
			// txt无满足内容，添加到上一行末尾
			else {
				return 7;
			}
		} else {
			// 当前行为空
			return 8;
		}
	}

}
