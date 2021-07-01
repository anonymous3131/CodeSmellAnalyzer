package it.project.codeSmellAnalyzer.testSmellDetector;

import it.project.codeSmellAnalyzer.beans.MethodBean;

public class FireAndForget {

	public double isFireAndForget(MethodBean pMethod) {
		double fireAndForget = 0.0;
		String body = pMethod.getTextContent();

		
	try {
		while (body.contains("Thread.sleep")) {
			int indexStart = body
					.indexOf("Thread.sleep");
			int indexEnd = indexStart;
			char c = body.charAt(indexStart);
			String substring = c + "";
			while (c != ';') {
				indexEnd++;
				c = body.charAt(indexEnd);
				substring += c + "";

			}
			fireAndForget++;
			body = body.replaceFirst(
					"Thread\\.sleep", "");
		}

	} catch (Exception e) {

	}

		return fireAndForget;
	}
}
