package views;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.PngImage;
import controllers.GraphBuilder;
import models.GraphsType;
import org.graphstream.stream.file.FileSinkImages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainFrame extends JFrame {

    public MainFrame() {

        this.setTitle("TP5 Graphs - POO | Fernandes Marc-Antoine");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        GridBagConstraints cont = new GridBagConstraints();
        cont.anchor = GridBagConstraints.NORTHWEST;
        cont.fill = GridBagConstraints.BOTH;

        this.setLayout(new GridBagLayout());

        GraphView graphView = new GraphView();
        ChoicePanel choicePanel = new ChoicePanel(this, graphView);
        ActionsPanel actionsPanel = new ActionsPanel(this, graphView);
        AlgoPanel algoPanel = new AlgoPanel(this, graphView);

        cont.gridx = 0;
        cont.gridy = 0;
        cont.gridheight = 3;
        this.add(graphView, cont);

        cont.gridx = 1;
        cont.gridy = 1;
        cont.gridheight = 1;
        this.add(choicePanel, cont);

        cont.gridy = 2;
        this.add(actionsPanel, cont);

        cont.gridy = 3;
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

                graphPanel.setGraph(GraphBuilder.make((GraphsType) choices.getSelectedItem(), var1, var2),(GraphsType) choices.getSelectedItem());
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

                Document document = new Document();
                FileSinkImages pic = new FileSinkImages(FileSinkImages.OutputType.PNG, FileSinkImages.Resolutions.VGA);
                pic.setLayoutPolicy(FileSinkImages.LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
                try {
                    pic.writeAll(graphPanel.getGraph(), "tmp.png");
                    com.itextpdf.text.Image convertBmp = PngImage.getImage("tmp.png");

                    PdfWriter.getInstance(document, new FileOutputStream("Export.pdf"));

                    document.open();
                    document.addTitle("Export To PDF | POO TP5 | Marc-Antoine FERNANDES");
                    document.add(new Paragraph("Informations sur le graphe"));
                    document.add(new Paragraph("Nom: " + graphPanel.getName()));
                    document.add(new Paragraph("Degré moyen: " + graphPanel.getAVG()));
                    document.add(new Paragraph("Degré maxi: " + graphPanel.getMax()));
                    document.add(new Paragraph("Degré min: " + graphPanel.getMin()));
                    document.add(new Paragraph("Diamètre: " + graphPanel.getDiameter()));
                    document.add(convertBmp);
                    document.close();


                } catch (IOException | DocumentException e1) {
                    e1.printStackTrace();
                }

                // TODO Export to PDF
            } else if (e.getSource() == pondBtn) {
                graphPanel.setRandomWeight();
            }
        }
    }

    public class AlgoPanel extends JPanel implements ActionListener {

        private JButton firstBtn;
        private JButton secondBtn;
        private JButton thirdBtn;

        private JLabel secondBtnLabel;
        private JLabel thirdBtnLabel;


        private GraphView graphPanel;
        private MainFrame mainFrame;

        private GridBagConstraints cont;

        public AlgoPanel(MainFrame mainFrame, GraphView graphPanel) {
            this.mainFrame = mainFrame;
            this.graphPanel = graphPanel;
            this.setBorder(BorderFactory.createTitledBorder("Algorithmes"));
            this.setLayout(new GridBagLayout());

            this.cont = new GridBagConstraints();
            this.cont.anchor = GridBagConstraints.NORTHWEST;
            this.cont.fill = GridBagConstraints.BOTH;


            this.firstBtn = new JButton("Arbre Couvrant");
            this.secondBtn = new JButton("Welsh-Powell");
            this.thirdBtn = new JButton("DSat");

            this.secondBtnLabel = new JLabel("");
            this.thirdBtnLabel = new JLabel("");

            this.firstBtn.addActionListener(this);
            this.secondBtn.addActionListener(this);
            this.thirdBtn.addActionListener(this);

            this.cont.gridy = 0;
            this.cont.gridwidth = 2;
            this.add(firstBtn, cont);

            this.cont.gridwidth = 1;
            this.cont.gridy = 1;
            this.cont.gridx = 0;
            this.add(secondBtn, cont);

            this.cont.gridx = 1;
            this.add(secondBtnLabel, cont);

            this.cont.gridy = 2;
            this.cont.gridx = 0;
            this.add(thirdBtn, cont);

            this.cont.gridx = 1;
            this.add(thirdBtnLabel, cont);
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == firstBtn) {
                graphPanel.algoAC();
            } else if (e.getSource() == secondBtn) {
                this.secondBtnLabel.setText(Integer.toString(graphPanel.algoWP()));
            } else if (e.getSource() == thirdBtn) {
                this.thirdBtnLabel.setText(Integer.toString(graphPanel.algoDSat()));
            }
        }
    }
}
