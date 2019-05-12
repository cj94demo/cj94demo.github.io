package com.cj.test;

import com.cj.test.util.CosineSimilarity;

public class Test {
	public static final String content1 = "全国人民代表大会常务委员会关于修改《中华人民共和国专利法》的决定(2000)";

	public static final String content2 = "《中华人民共和国专利法》";
	public static void main(String[] args) {

		double score = CosineSimilarity.getSimilarity(content1, content2);
		System.out.println("相似度：" + score);

		score = CosineSimilarity.getSimilarity(content1, content1);
		System.out.println("相似度：" + score);
	}
}
