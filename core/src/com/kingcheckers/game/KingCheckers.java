package com.kingcheckers.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.kingcheckers.game.Model.Board;
import com.kingcheckers.game.Model.Box;
import com.kingcheckers.game.Model.CheckerType;

public class KingCheckers extends ApplicationAdapter {
	SpriteBatch batch;
	Texture beigeBox;
	Texture brownBox;
	Board board;
	int xPos;
	int yPos;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		beigeBox = new Texture("beigeBox.png");
		brownBox = new Texture("brownBox.png");
	Texture whiteRegular;
	Texture blackRegular;
	Board board;
	int xPos;
	int yPos;
	OrthographicCamera camera;
	
	@Override
	public void create () {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		beigeBox = new Texture("beigeBox.png");
		brownBox = new Texture("brownBox.png");
		whiteRegular = new Texture("white_regular.png");
		blackRegular = new Texture("black_regular.png");
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

								if (!b.getClicked()) {
									b.setClicked(true);
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
		//batch.draw(brownBox, 0,0);
		batch.end();
	}

	private void renderBoard() {
		for (int row = 0; row < board.getBoxes().length; row++) {
			for (int col = 0; col < board.getBoxes()[row].length; col++) {
				Box b = board.getBoxes()[row][col];

				if (b.getColor() == Color.WHITE) {
					batch.draw(beigeBox, xPos, yPos);
					xPos = xPos + 50;
				} else {
					batch.draw(brownBox, xPos, yPos);
					xPos = xPos + 50;
				}

				if (col == 9) { // next row
					yPos = yPos + 50;
					xPos = 0;
				}

				if(row == 9 && col == 9) { // Reset positions after last row
					xPos = 0;
					yPos = 0;
				}
					batch.draw(beigeBox, b.getX(), b.getY());
				} else {
					batch.draw(brownBox, b.getX(), b.getY());

					if(b.getChecker() != null) {
						if(b.getChecker().getCheckerType() == CheckerType.WHITE_REGULAR) {
							batch.draw(whiteRegular, b.getX(), b.getY());
						}
						else if(b.getChecker().getCheckerType() == CheckerType.BLACK_REGULAR) {
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
