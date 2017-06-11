package com.kingcheckers.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.kingcheckers.game.Model.ActiveChecker;
import com.kingcheckers.game.Model.Board;
import com.kingcheckers.game.Model.Box;
import com.kingcheckers.game.Model.CheckerType;

public class KingCheckers extends ApplicationAdapter {
	SpriteBatch batch;
	Texture beigeBox;
	Texture brownBox;
	Texture whiteRegular;
	Texture whiteRegularSelected;
	Texture blackRegular;
	Texture blackRegularSelected;
	Board board;
	ActiveChecker selectedChecker;

	OrthographicCamera camera;
	int xPos;
	int yPos;
	
	@Override
	public void create () {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		beigeBox = new Texture("beigeBox.png");
		brownBox = new Texture("brownBox.png");
		whiteRegular = new Texture("white_regular.png");
		whiteRegularSelected = new Texture("white_regular_selected.png");
		blackRegular = new Texture("black_regular.png");
		blackRegularSelected = new Texture("black_regular_selected.png");
		board = new Board();
		board.initializeBoard();
		xPos = 0;
		yPos = 0;

		camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

		Gdx.input.setInputProcessor(new InputProcessor() {
			@Override
			public boolean keyDown(int keycode) {
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				Vector3 v = new Vector3(screenX, screenY, 0);
				camera.unproject(v);

				if (button == Input.Buttons.LEFT) {
					for (int row = 0; row < board.getBoxes().length; row++) {
						for (int col = 0; col < board.getBoxes()[row].length; col++) {
							Box b = board.getBoxes()[row][col];

							if (b.getBoundingBox().contains(v)) {
								System.out.println(b.getX() + " " + b.getY());

									if(!b.getClicked()) {
										b.setClicked(true);

										// check if place is clicked when already a pawn was selected
										if (selectedChecker != null && b.getActiveChecker() == null) {
											if (selectedChecker.canMoveTo(b)) {
												b.setActiveChecker(selectedChecker);

												for (int r = 0; r < board.getBoxes().length; r++) {
													for (int c = 0; c < board.getBoxes()[r].length; c++) {
														Box b2 = board.getBoxes()[r][c];

														if (b2.getX() == selectedChecker.getBox().getX()
																&& b2.getY() == selectedChecker.getBox().getY()) {
															board.getBoxes()[r][c].removeChecker();
														}
													}
												}
											}
											b.setClicked(false);
										} else {
											selectedChecker = b.getActiveChecker();
										}

									}
								}
							}
						}
						return true;
					}
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				return false;
			}
		});
	}

	@Override
	public void render () {
		//Gdx.gl.glClearColor(0, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		renderBoard();
		renderPieces();
		batch.end();
	}

	private void renderBoard() {
		for (int row = 0; row < board.getBoxes().length; row++) {
			for (int col = 0; col < board.getBoxes()[row].length; col++) {
				Box b = board.getBoxes()[row][col];

				if (b.getColor() == Color.WHITE) {
					batch.draw(beigeBox, b.getX(), b.getY());
				} else {
					batch.draw(brownBox, b.getX(), b.getY());
				}

			}
		}
	}

	private void renderPieces() {
		for (int row = 0; row < board.getBoxes().length; row++) {
			for (int col = 0; col < board.getBoxes()[row].length; col++) {
				Box b = board.getBoxes()[row][col];

				if(b.getActiveChecker() != null) {
					if(b.getActiveChecker().getCheckerType() == CheckerType.WHITE_REGULAR) {
						if(b.getClicked()) {
							batch.draw(whiteRegularSelected, b.getX(), b.getY());
						} else {
							batch.draw(whiteRegular, b.getX(), b.getY());
						}
					}
					else if(b.getActiveChecker().getCheckerType() == CheckerType.BLACK_REGULAR) {
						if(b.getClicked()) {
							batch.draw(blackRegularSelected, b.getX(), b.getY());
						} else {
							batch.draw(blackRegular, b.getX(), b.getY());
						}
					}
				}

			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		beigeBox.dispose();
		brownBox.dispose();
	}


}
