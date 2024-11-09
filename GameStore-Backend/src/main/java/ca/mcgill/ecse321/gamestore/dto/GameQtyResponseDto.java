package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.GameQty;

public class GameQtyResponseDto {
    private int id;
    private int qty;
    private TransactionResponseDto transaction;
    private GameResponseDto game;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private GameQtyResponseDto() {
    }

    public GameQtyResponseDto(GameQty model) {
        id = model.getId();
        qty = model.getQty();
        if (model.getGame() == null) {
            throw new IllegalArgumentException("GameQty object has no game associated.");
        }
        if (model.getTransaction() == null) {
            throw new IllegalArgumentException("GameQty object has no transaction associated.");
        }
        setGame(new GameResponseDto(model.getGame()));
        setTransaction(new TransactionResponseDto(model.getTransaction()));
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

    public int getId() {
        return id;
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