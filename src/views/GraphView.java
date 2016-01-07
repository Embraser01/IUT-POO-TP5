package views;

import controllers.GraphBuilder;
import models.GraphsType;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

import javax.swing.*;
import java.awt.*;


public class GraphView extends JPanel {

    public GraphView() {
        this.setGraph(GraphBuilder.make(GraphsType.Graphe_aleatoire, 100, 2));
    }

    public void setGraph(Graph graph) {

        this.removeAll();

        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);

        viewer.enableAutoLayout();
        View view = viewer.addDefaultView(false);   // false indicates "no JFrame".
        view.setPreferredSize(new Dimension(500,500));
        this.add(view);
        this.revalidate();
        this.repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }
}
