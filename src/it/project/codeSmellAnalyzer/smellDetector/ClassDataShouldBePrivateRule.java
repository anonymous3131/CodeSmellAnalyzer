package it.project.codeSmellAnalyzer.smellDetector;

import it.project.codeSmellAnalyzer.beans.ClassBean;
import it.project.codeSmellAnalyzer.metrics.CKMetrics;

import java.util.HashMap;
import java.util.Map;

public class ClassDataShouldBePrivateRule {

	private static Double checkValues(Double metric, int threshold){
		return (metric > threshold) ? metric - threshold: 0;
	}
	public Map<String, Double> isClassDataShouldBePrivate(ClassBean pClass) {
		Map<String, Double> toReturn = new HashMap<>();
		double NOPA = CKMetrics.getNOPA(pClass);

		toReturn.put("NOPA",NOPA);
		/*if (CKMetrics.getNOPA(pClass) > 10)
			return CKMetrics.getNOPA(pClass);

		return 0;*/
		return toReturn;
	}

}
