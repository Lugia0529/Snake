/*
 * Copyright (c) 2015 Pink Ziraffe Studio
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */

//-----------------------------------------------------------------------------

// Importation

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

//-----------------------------------------------------------------------------

public class GamePanel extends JPanel implements KeyListener
{
    private int mScore;                         // Player score
    private int mUpdateRate;                    // Snake move speed
    
    private boolean mGameOver;                  // Game over flag
    
    private Coor mFruit;                        // The coordinate of the fruit
    private Snake mSnake;                       // The snake object
    private Random mRandom;                     // Random Generator
    
    private static final int WIDTH = 30;        // Movable area width
    private static final int HEIGHT = 30;       // Movable area height
    
    //-------------------------------------------------------------------------
    
    // Constructor
    
    public GamePanel()
    {
        this.mRandom = new Random(System.currentTimeMillis());
    
        this.mSnake = new Snake();
        this.mFruit = new Coor(mRandom.nextInt(30), mRandom.nextInt(30));
        
        this.mScore = 0;
        this.mUpdateRate = 500;
        
        this.mGameOver = false;
        
        // Start the game loop thread
        
        new SnakeThread().start();
    }
    
    //-------------------------------------------------------------------------
    
    // UI drawing function
    
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        
        // Enable Anti-aliasing
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Clear the screen
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        // Draw player score
        
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        
        g2d.drawString(String.format("Score: %d", mScore), 16, 24);
        
        // Draw game border 
        
        g2d.setColor(Color.BLUE);
        g2d.drawRect(10, 30, 480, 480);
        
        // Draw fruit
        
        drawFruit(g2d);
        
        // Draw snake
        
        drawSnake(g2d);
        
        // Draw game over text
        
        if (mGameOver)
        {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 56));
            
            g2d.drawString("Game Over", 100, 280);
        }
    }
    
    //-------------------------------------------------------------------------
    
    // Event handler of key press event
    
    public void keyPressed(KeyEvent e)
    {
        // Restart the game if game is over
        
        if (mGameOver)
        {
            this.mSnake = new Snake();
            
            this.mFruit = new Coor(mRandom.nextInt(30), mRandom.nextInt(30));
            
            this.mScore = 0;
            this.mUpdateRate = 500;
            
            this.mGameOver = false;
            
            return;
        }
        
        // Change direction
        
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_UP:
                this.mSnake.changeDirection(Snake.Direction.UP);
                break;
    
            case KeyEvent.VK_DOWN:
                this.mSnake.changeDirection(Snake.Direction.DOWN);
                break;
            
            case KeyEvent.VK_LEFT:
                this.mSnake.changeDirection(Snake.Direction.LEFT);
                break;
            
            case KeyEvent.VK_RIGHT:
                this.mSnake.changeDirection(Snake.Direction.RIGHT);
                break;
        }
    }
    
    //-------------------------------------------------------------------------
    
    // Event handler of key type event
    
    public void keyTyped(KeyEvent e)
    {
        /* DO NOTHING */
    }
    
    //-------------------------------------------------------------------------
    
    // Event handler of key release event
    
    public void keyReleased(KeyEvent e)
    {
        /* DO NOTHING */
    }
    
    //-------------------------------------------------------------------------
    
    // Draw circle from center
    
    private void drawCircle(Graphics2D g2d, int x, int y, int radius)
    {
        x -= radius / 2;
        y -= radius / 2;
        
        g2d.fillOval(x, y, radius, radius);
    }
    
    //-------------------------------------------------------------------------
    
    // Draw the fruit
    
    private void drawFruit(Graphics2D g2d)
    {
        g2d.setColor(Color.YELLOW);
    
        Coor fruitCoor = calcCoordinate(mFruit);
    
        drawCircle(g2d, fruitCoor.getX(), fruitCoor.getY(), 12);
    }
    
    //-------------------------------------------------------------------------
    
    // Draw the snake
    
    private void drawSnake(Graphics2D g2d)
    {
        g2d.setColor(Color.BLUE);
        
        // Draw the snake head
        
        Coor c = calcCoordinate(mSnake.getSnakeHead());
        
        drawCircle(g2d, c.getX(), c.getY(), 16);
        
        // Draw the snake body
        
        List<Coor> snakeBody = mSnake.getSnakeBody();
        
        for (int i = 1; i < snakeBody.size(); i++)
        {
            c = calcCoordinate(snakeBody.get(i));
            
            drawCircle(g2d, c.getX(), c.getY(), 12);
        }
    }
    
    //-------------------------------------------------------------------------
    
    // Calculate the game UI coordinate
    
    private Coor calcCoordinate(Coor coor)
    {
        return new Coor(25 + coor.getX() * 15, 45 + coor.getY() * 15);
    }
    
    //-------------------------------------------------------------------------
    
    // Game loop thread
    
    class SnakeThread extends Thread
    {
        public void run()
        {
            while (true)
            {
                if (!mGameOver)
                {
                    // Move the snake
                    
                    mSnake.move();
                    
                    Coor c = mSnake.getSnakeHead();
                    
                    // Snake is eating fruit
                    
                    if (c.equals(mFruit))
                    {
                        // Spawn a new fruit in random location
                        
                        mFruit.setX(mRandom.nextInt(30));
                        mFruit.setY(mRandom.nextInt(30));
                        
                        // Increase the snake size by 1
                        
                        mSnake.extend();
                        
                        // Increse player score
                        
                        mScore = GamePanel.this.mScore + 100;
                        
                        // Increase snake moving speed
                        
                        if(mUpdateRate > 150)
                            mUpdateRate = mUpdateRate - 10;
                    }
                    
                    // Check for out of bound
                    
                    if (c.getX() < 0 || c.getX() > WIDTH || 
                        c.getY() < 0 || c.getY() > HEIGHT)
                        mGameOver = true;
                    
                    // Check for snake collide itself
                    
                    if(mSnake.collide())
                        mGameOver = true;
                }
                
                // Wait for next update
                
                try
                {
                    Thread.sleep(mUpdateRate);
                }
                catch (InterruptedException ie)
                {
                    ie.printStackTrace();
                }
                
                // Update game interface
                
                repaint();
            }
        }
    }
}
