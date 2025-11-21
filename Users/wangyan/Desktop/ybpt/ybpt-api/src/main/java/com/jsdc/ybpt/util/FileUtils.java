package com.jsdc.ybpt.util;

import cn.hutool.core.net.URLEncodeUtil;
import com.jsdc.ybpt.model.FileInfo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 文件处理工具类
 *
 * @author ruoyi
 */
public class FileUtils {
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os       输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void writeBytes(InputStream fis, OutputStream os) throws IOException {
        //FileInputStream fis = null;
        try {
//            File file = new File(filePath);
//            if (!file.exists()) {
//                throw new FileNotFoundException(filePath);
//            }
//            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 下载文件名重新编码
     *
     * @param request  请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    public static String getFileSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    public static String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * 删除文件夹所有内容
     *
     * @param filePath
     * @return
     */
    public static boolean deleteAllFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (filePath.endsWith(File.separator)) {
                temp = new File(filePath + tempList[i]);
            } else {
                temp = new File(filePath + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                if (!temp.delete()) {
                    System.gc();// 系统进行资源强制回收
                    temp.delete();
                }
            }
            if (temp.isDirectory()) {
                deleteAllFile(filePath + "/" + tempList[i]);// 先删除文件夹里面的文件
                deleteFolder(filePath + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath
     */
    public static void deleteFolder(String folderPath) {
        try {
            deleteAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    public static String fileToBase64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            in.read(bytes);
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
        return base64;
    }

    /**
     * MultipartFile转base64
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public static String multipartFileToBase64(MultipartFile multipartFile) throws IOException {
        byte[] imgByteArray = multipartFile.getBytes();
        return Base64.getEncoder().encodeToString(imgByteArray);
    }

    //BASE64解码成File文件
    public static void base64ToFile(String destPath,String base64, String fileName) {
        File file = null;
        //创建文件目录
        String filePath=destPath;
        File  dir=new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file=new File(filePath+"/"+fileName);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param dir void 判断路径是否存在，如果不存在则创建
     */
    public static void mkdirs(String dir) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(dir)) {
            return;
        }
        File file = new File(dir);
        if (!file.isDirectory()) {
            boolean mk = file.mkdirs();
            log.info("创建文件夹：" + dir + "=======" + mk);
        }
    }

    public static boolean checkFileSize(Long len, int size, String unit) {
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }

    //改变时间的格式
    public static String parseDate(String dateStr) throws java.text.ParseException {
        SimpleDateFormat input_date = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
        SimpleDateFormat output_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String finalDate = "";
        try {
            Date parse_date = input_date.parse(dateStr);
            finalDate = output_date.format(parse_date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return finalDate;
    }

    public static InputStream downLoadFile(String path,FileInfo fileInfo){
        URL url = null;
        URLConnection con = null;
        InputStream fis = null;
        if(fileInfo==null)
            return fis;
        try {
            String downLoadUrl = path + fileInfo.getFileUrl();
            url = new URL(URLEncodeUtil.encode(downLoadUrl, Charset.forName("UTF-8")));
            con = url.openConnection();
            con.connect();
            fis = con.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            try {
                fis.close();
            } catch (IOException e1) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
            try {
                fis.close();
            } catch (IOException e1) {
                e.printStackTrace();
            }
        }
//        finally {
//            try {
//                fis.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return fis;
    }

    public static void convertXlsToXlsx(InputStream xlsInputStream, OutputStream xlsxOutputStream){
        try {
            Workbook xlsWorkbook = new HSSFWorkbook(xlsInputStream);
            Workbook xlsxWorkbook = new XSSFWorkbook();
            // 复制所有工作表
            for (int i = 0; i < xlsWorkbook.getNumberOfSheets(); i++) {
                Sheet sheet = xlsWorkbook.getSheetAt(i);
                Sheet newSheet = xlsxWorkbook.createSheet(sheet.getSheetName());
                copySheet(sheet, newSheet);
            }

            // 写入输出流
            xlsxWorkbook.write(xlsxOutputStream);
            xlsxWorkbook.close();
            xlsWorkbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void copySheet(Sheet source, Sheet target) {
        for (Row row : source) {
            Row newRow = target.createRow(row.getRowNum());
            copyRow(row, newRow);
        }
    }

    private static void copyRow(Row source, Row target) {
        for (Cell cell : source) {
            Cell newCell = target.createCell(cell.getColumnIndex());
            copyCell(cell, newCell);
        }
    }

    private static void copyCell(Cell source, Cell target) {
        //CellStyle style = source.getCellStyle();
        CellStyle hssfCellStyle = source.getCellStyle();
        CellStyle xssfCellStyle = new XSSFCellStyle(new StylesTable());
        //xssfCellStyle.cloneStyleFrom(hssfCellStyle);
//        xssfCell.setCellStyle(xssfCellStyle);
        setCellValue(source, target);
        //target.setCellStyle(xssfCellStyle);
    }

    private static void setCellValue(Cell source, Cell target) {
        switch (source.getCellType()) {
            case STRING:
                target.setCellValue(source.getStringCellValue());
                break;
            case NUMERIC:
                target.setCellValue(source.getNumericCellValue());
                break;
            case BOOLEAN:
                target.setCellValue(source.getBooleanCellValue());
                break;
            case FORMULA:
                target.setCellFormula(source.getCellFormula());
                break;
            case BLANK:
                break;
            case ERROR:
                target.setCellErrorValue(source.getErrorCellValue());
                break;
        }
    }

    public static void convertXlsToXlsxNew(InputStream xlsInputStream, OutputStream xlsxOutputStream){
        try {
            HSSFWorkbook xlsWorkbook = new HSSFWorkbook(xlsInputStream);
            XSSFWorkbook xlsxWorkbook = new XSSFWorkbook();
            // 复制所有工作表
            for (int i = 0; i < xlsWorkbook.getNumberOfSheets(); i++) {
                HSSFSheet sheet = xlsWorkbook.getSheetAt(i);
                XSSFSheet newSheet = xlsxWorkbook.createSheet(sheet.getSheetName());
                copySheets(sheet, newSheet);
            }
            // 写入输出流
            xlsxWorkbook.write(xlsxOutputStream);
            xlsxWorkbook.close();
            xlsWorkbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copySheets(HSSFSheet source, XSSFSheet destination) {

        int maxColumnNum = 0;
        // 获取全部的合并单元格
        List<CellRangeAddress> cellRangeAddressList = source.getMergedRegions();
        for (int i = source.getFirstRowNum(); i <= source.getLastRowNum(); i++) {
            HSSFRow srcRow = source.getRow(i);
            XSSFRow destRow = destination.createRow(i);
            if (srcRow != null) {
                // 拷贝行
                copyRow(destination, srcRow, destRow, cellRangeAddressList);
                if (srcRow.getLastCellNum() > maxColumnNum) {
                    maxColumnNum = srcRow.getLastCellNum();
                }
            }
        }
        for (int i = 0; i <= maxColumnNum; i++) {
            destination.setColumnWidth(i, source.getColumnWidth(i));
        }

    }

    public static void copyRow(XSSFSheet destSheet, HSSFRow srcRow, XSSFRow destRow,
                               List<CellRangeAddress> cellRangeAddressList) {

        // 拷贝行高
        destRow.setHeight(srcRow.getHeight());

        for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {
            HSSFCell oldCell = srcRow.getCell(j);
            XSSFCell newCell = destRow.getCell(j);
            if (oldCell != null) {
                if (newCell == null) {
                    newCell = destRow.createCell(j);
                }
                // 拷贝单元格
                copyCell(oldCell, newCell, destSheet);

                // 获取原先的合并单元格
                CellRangeAddress mergedRegion = getMergedRegion(cellRangeAddressList, srcRow.getRowNum(),
                        (short) oldCell.getColumnIndex());

                if (mergedRegion != null) {
                    // 参照创建合并单元格
                    CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow(),
                            mergedRegion.getLastRow(), mergedRegion.getFirstColumn(), mergedRegion.getLastColumn());
                    destSheet.addMergedRegion(newMergedRegion);
                }
            }
        }

    }

    // 拷贝单元格
    public static void copyCell(HSSFCell oldCell, XSSFCell newCell, XSSFSheet destSheet) {
        HSSFCellStyle sourceCellStyle = oldCell.getCellStyle();
        XSSFCellStyle targetCellStyle = destSheet.getWorkbook().createCellStyle();

        if (sourceCellStyle == null) {
            sourceCellStyle = oldCell.getSheet().getWorkbook().createCellStyle();
        }


        // 设置对齐方式
        targetCellStyle.setAlignment(sourceCellStyle.getAlignment());
        targetCellStyle.setVerticalAlignment(sourceCellStyle.getVerticalAlignment());

        // 设置字体
        XSSFFont xssfFont = destSheet.getWorkbook().createFont();
        HSSFFont hssfFont = sourceCellStyle.getFont(oldCell.getSheet().getWorkbook());
        copyFont(xssfFont, hssfFont);
        targetCellStyle.setFont(xssfFont);
        // 文本换行
        targetCellStyle.setWrapText(sourceCellStyle.getWrapText());

        targetCellStyle.setBorderBottom(sourceCellStyle.getBorderBottom());
        targetCellStyle.setBorderLeft(sourceCellStyle.getBorderLeft());
        targetCellStyle.setBorderRight(sourceCellStyle.getBorderRight());
        targetCellStyle.setBorderTop(sourceCellStyle.getBorderTop());
        targetCellStyle.setBottomBorderColor(sourceCellStyle.getBottomBorderColor());
        targetCellStyle.setDataFormat(sourceCellStyle.getDataFormat());
        //targetCellStyle.setFillForegroundColor(sourceCellStyle.getFillForegroundColor());
        //targetCellStyle.setFillBackgroundColor(sourceCellStyle.getFillBackgroundColor());
        //targetCellStyle.setFillPattern(sourceCellStyle.getFillPattern());

        targetCellStyle.setHidden(sourceCellStyle.getHidden());
        targetCellStyle.setIndention(sourceCellStyle.getIndention());
        targetCellStyle.setLeftBorderColor(sourceCellStyle.getLeftBorderColor());
        targetCellStyle.setLocked(sourceCellStyle.getLocked());
        targetCellStyle.setQuotePrefixed(sourceCellStyle.getQuotePrefixed());
        targetCellStyle.setReadingOrder(ReadingOrder.forLong(sourceCellStyle.getReadingOrder()));
        targetCellStyle.setRightBorderColor(sourceCellStyle.getRightBorderColor());
        targetCellStyle.setRotation(sourceCellStyle.getRotation());

        newCell.setCellStyle(targetCellStyle);

        switch (oldCell.getCellType()) {
            case STRING:
                newCell.setCellValue(oldCell.getStringCellValue());
                break;
            case NUMERIC:
                newCell.setCellValue(oldCell.getNumericCellValue());
                break;
            case BLANK:
                newCell.setCellType(CellType.BLANK);
                break;
            case BOOLEAN:
                newCell.setCellValue(oldCell.getBooleanCellValue());
                break;
            case ERROR:
                newCell.setCellErrorValue(oldCell.getErrorCellValue());
                break;
            case FORMULA:
                newCell.setCellFormula(oldCell.getCellFormula());
                break;
            default:
                break;
        }
    }

    public static void copyFont(XSSFFont xssfFont, HSSFFont hssfFont) {
        xssfFont.setFontName(hssfFont.getFontName());
        xssfFont.setBold(hssfFont.getBold());
        xssfFont.setFontHeight(hssfFont.getFontHeight());
        xssfFont.setCharSet(hssfFont.getCharSet());
        xssfFont.setColor(hssfFont.getColor());
        xssfFont.setItalic(hssfFont.getItalic());
        xssfFont.setUnderline(hssfFont.getUnderline());
        xssfFont.setTypeOffset(hssfFont.getTypeOffset());
        xssfFont.setStrikeout(hssfFont.getStrikeout());
    }

    // 根据行列获取合并单元格
    public static CellRangeAddress getMergedRegion(List<CellRangeAddress> cellRangeAddressList, int rowNum, short cellNum) {
        for (int i = 0; i < cellRangeAddressList.size(); i++) {
            CellRangeAddress merged = cellRangeAddressList.get(i);
            if (merged.isInRange(rowNum, cellNum)) {
                // 已经获取过不再获取
                cellRangeAddressList.remove(i);
                return merged;
            }
        }
        return null;
    }


}
