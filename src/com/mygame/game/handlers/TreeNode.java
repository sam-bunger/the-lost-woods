package com.mygame.game.handlers;

import java.util.ArrayList;

import com.mygame.game.rng.PathSegment;

public class TreeNode {
	
	private ArrayList<TreeNode> children;
	private ArrayList<TreeNode> sibilings;
	private PathSegment path;
	private TreeNode parent;

	public TreeNode(PathSegment a, TreeNode parent) {
		this.path = a;
		this.parent = parent;
		children = new ArrayList<TreeNode>();
		sibilings = new ArrayList<TreeNode>();
	}
	
	public void setSibilings(){
		if(parent == null) return;	
		for(int i = 0; i < parent.getChildren().size(); i++){
			if(!parent.getChildren().get(i).getPath().equals(path)){
				sibilings.add(parent.getChildren().get(i));
			}
		}
	}
	
	public void addChild(TreeNode a){
		children.add(a);
	}
	
	public void setParent(TreeNode a){
		parent = a;
	}
	
	public TreeNode getParent(){ return parent; }
	public ArrayList<TreeNode> getChildren(){ return children; }
	public ArrayList<TreeNode> getSibilings(){ return sibilings; }
	public PathSegment getPath(){ return path; }

}
