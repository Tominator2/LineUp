/*

  This class implements a single piece for the tile "matching" game Line-Up. 

  Use a char array to store the card state.  

  Colours: 'R' 'O' 'Y' 'G' 'B'
  Blank:   ' '

  Use an array to map opposite sides 0, 1, 2, 3 map to 5, 4, 7, 6 resepctively:
 
     0 1
   +-----+
   | x x |
  7|x   x|2
   |     |
  6|x   x|3
   | x x |
   +-----+
     5 4
 
  Rotation using offset:
  0 & 1 -> 2 & 3 -> 4 & 5 -> 6 & 7 = (no. rotations * 2) mod 8
 
  Use ' ',' ' for an empty side (no connections) (was originally '_','_')
  Use ' ' for a blank next to a single side connection

  NB There are 5 pieces with a "repeated" side (although the '=' sign
  is symmetric):
 
  +------+ +-O----+ +----R-+ +-O--Y-+ +-R----+ 
  OOO    | | O    | YY   R | | O  Y | | R    | 
  |      | | O    | | Y  O | | O  O | | R    | 
  |  GGGGG OO     | | Y   OO | Y  O | RR     |
  +-G----+ +------+ +-Y----+ +-Y--O-+ +------+ 

*/

interface CardSides {

    public static final int TOP    = 0; 
    public static final int RIGHT  = 1;
    public static final int BOTTOM = 2; 
    public static final int LEFT   = 3;

    public static final String[] SIDE = {
	"TOP",
	"RIGHT",
	"BOTTOM",
	"LEFT"
    };
}


class Piece implements CardSides{

    // the offset is used so a default card with a default orientation can 
    // be defined.  Rotations are performed by incrementing the offset
    private int offset = 0; 

    private int number = 0;  // ID no. for this piece that will be it's array index

    // map for retrieving the corresponding index on an adjacent piece 
    private final int[] OPPOSITE = {5,4,7,6,1,0,3,2};

    private char[] colours = new char[8];


    //
    // default constructor
    //
    public Piece () {

	for (int i = 0; i <= 7; i++){
	    this.colours[i] = '-';
	}

     }

    
    //
    // Constructor that takes an array of colours (see the comments at
    // the top of this file describing a piece) and a unique no. to
    // help identify this piece.
    //
    public Piece(char[] colours, int no) {

	for (int i = 0; i <= 7; i++){
	    this.colours[i] = colours[i];
	}
	number = no;

    }


    public void setNumber(int no){

	number = no;

    }


    public int getNumber(){

	return number;

    }



    //
    // Set the colour at the ocrresponding match point.
    //
    public void setColPos(char col, int position){

	colours[position] = col;

    }


    //
    // Return the colour at this position based on the original piece
    // definition.
    //
   public char getColPos(int position){

	return colours[position];

   }


    //
    // Return the colour at this position allowing for rotation.
    //
   public char getOffsetColPos(int position){

	return colours[(position + (offset * 2))%8];

   }


    //
    // Rotate the pice 90 degrees in a clockwise direction.
    //
    public void rotate() {

	offset = (offset + 1) % 4;

    }
    

    //
    // Reset the piece to its original orientation.
    //
   public void resetOrientation(){

	offset = 0;

    }


    //
    // Rotate this piece so the "sideFrom" side will be in the
    // "sideTo' position.
    //
    public void rotate(int sideFrom, int sideTo){

	// resetOrientation();
	offset = offset + (sideFrom - sideTo);
	if (offset < 0) {
	    offset = offset + 4;
	}

    }


   //
   // Return the colour opposite this position
   //
   public char opposite(int position){

	return colours[OPPOSITE[(position + (offset * 2))%8]];

    }


    //
    // Convenience function to decode colour at position or
    // blank side character (for printing)
    //
    public char getCol(int position) {

	char result = colours[(position + (offset * 2))%8];

	if (result == '-' || result == ' ') {  // include spaces here as well?
	    switch (position) {
	    case 0:
	    case 1:
	    case 4:
	    case 5:
		result = '-';
		break;
	    case 2:
	    case 3:
	    case 6:
	    case 7:
		result = '|';
		break;
	    }
	}
	return result;

    }


    //
    // Returns a string to display the corresponding row for this piece.
    //
    // Note that the colours break into the borders which helps
    // readability with corresponding sides.
    //
    public String getRow(int row){

	String str = "";

	String no = "" + number;
	
	if (number < 10) {
	    no = " " + no;
	} 

	switch (row) {
	case 0: 
	    str = "+" + getCol(0) + "----" + getCol(1) + "+";
	    break;
	case 1: 
	    str = getCol(7) + "      " + getCol(2);
	    break;
	case 2: 
	    str = "|  " + no + "  |";
	    break;
	case 3: 
	    str = getCol(6) + "      " + getCol(3);
	    break;
	case 4: 
	    str = "+" + getCol(5) + "----" + getCol(4) + "+";
	    break;
	}
	return str;

    }


    //
    // Wider version of the getRow function that returns rows with an
    // unbroken/complete border.
    //
    public String getRow2(int row){

	String str = "";

	String no = "" + number;
	
	if (number < 10) {
	    no = " " + no;
	} 

	switch (row) {
	case 0: 
	    str = "+---------+";
	    break;
	case 1: 
	    str = "| " + getCol(0) + "     " + getCol(1) + " |";
	    break;
	case 2: 
	    str = "|" + getCol(7) + "       " + getCol(2) + "|";
	    break;
	case 3: 
	    str = "|   " + no + "    |";
	    break;
	case 4: 
	    str = "|" + getCol(6) + "       " + getCol(3) + "|";
	    break;
	case 5: 
	    str = "| " + getCol(5) + "     " + getCol(4) + " |";
	    break;
	case 6: 
	    str = "+---------+";
	    break;
	}
	return str;

    }


    //
    //  Return a String representing the HTML markup for the
    //  corresponding row in this piece.
    //
    public String getRowHTML(int row){

	String str = "";

	String no = "" + number;
	
	if (number < 10) {
	    no = "&nbsp;" + no;
	} 

	switch (row) {
	case 0: 
	    str = "<span class=\"w\">+</span>" + getColHTML(0) + 
		"<span class=\"w\">----</span>" + 
		getColHTML(1) + "<span class=\"w\">+</span>";
	    break;
	case 1: 
	    str = getColHTML(7) + 
		"<span class=\"w\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>" + 
		getColHTML(2);
	    break;
	case 2: 
	    str = "<span class=\"w\">|&nbsp;&nbsp;" + no + "&nbsp;&nbsp;|</span>";
	    break;
	case 3: 
	    str = getColHTML(6) + 
		"<span class=\"w\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>" + 
		getColHTML(3);
	    break;
	case 4: 
	    str = "<span class=\"w\">+</span>" + getColHTML(5) + 
		"<span class=\"w\">----</span>" +
		getColHTML(4) + "<span class=\"w\">+</span>";
	    break;
	}
	return str;

    }


    //
    // Convenience function to decode colour at position or
    // blank side character (for printing)
    //
    public String getColHTML(int position) {

	String result = "";

	char col = colours[(position + (offset * 2))%8];

	if (col == '-' || col == ' ') {  // include spaces here as well?
	    switch (position) {
	    case 0:
	    case 1:
	    case 4:
	    case 5:
		result = "<span class=\"w\">-</span>";
		break;
	    case 2:
	    case 3:
	    case 6:
	    case 7:
		result = "<span class=\"w\">|</span>";
		break;
	    }
	}
	else {
	    result = "<span class=\"" + col + "\">&nbsp;</span>"; 
	}

	return result;

    }


    //
    //  Returns a string with a simple represention of this piece
    //
    public String toString(){

	String str = "";
	for (int i = 0; i < 5; i++){ // < 7;
	    str = str + getRow(i) + "\n";
	}
	return str;

    }



    //
    //  Tests
    //
    public static void main(String args[]) {

	Piece p1 = new Piece(new char[] {'-','-',' ','O','B','Y','G','R'}, 0);
	System.out.println(p1);

	Piece p2 = new Piece(new char[] {' ','R','R','G','B','Y','O','O'}, 10);
	System.out.println(p2);

	for (int j = 0; j <= 4; j++){
	    p1.rotate();
	    System.out.println("rotate ->");
	    System.out.println(p1);
	    for (int i = 0; i <= 7; i++){
		System.out.println("Opposite " + i + " is " + p1.opposite(i));
	    }
	}

	System.out.println(p1);

	System.out.println("rotate TOP -> BOTTOM");
	p1.rotate(p1.TOP,p1.BOTTOM);
	System.out.println(p1);

	System.out.println("rotate TOP -> RIGHT");
	p1.rotate(p1.TOP,p1.RIGHT);
	System.out.println(p1);

	System.out.println("rotate TOP -> LEFT");
	p1.rotate(p1.TOP,p1.LEFT);
	System.out.println(p1);

	Piece p3 = new Piece();
	p3.setColPos('R', 1);
	System.out.println(p3);

    }

}
