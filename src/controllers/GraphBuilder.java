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
                return Cycle(params[0]);
            case Chaine:
                return Chaine(params[0]);
            case Tore:
                return Tore(params[0]);
            case Grille_carree:
                return Grille_carree(params[0]);
            case Arbre_n_aire_complet:
                return Arbre_n_aire_complet(params[0], params[1]);
            case Graphe_aleatoire:
                return Graphe_aleatoire(params[0], params[1]);
            default:
                return null;
        }
    }

    private static void algoInit(Graph graph) {

        String css = "edge .nointree {size:1px;fill-color:gray;} " +
                "edge .intree {size:3px;fill-color:black;}";

        graph.addAttribute("ui.stylesheet", css);
    }

    private static Graph Cycle(int nb_sommets) {
        Graph graph = new SingleGraph("Cycle");
        algoInit(graph);
        for (int i = 0; i < nb_sommets; i++) {
            graph.addNode(Integer.toString(i));
        }

        for (int i = 0; i < nb_sommets; i++) {
            graph.addEdge(Integer.toString(i) + Integer.toString((i + 1) % nb_sommets), Integer.toString(i), Integer.toString((i + 1) % nb_sommets));
        }
        return graph;
    }

    private static Graph Chaine(int nb_sommets) {

        Graph graph = new SingleGraph("Chaine");
        algoInit(graph);
        for (int i = 0; i < nb_sommets; i++) {
            graph.addNode(Integer.toString(i));
        }

        for (int i = 0; i < nb_sommets - 1; i++) {
            graph.addEdge(Integer.toString(i) + Integer.toString((i + 1) % nb_sommets), Integer.toString(i), Integer.toString(i + 1));
        }
        return graph;
    }

    private static Graph Tore(int taille) {
        Graph graph = new SingleGraph("grid");
        Generator gen = new GridGenerator(false, true);
        algoInit(graph);
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
        algoInit(graph);
        gen.addSink(graph);
        gen.begin();
        for (int i = 0; i < taille_cote; i++) {
            gen.nextEvents();
        }
        gen.end();
        return graph;
    }

    private static Graph Arbre_n_aire_complet(int height, int nb_child) {
        Graph graph = new SingleGraph("Arbre n-aire");
        algoInit(graph);
        graph.addNode(Integer.toString(height + 1) + "0");

        createChild(height, nb_child, graph, Integer.toString(height + 1) + "0");
        return graph;
    }

    private static void createChild(int height, int nb_child, Graph graph, String id) {
        if (height <= 0) return;

        for (int i = 0; i < nb_child; i++) {
            graph.addNode(id + Integer.toString(i));
        }

        for (int i = 0; i < nb_child; i++) {
            graph.addEdge("E" + id + Integer.toString(i), id, id + Integer.toString(i));

            createChild(height - 1, nb_child, graph, id + Integer.toString(i));
        }
    }

    private static Graph Graphe_aleatoire(int nb_sommet, int degree) {

        Graph graph = new SingleGraph("Random");
        algoInit(graph);
        Generator gen = new RandomGenerator(degree);
        gen.addSink(graph);
        gen.begin();
        for (int i = 0; i < nb_sommet; i++)
            gen.nextEvents();
        gen.end();

        return graph;
    }
}
