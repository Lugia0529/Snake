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

import java.util.ArrayList;
import java.util.List;

//-----------------------------------------------------------------------------

public class Snake
{
    private boolean mExtend;            // Snake extending flag
    
    private Direction mDirection;       // Snake moving direction
    private List<Coor> mSnakeBody;      // Snake body array
    
    //-------------------------------------------------------------------------
    
    // Possible moving direction
    
    public enum Direction
    {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    
    //-------------------------------------------------------------------------
    
    // Constructor
    
    public Snake()
    {
        this.mExtend = false;
        
        this.mDirection = Direction.RIGHT;
        this.mSnakeBody = new ArrayList<>();
        
        this.mSnakeBody.add(new Coor(15, 15));
        this.mSnakeBody.add(new Coor(14, 15));
        this.mSnakeBody.add(new Coor(13, 15));
        this.mSnakeBody.add(new Coor(12, 15));
        this.mSnakeBody.add(new Coor(11, 15));
    }
    
    //-------------------------------------------------------------------------
    
    // Get the length of snake, including head
    
    public int getSize()
    {
        return mSnakeBody.size();
    }
    
    //-------------------------------------------------------------------------
    
    // Set the extending flag
    
    public void extend()
    {
        mExtend = true;
    }
    
    //-------------------------------------------------------------------------
    
    // Change the snake moving direction
    
    public void changeDirection(Direction newDirection) 
    {
        switch(newDirection) 
        {
            case UP:
                if (mDirection != Snake.Direction.DOWN)
                    mDirection = Snake.Direction.UP;
                
                break;
            
            case DOWN:
                if (mDirection != Snake.Direction.UP)
                    mDirection = Snake.Direction.DOWN;
                
                break;
            
            case LEFT:
                if (mDirection != Snake.Direction.RIGHT)
                    mDirection = Snake.Direction.LEFT;
                
                break;
            
            case RIGHT:
                if (mDirection != Snake.Direction.LEFT)
                    mDirection = Snake.Direction.RIGHT;
                
                break;
        }
    }
    
    //-------------------------------------------------------------------------
    
    // Move the snake by one step
    
    public void move()
    {
        Coor c = mSnakeBody.get(0);
        
        switch (mDirection)
        {
            case UP:
                mSnakeBody.add(0, new Coor(c.getX(), c.getY() - 1));
                break;
            
            case DOWN:
                mSnakeBody.add(0, new Coor(c.getX(), c.getY() + 1));
                break;
            
            case LEFT:
                mSnakeBody.add(0, new Coor(c.getX() - 1, c.getY()));
                break;
            
            case RIGHT:
                mSnakeBody.add(0, new Coor(c.getX() + 1, c.getY()));
                break;
        }
        
        if (mExtend)
        {
            mExtend = false;
            
            return;
        }
        
        mSnakeBody.remove(mSnakeBody.size() - 1);
    }
    
    //-------------------------------------------------------------------------
    
    // Check whether snake is collided itself
    
    public boolean collide()
    {
        Coor head = mSnakeBody.get(0);
        
        for (int i = 1; i < mSnakeBody.size(); i++)
            if (head.equals(mSnakeBody.get(i)))
                return true;
        
        return false;
    }
    
    //-------------------------------------------------------------------------
    
    // Get the snake body coordinates list
    
    public List<Coor> getSnakeBody()
    {
        return mSnakeBody;
    }
    
    //-------------------------------------------------------------------------
    
    // Get the coordinate of the snake head
    
    public Coor getSnakeHead()
    {
        return mSnakeBody.get(0);
    }
    
    //-------------------------------------------------------------------------
    
    // String representation of snake object
    
    public String toString()
    {
        String str = String.format("Snake size: %d\n", mSnakeBody.size());
        
        for (Coor c : mSnakeBody)
            str += c + "\n";
        
        return str;
    }
}
