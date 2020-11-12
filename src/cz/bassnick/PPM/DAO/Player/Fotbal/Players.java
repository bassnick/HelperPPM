package cz.bassnick.PPM.DAO.Player.Fotbal;

//import cz.bassnick.PPM.DAO.Player.Fotbal.AgreementPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Fotbal.BasicPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Fotbal.Player;
//import cz.bassnick.PPM.DAO.Player.Fotbal.TrainingPlayerInfo;
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
            float zkg = (tp.getBra()*6f+tp.getNah()*1f+tp.getTec()*3f+tp.getRyc()*3f+tp.getHla()*1f) / 14f;

            float zkdc = (tp.getObr()*6f+tp.getNah()*2f+tp.getTec()*2f+tp.getRyc()*2f+tp.getHla()*2f) / 14f;
            float zkdw = (tp.getObr()*6f+tp.getNah()*2f+tp.getTec()*2f+tp.getRyc()*3f+tp.getHla()*1f) / 14f;

            float zkmc = (tp.getZal()*6f+tp.getNah()*3f+tp.getTec()*3f+tp.getRyc()*1f+tp.getHla()*1f + tp.getStr()* 2f) / 16f;
            float zkmw = (tp.getZal()*6f+tp.getNah()*2f+tp.getTec()*2f+tp.getRyc()*3f+tp.getHla()*1f + tp.getStr()* 2f) / 16f;

            float zkfc = (tp.getUto()*6f+tp.getNah()*1f+tp.getTec()*2f+tp.getRyc()*3f+tp.getHla()*1f  + tp.getStr()*4f ) / 17f;
            float zkfw = (tp.getUto()*6f+tp.getNah()*2f+tp.getTec()*3f+tp.getRyc()*3f+tp.getHla()*1f  + tp.getStr()*4f ) / 19f;

            float mr = Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(zkg,zkdc), zkdw), zkmc), zkmw), zkfc), zkfw);

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

                        .append(tpi.getZal()).append(Var.CSV_ITEMS)
                        .append(tpi.getQZal()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynZal()).append(Var.CSV_ITEMS)

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

                        .append(tpi.getRyc()).append(Var.CSV_ITEMS)
                        .append(tpi.getQRyc()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynRyc()).append(Var.CSV_ITEMS)

                        .append(tpi.getHla()).append(Var.CSV_ITEMS)
                        .append(tpi.getQHla()).append(Var.CSV_ITEMS)
                        .append(tpi.getDynHla()).append(Var.CSV_ITEMS)

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

            float zkg = (tpi.getBra()*6f+tpi.getNah()*1f+tpi.getTec()*3f+tpi.getRyc()*3f+tpi.getHla()*1f) / 14f;

            float zkdc = (tpi.getObr()*6f+tpi.getNah()*2f+tpi.getTec()*2f+tpi.getRyc()*2f+tpi.getHla()*2f) / 14f;
            float zkdw = (tpi.getObr()*6f+tpi.getNah()*2f+tpi.getTec()*2f+tpi.getRyc()*3f+tpi.getHla()*1f) / 14f;

            float zkmc = (tpi.getZal()*6f+tpi.getNah()*3f+tpi.getTec()*3f+tpi.getRyc()*1f+tpi.getHla()*1f + tpi.getStr()* 2f) / 16f;
            float zkmw = (tpi.getZal()*6f+tpi.getNah()*2f+tpi.getTec()*2f+tpi.getRyc()*3f+tpi.getHla()*1f + tpi.getStr()* 2f) / 16f;

            float zkfc = (tpi.getUto()*6f+tpi.getNah()*1f+tpi.getTec()*2f+tpi.getRyc()*3f+tpi.getHla()*1f  + tpi.getStr()*4f ) / 17f;
            float zkfw = (tpi.getUto()*6f+tpi.getNah()*2f+tpi.getTec()*3f+tpi.getRyc()*3f+tpi.getHla()*1f  + tpi.getStr()*4f ) / 19f;

            float mr = Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(zkg,zkdc), zkdw), zkmc), zkmw), zkfc), zkfw);

            float effective = (api.getDaySalary() * 100 / mr) / 100f;
            String ideal = "";
            if (zkg == mr)
                ideal += "G ";

            if (zkdc == mr)
                ideal += "DC ";
            if (zkdw == mr)
                ideal += "DW ";

            if (zkmc == mr)
                ideal += "MC ";
            if (zkmw == mr)
                ideal += "MW ";

            if (zkfc == mr)
                ideal += "FC ";
            if (zkfw == mr)
                ideal += "FW ";

            buff = buff.append(Helpers.round(zkg,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkdc,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkdw,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkmc,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkmw,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkfc,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkfw,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(mr,2)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(effective,2)).append(Var.CSV_ITEMS)
                    .append(ideal).append(Var.CSV_ITEMS)
                    .append(tpi.getPosition()).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkg/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkdc/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkdw/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkmc/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkmw/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkfc/mr*100,0)).append(Var.CSV_ITEMS)
                    .append(Helpers.round(zkfw/mr*100,0)).append(Var.CSV_ITEMS)
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
                    .append("Bra").append(Var.CSV_ITEMS)
                    .append("QBra").append(Var.CSV_ITEMS)
                    .append("DynBra").append(Var.CSV_ITEMS)

                    .append("Obr").append(Var.CSV_ITEMS)
                    .append("QObr").append(Var.CSV_ITEMS)
                    .append("DynObr").append(Var.CSV_ITEMS)

                    .append("Zal").append(Var.CSV_ITEMS)
                    .append("QZal").append(Var.CSV_ITEMS)
                    .append("DynZal").append(Var.CSV_ITEMS)

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

                    .append("Ryc").append(Var.CSV_ITEMS)
                    .append("QRyc").append(Var.CSV_ITEMS)
                    .append("DynRyc").append(Var.CSV_ITEMS)

                    .append("Hla").append(Var.CSV_ITEMS)
                    .append("QHla").append(Var.CSV_ITEMS)
                    .append("DynHla").append(Var.CSV_ITEMS)

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
                .append("Real ZKDC").append(Var.CSV_ITEMS)
                .append("Real ZKDW").append(Var.CSV_ITEMS)
                .append("Real ZKMC").append(Var.CSV_ITEMS)
                .append("Real ZKMW").append(Var.CSV_ITEMS)
                .append("Real ZKFC").append(Var.CSV_ITEMS)
                .append("Real ZKFW").append(Var.CSV_ITEMS)
                .append("Max Real ZK (MR)").append(Var.CSV_ITEMS)
                .append("Salary/MR").append(Var.CSV_ITEMS)
                .append("Ideal Position").append(Var.CSV_ITEMS)
                .append("Real Position").append(Var.CSV_ITEMS)
                .append("G% MR").append(Var.CSV_ITEMS)
                .append("DC% MR").append(Var.CSV_ITEMS)
                .append("DW% MR").append(Var.CSV_ITEMS)
                .append("MC% MR").append(Var.CSV_ITEMS)
                .append("MW% MR").append(Var.CSV_ITEMS)
                .append("FC% MR").append(Var.CSV_ITEMS)
                .append("FW% MR").append(Var.CSV_ITEMS)
                .append("MR Above Average");

        String headerAndData = header.append(Var.CSV_LINES).append(buff).toString();

        return headerAndData;
    }

    public static void saveCSV() {

        PrintWriter out = null;
        try {
            String csv = exportToCSV();
            Files.write(Paths.get("./dataFotbalPPM.csv"), csv.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
