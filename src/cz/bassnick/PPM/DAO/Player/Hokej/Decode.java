package cz.bassnick.PPM.DAO.Player.Hokej;

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
                            [8]=Útočné schopnosti - parse
                            [9]=Střelba - parse
                            [10]=Nahrávka - parse
                            [11]=Technika - parse
                            [12]=Agresivita - parse
                            [13]=Zkušenost - parse
                            [14]=Celkové zkušenosti - parse
                            [15]=Preferovaná strana
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
//                            [8]=Útočné schopnosti - parse
                playerInfo.setUto(Integer.parseInt(items[8].trim()));
//                            [9]=Střelba - parse
                playerInfo.setStr(Integer.parseInt(items[9].trim()));
//                            [10]=Nahrávka - parse
                playerInfo.setNah(Integer.parseInt(items[10].trim()));
//                            [11]=Technika - parse
                playerInfo.setTec(Integer.parseInt(items[11].trim()));
//                            [12]=Agresivita - parse
                playerInfo.setAgr(Integer.parseInt(items[12].trim()));
//                            [13]=Zkušenost - parse
                playerInfo.setZku(Integer.parseInt(items[13].trim()));
//                            [14]=Celkové zkušenosti - parse
                playerInfo.setCZ(Integer.parseInt(items[14].trim()));
//                            [15]=Preferovaná strana
                playerInfo.setPreferredSide(items[15].trim());
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
                    playerInfo.setDynUto(0);
                    playerInfo.setDynStr(0);
                    playerInfo.setDynNah(0);
                    playerInfo.setDynTec(0);
                    playerInfo.setDynAgr(0);

//                                [6]=
//                                    last 2 digits: Kvalita obranné schopnosti - parse
//                                    before: Obranné schopnosti - parse
                    String trimmed6 = items[6].trim();
                    int lenObr = trimmed6.length();
                    String QObr = trimmed6.substring(lenObr - 2, lenObr);
                    String Obr = trimmed6.substring(0, lenObr - 2);
                    playerInfo.setQObr(Integer.parseInt(QObr));
                    playerInfo.setObr(Integer.parseInt(Obr));
//                    
//
//                                [7]=
//                                    last 2 digits: Kvalita útočné schopnosti - parse
//                                    before: Útočné  schopnosti - parse
                    String trimmed7 = items[7].trim();
                    int lenUto = trimmed7.length();
                    String QUto = trimmed7.substring(lenUto - 2, lenUto);
                    String Uto = trimmed7.substring(0, lenUto - 2);
                    playerInfo.setQUto(Integer.parseInt(QUto));
                    playerInfo.setUto(Integer.parseInt(Uto));

//                                [8]=
//                                    last 2 digits: Kvalita střelba - parse
//                                    before: Střelba - parse

                    String trimmed8 = items[8].trim();
                    int lenStr = trimmed8.length();
                    String QStr = trimmed8.substring(lenStr - 2, lenStr);
                    String Str = trimmed8.substring(0, lenStr - 2);
                    playerInfo.setQStr(Integer.parseInt(QStr));
                    playerInfo.setStr(Integer.parseInt(Str));
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
                    playerInfo.setNah(Integer.parseInt(Obr));

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
                    String trimmed11 = items[11].trim();
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
                    String QObr = trimmed.substring(ln - 2, ln);
                    String Obr = trimmed.substring(0, ln- 2);
                    playerInfo.setQObr(Integer.parseInt(QObr));
                    playerInfo.setObr(Integer.parseInt(Obr));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynObr(0);
                    else
                        playerInfo.setDynObr(Integer.parseInt(lines[i].trim()));

                    i++;
                    trimmed = lines[i].trim().replace("\t", "");
                    ln = trimmed.length();
                    String QUto = trimmed.substring(ln - 2, ln);
                    String Uto = trimmed.substring(0, ln- 2);
                    playerInfo.setQUto(Integer.parseInt(QUto));
                    playerInfo.setUto(Integer.parseInt(Uto));
                    i++;
                    if (lines[i] == null || lines[i].trim().length() == 0)
                        playerInfo.setDynUto(0);
                    else
                        playerInfo.setDynUto(Integer.parseInt(lines[i].trim()));

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
        StatsFieldInfo playerInfo = new StatsFieldInfo();
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
                                [4]=TP  = Kanadske body
                                [5]=+/-
                                [6]=PIM = Trestnych minut
                                [7]=DS  = Kanadske bodovani obrancu
                                [8]=PPG = golu v presilovce
                                [9]=SHG = golu ve vlastnim oslabeni
                                [10]=S  = pocet strel
                                [11]=S% = uspesnost strelby
                                [12]=G/G = golu na zapas
                                [13]=A/G = asistenci na zapas
                                [14]=P/G = Kanadske body na zapas
                                [15]=Hv  = Hvezda zapasu
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
//                    [5] = + / -
            playerInfo.setPlusMinus(Integer.parseInt(items[5].trim()));
//                    [6]=PIM = Trestnych minut
            playerInfo.setPIM(Integer.parseInt(items[6].trim()));
//                    [7] = DS = Kanadske bodovani obrancu
            playerInfo.setDS(Integer.parseInt(items[7].trim()));
//                    [8]=PPG = golu v presilovce
            playerInfo.setPPG(Integer.parseInt(items[8].trim()));
//                    [9]=SHG = golu ve vlastnim oslabeni
            playerInfo.setSHG(Integer.parseInt(items[9].trim()));
//                    [10] = S = pocet strel
            playerInfo.setS(Integer.parseInt(items[10].trim()));
//                    [11] = S % = uspesnost strelby
            playerInfo.setSPercentage(Float.parseFloat(items[11].trim()));
//                    [12] = G / G = golu na zapas
            playerInfo.setGG(Float.parseFloat(items[12].trim()));
//                    [13]=A / G = asistenci na zapas
            playerInfo.setAG(Float.parseFloat(items[13].trim()));
//                    [14]=P / G = Kanadske body na zapas
            playerInfo.setPG(Float.parseFloat(items[14].trim()));
//                    [15] = Hv = Hvezda zapasu
            playerInfo.setHv(Integer.parseInt(items[15].trim()));
//                    [16] = HK = Hvezda kola
            playerInfo.setHK(Integer.parseInt(items[16].trim()));

            String name = playerInfo.getFirstname() + " " + playerInfo.getLastname();
            boolean found = false;

            for (int iPlayer = 0; iPlayer < Players.players.size(); iPlayer++) {
                if (name.equals(Players.players.get(iPlayer).getName())) {
                    Player player = Players.players.get(iPlayer);
                    player.setStatField(playerInfo);
                    found = true;
                }
            }
            if (!found) {
                Player player = new Player();
                player.setName(name);
                player.setState(playerInfo.getState());
                player.setStatField(playerInfo);
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
