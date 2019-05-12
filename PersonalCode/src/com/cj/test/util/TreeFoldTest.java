package com.cj.test.util;

import java.util.ArrayList;
import java.util.List;

public class TreeFoldTest {
	public TreeFoldTest(String title) {
		this.title = title;
	}

	private String title;
	private List<TreeFoldTest> children = new ArrayList<>();

	public void addChild(TreeFoldTest f) {
		children.add(f);
	}

	public String toString(String lftStr, String append) {
		StringBuilder b = new StringBuilder();
		b.append(append + title);
		b.append("\n");
		if (children.size() > 0) {
			for (int i = 0; i < children.size() - 1; i++) {
				b.append(lftStr + children.get(i).toString(lftStr + "│", "├"));
			}
			b.append(lftStr + children.get(children.size() - 1).toString(lftStr + " ", "└"));
		}
		return b.toString();
	}

	public static void main(String[] args) {
		TreeFoldTest root = new TreeFoldTest("菜单列表");
		TreeFoldTest f1 = new TreeFoldTest("开始菜单");
		root.addChild(f1);
		TreeFoldTest f1_1 = new TreeFoldTest("程序");
		f1.addChild(f1_1);
		TreeFoldTest f1_1_1 = new TreeFoldTest("附件");
		f1_1.addChild(f1_1_1);
		TreeFoldTest f1_1_1_1 = new TreeFoldTest("娱乐");
		f1_1_1.addChild(f1_1_1_1);
		TreeFoldTest f1_1_1_2 = new TreeFoldTest("娱乐2");
		f1_1_1.addChild(f1_1_1_2);
		TreeFoldTest f1_2 = new TreeFoldTest("辅助工具");
		f1.addChild(f1_2);
		TreeFoldTest f2 = new TreeFoldTest("My Documents ");
		root.addChild(f2);
		TreeFoldTest f3 = new TreeFoldTest("My Documents2 ");
		root.addChild(f3);
		System.out.println(root.toString(" ", ""));
	}

	public List<TreeFoldTest> getChildren() {
		return children;
	}

	public void setChildren(List<TreeFoldTest> children) {
		this.children = children;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}