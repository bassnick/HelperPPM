package cz.bassnick.PPM.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InspectPlayersPPM {
    private JButton btnHokej;
    private JButton btnFotbal;
    private JButton btnBasket;
    private JPanel jPanel;

    public InspectPlayersPPM() {
        btnHokej.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HokejScreen bs = new HokejScreen();

                JFrame frame = new JFrame("HokejScreen");

                JPanel jp = bs.jPanel;
                frame.setContentPane(jp);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

            }
        });
        btnFotbal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FotbalScreen fs = new FotbalScreen();

                JFrame frame = new JFrame("FotbalScreen");

                JPanel jp = fs.jPanel;
                frame.setContentPane(jp);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

            }
        });
        btnBasket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BasketScreen fs = new BasketScreen();

                JFrame frame = new JFrame("BasketScreen");

                JPanel jp = fs.jPanel;
                frame.setContentPane(jp);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

            }
        });
    }
    public static void main(String[] args) {

        InspectPlayersPPM ppm = new InspectPlayersPPM();

        JFrame frame = new JFrame("InspectPlayers");


        JPanel jp = ppm.jPanel;
        frame.setContentPane(jp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(200,200);
        //frame.setMaximumSize(new Dimension(200,200));
        frame.pack();
        frame.setVisible(true);
    }
}
