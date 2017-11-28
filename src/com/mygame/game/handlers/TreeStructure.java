package com.mygame.game.handlers;

import com.mygame.game.rng.PathSegment;

public class TreeStructure {
	
	private TreeNode root;

	public TreeStructure() {
		root = null;
	}
	
	public TreeStructure(TreeNode root) {
		this.root = root;
	}
	
	public void addRoot(PathSegment a){
		root = new TreeNode(a);
	}
	
	

}
