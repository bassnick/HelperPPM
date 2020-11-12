package cz.bassnick.PPM.DAO.Player.Basket;


import cz.bassnick.PPM.DAO.Player.Basket.AgreementPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Basket.BasicPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Basket.Player;
import cz.bassnick.PPM.DAO.Player.Basket.TrainingPlayerInfo;
import cz.bassnick.PPM.Helpers;
import cz.bassnick.PPM.Tools.Var;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Players {
    public static List<Player> players = new ArrayList<Player>();
    private static boolean isAgreement;
    private static boolean isBasic;
    private static boolean isTraining;
    private static boolean isOverview;
    private static boolean isStat;
    private static String exportToCSV() {
        StringBuffer buff = new StringBuffer();

        float sumMR = 0f;
        for (Player player: players) {
            TrainingPlayerInfo tp = player.getTraining();
            float zkc = (tp.getAgr()*5f + tp.getVys()*5f + tp.getTec()*2f + tp.getRyc()*2f +tp.getNah()*1f)/15f;
            float zkpg = (tp.getNah()*5f + tp.getTec()*4f + tp.getRyc()*4f + tp.getAgr()*1f + tp.getVys()*1f)/15f;
            float zksg = (tp.getNah()*4f + tp.getTec()*4f + tp.getRyc()*3f + tp.getAgr()*2f + tp.getVys()*2f)/15f;
            float zkpf = (tp.getAgr()*4f + tp.getVys()*4f + tp.getRyc()*3f + tp.getTec()*2f+tp.getNah()*2f)/15f;
            float zksf = (tp.getNah()*2f + tp.getTec()*2f + tp.getRyc()*2f + tp.getAgr()*2f+tp.getVys()*2f)/15f;
            float mr = Math.max(Math.max(Math.max(Math.max(zkc,zkpg), zksg), zkpf), zksf);
            sumMR += mr;
        }
        float averageMR = sumMR / players.size();

        for (Player player: players) {
            AgreementPlayerInfo api = new AgreementPlayerInfo();
            TrainingPlayerInfo tpi = new TrainingPlayerInfo();
            BasicPlayerInfo bpi = new BasicPlayerInfo();
            OverviewPlayerInfo opi = new OverviewPlayerInfo();

            buff = buff.append(player.getName()).append(Var.CSV_ITEMS)
                    .append(player.getState()).append(Var.CSV_ITEMS)
                    .append(player.getAge()).append(Var.CSV_ITEMS)
                    .append(player.getDayToBeIll()).append(Var.CSV_ITEMS);

            if (null != player.getAgreement()) {
                isAgreement = true;
                // append getAgreement to CSV
                api = player.getAgreement();
                buff =  buff.append(api.getRemainsDayOfAgreements()).append(Var.CSV_ITEMS)
                        .append(api.getDaysInTeam()).append(Var.CSV_ITEMS)
                        .append(api.getDaySalary()).append(Var.CSV_ITEMS)
                        .append(api.getAutomaticProlongedAgreement()).append(Var.CSV_ITEMS);
            }

            if (null != player.getTraining()) {
                isTraining = true;
                tpi = player.getTraining();
                buff = buff.append(tpi.getPosition()).append(Var.CSV_ITEMS)
                        .append(tpi.getProzkoumanyStat()).append(Var.CSV_ITEMS)
                        .append(tpi.getSV()).append(Var.CSV_ITEMS)

                        .append(tpi.getStr()).append(Var.CSV_ITEMS)
                        .append(tpi.getQStr()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynStr()).append(Var.CSV_ITEMS)

                        .append(tpi.getBlk()).append(Var.CSV_ITEMS)
                        .append(tpi.getQBlk()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynBlk()).append(Var.CSV_ITEMS)

                        .append(tpi.getNah()).append(Var.CSV_ITEMS)
                        .append(tpi.getQNah()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynNah()).append(Var.CSV_ITEMS)

                        .append(tpi.getTec()).append(Var.CSV_ITEMS)
                        .append(tpi.getQTec()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynTec()).append(Var.CSV_ITEMS)

                        .append(tpi.getRyc()).append(Var.CSV_ITEMS)
                        .append(tpi.getQRyc()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynRyc()).append(Var.CSV_ITEMS)

                        .append(tpi.getAgr()).append(Var.CSV_ITEMS)
                        .append(tpi.getQAgr()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynAgr()).append(Var.CSV_ITEMS)

                        .append(tpi.getVys()).append(Var.CSV_ITEMS)
                        .append(tpi.getQVys()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynVys()).append(Var.CSV_ITEMS)

                        .append(tpi.getTrg()).append(Var.CSV_ITEMS);
                // append getBasic to CSV
            }

            if (null != player.getBasic()) {
                isBasic = true;
                bpi = player.getBasic();

                buff = buff//.append(bpi.getPosition()).append(Var.CSV_ITEMS)
                        //.append(bpi.getProzkoumanyStat()).append(Var.CSV_ITEMS)
                        .append(bpi.getAverageQA()).append(Var.CSV_ITEMS)
                        //.append(bpi.getSV()).append(Var.CSV_ITEMS)
                        //.append(bpi.getBra()).append(Var.CSV_ITEMS)
                        //.append(bpi.getObr()).append(Var.CSV_ITEMS)
                        //.append(bpi.getUto()).append(Var.CSV_ITEMS)
                        //.append(bpi.getStr()).append(Var.CSV_ITEMS)
                        //.append(bpi.getNah()).append(Var.CSV_ITEMS)
                        //.append(bpi.getTec()).append(Var.CSV_ITEMS)
                        //.append(bpi.getAgr()).append(Var.CSV_ITEMS)
                        .append(bpi.getZku()).append(Var.CSV_ITEMS)
                        .append(bpi.getCZ()).append(Var.CSV_ITEMS)
                        .append(bpi.getVyska()).append(Var.CSV_ITEMS);
                // append getBasic to CSV
            }

            if (null != player.getOverview()) {
                isOverview = true;
                opi = player.getOverview();
                buff = buff
                        .append(opi.getEnergy()).append(Var.CSV_ITEMS)
                        .append(opi.getMaxEnergy()).append(Var.CSV_ITEMS);
            }

            float zkc = (tpi.getAgr()*5f + tpi.getVys()*5f + tpi.getTec()*2f + tpi.getRyc()*2f +tpi.getNah()*1f)/15f;
            float zkpg = (tpi.getNah()*5f + tpi.getTec()*4f + tpi.getRyc()*4f + tpi.getAgr()*1f + tpi.getVys()*1f)/15f;
            float zksg = (tpi.getNah()*4f + tpi.getTec()*4f + tpi.getRyc()*3f + tpi.getAgr()*2f + tpi.getVys()*2f)/15f;
            float zkpf = (tpi.getAgr()*4f + tpi.getVys()*4f + tpi.getRyc()*3f + tpi.getTec()*2f+tpi.getNah()*2f)/15f;
            float zksf = (tpi.getNah()*2f + tpi.getTec()*2f + tpi.getRyc()*2f + tpi.getAgr()*2f+tpi.getVys()*2f)/15f;
            float mr = Math.max(Math.max(Math.max(Math.max(zkc,zkpg), zksg), zkpf), zksf);

            float effective = (api.getDaySalary() * 100 / mr) / 100f;
            String ideal = "";
            if (zkc == mr)
                ideal += "C ";
            if (zkpg == mr)
                ideal += "PG ";
            if (zksg == mr)
                ideal += "SG ";
            if (zkpf == mr)
                ideal += "PF ";
            if (zksf == mr)
                ideal += "SF ";

            buff = buff.append(Helpers.round(zkc,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkpg,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zksg,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkpf,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zksf,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(mr,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(effective,2)).append(Var.CSV_ITEMS)
                    .append(ideal).append(Var.CSV_ITEMS)
                    .append(tpi.getPosition()).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkc/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkpg/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zksg/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkpf/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zksf/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round((100f * mr / averageMR)-100,2));


/*
            if (null != player.getStat()) {
                isStat = true;
               // append getStat to CSV
            }
*/
            buff.append(Var.CSV_LINES);
        }
        StringBuffer header = new StringBuffer();
        header = header.append("Jméno").append(Var.CSV_ITEMS)
                .append("Stát").append(Var.CSV_ITEMS)
                .append("Věk").append(Var.CSV_ITEMS)
                .append("Zbývá dní do vyléčení").append(Var.CSV_ITEMS);

        if (isAgreement) {
            header = header.append("Zbývá dnů smlouvy").append(Var.CSV_ITEMS)
                    .append("Dnů v týmu").append(Var.CSV_ITEMS)
                    .append("Denní plat").append(Var.CSV_ITEMS)
                    .append("Automatické prodloužení smlouvy").append(Var.CSV_ITEMS);
        }
        if (isTraining) {
            header = header.append("Pozice").append(Var.CSV_ITEMS)
                    .append("Je prozkoumaný?").append(Var.CSV_ITEMS)
                    .append("SV").append(Var.CSV_ITEMS)

                    .append("Str").append(Var.CSV_ITEMS)
                    .append("QStr").append(Var.CSV_ITEMS)
                    .append("DynStr").append(Var.CSV_ITEMS)

                    .append("Blk").append(Var.CSV_ITEMS)
                    .append("QBlk").append(Var.CSV_ITEMS)
                    .append("DynBlk").append(Var.CSV_ITEMS)

                    .append("Nah").append(Var.CSV_ITEMS)
                    .append("QNah").append(Var.CSV_ITEMS)
                    .append("DynNah").append(Var.CSV_ITEMS)

                    .append("Tech").append(Var.CSV_ITEMS)
                    .append("QTech").append(Var.CSV_ITEMS)
                    .append("DynTech").append(Var.CSV_ITEMS)

                    .append("Rych").append(Var.CSV_ITEMS)
                    .append("QRych").append(Var.CSV_ITEMS)
                    .append("DynRych").append(Var.CSV_ITEMS)

                    .append("Agr").append(Var.CSV_ITEMS)
                    .append("QAgr").append(Var.CSV_ITEMS)
                    .append("DynAgr").append(Var.CSV_ITEMS)

                    .append("Vyskok").append(Var.CSV_ITEMS)
                    .append("QVyskok").append(Var.CSV_ITEMS)
                    .append("DynVyskok").append(Var.CSV_ITEMS)

                    .append("Trg").append(Var.CSV_ITEMS);
        }
        if (isBasic) {
            header = header//.append("Pozice").append(Var.CSV_ITEMS)
                     //.append("Je prozkoumaný?").append(Var.CSV_ITEMS)
                    .append("Průměrná kvalita").append(Var.CSV_ITEMS)
                    //.append("SV").append(Var.CSV_ITEMS)
                    //.append("Bra").append(Var.CSV_ITEMS)
                    //.append("Obr").append(Var.CSV_ITEMS)
                    //.append("Uto").append(Var.CSV_ITEMS)
                    //.append("Str").append(Var.CSV_ITEMS)
                    //.append("Nah").append(Var.CSV_ITEMS)
                    //.append("Tec").append(Var.CSV_ITEMS)
                    //.append("Agr").append(Var.CSV_ITEMS)
                    .append("Zku").append(Var.CSV_ITEMS)
                    .append("CZ").append(Var.CSV_ITEMS)
                    .append("Výška").append(Var.CSV_ITEMS);
        }

        if (isOverview) {
            header = header
                    .append("Energy").append(Var.CSV_ITEMS)
                    .append("Max Energy").append(Var.CSV_ITEMS);
        }

        header = header.append("Real ZKC").append(Var.CSV_ITEMS)
                .append("Real ZKPG").append(Var.CSV_ITEMS)
                .append("Real ZKSG").append(Var.CSV_ITEMS)
                .append("Real ZKPF").append(Var.CSV_ITEMS)
                .append("Real ZKSF").append(Var.CSV_ITEMS)
                .append("Max Real ZK (MR)").append(Var.CSV_ITEMS)
                .append("Salary/MR").append(Var.CSV_ITEMS)
                .append("Ideal Position").append(Var.CSV_ITEMS)
                .append("Real Position").append(Var.CSV_ITEMS)
                .append("C% MR").append(Var.CSV_ITEMS)
                .append("PG% MR").append(Var.CSV_ITEMS)
                .append("SG% MR").append(Var.CSV_ITEMS)
                .append("PF% MR").append(Var.CSV_ITEMS)
                .append("SF% MR").append(Var.CSV_ITEMS)
                .append("MR Above Average");

        String headerAndData = header.append(Var.CSV_LINES).append(buff).toString();
        return headerAndData;
    }

    public static void saveCSV() {

        PrintWriter out = null;
        try {
            String csv = exportToCSV();
            Files.write(Paths.get("./dataBasketPPM.csv"), csv.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
