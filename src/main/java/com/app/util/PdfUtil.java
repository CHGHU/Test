package com.app.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Jpeg;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class PdfUtil {

    public static final BaseColor LIGHT_BLUE = new BaseColor(2, 151, 215);
    public static final BaseColor LIGHT_GREEN = new BaseColor(187, 231, 240);
    public static final BaseColor LIGHT_GRAY = new BaseColor(239, 239, 239);

    public static Map<String, Object> createPdf(String pdfFileName) throws FileNotFoundException, DocumentException {
        Map<String, Object> result = new HashMap<>();

        // 创建一个文档（默认大小A4，边距36, 36, 36, 36）
        Document document = new Document();
        // 设置文档大小
        document.setPageSize(PageSize.A3);
        // 设置边距，单位都是像素，换算大约1厘米=28.33像素
        document.setMargins(50, 50, 50, 50);

        // 设置pdf生成的路径
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\demo.pdf");
        // 创建writer，通过writer将文档写入磁盘
        PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);

        // 打开文档，只有打开后才能往里面加东西
        document.open();

        // 设置作者
        document.addAuthor("作者");
        // 设置创建者
        document.addCreator("作者");
        // 设置主题
        document.addSubject("测试");
        // 设置标题
        document.addTitle("测试");

        result.put("document", document);
        result.put("writer", writer);

        return result;
    }

    public static void closePdf(Document document, PdfWriter writer) {
        try {
            // 关闭文档，才能输出
            document.close();
            writer.close();

        } catch (Exception e) {
            log.error("PDF文档关闭错误！", e);
        }
    }

    public static void writeParagraphToPdf(Document document, int fontSize, BaseColor fontColor, String content) throws DocumentException {
        // 定义字体
        Font font = FontFactory.getFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED, 10, Font.UNDEFINED, BaseColor.GRAY);

        // 增加一个段落
        Paragraph paragraph = new Paragraph(content, font);
        paragraph.setSpacingAfter(10f);
        document.add(paragraph);
    }

    public static void writeTableToPdf(Document document, float[] columnWidths, List<Map<String, String>> datas, boolean titleFlag, boolean indexFlag) throws DocumentException {

        // 长宽
        int rowNum = datas.size();
        int columnNum = columnWidths.length;

        // 定义字体
        Font font = FontFactory.getFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED, 10, Font.UNDEFINED, BaseColor.GRAY);

        // 创建表格，5列的表格
        PdfPTable table = new PdfPTable(8);
        table.setTotalWidth(PageSize.A3.getWidth());
        table.setLockedWidth(true);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);

        // 表格宽度
        table.setWidths(columnWidths);
        table.setWidthPercentage(100);

        // 添加单元格
        PdfPCell cell;
        String content;
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {

                if (indexFlag) {
                    if (j == 0) {
                        content = i == 0 ? "序号" : String.valueOf(titleFlag ? i : i + 1);
                    } else {
                        content = datas.get(i).get(j - 1);
                    }
                } else {
                    content = datas.get(i).get(j);
                }

                cell = new PdfPCell(new Phrase(content, font));
                if (titleFlag && i == 0) {
                    cell.setBackgroundColor(LIGHT_GREEN);
                }

                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.BOTTOM);
                cell.setBorderColorBottom(LIGHT_GRAY);
                cell.setBorderWidthBottom(0.1f);
                cell.setPadding(3f);
                cell.setMinimumHeight(20f);
                table.addCell(cell);
            }
        }

        document.add(table);
    }

    public static void writeChartsToPdf(Document document, List<JFreeChart> charts) throws IOException, DocumentException {
        ByteArrayOutputStream imgOutputStream;
        Image image;

        for (JFreeChart chart : charts) {
            imgOutputStream = new ByteArrayOutputStream();
            ChartUtils.writeChartAsJPEG(imgOutputStream, chart, 1200, 600);
            image = new Jpeg(imgOutputStream.toByteArray());

            image.setAlignment(Image.MIDDLE | Image.TEXTWRAP);
            image.setBorder(Image.BOX);
            image.setBorderWidth(10);
            image.setBorderColor(BaseColor.WHITE);
            image.scaleToFit(600, 300);

            document.add(image);
        }
    }

}