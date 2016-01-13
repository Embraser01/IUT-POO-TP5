package views;

import controllers.GraphBuilder;
import models.GraphsType;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

import javax.swing.*;
import java.awt.*;


public class GraphView extends JPanel {

    public final static int MAX_WEIGHT = 100;

    private Graph graph;
    private GraphsType graphsType;

    public GraphView() {
        this.setGraph(GraphBuilder.make(GraphsType.Graphe_aleatoire, 100, 2), GraphsType.Graphe_aleatoire);
    }

    public Graph getGraph() {
        return graph;
    }

    public GraphsType getGraphsType() {
        return graphsType;
    }

    public void setGraph(Graph graph, GraphsType graphsType) {

        this.graph = graph;
        this.graphsType = graphsType;

        this.removeAll();

        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);

        viewer.enableAutoLayout();
        View view = viewer.addDefaultView(false);   // false indicates "no JFrame".
        view.setPreferredSize(new Dimension(500, 500));
        this.add(view);
        this.revalidate();
        this.repaint();
    }

    public String getName() {
        return graphsType.toString();
    }

    public double getAVG() {
        return Toolkit.averageDegree(graph);
    }

    public int getMax() {
        int max = 0;

        for (int i = 0; i < graph.getNodeCount(); i++) max = Math.max(graph.getNode(i).getDegree(), max);

        return max;
    }

    public int getMin() {

        if (graph.getNodeCount() <= 0) return 0;

        int min = graph.getNode(0).getDegree();

        for (int i = 1; i < graph.getNodeCount(); i++) min = Math.min(graph.getNode(i).getDegree(), min);

        return min;
    }

    public double getDiameter() {
        return Toolkit.diameter(graph);
    }

    public void setRandomWeight(){
        int random;

        for(int i = 0; i < graph.getEdgeCount(); i++){
            graph.getEdge(i).setAttribute("ui.label", (int)(Math.random() * (MAX_WEIGHT - 1)) + 1);
        }
        this.repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }
}
