package com.kingcheckers.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kingcheckers.game.Model.Board;
import com.kingcheckers.game.Model.Box;

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
		board = new Board();
		board.initializeBoard();
		xPos = 0;
		yPos = 0;
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
