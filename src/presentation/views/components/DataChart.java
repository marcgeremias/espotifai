package presentation.views.components;

import business.entities.Genre;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class DataChart extends JComponent {
    private final Integer DATA_X = 300;
    private final Integer TEXT_X = 200;
    private final Integer HEIGTH = 20;
    private final Integer DISTANCE = 26;
    private final Integer DATA_Y_DISTANCE = 50;

    private final Integer AXIS_WEIGHT = 600;
    private final Integer AXIS_HEIGHT = 1;

    private int[] data;
    private int y;
    private int textY;

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
        textY = 30;
        String[] array = new String[16];
        for (Genre genres : Genre.values()){
            array[i] = genres.toString();
            i++;
        }

        int value = getHighestValue();
        int partition = 0;
        if (value > 0) {
            partition = AXIS_WEIGHT / value;
        }

        Shape shape = new Rectangle2D.Float(DATA_X,y, this.data[0] + (partition * this.data[0]), HEIGTH);

        graphBar.setColor(Color.LIGHT_GRAY);
        graphBar.drawString(array[0], TEXT_X, textY);
        graphBar.drawString(Integer.toString(this.data[0]), DATA_X + this.data[0] + (partition * this.data[0]) + 10, textY);
        graphBar.fill(shape);
        graphBar.draw(shape);

        for (i = 1; i < array.length; i++) {

            shape = new Rectangle2D.Float(DATA_X,y + DISTANCE, this.data[i] + (partition * this.data[i]) , HEIGTH);

            graphBar.setColor(Color.LIGHT_GRAY);
            graphBar.drawString(array[i], TEXT_X, textY + DISTANCE);
            graphBar.drawString(Integer.toString(this.data[i]), DATA_X + this.data[i] + (partition * this.data[i]) + 10, textY + DISTANCE);
            graphBar.fill(shape);
            graphBar.draw(shape);
            y += DISTANCE;
            textY += DISTANCE;
        }

        Shape axis = new Rectangle2D.Float(DATA_X, textY + DISTANCE, AXIS_WEIGHT, AXIS_HEIGHT);
        graphBar.draw(axis);

        int data_x = DATA_X, j = 0, data_y = textY + DATA_Y_DISTANCE;

        graphBar.drawString(Integer.toString(0), data_x, data_y);
        graphBar.draw(axis);

        for (j = 1; j < value + 1; j++) {
            graphBar.drawString(Integer.toString(j), data_x + partition, data_y);
            graphBar.draw(axis);
            data_x += partition;
        }
    }

    /**
     * Method that loads the data from the persistence into the data chart for the statistics.
     * @param data: Array of Integers containing the number of songs for each genre.
     */
    public void loadData(int[] data){
        this.data = data;
    }

    /*
     * Method that returns the maximum number of songs between all genres in the database.
     * @return Integer with the representation of the maximum number.
     */
    private int getHighestValue() {
        int value = 0;

        for (int i = 0; i < data.length; i++) {
            if (data[i] > value){
                value = data[i];
            }
        }
        return value;
    }
}
