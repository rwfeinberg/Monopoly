package ooga.visualization;
/**
 * @author Catherine Livingston
 * <p>
 * Purpose - The Piece.java class is used to allow players to select their pieces.
 * <p>
 * Assumptions - This class assumes that when if new piece images are added, you add a new enum to
 * the list. Additionally assumes that the option listed in the dropdown menu during selection screen
 * is the same spelling as the enum created for it.
 * <p>
 * Dependencies - This class depends on PieceView.
 */
public enum Piece {
  SHOE(1),
  DOG(2),
  THIMBLE(3),
  WHEELBARROW(4);

  private PieceView image;

  private Piece(int pieceValue) {
    image = new PieceView(pieceValue);
  }


  public PieceView getImage() {
    return image;
  }

  /**
   * finds the piece for the user based on the drop down option selected
   *
   * @param option - The string of the piece chosen and should correspond with an enum
   */
  public static PieceView findPiece(String option) {
    try {
      Class pieceEnum = Class.forName("ooga.visualization.Piece");
      Object[] objects = pieceEnum.getEnumConstants();
      for (Object options : pieceEnum.getEnumConstants()) {
        if (option.toUpperCase().equals(options.toString())) {
          return ((Piece) options).getImage();
        }
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }
}
