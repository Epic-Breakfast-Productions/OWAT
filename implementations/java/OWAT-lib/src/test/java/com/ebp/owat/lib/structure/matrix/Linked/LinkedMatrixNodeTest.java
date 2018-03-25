package com.ebp.owat.lib.structure.matrix.Linked;

import com.ebp.owat.lib.datastructure.matrix.Linked.utils.Direction;
import com.ebp.owat.lib.datastructure.matrix.Linked.utils.LinkedMatrixNode;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class LinkedMatrixNodeTest {
	public Object[] getDirs() {
		return Direction.values();
	}

	private void assertBorder(LinkedMatrixNode node, Direction dir) {
		assertTrue(node.isBorder(dir));
	}

	private void assertAllBorder(LinkedMatrixNode node) {
		for (Direction curDir : Direction.values()) {
			assertBorder(node, curDir);
		}
	}

	private void assertBorderTo(LinkedMatrixNode node, Collection<Direction> dirsToBorder) {
		for (Direction curDir : dirsToBorder) {
			assertTrue(node.isBorder(curDir));
		}
	}

	@Test
	public void testLinkedMatrixNodeConstructors() {
		LinkedMatrixNode<Integer> node = new LinkedMatrixNode<>();
		assertAllBorder(node);
		assertNull(node.getValue());

		node = new LinkedMatrixNode<>(5);
		assertAllBorder(node);
		assertEquals((Integer) 5, node.getValue());
	}

	@Test
	public void testGetSetValue() {
		LinkedMatrixNode<Integer> node = new LinkedMatrixNode<>();

		assertFalse(node.hasValue());

		assertEquals(null, node.setValue(5));
		assertEquals((Integer) 5, node.getValue());

		assertTrue(node.hasValue());

		assertEquals((Integer)5, node.clearValue());
		assertFalse(node.hasValue());
	}

	@Test
	@Parameters(method = "getDirs")
	public void testBorders(Direction curDir){
		LinkedMatrixNode<Integer> baseNode = new LinkedMatrixNode<>();
		LinkedMatrixNode<Integer> borderNode = new LinkedMatrixNode<>();

		baseNode.setDir(curDir, borderNode);

		assertEquals(curDir, baseNode.borders(borderNode));
		assertTrue(baseNode.borders(borderNode, curDir));
	}

	@Test
	@Parameters(method = "getDirs")
	public void testGetSetBorders(Direction curDir) {
		LinkedList<Direction> allButCurDir = new LinkedList<>(Arrays.asList(Direction.values()));
		LinkedList<Direction> allButCurDirOpp = new LinkedList<>(Arrays.asList(Direction.values()));
		allButCurDir.remove(curDir);
		allButCurDirOpp.remove(curDir.opposite());

		LinkedMatrixNode<Integer> baseNode = new LinkedMatrixNode<>();
		LinkedMatrixNode<Integer> borderNode = new LinkedMatrixNode<>();

		assertEquals(null, baseNode.getDir(curDir));
		assertTrue(baseNode.isBorder(curDir));

		assertNull(baseNode.setDir(curDir, borderNode));

		assertEquals(borderNode, baseNode.getDir(curDir));
		assertEquals(baseNode, borderNode.getDir(curDir.opposite()));

		assertBorderTo(baseNode, allButCurDir);
		assertBorderTo(borderNode, allButCurDirOpp);


		LinkedMatrixNode<Integer> newBorderNode = new LinkedMatrixNode<>();


		LinkedMatrixNode<Integer> result = baseNode.setDir(curDir, newBorderNode);

		assertEquals(borderNode, result);
		assertEquals(newBorderNode, baseNode.getDir(curDir));
		assertEquals(baseNode, newBorderNode.getDir(curDir.opposite()));

		assertAllBorder(borderNode);

		assertBorderTo(baseNode, allButCurDir);
		assertBorderTo(newBorderNode, allButCurDirOpp);
	}
}
