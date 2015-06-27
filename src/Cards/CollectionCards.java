package Cards;

public class CollectionCards {
    int cardCount;
    Card[] collection = new Card[7];
    boolean[][] table = new boolean[14][4];
    byte[] flashDataMas = new byte[4];
    boolean[] straightDataMas = new boolean[15];
    byte[] pairDataMas = new byte[14];//how many cards with same rank in combination, 0 & 13 aces
    byte sameSuitMax;
    byte sameSuitMaxIndex;
    //public bool flashID;
    byte straightFlashIndex;
    byte straightIndex;

    public CollectionCards(Card... c) {
        cardCount = c.length;
        for (int i = 0; i < cardCount; i++) {
            collection[i] = new Card(c[i]);
        }
        tableBuilder();
    }

    /*public void addNewHand (Hand hand)
    {
        collection[5] = hand.c1;
        collection[6] = hand.c2;
        cardCount = 7;
        tableBuilder();
    }*/

    private void tableBuilder() {
        //inicialization
        sameSuitMax = 0;
        sameSuitMaxIndex = 0;
        //flashID = false;
        for (int i = 0; i < 14; i++) {
            straightDataMas[i] = false;
            for (int j = 0; j < 4; j++)
                table[i][j] = false;
        }

        for (int i = 0; i < 13; i++)
            pairDataMas[i] = 0;

        for (int i = 0; i < 4; i++)
            flashDataMas[i] = 0;
        //inicialization

        for (int i = 0; i < cardCount; i++) {
            if (collection[i].suit == 0) {
                table[collection[i].rank - 1][0] = true;
                straightDataMas[collection[i].rank - 1] = true;
                pairDataMas[collection[i].rank - 1]++;
                if (collection[i].rank == 14) {
                    table[0][0] = true;
                    straightDataMas[0] = true;
                }
                flashDataMas[0]++;
            }
            if (collection[i].suit == 1) {
                table[collection[i].rank - 1][1] = true;
                straightDataMas[collection[i].rank - 1] = true;
                pairDataMas[collection[i].rank - 1]++;
                if (collection[i].rank == 14) {
                    straightDataMas[0] = true;
                    table[0][1] = true;
                }
                flashDataMas[1]++;
            }
            if (collection[i].suit == 2) {
                table[collection[i].rank - 1][2] = true;
                straightDataMas[collection[i].rank - 1] = true;
                pairDataMas[collection[i].rank - 1]++;
                if (collection[i].rank == 14) {
                    table[0][2] = true;
                    straightDataMas[0] = true;
                }
                flashDataMas[2]++;
            }
            if (collection[i].suit == 3) {
                table[collection[i].rank - 1][3] = true;
                straightDataMas[collection[i].rank - 1] = true;
                pairDataMas[collection[i].rank - 1]++;
                if (collection[i].rank == 14) {
                    table[0][3] = true;
                    straightDataMas[0] = true;
                }
                flashDataMas[3]++;
            }
        }

        for (int i = 0; i < 4; i++)
            if (sameSuitMax < flashDataMas[i]) {
                sameSuitMax = flashDataMas[i];
                sameSuitMaxIndex = (byte) i;
            }
        //checkFlash();
    }

    private boolean checkFlash() {
        return sameSuitMax >= 5;
    }

    public boolean checkStraight() {
        byte counter = 0;
        for (byte i = 14; i > 0; i--) {
            if (straightDataMas[i]) {
                counter++;
                if (counter >= 5) {
                    straightIndex = i;
                    return true;
                }
            } else
                counter = 0;
        }
        return false;
    }

    public boolean checkStraightFlash() {
        byte counter = 0;
        for (byte i = 13; i > 0; i--) {
            if (table[i][sameSuitMaxIndex]) {
                counter++;
                if (counter >= 5) {
                    straightFlashIndex = i;
                    return true;
                }
            } else
                counter = 0;
        }
        return false;
    }

    public String checkCombination() {
        //int kareIndex = 0;

        //вспомогательный массив (4, 1, 1, 0) - фулл хаус
        byte[] counter = new byte[]{0, 0, 0, 0};
        for (int i = 1; i < 14; i++) {
            if (pairDataMas[i] > 0)
                counter[pairDataMas[i] - 1]++;
            if (pairDataMas[i] == 4) {
                return String.format("Kare of %d", i);
            }
        }

        if (checkFlash()) {
            if (checkStraightFlash()) {
                return String.format("Straight Flash from %d to %d", straightFlashIndex + 1, straightFlashIndex + 5);
            } else {
                byte[] com = new byte[5];
                byte j = 0;
                byte i = 13;
                while ((j < 5) || (i <= 0)) {
                    if (table[i][sameSuitMaxIndex]) {
                        com[j] = (byte) (i + 1);
                        j++;
                    }
                    i--;
                }
                return String.format("Flash %d + %d + %d + %d + %d", com[0], com[1], com[2], com[3], com[4]);
            }
        }

        if (checkStraight()) {
            return String.format("Straight from %d to %d", straightIndex + 1, straightIndex + 5);
        }

        if (counter[1] > 0)
            if (counter[2] > 0) {
                int maxSetId = 1, maxPairId = 1;
                for (int i = 1; i < 14; i++) {
                    if (pairDataMas[i] == 3) {
                        maxSetId = i;
                    } else {
                        if (pairDataMas[i] == 2) {
                            if (maxPairId < i) {
                                maxPairId = i;
                            }
                        }
                    }
                }
                return String.format("Full house %d %d %d %d %d", maxSetId + 1, maxSetId + 1, maxSetId + 1, maxPairId + 1, maxPairId + 1);
            } else {
                int i = 0;
                if (counter[1] == 1) {
                    do {
                        i++;
                    } while (pairDataMas[i] == 2);
                    return String.format("Pair of %d", i + 1);
                } else {
                    int maxPair1 = 1;
                    int maxPair2 = 2;
                    for (int j = 1; j < 14; j++) {
                        if (pairDataMas[i] > maxPair1) {
                            if (pairDataMas[i] > maxPair2) {
                                maxPair2 = pairDataMas[i];
                            } else {
                                maxPair1 = pairDataMas[i];
                            }
                        }
                    }
                    return String.format("Two pairs %d and %d", maxPair1, maxPair2);
                }
            }

        else {
            if (counter[2] > 0) {
                int i = 1;
                do {
                    i++;
                } while (pairDataMas[i] != 3);
                return String.format("Set of %d", i + 1);
            }
        }
        return "Kicker";
    }

    public int checkCombinationRank() {
        byte kareIndex = 0;

        //вспомогательный массив (4, 1, 1, 0) - фулл хаус
        byte[] counter = new byte[]{0, 0, 0, 0};
        for (int i = 1; i < 14; i++) {
            if ((pairDataMas[i] > 0) && (pairDataMas[i] < 5))
                counter[pairDataMas[i] - 1]++;
            if (pairDataMas[i] == 4) {
                kareIndex = (byte) i;
            }
        }

        if (counter[3] > 0) {
            for (int i = 13; i > 1; i--)
                if ((i != kareIndex) && straightDataMas[i])
                    //return String.format("Kare of {0} with kicker {1}", kareIndex + 1, i + 1);
                    return 7;
        } else {
            if (checkFlash()) {
                if (checkStraightFlash()) {
                    return 8;
                } else {
                    byte[] com = new byte[5];
                    byte j = 0;
                    byte i = 13;
                    while ((j < 5) && (i < 13)) {
                        if (table[i][sameSuitMaxIndex]) {
                            com[j] = (byte) (i + 1);
                            j++;
                        }
                        i--;
                    }
                    return 5;
                }
            }

            if (checkStraight()) {
                return 4;
            }

            if (counter[1] > 0)
                if (counter[2] > 0)
                    return 6;
                else if (counter[1] == 1)
                    return 1;
                else
                    return 2;
            else if (counter[2] > 0)
                return 3;
        }
        return 0;
    }

    private static int kicker(CollectionCards c1, CollectionCards c2) {
        return kickerRec(c1, c2, 14, 0, 5);
    }

    private static int kickerRec(CollectionCards c1, CollectionCards c2, int index, int coolectionLength, int kickerNumber) {
        if (coolectionLength < kickerNumber) {
            int i = index;
            do {
                i--;
            } while (c1.pairDataMas[i] == 0 && c2.pairDataMas[i] == 0);
            if (c1.pairDataMas[i] == 1) {
                if (c2.pairDataMas[i] == 1) {
                    return kickerRec(c1, c2, i, coolectionLength + 1, kickerNumber);
                } else {
                    return 1;
                }
            } else {
                if (c2.pairDataMas[i] == 1) {
                    return -1;
                } else {
                    System.out.println("NE WER' GLAZAM SWOIM");
                }
            }
        }
        System.out.println("NE WER' GLAZAM SWOIM");
        return 0;
    }

    private static int pair(CollectionCards c1, CollectionCards c2) {
        int i = 14;
        do {
            i--;
        } while (c1.pairDataMas[i] <= 1 && c2.pairDataMas[i] <= 1);
        if (c1.pairDataMas[i] >= 2)
            if (c2.pairDataMas[i] >= 2)
                return kickerRec(c1, c2, 14, 0, 3);
            else
                return 1;
        else if (c2.pairDataMas[i] >= 2)
            return -1;
        else
            System.out.println("NE WER' GLAZAM SWOIM");
        System.out.println("NE WER' GLAZAM SWOIM");
        return 0;
    }

    private static int twoPairs(CollectionCards c1, CollectionCards c2) {
        int i = 14;
        do {
            i--;
        } while (c1.pairDataMas[i] <= 1 && c2.pairDataMas[i] <= 1);
        if (c1.pairDataMas[i] >= 2)
            if (c2.pairDataMas[i] >= 2) {
                do {
                    i--;
                } while (c1.pairDataMas[i] <= 1 && c2.pairDataMas[i] <= 1);
                if (c1.pairDataMas[i] >= 2)
                    if (c2.pairDataMas[i] >= 2)
                        return kickerRec(c1, c2, 14, 0, 1);
                else
                    return 1;
                else
                if (c2.pairDataMas[i] >= 2)
                    return -1;
                else
                    System.out.println("NE WER' GLAZAM SWOIM");
                System.out.println("NE WER' GLAZAM SWOIM");
                return 0;
            } else
                return 1;
        else if (c2.pairDataMas[i] >= 2)
            return -1;
        else
            System.out.println("NE WER' GLAZAM SWOIM");
        System.out.println("NE WER' GLAZAM SWOIM");
        return 0;
    }

    private static int set(CollectionCards c1, CollectionCards c2) {
        int i = 14;
        do {
            i--;
        } while (c1.pairDataMas[i] <= 2 && c2.pairDataMas[i] <= 2);
        if (c1.pairDataMas[i] >= 3)
            if (c2.pairDataMas[i] >= 3)
                return kickerRec(c1, c2, 14, 0, 2);
            else
                return 1;
        else if (c2.pairDataMas[i] >= 2)
            return -1;
        else
            System.out.println("NE WER' GLAZAM SWOIM");
        System.out.println("NE WER' GLAZAM SWOIM");
        return 0;
    }

    private static int straight(CollectionCards c1, CollectionCards c2) {
        if (c1.straightIndex > c2.straightIndex)
            return 1;
        else if (c2.straightIndex > c1.straightIndex)
            return -1;
        else
            return 0;
    }

    private static int flash(CollectionCards c1, CollectionCards c2, int index, int coolectionLength) {
        if (coolectionLength < 5) {
            int i = index;
            do {
                i--;
            } while (c1.table[i][c1.sameSuitMaxIndex] && c2.table[i][c2.sameSuitMaxIndex]);
            if (c1.table[i][c1.sameSuitMaxIndex]) {
                if (c2.table[i][c2.sameSuitMaxIndex]) {
                    return flash(c1, c2, i, coolectionLength + 1);
                } else {
                    return 1;
                }
            } else {
                if (c2.table[i][c2.sameSuitMaxIndex]) {
                    return -1;
                } else {
                    System.out.println("NE WER' GLAZAM SWOIM");
                }
            }
        }
        return 0;
    }

    private static int fullHouse(CollectionCards c1, CollectionCards c2) {
        int i = 14;
        do {
            i--;
        } while (c1.pairDataMas[i] < 3 && c2.pairDataMas[i] < 3);
        if (c1.pairDataMas[i] == 3)
            if (c2.pairDataMas[i] == 3) {
                i = 14;
                do {
                    i--;
                } while (c1.pairDataMas[i] < 2 && c2.pairDataMas[i] < 2);
                if (c1.pairDataMas[i] == 2)
                    if (c2.pairDataMas[i] == 2) {
                        return 0;
                    } else
                        return 1;
                else
                    return -1;
            } else
                return 1;
        else
            return -1;
    }

    private static int kare(CollectionCards c1, CollectionCards c2) {
        int i = 14;
        do {
            i--;
        } while (c1.pairDataMas[i] < 4 && c2.pairDataMas[i] < 4);
        if (c1.pairDataMas[i] == 4)
            return 1;
        else
            return -1;
    }

    public static int bigRank(CollectionCards c1, CollectionCards c2, int combinationRank) {
        switch (combinationRank) {
            case 0: {
                return kicker(c1, c2);
                //break;
            }
            case 1: {
                return pair(c1, c2);
                //break;
            }
            case 2: {
                return twoPairs(c1, c2);
                //break;
            }
            case 3: {
                return set(c1, c2);
                //break;
            }
            case 4: {
                return straight(c1, c2);
                //break;
            }
            case 5: {
                return flash(c1, c2, 14, 0);
                //break;
            }
            case 6: {
                return fullHouse(c1, c2);
                //break;
            }
            case 7: {
                return kare(c1, c2);
                //break;
            }
            default:
                return 0;
            //break;
        }
    }

    public static int bigger(CollectionCards c1, CollectionCards c2) {
        int cr1 = c1.checkCombinationRank();
        int cr2 = c2.checkCombinationRank();
        if (cr1 > cr2)
            return 1;
        if (cr2 < cr1)
            return -1;
        if (cr1 == cr2)
            return bigRank(c1, c2, cr1);
        return 0;
    }

    public int bigger(CollectionCards col, int myComb) {
        switch (myComb) {
            case 0: {
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                break;
            }
            case 5: {
                break;
            }
            case 6: {
                break;
            }
            case 7: {
                break;
            }
            default:
                break;
        }


        byte kareIndex = 0;

        //вспомогательный массив (4, 1, 1, 0) - фулл хаус
        byte[] counter = new byte[]{0, 0, 0, 0};
        for (int i = 1; i < 14; i++) {
            if ((pairDataMas[i] > 0) && (pairDataMas[i] < 5))
                counter[pairDataMas[i] - 1]++;
            if (pairDataMas[i] == 4) {
                kareIndex = (byte) i;
            }
        }

        if (counter[3] > 0) {
            for (int i = 13; i > 1; i--)
                if ((i != kareIndex) && straightDataMas[i])
                    //return String.format("Kare of {0} with kicker {1}", kareIndex + 1, i + 1);
                    return 7;
        } else {
            if (checkFlash()) {
                if (checkStraightFlash()) {
                    return 8;
                } else {
                    byte[] com = new byte[5];
                    byte j = 0;
                    byte i = 13;
                    while ((j < 5) && (i < 13)) {
                        if (table[i][sameSuitMaxIndex]) {
                            com[j] = (byte) (i + 1);
                            j++;
                        }
                        i--;
                    }
                    return 5;
                }
            }

            if (checkStraight()) {
                return 4;
            }

            if (counter[1] > 0)
                if (counter[2] > 0)
                    return 6;
                else if (counter[1] == 1)
                    return 1;
                else
                    return 2;
            else if (counter[2] > 0)
                return 3;
        }
        return 0;
    }
}