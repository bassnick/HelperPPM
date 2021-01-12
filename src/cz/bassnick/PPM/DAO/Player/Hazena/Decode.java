package cz.bassnick.PPM.DAO.Player.Hazena;

import cz.bassnick.PPM.DAO.Player.Hazena.AgreementPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Hazena.BasicPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Hazena.OverviewPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Hazena.Player;
import cz.bassnick.PPM.DAO.Player.Hazena.Players;
import cz.bassnick.PPM.DAO.Player.Hazena.StatsFieldInfo;
import cz.bassnick.PPM.DAO.Player.Hazena.TrainingPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Hazena.*;
import cz.bassnick.PPM.DAO.Player.Hazena.StatsPlayerGoalieInfo;

public class Decode {
    public static boolean overview(String data) {
        try {
//           # /t state firstname lastname /t

            data = data.replaceAll("\r", "");
            String[] lines = data.split("\n");
            for (int i = 1; i < lines.length; i++) {
                cz.bassnick.PPM.DAO.Player.Hazena.OverviewPlayerInfo playerInfo = new OverviewPlayerInfo();
                String[] items = lines[i].split("\t");
//                            [1]:
                int illIx = items[1].indexOf(" Počet dní zranění:");
                if (illIx > 0) {
                    items[1] = items[1].substring(0, illIx);
                }

//              split " "
                String[] splits = items[1].split(" ");

                int len = splits.length;
//              (last-1)=Krestni jmeno
                playerInfo.setFirstname(splits[len - 2].trim());
//              last=Prijmeni
                playerInfo.setLastname(splits[len - 1].trim());
                String[] e = items[7].split("/");
                playerInfo.setEnergy(Integer.parseInt(e[0].replace("/","").trim()));
                playerInfo.setMaxEnergy(Integer.parseInt(e[1].replace("/","").trim()));
                String name = playerInfo.getFirstname() + " " + playerInfo.getLastname();
                boolean found = false;
                for (int iPlayer = 0; iPlayer < cz.bassnick.PPM.DAO.Player.Hazena.Players.players.size(); iPlayer++) {
                    if (name.equals(cz.bassnick.PPM.DAO.Player.Hazena.Players.players.get(iPlayer).getName())) {
                        cz.bassnick.PPM.DAO.Player.Hazena.Player player = cz.bassnick.PPM.DAO.Player.Hazena.Players.players.get(iPlayer);
                        player.setOverview(playerInfo);
                        player.setName(name);
                        found = true;
                    }
                }
                if (!found) {
                    cz.bassnick.PPM.DAO.Player.Hazena.Player player = new cz.bassnick.PPM.DAO.Player.Hazena.Player();
                    player.setName(name);
                    player.setOverview(playerInfo);
                    cz.bassnick.PPM.DAO.Player.Hazena.Players.players.add(player);
                }
            }
        }
        catch (Throwable t) {
            System.err.println(t.getMessage());
            return false;
        }
        return true;
    }
    public static boolean agreement(String data) {
        try {
            /*
                split \n \r
                    [0] header
                    [>0] data
                        every odd =
                            split \t
                                [0]:
                                [0]=[0].before(" Počet dní zranění: ...")
                                    split " "
                                        < (last-1)=Stat
                                        (last-1)=Krestni jmeno
                                        last=Prijmeni
                                [1]=Smlouva
                                [2]=Plat
                                [3]=DvM
                        every even = "APS":
                             ANO
                             NE
        */


//                split \n \r
            data = data.replaceAll("\r", "");
            String[] lines = data.split("\n");
//                    [0] header
//                    [>0] data
            cz.bassnick.PPM.DAO.Player.Hazena.AgreementPlayerInfo playerInfo = new cz.bassnick.PPM.DAO.Player.Hazena.AgreementPlayerInfo();
            for (int i = 1; i < lines.length; i++) {
//                        every odd =
                if (i % 2 == 1) {
                    playerInfo = new AgreementPlayerInfo();
//                            split \t
                    String[] items = lines[i].split("\t");
//                                [0]=
//                                [0]=[0].before(" Počet dní zranění: ...")
                    int illIx = items[0].indexOf(" Počet dní zranění:");
                    if (illIx > 0) {
                        int daysIx = items[0].indexOf(":");
                        String[] temp = items[0].substring(daysIx + 1).trim().split(" ");
                        playerInfo.setDaysToBeIll(Integer.parseInt(temp[0].trim()));
                        items[0] = items[0].substring(0, illIx);
                    } else {
                        playerInfo.setDaysToBeIll(0);
                    }
//                                    split " "
                    String[] infos = items[0].split(" ");
                    int len = infos.length;
//                                        < (last-1)=Stat
                    for (int statIx = 0; statIx < len - 2; statIx++) {
                        if (statIx == 0)
                            playerInfo.setState(infos[statIx]);
                        else {
                            playerInfo.setState(playerInfo.getState() + " " + infos[statIx]);
                        }
                    }
//                                        (last-1)=Krestni jmeno
                    playerInfo.setFirstname(infos[len - 2]);
//                                        last=Prijmeni
                    playerInfo.setLastname(infos[len - 1]);
//                                [1]=Vek
                    playerInfo.setAge(Integer.parseInt(items[1].trim()));
//                                [2]=Smlouva
                    playerInfo.setRemainsDayOfAgreements(Integer.parseInt(items[2].trim()));
//                                [3]=Plat
                    playerInfo.setDaySalary(Integer.parseInt(items[3].trim()));
//                                [4]=DvM
                    playerInfo.setDaysInTeam(Integer.parseInt(items[4].trim()));
                }
//                        every even = "APS"
//                             ANO
//                             NE
                else {
                    playerInfo.setAutomaticProlongedAgreement(lines[i]);
                    String name = playerInfo.getFirstname() + " " + playerInfo.getLastname();
                    boolean found = false;
                    for (int iPlayer = 0; iPlayer < cz.bassnick.PPM.DAO.Player.Hazena.Players.players.size(); iPlayer++) {
                        if (name.equals(cz.bassnick.PPM.DAO.Player.Hazena.Players.players.get(iPlayer).getName())) {
                            cz.bassnick.PPM.DAO.Player.Hazena.Player player = cz.bassnick.PPM.DAO.Player.Hazena.Players.players.get(iPlayer);
                            player.setAgreement(playerInfo);
                            player.setName(name);
                            player.setState(playerInfo.getState());
                            player.setAge(playerInfo.getAge());
                            player.setDayToBeIll(playerInfo.getDaysToBeIll());
                            found = true;
                        }
                    }
                    if (!found) {
                        cz.bassnick.PPM.DAO.Player.Hazena.Player player = new cz.bassnick.PPM.DAO.Player.Hazena.Player();
                        player.setName(name);
                        player.setState(playerInfo.getState());
                        player.setAge(playerInfo.getAge());
                        player.setAgreement(playerInfo);
                        player.setDayToBeIll(playerInfo.getDaysToBeIll());
                        cz.bassnick.PPM.DAO.Player.Hazena.Players.players.add(player);
                    }
                }
            }
        } catch (Throwable t) {
            System.err.println(t.getMessage());
            return false;
        }
        return true;
    }
    public static boolean basic(String data) {
        try {
            /*
                split \n \r
                    [0] header
                    [>0] data
                        split \t
                            [0]:
//                                [0]=[0].before(" Počet dní zranění: ...")
                                split " "
                                    (last-1)=Krestni jmeno
                                    last=Prijmeni
                            [1]=Pozice
                            [2]=Věk  - parse
                            [3]=Prozkoumaný hráč
                            [4]=Průměrná kvalita
                            [5]=Sportovní výdrž
                                split("/")[0] - parse
                            [6]=Brankářské schopnosti - parse
                            [7]=HvP
                            [8]=Střelba - parse
                            [9]=Blk - parse
                            [10]=Nahrávka - parse
                            [11]=Technika - parse
                            [12]=Rychlost - parse
                            [13]=Agresivita - parse
                            [14]=Zkušenost - parse
                            [15]=Celkové zkušenosti - parse
                            [16]=Preferovaná strana
        */

//                split \n \r
            data = data.replaceAll("\r", "");
            String[] lines = data.split("\n");
//                    [0] header
//                    [>0] data
            cz.bassnick.PPM.DAO.Player.Hazena.BasicPlayerInfo playerInfo = new cz.bassnick.PPM.DAO.Player.Hazena.BasicPlayerInfo();
            for (int i = 1; i < lines.length; i++) {
                playerInfo = new BasicPlayerInfo();
//                        split \t
                String[] items = lines[i].split("\t");
//                            [0]:
//              [0]=[0].before(" Počet dní zranění: ...")
                int illIx = items[0].indexOf(" Počet dní zranění:");
                if (illIx > 0) {
                    int daysIx = items[0].indexOf(":");
                    String[] temp = items[0].substring(daysIx + 1).trim().split(" ");
                    playerInfo.setDaysToBeIll(Integer.parseInt(temp[0].trim()));
                    items[0] = items[0].substring(0, illIx);
                } else {
                    playerInfo.setDaysToBeIll(0);
                }

//                                split " "
                String[] infos = items[0].split(" ");
                int len = infos.length;
//                                        < (last-1)=Stat
                for (int statIx = 0; statIx < len - 2; statIx++) {
                    if (statIx == 0)
                        playerInfo.setState(infos[statIx]);
                    else {
                        playerInfo.setState(playerInfo.getState() + " " + infos[statIx]);
                    }
                }
//                                  (last-1)=Krestni jmeno
                playerInfo.setFirstname(infos[len - 2].trim());
//                                  last=Prijmeni
                playerInfo.setLastname(infos[len - 1].trim());
//                [1]=Pozice
                playerInfo.setPosition(items[1].trim());
//                            [2]=Věk - parse
                playerInfo.setAge(Integer.parseInt(items[2].trim()));
//                            [3]=Prozkoumaný hráč
                playerInfo.setProzkoumanyStat(items[3].trim());
//                            [4]=Průměrná kvalita - parse
                playerInfo.setAverageQA(Integer.parseInt(items[4].trim()));
//                            [5]=Sportovní výdrž
//                                split("/")[0] - parse
                playerInfo.setSV(Integer.parseInt(items[5].trim().split("/")[0]));
//                            [6]=Brankářské schopnosti - parse
                playerInfo.setBra(Integer.parseInt(items[6].trim()));
//                            [7]=HvP
                playerInfo.setHvP(Integer.parseInt(items[7].trim()));
//                            [8]=Střelba - parse
                playerInfo.setStr(Integer.parseInt(items[8].trim()));
//                            [9]=Blk - parse
                playerInfo.setBlk(Integer.parseInt(items[9].trim()));
//                            [10]=Nahrávka - parse
                playerInfo.setNah(Integer.parseInt(items[10].trim()));
//                            [11]=Technika - parse
                playerInfo.setTec(Integer.parseInt(items[11].trim()));
//                            [12]=Rychlost - parse
                playerInfo.setRyc(Integer.parseInt(items[12].trim()));
//                            [13]=Agresivita - parse
                playerInfo.setAgr(Integer.parseInt(items[13].trim()));
//                            [14]=Zkušenost - parse
                playerInfo.setZku(Integer.parseInt(items[14].trim()));
//                            [15]=Celkové zkušenosti - parse
                playerInfo.setCZ(Integer.parseInt(items[15].trim()));
//                            [16]=Preferovaná strana
                playerInfo.setPrS(items[16].trim());
                String name = playerInfo.getFirstname() + " " + playerInfo.getLastname();
                boolean found = false;
                for (int iPlayer = 0; iPlayer < cz.bassnick.PPM.DAO.Player.Hazena.Players.players.size(); iPlayer++) {
                    if (name.equals(cz.bassnick.PPM.DAO.Player.Hazena.Players.players.get(iPlayer).getName())) {
                        cz.bassnick.PPM.DAO.Player.Hazena.Player player = cz.bassnick.PPM.DAO.Player.Hazena.Players.players.get(iPlayer);
                        player.setBasic(playerInfo);
                        player.setName(name);
                        player.setState(playerInfo.getState());
                        player.setAge(playerInfo.getAge());
                        player.setDayToBeIll(playerInfo.getDaysToBeIll());
                        found = true;
                    }
                }
                if (!found) {
                    cz.bassnick.PPM.DAO.Player.Hazena.Player player = new cz.bassnick.PPM.DAO.Player.Hazena.Player();
                    player.setName(name);
                    player.setState(playerInfo.getState());
                    player.setAge(playerInfo.getAge());
                    player.setBasic(playerInfo);
                    player.setDayToBeIll(playerInfo.getDaysToBeIll());
                    cz.bassnick.PPM.DAO.Player.Hazena.Players.players.add(player);

                }
            }
        } catch (
                Throwable t) {
            System.err.println(t.getMessage());
            return false;
        }
        return true;
    }
    public static boolean training(String data) {
        cz.bassnick.PPM.DAO.Player.Hazena.TrainingPlayerInfo playerInfo = new cz.bassnick.PPM.DAO.Player.Hazena.TrainingPlayerInfo();
        try {
            //split \n \r
            data = data.replaceAll("\r", "");
            String[] lines = data.split("\n");
            //[0] header
            //[>0] data
            for (int i = 1; i < lines.length; i++) {
                playerInfo = new TrainingPlayerInfo();
                String[] items = lines[i].split("\t");

//                        begins on \t or digit?
                //if (lines[i] != null && (!lines[i].startsWith("\t"))) {
                //   [0]=[0].before(" Počet dní zranění: ...")
                int illIx = items[0].indexOf(" Počet dní zranění:");
//              split " "
//                  (last-1)=Krestni jmeno
//                  last=Prijmeni
                if (illIx > 0) {
                    int daysIx = items[0].indexOf(":");
                    String[] temp = items[0].substring(daysIx + 1).trim().split(" ");
                    playerInfo.setDaysToBeIll(Integer.parseInt(temp[0].trim()));
                    items[0] = items[0].substring(0, illIx);
                } else {
                    playerInfo.setDaysToBeIll(0);
                }

//              split " "
                String[] infos = items[0].split(" ");
                int len = infos.length;
//                                        < (last-1)=Stat
                for (int statIx = 0; statIx < len - 2; statIx++) {
                    if (statIx == 0)
                        playerInfo.setState(infos[statIx]);
                    else {
                        playerInfo.setState(playerInfo.getState() + " " + infos[statIx]);
                    }
                }
                //                                        (last-1)=Krestni jmeno
                playerInfo.setFirstname(infos[len - 2]);
//                                        last=Prijmeni
                playerInfo.setLastname(infos[len - 1]);

//                                [1]=Pozice
                playerInfo.setPosition(items[1].trim());
//                                [2]=Věk  - parse
                playerInfo.setAge(Integer.parseInt(items[2].trim()));
//                                [3]=Prozkoumaný hráč
                playerInfo.setProzkoumanyStat(items[3].trim());
//                                [4]=Sportovní výdrž
//                                    split("/")[0] - parse
                playerInfo.setSV(Integer.parseInt(items[4].split("/")[0]));
//                                [5]=
//                                    last 2 digits: Kvalita brankářské schopnosti - parse
//                                    before: Brankářské schopnosti - parse
                String trimmed5 = items[5].trim();
                int lenBra = trimmed5.length();
                String QBra = trimmed5.substring(lenBra - 2, lenBra);
                String Bra = trimmed5.substring(0, lenBra - 2);
                playerInfo.setQBra(Integer.parseInt(QBra));
                playerInfo.setBra(Integer.parseInt(Bra));
//                                  if other '\t' then 

//                                  else if notother '\t' then 
                if (items.length > 6) {
                    playerInfo.setDynBra(0);
                    playerInfo.setDynHvP(0);
                    playerInfo.setDynStr(0);
                    playerInfo.setDynBlk(0);
                    playerInfo.setDynNah(0);
                    playerInfo.setDynTec(0);
                    playerInfo.setDynRyc(0);
                    playerInfo.setDynAgr(0);

//                                [6]=
//                                    last 2 digits: Kvalita obranné schopnosti - parse
//                                    before: Obranné schopnosti - parse
                    String trimmed6 = items[6].trim();
                    int lenHvP = trimmed6.length();
                    String QHvP = trimmed6.substring(lenHvP - 2, lenHvP);
                    String HvP = trimmed6.substring(0, lenHvP - 2);
                    playerInfo.setQHvP(Integer.parseInt(QHvP));
                    playerInfo.setHvP(Integer.parseInt(HvP));
//                    
//
//                                [7]=
//                                    last 2 digits: Kvalita útočné schopnosti - parse
//                                    before: Útočné  schopnosti - parse
                    String trimmed7 = items[7].trim();
                    int lenStr = trimmed7.length();
                    String QStr = trimmed7.substring(lenStr - 2, lenStr);
                    String Str = trimmed7.substring(0, lenStr - 2);
                    playerInfo.setQStr(Integer.parseInt(QStr));
                    playerInfo.setStr(Integer.parseInt(Str));

//                                [8]=
//                                    last 2 digits: Kvalita střelba - parse
//                                    before: Střelba - parse

                    String trimmed8 = items[8].trim();
                    int lenBlk = trimmed8.length();
                    String QBlk = trimmed8.substring(lenBlk - 2, lenBlk);
                    String Blk = trimmed8.substring(0, lenBlk - 2);
                    playerInfo.setQBlk(Integer.parseInt(QBlk));
                    playerInfo.setBlk(Integer.parseInt(Blk));
//                    
//
//                                [9]=
//                                    last 2 digits: Kvalita nahrávka - parse
//                                    before: Nahrávka - parse

                    String trimmed9 = items[9].trim();
                    int lenNah = trimmed9.length();
                    String QNah = trimmed9.substring(lenNah - 2, lenNah);
                    String Nah = trimmed9.substring(0, lenNah - 2);
                    playerInfo.setQNah(Integer.parseInt(QNah));
                    playerInfo.setNah(Integer.parseInt(Nah));

//
//                                [10]=
//                                    last 2 digits: Kvalita technika - parse
//                                    before: Technika - parse
                    String trimmed10 = items[10].trim();
                    int lenTec = trimmed10.length();
                    String QTec = trimmed10.substring(lenTec - 2, lenTec);
                    String Tec = trimmed10.substring(0, lenTec - 2);
                    playerInfo.setQTec(Integer.parseInt(QTec));
                    playerInfo.setTec(Integer.parseInt(Tec));

//
//
//                                [11]=
//                                    last 2 digits: Kvalita agresivita - parse
//                                    before: Agresivita - parse
//
                    String trimmed11 = items[12].trim();
                    int lenRyc = trimmed11.length();
                    String QRyc = trimmed11.substring(lenRyc - 2, lenRyc);
                    String Ryc = trimmed11.substring(0, lenRyc - 2);
                    playerInfo.setQRyc(Integer.parseInt(QRyc));
                    playerInfo.setRyc(Integer.parseInt(Ryc));

                    //
//
//                                [12]=
//                                    last 2 digits: Kvalita agresivita - parse
//                                    before: Agresivita - parse
//                    
                    String trimmed12 = items[12].trim();
                    int lenAgr = trimmed11.length();
                    String QAgr = trimmed11.substring(lenAgr - 2, lenAgr);
                    String Agr = trimmed11.substring(0, lenAgr - 2);
                    playerInfo.setQAgr(Integer.parseInt(QAgr));
                    playerInfo.setAgr(Integer.parseInt(Agr));

//                                [12]=Trénováno - parse float
                    playerInfo.setTrg(Float.parseFloat(items[12].trim()));
                }
                else {
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynBra(0);
                    else
                        playerInfo.setDynBra(Integer.parseInt(lines[i].trim()));


                    i++;
                    String trimmed = lines[i].trim().replace("\t", "");
                    int ln = trimmed.length();
                    String QHvP = trimmed.substring(ln - 2, ln);
                    String HvP = trimmed.substring(0, ln- 2);
                    playerInfo.setQHvP(Integer.parseInt(QHvP));
                    playerInfo.setHvP(Integer.parseInt(HvP));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynHvP(0);
                    else
                        playerInfo.setDynHvP(Integer.parseInt(lines[i].trim()));

                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    String QStr = trimmed.substring(ln - 2, ln);
                    String Str = trimmed.substring(0, ln- 2);
                    playerInfo.setQStr(Integer.parseInt(QStr));
                    playerInfo.setStr(Integer.parseInt(Str));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynStr(0);
                    else
                        playerInfo.setDynStr(Integer.parseInt(lines[i].trim()));

                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    String QBlk = trimmed.substring(ln - 2, ln);
                    String Blk = trimmed.substring(0, ln- 2);
                    playerInfo.setQBlk(Integer.parseInt(QBlk));
                    playerInfo.setBlk(Integer.parseInt(Blk));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynBlk(0);
                    else
                        playerInfo.setDynBlk(Integer.parseInt(lines[i].trim()));

                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    String QNah = trimmed.substring(ln - 2, ln);
                    String Nah = trimmed.substring(0, ln- 2);
                    playerInfo.setQNah(Integer.parseInt(QNah));
                    playerInfo.setNah(Integer.parseInt(Nah));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynNah(0);
                    else
                        playerInfo.setDynNah(Integer.parseInt(lines[i].trim()));

                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    String QTec = trimmed.substring(ln - 2, ln);
                    String Tec = trimmed.substring(0, ln- 2);
                    playerInfo.setQTec(Integer.parseInt(QTec));
                    playerInfo.setTec(Integer.parseInt(Tec));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynTec(0);
                    else
                        playerInfo.setDynTec(Integer.parseInt(lines[i].trim()));

                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    String QRyc = trimmed.substring(ln - 2, ln);
                    String Ryc = trimmed.substring(0, ln- 2);
                    playerInfo.setQAgr(Integer.parseInt(QRyc));
                    playerInfo.setAgr(Integer.parseInt(Ryc));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynRyc(0);
                    else
                        playerInfo.setDynRyc(Integer.parseInt(lines[i].trim()));


                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    String QAgr = trimmed.substring(ln - 2, ln);
                    String Agr = trimmed.substring(0, ln- 2);
                    playerInfo.setQAgr(Integer.parseInt(QAgr));
                    playerInfo.setAgr(Integer.parseInt(Agr));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynAgr(0);
                    else
                        playerInfo.setDynAgr(Integer.parseInt(lines[i].trim()));

                    i++;
                    playerInfo.setTrg(Float.parseFloat(lines[i].trim().replace("\t", "")));


                }


//                               


//            */

//            begins on \t or digit?
/* :YES:      append when ended from before
                        if yes:
                            if starts "" or empty: dynamický trénink = 0
                            if starts digit: dynamický trénink = digit
                            if starts \t : schopnosti, qa (last 2 digits)
                    for all
              [26]=Trénováno - parse float
*/

//                split \n \r
                String name = playerInfo.getFirstname() + " " + playerInfo.getLastname();
                boolean found = false;

                for (int iPlayer = 0; iPlayer < cz.bassnick.PPM.DAO.Player.Hazena.Players.players.size(); iPlayer++) {
                    if (name.equals(cz.bassnick.PPM.DAO.Player.Hazena.Players.players.get(iPlayer).getName())) {
                        cz.bassnick.PPM.DAO.Player.Hazena.Player player = cz.bassnick.PPM.DAO.Player.Hazena.Players.players.get(iPlayer);
                        player.setTraining(playerInfo);
                        player.setName(name);
                        player.setState(playerInfo.getState());
                        player.setAge(playerInfo.getAge());
                        player.setDayToBeIll(playerInfo.getDaysToBeIll());
                        found = true;
                    }
                }
                if (!found) {
                    cz.bassnick.PPM.DAO.Player.Hazena.Player player = new cz.bassnick.PPM.DAO.Player.Hazena.Player();
                    player.setName(name);
                    player.setState(playerInfo.getState());
                    player.setAge(playerInfo.getAge());
                    player.setTraining(playerInfo);
                    player.setDayToBeIll(playerInfo.getDaysToBeIll());
                    cz.bassnick.PPM.DAO.Player.Hazena.Players.players.add(player);

                }
            }
        } catch (
                Throwable t) {
            System.err.println(t.getMessage());
            return false;
        }
        return true;
    }
    public static boolean statsField(String data) {
        cz.bassnick.PPM.DAO.Player.Hazena.StatsFieldInfo playerInfo = new cz.bassnick.PPM.DAO.Player.Hazena.StatsFieldInfo();
        try {
            /*
                split \n \r
                    [0] header
                    [>0] data
                        split \t
                                [0]:
                                [0]=[0].before(" Počet dní zranění: ...")
                                    split " "
                                        < (last-1)=Stat
                                        (last-1)=Krestni jmeno
                                        last=Prijmeni
                                [1]=GP  = Pocet zapasu
                                [2]=G   = Goly
                                [3]=A   = Asistence
                                [4]=TP  = G + A
                                [5]=S   =
                                [6]=S%  =
                                [7]=G7m =
                                [8]=S7m =
                                [9]=TO  =
                                [10]=ST =
                                [11]=BS =
                                [12]=YC =
                                [13]=Min2 =
                                [14]=RC  =
                                [15]=Hv  = Hvezda vecera
                                [16]=HK  = Hvezda kola
        */


//                split \n \r
        data = data.replaceAll("\r", "");
        String[] lines = data.split("\n");
//                    [0] header
//                    [>0] data
        for (int i = 1; i < lines.length; i++) {
            playerInfo = new StatsFieldInfo();
//            split \t
            String[] items = lines[i].split("\t");
            //[0]:
            //                [0]=[0].before(" Počet dní zranění: ...")
            int illIx = items[0].indexOf(" Počet dní zranění:");
            if (illIx > 0) {
                int daysIx = items[0].indexOf(":");
                String[] temp = items[0].substring(daysIx + 1).trim().split(" ");
                playerInfo.setDaysToBeIll(Integer.parseInt(temp[0].trim()));
                items[0] = items[0].substring(0, illIx);
            } else {
                playerInfo.setDaysToBeIll(0);
            }

//            split " "
            String[] infos = items[0].split(" ");
            int len = infos.length;
//                    < (last-1)=Stat
            for (int statIx = 0; statIx < len - 2; statIx++) {
                if (statIx == 0)
                    playerInfo.setState(infos[statIx]);
                else {
                    playerInfo.setState(playerInfo.getState() + " " + infos[statIx]);
                }
            }
            // (last-1)=Krestni jmeno
            playerInfo.setFirstname(infos[len - 2]);
            // last=Prijmeni
            playerInfo.setLastname(infos[len - 1]);


//                    [1]=GP = Pocet zapasu
            playerInfo.setGP(Integer.parseInt(items[1].trim()));
//                    [2] = G = Goly
            playerInfo.setG(Integer.parseInt(items[2].trim()));
//                    [3] = A = Asistence
            playerInfo.setA(Integer.parseInt(items[3].trim()));
//                    [4] = TP = Kanadske body
            playerInfo.setTP(Integer.parseInt(items[4].trim()));
//                    [5] =
            playerInfo.setS(Integer.parseInt(items[5].trim()));
//                    [6]=
            playerInfo.setSPercentage(Float.parseFloat(items[6].trim()));
//                    [7] =
            playerInfo.setG7m(Integer.parseInt(items[7].trim()));
//                    [8]=
            playerInfo.setS7m(Integer.parseInt(items[8].trim()));
//                    [9]=
            playerInfo.setTO(Integer.parseInt(items[9].trim()));
//                    [10] =
            playerInfo.setST(Integer.parseInt(items[10].trim()));
//                    [11] =
            playerInfo.setBS(Integer.parseInt(items[11].trim()));
//                    [12] =
            playerInfo.setYC(Integer.parseInt(items[12].trim()));
//                    [13]=
            playerInfo.setMin2(Integer.parseInt(items[13].trim()));
//                    [14]=
            playerInfo.setRC(Integer.parseInt(items[14].trim()));
//                    [15] =
            playerInfo.setHv(Integer.parseInt(items[15].trim()));
//                    [16] =
            playerInfo.setHK(Integer.parseInt(items[16].trim()));

            String name = playerInfo.getFirstname() + " " + playerInfo.getLastname();
            boolean found = false;

            for (int iPlayer = 0; iPlayer < cz.bassnick.PPM.DAO.Player.Hazena.Players.players.size(); iPlayer++) {
                if (name.equals(cz.bassnick.PPM.DAO.Player.Hazena.Players.players.get(iPlayer).getName())) {
                    cz.bassnick.PPM.DAO.Player.Hazena.Player player = cz.bassnick.PPM.DAO.Player.Hazena.Players.players.get(iPlayer);
                    player.setFieldStats(playerInfo);
                    found = true;
                }
            }
            if (!found) {
                cz.bassnick.PPM.DAO.Player.Hazena.Player player = new cz.bassnick.PPM.DAO.Player.Hazena.Player();
                player.setName(name);
                player.setState(playerInfo.getState());
                player.setFieldStats(playerInfo);
                player.setDayToBeIll(playerInfo.getDaysToBeIll());
                cz.bassnick.PPM.DAO.Player.Hazena.Players.players.add(player);

            }
        }
        } catch (
                Throwable t) {
            System.err.println(t.getMessage());
            return false;
        }
        return true;
    }
    public static boolean statsGoalie(String data) {
        StatsPlayerGoalieInfo playerInfo = new StatsPlayerGoalieInfo();
        try {
            /*
                split \n \r
                    [0] header
                    [>0] data
                        split \t
                                [0]:
                                [0]=[0].before(" Počet dní zranění: ...")
                                    split " "
                                        < (last-1)=Stat
                                        (last-1)=Krestni jmeno
                                        last=Prijmeni
                                [1]=MP  = Pocet zapasu
                                [2]=SV =
                                [3]=SA  =
                                [4]=GA  =
                                [5]=Sv% =
                                [6]=7mSV =
                                [7]=7mSA  =
                                [8]=7mGA   =
                                [9]=7m%  =

        */


//                split \n \r
            data = data.replaceAll("\r", "");
            String[] lines = data.split("\n");
//                    [0] header
//                    [>0] data
            for (int i = 1; i < lines.length; i++) {
                playerInfo = new StatsPlayerGoalieInfo();
//            split \t
                String[] items = lines[i].split("\t");
                //[0]:
                //                [0]=[0].before(" Počet dní zranění: ...")
                int illIx = items[0].indexOf(" Počet dní zranění:");
                if (illIx > 0) {
                    int daysIx = items[0].indexOf(":");
                    String[] temp = items[0].substring(daysIx + 1).trim().split(" ");
                    playerInfo.setDaysToBeIll(Integer.parseInt(temp[0].trim()));
                    items[0] = items[0].substring(0, illIx);
                } else {
                    playerInfo.setDaysToBeIll(0);
                }

//            split " "
                String[] infos = items[0].split(" ");
                int len = infos.length;
//                    < (last-1)=Stat
                for (int statIx = 0; statIx < len - 2; statIx++) {
                    if (statIx == 0)
                        playerInfo.setState(infos[statIx]);
                    else {
                        playerInfo.setState(playerInfo.getState() + " " + infos[statIx]);
                    }
                }
                // (last-1)=Krestni jmeno
                playerInfo.setFirstname(infos[len - 2]);
                // last=Prijmeni
                playerInfo.setLastname(infos[len - 1]);


//                    [1]=
                playerInfo.setMP(Integer.parseInt(items[1].trim()));
//                    [2]=
                playerInfo.setSV(Integer.parseInt(items[2].trim()));
//                    [3]=
                playerInfo.setSA(Integer.parseInt(items[3].trim()));
//                    [4]=
                playerInfo.setGA(Integer.parseInt(items[4].trim()));
//                    [5]=
                playerInfo.setSvPercentage(Float.parseFloat(items[5].trim()));
//                    [6]=
                playerInfo.setSV7m(Integer.parseInt(items[6].trim()));
//                    [7]=
                playerInfo.setSA7m(Integer.parseInt(items[7].trim()));
//                    [8]=
                playerInfo.setGA7m(Integer.parseInt(items[8].trim()));
//                    [9]=
                playerInfo.setM7Percentage(Integer.parseInt(items[9].trim()));

                String name = playerInfo.getFirstname() + " " + playerInfo.getLastname();
                boolean found = false;

                for (int iPlayer = 0; iPlayer < cz.bassnick.PPM.DAO.Player.Hazena.Players.players.size(); iPlayer++) {
                    if (name.equals(cz.bassnick.PPM.DAO.Player.Hazena.Players.players.get(iPlayer).getName())) {
                        cz.bassnick.PPM.DAO.Player.Hazena.Player player = cz.bassnick.PPM.DAO.Player.Hazena.Players.players.get(iPlayer);
                        player.setGoalieStats(playerInfo);
                        found = true;
                    }
                }
                if (!found) {
                    cz.bassnick.PPM.DAO.Player.Hazena.Player player = new Player();
                    player.setName(name);
                    player.setState(playerInfo.getState());
                    player.setGoalieStats(playerInfo);
                    player.setDayToBeIll(playerInfo.getDaysToBeIll());
                    Players.players.add(player);

                }
            }
        } catch (
                Throwable t) {
            System.err.println(t.getMessage());
            return false;
        }
        return true;
    }
}
