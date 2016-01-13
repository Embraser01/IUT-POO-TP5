package controllers;


import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class DSAT {

    private Graph graph;
    private ArrayList<Node> nodes;
    private ArrayList<Color> colors;

    public DSAT() {
        this.colors = getUniqueColors(20);
    }

    public void init(Graph graph) {
        this.graph = graph;

        this.nodes = new ArrayList<>(graph.getNodeSet());
    }

    public int compute() {
        /* Algo Launch */

        this.order(); // Step 1

        this.colorNode(nodes.get(0), colors.get(0)); // Step 2

        while (!this.isOver()) this.colorNodeOpti(this.getMaxDSat()); // Step 3 & 4

        return this.getChromaticNumber();
    }

    private int dSat(Node n) {
        ArrayList<Color> listColor = new ArrayList<>();
        Iterator<Node> iterator = n.getNeighborNodeIterator();

        while (iterator.hasNext()) {
            Node childNode = iterator.next();
            if (!listColor.contains(childNode.getAttribute("dsat.color"))) listColor.add(childNode.getAttribute("dsat.color"));
        }

        return listColor.size();
    }


    private Node getMaxDSat() {

        // STEP 3

        int maxDSAT = 0;
        ArrayList<Node> maxDSatNode = new ArrayList<>();

        int tmp;

        for (Node n : this.nodes) {
            if(n.getAttribute("dsat.color") == null) {
                tmp = dSat(n);
                if (tmp > maxDSAT) {
                    maxDSatNode.clear();
                    maxDSatNode.add(n);
                    maxDSAT = tmp;
                } else if (tmp == maxDSAT) {
                    maxDSatNode.add(n);
                }
            }
        }

        if (maxDSatNode.size() > 1) {
            int maxDegree = 0;
            int idMax = 0;

            for (int i = 0; i < maxDSatNode.size(); i++) {
                if (maxDSatNode.get(i).getDegree() > maxDegree) {
                    maxDegree = maxDSatNode.get(i).getDegree();
                    idMax = i;
                }
            }

            return maxDSatNode.get(idMax);

        } else {
            return maxDSatNode.get(0);
        }
    }


    private void colorNodeOpti(Node n) {
        ArrayList<Color> listColor = new ArrayList<>();
        Iterator<Node> iterator = n.getNeighborNodeIterator();

        while (iterator.hasNext()) {
            Node childNode = iterator.next();
            if (!listColor.contains(childNode.getAttribute("dsat.color"))) listColor.add(childNode.getAttribute("dsat.color"));
        }

        for (Color c : this.colors) {
            if (!listColor.contains(c)) {
                this.colorNode(n, c);
            }
        }
    }


    private boolean isOver() {
        Iterator<Node> iterator = this.graph.getNodeIterator();

        while (iterator.hasNext()) {
            Node n = iterator.next();
            if (n.getAttribute("dsat.color") == null) return false;
        }
        return true;
    }

    private void order() {
        Collections.sort(this.nodes, (o1, o2) -> {
            if (o1.getDegree() == o2.getDegree()) return 0;
//                return (o1.getDegree() > o2.getDegree()) ? 1 : -1; // Croissant
            return (o1.getDegree() < o2.getDegree()) ? 1 : -1; // Décroissant
        });
    }


    private void colorNode(Node n, Color c) {
        n.addAttribute("dsat.color", c);
    }


    public int getChromaticNumber() {
        ArrayList<Color> listColor = new ArrayList<>();

        Iterator<Node> iterator = this.graph.getNodeIterator();

        while (iterator.hasNext()) {
            Node n = iterator.next();
            if (!listColor.contains(n.getAttribute("dsat.color"))) listColor.add(n.getAttribute("dsat.color"));
        }

        return listColor.size();
    }


    public void colorGraph() {
        Iterator<Node> iterator = this.graph.getNodeIterator();

        while (iterator.hasNext()) {
            Node n = iterator.next();
            Color c = n.getAttribute("dsat.color");
            n.setAttribute("ui.style", "fill-color:rgba(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ",200);");
        }
    }

    // Helper function
    private ArrayList<Color> getUniqueColors(int amount) {
        final int lowerLimit = 0x10;
        final int upperLimit = 0xE0;
        final int colorStep = (int) ((upperLimit - lowerLimit) / Math.pow(amount, 1f / 3));

        final ArrayList<Color> colors = new ArrayList<>(amount);

        for (int R = lowerLimit; R < upperLimit; R += colorStep)
            for (int G = lowerLimit; G < upperLimit; G += colorStep)
                for (int B = lowerLimit; B < upperLimit; B += colorStep) {
                    if (colors.size() >= amount) { //The calculated step is not very precise, so this safeguard is appropriate
                        return colors;
                    } else {
                        colors.add(new Color(R, G, B));
                    }
                }
        return colors;
    }
}
