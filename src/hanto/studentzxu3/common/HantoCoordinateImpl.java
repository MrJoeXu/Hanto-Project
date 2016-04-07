/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2015 Gary F. Pollice
 *******************************************************************************/

package hanto.studentzxu3.common;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import hanto.common.HantoCoordinate;

/**
 * The implementation for my version of Hanto.
 * @version Mar 2, 2016
 */
public class HantoCoordinateImpl implements HantoCoordinate
{
	final private int x, y;
	
	/**
	 * The only constructor.
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public HantoCoordinateImpl(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Copy constructor that creates an instance of HantoCoordinateImpl from an
	 * object that implements HantoCoordinate.
	 * @param coordinate an object that implements the HantoCoordinate interface.
	 */
	public HantoCoordinateImpl(HantoCoordinate coordinate)
	{
		this(coordinate.getX(), coordinate.getY());
	}
	
	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HantoCoordinateImpl)) {
			return false;
		}
		final HantoCoordinateImpl other = (HantoCoordinateImpl) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * get a list of coordinate that adjacent to this coordinate
	 * @param startCoor
	 * 		the origin coordinate
	 * @return adjacent queue of coordinate
	 */
	public Queue<HantoCoordinate> getAdjacent() {
		Queue<HantoCoordinate> adjacents = new LinkedList<HantoCoordinate>();
		
		adjacents.add(new HantoCoordinateImpl(x,y+1));
		adjacents.add(new HantoCoordinateImpl(x,y-1));
		adjacents.add(new HantoCoordinateImpl(x+1,y));
		adjacents.add(new HantoCoordinateImpl(x-1,y));
		adjacents.add(new HantoCoordinateImpl(x-1,y+1));
		adjacents.add(new HantoCoordinateImpl(x+1,y-1));
		
		return adjacents;
	}
	
	
	/**
	 * Calculate the distance between this coordinate and targeted coordinate
	 * 
	 * @param to target coordinate to compute distance
	 * 
	 */
	public int getDistance(HantoCoordinate to) {
		int distance = 0;
		int dx = Math.abs(to.getX() - x);
		int dy = Math.abs(to.getY() - y);
		if (to.getX() == to.getY() && x == y) {
			distance = dx + dy + Math.abs(dx - dy);
		}
		else {
			distance = (dx + dy + Math.abs(dx - dy))/2;

		}
		
		return distance;
	}

	/**
	 * check whether this coordinate has any neighbors in the passed in list
	 * @param list list to check
	 * @return boolean
	 */
	public boolean hasAdjacencyInList(Set<HantoCoordinate> list) {
		Queue<HantoCoordinate> myAdjacets = this.getAdjacent();

		for (HantoCoordinate i: list) {
			if (myAdjacets.contains(i)) {
				return true;
			}
		}
		return false;
	}
	
	


}
