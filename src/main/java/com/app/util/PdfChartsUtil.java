package com.app.util;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.AxisLabelLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.util.List;
import java.util.Map;

@Slf4j
public class PdfChartsUtil {

    public static Font FONT_10 = new Font("黑体", Font.PLAIN, 10);
    public static Font FONT_12 = new Font("黑体", Font.PLAIN, 12);
    public static Font FONT_14 = new Font("黑体", Font.PLAIN, 14);
    public static Font FONT_18 = new Font("黑体", Font.PLAIN, 18);
    public static Font FONT_20 = new Font("黑体", Font.PLAIN, 20);
    public static Font FONT_30 = new Font("黑体", Font.PLAIN, 30);

    public static DefaultCategoryDataset categoryDataset(List<String> xAxisList, Map<String, String> dataList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        final String fiat = "FIAT";
        final String audi = "AUDI";
        final String ford = "FORD";
        final String speed = "Speed";
        final String millage = "Millage";
        final String userrating = "User Rating";
        final String safety = "safety";
        final String aaa = "aaa";

        dataset.addValue(1.0, fiat, speed);
        dataset.addValue(3.0, fiat, userrating);
        dataset.addValue(5.0, fiat, millage);
        dataset.addValue(5.0, fiat, safety);
        dataset.addValue(6.0, fiat, aaa);

        dataset.addValue(5.0, audi, speed);
        dataset.addValue(6.0, audi, userrating);
        dataset.addValue(10.0, audi, millage);
        dataset.addValue(4.0, audi, safety);
        dataset.addValue(5.0, audi, aaa);

        dataset.addValue(4.0, ford, speed);
        dataset.addValue(2.0, ford, userrating);
        dataset.addValue(3.0, ford, millage);
        dataset.addValue(6.0, ford, safety);
        dataset.addValue(8.0, ford, aaa);

        if (xAxisList != null && !xAxisList.isEmpty() && dataList != null && !dataList.isEmpty()) {
            for (Map.Entry<String, String> entry : dataList.entrySet()) {
                for (String xAxis : xAxisList) {
                    dataset.addValue(Double.valueOf(entry.getValue()), entry.getKey(), xAxis);
                }
            }
        }

        return dataset;

    }

    public static DefaultPieDataset pieDataSet(Map<String, String> dataList) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        dataset.setValue("IPhone 5s", new Double(20));
        dataset.setValue("SamSung Grand", new Double(20));
        dataset.setValue("MotoG", new Double(40));
        dataset.setValue("Nokia Lumia", new Double(10));

        if (dataList != null && !dataList.isEmpty()) {
            for (Map.Entry<String, String> entry : dataList.entrySet()) {
                dataset.setValue(entry.getKey(), Double.valueOf(entry.getValue()));
            }
        }

        return dataset;
    }

    public static JFreeChart lineChart(String title, String xName, String yName, DefaultCategoryDataset dataset) {
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
//        mChartTheme.setLargeFont();
        JFreeChart jFreeChart = ChartFactory.createLineChart(title, xName, yName, dataset, PlotOrientation.VERTICAL, false, true, false);

        //设置图形的标题
        TextTitle jtitle = new TextTitle(title);
        jtitle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        jtitle.setPadding(0, 20, 0, 0);
        jtitle.setFont(FONT_30);
        jtitle.setPaint(Color.GRAY);
        jFreeChart.setTitle(jtitle);

        CategoryPlot plot = (CategoryPlot) jFreeChart.getPlot();
        plot.setBackgroundPaint(null);
        plot.setRangeGridlinePaint(Color.DARK_GRAY);//背景底部横虚线
        plot.setOutlinePaint(null);//边界线

//        CategoryPlot plot=jFreeChart.getCategoryPlot();//获取图形区域对象
        //------------------------------------------获取X轴
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(FONT_18);//设置X轴的标题的字体
        domainAxis.setTickLabelFont(FONT_18);//设置X轴坐标上的字体
//        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        //-----------------------------------------获取Y轴
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelFont(FONT_12);//设置Y轴坐标上的标题字体
        rangeAxis.setLabel(yName);
        rangeAxis.setLabelLocation(AxisLabelLocation.HIGH_END);
//        rangeAxis.setLabelAngle(1.57);
        //设置图样的文字样式
        //jFreeChart.getLegend().setItemFont(new Font("黑体",Font.BOLD ,16));

        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setDefaultItemLabelsVisible(true);
        //设置数据显示位置
        renderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.INSIDE9, TextAnchor.BASELINE_CENTER));
        //显示折点相应数据
        //renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        // 自定义线段的绘制颜色
        Color color[] = new Color[dataset.getRowCount()];
        Color[] colors = new Color[]{
                new Color(0, 51, 102),    //'#003366',
                new Color(229, 50, 62),   //'#e5323e',
                new Color(97, 160, 168),  //'#61A0A8',
                new Color(212, 130, 101), //'#D48265',
                new Color(145, 199, 174), //'#91C7AE',
                new Color(116, 159, 131), //'#749F83',
                new Color(202, 134, 34), //'#CA8622',
                new Color(189, 162, 154), //'#BDA29A',
                new Color(110, 112, 116), //'#6E7074',
                new Color(84, 101, 112), //'#546570',
                new Color(196, 204, 211), //'#C4CCD3',
                new Color(255, 186, 179), //'#FFBAB3',
                new Color(36, 216, 131)  //'#24D809'
        };
        for (int i = 0; i < color.length; i++) {
            color[i] = colors[i % colors.length];
            renderer.setSeriesPaint(i, color[i]);
        }

        return jFreeChart;
    }

    public static JFreeChart barChart(String title, String xName, String yName, DefaultCategoryDataset dataset) {
        JFreeChart jFreeChart = ChartFactory.createBarChart(title, xName, yName, dataset, PlotOrientation.VERTICAL, false, true, false);

        //设置图形的标题
        TextTitle jtitle = new TextTitle(title);
        jtitle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        jtitle.setPadding(0, 20, 0, 0);
        jtitle.setFont(FONT_30);
        jtitle.setPaint(Color.GRAY);
        jFreeChart.setTitle(jtitle);

        CategoryPlot plot = jFreeChart.getCategoryPlot();//获取图形区域对象
        plot.setBackgroundPaint(Color.WHITE);
        plot.setBackgroundAlpha(0.5f);
        plot.setRangeGridlinePaint(Color.DARK_GRAY);
        plot.setOutlineVisible(false);
        //------------------------------------------获取X轴
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(FONT_12);//设置X轴的标题的字体
        domainAxis.setTickLabelFont(FONT_10);//设置X轴坐标上的字体
//        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        
        //-----------------------------------------获取Y轴
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelFont(FONT_12);//设置Y轴坐标上的标题字体
        rangeAxis.setLabel(yName);
        rangeAxis.setLabelLocation(AxisLabelLocation.HIGH_END);
//        rangeAxis.setLabelAngle(1.57);
        //设置图样的文字样式
        //jFreeChart.getLegend().setItemFont(new Font("黑体",Font.PLAIN ,16));
        //设置图形的标题
        jFreeChart.getTitle().setFont(FONT_20);

        BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
        barRenderer.setMaximumBarWidth(0.025);
        StandardBarPainter barPrinter = new StandardBarPainter();
        barRenderer.setBarPainter(barPrinter);
        barRenderer.setSeriesPaint(0, new Color(50, 50, 100));
        //barRenderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());//显示每个柱的数值
        barRenderer.setDefaultItemLabelsVisible(true);

        return jFreeChart;
    }

    public static JFreeChart pieChart(String title, DefaultPieDataset dataset) {
        JFreeChart jFreeChart = ChartFactory.createRingChart(title, dataset, false, false, false);

        //设置图形的标题
        TextTitle jtitle = new TextTitle(title);
        jtitle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        jtitle.setPadding(0, 20, 0, 0);
        jtitle.setFont(FONT_30);
        jtitle.setPaint(Color.GRAY);
        jFreeChart.setTitle(jtitle);

        //jFreeChart.getLegend().setItemFont(FONT_10);
        //jFreeChart.getLegend().setHorizontalAlignment(HorizontalAlignment.CENTER);
        //jFreeChart.getLegend().setVisible(false);

        // 环形图
        RingPlot ringplot = (RingPlot) jFreeChart.getPlot();
        ringplot.setOutlineVisible(false);
        //{2}表示显示百分比
        //ringplot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}"));
        ringplot.setBackgroundPaint(Color.WHITE);
        //设置标签样式
        ringplot.setLabelFont(FONT_10);
        ringplot.setSimpleLabels(false);
        ringplot.setLabelLinkPaint(Color.LIGHT_GRAY);
        ringplot.setLabelOutlinePaint(Color.WHITE);
        ringplot.setLabelLinksVisible(true);
        ringplot.setLabelShadowPaint(null);
        //ringplot.setLabelOutlinePaint(new Color(0,true));
        ringplot.setLabelBackgroundPaint(new Color(0, true));
        //ringplot.setLabelPaint(Color.DARK_GRAY);

        ringplot.setSectionOutlinePaint("", Color.WHITE);
        ringplot.setSeparatorsVisible(false);
        ringplot.setSeparatorPaint(Color.WHITE);
        ringplot.setShadowPaint(null);
        ringplot.setSectionDepth(0.4);
        ringplot.setStartAngle(90);
        ringplot.setDrawingSupplier(new DefaultDrawingSupplier(
                new Paint[]{
                        new Color(0, 51, 102),    //'#003366',
                        new Color(229, 50, 62),   //'#e5323e',
                        new Color(97, 160, 168),  //'#61A0A8',
                        new Color(212, 130, 101), //'#D48265',
                        new Color(145, 199, 174), //'#91C7AE',
                        new Color(116, 159, 131), //'#749F83',
                        new Color(202, 134, 34), //'#CA8622',
                        new Color(189, 162, 154), //'#BDA29A',
                        new Color(110, 112, 116), //'#6E7074',
                        new Color(84, 101, 112), //'#546570',
                        new Color(196, 204, 211), //'#C4CCD3',
                        new Color(255, 186, 179), //'#FFBAB3',
                        new Color(36, 216, 131)  //'#24D809'
                },
                DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));

        return jFreeChart;
    }

}