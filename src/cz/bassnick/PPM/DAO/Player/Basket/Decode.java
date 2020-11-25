package cz.bassnick.PPM.DAO.Player.Basket;


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
                String[] e = items[8].split("/");
                playerInfo.setEnergy(Integer.parseInt(e[0].replace("/","").trim()));
                playerInfo.setMaxEnergy(Integer.parseInt(e[1].replace("/","").trim()));
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
        }
        catch (Throwable t) {
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
                            [6]=Střelba - parse
                            [7]=Bloky - parse
                            [8]-Nahrávky - parse
                            [9]=Technika - parse
                            [10]=Rychlost - parse
                            [11]=Agresivita - parse
                            [12]=Výskok - parse
                            [13]=Zkušenost - parse
                            [14]=Celkové zkušenosti - parse
                            [15]=Výška - parse
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
//                            [6]=Střelba - parse
                playerInfo.setStr(Integer.parseInt(items[6].trim()));
//                            [7]=Bloky - parse
                playerInfo.setBlk(Integer.parseInt(items[7].trim()));
//                            [8]=Nahrávky - parse
                playerInfo.setNah(Integer.parseInt(items[8].trim()));
//                            [9]=Technika - parse
                playerInfo.setTec(Integer.parseInt(items[9].trim()));
//                            [10]=Rychlost - parse
                playerInfo.setRyc(Integer.parseInt(items[10].trim()));
//                            [11]=Agresivita - parse
                playerInfo.setAgr(Integer.parseInt(items[11].trim()));
//                            [12]=Výskok - parse
                playerInfo.setVys(Integer.parseInt(items[12].trim()));
//                            [13]=Zkušenost - parse
                playerInfo.setZku(Integer.parseInt(items[13].trim()));
//                            [14]=Celkové zkušenosti - parse
                playerInfo.setCZ(Integer.parseInt(items[14].trim()));
//                            [15]=Výška - parse
                playerInfo.setVyska(Integer.parseInt(items[15].trim()));
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
//                                [5]=Výška
                playerInfo.setVyska(Integer.parseInt(items[5].trim()));
//                                [6]=
//                                    last 2 digits: Kvalita Střelba - parse
//                                    before: Střelba- parse
                String trimmed = items[6].trim();
                int ln = trimmed.length();
                String qt = trimmed.substring(ln - 2, ln);
                String t = trimmed.substring(0, ln - 2);
                playerInfo.setQStr(Integer.parseInt(qt));
                playerInfo.setStr(Integer.parseInt(t));
//                                  if other '\t' then 

//                                  else if notother '\t' then 
                if (items.length > 7) {
                    playerInfo.setDynStr(0);
                    playerInfo.setDynBlk(0);
                    playerInfo.setDynNah(0);
                    playerInfo.setDynTec(0);
                    playerInfo.setDynRyc(0);
                    playerInfo.setDynAgr(0);
                    playerInfo.setDynVys(0);
//                                [7]=
//                                    last 2 digits: Kvalita bloky - parse
//                                    before: bloky - parse
                    trimmed = items[7].trim();
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQBlk(Integer.parseInt(qt));
                    playerInfo.setBlk(Integer.parseInt(t));
//                    
//
//                                [8]=
//                                    last 2 digits: Kvalita nahrávky - parse
//                                    before: nahrávky - parse
                    trimmed = items[8].trim();
                    ln = trimmed.length();
                    qt= trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQNah(Integer.parseInt(qt));
                    playerInfo.setNah(Integer.parseInt(t));
//
//
//                                [9]=
//                                   last 2 digits: Kvalita technika - parse
//                                    before: Technika - parse
                    trimmed = items[9].trim();
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQTec(Integer.parseInt(qt));
                    playerInfo.setTec(Integer.parseInt(t));

//                                [10]=
//                                    last 2 digits: Kvalita rychlost - parse
//                                    before: Rychlost - parse

                    trimmed = items[10].trim();
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQRyc(Integer.parseInt(qt));
                    playerInfo.setRyc(Integer.parseInt(t));
//                    
//
//                                [11]=
//                                    last 2 digits: Kvalita agresivita - parse
//                                    before: agresivita - parse

                    trimmed = items[11].trim();
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQAgr(Integer.parseInt(qt));
                    playerInfo.setAgr(Integer.parseInt(t));

//
//                                [12]=
//                                    last 2 digits: Kvalita výskok - parse
//                                    before: Výskok - parse
                    trimmed = items[12].trim();
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln - 2);
                    playerInfo.setQVys(Integer.parseInt(qt));
                    playerInfo.setVys(Integer.parseInt(t));

//                                [13]=Trénováno - parse float
                    playerInfo.setTrg(Float.parseFloat(items[13].trim()));
                }
                else {
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynStr(0);
                    else
                        playerInfo.setDynStr(Integer.parseInt(lines[i].trim()));


                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln- 2);
                    playerInfo.setQBlk(Integer.parseInt(qt));
                    playerInfo.setBlk(Integer.parseInt(t));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynBlk(0);
                    else
                        playerInfo.setDynBlk(Integer.parseInt(lines[i].trim()));
                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln- 2);
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
                    t = trimmed.substring(0, ln- 2);
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
                    t = trimmed.substring(0, ln- 2);
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
                    t = trimmed.substring(0, ln- 2);
                    playerInfo.setQAgr(Integer.parseInt(qt));
                    playerInfo.setAgr(Integer.parseInt(t));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynAgr(0);
                    else
                        playerInfo.setDynAgr(Integer.parseInt(lines[i].trim()));

                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    qt = trimmed.substring(ln - 2, ln);
                    t = trimmed.substring(0, ln- 2);
                    playerInfo.setQVys(Integer.parseInt(qt));
                    playerInfo.setVys(Integer.parseInt(t));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynVys(0);
                    else
                        playerInfo.setDynVys(Integer.parseInt(lines[i].trim()));

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

    public static boolean statsGame(String data) {
        StatsGamePlayerInfo playerInfo = new StatsGamePlayerInfo();
        try {
            //split \n \r
            data = data.replaceAll("\r", "");
            String[] lines = data.split("\n");
            //[0] header
            //[>0] data
            for (int i = 1; i < lines.length; i++) {
                playerInfo = new StatsGamePlayerInfo();
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
//                                last=Prijmeni
                playerInfo.setLastname(infos[len - 1]);
//                                [1]=GP
                playerInfo.setGP(Integer.parseInt(items[1].trim()));
//                                [2]=Min
                playerInfo.setMin(Float.parseFloat(items[2].trim()));
//                                [3]=2FGM
                playerInfo.setFGM2(Float.parseFloat(items[3].trim()));
//                                [4]=2FGA
                playerInfo.setFGA2(Float.parseFloat(items[4].trim()));
//                                [5]=2FG%
                playerInfo.setFG2Percent(Float.parseFloat(items[5].trim()));
//                                [6]=3FGM
                playerInfo.setFGM3(Float.parseFloat(items[6].trim()));
//                                [7]=3FGA
                playerInfo.setFGA3(Float.parseFloat(items[7].trim()));
//                                [8]=3FG%
                playerInfo.setFG3Percent(Float.parseFloat(items[8].trim()));
//                                [9]=FTM
                playerInfo.setFTM(Float.parseFloat(items[9].trim()));
//                                [10]=FTA
                playerInfo.setFTA(Float.parseFloat(items[10].trim()));
//                                [11]=FT%
                playerInfo.setFTPercent(Float.parseFloat(items[11].trim()));
//                                [12]=OReb
                playerInfo.setOReb(Float.parseFloat(items[12].trim()));
//                                [13]=DReb
                playerInfo.setDReb(Float.parseFloat(items[13].trim()));
//                                [14]=Reb
                playerInfo.setReb(Float.parseFloat(items[14].trim()));
//                                [15]=A
                playerInfo.setA(Float.parseFloat(items[15].trim()));
//                                [16]=TO
                playerInfo.setTO(Float.parseFloat(items[16].trim()));
//                                [17]=ST
                playerInfo.setST(Float.parseFloat(items[17].trim()));
//                                [18]=Blk
                playerInfo.setBlk(Float.parseFloat(items[18].trim()));
//                                [19]=PF
                playerInfo.setPF(Float.parseFloat(items[19].trim()));
//                                [20]=Pts
                playerInfo.setPts(Float.parseFloat(items[20].trim()));
//                                [21]=+/-
                playerInfo.setPlusMinus(Float.parseFloat(items[21].trim()));
//                                [22]=Hv
                playerInfo.setHv(Integer.parseInt(items[22].trim()));
//                                [23]=HK
                playerInfo.setHK(Integer.parseInt(items[23].trim()));
                String name = playerInfo.getFirstname() + " " + playerInfo.getLastname();
                boolean found = false;

                for (int iPlayer = 0; iPlayer < Players.players.size(); iPlayer++) {
                    if (name.equals(Players.players.get(iPlayer).getName())) {
                        Player player = Players.players.get(iPlayer);
                        player.setStatsGame(playerInfo);
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
                    player.setStatsGame(playerInfo);
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

    public static boolean stats40(String data) {
        Stats40PlayerInfo playerInfo = new Stats40PlayerInfo();
        try {
            //split \n \r
            data = data.replaceAll("\r", "");
            String[] lines = data.split("\n");
            //[0] header
            //[>0] data
            for (int i = 1; i < lines.length; i++) {
                playerInfo = new Stats40PlayerInfo();
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
//                                last=Prijmeni
                playerInfo.setLastname(infos[len - 1]);
//                                [1]=GP
                playerInfo.setGP(Integer.parseInt(items[1].trim()));
//                                [2]=2FGM
                playerInfo.setFGM2(Float.parseFloat(items[2].trim()));
//                                [3]=2FGA
                playerInfo.setFGA2(Float.parseFloat(items[3].trim()));
//                                [4]=2FG%
                playerInfo.setFG2Percent(Float.parseFloat(items[4].trim()));
//                                [5]=3FGM
                playerInfo.setFGM3(Float.parseFloat(items[5].trim()));
//                                [6]=3FGA
                playerInfo.setFGA3(Float.parseFloat(items[6].trim()));
//                                [7]=3FG%
                playerInfo.setFG3Percent(Float.parseFloat(items[7].trim()));
//                                [8]=FTM
                playerInfo.setFTM(Float.parseFloat(items[8].trim()));
//                                [9]=FTA
                    playerInfo.setFTA(Float.parseFloat(items[9].trim()));
//                                [10]=FT%
                    playerInfo.setFTPercent(Float.parseFloat(items[10].trim()));
//                                [11]=OReb
                    playerInfo.setOReb(Float.parseFloat(items[11].trim()));
//                                [12]=DReb
                    playerInfo.setDReb(Float.parseFloat(items[12].trim()));
//                                [13]=Reb
                    playerInfo.setReb(Float.parseFloat(items[13].trim()));
//                                [14]=A
                    playerInfo.setA(Float.parseFloat(items[14].trim()));
//                                [15]=TO
                playerInfo.setTO(Float.parseFloat(items[15].trim()));
//                                [16]=ST
                playerInfo.setST(Float.parseFloat(items[16].trim()));
//                                [17]=Blk
                playerInfo.setBlk(Float.parseFloat(items[17].trim()));
//                                [18]=PF
                playerInfo.setPF(Float.parseFloat(items[18].trim()));
//                                [19]=Pts
                playerInfo.setPts(Float.parseFloat(items[19].trim()));
//                                [20]=+/-
                playerInfo.setPlusMinus(Float.parseFloat(items[20].trim()));
//                                [21]=Hv
                playerInfo.setHv(Integer.parseInt(items[21].trim()));
//                                [22]=HK
                playerInfo.setHK(Integer.parseInt(items[22].trim()));
                String name = playerInfo.getFirstname() + " " + playerInfo.getLastname();
                boolean found = false;

                for (int iPlayer = 0; iPlayer < Players.players.size(); iPlayer++) {
                    if (name.equals(Players.players.get(iPlayer).getName())) {
                        Player player = Players.players.get(iPlayer);
                        player.setStats40(playerInfo);
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
                    player.setStats40(playerInfo);
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
