package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class sampleChart {
	public static void main(String[] args) {
		chartBean bean = new chartBean();
		List<chartBean> list = new ArrayList<chartBean>();

		bean.setSubName("Day1");
		bean.setMarks(50000);
		bean.setLabVal("50000");
		list.add(bean);
		bean = new chartBean();
		bean.setSubName("Day2");
		bean.setMarks(20000);
		bean.setLabVal("20000");
		list.add(bean);
		bean = new chartBean();
		bean.setSubName("Day3");
		bean.setMarks(30000);
		bean.setLabVal("30000");
		list.add(bean);
		bean = new chartBean();
		bean.setSubName("Day4");
		bean.setMarks(130000);
		bean.setLabVal("130000");
		list.add(bean);
		bean = new chartBean();
		bean.setSubName("Day5");
		bean.setMarks(2000);
		bean.setLabVal("2000");
		list.add(bean);
		bean = new chartBean();
		bean.setSubName("Day6");
		bean.setMarks(80000);
		bean.setLabVal("80000");
		list.add(bean);
		

		InputStream inputStream;
		try {
			inputStream = new FileInputStream(
					"F:/Jasper Report Workspace/JasperTesting/src/jaspers/sampleChart.jrxml");

			JRBeanCollectionDataSource beanDataSource1 = new JRBeanCollectionDataSource(list, true);
			JRDataSource jrDatasource1 = (JRDataSource) beanDataSource1;

			JasperReport jasperReport = JasperCompileManager
					.compileReport(inputStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, null, jrDatasource1);
			JasperExportManager.exportReportToPdfFile(jasperPrint,
					"F:/sample.pdf");
		} catch (FileNotFoundException | JRException e) {
			e.printStackTrace();
		}

		System.out.println("Printed ......");

	}
}
