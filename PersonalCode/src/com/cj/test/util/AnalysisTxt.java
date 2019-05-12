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
	 * �ṹ���ļ�
	 * 
	 * @exception/throws [Υ������] [Υ��˵��]
	 * @see [�ࡢ��#��������#��Ա]
	 */
	public static void StructData(String fileContent) throws UnsupportedEncodingException, IOException {
		// �ṹ������
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
			System.out.println("�������ļ�����");
		}
	}

	// ������߸�������
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
				itemName = lineTxt.substring(0, lineTxt.indexOf("��") + 1);
			} else if (itemLevel == 1) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("��") + 1);
			} else if (itemLevel == 2) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("��") + 1);
			} else if (itemLevel == 3) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("��") + 1);
			} else if (itemLevel == 4) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("��") + 1);
			} else if (itemLevel == 5) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("��") + 1);
			} else if (itemLevel == 6) {
				itemName = lineTxt.substring(0, lineTxt.indexOf("Ŀ") + 1);
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

	// ƥ��㼶
	public static int matchTxtLevel(String lineTxt) {
		String txt = lineTxt.trim();
		if (txt != null && txt != "") {
			// ��:0
			if (txt.indexOf("��") != -1 && txt.indexOf("��") != -1 && txt.startsWith("��")
					&& txt.substring(txt.indexOf("��"), txt.indexOf("��")).length() <= 7) {
				return 0;
			}
			// ��:1
			else if (txt.indexOf("��") != -1 && txt.indexOf("��") != -1 && txt.startsWith("��")
					&& txt.substring(txt.indexOf("��"), txt.indexOf("��")).length() <= 7) {
				return 1;
			}
			// ��:2
			else if (txt.indexOf("��") != -1 && txt.indexOf("��") != -1 && txt.startsWith("��")
					&& txt.substring(txt.indexOf("��"), txt.indexOf("��")).length() <= 7) {
				return 2;
			}
			// ��:3
			else if (txt.indexOf("��") != -1 && txt.indexOf("��") != -1 && txt.startsWith("��")
					&& txt.substring(txt.indexOf("��"), txt.indexOf("��")).length() <= 7) {
				return 3;
			}
			// ��:4
			else if (txt.indexOf("��") != -1 && txt.indexOf("��") != -1 && txt.startsWith("��")
					&& txt.substring(txt.indexOf("��"), txt.indexOf("��")).length() <= 7) {
				return 4;
			}
			// ��:5
			else if (txt.indexOf("��") != -1 && txt.indexOf("��") != -1 && txt.startsWith("��")
					&& txt.substring(txt.indexOf("��"), txt.indexOf("��")).length() <= 7) {
				return 5;
			}
			// Ŀ:6
			else if (txt.indexOf("��") != -1 && txt.indexOf("Ŀ") != -1 && txt.startsWith("��")
					&& txt.substring(txt.indexOf("��"), txt.indexOf("Ŀ")).length() <= 7) {
				return 6;
			}
			// txt���������ݣ���ӵ���һ��ĩβ
			else {
				return 7;
			}
		} else {
			// ��ǰ��Ϊ��
			return 8;
		}
	}

}
