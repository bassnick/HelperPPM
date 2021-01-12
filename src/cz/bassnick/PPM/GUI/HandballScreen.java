package cz.bassnick.PPM.GUI;

import cz.bassnick.PPM.DAO.Player.Hazena.Players;
import cz.bassnick.PPM.DAO.Player.Hazena.Decode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HandballScreen {
    protected JPanel jPanel;
    private JTextArea textArea1;
    private JButton saveButton;
    private JButton deleteTextAreaButton;
    private JButton loadGoalieStatsButton;
    private JButton loadFieldStatsButton;
    private JButton loadTrainingInfoButton;
    private JButton loadAgreementInfoButton;
    private JButton loadOverviewInfoButton;
    private JButton loadPlayersInfoButton;
    final JScrollPane scroll;

    public HandballScreen() {

        textArea1 = new JTextArea(5,20);
        scroll = new JScrollPane(textArea1);
        jPanel = new JPanel(new GridLayout(9,1));
        //scrolll.add(movieinfo);
        //pangui.add(movieinfo);

        jPanel.add(scroll);
        jPanel.add(deleteTextAreaButton);
        jPanel.add(loadPlayersInfoButton);
        jPanel.add(loadOverviewInfoButton);
        jPanel.add(loadAgreementInfoButton);
        jPanel.add(loadTrainingInfoButton);
        jPanel.add(loadFieldStatsButton);
        jPanel.add(loadGoalieStatsButton);
        jPanel.add(saveButton);



        deleteTextAreaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("");
            }
        });
        loadTrainingInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Decode.training(textArea1.getText());
            }
        });
        loadPlayersInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { Decode.overview(textArea1.getText()); }
        });
        loadAgreementInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Decode.agreement(textArea1.getText());
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Players.saveCSV();
            }
        });
        loadOverviewInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { Decode.basic(textArea1.getText()); }
        });
        loadFieldStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { Decode.statsField(textArea1.getText());}
        });
        loadGoalieStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { Decode.statsGoalie(textArea1.getText()); }
        });
    }
}
