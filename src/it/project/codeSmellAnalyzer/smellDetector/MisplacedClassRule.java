package it.project.codeSmellAnalyzer.smellDetector;

import it.project.codeSmellAnalyzer.beans.MethodBean;
import it.project.codeSmellAnalyzer.beans.PackageBean;
import it.project.codeSmellAnalyzer.beans.ClassBean;


public class MisplacedClassRule {

	public boolean isMisplacedClass(ClassBean pClass, PackageBean pBelongingPackage, PackageBean pCandidateEnvyPackage) {
		
		int dependenciesWithCandidateEnvyPackage = computeDependencies(pClass, pCandidateEnvyPackage);
		int dependenciesWithBelongingPackage = computeDependencies(pClass, pBelongingPackage);
		double structuralDiff = dependenciesWithCandidateEnvyPackage - dependenciesWithBelongingPackage;
		
		if(structuralDiff > 10) {
				return true;
		}
		
		return false;
	}

	private int computeDependencies(ClassBean pClass, PackageBean pPackage) {
		int dependencies = 0;

		for(MethodBean methodBean: pClass.getMethods()) {
			
			for(MethodBean calledMethod: methodBean.getMethodCalls()) {
				
				for(ClassBean classBean: pPackage.getClasses()) {
					for(MethodBean classMb: classBean.getMethods()) {
						for(MethodBean calledClassMb: classMb.getMethodCalls()) {
							
							if(calledMethod.getName().equals(calledClassMb.getName()))
								dependencies++;
						}
					}
				}
				
			}
		}

		return dependencies;
	}
}
