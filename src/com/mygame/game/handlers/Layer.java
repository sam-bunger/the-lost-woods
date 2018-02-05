package com.mygame.game.handlers;

import java.util.ArrayList;
import java.util.Collections;

import com.mygame.game.entities.GameObj;
import com.mygame.game.entities.Player;

public class Layer {
    private ArrayList<GameObj> entities;
     
    public ArrayList<GameObj> getEntities() {
        return entities;
    }
 
    public Layer(){
        entities = new ArrayList<GameObj>();
    }
    
    public void add(GameObj obj){
    	if(entities.size()<1){
    		entities.add(obj);
    		return;
    	}
    	
    	for(int i=0;i<entities.size();i++){
    		if(obj.getDepth()>=entities.get(i).getDepth()){
    			entities.add(i, obj);
    			return;
    		}
    	}
    	entities.add(obj);
    }
    
    public void remove(GameObj obj){
    	for(int i=0;i<entities.size();i++){
    		if(obj.equals(entities.get(i))){
    			entities.remove(i);
    			return;
    		}
    	}
    }
     
    public void sort(){       
        divide(0,entities.size()-1);
        Collections.reverse(entities);
    }
  
    private void divide(int startIndex,int endIndex){
         
        //Divide till you breakdown your list to single element
        if(startIndex<endIndex && (endIndex-startIndex)>=1){
            int mid = (endIndex + startIndex)/2;
            divide(startIndex, mid);
            divide(mid+1, endIndex);        
             
            //merging Sorted array produce above into one sorted array
            merge(startIndex,mid,endIndex);            
        }       
    }   
     
    private void merge(int startIndex,int midIndex,int endIndex){
         
        //Below is the mergedarray that will be sorted array Array[i-midIndex] , Array[(midIndex+1)-endIndex]
        ArrayList<GameObj> mergedSortedArray = new ArrayList<GameObj>();
         
        int leftIndex = startIndex;
        int rightIndex = midIndex+1;
         
        while(leftIndex<=midIndex && rightIndex<=endIndex){
            if(entities.get(leftIndex).getDepth()<=entities.get(rightIndex).getDepth()){
                mergedSortedArray.add(entities.get(leftIndex));
                leftIndex++;
            }else{
                mergedSortedArray.add(entities.get(rightIndex));
                rightIndex++;
            }
        }       
         
        //Either of below while loop will execute
        while(leftIndex<=midIndex){
            mergedSortedArray.add(entities.get(leftIndex));
            leftIndex++;
        }
         
        while(rightIndex<=endIndex){
            mergedSortedArray.add(entities.get(rightIndex));
            rightIndex++;
        }
         
        int i = 0;
        int j = startIndex;
        //Setting sorted array to original one
        while(i<mergedSortedArray.size()){
        	entities.set(j, mergedSortedArray.get(i++));
            j++;
        }
        
        
    }
}
