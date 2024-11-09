package ca.mcgill.ecse321.gamestore.dto;

public class GameQtyRequestDto {
    private int qty;
    private TransactionResponseDto transaction;
    private GameResponseDto game;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private GameQtyRequestDto() {
    }

    public GameQtyRequestDto(int aQty, TransactionRequestDto aTransaction, GameResponseDto aGame) {
        qty = aQty;
        setGame(aGame);
        setTransaction(transaction);
    }

    public boolean setQty(int aQty) {
        boolean wasSet = false;
        qty = aQty;
        wasSet = true;
        return wasSet;
    }

    public int getQty() {
        return qty;
    }

    /* Code from template association_GetOne */
    public TransactionResponseDto getTransaction() {
        return transaction;
    }

    /* Code from template association_GetOne */
    public GameResponseDto getGame() {
        return game;
    }

    /* Code from template association_SetOneToMany */
    public boolean setTransaction(TransactionResponseDto aTransaction) {
        boolean wasSet = false;
        if (aTransaction == null) {
            return wasSet;
        }

        transaction = aTransaction;
        wasSet = true;
        return wasSet;
    }

    /* Code from template association_SetOneToMany */
    public boolean setGame(GameResponseDto aGame) {
        boolean wasSet = false;
        if (aGame == null) {
            return wasSet;
        }

        game = aGame;
        wasSet = true;
        return wasSet;
    }
}