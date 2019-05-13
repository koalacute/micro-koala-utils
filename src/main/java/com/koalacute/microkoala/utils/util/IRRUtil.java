package com.koalacute.microkoala.utils.util;

import java.util.List;

public class IRRUtil {
		
	/** 最小差异 */
	public static final double MINDIF = 0.00000001;

	/**
	 * @desc 使用方法参考main方法
	 * @param cashFlow
	 *            资金流
	 * @return 收益率
	 */
	public static double getIrr(List<Double> cashFlow) {
		int LOOPNUM = 1000;
		double flowOut = cashFlow.get(0);
		double minValue = 0d;
		double maxValue = 1d;
		double testValue = 0d;
		while (LOOPNUM > 0) {
			testValue = (minValue + maxValue) / 2;
			double npv = NPV(cashFlow, testValue);
			if (Math.abs(flowOut + npv) < MINDIF) {
				break;
			} else if (Math.abs(flowOut) > npv) {
				maxValue = testValue;
			} else {
				minValue = testValue;
			}
			LOOPNUM--;
		}
		return testValue;
	}

	public static double NPV(List<Double> flowInArr, double rate) {
		double npv = 0;
		for (int i = 1; i < flowInArr.size(); i++) {
			npv += flowInArr.get(i) / Math.pow(1 + rate, i);
		}
		return npv;
	}

}
