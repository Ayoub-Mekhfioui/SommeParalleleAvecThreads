import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[] tableau = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        int nombreThreads = 4; // Nombre de threads dans le pool
        int taillePlage = tableau.length / nombreThreads;

        ExecutorService pool = Executors.newFixedThreadPool(nombreThreads);
        Sommeur[] sommeurs = new Sommeur[nombreThreads];

        // Créer et exécuter les threads
        for (int i = 0; i < nombreThreads; i++) {
            int debut = i * taillePlage;
            int fin = (i == nombreThreads - 1) ? tableau.length : debut + taillePlage;
            sommeurs[i] = new Sommeur(tableau, debut, fin);
            pool.execute(sommeurs[i]);
        }

        // Attendre que tous les threads terminent
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        // Calculer la somme totale
        int sommeTotale = 0;
        for (Sommeur sommeur : sommeurs) {
            sommeTotale += sommeur.getSomme();
        }

        System.out.println("Somme totale du tableau: " + sommeTotale);
    }}