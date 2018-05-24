package com.osp.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

/**
 * execle 操作工具
 * 
 * @author liudonghe 2017年5月10日 下午4:19:04
 *
 */
public class ExcelUtil {
	private ExcelUtil() {

	}

	/**
	 * 表格内容样式
	 * 
	 * @param workbook
	 * @return
	 */
	private static HSSFCellStyle createRowStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setColor(HSSFColor.BLACK.index);
		style.setBorderBottom((short) 1);
		style.setBorderLeft((short) 1);
		style.setBorderRight((short) 1);
		style.setBorderTop((short) 1);
		style.setFont(font);
		return style;
	}

	/**
	 * 表头样式
	 * 
	 * @param workbook
	 * @return
	 */
	private static HSSFCellStyle createHeadStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setColor(HSSFColor.BLACK.index);
		style.setBorderBottom((short) 1);
		style.setBorderLeft((short) 1);
		style.setBorderRight((short) 1);
		style.setBorderTop((short) 1);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillBackgroundColor(HSSFColor.LIGHT_GREEN.index);
		style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		style.setFont(font);
		return style;
	}

	/**
	 * 根据数据源、表头中文、英文、输出流 ——》导出Excel
	 * 
	 * @param sourceList
	 * @param headEngs
	 * @param headChns
	 * @param filePath
	 * @param out
	 */
	public static void createExcel(List<Map<String, Object>> sourceList, String[] headEngs, String[] headChns,
			OutputStream out) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFCellStyle headStyle = createHeadStyle(workbook);
			HSSFCellStyle rowStyle = createRowStyle(workbook);
			HSSFSheet sheet = workbook.createSheet();
			HSSFRow headRow = sheet.createRow(0);
			for (int j = 0; j < headChns.length; j++) {
				HSSFCell excelCell = headRow.createCell(j);
				excelCell.setCellValue(headChns[j]);
				excelCell.setCellStyle(headStyle);
			}
			for (int i = 0; i < sourceList.size(); i++) {
				Map<String, Object> rowMap = sourceList.get(i);
				HSSFRow excelRow = sheet.createRow(i + 1);
				for (int j = 0; j < headEngs.length; j++) {
					HSSFCell excelCell = excelRow.createCell(j);
					excelCell.setCellValue(rowMap.get(headEngs[j]) == null ? "" : rowMap.get(headEngs[j]).toString());
					excelCell.setCellStyle(rowStyle);
				}
			}
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: toExcel
	 * @Description: 将一个List<Object>的集合写入到excel中返回excel对象。
	 * @param <T>
	 * @param title
	 *            题头，在excel加入每个字段名，如 姓名 年龄
	 * @param list
	 *            目标集合
	 * @param convert
	 *            将一个Object对象，转化成一个你想写入到文本中的数组(回调函数）
	 * @return
	 */
	private static <T> HSSFWorkbook toExecel(String[] title, List<T> list, IConvert<T> convert) {
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet();
		HSSFCellStyle headStyle = createHeadStyle(book);
		HSSFCellStyle rowStyle = createRowStyle(book);
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < title.length; i++) {
			HSSFCell tempCell = row.createCell(i);
			tempCell.setCellValue(title[i]);
			tempCell.setCellStyle(headStyle);
		}
		for (int i = 1; i < list.size(); i++) {
			T obj = list.get(i);
			String[] pars = convert.par(obj);
			HSSFRow tempRow = sheet.createRow(i);
			for (int j = 0; j < pars.length; j++) {
				HSSFCell cell = tempRow.createCell(j);
				cell.setCellValue(pars[j] == null ? "" : pars[j]);
				cell.setCellStyle(rowStyle);
			}
		}
		return book;
	}

	/**
	 * 
	 * @param sourceList
	 * @param headEngs
	 * @param headChns
	 * @param out
	 */
	public static <T> void createExcelByBean(List<T> sourceList, final String[] headEngs, String[] headChns,
			OutputStream out) {
		HSSFWorkbook execel = toExecel(headChns, sourceList, new IConvert<T>() {
			@Override
			public String[] par(T obj) {
				List<String> arr = new ArrayList<>();
				try {
					BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
					PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
					for (String en : headEngs) {
						for (PropertyDescriptor p : propertyDescriptors) {
							if (p.getName().equals(en)) {
								Object invoke = p.getReadMethod().invoke(obj);
								arr.add(invoke == null ? "" : invoke.toString());
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return arr.toArray(new String[arr.size()]);
			}
		});
		try {
			execel.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @ClassName: IConvert
	 * @Description: 单行转换模板
	 * @author liudonghe
	 * @date 2017年5月10日 下午4:20:04
	 * 
	 * @param <T>
	 */
	private interface IConvert<T> {
		public String[] par(T obj);
	}
}
