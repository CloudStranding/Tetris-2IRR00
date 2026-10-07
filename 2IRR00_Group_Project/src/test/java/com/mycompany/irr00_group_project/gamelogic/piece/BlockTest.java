package com.mycompany.irr00_group_project.gamelogic.piece;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;

// @author: Steve

public class BlockTest {

    @Test
    public void testBlockConstructionAndGetPos() {
        Block block = new Block(new Point(3, 5), 20, Color.BLUE);

        Point pos = block.getPos();
        assertEquals(3, pos.x);
        assertEquals(5, pos.y);
    }

    @Test
    public void testBlockSetPos() {
        Block block = new Block(new Point(0, 0), 20, Color.GREEN);
        block.setPos(new Point(4, 6));

        Point pos = block.getPos();
        assertEquals(4, pos.x);
        assertEquals(6, pos.y);
    }
    
    @Test
    public void testBlockClone() {
        Block original = new Block(new Point(1, 1), 20, Color.RED);
        Block copy = original.clone();

        assertNotSame(original, copy); 
        assertEquals(original.getPos(), copy.getPos()); 
        assertNotSame(original.getPos(), copy.getPos()); 
    }


}
