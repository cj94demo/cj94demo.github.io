package com.cj.test.util;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import java.util.List;
import java.util.stream.Collectors;


/**
 * ���ķִʹ�����*/
public class Tokenizer {

    /**
     * �ִ�*/
    public static List<Word> segment(String sentence) {

        //1�� ����HanLP������Ȼ���Դ����б�׼�ִʽ��зִ�
        List<Term> termList = HanLP.segment(sentence);

        //�������̨��ӡ��Ϣ�������������
        System.out.println(termList.toString());

        //2�����·�װ��Word�����У�term.word����ִʺ�Ĵ��term.nature����ĴʵĴ��ԣ�
        return termList.stream().map(term -> new Word(term.word, term.nature.toString())).collect(Collectors.toList());
    }
}