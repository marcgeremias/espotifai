package presentation.views.components;

import business.entities.Genre;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class DataChart extends JComponent {
    public final static Integer X = 300;
    public final static Integer TEXT_X = 200;
    public final static Integer WEIGHT = 0;
    public final static Integer HEIGTH = 20;

    public final static Integer MULTIPLIER = 10;
    public final static Integer DISTANCE = 30;

    private int[] data;
    private int y;
    private int text_y;

    /**
     * Method that paints the component which we are creating for the graphic
     * @param g: Graphic component which contains the area we are filling
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D graphBar = (Graphics2D) g;
        graphBar.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int i = 0;
        y = 10;
        text_y = 30;
        String[] array = new String[16];
        for (Genre genres : Genre.values()){
            array[i] = genres.toString();
            i++;
        }

        Shape data = new Rectangle2D.Float(X,y, WEIGHT + this.data[0] * MULTIPLIER, HEIGTH);

        graphBar.setColor(Color.LIGHT_GRAY);
        int dataValue = X + this.data[0] * MULTIPLIER;
        graphBar.drawString(array[0], TEXT_X, text_y);
        graphBar.drawString(Integer.toString(this.data[0]), dataValue + 10, text_y);
        graphBar.fill(data);
        graphBar.draw(data);

        for (i = 1; i < 16; i++) {

            data = new Rectangle2D.Float(X,y + DISTANCE, WEIGHT + this.data[i] * MULTIPLIER , HEIGTH);

            dataValue = X + this.data[i] * MULTIPLIER;
            graphBar.setColor(Color.LIGHT_GRAY);
            graphBar.drawString(array[i], TEXT_X, text_y + DISTANCE);
            graphBar.drawString(Integer.toString(this.data[i]), dataValue + 10, text_y + DISTANCE);
            graphBar.fill(data);
            graphBar.draw(data);
            y += DISTANCE;
            text_y += DISTANCE;

        }
    }

    public void loadData(int[] data){
        this.data = data;
    }
}
