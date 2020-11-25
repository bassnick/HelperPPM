package cz.bassnick.PPM.GUI;

import cz.bassnick.PPM.DAO.Player.Basket.Decode;
import cz.bassnick.PPM.DAO.Player.Basket.Players;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BasketScreen {
    private JTextArea textArea1;
    private JButton btnDelete;
    private JButton btnAgreement;
    private JButton btnTraining;
    private JButton btnBasic;
    private JButton btnSave;
    protected JPanel jPanel;
    private JButton btnOverview;
    private JButton btnStats40;
    private JButton btnStatsGame;
    final JScrollPane scroll;

    public BasketScreen() {
        textArea1 = new JTextArea(5, 20);
        scroll = new JScrollPane(textArea1);
        jPanel = new JPanel(new GridLayout(9, 1));

        jPanel.add(scroll);
        jPanel.add(btnDelete);
        jPanel.add(btnOverview);
        jPanel.add(btnBasic);
        jPanel.add(btnAgreement);
        jPanel.add(btnTraining);
        jPanel.add(btnStatsGame);
        jPanel.add(btnStats40);
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
            public void actionPerformed(ActionEvent e) {
                Decode.basic(textArea1.getText());
            }
        });
        btnAgreement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { Decode.agreement(textArea1.getText()); }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { Players.saveCSV(); }
        });
        btnOverview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { Decode.overview(textArea1.getText()); }
        });
        btnStats40.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { Decode.stats40(textArea1.getText()); }
        });
        btnStatsGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { Decode.statsGame(textArea1.getText()); }
        });
    }
}
