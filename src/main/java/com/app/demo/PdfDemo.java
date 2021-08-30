package com.app.demo;

import com.app.util.PdfChartsUtil;
import com.app.util.PdfUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class PdfDemo {

    public static void main(String[] args) {
        Document document = null;
        PdfWriter writer = null;

        try {
            Map<String, Object> map = PdfUtil.createPdf("demo");

            document = (Document) map.get("document");
            writer = (PdfWriter) map.get("writer");

            String content = "科技有限公司";
            PdfUtil.writeParagraphToPdf(document, 24, BaseColor.BLACK, content);

            content = "国家级高兴技术企业";
            PdfUtil.writeParagraphToPdf(document, 12, PdfUtil.LIGHT_BLUE, content);

            content = "创新层";
            PdfUtil.writeParagraphToPdf(document, 12, BaseColor.DARK_GRAY, content);

            content = "技术流评价报告";
            PdfUtil.writeParagraphToPdf(document, 20, BaseColor.DARK_GRAY, content);

            content = "报告编号   12345678909876543210";
            PdfUtil.writeParagraphToPdf(document, 12, BaseColor.DARK_GRAY, content);

            content = "查询时间   2021-08-30 11:11:11";
            PdfUtil.writeParagraphToPdf(document, 12, BaseColor.DARK_GRAY, content);

            content = "查询机构   兴业银行";
            PdfUtil.writeParagraphToPdf(document, 12, BaseColor.DARK_GRAY, content);

            content = "查询机构   兴业银行";
            PdfUtil.writeParagraphToPdf(document, 12, BaseColor.DARK_GRAY, content);

            content = "\"技术流\"评分";
            PdfUtil.writeParagraphToPdf(document, 18, PdfUtil.LIGHT_BLUE, content);

            content = "技术流评分技术流评分技术流评分技术流评分技术流评分技术流评分技术流评分技术流评分技术流评分技术流评分技术流评分技术流评分技术流评分技术流评分技术流评分技术流评分";
            PdfUtil.writeParagraphToPdf(document, 12, BaseColor.DARK_GRAY, content);

            content = "113";
            PdfUtil.writeParagraphToPdf(document, 28, BaseColor.BLUE, content);

            content = "AAA级（排名前5%）";
            PdfUtil.writeParagraphToPdf(document, 12, PdfUtil.LIGHT_BLUE, content);

            content = "评估时间：2021-08-30";
            PdfUtil.writeParagraphToPdf(document, 12, BaseColor.DARK_GRAY, content);

            document.add(new Paragraph("\n\r"));

            content = "总览";
            PdfUtil.writeParagraphToPdf(document, 18, PdfUtil.LIGHT_BLUE, content);

            content = "专利信息";
            PdfUtil.writeParagraphToPdf(document, 17, BaseColor.DARK_GRAY, content);

            PdfUtil.writeTableToPdf(document, null, null);

            DefaultCategoryDataset categoryDataset = PdfChartsUtil.categoryDataset(null, null);
            JFreeChart lineChart = PdfChartsUtil.lineChart("title", "xName", "yName", categoryDataset);
            JFreeChart barChart = PdfChartsUtil.barChart("title", "xName", "yName", categoryDataset);

            DefaultPieDataset pieDataset = PdfChartsUtil.pieDataSet(null);
            JFreeChart pieChart = PdfChartsUtil.pieChart("title", pieDataset);

            List<JFreeChart> charts = new ArrayList<>();
            charts.add(lineChart);
            charts.add(barChart);
            charts.add(pieChart);
            PdfUtil.writeChartsToPdf(document, charts);

            log.info("PDF文档生成成功！");

        } catch (Exception e) {
            log.error("PDF文档生成错误！");

        } finally {
            PdfUtil.closePdf(document, writer);
        }
    }

}