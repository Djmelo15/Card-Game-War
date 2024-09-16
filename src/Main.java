import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Main {


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //initalizing decks from the playerdecks class, a deck for each player, so 2 total
        PlayerDecks p1Cards = new PlayerDecks();
        PlayerDecks p2Cards = new PlayerDecks();


        int p1CardCount, p2CardCount;


        p1CardCount = input.nextInt();//getting the number of cards for the first player
        while (p1CardCount < 0) {//ensuring that the constraint of N>0 is being met
            p1CardCount = input.nextInt();//if N < 0, it asks for the input again
        }
        for (int x = 0; x < p1CardCount; x++) {//collecting card inputs one by one
            String temp = input.next();
            p1Cards.addP1(temp);
        }


        p2CardCount = input.nextInt(); //getting the number of cards in the second deck
        while (p2CardCount > 1000) {//ensuring that the constraint of M<1000 is being met
            p2CardCount = input.nextInt();//if M>1000, it asks for the input again
        }


        for (int x = 0; x < p2CardCount; x++) { //entering the cards into deck 2 one by one
            String temp = input.next();
            p2Cards.addP2(temp);
        }


        int winner = battles(p1Cards, p2Cards); //uses the battle method to get an integer value, the battle method figures out which card has a greater value, or if they're equal


        if (winner == 2) { //if battle returns a value of 2, player two wins
            System.out.println("2 " + Counter.getCount());
        }
        else if (winner == 1) {//if it returns 1, player 1 wins
            System.out.println("1 " + Counter.getCount());
        }
        else if (winner == 0) { //if it returns 0, it means that a player ran out of cards mid war, resulting in both being winners, PAT
            System.out.println("PAT");
        }
    }




    static int battles(PlayerDecks p1Cards, PlayerDecks p2Cards) {//the battle method which handles singular battles
        Battle battle = new Battle();
        if (Counter.getnoCards() == 1) { //before checking if any of the decks are empty, a counter is checked to see if a player ran out of cards mid war, which would return 0 resulting in PAT
            return 0;
        }
        if (p1Cards.p1isEmpty()) {//if player 1's deck is empty, that means player 2 wins
            return 2;
        }
        if (p2Cards.p2isEmpty()) {//if P2's deck is empty, it means P1 wins
            return 1;
        }
        //saving the top cards of each deck into String varibles to send them into the normalBattle method in Battle class
        String p1Top = p1Cards.removeP1();
        String p2Top = p2Cards.removeP2();


        int val = Battle.normalBattle(p1Top, p2Top); //this returns a value saved into the var 'val'
        if (val == 1) {//if val is 1, that means player 1 had the higher card, so they win both cards
            p1Cards.addP1(p1Top);
            p1Cards.addP1(p2Top);
        }
        else if (val == 2) {//if val = 2, it means p2 won, so player 2's deck gains the two cards that battled
            p2Cards.addP2(p1Top);
            p2Cards.addP2(p2Top);
        }
        else if (val == 0) { //if the method returns a 0, that means that the cards are of equal value, which means a war
            //two new queues are created to save cards into that will be later used to transfer cards to the winner of the war
            Queue<String> warCardsP1 = new LinkedList<>();
            Queue<String> warCardsP2 = new LinkedList<>();
            warCardsP1.add(p1Top); //the cards that went to war are added to the queues b/c that is how it was specified to add cards to deck in regards to war
            warCardsP2.add(p2Top);
            war(p1Cards, p2Cards, warCardsP1, warCardsP2); //calls the war method
        }


        Counter.increment();//this is calling a method in the Counter class to count the number of rounds played so far
        return battles(p1Cards, p2Cards);//this method uses recursion to continue battling until a base case is reached
    }
    static void war(PlayerDecks p1Deck, PlayerDecks p2Deck, Queue<String> warCardsP1, Queue<String> warCardsP2) {
        //this method first checks if each deck has enoguh cards for a war, if either decks lacks the necessary number of cards, it will increment the counter and return nothing
        //this is because when a deck doesn't have enough cards for war, it will result in a PAT, which is what this if statement checks
        if (p1Deck.getP1Cards().size() < 4 || p2Deck.getP2Cards().size() < 4) {
            Counter.noCardsincrement();
            return;
        }


        //if the decks have enough cards, it will take the top 3 cards off each players deck and add them to the respective decks collecting war cards
        for (int i = 0; i < 3; i++) {
            if (!p1Deck.p1isEmpty()) {
                warCardsP1.add(p1Deck.removeP1());
            }
            if (!p2Deck.p2isEmpty()){
                warCardsP2.add(p2Deck.removeP2());
            }
        }


        //as the assignment stated, the fourth card determines the winner, so here we compare the values of the cards to find the winner
        if (!p1Deck.p1isEmpty() && !p2Deck.p2isEmpty()) {
            String p1Card = p1Deck.removeP1(); //gets the top card of each players deck, i.e the fourth card
            String p2Card = p2Deck.removeP2();
            warCardsP1.add(p1Card);//adds the fourth card to the war decks
            warCardsP2.add(p2Card);


            int p1Value = CardValue.getValue(p1Card);//gets the value of each card
            int p2Value = CardValue.getValue(p2Card);


            if (p1Value > p2Value) { //if player 1's fourth card has a higher value, they win
                for (String card : warCardsP1){
                    p1Deck.addP1(card); //all the cards from player 1 are added to the deck first
                }
                for (String card : warCardsP2){
                    p1Deck.addP1(card);//then the cards from player 2 are added
                }
            }
            else if (p2Value > p1Value) {//if p2 wins, they get the cards
                for (String card : warCardsP1){
                    p2Deck.addP2(card);//cards from p1
                }
                for (String card : warCardsP2) {
                    p2Deck.addP2(card);//cards from p2
                }
            }
            else {
                //System.out.println("test");
                war(p1Deck, p2Deck, warCardsP1, warCardsP2); //if the fourth cards are the same, it will result in a chained war, so we use recursion here to handle that, by calling the war method again with the war decks that already have the previous wars cards as parameters
            }
        }
    }
}
