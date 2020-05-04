package test;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

@SuppressWarnings("deprecation")
public class sampleBusinessLogic {
	public static void main(String[] args) throws SQLException, JRException {
		List<List<?>> beanCollectionFinal = new ArrayList<List<?>>();
		List<HashMap<String, String>> dataList = getData();
		List<sampleBean> sBeanList = new ArrayList<sampleBean>();
		sampleBean sBean = null;
		sBeanList.add(null);
		for (HashMap<String, String> dataRow : dataList) {
			sBean = new sampleBean();
			sBean.setEmpId(Integer.parseInt(dataRow.get("emp_id").toString()));
			sBean.setEmpName(dataRow.get("emp_name").toString());
			sBean.setEmpSalary(Double.parseDouble(dataRow.get("emp_salary")));
			sBeanList.add(sBean);
		}
		beanCollectionFinal.add(sBeanList);
		showJasperReport(sBeanList);
	}

	public static Connection getConnection() throws SQLException {
		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/system", "system", "system");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return c;
	}

	public static List<HashMap<String, String>> getData() throws SQLException {
		List<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> dataRow = null;
		Connection con = getConnection();
		Statement stmt = con.createStatement();
		StringBuilder querySB = new StringBuilder();
		querySB.append("select emp_id, emp_name, emp_salary from employee");
		try {
			ResultSet rs = stmt.executeQuery(querySB.toString());
			while (rs.next()) {
				dataRow = new HashMap<String, String>();
				dataRow.put("emp_id", rs.getString("emp_id"));
				dataRow.put("emp_name", rs.getString("emp_name"));
				dataRow.put("emp_salary", rs.getString("emp_salary"));
				dataList.add(dataRow);
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return dataList;
	}

	public static void showJasperReport(List<sampleBean> beanCollectionFinal) throws JRException {
		File sub1File = new File("F:\\Jasper Report Workspace\\JasperTesting\\src\\subSampleTest.jasper");

		JasperReport jasperReport1 = JasperCompileManager
				.compileReport("F:\\Jasper Report Workspace\\JasperTesting\\src\\sampleTestMain.jrxml");
		JRBeanCollectionDataSource beanDataSource1 = new JRBeanCollectionDataSource(beanCollectionFinal, true);
		JRDataSource jrDatasource1 = (JRDataSource) beanDataSource1;
		System.out.println("```````````Generating Report``````````");

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("PAGE1", sub1File.getPath());
		parameters.put("DATA", beanDataSource1);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport1, parameters, jrDatasource1);
		String outFileNamePDF = "UserDetails.pdf";
		JRExporter exporter1 = new JRPdfExporter();
		exporter1.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "F:\\" + outFileNamePDF);
		exporter1.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter1.exportReport();

	}

}
