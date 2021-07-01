package it.project.codeSmellAnalyzer.smellDetector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import it.project.codeSmellAnalyzer.beans.ClassBean;
import it.project.codeSmellAnalyzer.beans.MethodBean;
import it.project.codeSmellAnalyzer.metrics.CKMetrics;

/*
 * 
   RULE_CARD: SpaghettiCode {
	RULE: SpaghettiCode {INTER: NoInheritanceClassGlobalVariable LongMethodMethodNoParameter} 
	RULE: LongMethodMethodNoParameter {INTER LongMethod MethodNoParameter}
	RULE: LongMethod { (METRIC: METHOD_LOC, VERY_HIGH, 0) }
	RULE: MethodNoParameter { (STRUCT: METHOD_NO_PARAM) }
	RULE: NoInheritanceClassGlobalVariable {INTER NoInheritance ClassGlobalVariable}
	RULE: NoInheritance { (METRIC: DIT, INF_EQ, 2, 0) }
	RULE: ClassGlobalVariable {INTER ClassOneMethod FieldPrivate}
	RULE: ClassOneMethod { (STRUCT: GLOBAL_VARIABLE, 1) } };

 * 
 */

public class SpaghettiCodeRule {

	private static Double checkValues(Double metric, int threshold){
		return (metric > threshold) ? metric - threshold: 0;
	}


	public Map<String, Double> isSpaghettiCode(ClassBean pClass) {
		Collection<MethodBean> methods = pClass.getMethods();
		Map<String, Double> toReturn = new HashMap<>();


		if(CKMetrics.getELOC(pClass) > 600) {
			if(hasLongMethodNoParameter(methods))
				toReturn.put("ELOC", (double) CKMetrics.getELOC(pClass));
			else
				toReturn.put("ELOC",0.0);
		}
		else
			toReturn.put("ELOC",0.0);
		return toReturn;
	}

	private boolean hasLongMethodNoParameter(Collection<MethodBean> pMethods) {

		for(MethodBean methodBean: pMethods) {
			String[] tokenizedTextualContent = methodBean.getTextContent().split("\n");

			if( (tokenizedTextualContent.length > 100) || (methodBean.getParameters().size() == 0) )
				return true;
		}

		return false;
	}

}
