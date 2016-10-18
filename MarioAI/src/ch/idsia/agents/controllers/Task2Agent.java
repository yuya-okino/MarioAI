package ch.idsia.agents.controllers;

import ch.idsia.agents.Agent;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.mario.environments.Environment;

public class Task2Agent extends BasicMarioAIAgent implements Agent
{
int trueJumpCounter = 0;
int trueSpeedCounter = 0;
int[][] ground = new int[19][];
boolean once = true; 

public Task2Agent()
{
    super("Task2Agent");
    reset();
}

//プログラム開始時
public void reset()
{		
    action = new boolean[Environment.numberOfKeys];
    action[Mario.KEY_RIGHT] = true;
    action[Mario.KEY_SPEED] = true;
    trueJumpCounter = 0;
    trueSpeedCounter = 0;
    once = true;
}

//フレームごとの動き
public boolean[] getAction()
{   
    int r, c;
	//地形情報を格納する多次元配列を作る
    for(int i=0; i<=18; i++){
    	ground[i] = new int[19];
    }
    for(r=0; r<=18; r++){
    	for(c=0; c<=18; c++){
    		ground[r][c] = getReceptiveFieldCellValue(r, c);
    	}
    }
    
    //ログに出力
    for(r=0; r<=18; r++){
    	for(c=0; c<=18; c++){
    		System.out.printf("%5s",ground[r][c]);
    	}
    	System.out.print('\n');
    }
    System.out.print("--------------------------------------------------"+"\n");
    
    
    return action;
}

//マリオのmマス先に下りがあるか
private boolean existDown(int m){
	if(getReceptiveFieldCellValue(marioEgoRow+m, marioEgoCol+1) ==  0){
		return true;
	}else{
		return false;
	}
}

//マリオのmマス先の下りの深さを図る (マリオとの相対位置)
private int getDepth(int m){
	int  i=0;
	int marioheight = marioEgoCol;
	while(true){
		if(getReceptiveFieldCellValue(marioEgoRow+m, marioEgoCol+i+1) == 0){
				break;
		}
		i++;
	}
	//このときは穴
	if(marioheight == i){
		return -1;
	}else{
		return i;
	}
}

//床情報
private void update() {
}



//穴が存在するか
private boolean existHole(int m){
	if(	getDepth(m)==-1){
		return true;
	}else{
		return false;
	}
}

private boolean DangerOfAny()
{
        if ((getReceptiveFieldCellValue(marioEgoRow + 2, marioEgoCol + 1) == 0 &&
            getReceptiveFieldCellValue(marioEgoRow + 1, marioEgoCol + 1) == 0) ||
            getReceptiveFieldCellValue(marioEgoRow, marioEgoCol + 1) != 0 ||
            getReceptiveFieldCellValue(marioEgoRow, marioEgoCol + 2) != 0 ||
            getEnemiesCellValue(marioEgoRow, marioEgoCol + 1) != 0 ||
            getEnemiesCellValue(marioEgoRow, marioEgoCol + 2) != 0)
            return true;
        else
            return false;
}
}
