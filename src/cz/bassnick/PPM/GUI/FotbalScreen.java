package cz.bassnick.PPM.GUI;

import cz.bassnick.PPM.DAO.Player.Fotbal.Players;
import cz.bassnick.PPM.DAO.Player.Fotbal.Decode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FotbalScreen {
    private JButton btnDelete;
    protected JPanel jPanel;
    private JButton btnTraining;
    private JButton btnSave;
    private JButton btnBasic;
    private JButton btnAgreement;
    private JTextArea textArea1;
    private JButton btnOverview;
    final JScrollPane scroll;

    public FotbalScreen() {

        textArea1 = new JTextArea(6,20);
        scroll = new JScrollPane(textArea1);
        jPanel = new JPanel(new GridLayout(7,1));
        //scrolll.add(movieinfo);
        //pangui.add(movieinfo);

        jPanel.add(scroll);
        jPanel.add(btnDelete);
        jPanel.add(btnOverview);
        jPanel.add(btnBasic);
        jPanel.add(btnAgreement);
        jPanel.add(btnTraining);
        jPanel.add(btnSave);



        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("");
            }
        });
        btnTraining.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Decode.training(textArea1.getText());
            }
        });
        btnBasic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { Decode.basic(textArea1.getText()); }
        });
        btnAgreement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Decode.agreement(textArea1.getText());
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Players.saveCSV();
            }
        });
        btnOverview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { Decode.overview(textArea1.getText()); }
        });
    }
}
