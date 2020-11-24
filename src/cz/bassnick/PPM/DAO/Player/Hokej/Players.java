package cz.bassnick.PPM.DAO.Player.Hokej;

import cz.bassnick.PPM.DAO.Player.Hokej.AgreementPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Hokej.BasicPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Hokej.Player;
import cz.bassnick.PPM.DAO.Player.Hokej.TrainingPlayerInfo;
import cz.bassnick.PPM.Helpers;
import cz.bassnick.PPM.Tools.Var;

import java.io.*;
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
            float zkg = (tp.getBra()*2.5f + tp.getNah()*1.25f + tp.getTec()*1.25f)/5f;
            float zkd = (tp.getObr()*2.0f + tp.getNah()*1.0f + tp.getAgr()*1.0f + tp.getStr() + 1.0f)/5f;
            float zkw = (tp.getUto()*2.0f + tp.getTec()*1.0f + tp.getAgr()*1.0f + tp.getStr() + 1.0f)/5f;
            float zkc = (tp.getUto()*2.0f + tp.getNah()*1.0f + tp.getTec()*1.0f + tp.getStr() + 1.0f)/5f;
            float mr = Math.max(Math.max(Math.max(zkg,zkd), zkw), zkc);
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
            OverviewPlayerInfo opi = new OverviewPlayerInfo();
            StatsFieldInfo sfi = new StatsFieldInfo();
            StatsGoalieInfo sgi = new StatsGoalieInfo();

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

                        .append(tpi.getObr()).append(Var.CSV_ITEMS)
                        .append(tpi.getQObr()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynObr()).append(Var.CSV_ITEMS)

                        .append(tpi.getUto()).append(Var.CSV_ITEMS)
                        .append(tpi.getQUto()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynUto()).append(Var.CSV_ITEMS)

                        .append(tpi.getStr()).append(Var.CSV_ITEMS)
                        .append(tpi.getQStr()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynStr()).append(Var.CSV_ITEMS)

                        .append(tpi.getNah()).append(Var.CSV_ITEMS)
                        .append(tpi.getQNah()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynNah()).append(Var.CSV_ITEMS)

                        .append(tpi.getTec()).append(Var.CSV_ITEMS)
                        .append(tpi.getQTec()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynTec()).append(Var.CSV_ITEMS)

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
                        .append(bpi.getPreferredSide()).append(Var.CSV_ITEMS);
                // append getBasic to CSV
            }

            if (null != player.getOverview()) {
                isOverview = true;
                opi = player.getOverview();
                buff = buff
                        .append(opi.getEnergy()).append(Var.CSV_ITEMS)
                        .append(opi.getMaxEnergy()).append(Var.CSV_ITEMS);
            }

            float zkg = (tpi.getBra()*2.5f + tpi.getNah()*1.25f + tpi.getTec()*1.25f)/5f;
            float zkd = (tpi.getObr()*2.0f + tpi.getNah()*1.0f + tpi.getAgr()*1.0f + tpi.getStr() + 1.0f)/5f;
            float zkw = (tpi.getUto()*2.0f + tpi.getTec()*1.0f + tpi.getAgr()*1.0f + tpi.getStr() + 1.0f)/5f;
            float zkc = (tpi.getUto()*2.0f + tpi.getNah()*1.0f + tpi.getTec()*1.0f + tpi.getStr() + 1.0f)/5f;
            float mr = Math.max(Math.max(Math.max(zkg,zkd), zkw), zkc);
            float effective = (api.getDaySalary() * 100 / mr) / 100f;
            String ideal = "";
            if (zkg == mr)
                ideal += "G ";
            if (zkd == mr)
                ideal += "D ";
            if (zkw == mr)
                ideal += "W ";
            if (zkc == mr)
                ideal += "C ";


            buff = buff.append(Helpers.round(zkg,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkd,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkw,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkc,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(mr,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(effective,2)).append(Var.CSV_ITEMS)
                    .append(ideal).append(Var.CSV_ITEMS)
                    .append(tpi.getPosition()).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkg/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkd/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkw/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkc/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round((100f * mr / averageMR)-100,2)).append(Var.CSV_ITEMS);

            if (null != player.getStatField()) {
                sfi = player.getStatField();
                buff = buff
                        .append(sfi.getGP()).append(Var.CSV_ITEMS)
                        .append(sfi.getG()).append(Var.CSV_ITEMS)
                        .append(sfi.getA()).append(Var.CSV_ITEMS)
                        .append(sfi.getTP()).append(Var.CSV_ITEMS)
                        .append(sfi.getPlusMinus()).append(Var.CSV_ITEMS)
                        .append(sfi.getPIM()).append(Var.CSV_ITEMS)
                        .append(sfi.getDS()).append(Var.CSV_ITEMS)
                        .append(sfi.getPPG()).append(Var.CSV_ITEMS)
                        .append(sfi.getSHG()).append(Var.CSV_ITEMS)
                        .append(sfi.getS()).append(Var.CSV_ITEMS)
                        .append(sfi.getSPercentage()).append(Var.CSV_ITEMS)
                        .append(sfi.getGG()).append(Var.CSV_ITEMS)
                        .append(sfi.getAG()).append(Var.CSV_ITEMS)
                        .append(sfi.getPG()).append(Var.CSV_ITEMS)
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

            if (null != player.getStatGoalie()) {
                sgi = player.getStatGoalie();
                buff = buff
                        .append(sgi.getGP()).append(Var.CSV_ITEMS)
                        .append(sgi.getTOI()).append(Var.CSV_ITEMS)
                        .append(sgi.getSA()).append(Var.CSV_ITEMS)
                        .append(sgi.getGA()).append(Var.CSV_ITEMS)
                        .append(sgi.getGAA()).append(Var.CSV_ITEMS)
                        .append(sgi.getSvPercentage()).append(Var.CSV_ITEMS)
                        .append(sgi.getSO()).append(Var.CSV_ITEMS)
                        .append(sgi.getA()).append(Var.CSV_ITEMS)
                        .append(sgi.getTP()).append(Var.CSV_ITEMS)
                        .append(sgi.getHv()).append(Var.CSV_ITEMS)
                        .append(sgi.getHK()).append(Var.CSV_ITEMS);
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

                    .append("Obr").append(Var.CSV_ITEMS)
                    .append("QObr").append(Var.CSV_ITEMS)
                    .append("DynObr").append(Var.CSV_ITEMS)

                    .append("Uto").append(Var.CSV_ITEMS)
                    .append("QUto").append(Var.CSV_ITEMS)
                    .append("DynUto").append(Var.CSV_ITEMS)

                    .append("Str").append(Var.CSV_ITEMS)
                    .append("QStr").append(Var.CSV_ITEMS)
                    .append("DynStr").append(Var.CSV_ITEMS)

                    .append("Nah").append(Var.CSV_ITEMS)
                    .append("QNah").append(Var.CSV_ITEMS)
                    .append("DynNah").append(Var.CSV_ITEMS)

                    .append("Tec").append(Var.CSV_ITEMS)
                    .append("QTec").append(Var.CSV_ITEMS)
                    .append("DynTec").append(Var.CSV_ITEMS)

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
                    .append("Preferovaná strana").append(Var.CSV_ITEMS);
        }
        if (isOverview) {
            header = header
                    .append("Energy").append(Var.CSV_ITEMS)
                    .append("Max Energy").append(Var.CSV_ITEMS);
        }

        header = header.append("Real ZKG").append(Var.CSV_ITEMS)
                .append("Real ZKD").append(Var.CSV_ITEMS)
                .append("Real ZKW").append(Var.CSV_ITEMS)
                .append("Real ZKC").append(Var.CSV_ITEMS)
                .append("Max Real ZK (MR)").append(Var.CSV_ITEMS)
                .append("Salary/MR").append(Var.CSV_ITEMS)
                .append("Ideal Position").append(Var.CSV_ITEMS)
                .append("Real Position").append(Var.CSV_ITEMS)
                .append("G% MR").append(Var.CSV_ITEMS)
                .append("D% MR").append(Var.CSV_ITEMS)
                .append("W% MR").append(Var.CSV_ITEMS)
                .append("C% MR").append(Var.CSV_ITEMS)
                .append("MR Above Average").append(Var.CSV_ITEMS);

            header = header.append("GP").append(Var.CSV_ITEMS)
                    .append("G").append(Var.CSV_ITEMS)
                    .append("A").append(Var.CSV_ITEMS)
                    .append("TP").append(Var.CSV_ITEMS)
                    .append("+/-").append(Var.CSV_ITEMS)
                    .append("PIM").append(Var.CSV_ITEMS)
                    .append("DS").append(Var.CSV_ITEMS)
                    .append("PPG").append(Var.CSV_ITEMS)
                    .append("SHG").append(Var.CSV_ITEMS)
                    .append("S").append(Var.CSV_ITEMS)
                    .append("S%").append(Var.CSV_ITEMS)
                    .append("G/G").append(Var.CSV_ITEMS)
                    .append("A/G").append(Var.CSV_ITEMS)
                    .append("P/G").append(Var.CSV_ITEMS)
                    .append("Hv").append(Var.CSV_ITEMS)
                    .append("HK").append(Var.CSV_ITEMS);
        header = header.append("GP").append(Var.CSV_ITEMS)
                .append("TOI").append(Var.CSV_ITEMS)
                .append("SA").append(Var.CSV_ITEMS)
                .append("GA").append(Var.CSV_ITEMS)
                .append("GAA").append(Var.CSV_ITEMS)
                .append("Sv%").append(Var.CSV_ITEMS)
                .append("SO").append(Var.CSV_ITEMS)
                .append("A").append(Var.CSV_ITEMS)
                .append("TP").append(Var.CSV_ITEMS)
                .append("Hv").append(Var.CSV_ITEMS)
                .append("HK");



        String headerAndData = header.append(Var.CSV_LINES).append(buff).toString();
        return headerAndData;
    }

    public static void saveCSV() {

        PrintWriter out = null;
        try {
            String csv = exportToCSV();
            Files.write(Paths.get("./dataHokejPPM.csv"), csv.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
