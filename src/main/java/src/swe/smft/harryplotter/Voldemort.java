package src.swe.smft.harryplotter;

public interface Voldemort {

    /**
     * Colui che non deve essere istanziato, ergo il nome
     * Va usato tramite la classe Horcrux (adapter pattern)
     */

    void plotSimulation();
    void plotReliability();
    void plotAvarage();
    void plotTree();

}
