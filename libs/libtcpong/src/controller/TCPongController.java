package controller;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import model.data.GameState;
import model.data.PongData;
import tcp.ConnectionManager;
import tcp.DataManager;

public class TCPongController extends PokePongController {

	private ConnectionManager connectionManager;
	private DataManager dataManager;
	private final Semaphore dataSemaphore = new Semaphore(1);

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
		this.dataManager.setStreams(connectionManager);
	}

	private void startMovementThread() {
		if (!connectionManager.getIsClient()) {
			new Thread(this::receiveAndHandleMovementData).start();
		}
	}

	private void updateData() {
		try {
			dataSemaphore.acquire();
			if (connectionManager.getIsClient()) {
				try {
					model.setData(dataManager.receiveData(PongData.class));
				} catch (IOException e) {
					connectionManager.handleServerConnectionLoss();
				}
			} else {
				try {
					dataManager.sendData(model.getData());
				} catch (IOException e) {
					connectionManager.handleClientConnectionLoss();
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			dataSemaphore.release();
		}
	}

	private void receiveAndHandleMovementData() {
		while (connectionManager.getConnectionAlive()) {
			try {
				if (model.getGameState() != GameState.PLAYING) {
					continue;
				}
				dataSemaphore.acquire();
				Integer keyCode = dataManager.receiveData(Integer.class);
				if (keyCode == null) {
					handleFailedMovementData();
					continue;
				}
				handlePlayerTwoInput(keyCode);
			} catch (InterruptedException | IOException e) {
				connectionManager.handleClientConnectionLoss();
				Thread.currentThread().interrupt();
			} finally {
				dataSemaphore.release();
			}
		}
	}

	private void handleFailedMovementData() {
		System.err.println("[server] Failed to receive movement data");
	}

	@Override
	public void nextFrame() {
		if (connectionManager.getConnectionAlive()) {
			updateData();
		} else {
			model.setGameState(GameState.GAME_OVER);
			connectionManager.startClient();
		}
		view.drawGame();
	}

	@Override
	public void startGame(
		int width,
		int height,
		int PLAYER_SIZE,
		int PADDLE_WIDTH,
		int BALL_SIZE
	) {
		this.width = width;
		this.height = height;
		this.PLAYER_SIZE = PLAYER_SIZE;
		this.PADDLE_WIDTH = PADDLE_WIDTH;
		this.BALL_SIZE = BALL_SIZE;
		model.startGame(width, height, PLAYER_SIZE, PADDLE_WIDTH, BALL_SIZE);
		startMovementThread();
	}

	@Override
	protected void handlePlayingInput(char key, int keyCode) {
		if (connectionManager.getIsClient()) {
			try {
				dataManager.sendData(keyCode);
			} catch (IOException e) {
				connectionManager.handleClientConnectionLoss();
				model.setGameState(GameState.GAME_OVER);
			}
		} else {
			handlePlayerOneInput(key);
		}
	}
}
