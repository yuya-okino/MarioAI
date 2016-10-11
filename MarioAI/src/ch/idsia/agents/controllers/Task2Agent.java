package ch.idsia.agents.controllers;

import ch.idsia.agents.Agent;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.mario.environments.Environment;

public class Task2Agent extends BasicMarioAIAgent implements Agent
{
int trueJumpCounter = 0;
int trueSpeedCounter = 0;

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
}

//フレームごとの動き
public boolean[] getAction()
{
    //穴があるとき
    if (DangerOfAny() && getReceptiveFieldCellValue(marioEgoRow, marioEgoCol + 1) != 1)  // a coin
    {
        if (isMarioAbleToJump || (!isMarioOnGround && action[Mario.KEY_JUMP]))
        {
            action[Mario.KEY_JUMP] = true;
        }
        ++trueJumpCounter;
    }
    else
    {
        action[Mario.KEY_JUMP] = false;
        trueJumpCounter = 0;
    }

    if (trueJumpCounter > 16)
    {
        trueJumpCounter = 0;
        action[Mario.KEY_JUMP] = false;
    }
    
    return action;
}

//マリオのmマス先に下りがあるか
private boolean existDown(int m){
	if(getReceptiveFieldCellValue(marioEgoRow + m, marioEgoCol -1 ) ==  0){
		return true;
	}else{
		return false;
	}
}

//マリオのmマス先の下りの深さを図る 
private int getDepth(int m){
	int  i=0;
	int marioheight = marioEgoCol;
	while(true){
		if(getReceptiveFieldCellValue(marioEgoRow + m, marioEgoCol-i-1) == 0){
				break;
		}
		i++;
	}
	//このときは穴
	if(marioEgoCol == i){
		return -1;
	}else{
		return i;
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