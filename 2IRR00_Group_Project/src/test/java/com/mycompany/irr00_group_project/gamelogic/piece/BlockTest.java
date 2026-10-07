package com.mycompany.irr00_group_project.gamelogic.piece;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;

/**
 * Test class for Block.
 * @author Steve
 */
public class BlockTest {

    @Test
    public void testBlockCreation() {
        Point position = new Point(5, 10);
        int blockSize = 30;
        Color color = Color.RED;
        
        Block block = new Block(position, blockSize, color);
        assertEquals(position, block.getPos(), "Block should have correct position");
    }

    @Test
    public void testSetPosition() {
        Block block = new Block(new Point(0, 0), 20, Color.BLUE);
        Point newPosition = new Point(3, 7);
        
        block.setPos(newPosition);
        assertEquals(newPosition, block.getPos(), "Block should have updated position");
    }

    @Test
    public void testClone() {
        Point originalPosition = new Point(2, 4);
        Block originalBlock = new Block(originalPosition, 25, Color.GREEN);
        
        Block clonedBlock = originalBlock.clone();
        assertNotSame(originalBlock, clonedBlock, "Cloned block should be different object");
        assertEquals(originalBlock.getPos(), clonedBlock.getPos(), 
            "Cloned block should have same position");
        assertNotSame(originalBlock.getPos(), clonedBlock.getPos(), 
            "Positions should be different objects");
        
        originalBlock.setPos(new Point(10, 10));
        assertEquals(originalPosition, clonedBlock.getPos(), "Clone should be independent");
    }
}