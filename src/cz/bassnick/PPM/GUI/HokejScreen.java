package cz.bassnick.PPM.GUI;

import cz.bassnick.PPM.DAO.Player.Hokej.Players;
import cz.bassnick.PPM.DAO.Player.Hokej.Decode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HokejScreen {
    private JButton btnLoadBasicInfo;
    protected JPanel jPanel;
    private JButton btnLoadTrainingInfo;
    private JButton btnLoadAgreementInfo;
    private JButton btnOverviewPlayerInfo;
    private JTextArea inputTextArea;
    private JButton btnDeleteInputText;
    private JButton btnSave;
    private JButton btnStatsFieldInfo;
    private JButton btnStatsGoalieInfo;
    final JScrollPane scroll;

    public HokejScreen() {
        inputTextArea = new JTextArea(5,20);
        scroll = new JScrollPane(inputTextArea);
        jPanel = new JPanel(new GridLayout(
                9,1));

        jPanel.add(scroll);
        jPanel.add(btnDeleteInputText);
        jPanel.add(btnOverviewPlayerInfo);
        jPanel.add(btnLoadBasicInfo);
        jPanel.add(btnLoadAgreementInfo);
        jPanel.add(btnLoadTrainingInfo);
        jPanel.add(btnStatsFieldInfo);
        jPanel.add(btnStatsGoalieInfo);
        jPanel.add(btnSave);

        btnLoadAgreementInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Decode.agreement(inputTextArea.getText());
            }
        });

        btnLoadBasicInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Decode.basic(inputTextArea.getText());
            }
        });

        btnLoadTrainingInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Decode.training(inputTextArea.getText());
            }
        });
        btnDeleteInputText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            inputTextArea.setText("");
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Players.saveCSV();
            }
        });
        btnOverviewPlayerInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { Decode.overview(inputTextArea.getText()); }
        });

        btnStatsFieldInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { Decode.statsField(inputTextArea.getText()); }
        });

        btnStatsGoalieInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  Decode.statsGoalie(inputTextArea.getText());

            }
        });
    }
}
