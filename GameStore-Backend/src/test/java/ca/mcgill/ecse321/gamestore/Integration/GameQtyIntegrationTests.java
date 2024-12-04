package ca.mcgill.ecse321.gamestore.Integration;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GameQtyIntegrationTests {
        /*
         * @Autowired
         * private GameRepository gameRepository;
         * 
         * @Autowired
         * private TransactionRepository transactionRepository;
         * 
         * @Autowired
         * private CustomerAccountRepository customerAccountRepository;
         * 
         * @Autowired
         * private TestRestTemplate client;
         * 
         * private final String VALID_GAME_NAME = "The Legend of Zelda";
         * private final int VALID_PRICE = 60;
         * private final String VALID_DESCRIPTION =
         * "An epic action-adventure game set in a fantasy world.";
         * private final Category VALID_CATEGORY = Category.ActionAdventure;
         * private final GameConsole VALID_GAME_CONSOLE = GameConsole.PC;
         * 
         * private final int GAME_QTY = 1;
         * 
         * @BeforeAll
         * 
         * @AfterAll
         * public void clearDatabase() {
         * transactionRepository.deleteAll();
         * customerAccountRepository.deleteAll();
         * gameRepository.deleteAll();
         * }
         */

        /*
         * @Test
         * 
         * @Order(1)
         * public void testCreateValidGameQty() throws Exception {
         * // Arrange
         * // create and persist necessary objects for creation of GameQty
         * CustomerAccount customerAccount = new CustomerAccount();
         * customerAccount.setEmail("bob@gmail.com");
         * customerAccount.setName("bob");
         * customerAccount.setUsername("bob123");
         * customerAccount.setRandomPassword("aRandomPassword");
         * customerAccount.setPasswordHash("thePasswordHash");
         * 
         * Transaction transactionModel = new Transaction();
         * TransactionResponseDto transaction = new
         * TransactionResponseDto(transactionModel);
         * Game gameModel = new Game(VALID_GAME_NAME, VALID_PRICE, VALID_DESCRIPTION,
         * VALID_CATEGORY,
         * VALID_GAME_CONSOLE);
         * GameResponseDto game = new GameResponseDto(gameModel);
         * 
         * GameQtyRequestDto request = new GameQtyRequestDto(GAME_QTY, transaction,
         * game);
         * 
         * // Act
         * ResponseEntity<GameQtyResponseDto> response =
         * client.postForEntity("/api/game-qty/create", request,
         * GameQtyResponseDto.class);
         * 
         * // Assert
         * assertNotNull(response.getBody());
         * GameQtyResponseDto createdGameQty = response.getBody();
         * assertNotNull(createdGameQty);
         * assertEquals(transaction.getTransactionId(),
         * createdGameQty.getTransaction().getTransactionId());
         * assertEquals(game.getId(), createdGameQty.getGame().getId());
         * assertTrue(createdGameQty.getId() >= 0);
         * }
         * 
         * @SuppressWarnings("null")
         * 
         * @Test
         * 
         * @Order(2)
         * public void testReadGameQtyByValidId() throws Exception {
         * // Arrange
         * // Create and persist necessary objects for creation of GameQty
         * CustomerAccount customerAccount = new CustomerAccount();
         * customerAccount.setEmail("bob@gmail.com");
         * customerAccount.setName("bob");
         * customerAccount.setUsername("bob123");
         * customerAccount.setRandomPassword("aRandomPassword");
         * customerAccount.setPasswordHash("thePasswordHash");
         * 
         * Transaction transactionModel = new Transaction();
         * Game gameModel = new Game(VALID_GAME_NAME, VALID_PRICE, VALID_DESCRIPTION,
         * VALID_CATEGORY,
         * VALID_GAME_CONSOLE);
         * 
         * GameQtyRequestDto request = new GameQtyRequestDto(GAME_QTY,
         * new TransactionResponseDto(transactionModel),
         * new GameResponseDto(gameModel));
         * 
         * ResponseEntity<GameQtyResponseDto> createResponse =
         * client.postForEntity("/api/game-qty/create",
         * request,
         * GameQtyResponseDto.class);
         * GameQtyResponseDto createdGameQty = createResponse.getBody();
         * assertNotNull(createdGameQty);
         * 
         * // Act
         * ResponseEntity<GameQtyResponseDto> response = client.getForEntity(
         * "/api/game-qty/get/" + createdGameQty.getId(),
         * GameQtyResponseDto.class);
         * 
         * // Assert
         * assertNotNull(response);
         * assertEquals(createdGameQty.getId(), response.getBody().getId());
         * assertEquals(createdGameQty.getTransaction().getTransactionId(),
         * response.getBody().getTransaction().getTransactionId());
         * assertEquals(createdGameQty.getGame().getId(),
         * response.getBody().getGame().getId());
         * }
         * 
         * @SuppressWarnings("null")
         * 
         * @Test
         * 
         * @Order(3)
         * public void testReadGameQtyByTransaction() throws Exception {
         * // Arrange
         * // Create and persist necessary objects for creation of GameQty
         * CustomerAccount customerAccount = new CustomerAccount();
         * customerAccount.setEmail("bob@gmail.com");
         * customerAccount.setName("bob");
         * customerAccount.setUsername("bob123");
         * customerAccount.setRandomPassword("aRandomPassword");
         * customerAccount.setPasswordHash("thePasswordHash");
         * 
         * Transaction transactionModel = new Transaction();
         * Game gameModel = new Game(VALID_GAME_NAME, VALID_PRICE, VALID_DESCRIPTION,
         * VALID_CATEGORY,
         * VALID_GAME_CONSOLE);
         * 
         * GameQtyRequestDto request = new GameQtyRequestDto(GAME_QTY,
         * new TransactionResponseDto(transactionModel),
         * new GameResponseDto(gameModel));
         * 
         * ResponseEntity<GameQtyResponseDto> createResponse =
         * client.postForEntity("/api/game-qty/create",
         * request,
         * GameQtyResponseDto.class);
         * GameQtyResponseDto createdGameQty = createResponse.getBody();
         * assertNotNull(createdGameQty);
         * 
         * // Act
         * ResponseEntity<GameQtyResponseDto[]> response = client.getForEntity(
         * "/api/gameQty/get-by-transaction/" + transactionModel.getTransactionId(),
         * GameQtyResponseDto[].class);
         * 
         * // Assert
         * assertNotNull(response);
         * assertTrue(response.getBody().length > 0);
         * assertEquals(transactionModel.getTransactionId(),
         * response.getBody()[0].getTransaction().getTransactionId());
         * }
         * 
         * @SuppressWarnings("null")
         * 
         * @Test
         * 
         * @Order(4)
         * public void testUpdateGameQty() throws Exception {
         * // Arrange
         * // Create and persist necessary objects for creation of GameQty
         * CustomerAccount customerAccount = new CustomerAccount();
         * customerAccount.setEmail("bob@gmail.com");
         * customerAccount.setName("bob");
         * customerAccount.setUsername("bob123");
         * customerAccount.setRandomPassword("aRandomPassword");
         * customerAccount.setPasswordHash("thePasswordHash");
         * 
         * Transaction transactionModel = new Transaction();
         * Game gameModel = new Game(VALID_GAME_NAME, VALID_PRICE, VALID_DESCRIPTION,
         * VALID_CATEGORY,
         * VALID_GAME_CONSOLE);
         * 
         * GameQtyRequestDto request = new GameQtyRequestDto(GAME_QTY,
         * new TransactionResponseDto(transactionModel),
         * new GameResponseDto(gameModel));
         * 
         * ResponseEntity<GameQtyResponseDto> createResponse =
         * client.postForEntity("/api/game-qty/create",
         * request,
         * GameQtyResponseDto.class);
         * GameQtyResponseDto createdGameQty = createResponse.getBody();
         * assertNotNull(createdGameQty);
         * 
         * // Update quantity to 3
         * createdGameQty.setQty(3);
         * 
         * // Act
         * client.put("/api/game-qty/update", createdGameQty);
         * 
         * // Assert
         * ResponseEntity<GameQtyResponseDto> updatedResponse = client
         * .getForEntity("/api/game-qty/get/" + createdGameQty.getId(),
         * GameQtyResponseDto.class);
         * assertEquals(HttpStatus.OK, updatedResponse.getStatusCode());
         * assertEquals(3, updatedResponse.getBody().getQty());
         * }
         * 
         * @Test
         * 
         * @Order(5)
         * public void testDeleteGameQty() throws Exception {
         * // Arrange
         * // Create and persist necessary objects for creation of GameQty
         * CustomerAccount customerAccount = new CustomerAccount();
         * customerAccount.setEmail("bob@gmail.com");
         * customerAccount.setName("bob");
         * customerAccount.setUsername("bob123");
         * customerAccount.setRandomPassword("aRandomPassword");
         * customerAccount.setPasswordHash("thePasswordHash");
         * 
         * Transaction transactionModel = new Transaction();
         * Game gameModel = new Game(VALID_GAME_NAME, VALID_PRICE, VALID_DESCRIPTION,
         * VALID_CATEGORY,
         * VALID_GAME_CONSOLE);
         * 
         * GameQtyRequestDto request = new GameQtyRequestDto(GAME_QTY,
         * new TransactionResponseDto(transactionModel),
         * new GameResponseDto(gameModel));
         * 
         * ResponseEntity<GameQtyResponseDto> createResponse =
         * client.postForEntity("/api/game-qty/create",
         * request,
         * GameQtyResponseDto.class);
         * GameQtyResponseDto createdGameQty = createResponse.getBody();
         * assertNotNull(createdGameQty);
         * 
         * // Act
         * client.delete("/api/game-qty/delete/" + createdGameQty.getId());
         * 
         * // Assert
         * ResponseEntity<GameQtyResponseDto> response = client.getForEntity(
         * "/api/game-qty/get/" + createdGameQty.getId(),
         * GameQtyResponseDto.class);
         * assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
         * }
         */
}