package com.mygame.game.handlers;

import java.util.ArrayList;

import com.mygame.game.rng.PathSegment;

public class TreeNode {
	
	private ArrayList<PathSegment> children;
	private PathSegment path;
	private PathSegment parent;

	public TreeNode(PathSegment a) {
		this.path = a;
		if(a.getParent() != null){
			this.parent = a.getParent();
		}else{
			this.parent = null;
		}
		children = new ArrayList<PathSegment>();
	}
	
	public void addChild(PathSegment a){
		children.add(a);
	}

}
