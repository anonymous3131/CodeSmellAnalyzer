package it.project.codeSmellAnalyzer.smellDetector;

import it.project.codeSmellAnalyzer.beans.ClassBean;
import it.project.codeSmellAnalyzer.metrics.CKMetrics;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Implementation of DECOR's God Class Rule Card (reported in the following)
 * 
 * RULE_CARD: Blob {
	RULE: Blob {ASSOC: associated FROM: mainClass ONE
		TO: DataClass MANY}
	RULE: mainClass {UNION LargeClassLowCohesion ControllerClass}
	RULE: LargeClassLowCohesion {UNION LargeClass LowCohesion} 
	RULE: LargeClass {(METRIC: NMD + NAD, VERY_HIGH, 20)} 
	RULE: LowCohesion {(METRIC: LCOM5, VERY_HIGH, 20)}
	RULE: ControllerClass {UNION (SEMANTIC: METHODNAME,
		{Process, Control, Command, Manage, Drive, System}), (SEMANTIC: CLASSNAME, {Process, Control, Command, Manage, Drive, System}}
	RULE: DataClass {(STRUCT: METHOD_ACCESSOR, 90)} };
 * 
 *
 *
 */
public class GodClassRule {

	public Map<String, Double> isGodClass(ClassBean pClass) {

		if(isControllerClass(pClass)) {
			return isLargeClassLowCohesion(pClass);
		}
		Map<String, Double> emptyMap = new HashMap<>();
			emptyMap.put("LCOM2", 0.0);
		emptyMap.put("featureSum",0.0);
		emptyMap.put("ELOC", 0.0);
		//emptyMap.put("sum", 0.0);

		return emptyMap;
	}

	private static double checkValues(double metric, int threshold){
		return (metric > threshold) ? metric - threshold: 0;
	}

	private static Map<String, Double> isLargeClassLowCohesion(ClassBean pClass) {
		Map<String, Double> metricsToReturn = new HashMap<>();
		double sum=0;
		int featureSum = CKMetrics.getWMC(pClass) + CKMetrics.getNOA(pClass);
		double LCOM2 = CKMetrics.getLCOM2(pClass);
		double ELOC= CKMetrics.getELOC(pClass);
		if( (LCOM2 > 350) || (featureSum > 20)) {
			if(ELOC> 500) {
				 sum = (LCOM2 - 350) + (featureSum - 20) + (ELOC - 500);
				//return sum;
			}
		}
		metricsToReturn.put("LCOM2", LCOM2);
		metricsToReturn.put("featureSum", (double)featureSum);
			metricsToReturn.put("ELOC", LCOM2);
		//metricsToReturn.put("sum", sum);

		return metricsToReturn;
	}

	private static boolean isControllerClass(ClassBean pClass) {
		String pClassName = pClass.getName().toLowerCase();

		if( (pClassName.contains("process")) || (pClassName.contains("control") || pClassName.contains("command") 
				|| pClassName.contains("manage") || pClassName.contains("drive") || pClassName.contains("system"))) {
			int featureSum = CKMetrics.getWMC(pClass) + CKMetrics.getNOA(pClass);

			if( (CKMetrics.getLCOM2(pClass) > 350) || (featureSum > 20)) {
				if(CKMetrics.getELOC(pClass) > 500)  
					return true;
			}
		}

		return false;
	}
}