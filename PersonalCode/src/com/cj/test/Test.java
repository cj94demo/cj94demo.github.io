package com.cj.test;

import com.cj.test.util.CosineSimilarity;

public class Test {
	public static final String content1 = "ȫ����������᳣��ίԱ������޸ġ��л����񹲺͹�ר�������ľ���(2000)";

	public static final String content2 = "���л����񹲺͹�ר������";
	public static void main(String[] args) {

		double score = CosineSimilarity.getSimilarity(content1, content2);
		System.out.println("���ƶȣ�" + score);

		score = CosineSimilarity.getSimilarity(content1, content1);
		System.out.println("���ƶȣ�" + score);
	}
}
