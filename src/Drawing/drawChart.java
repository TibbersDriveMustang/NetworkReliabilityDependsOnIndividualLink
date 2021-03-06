package Drawing;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public class drawChart extends ApplicationFrame{
    private SystemStyle systemStyle;
	
	/**
     * 
     */
	public void centerChart(){
		RefineryUtilities.centerFrameOnScreen(this);
	}

	public enum SystemStyle{
		FixedP,UnFixedP
	}
	
    /**
     *
     * @param title  the frame title.
     */
	
	public drawChart(String title , double[] data, SystemStyle systemStyle) {

        super(title);
		this.systemStyle = systemStyle;
        CategoryDataset dataset = createDataset(data);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);

    }
    /**
     * Returns a dataset.
     * 
     * @return The dataset.
     */
    private CategoryDataset createDataset(double[] data) {
        
        // row keys...
        String series1 = "Reliability";
        //final String series2 = "Second";
        //final String series3 = "Third";

        // column keys...
        if(this.systemStyle == SystemStyle.FixedP){
        	String category1 = "P";
        }
        else if(this.systemStyle == SystemStyle.UnFixedP){
        	String category1 = "K";
        }
        //final String category2 = "Category 2";
        //final String category3 = "Category 3";
        //final String category4 = "Category 4";
        //final String category5 = "Category 5";

        // create the dataset...
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if(this.systemStyle == SystemStyle.FixedP){
        	for(int i = 0; i < 20; i++){        	
        		String temp = "0.05*" + (i + 1);
        		dataset.addValue(data[i], series1, temp);
        	}
        }
        else if(this.systemStyle == SystemStyle.UnFixedP){
        	for(int i = 0; i < 21; i++){        	
        		String temp = "" + i;
        		dataset.addValue(data[i], series1, temp);
        	}
        }
/*
        dataset.addValue(1.0, series1, "0.05");
        dataset.addValue(2.0, series1, "0.10");
        dataset.addValue(3.0, series1, "0.15");
        dataset.addValue(4.0, series1, category2);
        dataset.addValue(3.0, series1, category3);
        dataset.addValue(5.0, series1, category4);
        dataset.addValue(5.0, series1, category5);

        dataset.addValue(5.0, series2, category1);
        dataset.addValue(7.0, series2, category2);
        dataset.addValue(6.0, series2, category3);
        dataset.addValue(8.0, series2, category4);
        dataset.addValue(4.0, series2, category5);

        dataset.addValue(4.0, series3, category1);
        dataset.addValue(3.0, series3, category2);
        dataset.addValue(2.0, series3, category3);
        dataset.addValue(3.0, series3, category4);
        dataset.addValue(6.0, series3, category5);
*/        
        
        return dataset;
        
    }	
    /**
     * Creates a chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(CategoryDataset dataset) {
    	String axisLabel = "";
    	if(this.systemStyle == SystemStyle.FixedP){
    		axisLabel = "P";
    	}
    	else if(this.systemStyle == SystemStyle.UnFixedP){
    		axisLabel = "K";
    	}
        // create the chart...
        JFreeChart chart = ChartFactory.createBarChart(
            "System Reliability",         // chart title
            axisLabel,               // domain axis label
            "Reliability",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
        // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.red, 
            0.0f, 0.0f, Color.lightGray
        );
 /*       final GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.green, 
            0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp2 = new GradientPaint(
            0.0f, 0.0f, Color.red, 
            0.0f, 0.0f, Color.lightGray
        );
        */
        renderer.setSeriesPaint(0, gp0);
        //renderer.setSeriesPaint(1, gp1);
        //renderer.setSeriesPaint(2, gp2);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
        
    }    
/*    
    public static void main(final String[] args) {

        final drawChart demo = new drawChart("Bar Chart Demo");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
 */ 
	
}
