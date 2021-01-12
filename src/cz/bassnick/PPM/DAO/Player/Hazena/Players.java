package cz.bassnick.PPM.DAO.Player.Hazena;

import cz.bassnick.PPM.DAO.Player.Hazena.AgreementPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Hazena.BasicPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Hazena.OverviewPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Hazena.Player;
import cz.bassnick.PPM.DAO.Player.Hazena.StatsFieldInfo;
import cz.bassnick.PPM.DAO.Player.Hazena.TrainingPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Hazena.*;
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
    private static boolean isOverview;
    private static boolean isTraining;

    private static String exportToCSV() {
        StringBuffer buff = new StringBuffer();
        float sumMR = 0f;
        for (Player player: players) {
            TrainingPlayerInfo tp = player.getTraining();
            float zkg = (tp.getBra()*6f + tp.getBlk()*3f + tp.getNah()*2 + tp.getTec() + tp.getRyc())/13f;
            float zkSpojka = (tp.getHvP()*6f + tp.getNah()*3f + tp.getTec() *2f + tp.getAgr() + tp.getRyc() + tp.getStr()*4f)/17f;
            float zkw = (tp.getHvP()*6f + tp.getRyc()*3.0f + tp.getTec()*2f + tp.getNah() +tp.getAgr() + tp.getStr() * 4f)/17f;
            float zkc = (tp.getHvP()*6f + tp.getAgr()*3.0f + tp.getTec()*2f + tp.getNah() +tp.getRyc() + tp.getStr() * 4f)/17f;
            float mr = Math.max(Math.max(Math.max(zkg, zkSpojka), zkw), zkc);
            sumMR += mr;
        }
        float averageMR = sumMR / players.size();

        for (Player player: players) {
            buff = buff.append(player.getName()).append(Var.CSV_ITEMS)
                    .append(player.getState()).append(Var.CSV_ITEMS)
                    .append(player.getAge()).append(Var.CSV_ITEMS)
                    .append(player.getDayToBeIll()).append(Var.CSV_ITEMS);
            AgreementPlayerInfo api = new AgreementPlayerInfo();
            TrainingPlayerInfo tpi = new TrainingPlayerInfo();
            BasicPlayerInfo bpi = new BasicPlayerInfo();
            cz.bassnick.PPM.DAO.Player.Hazena.OverviewPlayerInfo opi = new OverviewPlayerInfo();
            cz.bassnick.PPM.DAO.Player.Hazena.StatsFieldInfo sfi = new StatsFieldInfo();
            StatsPlayerGoalieInfo sgi = new StatsPlayerGoalieInfo();

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
                        .append(tpi.getBra()).append(Var.CSV_ITEMS)
                        .append(tpi.getQBra()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynBra()).append(Var.CSV_ITEMS)

                        .append(tpi.getHvP()).append(Var.CSV_ITEMS)
                        .append(tpi.getQHvP()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynHvP()).append(Var.CSV_ITEMS)

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
                        .append(bpi.getPrS()).append(Var.CSV_ITEMS);
                // append getBasic to CSV
            }

            if (null != player.getOverview()) {
                isOverview = true;
                opi = player.getOverview();
                buff = buff
                        .append(opi.getEnergy()).append(Var.CSV_ITEMS)
                        .append(opi.getMaxEnergy()).append(Var.CSV_ITEMS);
            }
            float zkg = (tpi.getBra()*6f + tpi.getBlk()*3f + tpi.getNah()*2 + tpi.getTec() + tpi.getRyc())/13f;
            float zkSpojka = (tpi.getHvP()*6f + tpi.getNah()*3f + tpi.getTec() *2f + tpi.getAgr() + tpi.getRyc() + tpi.getStr()*4f)/17f;
            float zkw = (tpi.getHvP()*6f + tpi.getRyc()*3.0f + tpi.getTec()*2f + tpi.getNah() +tpi.getAgr() + tpi.getStr() * 4f)/17f;
            float zkc = (tpi.getHvP()*6f + tpi.getAgr()*3.0f + tpi.getTec()*2f + tpi.getNah() +tpi.getRyc() + tpi.getStr() * 4f)/17f;
            float mr = Math.max(Math.max(Math.max(zkg,zkSpojka), zkw), zkc);
            float effective = (api.getDaySalary() * 100 / mr) / 100f;
            String ideal = "";
            if (zkg == mr)
                ideal += "G ";
            if (zkSpojka == mr)
                ideal += "S ";
            if (zkw == mr)
                ideal += "W ";
            if (zkc == mr)
                ideal += "C ";


            buff = buff.append(Helpers.round(zkg,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkSpojka,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkw,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkc,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(mr,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(effective,2)).append(Var.CSV_ITEMS)
                    .append(ideal).append(Var.CSV_ITEMS)
                    .append(tpi.getPosition()).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkg/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkSpojka/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkw/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkc/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round((100f * mr / averageMR)-100,2)).append(Var.CSV_ITEMS);

            if (null != player.getFieldStats()) {
                sfi = player.getFieldStats();
                buff = buff
                        .append(sfi.getGP()).append(Var.CSV_ITEMS)
                        .append(sfi.getG()).append(Var.CSV_ITEMS)
                        .append(sfi.getA()).append(Var.CSV_ITEMS)
                        .append(sfi.getTP()).append(Var.CSV_ITEMS)
                        .append(sfi.getS()).append(Var.CSV_ITEMS)
                        .append(sfi.getSPercentage()).append(Var.CSV_ITEMS)
                        .append(sfi.getG7m()).append(Var.CSV_ITEMS)
                        .append(sfi.getS7m()).append(Var.CSV_ITEMS)
                        .append(sfi.getTO()).append(Var.CSV_ITEMS)
                        .append(sfi.getST()).append(Var.CSV_ITEMS)
                        .append(sfi.getBS()).append(Var.CSV_ITEMS)
                        .append(sfi.getYC()).append(Var.CSV_ITEMS)
                        .append(sfi.getMin2()).append(Var.CSV_ITEMS)
                        .append(sfi.getRC()).append(Var.CSV_ITEMS)
                        .append(sfi.getHv()).append(Var.CSV_ITEMS)
                        .append(sfi.getHK()).append(Var.CSV_ITEMS);
            } else {
                buff = buff
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS);
            }

            if (null != player.getGoalieStats()) {
                sgi = player.getGoalieStats();
                buff = buff
                        .append(sgi.getMP()).append(Var.CSV_ITEMS)
                        .append(sgi.getSV()).append(Var.CSV_ITEMS)
                        .append(sgi.getSA()).append(Var.CSV_ITEMS)
                        .append(sgi.getGA()).append(Var.CSV_ITEMS)
                        .append(sgi.getSvPercentage()).append(Var.CSV_ITEMS)
                        .append(sgi.getSV7m()).append(Var.CSV_ITEMS)
                        .append(sgi.getSA7m()).append(Var.CSV_ITEMS)
                        .append(sgi.getGA7m()).append(Var.CSV_ITEMS)
                        .append(sgi.getM7Percentage()).append(Var.CSV_ITEMS);
            } else {
                buff = buff
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS)
                        .append(Var.CSV_ITEMS);
            }

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
                    .append("Bra").append(Var.CSV_ITEMS)
                    .append("QBra").append(Var.CSV_ITEMS)
                    .append("DynBra").append(Var.CSV_ITEMS)

                    .append("HvP").append(Var.CSV_ITEMS)
                    .append("QHvP").append(Var.CSV_ITEMS)
                    .append("DynHvP").append(Var.CSV_ITEMS)

                    .append("Str").append(Var.CSV_ITEMS)
                    .append("QStr").append(Var.CSV_ITEMS)
                    .append("DynStr").append(Var.CSV_ITEMS)

                    .append("Blk").append(Var.CSV_ITEMS)
                    .append("QBlk").append(Var.CSV_ITEMS)
                    .append("DynBlk").append(Var.CSV_ITEMS)

                    .append("Nah").append(Var.CSV_ITEMS)
                    .append("QNah").append(Var.CSV_ITEMS)
                    .append("DynNah").append(Var.CSV_ITEMS)

                    .append("Tec").append(Var.CSV_ITEMS)
                    .append("QTec").append(Var.CSV_ITEMS)
                    .append("DynTec").append(Var.CSV_ITEMS)

                    .append("Ryc").append(Var.CSV_ITEMS)
                    .append("QRyc").append(Var.CSV_ITEMS)
                    .append("DynRyc").append(Var.CSV_ITEMS)

                    .append("Agr").append(Var.CSV_ITEMS)
                    .append("QAgr").append(Var.CSV_ITEMS)
                    .append("DynAgr").append(Var.CSV_ITEMS)

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
                    .append("PrS").append(Var.CSV_ITEMS);
        }
        if (isOverview) {
            header = header
                    .append("Energy").append(Var.CSV_ITEMS)
                    .append("Max Energy").append(Var.CSV_ITEMS);
        }

        header = header.append("Real ZKG").append(Var.CSV_ITEMS)
                .append("Real ZKS").append(Var.CSV_ITEMS)
                .append("Real ZKW").append(Var.CSV_ITEMS)
                .append("Real ZKC").append(Var.CSV_ITEMS)
                .append("Max Real ZK (MR)").append(Var.CSV_ITEMS)
                .append("Salary/MR").append(Var.CSV_ITEMS)
                .append("Ideal Position").append(Var.CSV_ITEMS)
                .append("Real Position").append(Var.CSV_ITEMS)
                .append("G% MR").append(Var.CSV_ITEMS)
                .append("S% MR").append(Var.CSV_ITEMS)
                .append("W% MR").append(Var.CSV_ITEMS)
                .append("C% MR").append(Var.CSV_ITEMS)
                .append("MR Above Average").append(Var.CSV_ITEMS);

            header = header.append("GP").append(Var.CSV_ITEMS)
                    .append("G").append(Var.CSV_ITEMS)
                    .append("A").append(Var.CSV_ITEMS)
                    .append("G+A").append(Var.CSV_ITEMS)
                    .append("S").append(Var.CSV_ITEMS)
                    .append("S%").append(Var.CSV_ITEMS)
                    .append("7m G").append(Var.CSV_ITEMS)
                    .append("7m S").append(Var.CSV_ITEMS)
                    .append("TO").append(Var.CSV_ITEMS)
                    .append("ST").append(Var.CSV_ITEMS)
                    .append("BS").append(Var.CSV_ITEMS)
                    .append("YC").append(Var.CSV_ITEMS)
                    .append("2 min").append(Var.CSV_ITEMS)
                    .append("RC").append(Var.CSV_ITEMS)
                    .append("Hv").append(Var.CSV_ITEMS)
                    .append("HK").append(Var.CSV_ITEMS);
        header = header.append("MP").append(Var.CSV_ITEMS)
                .append("SV").append(Var.CSV_ITEMS)
                .append("SA").append(Var.CSV_ITEMS)
                .append("GA").append(Var.CSV_ITEMS)
                .append("SV%").append(Var.CSV_ITEMS)
                .append("SV 7m").append(Var.CSV_ITEMS)
                .append("SA 7m").append(Var.CSV_ITEMS)
                .append("GA 7m").append(Var.CSV_ITEMS)
                .append("M7%").append(Var.CSV_ITEMS);
                //.append("Hv").append(Var.CSV_ITEMS)
                //.append("HK");



        String headerAndData = header.append(Var.CSV_LINES).append(buff).toString();
        return headerAndData;
    }

    public static void saveCSV() {

        PrintWriter out = null;
        try {
            String csv = exportToCSV();
            Files.write(Paths.get("./dataHazenaPPM.csv"), csv.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
