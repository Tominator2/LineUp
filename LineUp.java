import java.util.Vector;
import java.util.Iterator;
import java.util.Random;

/*

  This program attempts to find layouts for the coloured-card matching
  game 'Line Up' that use all 90 cards.

  To Do:
  ------
  - Add a '-h' or '-html' command line switch for html output mode?
  - Could use existing map drawing methods - add " if (!printHTML) return; " 
    to HTML printers and equivalent for TXT printers
  - Could also add a debug method to wrap all System.out.print/println fns 
    with a level 

*/

public class LineUp {

    private Piece[] pieces = new Piece[90];

    // Pieces are placed into a 90 x 90 X-Y grid layout with the first
    // piece always being placed at position (45,45).  It would be
    // possible for an unusual layout to exceed these bounds!
    private Piece[][]  layout   = new Piece[90][90]; // 180 x 180?

    private Vector available = new Vector(90);

    private boolean outputHTML = false;

    // Setting this greater than 0 provides more verbose information while running.
    private int debugLevel  = 0;  

    private int piecesTried = 0;

    private int mapsFound   = 0;


    //
    //  Constructor.
    //
    public LineUp(){

	createPieces();
	clearLayout();
	resetAvailablePieces();

    }


    //
    //  Define the 90 pieces.  
    //
    public void createPieces() {

	int count = 0; 

	// The comments for each piece are as follows:

	// The photograph containing this piece:
	//
	// Image: X.jpg 

	// A rough visual description to help distinguish this piece:
	//
	// Type:  X 

	// The position of this piece in the photograph (labelled, left 
	// to right, top to bottom):
	//
	// Position: X (1..12) 

	// Create a new piece by defining the colours for the two
	// matching points on each side of the piece as you proceed in
	// a clockwise direction starting from the top left corner.
	// See 'Piece.java' for more information:
	//
	// pieces[X] = new Piece(new char[] {'','','','','','','',''});


	// Image: a.jpg
	// Type:  A 'j + r'
	// Position: 1
	pieces[0] = new Piece(new char[] {'B','Y',' ','Y',' ',' ',' ','B'}, count++);

	// Image: a.jpg
	// Type:  B 'double rainbow'
	// Position: 2
	pieces[1] = new Piece(new char[] {' ',' ','R','Y','Y','O',' ',' '}, count++);

	// Image: a.jpg
	// Type:  C '| + C'
	// Position: 3
	pieces[2] = new Piece(new char[] {'R',' ','Y','G',' ','R',' ',' '}, count++);

	// Image: a.jpg
	// Type:  D '='
	// Position: 4
	pieces[3] = new Piece(new char[] {' ',' ','O','Y',' ',' ','Y','R'}, count++);

	// Image: a.jpg
	// Type:  E '|'
	// Position: 5
	pieces[4] = new Piece(new char[] {' ',' ',' ','G',' ',' ','Y',' '}, count++);

	// Image: a.jpg
	// Type:  F '| + r'
	// Position: 6
	pieces[5] = new Piece(new char[] {'O',' ',' ','G',' ',' ','Y','O'}, count++);

	// Image: a.jpg
	// Type:  G 'single large rainbow'
	// Position: 7
	pieces[6] = new Piece (new char[] {'G',' ',' ','Y',' ',' ',' ',' '}, count++);

	// Image: a.jpg
	// Type:  F '| + r'
	// Position: 8
	pieces[7] = new Piece(new char[] {' ',' ','G',' ',' ','O','O','Y'}, count++);

	// Image: a.jpg
	// Type:  C '| + C'
	// Position: 9
	pieces[8] = new Piece(new char[] {' ','R',' ',' ','O',' ','B','G'}, count++);

	// Image: a.jpg
	// Type:  H 'single stop'
	// Position: 10
	pieces[9] = new Piece(new char[] {'G',' ',' ',' ',' ',' ',' ',' '}, count++);

	// Image: a.jpg
	// Type:  F '| + r'
	// Position: 11
	pieces[10] = new Piece(new char[] {'Y',' ',' ','G',' ',' ','G','Y'}, count++);

	// Image: a.jpg
	// Type:  F '| + r'
	// Position: 12
	pieces[11] = new Piece(new char[] {'O','R','R',' ',' ','Y',' ',' '}, count++);

	// Next highest type: I

	// Image: b.jpg
	// Type:  B 'double rainbow'
	// Position: 1
	pieces[12] = new Piece(new char[] {' ',' ','G','Y','Y','B',' ',' '}, count++);

	// Image: b.jpg
	// Type:  F '| + r'
	// Position: 2
	pieces[13] = new Piece(new char[] {' ','B',' ',' ','B','R','R',' '}, count++);

	// Image: b.jpg
	// Type:  A 'j + r'
	// Position: 3
	pieces[14] = new Piece(new char[] {'B',' ','G','Y','Y',' ',' ',' '}, count++);

	// Image: b.jpg
	// Type:  I 'j + |'
	// Position: 4
	pieces[15] = new Piece(new char[] {' ','R',' ','R',' ',' ','R','O'}, count++);

	// Image: b.jpg
	// Type:  G 'single large rainbow'
	// Position: 5
	pieces[16] = new Piece(new char[] {'R',' ',' ','O',' ',' ',' ',' '}, count++);

	// Image: b.jpg
	// Type:  I 'j + |'
	// Position: 6
	pieces[17] = new Piece(new char[] {'G','O',' ',' ','R',' ','G',' '}, count++);

	// Image: b.jpg
	// Type:  B 'double rainbow'
	// Position: 7
	pieces[18] = new Piece (new char[] {' ',' ','G','O','O','G',' ',' '}, count++);

	// Image: b.jpg
	// Type:  J 'C'
	// Position: 8
	pieces[19] = new Piece(new char[] {' ',' ',' ',' ',' ',' ','Y','G'}, count++);

	// Image: b.jpg
	// Type:  B 'double rainbow'
	// Position: 9
	pieces[20] = new Piece(new char[] {'B','R','R','B',' ',' ',' ',' '}, count++);

	// Image: b.jpg
	// Type:  E '|'
	// Position: 10
	pieces[21] = new Piece(new char[] {' ',' ',' ','O',' ',' ','Y',' '}, count++);

	// Image: b.jpg
	// Type:  B 'double rainbow'
	// Position: 11
	pieces[22] = new Piece(new char[] {'G','O','O','Y',' ',' ',' ',' '}, count++);

	// Image: b.jpg
	// Type:  D '='
	// Position: 12
	pieces[23] = new Piece(new char[] {' ',' ','O','B',' ',' ','B','R'}, count++);

	// Next highest type: K

	// Image: c.jpg
	// Type:  D '='
	// Position: 1
	pieces[24] = new Piece(new char[] {' ',' ','Y','G',' ',' ','G','O'}, count++);

	// Image: c.jpg
	// Type:  K 'U'
	// Position: 2
	pieces[25] = new Piece(new char[] {' ',' ','R','Y',' ',' ',' ',' '}, count++);

	// Image: c.jpg
	// Type:  E '|'
	// Position: 3
	pieces[26] = new Piece(new char[] {' ','G',' ',' ','B',' ',' ',' '}, count++);

	// Image: c.jpg
	// Type:  L 'stop + |'
	// Position: 4
	pieces[27] = new Piece(new char[] {' ','O',' ',' ','Y','G',' ',' '}, count++);

	// Image: c.jpg
	// Type:  M 'r + stop'
	// Position: 5
	pieces[28] = new Piece(new char[] {' ',' ',' ',' ','O','G','G',' '}, count++);

	// Image: c.jpg
	// Type:  K 'U'
	// Position: 6
	pieces[29] = new Piece(new char[] {'Y','G',' ',' ',' ',' ',' ',' '}, count++);

	// Image: c.jpg
	// Type:  L 'stop + |'
	// Position: 7
	pieces[30] = new Piece (new char[] {' ',' ',' ','B',' ',' ','B','R'}, count++);

	// Image: c.jpg
	// Type:  F '| + r'
	// Position: 8
	pieces[31] = new Piece(new char[] {' ',' ','Y','G','G',' ',' ','G'}, count++);

	// Image: c.jpg
	// Type:  C '| + C'
	// Position: 9
	pieces[32] = new Piece(new char[] {' ','R',' ',' ','O',' ','G','Y'}, count++);

	// Image: c.jpg
	// Type:  M 'j'
	// Position: 10
	pieces[33] = new Piece(new char[] {' ',' ','B',' ','G',' ',' ',' '}, count++);

	// Image: c.jpg
	// Type:  N 'double stop'
	// Position: 11                                     ? - NOT SURE!!
	pieces[34] = new Piece(new char[] {' ',' ',' ',' ','O','G',' ',' '}, count++);

	// Image: c.jpg
	// Type:  N 'stop + j'
	// Position: 12
	pieces[35] = new Piece(new char[] {' ',' ',' ','G',' ','G',' ','O'}, count++);

	// Next highest type: O

	// Image: d.jpg
	// Type:  ? '='
	// Position: 1
	pieces[36] = new Piece(new char[] {'O','G',' ',' ','B','O',' ',' '}, count++);

	// Image: d.jpg
	// Type:  ? '| + r'
	// Position: 2
	pieces[37] = new Piece(new char[] {'Y',' ',' ','G','G','O',' ',' '}, count++);

	// Image: d.jpg
	// Type:  ? ''
	// Position: 3
	pieces[38] = new Piece(new char[] {'R','B',' ',' ','G','Y','Y','R'}, count++);

	// Image: d.jpg
	// Type:  ? ''
	// Position: 4
	pieces[39] = new Piece(new char[] {' ','G',' ',' ','Y','O',' ',' '}, count++);

	// Image: d.jpg
	// Type:  ? ''
	// Position: 5
	pieces[40] = new Piece(new char[] {'O',' ',' ',' ',' ',' ','O',' '}, count++);

	// Image: d.jpg
	// Type:  ? ''
	// Position: 6
	pieces[41] = new Piece(new char[] {'Y',' ','Y','G',' ',' ','G',' '}, count++);

	// Image: d.jpg
	// Type:  ? ''
	// Position: 7
	pieces[42] = new Piece(new char[] {'Y',' ',' ',' ','Y',' ','G','Y'}, count++);

	// Image: d.jpg
	// Type:  ? '|'
	// Position: 8
	pieces[43] = new Piece(new char[] {' ',' ',' ','O',' ',' ','O',' '}, count++);

	// Image: d.jpg
	// Type:  ? ''
	// Position: 9
	pieces[44] = new Piece(new char[] {' ',' ',' ','B','B','O',' ','R'}, count++);

	// Image: d.jpg
	// Type:  ? ''
	// Position: 10
	pieces[45] = new Piece(new char[] {' ','R',' ','O',' ','Y',' ','Y'}, count++);

	// Image: d.jpg
	// Type:  ? '|'
	// Position: 11
	pieces[46] = new Piece(new char[] {' ',' ',' ','B',' ',' ','B',' '}, count++);

	// Image: d.jpg
	// Type:  ? ''
	// Position: 12
	pieces[47] = new Piece(new char[] {'B',' ',' ',' ',' ',' ','G',' '}, count++);

	// Next Highest Type: ?

	// Image: e.jpg
	// Type:  ? ''
	// Position: 1
	pieces[48] = new Piece(new char[] {'Y','B','B',' ',' ','O',' ',' '}, count++);

	// Image: e.jpg
	// Type:  ? ''
	// Position: 2
	pieces[49] = new Piece(new char[] {'Y','O',' ','O',' ','G','G','Y'}, count++);

	// Image: e.jpg
	// Type:  ? '='
	// Position: 3
	pieces[50] = new Piece(new char[] {'Y','G',' ',' ','G','Y',' ',' '}, count++);

	// Image: e.jpg
	// Type:  ? 'U'
	// Position: 4
	pieces[51] = new Piece(new char[] {' ',' ',' ',' ','O','Y',' ',' '}, count++);

	// Image: e.jpg
	// Type:  ? ''
	// Position: 5
	pieces[52] = new Piece(new char[] {' ','Y',' ',' ',' ',' ','G',' '}, count++);

	// Image: e.jpg
	// Type:  ? ''
	// Position: 6
	pieces[53] = new Piece(new char[] {' ',' ',' ','O','O',' ','Y','G'}, count++);

	// Image: e.jpg
	// Type:  ? '|'
	// Position: 7
	pieces[54] = new Piece(new char[] {' ',' ',' ','G',' ',' ','G',' '}, count++);

	// Image: e.jpg
	// Type:  ? ''
	// Position: 8
	pieces[55] = new Piece(new char[] {'G','Y',' ',' ','O',' ',' ','G'}, count++);

	// Image: e.jpg
	// Type:  ? '|'
	// Position: 9
	pieces[56] = new Piece(new char[] {' ',' ',' ','R',' ',' ','R',' '}, count++);

	// Image: e.jpg
	// Type:  ? ''
	// Position: 10
	pieces[57] = new Piece(new char[] {' ',' ','O','G',' ','Y',' ','R'}, count++);

	// Image: e.jpg
	// Type:  ? ''
	// Position: 11
	pieces[58] = new Piece(new char[] {' ','B',' ',' ','B','Y','Y',' '}, count++);

	// Image: e.jpg
	// Type:  ? ''
	// Position: 12
	pieces[59] = new Piece(new char[] {'Y','G','G','B','B',' ','O',' '}, count++);

	// Next highest type: O

	// Image: f.jpg
	// Type:  ? ''
	// Position: 1
	pieces[60] = new Piece(new char[] {' ',' ',' ','R','R',' ',' ',' '}, count++);

	// Image: f.jpg
	// Type:  ? '='
	// Position: 2
	pieces[61] = new Piece(new char[] {'R','G',' ',' ','Y','O',' ',' '}, count++);

	// Image: f.jpg
	// Type:  ? 'U'
	// Position: 3
	pieces[62] = new Piece(new char[] {' ',' ',' ',' ','Y','O',' ',' '}, count++);

	// Image: f.jpg
	// Type:  ? ''
	// Position: 4
	pieces[63] = new Piece(new char[] {'O','B',' ','G',' ','R',' ',' '}, count++);

	// Image: f.jpg
	// Type:  ? ''
	// Position: 5
	pieces[64] = new Piece(new char[] {'O','Y',' ',' ','G',' ','Y',' '}, count++);

	// Image: f.jpg
	// Type:  ? ''
	// Position: 6
	pieces[65] = new Piece(new char[] {'Y',' ',' ','Y',' ',' ',' ',' '}, count++);

	// Image: f.jpg
	// Type:  ? ''
	// Position: 7
	pieces[66] = new Piece(new char[] {' ',' ','G','R','R','B',' ',' '}, count++);

	// Image: f.jpg
	// Type:  ? ''
	// Position: 8
	pieces[67] = new Piece(new char[] {'G','B',' ',' ',' ',' ','B','G'}, count++);

	// Image: f.jpg
	// Type:  ? ''
	// Position: 9
	pieces[68] = new Piece(new char[] {' ','Y','Y','R',' ',' ','R',' '}, count++);

	// Image: f.jpg
	// Type:  ? ''
	// Position: 10
	pieces[69] = new Piece(new char[] {' ','R','R',' ',' ','O','O',' '}, count++);

	// Image: f.jpg
	// Type:  ? ''
	// Position: 11
	pieces[70] = new Piece(new char[] {'R','Y',' ',' ','O',' ','R',' '}, count++);

	// Image: f.jpg
	// Type:  ? ''
	// Position: 12
	pieces[71] = new Piece(new char[] {' ',' ',' ','O',' ',' ','O','Y'}, count++);

	// Next highest type: O

	// Image: g.jpg
	// Type:  ? ''
	// Position: 1
	pieces[72] = new Piece(new char[] {' ',' ',' ','Y',' ',' ','Y','B'}, count++);

	// Image: g.jpg
	// Type:  ? ''
	// Position: 2
	pieces[73] = new Piece(new char[] {' ',' ','G',' ',' ','B',' ',' '}, count++);

	// Image: g.jpg
	// Type:  ? ''
	// Position: 3
	pieces[74] = new Piece(new char[] {'B',' ',' ','Y','Y',' ',' ','B'}, count++);

	// Image: g.jpg
	// Type:  ? ''
	// Position: 4
	pieces[75] = new Piece(new char[] {'G',' ','R',' ','R','B',' ',' '}, count++);

	// Image: g.jpg
	// Type:  ? 'C'
	// Position: 5
	pieces[76] = new Piece(new char[] {'Y','O',' ',' ',' ',' ',' ',' '}, count++);

	// Image: g.jpg
	// Type:  ? ''
	// Position: 6
	pieces[77] = new Piece(new char[] {'O','Y',' ',' ','O','Y',' ',' '}, count++);

	// Image: g.jpg
	// Type:  ? ''
	// Position: 7
	pieces[78] = new Piece(new char[] {'O','B',' ',' ','B','Y',' ',' '}, count++);

	// Image: g.jpg
	// Type:  ? ''
	// Position: 8
	pieces[79] = new Piece(new char[] {' ',' ','G','Y','Y','B','B','G'}, count++);

	// Image: g.jpg
	// Type:  ? ''
	// Position: 9
	pieces[80] = new Piece(new char[] {' ',' ',' ','R','R','B',' ',' '}, count++);

	// Image: g.jpg
	// Type:  ? ''
	// Position: 10
	pieces[81] = new Piece(new char[] {' ',' ',' ',' ','B',' ','B','O'}, count++);

	// Image: g.jpg
	// Type:  ? ''
	// Position: 11
	pieces[82] = new Piece(new char[] {'B','Y',' ','G',' ','B',' ',' '}, count++);

	// Image: g.jpg
	// Type:  ? ''
	// Position: 12
	pieces[83] = new Piece(new char[] {'G','B','B','Y','Y','B',' ',' '}, count++);

	// Next highest type: O

	// Image: h.jpg
	// Type:  ? ''
	// Position: 1
	pieces[84] = new Piece(new char[] {' ',' ',' ',' ',' ',' ','B','R'}, count++);

	// Image: h.jpg
	// Type:  ? ''
	// Position: 2
	pieces[85] = new Piece(new char[] {' ',' ','O',' ',' ','Y',' ',' '}, count++);

	// Image: h.jpg
	// Type:  ? ''
	// Position: 3
	pieces[86] = new Piece(new char[] {'Y','O','O',' ',' ','O',' ',' '}, count++);

	// Image: h.jpg
	// Type:  ? ''
	// Position: 4
	pieces[87] = new Piece(new char[] {'R',' ',' ',' ',' ',' ','R',' '}, count++);

	// Image: h.jpg
	// Type:  ? '='
	// Position: 5
	pieces[88] = new Piece(new char[] {'R','B',' ',' ','B','R',' ',' '}, count++);

	// Image: h.jpg
	// Type:  ? ''
	// Position: 6
	pieces[89] = new Piece(new char[] {'B','G',' ',' ','G','B',' ',' '}, count++);

    }

    
    //
    // Add all of the pieces back onto the available pieces. 
    //
    public void resetAvailablePieces(){

	available.clear();
	for (int i = 0; i < pieces.length; i++) {
	    available.add(pieces[i]);
	}

    } 


    //
    //  (Re-)Initialise the layout grid.
    //
    public void clearLayout() {

	for (int i = 0; i < 90; i++){
	    for (int j = 0; j < 90; j++){
		layout[i][j] = null;
	    }
	}	

    }


    //
    // Print debug messages if the level is greater than 0.
    //
    public void printDebug(String str){

	if (debugLevel > 0) {
	    System.out.print(str);
	}

    }


    //
    // Print debug messages with a newline if the level is greater
    // than 0.
    //
    public void printDebugln(String str){

	if (debugLevel > 0) {
	    System.out.println(str);
	}

    }


    //
    //
    //
    public void setHTMLoutput(boolean isHTML){
	
	outputHTML = isHTML;

    }


    //
    // Print a small representation of the entire layout where pieces
    // are inidicated by an 'X'.  This gives a quick overview of the
    // maps shape.
    //
    // This could use an offset like the larger map although it may be
    // interesting to see how the resulting layout is positioned in
    // the overall grid (which is currently only 90x90 characters
    // anyway).
    //
    public void printMiniMap() {
	
	System.out.println("Mini Map:");
	for (int i = 0; i < 90; i++){
	    System.out.print("-");
	}
	System.out.print("\n");

	for (int i = 0; i < 90; i++){
	    for (int j = 0; j < 90; j++){
		if (layout[j][i] == null) {
		    System.out.print(" ");
		}
		else {
		    System.out.print("X");
		}
	    }
	    System.out.print("\n");
	}
	for (int i = 0; i < 90; i++){
	    System.out.print("-");
	}
	System.out.print("\n");

    }

  
    //
    //  This is a convenience method used for printing a piece that
    //  returns either the top/bottom bar or a blank row.
    //
    public String getEmptyRow(int row) {

	if (row == 0 || row == 4) {
	    return "+------";
	}
	else {
	    return "|      ";
	}

    }


    //
    // Find the row containing the top-most piece
    //
    public int topIndex(){

	for (int i = 0; i < 90; i++){
	    for (int j = 0; j < 90; j++){
		if (layout[j][i] != null){
		    return i;
		}
	    }
	}
	return -1;

    }


    //
    // Find the column containing the left-most piece for this layout
    //
    public int leftIndex(int topIndex) {

	int leftIndex = 90;

	for (int i = topIndex; i < 90; i++){
	    for (int j = 0; j < leftIndex; j++){
		if (layout[j][i] != null){
		    leftIndex = j;
		}
	    }
	}
	return leftIndex;

    }


    //
    // Find the column of the right-most piece for this layout.  
    //
    // Finding the rightmost and lowest pieces could be useful for
    // determing how "big" a layout is if we are looking for the most
    // compact layout.
    //
    public int rightIndex(int top) {

	int rightIndex = 0;

	for (int i = top; i < 90; i++){
	    for (int j = rightIndex; j < 90; j++){
		if (layout[j][i] != null){
		    if (j > rightIndex){
			rightIndex = j;
		    }
		}
	    }
	}

	return rightIndex;

    }


    //
    // Find the row of the lowest piece
    //
    public int bottomIndex(int topIndex){

	int bottom = topIndex;

	for (int i = topIndex; i < 90; i++){
	    for (int j = 0; j < 90; j++){
		if (layout[j][i] != null){
		    bottom = i;
		}
	    }
	}

	return bottom;

    }


    //
    // Print a map representing the layout of the pieces to standard output
    //
    public void printMap() {

	if (outputHTML){
	    return;
	}

	// find index of topmost piece
	int top = topIndex();

	printMap(top, leftIndex(top), bottomIndex(top), rightIndex(top));

    }


    //
    // This version of printMap takes the position of the topmost and
    // leftmost pieces and aligns the map to the top left position to 
    // minimise wasted space in the layout.
    //
    public void printMap(int top, int left, int bottom, int right) {

	System.out.println("Map: (offset " + left + "," + top + ") " + 
			   piecesTried + " pieces tried.");
	for (int i = top; i <= bottom; i++){
	    for (int j = 0; j < 5; j++){
		for (int k = left; k <= right; k++){
		    if (layout[k][i] == null) {
			System.out.print(getEmptyRow(j));
			//printDebug(getEmptyRow(j));
			System.out.print("        ");
		    }
		    else {
			System.out.print(layout[k][i].getRow(j));
		    }
		}
		System.out.print("\n");
	    }
	}

    }



    //
    //  Output the opening HTML markup for colour layout map(s)
    //
    public void printHTMLHeader() {

	System.out.print("<html>\n" +
                         "<head>\n" + 
			 "<style>\n" +
			 ".r { background-color: red; }\n" + 
                         ".y { background-color: yellow; }\n" +
                         ".g { background-color: green; }\n" + 
                         ".b { background-color: blue; }\n" +  
                         ".o { background-color: orange; }\n" + 
			 ".w { background-color: white; }\n" +
			 ".blank{ background-color: black; }\n" +
			 ".code {font: 100% courier,monospace; overflow: auto; " + 
			 "background-color: black;}\n" +
			 "</style>\n" +
			 "</head>\n" +
			 "<body>\n" +
			 "<div class=\"code\">\n");
	//		 ".code {font: 100% courier,monospace; overflow: auto; " + 
	//		 "overflow-x: auto; overflow-x: auto; background-color: black;}\n" +

    }


    //
    //  Output the closing HTML  markup for colour layout map(s)
    //
    public void printHTMLFooter() {

	System.out.print("<br></div>" +
			 "</body>" +
			 "</html>");

    }


    //
    //  Output the HTML markup for a colour map and remove any emtpy
    //  rows or columns.
    //
    public void printHTMLMap() {

	// find index of topmost piece
	int top  = topIndex();

	printHTMLMap(top, leftIndex(top), bottomIndex(top), rightIndex(top));

    }


    //
    //  Output the HTML markup for a map wth the specified dimensions
    //
    public void printHTMLMap(int top, int left, int bottom, int right) {

	System.out.println("<p class=\"w\">Map with offset (" + left + "," 
			   + top + ")</p><br>");
	for (int i = top; i <= bottom; i++){
	    for (int j = 0; j < 5; j++){
		System.out.print("&nbsp;"); // add a leading space
		for (int k = left; k <= right; k++){
		    if (layout[k][i] == null) {
			System.out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		    }
		    else {
			System.out.print(layout[k][i].getRowHTML(j));
		    }
		}
		System.out.print("<br>\n");
	    }
	}

    }


    //
    //  Try to fit the given piece at the specified position.
    //
    public void tryPiece(Piece piece, int posX, int posY) {

	if (layout[posX][posY] != null) {
	    //System.out.println("There is already a piece at " + posX + "," + 
	    //			       posY + "! *************");
	    return;
	}

	// does it fit?
	if (!alignPiece(piece, posX, posY)){
	    System.out.println("This piece does not fit at " + posX + "," + 
	    	       posY + ":");
	    return;
	//System.out.println(piece);
	    // break|exit|return here?
	}

	// add this piece at this location
	layout[posX][posY] = piece;
	printDebugln("Adding piece at " + posX + "," + posY);
	//printMiniMap();

	// remove it from the available list
	available.removeElement(piece);

	// check for each of the sides
	if (layout[posX][posY - 1] == null) {
	    if (!((piece.getOffsetColPos(0) == ' ') && 
		  (piece.getOffsetColPos(1) == ' '))) {
		tryMatches(posX, posY - 1);
	    }
	}
	if (layout[posX + 1][posY] == null) {
	    if (!((piece.getOffsetColPos(2) == ' ') && 
		  (piece.getOffsetColPos(3) == ' '))) {
		tryMatches(posX + 1, posY);
	    }
	}
	if (layout[posX][posY + 1] == null) {
	    if (!((piece.getOffsetColPos(4) == ' ') && 
		  (piece.getOffsetColPos(5) == ' '))){
		tryMatches(posX, posY + 1);
	    }
	}
	if (layout[posX - 1][posY] == null) {
	    if (!((piece.getOffsetColPos(6) == ' ') &&
		  (piece.getOffsetColPos(7) == ' '))) {
		tryMatches(posX - 1, posY);
	    }
	}

	// Note that there are 5 pieces drawn in the comments at the
	// start of Pieces.java which have more than one matching side
	// and therefore more than one orientation (although one is a
	// mirror so only 4 may effect the possible layout outcomes

    }


    //
    //  Look for a possible match for this position.
    //
    public void tryMatches(int posX, int posY){

	// check the list of possible pieces for matches
	Vector matches = getMatches(posX, posY);
	//System.out.println("Trying " + matches.size() + " match(es) for " + posX +
	//		   "," + posY);

	Iterator possiblePieces = matches.iterator();
	Piece currentPiece;
	while(possiblePieces.hasNext()) {
	    currentPiece = (Piece) possiblePieces.next();
	    tryPiece(currentPiece, posX, posY);
	    // add the piece back again here? (or in tryPiece ?)
	    //available.addElement(currentPiece);
	}

    }


    //
    // Return a shuffled vector of possible matching pieces for this position.
    //
    public Vector getMatches(int posX, int posY) {

	Vector possiblePieces = new Vector();

	// create a match array for the surrounding non-empty pieces
	char[] match = getMatchArray(posX, posY);
	
	// check the list of available pieces for possible matches
	Iterator availablePieces = available.iterator();
	Piece currentPiece;
	while(availablePieces.hasNext()) {

	    currentPiece = (Piece) availablePieces.next();
	    
	    // test this piece

	    // print for debugging
	    /*
	    System.out.println("Testing piece:\n" + currentPiece);
	    System.out.print("[");
	    for (int i = 0; i < 4; i++) {
		System.out.print(currentPiece.getColPos(i * 2) + "" +  
				 currentPiece.getColPos((i * 2) + 1));
	    }
	    System.out.println("] <- current piece");
	    for (int j = 0; j < 8; j = j + 2) { 
		System.out.print("[");
		for (int i = 0; i < 4; i++) {	    
		    System.out.print(match[(j + (i * 2)) % 8] + "" 
		    + match[((j + (i * 2)) % 8) + 1]);
		}
		System.out.println("] j=" + j);
	    }
	    */

	    Boolean matched = true;
	    outer:
	    for (int j = 0; j < 8; j = j + 2) { 
		for (int i = 0; i < 4; i++) {
		    if (match[(j + (i * 2)) % 8] != '-') { // only check non-blank sides!
			// check any non-blank sides
			if ((currentPiece.getColPos(i * 2) != match[(j + (i * 2)) % 8]) ||
			    (currentPiece.getColPos((i * 2) + 1) != match[((j + (i * 2)) % 8) + 1])){
			    matched = false; 
			    //System.out.println("no match on   j=" + j + ", i=" + i);
			    break; // no need to keep testing
			}
			else {
			    //System.out.println("matched on    j=" + j + ", i=" + i);
			}
		    }
		    else {
			//System.out.println("blank side on j=" + j + ", i=" + i);
		    }
		    
		}
		
		if (matched) {
		    //System.out.print("Possible piece for [" + posX + "][" + posY + 
		    //		     "] is\n" + currentPiece);
		    possiblePieces.add(currentPiece);
		    break outer; 
		}
		else {
		    matched = true; // reset the flag and rotate
		}    
	    }
	    
	}
	
	// shuffle pieces
	Vector shuffledPieces = new Vector(possiblePieces.size());
	Random rnd = new Random();

	//System.out.println("Possible: " + possiblePieces.toString());

	while (possiblePieces.size() > 0) {
	    shuffledPieces.add((Piece) possiblePieces.remove(rnd.nextInt(possiblePieces.size())));
	} 

	//System.out.println("Shuffled: " + shuffledPieces.toString());

	//return possiblePieces;

	return shuffledPieces;
    }


    //
    //  Check the sides of surrounding non-empty pieces to use when we
    //  look or possble matching pieces.
    //
    public char[] getMatchArray(int posX, int posY) {

	// create a match array from surrounding non-empty pieces
	char[] match = new char[] {'-','-','-','-','-','-','-','-'};
	
	// check piece above
	if (layout[posX][posY - 1] != null) {
	    match[0] = layout[posX][posY - 1].opposite(0); 
	    match[1] = layout[posX][posY - 1].opposite(1);
	}  

	// check piece right
	if (layout[posX + 1][posY] != null) {
	    match[2] = layout[posX + 1][posY].opposite(2); 
	    match[3] = layout[posX + 1][posY].opposite(3);
	}  

 	// check piece below
	if (layout[posX][posY + 1] != null) {
	    match[4] = layout[posX][posY + 1].opposite(4); 
	    match[5] = layout[posX][posY + 1].opposite(5);
	}  

 	// check piece left
	if (layout[posX - 1][posY] != null) {
	    match[6] = layout[posX - 1][posY].opposite(6); 
	    match[7] = layout[posX - 1][posY].opposite(7);
	}  

	//System.out.print("Match array for [" + posX + "][" + posY + "] = |");
	//	for (int i = 0; i < match.length; i++) {
	//  System.out.print(match[i]);
	//}
	//System.out.print("|\n");

	return match;

    }


    //
    // This method rotates the piece to match so that it fits with any
    // already existing pieces surrounding it.  It returns false if
    // this is not possible.
    //
    public Boolean alignPiece(Piece piece, int posX, int posY) {

	//System.out.println("Rotating piece in position " + posX + ", " + posY + " :");
	//System.out.println(piece);

	char[] match = getMatchArray(posX, posY);

	Boolean matched = true;
	outer:
	for (int i = 0; i < 4; i++) { 
	    for (int j = 0; j < 4; j++) { 
		if (match[j * 2] != '-') { // only check non-blank sides!
		    // check any non-blank sides
		    if ((piece.getOffsetColPos(j * 2) != match[j * 2]) || 
			(piece.getOffsetColPos((j * 2) + 1) != match[(j * 2) + 1])){
			matched = false; 
			//System.out.println("no match on   j=" + j + ", i=" + i);
			break; // no need to keep testing
		    }
		    else {
			//System.out.println("matched on    j=" + j + ", i=" + i);
		    }
		}
		else {
		    //System.out.println("blank side on j=" + j + ", i=" + i);
		}
	    }
	    if (matched) {
		//System.out.println("Piece aligned in position " + posX + 
		//		   ", " + posY + " :");
		printDebug(piece.toString());
		return true;
	    }
	    else {
		matched = true; // reset the flag 
		piece.rotate(); // rotate the piece and try again
	    }    
	}
	return false; 

    }


    //
    //  Choose a random piece and then try to find a layout using all
    //  90 pieces.  Try to find layouts by trying each piece in turn
    //  as a starting piece and checking it in all 4 orientatons.
    //
    public void solve() {

	setHTMLoutput(true);

	printHTMLHeader();

	Piece first = null;

	for (int i = 0; i < pieces.length; i++) { // try all pieces!

	    first = pieces[i];

	    // try all rotations of this piece!
	    for (int rot = 0; rot < 4; rot++) {
		
		tryPiece(first, 45, 45);  // place piece in the center
		printDebugln("Starting with piece " + i +
			     " leaves " + available.size() + " pieces.");
		if (available.size() == 0) {
		    System.out.println("Starting with piece " + i);
		    printHTMLMap();
		    //printMiniMap();
		    //printMap();
		    System.out.println(++mapsFound + " map(s) found!\n");
		}
		clearLayout();
		resetAvailablePieces();
		first.rotate();

	    }

	}

	printHTMLFooter();

    } 


    //
    //  Create a puzzle and start looking for solutions!
    //
    public static void main(String args[]) {

	LineUp l = new LineUp();
	
	l.solve();

	// Testing the class:

	/*
	l.layout[2][1] = l.pieces[6]; // a.jpg piece 7
	l.layout[2][1].rotate(Piece.TOP, Piece.RIGHT);
	l.layout[1][2] = l.pieces[2]; // a.jpg piece 3
	l.layout[3][2] = l.pieces[9]; // a.jpg piece 10
	l.layout[3][2].rotate(Piece.TOP, Piece.LEFT);

	System.out.println(l.available.size() + " pieces available.");

	// Text output
	l.printMiniMap();
	l.printMap();

	Vector v = null;
	v = l.getMatches(1,1);
	v = l.getMatches(3,1);
	v = l.getMatches(2,2);
	v = l.getMatches(1,3);
	
	// HTML output
	//l.printHTMLHeader();
	//l.printHTMLMap();
	//l.printHTMLFooter();
	l.alignPiece(l.pieces[8], 1, 1);
	l.pieces[10].rotate();
	l.alignPiece(l.pieces[10], 2, 2);
	l.alignPiece(l.pieces[17], 4, 4);

	*/

    }

}
