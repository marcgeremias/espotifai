package presentation.controllers;

public interface HomeListener {
    /**
     * Method to change the view with the one chosen in the card
     * @param card the name put on the card layout that referes to a specific view
     */
    void changeView(String card);
}
