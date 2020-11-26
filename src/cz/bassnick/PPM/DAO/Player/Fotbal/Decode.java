package cz.bassnick.PPM.DAO.Player.Fotbal;

import cz.bassnick.PPM.DAO.Player.Fotbal.Player;
import cz.bassnick.PPM.DAO.Player.Fotbal.Players;
import cz.bassnick.PPM.DAO.Player.Hokej.StatsFieldInfo;

public class Decode {


    public static boolean overview(String data) {
        try {
//           # /t state firstname lastname /t

            data = data.replaceAll("\r", "");
            String[] lines = data.split("\n");
            for (int i = 1; i < lines.length; i++) {
                OverviewPlayerInfo playerInfo = new OverviewPlayerInfo();
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
                playerInfo.setEnergy(Integer.parseInt(e[0].replace("/", "").trim()));
                playerInfo.setMaxEnergy(Integer.parseInt(e[1].replace("/", "").trim()));
                String name = playerInfo.getFirstname() + " " + playerInfo.getLastname();
                boolean found = false;
                for (int iPlayer = 0; iPlayer < Players.players.size(); iPlayer++) {
                    if (name.equals(Players.players.get(iPlayer).getName())) {
                        Player player = Players.players.get(iPlayer);
                        player.setOverview(playerInfo);
                        player.setName(name);
                        found = true;
                    }
                }
                if (!found) {
                    Player player = new Player();
                    player.setName(name);
                    player.setOverview(playerInfo);
                    Players.players.add(player);
                }
            }
        } catch (Throwable t) {
            System.err.println(t.getMessage());
            return false;
        }
        return true;
    }


    public static boolean agreement(String data) {
        try {
//
//                split \n \r
//                    [0] header
//                    [>0] data
//                        every odd =
//                            split \t
//                                [0]:
//                                [0]=[0].before(" Počet dní zranění: ...")
//                                    split " "
//                                        < (last-1)=Stat
//                                        (last-1)=Krestni jmeno
//                                        last=Prijmeni
//                                [1]=Smlouva
//                                [2]=Plat
//                                [3]=DvM
//                        every even = "APS":
//                             ANO
//                             NE
//


//                split \n \r
            data = data.replaceAll("\r", "");
            String[] lines = data.split("\n");
//                    [0] header
//                    [>0] data
            AgreementPlayerInfo playerInfo = new AgreementPlayerInfo();
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
                    for (int iPlayer = 0; iPlayer < Players.players.size(); iPlayer++) {
                        if (name.equals(Players.players.get(iPlayer).getName())) {
                            Player player = Players.players.get(iPlayer);
                            player.setAgreement(playerInfo);
                            player.setName(name);
                            player.setState(playerInfo.getState());
                            player.setAge(playerInfo.getAge());
                            player.setDayToBeIll(playerInfo.getDaysToBeIll());
                            found = true;
                        }
                    }
                    if (!found) {
                        Player player = new Player();
                        player.setName(name);
                        player.setState(playerInfo.getState());
                        player.setAge(playerInfo.getAge());
                        player.setAgreement(playerInfo);
                        player.setDayToBeIll(playerInfo.getDaysToBeIll());
                        Players.players.add(player);
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
                            [7]=Obranné schopnosti - parse
                            [8]-Záložní schopnosti - parse
                            [9]=Útočné schopnosti - parse
                            [10]=Střelba - parse
                            [11]=Nahrávka - parse
                            [12]=Technika - parse
                            [13]=Rychlost - parse
                            [14]=Hlavička - parse
                            [15]=Zkušenost - parse
                            [16]=Celkové zkušenosti - parse
                            [17]=Preferovaná strana
        */

//                split \n \r
            data = data.replaceAll("\r", "");
            String[] lines = data.split("\n");
//                    [0] header
//                    [>0] data
            BasicPlayerInfo playerInfo = new BasicPlayerInfo();
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
//                            [7]=Obranné schopnosti - parse
                playerInfo.setObr(Integer.parseInt(items[7].trim()));
//                            [8]=Záložní schopnosti - parse
                playerInfo.setZal(Integer.parseInt(items[8].trim()));
//                            [9]=Útočné schopnosti - parse
                playerInfo.setUto(Integer.parseInt(items[9].trim()));
//                            [10]=Střelba - parse
                playerInfo.setStr(Integer.parseInt(items[10].trim()));
//                            [11]=Nahrávka - parse
                playerInfo.setNah(Integer.parseInt(items[11].trim()));
//                            [12]=Technika - parse
                playerInfo.setTec(Integer.parseInt(items[12].trim()));
//                            [13]=Rychlost - parse
                playerInfo.setRyc(Integer.parseInt(items[13].trim()));
//                            [14]=Hlavičky - parse
                playerInfo.setNah(Integer.parseInt(items[14].trim()));
//                            [15]=Zkušenost - parse
                playerInfo.setZku(Integer.parseInt(items[15].trim()));
//                            [16]=Celkové zkušenosti - parse
                playerInfo.setCZ(Integer.parseInt(items[16].trim()));
//                            [17]=Preferovaná strana
                playerInfo.setPreferredSide(items[17].trim());
                String name = playerInfo.getFirstname() + " " + playerInfo.getLastname();
                boolean found = false;
                for (int iPlayer = 0; iPlayer < Players.players.size(); iPlayer++) {
                    if (name.equals(Players.players.get(iPlayer).getName())) {
                        Player player = Players.players.get(iPlayer);
                        player.setBasic(playerInfo);
                        player.setName(name);
                        player.setState(playerInfo.getState());
                        player.setAge(playerInfo.getAge());
                        player.setDayToBeIll(playerInfo.getDaysToBeIll());
                        found = true;
                    }
                }
                if (!found) {
                    Player player = new Player();
                    player.setName(name);
                    player.setState(playerInfo.getState());
                    player.setAge(playerInfo.getAge());
                    player.setBasic(playerInfo);
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


    public static boolean training(String data) {
        TrainingPlayerInfo playerInfo = new TrainingPlayerInfo();
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
                    playerInfo.setDynObr(0);
                    playerInfo.setDynZal(0);
                    playerInfo.setDynUto(0);
                    playerInfo.setDynStr(0);
                    playerInfo.setDynNah(0);
                    playerInfo.setDynTec(0);
                    playerInfo.setDynRyc(0);
                    playerInfo.setDynHla(0);
//                                [6]=
//                                    last 2 digits: Kvalita obranné schopnosti - parse
//                                    before: Obranné schopnosti - parse
                    String trimmed = items[6].trim();
                    int ln = trimmed.length();
                    String qt = trimmed.substring(ln - 2, ln);
                    String t = trimmed.substring(0, ln - 2);
                    playerInfo.setQObr(Integer.parseInt(qt));
                    playerInfo.setObr(Integer.parseInt(t));
//                    
//
//                                [6a]=
//                                    last 2 digits: Kvalita záložní schopnosti - parse
//                                    before: Záložní schopnosti - parse
                    trimmed = items[7].trim();
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQZal(Integer.parseInt(qt));
                    playerInfo.setZal(Integer.parseInt(t));
//
//
//                                [8]=
//                                   last 2 digits: Kvalita útočné schopnosti - parse
//                                    before: Útočné  schopnosti - parse
                    trimmed = items[8].trim();
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQUto(Integer.parseInt(qt));
                    playerInfo.setUto(Integer.parseInt(t));

//                                [9]=
//                                    last 2 digits: Kvalita střelba - parse
//                                    before: Střelba - parse

                    trimmed = items[9].trim();
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQStr(Integer.parseInt(qt));
                    playerInfo.setStr(Integer.parseInt(t));
//                    
//
//                                [10]=
//                                    last 2 digits: Kvalita nahrávka - parse
//                                    before: Nahrávka - parse

                    trimmed = items[10].trim();
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQNah(Integer.parseInt(qt));
                    playerInfo.setNah(Integer.parseInt(t));

//
//                                [11]=
//                                    last 2 digits: Kvalita technika - parse
//                                    before: Technika - parse
                    trimmed = items[11].trim();
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQTec(Integer.parseInt(qt));
                    playerInfo.setTec(Integer.parseInt(t));

//                    
//
//                                [12]=
//                                    last 2 digits: Kvalita rychlost - parse
//                                    before: Rychlost - parse
//                    
                    trimmed = items[12].trim();
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQRyc(Integer.parseInt(qt));
                    playerInfo.setRyc(Integer.parseInt(t));

                    //          [11b]=
//                                    last 2 digits: Kvalita hlavička - parse
//                                    before: Hlavička - parse
//
                    trimmed = items[13].trim();
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQHla(Integer.parseInt(qt));
                    playerInfo.setHla(Integer.parseInt(t));

//                                [12]=Trénováno - parse float
                    playerInfo.setTrg(Float.parseFloat(items[14].trim()));
                } else {
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynBra(0);
                    else
                        playerInfo.setDynBra(Integer.parseInt(lines[i].trim()));


                    i++;
                    String trimmed = lines[i].trim().replace("\t", "");
                    int ln = trimmed.length();
                    String qt = trimmed.substring(ln - 2, ln);
                    String t = trimmed.substring(0, ln - 2);
                    playerInfo.setQObr(Integer.parseInt(qt));
                    playerInfo.setObr(Integer.parseInt(t));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynObr(0);
                    else
                        playerInfo.setDynObr(Integer.parseInt(lines[i].trim()));
                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQZal(Integer.parseInt(qt));
                    playerInfo.setZal(Integer.parseInt(t));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynZal(0);
                    else
                        playerInfo.setDynZal(Integer.parseInt(lines[i].trim()));

                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQUto(Integer.parseInt(qt));
                    playerInfo.setUto(Integer.parseInt(t));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynUto(0);
                    else
                        playerInfo.setDynUto(Integer.parseInt(lines[i].trim()));

                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQStr(Integer.parseInt(qt));
                    playerInfo.setStr(Integer.parseInt(t));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynStr(0);
                    else
                        playerInfo.setDynStr(Integer.parseInt(lines[i].trim()));

                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQNah(Integer.parseInt(qt));
                    playerInfo.setNah(Integer.parseInt(t));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynNah(0);
                    else
                        playerInfo.setDynNah(Integer.parseInt(lines[i].trim()));

                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQTec(Integer.parseInt(qt));
                    playerInfo.setTec(Integer.parseInt(t));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynTec(0);
                    else
                        playerInfo.setDynTec(Integer.parseInt(lines[i].trim()));

                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQRyc(Integer.parseInt(qt));
                    playerInfo.setRyc(Integer.parseInt(t));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynRyc(0);
                    else
                        playerInfo.setDynRyc(Integer.parseInt(lines[i].trim()));

                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQHla(Integer.parseInt(qt));
                    playerInfo.setHla(Integer.parseInt(t));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynHla(0);
                    else
                        playerInfo.setDynHla(Integer.parseInt(lines[i].trim()));

                    i++;
                    playerInfo.setTrg(Float.parseFloat(lines[i].trim().replace("\t", "")));
                }
//
//
//            begins on \t or digit?
// :YES:      append when ended from before
//                        if yes:
//                            if starts "" or empty: dynamický trénink = 0
//                            if starts digit: dynamický trénink = digit
//                            if starts \t : schopnosti, qa (last 2 digits)
//                    for all
//              [26]=Trénováno - parse float
//                split \n \r
                String name = playerInfo.getFirstname() + " " + playerInfo.getLastname();
                boolean found = false;

                for (int iPlayer = 0; iPlayer < Players.players.size(); iPlayer++) {
                    if (name.equals(Players.players.get(iPlayer).getName())) {
                        Player player = Players.players.get(iPlayer);
                        player.setTraining(playerInfo);
                        player.setName(name);
                        player.setState(playerInfo.getState());
                        player.setAge(playerInfo.getAge());
                        player.setDayToBeIll(playerInfo.getDaysToBeIll());
                        found = true;
                    }
                }
                if (!found) {
                    Player player = new Player();
                    player.setName(name);
                    player.setState(playerInfo.getState());
                    player.setAge(playerInfo.getAge());
                    player.setTraining(playerInfo);
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

    public static boolean statsField(String data) {


        try {

//                split \n \r
            data = data.replaceAll("\r", "");
            String[] lines = data.split("\n");
//                    [0] header
//                    [>0] data
            StatsPlayerInfo playerInfo = new StatsPlayerInfo();
            for (int i = 1; i < lines.length; i++) {
                playerInfo = new StatsPlayerInfo();
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
//                [1]=MP
                playerInfo.setMP(Integer.parseInt(items[1].trim()));
//                            [2]=Min
                playerInfo.setMin(Integer.parseInt(items[2].trim()));
//                            [3]=G
                playerInfo.setG(Integer.parseInt(items[3].trim()));
//                            [4]=G/M
                playerInfo.setGperM(Float.parseFloat(items[4].trim()));
//                            [5]=A
                playerInfo.setA(Integer.parseInt(items[5].trim().split("/")[0]));
//                            [6]=ST
                playerInfo.setST(Integer.parseInt(items[6].trim()));
//                            [7]=SW
                playerInfo.setSW(Integer.parseInt(items[7].trim()));
//                            [8]=YC
                playerInfo.setYC(Integer.parseInt(items[8].trim()));
//                            [9]=RC
                playerInfo.setRC(Integer.parseInt(items[9].trim()));
//                            [10]=SO
                playerInfo.setSO(Integer.parseInt(items[10].trim()));
//                            [11]=FC
                playerInfo.setFC(Integer.parseInt(items[11].trim()));
//                            [12]=HZ
                playerInfo.setHZ(Integer.parseInt(items[12].trim()));
//                            [13]=JK
                playerInfo.setJK(Integer.parseInt(items[13].trim()));
                String name = playerInfo.getFirstname() + " " + playerInfo.getLastname();
                boolean found = false;
                for (int iPlayer = 0; iPlayer < Players.players.size(); iPlayer++) {
                    if (name.equals(Players.players.get(iPlayer).getName())) {
                        Player player = Players.players.get(iPlayer);
                        player.setFieldStats(playerInfo);
                        player.setName(name);
                        player.setState(playerInfo.getState());
                        player.setDayToBeIll(playerInfo.getDaysToBeIll());
                        found = true;
                    }
                }
                if (!found) {
                    Player player = new Player();
                    player.setName(name);
                    player.setState(playerInfo.getState());
                    player.setFieldStats(playerInfo);
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

    public static boolean statsGoalie(String data) {
        try {

//                split \n \r
            data = data.replaceAll("\r", "");
            String[] lines = data.split("\n");
//                    [0] header
//                    [>0] data
            StatsPlayerGoalieInfo playerInfo = new StatsPlayerGoalieInfo();
            for (int i = 1; i < lines.length; i++) {
                playerInfo = new StatsPlayerGoalieInfo();
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
//                            [1]=MP
                playerInfo.setMP(Integer.parseInt(items[1].trim()));
//                            [2]=GA
                playerInfo.setGA(Integer.parseInt(items[2].trim()));
//                            [3]=SV
                playerInfo.setSV(Integer.parseInt(items[3].trim()));
//                            [4]=Sv%
                playerInfo.setSvPercentage(Float.parseFloat(items[4].trim()));
//                            [5]=CS
                playerInfo.setCS(Integer.parseInt(items[5].trim()));
//                            [6]=Str
                playerInfo.setStr(items[6].trim());
//                            [7]=LStr
                playerInfo.setLStr(items[7].trim());

                String name = playerInfo.getFirstname() + " " + playerInfo.getLastname();
                boolean found = false;
                for (int iPlayer = 0; iPlayer < Players.players.size(); iPlayer++) {
                    if (name.equals(Players.players.get(iPlayer).getName())) {
                        Player player = Players.players.get(iPlayer);
                        player.setGoalieStats(playerInfo);
                        player.setName(name);
                        player.setState(playerInfo.getState());
                        player.setDayToBeIll(playerInfo.getDaysToBeIll());
                        found = true;
                    }
                }
                if (!found) {
                    Player player = new Player();
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
