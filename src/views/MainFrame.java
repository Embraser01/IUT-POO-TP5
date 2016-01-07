package views;


import controllers.GraphBuilder;
import models.GraphsType;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Executable;

public class MainFrame extends JFrame {

    private GridBagConstraints cont;

    private ChoicePanel choicePanel;
    private ActionsPanel actionsPanel;
    private AlgoPanel algoPanel;
    private GraphView graphView;

    public MainFrame() {

        this.setTitle("TP5 Graphs - POO | Fernandes Marc-Antoine");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.cont = new GridBagConstraints();
        this.cont.anchor = GridBagConstraints.NORTHWEST;
        this.cont.fill = GridBagConstraints.BOTH;

        this.setLayout(new GridBagLayout());

        this.graphView = new GraphView();
        this.choicePanel = new ChoicePanel(this, graphView);
        this.actionsPanel = new ActionsPanel(this, graphView);
        this.algoPanel = new AlgoPanel(this, graphView);

        cont.gridx = 0;
        cont.gridy = 0;
        cont.gridheight = 3;
        this.add(graphView, cont);

        cont.gridx = 1;
        cont.gridheight = 1;
        this.add(choicePanel, cont);

        cont.gridx = 2;
        this.add(actionsPanel, cont);

        cont.gridx = 3;
        this.add(algoPanel, cont);

        this.setVisible(true);
        this.pack();
    }


    public class ChoicePanel extends JPanel implements ActionListener {

        private JComboBox choices;
        private JButton validBtn;
        private GraphView graphPanel;
        private MainFrame mainFrame;

        private GridBagConstraints cont;

        public ChoicePanel(MainFrame mainFrame, GraphView graphPanel) {
            this.mainFrame = mainFrame;
            this.graphPanel = graphPanel;
            this.setBorder(BorderFactory.createTitledBorder("Choix du graphe"));

            this.validBtn = new JButton("Générer");
            this.validBtn.addActionListener(this);
            this.choices = new JComboBox(GraphsType.class.getEnumConstants());

            this.add(choices);
            this.add(validBtn);
        }

        private void launchDialog() {

            JButton validBtn = new JButton("Ok");
            JPanel panel = new JPanel();


            JLabel field1_label = new JLabel();
            JTextField field1 = new JTextField(12);

            JLabel field2_label = new JLabel();
            JTextField field2 = new JTextField(12);

            // Set label
            switch ((GraphsType) choices.getSelectedItem()) {
                case Cycle:
                case Chaine:
                case Graphe_aleatoire:
                    field1_label.setText("Nombre de sommets :");
                    break;

                case Tore:
                case Grille_carree:
                    field1_label.setText("Taille :");
                    break;

                case Arbre_n_aire_complet:
                    field1_label.setText("Hauteur :");
                    break;
            }
            panel.add(field1_label);
            panel.add(field1);

            switch ((GraphsType) choices.getSelectedItem()) {
                case Graphe_aleatoire:
                    field2_label.setText("Degré :");
                    panel.add(field2_label);
                    panel.add(field2);
                    break;

                case Arbre_n_aire_complet:
                    field2_label.setText("Nombre de fils pour chaque noeud :");
                    panel.add(field2_label);
                    panel.add(field2);
                    break;
            }

            panel.add(validBtn);
            JDialog dialog = new JDialog(mainFrame, true);

            validBtn.addActionListener(e -> {
                int var1 = 0;
                int var2 = 0;

                try {
                    var1 = Integer.parseInt(field1.getText());
                    var2 = Integer.parseInt(field2.getText());
                } catch (Exception ex) {
                }

                graphPanel.setGraph(GraphBuilder.make((GraphsType) choices.getSelectedItem(), var1, var2));
                dialog.dispose();
            });

            dialog.getContentPane().add(panel);
            dialog.pack();
            dialog.setLocation(525, 200);
            dialog.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == validBtn) {
                launchDialog();
            }
        }
    }

    public class ActionsPanel extends JPanel implements ActionListener {

        private JButton exportBtn;
        private JButton pondBtn;
        private GraphView graphPanel;
        private MainFrame mainFrame;

        private GridBagConstraints cont;

        public ActionsPanel(MainFrame mainFrame, GraphView graphPanel) {
            this.mainFrame = mainFrame;
            this.graphPanel = graphPanel;
            this.setBorder(BorderFactory.createTitledBorder("Actions"));

            this.exportBtn = new JButton("Export PDF");
            this.pondBtn = new JButton("Pondérer");

            this.exportBtn.addActionListener(this);
            this.pondBtn.addActionListener(this);

            this.add(exportBtn);
            this.add(pondBtn);
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == exportBtn) {
                // TODO Export to PDF
            } else if (e.getSource() == pondBtn) {
                // TODO Pondérer
            }
        }
    }

    public class AlgoPanel extends JPanel implements ActionListener {

        private JButton firstBtn;
        private JButton secondBtn;
        private JButton thirdBtn;
        private GraphView graphPanel;
        private MainFrame mainFrame;

        private GridBagConstraints cont;

        public AlgoPanel(MainFrame mainFrame, GraphView graphPanel) {
            this.mainFrame = mainFrame;
            this.graphPanel = graphPanel;
            this.setBorder(BorderFactory.createTitledBorder("Algorithmes"));

            this.firstBtn = new JButton("Arbre Couvrant");
            this.secondBtn = new JButton("Welsh-Powell");
            this.thirdBtn = new JButton("DSat");

            this.firstBtn.addActionListener(this);
            this.secondBtn.addActionListener(this);
            this.thirdBtn.addActionListener(this);

            this.add(firstBtn);
            this.add(secondBtn);
            this.add(thirdBtn);
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == firstBtn) {
                // TODO Arbre couvrant
            } else if (e.getSource() == secondBtn) {
                // TODO Welsh-Powell
            } else if (e.getSource() == thirdBtn) {
                // TODO DSat
            }
        }
    }
}
