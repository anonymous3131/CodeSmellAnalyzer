package it.project.codeSmellAnalyzer.smellDetector;

import it.project.codeSmellAnalyzer.beans.ClassBean;
import it.project.codeSmellAnalyzer.metrics.CKMetrics;

import java.util.HashMap;
import java.util.Map;

public class FunctionalDecompositionRule {
	private static Double checkValues(Double metric, int threshold){
		return (metric < threshold) ? metric - threshold: 0;
	}



	public Map<String,Double> isFunctionalDecomposition(ClassBean pClass) {
		int nPrivateFields = CKMetrics.getNOPrivateA(pClass);
		int noa = CKMetrics.getNOA(pClass);
		Map<String,Double> toReturn = new HashMap<>();
		toReturn.put("WCM", 0.0);
		if(noa == nPrivateFields) {
			if(CKMetrics.getWMC(pClass) < 3) {
				String className = pClass.getName();

				if( (className.toLowerCase().contains("make")) || (className.toLowerCase().contains("create")) ||
						(className.toLowerCase().contains("creator")) || (className.toLowerCase().contains("execute")) ||
						(className.toLowerCase().contains("exec")) || (className.toLowerCase().contains("compute")) || 
						(className.toLowerCase().contains("display")) || (className.toLowerCase().contains("calculate"))) {
					toReturn.put("WCM", (double) CKMetrics.getWMC(pClass));

				} else
					toReturn.put("WCM", 0.0);


				//toReturn.put("WCM", checkValues((double) CKMetrics.getWMC(pClass),3));
				//return CKMetrics.getWMC(pClass);
			}
		}

		return toReturn;
	}
}
