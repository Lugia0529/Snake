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

public class Coor
{
    private int mX;         // X coordinate
    private int mY;         // Y coordinate
    
    //-------------------------------------------------------------------------
    
    // Constructor
    
    public Coor(int x, int y)
    {
        this.mX = x;
        this.mY = y;
    }
    
    //-------------------------------------------------------------------------
    
    // Set the x coordinate
    
    public void setX(int x)
    {
        this.mX = x;
    }
    
    //-------------------------------------------------------------------------
    
    // Set the y coordinate
    
    public void setY(int y)
    {
        this.mY = y;
    }
    
    //-------------------------------------------------------------------------
    
    // Get the x coordinate
    
    public int getX()
    {
        return this.mX;
    }
    
    //-------------------------------------------------------------------------
    
    // Get the y coordinate
    
    public int getY()
    {
        return this.mY;
    }
    
    //-------------------------------------------------------------------------
    
    // Check whether both coordinates are same
    
    public boolean equals(Object obj) 
    {
        if (this == obj)
            return true;
        
        if (obj instanceof Coor) 
        {
            Coor c = (Coor)obj;
            
            return this.mX == c.mX && this.mY == c.mY;
        }
        
        return false;
    }
    
    //-------------------------------------------------------------------------
    
    // String representation of coordinate object
    
    public String toString() 
    {
        return String.format("(%d, %d)", this.mX, this.mY);
    }
}
