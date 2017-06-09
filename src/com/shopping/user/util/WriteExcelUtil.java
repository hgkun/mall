package com.shopping.user.util;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel写入工具
 * 
 
 * @version V1.0
 * @since V1.0
 */
public class WriteExcelUtil{

	/**
	 * 创建excel表
	 * 
	 * @param headers 标题
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Workbook createWorkbook(List list,String[] headers, String fileName, ExportExcel exportExcel) {
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(fileName);
		sheet.setDefaultRowHeight((short) (2*256));
		sheet.setColumnWidth(1,256*20);
		Row row = sheet.createRow((int) 0);
		CellStyle style = workbook.createCellStyle();
		DataFormat dataFormat =  workbook.createDataFormat();  
		short dataformat = dataFormat.getFormat("yyyy-mm-dd"); 
		style.setDataFormat(dataformat);
		style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setAlignment(CellStyle.ALIGN_CENTER);

		for (int i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(style);
			//sheet.autoSizeColumn(i);
		}
		exportExcel.buildDatas(row, sheet, list);
	    for (int i = 0; i < headers.length; i++) {
			 sheet.autoSizeColumn((short)i);
		}

		return workbook;

	}

	public static Workbook createWorkbook(List list,String[] headers1, String[] header2,String fileName, ExportExcel exportExcel) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(fileName);
		sheet.setDefaultRowHeight((short) (2*256));
		sheet.setColumnWidth(1,256*20);
		Row row = sheet.createRow((int) 0);
		CellStyle style = workbook.createCellStyle();
		DataFormat dataFormat =  workbook.createDataFormat();  
		short dataformat = dataFormat.getFormat("yyyy-mm-dd"); 
		style.setDataFormat(dataformat);
		style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		for (int i = 0; i < headers1.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(headers1[i]);
			cell.setCellStyle(style);
			//sheet.autoSizeColumn(i);
		}
		exportExcel.buildDatas(row, sheet, list,header2);
		
		
		for (int i = 0; i < headers1.length; i++) {
			 sheet.autoSizeColumn((short)i);
		}

		return workbook;

	}
}
