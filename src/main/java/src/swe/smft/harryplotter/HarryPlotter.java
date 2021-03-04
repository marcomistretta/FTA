package src.swe.smft.harryplotter;

public class HarryPlotter {

    /**
     * stampa magicamente i dati! Amo la magia
     */

    private static HarryPlotter mainCharacter = null;

    public static HarryPlotter getInstance() {
        if (mainCharacter != null)
            return mainCharacter;
        mainCharacter = new HarryPlotter();
        return mainCharacter;
    }

    public void plotSimulation() {

    }

    public void plotReliability() {

    }

    public void plotAvarage() {

    }

    public void plotTree() {

    }

}
