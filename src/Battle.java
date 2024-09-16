import java.util.*;
class Battle {// checks to see which card value is greater
    private int value;
    public static int normalBattle(String p1Top, String p2Top){
        int p1Value = CardValue.getValue(p1Top); //saves the value of the first decks top card
        int p2Value = CardValue.getValue(p2Top);//saves the value of the second decks top card


        int winner = p1Value-p2Value;//finds the difference


        if(winner > 0){//if deck 1's card is greater, returns 1
            return 1;
        }
        else if(winner < 0){ //if deck 2's card is greater, returns 2
            return 2;
        }
        else{ //if the value is the same, it returns 0 as it means the cards are the same, i.e war
            return 0;
        }
    }
}
class CardValue {
    private String value;
    public CardValue(String value) { //constructor
        this.value = value;
    }


    public static int getValue(String value) { //gets the value of cards through this method


        char firstChar = value.charAt(0); //saves the first character of the card being checked because the first char indicates the value
        int val;
        switch(firstChar){
            case '2':
                val = 2;
                break;
            case '3':
                val = 3;
                break;
            case '4':
                val = 4;
                break;
            case '5':
                val = 5;
                break;
            case '6':
                val = 6;
                break;
            case '7':
                val = 7;
                break;
            case '8':
                val = 8;
                break;
            case '9':
                val = 9;
                break;
            case '1'://since there is no other cards that begin with '1', we checked '1' for 10 for simplicity
                val = 10;
                break;
            case 'J':
                val = 11;
                break;
            case 'Q':
                val = 12;
                break;
            case 'K':
                val = 13;//returns 13 b/c the num value of a king is 13
                break;
            case 'A'://returns 14 b/c the number value of an Ace is 14
                val = 14;
                break;
            default: val = 0; //defaults to 0 for any cards the user might have mistyped
        }
        return val;
    }
}
class Counter {
    private static int count = 0; //counter to be used for counting rounds played


    private static int noCards = 0; //counter to be used for checking if a player has enough cards to war
    public static void increment() { //this method increases the rounds played by 1 everytime a round finishes
        count++;
    }
    public static void noCardsincrement() {//this is only called if a player runs out of cards mid war
        noCards++;
    }
    public static int getCount() {// a getter method to retrieve the number of rounds played
        return count;
    }
    public static int getnoCards() { //a getter method to check if a player has a ran out of cards in war
        return noCards;
    }
}
class PlayerDecks {
    public Queue<String> p1Cards; //deck for player 1
    public Queue<String> p2Cards;//deck for player 2


    //constructor to initalize the decks
    public PlayerDecks() {
        p1Cards = new LinkedList<>();
        p2Cards = new LinkedList<>();
    }
    public void addP1(String card) { //a method that adds cards to player 1's deck
        p1Cards.add(card);
    }


    public void addP2(String card) {//adds cards to p2's deck
        p2Cards.add(card);
    }


    public Queue<String> getP1Cards() {// retrives player 1's cards
        return p1Cards;
    }
    public Queue<String> getP2Cards() {// retreives player 2's cards
        return p2Cards;
    }
    public String removeP1() { //uses built in remove function for queues to remove a card from p1
        return p1Cards.remove();
    }
    public String removeP2() {//same thing as the above method, but for p2
        return p2Cards.remove();
    }
    public boolean p1isEmpty() {//checks if player 1's deck is empty
        return p1Cards.isEmpty();
    }
    public boolean p2isEmpty() {//checks if p2's deck is empty
        return p2Cards.isEmpty();
    }
}




