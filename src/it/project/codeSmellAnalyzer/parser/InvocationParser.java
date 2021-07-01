package it.project.codeSmellAnalyzer.parser;

import it.project.codeSmellAnalyzer.beans.MethodBean;

public class InvocationParser {
	
	public static MethodBean parse(String pInvocationName) {
		MethodBean methodBean = new MethodBean();
		methodBean.setName(pInvocationName);
		return methodBean;
	}

}
