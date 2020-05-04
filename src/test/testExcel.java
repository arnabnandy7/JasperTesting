package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

public class testExcel {

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
					"F:/Jasper Report Workspace/JasperTesting/src/jaspers/testExcel.jrxml");
			
			File destFile = new File("F:/sample.xlsx"); 

			JRBeanCollectionDataSource beanDataSource1 = new JRBeanCollectionDataSource(
					list, true);
			JRDataSource jrDatasource1 = (JRDataSource) beanDataSource1;

			JasperReport jasperReport = JasperCompileManager
					.compileReport(inputStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, null, jrDatasource1);
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.TRUE);
			exporter.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE,
					Boolean.TRUE);
			exporter.setParameter(
					JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);
			exporter.setParameter(
					JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.TRUE);
			exporter.setParameter(JExcelApiExporterParameter.OUTPUT_FILE_NAME,
					destFile.toString());
			exporter.setParameter(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.FALSE);
			exporter.exportReport();
		} catch (FileNotFoundException | JRException e) {
			e.printStackTrace();
		}
	}
}
