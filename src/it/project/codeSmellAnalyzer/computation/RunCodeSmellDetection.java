package it.project.codeSmellAnalyzer.computation;

import java.io.*;
import java.util.*;

import it.project.codeSmellAnalyzer.beans.ClassBean;
import it.project.codeSmellAnalyzer.beans.PackageBean;
import it.project.codeSmellAnalyzer.metrics.CKMetrics;
import it.project.codeSmellAnalyzer.smellDetector.*;
import org.eclipse.core.runtime.CoreException;

public class RunCodeSmellDetection {
	public static String getNameProject(String nameSubProject) {
		String toReturn = "";
		nameSubProject = nameSubProject.toLowerCase();
		if (nameSubProject.contains("ant")){
			toReturn = "ANT";
		}

		if (nameSubProject.contains("jhotdraw")){
			toReturn = "JHOTDRAW";
		}
		if (nameSubProject.contains("jedit")) {
			toReturn = "JEDIT";
		}
		return toReturn;
	}

	public static void main(String... args) {
		boolean flag;

		// Path to the directory containing all the projects under analysis 
				String pathToDirectory = "/path/to/projects/";
		File experimentDirectory = new File(pathToDirectory);

		// Declaring Class-level code smell objects.
		ClassDataShouldBePrivateRule cdsbp = new ClassDataShouldBePrivateRule();
		ComplexClassRule complexClass = new ComplexClassRule();
		FunctionalDecompositionRule functionalDecomposition = new FunctionalDecompositionRule();
		GodClassRule godClass = new GodClassRule();
		SpaghettiCodeRule spaghettiCode = new SpaghettiCodeRule();
		for(File pathFolderRoot: Objects.requireNonNull(experimentDirectory.listFiles())) {
			String parentRoot = pathFolderRoot.getName();
			File experimentDirectorySub = new File(experimentDirectory.getPath()+"/"+parentRoot);
			for(File projSub : Objects.requireNonNull(experimentDirectorySub.listFiles())) {
				flag = false;
				try {
					Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(projSub.getAbsolutePath());

					for (PackageBean packageBean : packages) {

						for (ClassBean classBean : packageBean.getClasses()) {
							Map<String, Double> isClassDataShouldBePrivate = cdsbp.isClassDataShouldBePrivate(classBean);
							Map<String, Double> isComplexClass = complexClass.isComplexClass(classBean);
							Map<String, Double> isFunctionalDecomposition = functionalDecomposition.isFunctionalDecomposition(classBean);
							Map<String, Double> isGodClass = godClass.isGodClass(classBean);
							Map<String, Double> isSpaghettiCode = spaghettiCode.isSpaghettiCode(classBean);
							if (!flag) {
								List<String> columns = new ArrayList<>();
								columns.add("Project");
								columns.add("Release");
								columns.add("Class Name");
								columns.addAll(isClassDataShouldBePrivate.keySet());
								columns.add("LOC");
								columns.add("LCOM"	);
								columns.add("CBO");
								columns.add("McCabeMetricQuality");
								columns.add("RFC");
								columns.add("DIT");
								columns.add("NOC");
								addColumns(projSub.getName(), "isClassDataShouldBePrivate", columns);
								columns.clear();

								columns.add("Project");
								columns.add("Release");
								columns.add("Class Name");
								columns.addAll(isComplexClass.keySet());
								columns.add("LOC");
								columns.add("LCOM");
								columns.add("CBO");
								columns.add("McCabeMetricQuality");
								columns.add("RFC");
								columns.add("DIT");
								columns.add("NOC");
								addColumns(projSub.getName(), "isComplexClass", columns);
								columns.clear();

								columns.add("Project");
								columns.add("Release");
								columns.add("Class Name");
								columns.addAll(isFunctionalDecomposition.keySet());
								columns.add("LOC");
								columns.add("LCOM");
								columns.add("CBO");
								columns.add("McCabeMetricQuality");
								columns.add("RFC");
								columns.add("DIT");
								columns.add("NOC");
								addColumns(projSub.getName(), "isFunctionalDecomposition", columns);
								columns.clear();

								columns.add("Project");
								columns.add("Release");
								columns.add("Class Name");
								columns.addAll(isGodClass.keySet());
								columns.add("LOC");
								columns.add("LCOM");
								columns.add("CBO");
								columns.add("McCabeMetricQuality");
								columns.add("RFC");
								columns.add("DIT");
								columns.add("NOC");
								addColumns(projSub.getName(), "isGodClass", columns);
								columns.clear();

								columns.add("Project");
								columns.add("Release");
								columns.add("Class Name");
								columns.addAll(isSpaghettiCode.keySet());
								columns.add("LOC");
								columns.add("LCOM");
								columns.add("CBO");
								columns.add("McCabeMetricQuality");
								columns.add("RFC");
								columns.add("DIT");
								columns.add("NOC");
								addColumns(projSub.getName(), "isSpaghettiCode", columns);
								columns.clear();
								flag = true;
							}

							List<String> row = new ArrayList<>();
							row.add(getNameProject(projSub.getName()));
							row.add(projSub.getName());
							row.add(classBean.getRelativePath());
							for (Map.Entry<String, Double> entry : isClassDataShouldBePrivate.entrySet()) {
								row.add(String.valueOf(entry.getValue()));
							}

							row.add(String.valueOf(CKMetrics.getLOC(classBean)));
							row.add(String.valueOf(CKMetrics.getLCOM2(classBean)));
							row.add(String.valueOf(CKMetrics.getCBO(classBean)));
							row.add(String.valueOf(CKMetrics.getMcCabeMetric(classBean)));
							row.add(String.valueOf(CKMetrics.getRFC(classBean)));
							row.add(String.valueOf(CKMetrics.getDIT(classBean,packages,0)));
							row.add(String.valueOf(CKMetrics.getNOC(classBean,packages)));


							writeToCSV(projSub.getName(),"isClassDataShouldBePrivate", row);
							row.clear();

							row.add(getNameProject(projSub.getName()));
							row.add(projSub.getName());
							row.add(classBean.getRelativePath());
							for (Map.Entry<String, Double> entry : isComplexClass.entrySet()) {
								row.add(String.valueOf(entry.getValue()));
							}
							row.add(String.valueOf(CKMetrics.getLOC(classBean)));
							row.add(String.valueOf(CKMetrics.getLCOM2(classBean)));
							row.add(String.valueOf(CKMetrics.getCBO(classBean)));
							row.add(String.valueOf(CKMetrics.getMcCabeMetric(classBean)));
							row.add(String.valueOf(CKMetrics.getRFC(classBean)));
							row.add(String.valueOf(CKMetrics.getDIT(classBean,packages,0)));
							row.add(String.valueOf(CKMetrics.getNOC(classBean,packages)));
							writeToCSV(projSub.getName(),"isComplexClass", row);
							row.clear();

							row.add(getNameProject(projSub.getName()));
							row.add(projSub.getName());
							row.add(classBean.getRelativePath());
							for (Map.Entry<String, Double> entry : isFunctionalDecomposition.entrySet()) {
								row.add(String.valueOf(entry.getValue()));
							}

							row.add(String.valueOf(CKMetrics.getLOC(classBean)));
							row.add(String.valueOf(CKMetrics.getLCOM2(classBean)));
							row.add(String.valueOf(CKMetrics.getCBO(classBean)));
							row.add(String.valueOf(CKMetrics.getMcCabeMetric(classBean)));
							row.add(String.valueOf(CKMetrics.getRFC(classBean)));
							row.add(String.valueOf(CKMetrics.getDIT(classBean,packages,0)));
							row.add(String.valueOf(CKMetrics.getNOC(classBean,packages)));
							writeToCSV(projSub.getName(),"isFunctionalDecomposition", row);
							row.clear();

							row.add(getNameProject(projSub.getName()));
							row.add(projSub.getName());
							row.add(classBean.getRelativePath());

							for (Map.Entry<String, Double> entry : isGodClass.entrySet()) {
								row.add(String.valueOf(entry.getValue()));
							}

							row.add(String.valueOf(CKMetrics.getLOC(classBean)));
							row.add(String.valueOf(CKMetrics.getLCOM2(classBean)));
							row.add(String.valueOf(CKMetrics.getCBO(classBean)));
							row.add(String.valueOf(CKMetrics.getMcCabeMetric(classBean)));
							row.add(String.valueOf(CKMetrics.getRFC(classBean)));
							row.add(String.valueOf(CKMetrics.getDIT(classBean,packages,0)));
							row.add(String.valueOf(CKMetrics.getNOC(classBean,packages)));
							writeToCSV(projSub.getName(),"isGodClass", row);
							row.clear();

							row.add(getNameProject(projSub.getName()));
							row.add(projSub.getName());
							row.add(classBean.getRelativePath());

							for (Map.Entry<String, Double> entry : isSpaghettiCode.entrySet()) {
								row.add(String.valueOf(entry.getValue()));
							}

							row.add(String.valueOf(CKMetrics.getLOC(classBean)));
							row.add(String.valueOf(CKMetrics.getLCOM2(classBean)));
							row.add(String.valueOf(CKMetrics.getCBO(classBean)));
							row.add(String.valueOf(CKMetrics.getMcCabeMetric(classBean)));
							row.add(String.valueOf(CKMetrics.getRFC(classBean)));
							row.add(String.valueOf(CKMetrics.getDIT(classBean,packages,0)));
							row.add(String.valueOf(CKMetrics.getNOC(classBean,packages)));
							writeToCSV(projSub.getName(),"isSpaghettiCode", row);
							row.clear();
						}
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}


		}
	}


	private static void addColumns(String project,String typeOfSmell, List<String> columns){

		File CSVFile = new File("projects_csv/"+project+"/"+typeOfSmell+".csv");
		CSVFile.getParentFile().mkdirs();
			FileWriter fw;
		try {
			fw = new FileWriter(CSVFile,true);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i =0;i<columns.size();i++){
					bw.write(columns.get(i));
				if (i<columns.size()-1) {
					bw.write(",");
				}
			}
			bw.newLine();
			bw.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	private static void writeToCSV(String project, String typeOfSmell,List<String>row)  {
		try {
		File CSVFile = new File("projects_csv/"+project+"/"+typeOfSmell+""+".csv");
		CSVFile.getParentFile().mkdirs();
		System.out.println(CSVFile);

		FileWriter fw = null;
		fw = new FileWriter(CSVFile,true);
		BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i< row.size(); i++) {
				bw.write(row.get(i));
				if (i<row.size()-1)
					bw.write(",");
			}
			bw.newLine();
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
