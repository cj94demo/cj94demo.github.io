package com.cj.test.util;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cj.test.dataStruct.AtomicFloat;

/**
 * �ж���ʽ���������ƶȣ�ͨ���������������ļн�����ֵ���������ǵ����ƶ� ���Ҽн�ԭ�� ����a=(x1,y1),����b=(x2,y2)
 * similarity=a.b/|a|*|b| a.b=x1x2+y1y2
 * |a|=����[(x1)^2+(y1)^2],|b|=����[(x2)^2+(y2)^2]
 */
public class CosineSimilarity {
	protected static final Logger LOGGER = LoggerFactory.getLogger(CosineSimilarity.class);

	/**
	 * 1�����������ַ��������ƶ�
	 */
	public static double getSimilarity(String text1, String text2) {

		// ���wei�գ������ַ�����Ϊ0���������ȫ��ͬ
		if (StringUtils.isBlank(text1) && StringUtils.isBlank(text2)) {
			return 1.0;
		}
		// ���һ��Ϊ0���߿գ�һ����Ϊ����˵����ȫ������
		if (StringUtils.isBlank(text1) || StringUtils.isBlank(text2)) {
			return 0.0;
		}
		// ���������������ַ�������ǵ�Ȼ����1�ˣ������Ϊ������Ҳ�ִʼ���һ�£�����ע�͵��ˣ�
//        if (text1.equalsIgnoreCase(text2)) {
//            return 1.0;
//        }
		// ��һ�������зִ�
		List<Word> words1 = Tokenizer.segment(text1);
		List<Word> words2 = Tokenizer.segment(text2);

		return getSimilarity(words1, words2);
	}

	/**
	 * 2�����ڼ���������ƶȱ���С�������λ
	 */
	public static double getSimilarity(List<Word> words1, List<Word> words2) {

		double score = getSimilarityImpl(words1, words2);

		// (int) (score * 1000000 + 0.5)��ʵ������С�������λ
		// ,��Ϊ1034234.213ǿ��ת��������1034234������ǿ��ת�����0.5�͵�����������
		score = (int) (score * 1000000 + 0.5) / (double) 1000000;

		return score;
	}

	/**
	 * �ı����ƶȼ��� �ж���ʽ���������ƶȣ�ͨ���������������ļн�����ֵ���������ǵ����ƶ� ���Ҽн�ԭ�� ����a=(x1,y1),����b=(x2,y2)
	 * similarity=a.b/|a|*|b| a.b=x1x2+y1y2
	 * |a|=����[(x1)^2+(y1)^2],|b|=����[(x2)^2+(y2)^2]
	 */
	public static double getSimilarityImpl(List<Word> words1, List<Word> words2) {

		// ��ÿһ��Word��������Զ�ע��weight��Ȩ�أ�����ֵ
		taggingWeightByFrequency(words1, words2);

		// �ڶ����������Ƶ
		// ͨ����һ����ÿ��Word������Ȩ��ֵ����ô�ڷ�װ��map�У�key�Ǵʣ�value�Ǹôʳ��ֵĴ�������Ȩ�أ���
		Map<String, Float> weightMap1 = getFastSearchMap(words1);
		Map<String, Float> weightMap2 = getFastSearchMap(words2);

		// �����дʶ�װ��set������
		Set<Word> words = new HashSet<>();
		words.addAll(words1);
		words.addAll(words2);

		AtomicFloat ab = new AtomicFloat();// a.b
		AtomicFloat aa = new AtomicFloat();// |a|��ƽ��
		AtomicFloat bb = new AtomicFloat();// |b|��ƽ��

		// ��������д����Ƶ����������м���
		words.parallelStream().forEach(word -> {
			// ��ͬһ����a��b�������ϳ��ֵĴ˴�
			Float x1 = weightMap1.get(word.getName());
			Float x2 = weightMap2.get(word.getName());
			if (x1 != null && x2 != null) {
				// x1x2
				float oneOfTheDimension = x1 * x2;
				// +
				ab.addAndGet(oneOfTheDimension);
			}
			if (x1 != null) {
				// (x1)^2
				float oneOfTheDimension = x1 * x1;
				// +
				aa.addAndGet(oneOfTheDimension);
			}
			if (x2 != null) {
				// (x2)^2
				float oneOfTheDimension = x2 * x2;
				// +
				bb.addAndGet(oneOfTheDimension);
			}
		});
		// |a| ��aa����
		double aaa = Math.sqrt(aa.doubleValue());
		// |b| ��bb����
		double bbb = Math.sqrt(bb.doubleValue());

		// ʹ��BigDecimal��֤��ȷ���㸡����
		// double aabb = aaa * bbb;
		BigDecimal aabb = BigDecimal.valueOf(aaa).multiply(BigDecimal.valueOf(bbb));

		// similarity=a.b/|a|*|b|
		// divide����˵����aabb������,9��ʾС�������9λ�����һ����ʾ�ñ�׼���������뷨
		double cos = BigDecimal.valueOf(ab.get()).divide(aabb, 9, BigDecimal.ROUND_HALF_UP).doubleValue();
		return cos;
	}

	/**
	 * ��ÿһ��Word��������Զ�ע��weight��Ȩ�أ�����ֵ
	 */
	protected static void taggingWeightByFrequency(List<Word> words1, List<Word> words2) {
		if (words1.get(0).getWeight() != null && words2.get(0).getWeight() != null) {
			return;
		}
		// ��Ƶͳ�ƣ�key�Ǵʣ�value�Ǹô�����ξ����г��ֵĴ�����
		Map<String, AtomicInteger> frequency1 = getFrequency(words1);
		Map<String, AtomicInteger> frequency2 = getFrequency(words2);

		// �����DEBUGģʽ�����Ƶͳ����Ϣ
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("��Ƶͳ��1��\n{}", getWordsFrequencyString(frequency1));
			LOGGER.debug("��Ƶͳ��2��\n{}", getWordsFrequencyString(frequency2));
		}
		// ��עȨ�أ��ôʳ��ֵĴ�����
		words1.parallelStream().forEach(word -> word.setWeight(frequency1.get(word.getName()).floatValue()));
		words2.parallelStream().forEach(word -> word.setWeight(frequency2.get(word.getName()).floatValue()));
	}

	/**
	 * ͳ�ƴ�Ƶ
	 * 
	 * @return ��Ƶͳ��ͼ
	 */
	private static Map<String, AtomicInteger> getFrequency(List<Word> words) {

		Map<String, AtomicInteger> freq = new HashMap<>();
		// �ⲽ��˧Ŷ
		words.forEach(i -> freq.computeIfAbsent(i.getName(), k -> new AtomicInteger()).incrementAndGet());
		return freq;
	}

	/**
	 * �������Ƶͳ����Ϣ
	 */
	private static String getWordsFrequencyString(Map<String, AtomicInteger> frequency) {
		StringBuilder str = new StringBuilder();
		if (frequency != null && !frequency.isEmpty()) {
			AtomicInteger integer = new AtomicInteger();
			frequency.entrySet().stream().sorted((a, b) -> b.getValue().get() - a.getValue().get())
					.forEach(i -> str.append("\t").append(integer.incrementAndGet()).append("��").append(i.getKey())
							.append("=").append(i.getValue()).append("\n"));
		}
		str.setLength(str.length() - 1);
		return str.toString();
	}

	/**
	 * ����Ȩ�ؿ�����������
	 */
	protected static Map<String, Float> getFastSearchMap(List<Word> words) {
		if (CollectionUtils.isEmpty(words)) {
			return Collections.emptyMap();
		}
		Map<String, Float> weightMap = new ConcurrentHashMap<>(words.size());

		words.parallelStream().forEach(i -> {
			if (i.getWeight() != null) {
				weightMap.put(i.getName(), i.getWeight());
			} else {
				LOGGER.error("no word weight info:" + i.getName());
			}
		});
		return weightMap;
	}

}
