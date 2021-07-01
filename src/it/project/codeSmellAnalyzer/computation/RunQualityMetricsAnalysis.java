package it.project.codeSmellAnalyzer.computation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

import it.project.codeSmellAnalyzer.beans.PackageBean;
import org.eclipse.core.runtime.CoreException;

import it.project.codeSmellAnalyzer.beans.ClassBean;
import it.project.codeSmellAnalyzer.metrics.CKMetrics;

public class RunQualityMetricsAnalysis {

	public static void main(String[] args) {
		
		// Path to the directory containing all the projects under analysis 

		String pathToDirectory = "/path/to/projects/";
		File experimentDirectory = new File(pathToDirectory);
		
		for(File project: Objects.requireNonNull(experimentDirectory.listFiles())) {
			try {
				// Method to convert a directory into a set of java packages.
				Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());
				String output = "className;LOC;LCOM;CBO;WMC;RFC\n";
				Map<String,Double> LOC= new HashMap<>();
				Map<String,Double> LCOM = new HashMap<>();
				Map<String,Double> CBO = new HashMap<>();
				Map<String,Double> McCabeMetric = new HashMap<>();
				Map<String,Double> RFC = new HashMap<>();
				for(PackageBean packageBean: packages) {
					for(ClassBean classBean: packageBean.getClasses()) {


						LOC.put("LOC", (double) CKMetrics.getLOC(classBean));
						LCOM.put("LCOM", (double) CKMetrics.getLCOM2(classBean));
						CBO.put("CBO", (double) CKMetrics.getCBO(classBean));
						McCabeMetric.put("McCabeMetric", (double) CKMetrics.getMcCabeMetric(classBean));
						RFC.put("RFC", (double) CKMetrics.getRFC(classBean));
						output+=packageBean.getName() +"."+classBean.getName() + ";" + CKMetrics.getLOC(classBean) + ";" 
								+ CKMetrics.getLCOM2(classBean) + ";" + CKMetrics.getCBO(classBean) + ";" 
								+ CKMetrics.getMcCabeMetric(classBean) + ";" + CKMetrics.getRFC(classBean) + "\n";
						
						// Other metrics are available in the CKMetrics class.
						
					}
				}	
				
				FileUtility.writeFile(output, "/path/to/results/" + project.getName() + ".csv");
			} catch (CoreException e) {
				e.printStackTrace();
			}	
		}
	}
}
