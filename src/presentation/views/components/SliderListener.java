package presentation.views.components;

/**
 * Public interface that notifies when a slider position has changed
 */
public interface SliderListener {

    /**
     * This method will be called when the position of the slider is updated in any way the view containing the observer
     * desires
     * @param sliderPos integer containing the current slider position
     * @param source String containing the key of the source of the change
     */
    void sliderPositionChanged(int sliderPos, String source);

}
