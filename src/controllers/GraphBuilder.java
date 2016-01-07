package controllers;

import models.GraphsType;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;


public class GraphBuilder {

    public static Graph make(GraphsType type, int... params) {

        switch (type) {
            case Cycle:
                return Cycle();
            case Chaine:
                return Chaine();
            case Tore:
                return Tore(params[0]);
            case Grille_carree:
                return Grille_carree(params[0]);
            case Arbre_n_aire_complet:
                return Arbre_n_aire_complet();
            case Graphe_aleatoire:
                return Graphe_aleatoire(params[0], params[1]);
            default:
                return null;
        }
    }

    private static Graph Cycle() {
        return null;
    }

    private static Graph Chaine() {
        return null;
    }

    private static Graph Tore(int taille) {
        Graph graph = new SingleGraph("grid");
        Generator gen = new GridGenerator(false, true);

        gen.addSink(graph);
        gen.begin();
        for (int i = 0; i < taille; i++) {
            gen.nextEvents();
        }
        gen.end();
        return graph;
    }

    private static Graph Grille_carree(int taille_cote) {

        Graph graph = new SingleGraph("grid");
        Generator gen = new GridGenerator();

        gen.addSink(graph);
        gen.begin();
        for (int i = 0; i < taille_cote; i++) {
            gen.nextEvents();
        }
        gen.end();
        return graph;
    }

    private static Graph Arbre_n_aire_complet() {
        return null;
    }

    private static Graph Graphe_aleatoire(int nb_sommet, int degree) {

        Graph graph = new SingleGraph("Random");
        Generator gen = new RandomGenerator(degree);
        gen.addSink(graph);
        gen.begin();
        for (int i = 0; i < nb_sommet; i++)
            gen.nextEvents();
        gen.end();
        return graph;
    }
}
