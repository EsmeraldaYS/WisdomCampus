/*
package demo.ysu.com.wisdomcampus;


import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * Created by Yang on 2017/3/23.
 *//*


public class MyClass {
        static private Workbook wb;
        static private Sheet sheet;
        static private Row row;

        */
/**
         *
         * @method ：readExcelTitle<br>
         * @describe ：读取 Excel 文件<br>
         * @author ：wanglongjie<br>
         * @createDate ：2015年8月31日下午2:41:25 <br>
         * @param fileName
         *            ：Excel 文件路径
         * @return String[]
         *//*

        public static String[] readExcelTitle(String fileName) {
            InputStream is;
            try {
                is = new FileInputStream(fileName);
                String postfix = fileName.substring(fileName.lastIndexOf("."),
                        fileName.length());
                if (postfix.equals(".xls")) {
                    // 针对 2003 Excel 文件
                    wb = new HSSFWorkbook(new POIFSFileSystem(is));
                    sheet = wb.getSheetAt(0);
                } else {
                    // 针对2007 Excel 文件
                    wb = new XSSFWorkbook(is);
                    sheet = wb.getSheetAt(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            sheet = wb.getSheetAt(0);
            row = sheet.getRow(0);// 获取第一行（约定第一行是标题行）
            int colNum = row.getPhysicalNumberOfCells();// 获取行的列数
            String[] titles = new String[colNum];
            for (int i = 0; i < titles.length; i++) {
                titles[i] = getCellFormatValue(row.getCell(i));
            }
            return titles;
        }

        */
/**
         *
         * @method ：readExcelContent<br>
         * @describe ：读取 Excel 内容<br>
         * @author ：wanglongjie<br>
         * @createDate ：2015年8月31日下午3:12:06 <br>
         * @param fileName
         *            ：Excel 文件路径
         * @return List<Map<String,String>>
         *//*

        public static List<Map<String, String>> readExcelContent(String fileName) {
            List<Map<String, String>> list = new ArrayList<>();
            Map<String, String> content = null;
            try {
                InputStream is;
                is = new FileInputStream(fileName);
                String postfix = fileName.substring(fileName.lastIndexOf("."),
                        fileName.length());
                if (postfix.equals(".xls")) {
                    // 针对 2003 Excel 文件
                    wb = new HSSFWorkbook(new POIFSFileSystem(is));
                    sheet = wb.getSheetAt(0);
                } else {
                    // 针对2007 Excel 文件
                    wb = new XSSFWorkbook(is);
                    sheet = wb.getSheetAt(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();// 得到总行数
            row = sheet.getRow(0);
            int colNum = row.getPhysicalNumberOfCells();
            String titles[] = readExcelTitle(fileName);
            // 正文内容应该从第二行开始,第一行为表头的标题
            for (int i = 1; i <= rowNum; i++) {
                int j = 0;
                Log.d("ASSET", "readExcelContent: "+getCellFormatValue(row.getCell(j)).trim());
                row = sheet.getRow(i);
                content = new LinkedHashMap<>();
                if ( getCellFormatValue(row.getCell(j)).trim().isEmpty() )break;
                do {
                    if ( getCellFormatValue(row.getCell(j)).trim().isEmpty() )break;
                    content.put(titles[j], getCellFormatValue(row.getCell(j)).trim());
                    j++;
                } while (j < colNum);
                list.add(content);
            }
            return list;
        }

        */
/**
         * 根据Cell类型设置数据
         *
         * @param cell
         * @return
         *//*

        private static String getCellFormatValue(Cell cell) {
            String cellvalue = "";
            if (cell != null) {
                // 判断当前Cell的Type
                switch (cell.getCellType()) {
                    // 如果当前Cell的Type为NUMERIC
                    case Cell.CELL_TYPE_NUMERIC:
                    case Cell.CELL_TYPE_FORMULA: {
                        // 判断当前的cell是否为Date
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            // 方法2：这样子的data格式是不带带时分秒的：2011-10-12
                            Date date = cell.getDateCellValue();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            cellvalue = sdf.format(date);
                        } else {
                            // 如果是纯数字取得当前Cell的数值
                            cellvalue = String.valueOf(cell.getNumericCellValue());
                        }
                        break;
                    }
                    // 如果当前Cell的Type为STRIN
                    case Cell.CELL_TYPE_STRING:
                        // 取得当前的Cell字符串
                        cellvalue = cell.getRichStringCellValue().getString();
                        break;
                    default:
                        // 默认的Cell值
                        cellvalue = " ";
                }
            } else {
                cellvalue = "";
            }
            return cellvalue;

        }
}
*/
