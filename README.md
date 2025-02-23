# SommeParalleleAvecThreads

![Java](https://img.shields.io/badge/Java-17-blue)
![GitHub](https://img.shields.io/badge/GitHub-Repository-brightgreen)

**SommeParalleleAvecThreads** est un programme Java qui calcule la somme des éléments d'un tableau en utilisant un pool de threads pour paralléliser le travail. Le programme divise le tableau en plusieurs plages et utilise des threads pour calculer la somme de chaque plage de manière concurrente.

---

## Fonctionnalités

1. **Division du tableau** : Le tableau est divisé en plusieurs plages, chacune traitée par un thread distinct.
2. **Calcul parallèle** : Chaque thread calcule la somme de sa plage assignée.
3. **Somme totale** : Les résultats partiels sont additionnés pour obtenir la somme totale du tableau.

---

## Utilisation

1. Compilez et exécutez la classe `Main`.
2. Observez la somme totale du tableau affichée dans la console.

---

## Exemple de code

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Sommeur implements Runnable {
    private int[] tableau;
    private int debut;
    private int fin;
    private int somme;

    public Sommeur(int[] tableau, int debut, int fin) {
        this.tableau = tableau;
        this.debut = debut;
        this.fin = fin;
        this.somme = 0;
    }

    @Override
    public void run() {
        for (int i = debut; i < fin; i++) {
            somme += tableau[i];
        }
    }

    public int getSomme() {
        return somme;
    }
}

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
    }
}