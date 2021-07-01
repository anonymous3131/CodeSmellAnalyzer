package it.project.codeSmellAnalyzer.computation;

import java.util.ArrayList;
import java.util.Vector;

import it.project.codeSmellAnalyzer.beans.ClassBean;
import it.project.codeSmellAnalyzer.beans.MethodBean;
import it.project.codeSmellAnalyzer.beans.PackageBean;

import it.unisa.codeSmellAnalyzer.beans.*;


public class TestMutationUtilities {

	public ArrayList<ClassBean> getTestCases(Vector<PackageBean> pProject) {
		ArrayList<ClassBean> testCases = new ArrayList<ClassBean>();



		for(PackageBean packageBean: pProject) {
			for(ClassBean classBean: packageBean.getClasses()) {

				/*if(classBean.getTextContent().contains("EvoSuiteTest {")) {
					testCases.add(classBean);
				}*/

				if(classBean.getTextContent().contains(" extends TestCase")) {
					testCases.add(classBean);
				} else if(classBean.getTextContent().contains("@Test")) {
					testCases.add(classBean);
				} else if(classBean.getTextContent().contains("@BeforeClass")) {
					testCases.add(classBean);
				}
			}
		}

		return testCases;
	}
	
	public ArrayList<ClassBean> getManuallyWrittenTestCases(Vector<PackageBean> pProject) {
		ArrayList<ClassBean> testCases = new ArrayList<ClassBean>();



		for(PackageBean packageBean: pProject) {
			for(ClassBean classBean: packageBean.getClasses()) {

				if(classBean.getTextContent().contains(" extends TestCase")) {
					testCases.add(classBean);
				} else if(classBean.getTextContent().contains("@Test")) {
					testCases.add(classBean);
				} else if(classBean.getTextContent().contains("@BeforeClass")) {
					testCases.add(classBean);
				}
			}
		}

		return testCases;
	}

	public static ClassBean getProductionClassBy(String pTestSuiteName, Vector<PackageBean> pSystem) {

		for(PackageBean packageBean : pSystem) {
			for(ClassBean classBean : packageBean.getClasses()) {

				String productionClassName = pTestSuiteName.replace("EvoSuiteTest", "");

				if(productionClassName.equals(classBean.getName())) 
					return classBean;

			}
		}

		return null;
	}
	
	public static ClassBean getManuallyWrittenProductionClassBy(String pTestSuiteName, Vector<PackageBean> pSystem) {

		for(PackageBean packageBean : pSystem) {
			for(ClassBean classBean : packageBean.getClasses()) {

				String testSuiteName = pTestSuiteName.replace("Test", "");
						
				if(classBean.getName().toLowerCase().equals(testSuiteName.toLowerCase())) 
					return classBean;

			}
		}

		return null;
	}
	

	public static MethodBean getProductionMethodBy(String pTestMethodCall, Vector<PackageBean> pSystem) {

		for(PackageBean packageBean : pSystem) {
			for(ClassBean classBean : packageBean.getClasses()) {
				for(MethodBean methodBean : classBean.getMethods()) {
					if(methodBean.getName().equals(pTestMethodCall)) 
						return methodBean;
				}
			}
		}

		return null;
	}

}