package it.project.codeSmellAnalyzer.smellDetector;

import it.project.codeSmellAnalyzer.beans.ClassBean;
import it.project.codeSmellAnalyzer.metrics.CKMetrics;

import java.util.HashMap;
import java.util.Map;

public class ComplexClassRule {

	private static Double checkValues(Double metric, int threshold){
		return (metric > threshold) ? metric - threshold: 0;
	}


	public Map<String, Double> isComplexClass(ClassBean pClass) {
		double mcCabeMetric = CKMetrics.getMcCabeMetric(pClass);
		Map<String, Double> toReturn = new HashMap<>();
					toReturn.put("mcCabeMetric",mcCabeMetric);
		/*if(CKMetrics.getMcCabeMetric(pClass) > 200)
				return CKMetrics.getMcCabeMetric(pClass);
*/
		return toReturn;
	}
}